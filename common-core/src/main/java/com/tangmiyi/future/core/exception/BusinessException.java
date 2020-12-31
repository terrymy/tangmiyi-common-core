package com.tangmiyi.future.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private String errorCode;

    private String errorMsg;

    public BusinessException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode,String errorMsg) {
        super(errorCode);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
}
