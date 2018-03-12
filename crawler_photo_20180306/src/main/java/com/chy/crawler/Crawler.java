package com.chy.crawler;

import com.chy.BlockQueue.MyLinkBlockQueue;
import com.chy.DownloadPic;
import com.chy.ParserHttpUrl;
import com.chy.filter.Filter;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 14:48
 */
public class Crawler {

    //图片保存目录
    private String save_dir = "C:\\Users\\superuser\\Desktop\\2\\";

    //抓取图片的线程数
    private int thread_nums = 1;

    public Crawler(String save_dir) {
        this.save_dir = save_dir;
    }

    public Crawler() {
        int core_thread_num = Runtime.getRuntime().availableProcessors();
        this.thread_nums = core_thread_num;
    }

    public String getSave_dir() {
        return save_dir;
    }

    public void setSave_dir(String save_dir) {
        this.save_dir = save_dir;
    }

    public int getThread_nums() {
        return thread_nums;
    }

    public void setThread_nums(int thread_nums) {
        this.thread_nums = thread_nums;
    }

    //开始扒取网络地址的入口
    public void crawling(String url, final Filter filter) {
        final MyLinkBlockQueue linkBlockQueue = new MyLinkBlockQueue();
        linkBlockQueue.addUnVisitedUrl(url);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        final DownloadPic downloadPic = new DownloadPic();

        for (int i=0; i<thread_nums; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String visitUrl = linkBlockQueue.getUnVisitUrl();
                            downloadPic.downloadPic(visitUrl, save_dir);
                            //获取该url页面的所有符合条件的链接
                            //Set<String> links = ParserHttpUrl.extracLinks(visitUrl, filter);
                            //添加扒取的超链接进队列里
                            //for (String link : links) {
                            //    linkBlockQueue.addUnVisitedUrl(link);
                            //}
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            });
        }
    }
}
