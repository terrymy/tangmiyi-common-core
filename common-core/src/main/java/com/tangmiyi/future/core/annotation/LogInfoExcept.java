package com.tangmiyi.future.core.annotation;


import java.lang.annotation.*;

/**
 * <p>log日志不打印注解，直接放在controller或方法名称上</p>
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfoExcept {

}
