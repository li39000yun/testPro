package com.lyq.tcb.vo;

import com.kingsoft.control.util.StringManage;

/**
 * 厦门智慧闸口封装对象
 * Created by lyq on 2016/7/7.
 */
public class Xmzhzk {

    public String MenuID = StringManage.FS_EMPTY;
    public String LoginName = StringManage.FS_EMPTY;
    public String PWD = StringManage.FS_EMPTY;
    public String Eir_No = StringManage.FS_EMPTY;
    public String Blo = StringManage.FS_EMPTY;
    public String Ctn = StringManage.FS_EMPTY;
    public String Driver = StringManage.FS_EMPTY;
    public String E_SEALNO = StringManage.FS_EMPTY;
    public int IsWhole = 0;
    public String TC_LOAD_DEPOT = StringManage.FS_EMPTY;

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

    public String getEir_No() {
        return Eir_No;
    }

    public void setEir_No(String eir_No) {
        Eir_No = eir_No;
    }

    public String getBlo() {
        return Blo;
    }

    public void setBlo(String blo) {
        Blo = blo;
    }

    public String getCtn() {
        return Ctn;
    }

    public void setCtn(String ctn) {
        Ctn = ctn;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getE_SEALNO() {
        return E_SEALNO;
    }

    public void setE_SEALNO(String e_SEALNO) {
        E_SEALNO = e_SEALNO;
    }

    public int getIsWhole() {
        return IsWhole;
    }

    public void setIsWhole(int isWhole) {
        IsWhole = isWhole;
    }

    public String getTC_LOAD_DEPOT() {
        return TC_LOAD_DEPOT;
    }

    public void setTC_LOAD_DEPOT(String TC_LOAD_DEPOT) {
        this.TC_LOAD_DEPOT = TC_LOAD_DEPOT;
    }
}
