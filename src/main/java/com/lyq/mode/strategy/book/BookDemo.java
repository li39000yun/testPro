package com.lyq.mode.strategy.book;

/**
 * 使用策略模式卖书籍
 * Created by lyq on 2016/12/6.
 */
public class BookDemo {
//    假设现在要设计一个贩卖各类书籍的电子商务网站的购物车系统。
// 一个最简单的情况就是把所有货品的单价乘上数量，但是实际情况肯定比这要复杂。
// 比如，本网站可能对所有的高级会员提供每本20%的促销折扣；
// 对中级会员提供每本10%的促销折扣；对初级会员没有折扣。

    public static void main(String[] args) {
        Book book = new Book();
        book.setMoney(1000);
        book.setName("哈利波特");
        book.setDiscount(new SeniorDiscount());
        book.show();
        book.setDiscount(new IntermediateDiscount());
        book.show();
    }

}
