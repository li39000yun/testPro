package com.lyq.mode.strategy.recharge;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象工厂类
 * Created by lyq on 2016/12/6.
 */
public class StrategyFactory {

    private static StrategyFactory factory = new StrategyFactory();

    private StrategyFactory() {

    }

    private static Map strategyMap = new HashMap();

    static {
        strategyMap.put(RechargeTypeEnum.E_BANK.value(), new EBankStrategy());
        strategyMap.put(RechargeTypeEnum.BUSI_ACCOUNTS.value(), new BusiAcctStrategy());
        strategyMap.put(RechargeTypeEnum.CARD_RECHARGE.value(), new CardStrategy());
        strategyMap.put(RechargeTypeEnum.MOBILE.value(), new MobileStrategy());
    }

    public Strategy creator(Integer type) {
        return (Strategy) strategyMap.get(type);
    }

    public static StrategyFactory getInstance() {
        return factory;
    }

}
