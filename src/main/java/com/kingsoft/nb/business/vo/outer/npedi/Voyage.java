package com.kingsoft.nb.business.vo.outer.npedi;

/**
 * 单个信息
 * @author lxm
 * @date 2016年5月9日
 */
public class Voyage {
  private String cpcode= "";//代码
  private String ctnend= "";//进箱截止时间
  private String ctnstart= "";//进箱开始
  private String ports= "";//所有码头
  private String vesselcode= "";//un代码
  private String vesselename= "";//英文船名
  private String voyage= "";//航次
  
  
  public String getCpcode() {
    return cpcode;
  }
  public void setCpcode(String cpcode) {
    this.cpcode = cpcode;
  }
  public String getCtnend() {
    return ctnend;
  }
  public void setCtnend(String ctnend) {
    this.ctnend = ctnend;
  }
  public String getCtnstart() {
    return ctnstart;
  }
  public void setCtnstart(String ctnstart) {
    this.ctnstart = ctnstart;
  }
  public String getPorts() {
    return ports;
  }
  public void setPorts(String ports) {
    this.ports = ports;
  }
  public String getVesselcode() {
    return vesselcode;
  }
  public void setVesselcode(String vesselcode) {
    this.vesselcode = vesselcode;
  }
  public String getVesselename() {
    return vesselename;
  }
  public void setVesselename(String vesselename) {
    this.vesselename = vesselename;
  }
  public String getVoyage() {
    return voyage;
  }
  public void setVoyage(String voyage) {
    this.voyage = voyage;
  }
}
