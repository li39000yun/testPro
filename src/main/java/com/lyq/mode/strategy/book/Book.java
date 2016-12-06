package com.lyq.mode.strategy.book;

/**
 * 书籍、策略环境角色
 * Created by lyq on 2016/12/6.
 */
public class Book {

    private double money;// 单价
    private String name;// 书名
    private Discount discount;// 折扣

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void show() {
        System.out.printf("%s售价:%s%n", name, discount.doDiscount(money));
    }

}
