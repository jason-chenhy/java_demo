package com.chy.webmagic.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author chenhaoyu
 * @Created 2018-03-12 15:55
 */
public class PropertyUtils {

    public static Properties props = null;

    public static Properties initProperties(String file) {
        if (props != null) {
            props = new Properties();
            try {
                props.load(new FileInputStream(file));
                return props;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }

    public static String getValueString(String key) {
        return String.valueOf(props.getProperty(key));
    }

    public static String getValueString(String key, String defaultValue) {
        try {
            return String.valueOf(props.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}