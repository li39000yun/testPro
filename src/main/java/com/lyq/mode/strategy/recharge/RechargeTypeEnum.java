package com.lyq.mode.strategy.recharge;

/**
 * 枚举
 * Created by lyq on 2016/12/6.
 */
public enum RechargeTypeEnum {
    E_BANK(1, "银行"),
    BUSI_ACCOUNTS(2, "商户账号"),
    MOBILE(3, "手机卡充值"),
    CARD_RECHARGE(4, "充值卡");

    RechargeTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static RechargeTypeEnum valueOf(int value) {
        for (RechargeTypeEnum type : RechargeTypeEnum.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;

    }

    private int value;
    private String description;

    public int value() {
        return value;
    }

    public String description() {
        return description;
    }
}
