package com.chy.webmagic.pipeline;

import com.chy.webmagic.constants.PageField;
import org.apache.commons.codec.digest.DigestUtils;
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
        for(String img : imgs) {
            download(img, task);
        }
    }

    /**
     * 下载图片
     * @param imgUrl
     * @param task
     */
    private void download(String imgUrl, Task task) {
        if (!StringUtils.isBlank(imgUrl)) {
            String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
            HttpGet request = new HttpGet(imgUrl);
            try(CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(request);
                BufferedInputStream in = new BufferedInputStream(response.getEntity().getContent());
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(getFile(path + DigestUtils.md5Hex(imgUrl) + imgUrl.substring(imgUrl.lastIndexOf(".")))))) {

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
