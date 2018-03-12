package com.chy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 15:12
 */
public class DownloadPic {

    private static final String ECODING = "UTF-8";

    private static final String IMGURL_REG = "<img.*src=(.*?)(jpg|png)(.*?)[>]{1}";
    //private static final String IMGURL_REG = "<img.*src=(.*?)[>]{1}";


    //private static final String IMGSRC_REG = "(https:\"?(.*?)(\"|>|\\s+))|(http:\"?(.*?)(\"|>|\\s+))";

    public void downloadPic(String url, String dirName) {
        String HTML = null;
        try {
            HTML = getHTML(url);

            if (null != HTML && !"".equals(HTML)) {
                //该url对应的网页所有是图片元素的代码
                //List<String> imgUrl = getImageUrl(HTML);
                //该页面所有图片元素的地址
                List<String> imgSrc = getImageSrc(HTML);
                //下载并保存图片
                download(imgSrc, dirName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private String getHTML(String url) throws Exception {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        CloseableHttpResponse response = client.execute(request);

        InputStream in = response.getEntity().getContent();
        byte[] buf = new byte[1024];
        int length = 0;
        StringBuffer sb = new StringBuffer();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, ECODING));
        }
        in.close();
        response.close();
        client.close();
        return sb.toString();

    }

    /**
     * 获取页面所有是图片元素的代码
     * @param HTML
     * @return
     */
    /*
    private List<String> getImageUrl(String HTML) {
        Pattern pattern = Pattern.compile(IMGURL_REG);
        Matcher matcher = pattern.matcher(HTML);
        List<String> listImgUrl = new ArrayList<>();

        while (matcher.find()) {
            String imgUrl = matcher.group();
            String brRegex = "<br>";
            String[] imgs = Pattern.compile(brRegex).split(imgUrl);
            for (String img : imgs) {
                listImgUrl.add(img);
            }
        }
        return listImgUrl;
    }
    */

    /**
     * 获取页面所有图片元素的地址
     * @param HTML
     * @return
     */
    private List<String> getImageSrc(String HTML) {
        Pattern pattern = Pattern.compile(IMGURL_REG);
        Matcher matcher = pattern.matcher(HTML);
        List<String> listImgSrc = new ArrayList<>();

        while (matcher.find()) {
            String imgUrl = (matcher.group(1)+matcher.group(2)).substring(1);
            if (!listImgSrc.contains(imgUrl)){
                listImgSrc.add(imgUrl);
            }
        }

        //for (String image : listImageUrl) {
        //    Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
        //    while (matcher.find()) {
        //        String url = matcher.group().substring(0, matcher.group().length() - 1);
        //        listImgSrc.add(url);
        //    }
        //}
        return listImgSrc;
    }

    /**
     * 下载并保存图片
     * @param listImgSrc
     * @param dirName
     * @throws Exception
     */
    private void download(final List<String> listImgSrc, final String dirName) throws Exception {

        final CloseableHttpClient client = HttpClients.createDefault();
        final CountDowns coutdowns = new CountDowns(listImgSrc.size());
        for (final String url : listImgSrc) {

            Thread workers = new Thread(new Runnable() {


                public void run() {
                    try {
                        String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                        HttpGet request = new HttpGet(url);

                        CloseableHttpResponse response = client.execute(request);
                        File file = new File(dirName + imageName);
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        try {
                            file.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                            doPrivilegedAction(dirName + imageName);
                        }

                        FileOutputStream out = new FileOutputStream(file);
                        InputStream in = response.getEntity().getContent();
                        byte[] buf = new byte[2048];
                        ByteBuffer buff = ByteBuffer.allocate(1024);
                        int length = 0;
                        while (true) {
                            int len = in.read(buf);
                            if (len != -1) {
                                out.write(buf, 0, len);
                            } else {
                                coutdowns.decreaseCounts();
                                in.close();
                                response.close();
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            });
            workers.start();
        }
        while(coutdowns.getCounts()>0){//如果同一个页面所有的图片下载线程没有下载完成，则等待
            Thread.sleep(500);
            System.out.println("该页面还有"+coutdowns.getCounts()+"个图片没下载!");
        }

        client.close();
    }

    /**
     * 以特权的权限在文件系统创建文件
     * @param fileName
     */
    private void doPrivilegedAction(final String fileName) {

        AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                makeFile(fileName);
                return null;
            }
        });
    }

    /**
     * 创建文件
     * @param fileName
     */
    public void makeFile(String fileName) {
        try {

            File fs = new File(fileName);
            fs.createNewFile();
        } catch (AccessControlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}