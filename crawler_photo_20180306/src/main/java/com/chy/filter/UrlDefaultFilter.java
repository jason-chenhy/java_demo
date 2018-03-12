package com.chy.filter;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 15:06
 */
public class UrlDefaultFilter implements Filter{
    @Override
    public boolean accept(String url) {
        if (url.contains("sina")) {//对包含sina的url放行, 否则不进行爬取
            return true;
        }else{
            return false;
        }
    }
}
