package com.lyq.mode.strategy.recharge;

/**
 * 抽象策略角色
 * Created by lyq on 2016/12/6.
 */
public interface Strategy {

    public Double calRecharge(Double money, RechargeTypeEnum type);

}
