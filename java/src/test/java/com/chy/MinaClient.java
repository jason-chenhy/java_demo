package com.chy;

import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author chenhaoyu
 * @Created 2018-03-02 11:28
 */
public class MinaClient {
    private static final Logger logger = LoggerFactory.getLogger(MinaClient.class);

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000);
        //编写过滤器
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        //编写IOHandler
        connector.setHandler(new ClientIOHandler("大家好！"));

        connector.connect(new InetSocketAddress("localhost", 9123));
    }

}

class ClientIOHandler extends IoHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ClientIOHandler.class);

    private final String values;

    public ClientIOHandler(String values) {
        this.values = values;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        session.write(values);
    }
}