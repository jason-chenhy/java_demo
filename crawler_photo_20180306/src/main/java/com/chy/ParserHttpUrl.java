package com.chy;

import com.chy.filter.Filter;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取一个页面所有的超链接
 * @author chenhaoyu
 * @Created 2018-03-06 15:47
 */
public class ParserHttpUrl {

    public static Set<String> extracLinks(String url, Filter filter) {
        Set<String> links = new HashSet<>();
        try {
            Parser parser = new Parser(url);

            parser.setEncoding("UTF-8");
            TagNameFilter tagFilter = new TagNameFilter("A");
            NodeList list = parser.extractAllNodesThatMatch(tagFilter);

            for (int i=0; i<list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag) {
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();
                    if (filter.accept(linkUrl)) {
                        links.add(linkUrl);
                    }
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }

}
