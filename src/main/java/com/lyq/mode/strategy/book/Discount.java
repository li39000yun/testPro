package com.lyq.mode.strategy.book;

/**
 * 折扣，策略接口角色
 * Created by lyq on 2016/12/6.
 */
public interface Discount {

    /**
     * 计算图书的价格
     * @param money    图书的原价
     * @return    计算出打折后的价格
     */
    public double doDiscount(double money);

}
