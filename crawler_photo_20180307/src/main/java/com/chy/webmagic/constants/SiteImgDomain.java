package com.chy.webmagic.constants;

/**
 * @author chenhaoyu
 * @Created 2018-03-13 10:05
 */
public enum SiteImgDomain {

    ZOL("desk.zol.com.cn", "http://desk.zol.com.cn/", "https://desk-fd.zol-img.com.cn/"),
    NET_BIAN("www.netbian.com", "http://www.netbian.com/", "http://img.netbian.com/");

    private String urlDomain;
    private String baseUrl;
    private String imgUrl;

    SiteImgDomain(String urlDomain, String baseUrl, String imgUrl) {
        this.urlDomain = urlDomain;
        this.baseUrl = baseUrl;
        this.imgUrl = imgUrl;
    }

    public String getUrlDomain() {
        return urlDomain;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public static String getImgDomain(String urlDomain) {
        for (SiteImgDomain siteImgDomain : SiteImgDomain.values()) {
            if (siteImgDomain.getUrlDomain().equals(urlDomain)) {
                return siteImgDomain.getImgUrl();
            }
        }
        return "";
    }

    public static String getBaseUrl(String urlDomain) {
        for (SiteImgDomain siteImgDomain : SiteImgDomain.values()) {
            if (siteImgDomain.getUrlDomain().equals(urlDomain)) {
                return siteImgDomain.getBaseUrl();
            }
        }
        return "";
    }
}
