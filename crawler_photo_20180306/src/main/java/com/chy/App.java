package com.chy;

import com.chy.crawler.Crawler;
import com.chy.filter.UrlDefaultFilter;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 15:05
 */
public class App {

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.setSave_dir("C:\\Users\\superuser\\Desktop\\2\\");
        crawler.setThread_nums(3);
        crawler.crawling("http://photo.sina.com.cn/", new UrlDefaultFilter());
    }

}
