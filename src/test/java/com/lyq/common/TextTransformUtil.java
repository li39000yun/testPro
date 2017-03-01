package com.lyq.common;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文本转换工具
 * 替换掉下划线并让紧跟它后面的字母大写
 * Created by 云强 on 2017/3/1.
 */
public class TextTransformUtil {
    /**
     * @param args
     */
    public static void main(String[] args) {
//        System.out.println(replaceUnderlineAndfirstToUpper("ni_hao_abc_","_",""));
        String filePath = "D:\\java\\ideaProjects\\testPro\\src\\test\\java\\com\\lyq\\yonyou\\szgzw\\finance\\EntFinanace.java";
        File file = new File(filePath);
        try {
            String content = FileUtils.readFileToString(file);
            String result = replaceUnderlineAndfirstToUpper(content, "_", "");
            FileUtils.writeStringToFile(file, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母大写
     *
     * @param srcStr
     * @return
     */
    public static String firstCharacterToUpper(String srcStr) {
        return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     *
     * @param srcStr
     * @param org
     * @param ob
     * @return
     */
    public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
        String newString = "";
        int first = 0;
        while (srcStr.indexOf(org) != -1 && srcStr.length() != srcStr.indexOf(org) + 1) {
            first = srcStr.indexOf(org);
            if (first != srcStr.length()) {
                newString = newString + srcStr.substring(0, first) + ob;
                srcStr = srcStr.substring(first + org.length(), srcStr.length());
                srcStr = firstCharacterToUpper(srcStr);
            }
        }
        newString = newString + srcStr;
        return newString;
    }
}
