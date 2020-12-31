package com.tangmiyi.future.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常不执行钉钉通知
 */
@Getter
@Setter
public class DisableNoticeBusinessException extends BusinessException {

    private String errorCode;

    private String errorMsg;

    public DisableNoticeBusinessException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public DisableNoticeBusinessException(String errorCode,String errorMsg) {
        super(errorCode);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }
}
