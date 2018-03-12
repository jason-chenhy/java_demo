package com.chy.BlockQueue;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 14:58
 */
public class MyLinkBlockQueue {
    //已访问的url集合
    private CopyOnWriteArraySet<String> visitedUrls = new CopyOnWriteArraySet<>();
    //未访问的url
    private LinkedBlockingDeque<String> unVisitUrls = new LinkedBlockingDeque<>();

    //未访问的url出队列
    public String getUnVisitUrl() {
        String url = null;
        try {
            url = unVisitUrls.take();
            visitedUrls.add(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return url;
    }

    //新的url添加进来的时候进行验证, 保证只是添加一次
    public void addUnVisitedUrl(String url) {
        if(url!=null && !url.trim().equals("") && !visitedUrls.equals(url)
                && !unVisitUrls.contains(url)) {
            try {
                unVisitUrls.put(url);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
