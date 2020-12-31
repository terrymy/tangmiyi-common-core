package com.tangmiyi.future.core.utils.message;

import com.tangmiyi.future.core.enums.ValidCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ErrorMessageUtils {
    private static final String BASE_NAME = "i18n.messages-err";

    private static ConcurrentMap<String, ResourceBundle> resourceBundleMap = new ConcurrentHashMap<>();

    public static String getText(String errorCode) {
        return getText(Locale.SIMPLIFIED_CHINESE, errorCode);
    }

    public static String getText(String locale, String errorCode) {
        return getText(new Locale(locale), errorCode);
    }

    public static String getText(Locale locale, String errorCode) {
        ResourceBundle rb = getBundle(locale);
        if (rb == null){
            return errorCode;
        }

        try {
            return rb.getString(errorCode + "");
        } catch (Exception e) {
            return errorCode;
        }
    }

    public static String getText(String errorCode, Object... params) {
        return getText(Locale.SIMPLIFIED_CHINESE, errorCode, params);
    }

    public static String getText(String locale, String errorCode, Object... params) {
        return getText(new Locale(locale), errorCode, params);
    }

    public static String getText(Locale locale, String errorCode, Object... params) {
        String text = getText(locale, errorCode);
        if (text == null){
            return null;
        }
        return MessageFormat.format(text, params);
    }

    public static ResourceBundle getBundle(Locale locale) {
        if (resourceBundleMap.containsKey(locale.toString())){
            return resourceBundleMap.get(locale.toString());
        }

        ResourceBundle rb = ResourceBundle.getBundle(BASE_NAME, locale, ErrorMessageUtils.class.getClassLoader(),
                new MessageSourceControl());
        if (rb == null){
            return null;
        }
        log.debug("load bundle {}_{}.properties", BASE_NAME, locale.toString());
        resourceBundleMap.put(locale.toString(), rb);
        return rb;
    }

    public static void clearCache() {
        resourceBundleMap.clear();
    }

    public static void clearCache(Locale locale) {
        clearCache(locale.toString());
    }

    public static void clearCache(String locale) {
        resourceBundleMap.remove(locale);
    }

    /**
     * 获取校验信息
     * @param errors
     * @return
     */
    public static Map<String,String> getValidMessage(final String locale, List<ObjectError> errors){
        Map<String,String> errorMessages = new HashMap<>();
        errors.stream().forEach(oe -> {
            if (oe instanceof FieldError) {
                FieldError fe = (FieldError)oe;
                // 获取valid类型code
                String validCode = fe.getCode();
                ValidCodeEnum validCodeEnum = ValidCodeEnum.validCodeValueOf(validCode);
                if(validCodeEnum != null){
                    String msgCode = validCodeEnum.getMsgCode();
                    // 获取valid检验value数组，由于第一个元素是field字符串，需剔除
                    Object[] arg = fe.getArguments();
                    String msg = "";
                    if(arg.length > 1){
                        Object[] targetArg = new Object[arg.length - 1];
                        for(int i=0;i<arg.length - 1;i++){
                            targetArg[i] = arg[i+1];
                        }
                        msg = ErrorMessageUtils.getText(msgCode,targetArg);
                    }else{
                        msg = ErrorMessageUtils.getText(msgCode);
                    }
                    if(StringUtils.isBlank(msg)){
                        errorMessages.put(fe.getField(),fe.getDefaultMessage());
                    }else{
                        errorMessages.put(fe.getField(),msg);
                    }
                }else{
                    errorMessages.put(fe.getField(),fe.getDefaultMessage());
                }
            }
        });
        return errorMessages;
    }

    /**
     * 获取校验信息
     * @param errors
     * @return
     */
    public static Map<String,String> getValidMessage(List<ObjectError> errors){
        return getValidMessage(Locale.SIMPLIFIED_CHINESE.toString(), errors);
    }
}
