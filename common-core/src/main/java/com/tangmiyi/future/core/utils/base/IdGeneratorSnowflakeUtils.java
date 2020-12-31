package com.tangmiyi.future.core.utils.base;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenpengcheng
 * @date 2020/4/28
 */
@Slf4j
public class IdGeneratorSnowflakeUtils {

    /**
     * 只能0~31，5bit
     */
    private static long workerId = 1;
    /**
     * 只能0~31，5bit
     */
    private static long dataCenterId = 1;

    private static Snowflake snowflake = null;

    /**
     * 生成id
     *
     * @return
     */
    public static long genId() {
        return snowflake.nextId();
    }

    /**
     * 生成id,字符串列表
     *
     * @return
     */
    public static String genIdStr() {
        return snowflake.nextIdStr();
    }

    static {
        try {
            initWorkerDataCenterId();
            snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
        } catch (Exception e) {
            log.error("IdGeneratorSnowflake static Exception:", e);
            throw e;
        }
    }

    /**
     * 根据机器ip地址的后10位生成workerId, dataCenterId
     * 减少避免冲突的可能性
     */
    private static void initWorkerDataCenterId() {
        String host = NetUtil.getLocalhostStr();
        if (!Validator.isIpv4(host)) {
            throw new ValidateException("IdGeneratorSnowflake initWorkerDataCenterId, host ip error, host:{}", host);
        }
        String[] ipSplit = host.split("\\.");
        // workerId取最后一段（最大255，8位）的低五位（最大32）
        workerId = Long.parseLong(ipSplit[3]) % 32;
        // dataCenterId低位三位取最后一段（最大255，8位）的高三位
        long dataCenterIdLow = Long.parseLong(ipSplit[3]) >> 5;
        // dataCenterId高位两位取倒二段（最大255，8位）的低两位
        long dataCenterIdHigh = Long.parseLong(ipSplit[2]) % 4;
        dataCenterId = (dataCenterIdHigh << 3) + dataCenterIdLow;
        log.info("IdGeneratorSnowflake initWorkerId host:{}, workerId:{}, dataCenterId:{}", host, workerId, dataCenterId);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Long id = IdGeneratorSnowflakeUtils.genId();
            System.out.println(id);
        }
    }
}
