package com.lyq.mode.strategy.book;

/**
 * 高级会员折扣，策略接口实现类
 * Created by lyq on 2016/12/6.
 */
public class SeniorDiscount implements Discount {

    @Override
    public double doDiscount(double money) {
        return money * 0.8;
    }

}
