package com.lyq.mode.strategy.recharge;

/**
 * 环境角色
 * Created by lyq on 2016/12/6.
 */
public class Context {
    private Strategy strategy;

    public Double calRecharge(Double money, Integer type) {
        strategy = StrategyFactory.getInstance().creator(type);
        return strategy.calRecharge(money, RechargeTypeEnum.valueOf(type));
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
