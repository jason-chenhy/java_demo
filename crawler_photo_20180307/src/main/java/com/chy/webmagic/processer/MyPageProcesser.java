package com.chy.webmagic.processer;

import com.chy.webmagic.constants.PageField;
import com.chy.webmagic.constants.Props;
import com.chy.webmagic.utils.PropertyUtils;
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
        List<String> allImgs = getAllImgs(page, Props.PROPERTY_CRAWLER_PAGE_CUSTOM_IMG_SELECTOR, "li a img:src");
        page.putField(PageField.FIELD_CUSTOM_IMGS, new HashSet<>(allImgs));
        if(allImgs.size() <= 0) {
            allImgs = getAllImgs(page, Props.PROPERTY_CRAWLER_PAGE_ALL_IMG_SELECTOR, "img:src");
            page.putField(PageField.FIELD_ALL_IMG, new HashSet<>(allImgs));
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
     * @param defaultSelector
     * @return
     */
    private List<String> getAllImgs(Page page,String selector,  String defaultSelector) {
        List<String> allImgs = new ArrayList<>();
        String picSel = PropertyUtils.getValueString(selector, defaultSelector);
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
        System.out.println(allImgs);
        return allImgs;
    }
}
