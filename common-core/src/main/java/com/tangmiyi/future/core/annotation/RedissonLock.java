package com.tangmiyi.future.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {

    /**
     * 要锁哪个参数
     */
    int lockIndex() default -1;

    /**
     * 是否调用trylock，默认为true
     * @return
     */
    boolean tryLock() default true;

    /**
     * 锁多久后自动释放（单位：秒）
     */
    int leaseTime() default 30;
}
