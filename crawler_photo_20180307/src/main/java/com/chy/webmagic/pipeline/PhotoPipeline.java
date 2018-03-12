package com.chy.webmagic.pipeline;

import com.chy.webmagic.constants.PageField;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.FilePipeline;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;

/**
 * @author chenhaoyu
 * @Created 2018-03-07 22:13
 */
public class PhotoPipeline extends FilePipeline {
    private static final Logger logger = LoggerFactory.getLogger(PhotoPipeline.class);

    public PhotoPipeline() {
    }

    public PhotoPipeline(String path) {
        super(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        Set<String> imgs = (Set<String>) resultItems.getAll().get(PageField.FIELD_ALL_IMGS);
        Set<String> finalImgs = imgs.size()==0?(Set<String>) resultItems.getAll().get(PageField.FIELD_SINGLETON_IMG):imgs;
        for(String img : finalImgs) {
            download(processImgUrl(img));
        }
    }

    /**
     * 截取 img 的url路径
     * 因为 img 标签的属性有 src、srch、loadsrc，所以需要截取
     * @param value
     * @return
     */
    private String processImgUrl(String value) {
        int start = value.indexOf(PageField.REGEX_HTTP);
        if (start != -1) {
            int tempJpg = value.toLowerCase().indexOf(FileSuffix.SUFFIX_JPG);
            int tempPng = value.toLowerCase().indexOf(FileSuffix.SUFFIX_PNG);
            int end = tempJpg!=-1 ? (tempJpg+3) : (tempPng!=-1 ? (tempPng+3) : value.length());
            value = value.substring(start, end);
        } else {
            value = "";
        }
        logger.info(value);
        return value;
    }

    /**
     * 下载图片
     * @param imgUrl
     */
    private void download(String imgUrl) {
        if (!StringUtils.isBlank(imgUrl)) {
            HttpGet request = new HttpGet(imgUrl);
            try(CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(request);
                BufferedInputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(getFile(path + imgUrl.substring(imgUrl.lastIndexOf("/")))))) {

                byte[] buf = new byte[2048];
                int length = 0;
                while ((length=in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                logger.info(imgUrl + "下载成功！");
            } catch (Exception e) {
                logger.info(imgUrl + "下载失败！");
                e.printStackTrace();
            }
        }
    }
}
