package com.lyq.mode.strategy.recharge;

/**
 * 测试客户端
 * Created by lyq on 2016/12/6.
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        Double money = context.calRecharge(100D, RechargeTypeEnum.E_BANK.value());
        System.out.println("网银充值100，需缴费:" + money);

        money = context.calRecharge(100D, RechargeTypeEnum.BUSI_ACCOUNTS.value());
        System.out.println("商家账户充值100，需缴费:" + money);

        money = context.calRecharge(100D, RechargeTypeEnum.MOBILE.value());
        System.out.println("手机充值100，需缴费:" + money);

        money = context.calRecharge(100D, RechargeTypeEnum.CARD_RECHARGE.value());
        System.out.println("充值卡充值100,需缴费:" + money);
    }
}
