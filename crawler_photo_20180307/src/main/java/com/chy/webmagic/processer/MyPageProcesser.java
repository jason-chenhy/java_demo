package com.chy.webmagic.processer;

import com.chy.webmagic.constants.PageField;
import com.chy.webmagic.constants.Props;
import com.chy.webmagic.utils.PropertyUtils;
import sun.nio.cs.ext.PCK;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author chenhaoyu
 * @Created 2018-03-07 16:45
 */
public class MyPageProcesser implements PageProcessor{
    private static final String SELECTOR_SPLIT_REGEX = ",";
    private static final String ATTRIBUTE_SPLIT_REGEX = ":";
    /**
     * 一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
      */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    //process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
        //二：抽取页面图片信息，并保存下来
        List<String> allImgs = getAllImgs(page);

        page.putField(PageField.FIELD_ALL_IMGS, new HashSet<>(allImgs));
        if(allImgs.size() <= 0) {
            page.putField(PageField.FIELD_SINGLETON_IMG, page.getHtml().$("img").all());
        }
        //三：从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex("http://desk\\.zol\\.com\\.cn/[\\w/]+\\.html").all());
        //getWholeTargetRequests(page.getHtml().links().regex("http://desk\\.zol\\.com\\.cn/\\w+/").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<String> getWholeTargetRequests(List<String> targetRequests) {
        for (String str:targetRequests) {
            System.out.println(str);
        }
        return null;
    }

    /**
     * 抽取页面图片信息，并保存下来
     * @param page
     * @return
     */
    private List<String> getAllImgs(Page page) {
        List<String> allImgs = new ArrayList<>();
        String picSel = PropertyUtils.getValueString(Props.PROPERTY_CRAWLER_PAGE_PICTURE_SELECTOR, "li a img:src");
        if (picSel.contains(SELECTOR_SPLIT_REGEX)) {
            String[] sels = picSel.split(SELECTOR_SPLIT_REGEX);
            for (String sel : sels) {
                String[] sel_attr = sel.split(ATTRIBUTE_SPLIT_REGEX);
                List<String> tempList = page.getHtml().$(sel_attr[0], sel_attr[1]).all();
                if (tempList!=null && tempList.size()>0) {
                    allImgs.addAll(tempList);
                }
            }
        }else {
            String[] sel_attr = picSel.split(ATTRIBUTE_SPLIT_REGEX);
            allImgs = page.getHtml().$(sel_attr[0], sel_attr[1]).all();
        }
        return allImgs;
    }
}
