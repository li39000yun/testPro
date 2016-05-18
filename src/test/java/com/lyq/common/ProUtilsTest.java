package com.lyq.common;

import net.sf.javavp8decoder.imageio.WebPImageReader;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by lyq on 2016/5/17.
 */
public class ProUtilsTest {
    public void testWebp2jpg() throws Exception {
        ProUtils.webp2jpg("C:\\Users\\lyq\\Desktop\\1.webp");
    }

    @Test
    public void testWebp() throws Exception {
        File file1 = new File("C:\\Users\\lyq\\Desktop\\1.webp");
//        WebPImageReader webPImageReader = new WebPImageReader(ImageIO.read(file1));
    }
//    File file1 = new File(fileName);
//    File file2 = new File(fileName.replace("webp", "jpg"));
//    System.out.println(System.getProperty("java.library.path"));
//    try {
//        BufferedImage im = ImageIO.read(file1);
//        ImageIO.write(im, "jpg", file2);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
}