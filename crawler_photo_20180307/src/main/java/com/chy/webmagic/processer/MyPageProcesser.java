package com.chy.webmagic.processer;

import com.chy.webmagic.constants.FileType;
import com.chy.webmagic.constants.PageField;
import com.chy.webmagic.constants.SiteImgDomain;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenhaoyu
 * @Created 2018-03-07 16:45
 */
public class MyPageProcesser implements PageProcessor{
    private static final String CRAWLER_REGEX = ".*?.";
    private static final String CRAWLER_IMG_REGEX = "img";
    private static final String CRAWLER_HTML_REGEX = "html";

    /**
     * 一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
      */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    //process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
        //二：抽取页面图片信息，并保存下来
        //Set<String> imgUrls = getAllImgUrls(page.getHtml().css(CRAWLER_IMG_REGEX).all());
        //page.putField(PageField.FIELD_ALL_IMGS, imgUrls);

        //三：从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex(SiteImgDomain.getBaseUrl(site.getDomain())+CRAWLER_REGEX+CRAWLER_HTML_REGEX).all());
        page.addTargetRequests(page.getHtml().links().regex(SiteImgDomain.getBaseUrl(site.getDomain())+CRAWLER_REGEX).all());
        //getWholeTargetRequests(page.getHtml().links().regex("http://desk\\.zol\\.com\\.cn/\\w+/").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 获取所有的图片地址
     * @param imgs
     * @return
     */
    private Set<String> getAllImgUrls(List<String> imgs) {
        Set<String> allImgs = new HashSet<>();
        String[] fileTypes = FileType.getTypeNames();

        String regex = "";
        for (String img : imgs) {
            for (String type : fileTypes) {
                regex = SiteImgDomain.getImgDomain(site.getDomain())+CRAWLER_REGEX+type;

                Set<String> tempImgs = getImgUrl(img, regex);
                if (tempImgs!=null && tempImgs.size()>0) {
                    allImgs.addAll(tempImgs);
                    break;
                }
            }
        }
        return allImgs;
    }

    /**
     * 正则获取所需字符串
     * @param url
     * @param regex
     * @return
     */
    private Set<String> getImgUrl(String url, String regex) {
        Set<String> urls = new HashSet<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }
}
