/**
 * @(#)ConnectionLog.java 2016-08-25
 * <p>
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved.
 * <p>
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.entity.system;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.Console;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 接口操作日志
 *
 * @author kingsoft
 *
 * @version 2016-08-25
 *
 * @since JDK 1.6.0_43
 *
 */

public class ConnectionLog implements MappingTableModel {

    private static final long serialVersionUID = 1L;

    private static final String $MAPPING_TABLE_NAME = "sys_connection_log";//数据库表名称

    protected int count = 0;//分页列表中的序号
    protected int id = Integer.MIN_VALUE;//接口日志编号
    protected String accreditId = StringManage.FS_EMPTY;//授权编号,引用(accredit_corporate.accredit_id)
    protected String operationMan = StringManage.FS_EMPTY;//操作人
    protected String operationTime = StringManage.FS_EMPTY;//操作时间,格式:yyyy-MM-dd HH:mm:ss
    protected String service = StringManage.FS_EMPTY;//接口地址及名称
    protected String parameter = StringManage.FS_EMPTY;//传递的参数
    protected String result = StringManage.FS_EMPTY;//返回结果
    protected String remark = StringManage.FS_EMPTY;//日志描述备注

    public ConnectionLog() {

    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccreditId() {
        return accreditId;
    }

    public void setAccreditId(String accreditId) {
        if (accreditId != null) {
            this.accreditId = accreditId;
        }
    }

    public String getOperationMan() {
        return operationMan;
    }

    public void setOperationMan(String operationMan) {
        if (operationMan != null) {
            this.operationMan = operationMan;
        }
    }

    public String getOperationTime() {
        if (!StringManage.isEmpty(operationTime)) {
            return operationTime;
        }
        return Console.FS_TIME.getNow();
    }

    public void setOperationTime(String operationTime) {
        if (operationTime != null) {
            this.operationTime = operationTime;
        }
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        if (service != null) {
            this.service = service;
        }
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        if (parameter != null) {
            this.parameter = parameter;
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        if (result != null) {
            this.result = result;
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        if (remark != null) {
            this.remark = remark;
        }
    }

    public final String mappingTableName() {
        return $MAPPING_TABLE_NAME;
    }

    public void clone(MappingTableModel model) {
        if (!(model instanceof ConnectionLog)) {
            return;
        }
        ConnectionLog obj = (ConnectionLog) model;
        if (obj != null) {
            obj.count = count;
            obj.id = id;
            obj.accreditId = accreditId;
            obj.operationMan = operationMan;
            obj.operationTime = operationTime;
            obj.service = service;
            obj.parameter = parameter;
            obj.result = result;
            obj.remark = remark;
        }
    }

    public void validate() throws DataBaseException {
        if (StringManage.isEmpty(operationTime)) {
            throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR, "操作时间 不能为空.");
        }

    }

    public String toString() {
        String br = "\r\n";
        StringBuilder sb = new StringBuilder();
        sb.append("时间：").append(getOperationTime()).append(br);
        sb.append(" 地址：").append(service).append(br);
        sb.append(" 参数：").append(parameter).append(br);
        sb.append(" 结果：").append(result).append(br);
        sb.append(" 备注：").append(remark).append(br);
        return sb.toString();
    }
}