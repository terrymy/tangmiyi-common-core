package com.tangmiyi.future.core.utils.base;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 该方法用于将异常信息详细记录到日志中，效果同：e.printStackTrace();
 */
public class ExUtils {
    public static String getEDetail(Throwable e) {
        ExceptionUtils.getStackTrace(e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }
}
