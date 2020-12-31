package com.tangmiyi.future.core.enums;
/**
 * 参数实体校验枚举值
 * */
public enum ValidCodeEnum {

    /**
     * 限制只能为null
     */
    NULL("Null","000200"),

    /**
     * 限制必须不为null
     */
    NOTNULL("NotNull","000201"),

    /**
     * 限制必须为false
     */
    ASSERTFALSE("AssertFalse","000202"),

    /**
     * 限制必须为true
     */
    ASSERTTRUE("AssertTrue","000203"),

    /**
     * @DecimalMax(value) 限制必须为一个不大于指定值的数字
     */
    DECIMALMAX("DecimalMax","000204"),

    /**
     * @DecimalMin(value) 限制必须为一个不小于指定值的数字
     */
    DECIMALMIN("DecimalMin","000205"),

    /**
     * @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
     */
    DIGITS("Digits","000206"),

    /**
     * 限制必须是一个将来的日期
     */
    FUTURE("Future","000207"),

    /**
     * @Max(value) 限制必须为一个不大于指定值的数字
     */
    MAX("Max","000208"),

    /**
     * @Min(value) 限制必须为一个不小于指定值的数字
     */
    MIN("Min","000209"),

    /**
     * 限制必须是一个过去的日期
     */
    PAST("Past","000210"),

    /**
     * @Pattern(value) 限制必须符合指定的正则表达式
     */
    PATTERN("Pattern","000211"),

    /**
     * @Size(max,min)  限制字符长度必须在min到max之间
     */
    SIZE("Size","000212"),

    /**
     * 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0
     */
    NOTEMPTY("NotEmpty","000213"),

    /**
     *  验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
     */
    NOTBLANK("NotBlank","000214"),

    /**
     * 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式
     */
    EMAIL("Email","000215");

    private String validCode;

    private String msgCode;

    private ValidCodeEnum(String validCode, String msgCode) {
        this.validCode = validCode;
        this.msgCode = msgCode;
    }
    public String getValidCode() {
        return validCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    /**
     * 根据validCode返回ValidCodeEnum
     * @param validCode
     * @return
     */
    public static ValidCodeEnum validCodeValueOf(String validCode) {
        ValidCodeEnum[] validCodeEnums = ValidCodeEnum.values();
        for(ValidCodeEnum validCodeEnum : validCodeEnums){
            if(validCodeEnum.getValidCode().equals(validCode)){
                return validCodeEnum;
            }
        }
        return null;
    }
}