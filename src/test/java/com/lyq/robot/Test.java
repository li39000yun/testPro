package com.lyq.robot;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * 模拟键盘鼠标操作
 * Created by lyq on 2016/12/9.
 */
public class Test {

    public static void main(String[] args) throws AWTException, IOException {
        Robot robot = new Robot();// 创建一个robot对象
        Runtime.getRuntime().exec("notepad");// 打开一个记事本程序
        robot.delay(2000);// 等待2秒
        // 窗口最大化
        keyPressWithAlt(robot, KeyEvent.VK_SPACE);// 按下alt+空格
        keyPress(robot, KeyEvent.VK_X);// 按下x
        robot.delay(1000);// 等待1秒
        keyPressString(robot, "大家好，我是一个小机器人,我有很多本领呢！");
        robot.delay(3000);// 等待3秒
        keyPress(robot, KeyEvent.VK_ENTER);// 按下enter换行
        keyPressString(robot, "现在，我向大家展示一下......");
        robot.delay(3000);// 等待3秒
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        keyPressString(robot, "首先，我能按下 键盘的任何一个键。下面,我单独按下a,b,c,d键"); //输入字符串
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        robot.delay(3000);  //等待 3秒
        keyPress(robot, KeyEvent.VK_A); //按下 a 键
        robot.delay(2000);  //等待 2秒
        keyPress(robot, KeyEvent.VK_B); //按下 b 键
        robot.delay(2000);  //等待 2秒
        keyPress(robot, KeyEvent.VK_C); //按下 c 键
        robot.delay(2000);  //等待 2秒
        keyPress(robot, KeyEvent.VK_D); //按下 d 键
        robot.delay(2000);  //等待 2秒
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        keyPressString(robot, "呵呵，完成了。。。。  ");
        robot.delay(3000);  //等待 3秒
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        keyPressString(robot, "恩，对了    上面 文字很多  是不是 感到 很乱呢？？？     我现在 帮你清空一下 ");
        robot.delay(2000);  //等待 2秒
        keyPressWithCtrl(robot, KeyEvent.VK_A); //按下 ctrl+A 全选
        robot.delay(2000);  //等待 2秒
        keyPress(robot, KeyEvent.VK_DELETE); //清除
        robot.delay(3000);  //等待 3秒
        keyPressString(robot, "恩，现在 是不是 觉得 清爽多了              另外 我还会按 组合键呢 ...");
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        robot.delay(3000);  //等待 3秒
        keyPressString(robot, "................好像已经 演示过了 吧 ，呵呵    ");
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        robot.delay(3000);  //等待 3秒
        keyPressString(robot, "其实，我还有很多本领呢                           现在就不向大家展示了 .....");
        keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
        robot.delay(3000);  //等待 3秒
        keyPressString(robot, "谢谢大家！！！！！");
    }

    //ctrl+按键
    private static void keyPressWithCtrl(Robot robot, int key) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(100);
    }

    private static void keyPressString(Robot robot, String str) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();// 获取剪贴板
        Transferable tText = new StringSelection(str);
        clip.setContents(tText, null);// 设置剪贴板内容
        keyPressWithCtrl(robot, KeyEvent.VK_V);// 粘贴
        robot.delay(100);
    }

    private static void keyPress(Robot robot, int key) {
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.delay(100);
    }

    private static void keyPressWithAlt(Robot robot, int key) {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.delay(100);
    }

}
