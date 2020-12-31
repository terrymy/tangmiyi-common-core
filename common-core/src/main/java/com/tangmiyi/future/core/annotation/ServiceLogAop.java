package com.tangmiyi.future.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务接口接口日志
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLogAop {

    /**
     * 是否打开info日志。true: 日志将输出接口入参、返回值。false：仅当接口执行失败或异常时，日志将输出接口入参、返回值
     * 注意高并发接口请不要打开info日志，避免影响接口性能以及打满磁盘
     *
     * @return
     */
    boolean logInfoOutput() default true;

}
