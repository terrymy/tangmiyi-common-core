package com.tangmiyi.future.core.utils.base;

import java.util.Date;

/**
 * @Classname IdUtil
 * @Description TODO
 * @Date 2020/2/14 14:44
 * @Created by hyc
 */
public class IdUtils {
    /**
     * 生成id
     *
     * @param date      日期
     * @param format    格式
     * @param suffixLen 尾部长度
     * @return
     */
    private static String genId(Date date, String format, int suffixLen) {
        return DateUtils.formatDate(date, format) + (int) ((Math.random() * 9 + 1) *  Math.pow(10, suffixLen -1));
    }
    /**
     * 制定18位提现流水号，格式为yyyyddmmhhmmssABCD，前14位为用户提现时间（年月日时分秒），后4位为1-9999的随机数，不重复。
     */
    public static String genWithdrawId() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return genId(new Date(), DateUtils.FULL_SEQ_FORMAT, 4);
    }

    public static void main(String[] args) {
        System.out.println(genWithdrawId());
    }
}
