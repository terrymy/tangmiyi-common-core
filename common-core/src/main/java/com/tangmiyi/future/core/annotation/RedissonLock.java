package com.tangmiyi.future.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {

    /**
     * 要锁哪个参数，0为起始参数
     */
    int lockIndex() default -1;

    /**
     * 是否等待，默认为true，false跳过本次执行
     * @return
     */
    boolean waiting() default true;

    /**
     * 锁多久后自动释放（单位：秒）
     */
    int leaseTime() default 30;
}
