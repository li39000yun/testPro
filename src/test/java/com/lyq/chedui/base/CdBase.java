package com.lyq.chedui.base;

/**
 * Created by lyq on 2016/5/13.
 */
public class CdBase {
    private String accreditId = "";// 授权编号
    private String userId = "";// 用户名
    private String password = "";// 密码
    private String methodname = "";// 方法名

    public String getAccreditId() {
        return accreditId;
    }

    public void setAccreditId(String accreditId) {
        this.accreditId = accreditId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }
}
