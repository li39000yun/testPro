package com.lyq.mode.strategy.recharge;

/**
 * 具体策略角色
 * Created by lyq on 2016/12/6.
 */
public class CardStrategy implements Strategy {
    @Override
    public Double calRecharge(Double money, RechargeTypeEnum type) {
        return money + money * 0.1;
    }
}
