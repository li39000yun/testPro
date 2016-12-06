package com.lyq.mode.strategy.book;

/**
 * 中级会员,策略接口实现类
 * Created by lyq on 2016/12/6.
 */
public class IntermediateDiscount implements Discount {
    @Override
    public double doDiscount(double money) {
        return money * 0.9;
    }
}
