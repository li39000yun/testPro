package com.lyq.test;

import com.kingsoft.control.Console;
import com.kingsoft.control.dao.AbstractDAO;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by lyq on 2016/5/12.
 */
public class TestSql extends AbstractDAO {

    public void testSql() {
        StringBuffer sb = new StringBuffer();
        appendEscape(sb, "ww'");
        System.out.println(mysql_escape("ww'"));
        System.out.printf(sb.toString());

        double number1 = -12323.73;
        double number2 = 100000000;
        System.out.println(new BigDecimal(number1).setScale(2, RoundingMode.HALF_DOWN).toString());
        System.out.println(new BigDecimal(number2).setScale(2, RoundingMode.HALF_DOWN).toString());
        BigDecimal num = BigDecimal.valueOf(number1);
        BigDecimal num2 = BigDecimal.valueOf(number2);
        BigDecimal num3 = new BigDecimal(number1);
        num3 = num3.setScale(2, RoundingMode.HALF_DOWN);
        System.out.println(num.toString());
        System.out.println(num2.setScale(2, RoundingMode.HALF_DOWN).toString());
        System.out.println(num3.toString());
    }
}
