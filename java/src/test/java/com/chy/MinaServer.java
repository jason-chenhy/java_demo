package com.chy;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author chenhaoyu
 * @Created 2018-03-02 10:38
 */
public class MinaServer {
    private static final Logger logger = LoggerFactory.getLogger(MinaServer.class);

    public static void main(String[] args) {
        IoAcceptor acceptor = new NioSocketAcceptor();
        //初始化套接字设置
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        //编写过滤器
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //编写IOHandler
        acceptor.setHandler(new ServerIOHandler());

        try {
            acceptor.bind(new InetSocketAddress(9123));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ServerIOHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerIOHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        logger.info("Receive message is: "+str);
        if("quit".equals(str)) {
            session.close(true);
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.info(cause.getMessage());
    }
}