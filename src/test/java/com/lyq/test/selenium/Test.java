package com.lyq.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

/**
 * Created by lyq on 2016/12/9.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        // 设置 chrome 的路径
        System.setProperty(
                "webdriver.chrome.driver",
                "C:/Users/sunlc/AppData/Local/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome
        @SuppressWarnings("deprecation")
        ChromeDriverService service = new ChromeDriverService.Builder()

                .usingDriverExecutable(
                        new File(
                                "E:/lib/selenium/dirver/chromedriver_2.9.exe"))

                .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        WebDriver driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        // 让浏览器访问 Baidu
        driver.get("http://www.baidu.com");
        // 用下面代码也可以实现
        // driver.navigate().to("http://www.baidu.com");
        // 获取 网页的 title
        System.out.println(" Page title is: " + driver.getTitle());
        // 通过 id 找到 input 的 DOM
        WebElement element = driver.findElement(By.id("kw1"));
        // 输入关键字
        element.sendKeys("zTree");
        // 提交 input 所在的 form
        element.submit();
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition() {
            @Override
            public Object apply(Object o) {
                return null;
            }

            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("ztree");
            }
        });
        // 显示搜索结果页面的 title
        System.out.println(" Page title is: " + driver.getTitle());
        // 关闭浏览器
        driver.quit();
        // 关闭 ChromeDriver 接口
        service.stop();
    }

    //启动并加载firebug插件
    public static void myFirefox() {
        //加载firebug插件
        File file = new File("files/firebug-1.8.4.xpi");
        FirefoxProfile firefoxprofile = new FirefoxProfile();
        firefoxprofile.addExtension(file);
        //设置firebug插件属性
        firefoxprofile.setPreference("extension.firebug.currentVersion", "1.8.4");
        //启动firefox
        WebDriver webdriver = new FirefoxDriver(firefoxprofile);
        webdriver.get("http://www.baidu.com");
        //注意这里 Driver.Quit()退出driver；Driver.Close()关闭当前窗口；
        webdriver.close();
    }
}
