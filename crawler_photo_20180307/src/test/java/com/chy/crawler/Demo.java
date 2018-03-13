package com.chy.crawler;

import com.chy.webmagic.pipeline.PhotoPipeline;
import com.chy.webmagic.processer.MyPageProcesser;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author chenhaoyu
 * @Created 2018-03-07 17:22
 */
public class Demo {

    public static void main(String[] args) {

        Spider.create(new MyPageProcesser())
                //.addUrl("http://www.netbian.com/")
                .addUrl("http://desk.zol.com.cn/")
                .addPipeline(new PhotoPipeline("C:\\Users\\superuser\\Desktop\\2\\"))
                //使用内存队列保存待抓取URL
                //使用BloomFilter来进行去重，占用内存较小，但是可能漏抓页面
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(5)))
                .thread(5).run();

    }

}
