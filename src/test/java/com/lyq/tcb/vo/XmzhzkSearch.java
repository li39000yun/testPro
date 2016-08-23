package com.lyq.tcb.vo;

import com.kingsoft.control.util.StringManage;

/**
 * 厦门智慧闸口封装对象
 * Created by lyq on 2016/7/7.
 */
public class XmzhzkSearch {

    public String MenuID = StringManage.FS_EMPTY;
    public String LoginName = StringManage.FS_EMPTY;
    public String PWD = StringManage.FS_EMPTY;
    public String Blo = StringManage.FS_EMPTY;

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public String getBlo() {
        return Blo;
    }

    public void setBlo(String blo) {
        Blo = blo;
    }

}
