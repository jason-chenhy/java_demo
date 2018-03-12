package com.chy.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenhaoyu
 * @Created 2018-03-06 16:52
 */
public class RegexDemo {

    private static final String IMG_TABLE = "<img.*src=(.*?)(jpg|png)(.*?)[>]{1}";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(IMG_TABLE);
        Matcher matcher = pattern.matcher("sadadada <img src=\"http://h.hiphotos.baidu.com/image/h%3D300/sign=c212fdd277899e51678e3c1472a6d990/e824b899a9014c0899ee068a067b02087af4f4cc.jpg\" width=\"320.944888989068\" height=\"213.87967992787108\"> adasddfad");

        System.out.println(matcher.groupCount());
        while (matcher.find()) {
            String str = matcher.group(1)+matcher.group(2);
            System.out.println(str);
        }
    }
}
