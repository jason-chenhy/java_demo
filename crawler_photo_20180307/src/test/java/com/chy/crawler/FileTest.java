package com.chy.crawler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * @author chenhaoyu
 * @Created 2018-03-08 15:34
 */
public class FileTest {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\superuser\\Desktop\\2\\");
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                try {
                    BufferedImage image = ImageIO.read(pathname);
                    if (image.getWidth()>300 || image.getHeight()>200){
                        return true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        for (File temp : files) {
            System.out.println(temp.getName());
        }
    }
}
