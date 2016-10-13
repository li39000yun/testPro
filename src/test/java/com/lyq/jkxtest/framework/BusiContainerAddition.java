package com.lyq.jkxtest.framework;

import com.kingsoft.control.database.MappingTableModel;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.util.StringManage;

public class BusiContainerAddition
  implements MappingTableModel
{
  private static final long serialVersionUID = 1L;
  private static final String $MAPPING_TABLE_NAME = "busi_container_addition";
  protected int count = 0;
  protected String accreditId = "";
  protected String busiId = "";
  protected int sequence = 1;
  protected String fileName = "";

  public int getCount()
  {
    return this.count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getAccreditId() {
    return this.accreditId;
  }

  public void setAccreditId(String accreditId) {
    if (accreditId != null)
      this.accreditId = accreditId;
  }

  public String getBusiId()
  {
    return this.busiId;
  }

  public void setBusiId(String busiId) {
    if (busiId != null)
      this.busiId = busiId;
  }

  public int getSequence()
  {
    return this.sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String fileName) {
    if (fileName != null)
      this.fileName = fileName;
  }

  public final String mappingTableName()
  {
    return "busi_container_addition";
  }

  public void clone(MappingTableModel model) {
    if (!(model instanceof BusiContainerAddition)) return;
    BusiContainerAddition obj = (BusiContainerAddition)model;
    if (obj != null) {
      obj.count = this.count;
      obj.accreditId = this.accreditId;
      obj.busiId = this.busiId;
      obj.sequence = this.sequence;
      obj.fileName = this.fileName;
    }
  }

  public void validate() throws DataBaseException {
    if (StringManage.isEmpty(this.accreditId)) {
      throw new DataBaseException(5, "授权编号 不能为空.");
    }
    if (StringManage.isEmpty(this.busiId))
      throw new DataBaseException(5, "承运单号 不能为空.");
  }
}