package com.lyq.common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 工具类
 * Created by lyq on 2016/5/17.
 */
public class ProUtils {

    public static void webp2jpg(String fileName) {
        File file1 = new File(fileName);
        File file2 = new File(fileName.replace("webp", "jpg"));
        System.out.println(System.getProperty("java.library.path"));
        try {
            BufferedImage im = ImageIO.read(file1);
            ImageIO.write(im, "jpg", file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
