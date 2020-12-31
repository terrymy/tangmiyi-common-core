package com.tangmiyi.future.core.utils.number;

import com.tangmiyi.future.core.utils.base.DateFormatUtils;
import com.tangmiyi.future.core.utils.base.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by carbinlin on 2017/3/22.
 * 订单号工具类
 * <p>
 * orderNumber(每部分用-分开)
 * 10-00-170322-66665512-123-32
 * 2位 业务线编号
 * 2位 机器编号
 * 6位 年月日（年取后后两位）
 * 8位 当前时间距离当天0时0分0秒的时间戳
 * 3位 序列，毫秒内计数
 * 2位 校验码  算法： ISO 7064,MOD 97-10；序号乘以1000加上业务线码再乘以100，除以97，再用98减去上述余数，
 * 所得结果中的两个数字就是校验码
 */
@Slf4j
public class OrderNumberWorker {


    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 业务线ID
     */
    private String bussinessId;

    /**
     * 机器ID
     */
    private String machineId;

    public static volatile OrderNumberWorker instance;//未初始化

    /**
     * 构造函数
     */
    public static OrderNumberWorker getInstance() {
        if (instance == null) {
            synchronized (OrderNumberWorker.class) {
                if (instance == null) {
                    instance = new OrderNumberWorker();
                }
            }
        }
        return instance;
    }

    /**
     * 构造函数
     */
    public OrderNumberWorker() {

    }

    /**
     * 获取订单号
     *
     * @return
     */
    public synchronized BigInteger nextNumber(String bussinessId, String machineId) {
        long timestamp = timeGen();
        long timeMillions = DateUtils.timeMillions(timestamp);

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = sequence + 1;
            //毫秒内序列溢出
            if (sequence > 999) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //2位业务线 + 2位机器ID +6位年月日 + 8位时间戳 + 3位序列号 + 2位校验码
        return new BigInteger(bussinessId + machineId + getDate(timestamp) + numberToStringByLength(8, timeMillions) +
                numberToStringByLength(3, sequence) +
                getCheckTwo(timeMillions + sequence, Integer.parseInt(bussinessId)));
    }

    /**
     * 返回当前日期的 年后两位、月、日（例如：170322）
     *
     * @param currentMillions
     * @return
     */
    private long getDate(long currentMillions) {
        String shortYearMonthDay = DateFormatUtils.formatDate("yyyyMMdd", new Date(currentMillions)).substring(2);
        return Long.parseLong(shortYearMonthDay);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 生成两位校验码
     * 算法： ISO 7064,MOD 97-10
     * 序号乘以1000加上业务线码再乘以100，除以97，再用98减去上述余数，
     * 所得结果中的两个数字就是校验码
     */

    private String getCheckTwo(long sequence, int bussinessId) {
        sequence = sequence * 1000 + bussinessId;
        sequence = sequence * 100;
        sequence = sequence % 97;
        sequence = 98 - sequence;

        //可能出现一位的数字，强制转换成两位
        return numberToStringByLength(2, sequence);
    }

    /**
     * 根据数字产生指定长度的字符串
     *
     * @param length 字符串长度
     * @param number 数字
     * @return
     */
    private static String numberToStringByLength(int length, long number) {
        String format = "%0" + length + "d";
        String numberString = String.format(format, number);
        return numberString;
    }

    //==============================Test=============================================

    /**
     * 测试
     */
    public static void main(String[] args) {
        OrderNumberWorker orderNumberWorker = OrderNumberWorker.getInstance();

        for (int i = 0; i < 100; i++) {
            BigInteger id = orderNumberWorker.nextNumber("10", "01");
            System.out.println(id);
        }
    }
}
