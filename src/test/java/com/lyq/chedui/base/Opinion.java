package com.lyq.chedui.base;

/**
 * Created by lyq on 2016/5/13.
 */
public class Opinion extends CdBase{
    private String contents = "";// 内容
    private String uniqueKey = "";// 唯一标识(用户名+年月日时分秒)
    private String mobilePhoneInfo = "";// 手机信息
    private String version = "";// 版本信息
    private String imageFormat = "";// 图片格式(webp、png)
    private String[] imageStreams = new String[0];// 图片流数组

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getMobilePhoneInfo() {
        return mobilePhoneInfo;
    }

    public void setMobilePhoneInfo(String mobilePhoneInfo) {
        this.mobilePhoneInfo = mobilePhoneInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getImageStreams() {
        return imageStreams;
    }

    public void setImageStreams(String[] imageStreams) {
        this.imageStreams = imageStreams;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }
}
