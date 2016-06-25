package com.lyq.tcb.vo;

import java.io.Serializable;

/**
 * 调度确认司机费用信息对象
 *
 * @author liyunqiang
 * @version 2015-12-2
 */
public class TcbComfirmDriverFeeVO extends TcbBase implements Serializable {

    private static final long serialVersionUID = 1L;
    protected TcbComfirmDriverFee data = new TcbComfirmDriverFee();// 业务编号数组

    public TcbComfirmDriverFee getData() {
        return data;
    }

    public void setData(TcbComfirmDriverFee data) {
        this.data = data;
    }


}
