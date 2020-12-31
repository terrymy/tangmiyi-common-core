package com.tangmiyi.future.core.exception;

import brave.Tracer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.core.consts.CommonReturnConstants;
import com.tangmiyi.future.core.utils.base.ExUtils;
import com.tangmiyi.future.core.utils.message.ErrorMessageUtils;
import com.tangmiyi.future.core.utils.message.LocaleMessageSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private LocaleMessageSourceUtils messageSourceUtil;

    @Autowired
    private Tracer tracer;

    /**
     * 捕获不合法的的json格式
     *
     * @return
     */
    @ExceptionHandler(value = JSONException.class)
    @ResponseBody
    public ResponseEntity jsonExceptionHandler(JSONException e) {
        log.error("invalid json exception:{}", e);
        ResultBean resultBean = ResultBean.uncaughtedError();
        resultBean.setCode(CommonReturnConstants.INVALID_JSON);
        resultBean.setMsg(messageSourceUtil.getMessage(resultBean.getCode()));
        tracer.currentSpan().tag("invalid.json.error", ExUtils.getEDetail(e));
        return new ResponseEntity(resultBean, HttpStatus.OK);
    }

    /**
     * 参数校验绑定异常
     *
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseEntity bindExceptionHandler(BindException e) {
        log.error("bind exception:{}", e);
        ResultBean<Map<String,String>> resultBean = ResultBean.uncaughtedError();
        resultBean.setCode(CommonReturnConstants.REQUEST_DATA_INVALID);
        resultBean.setMsg(messageSourceUtil.getMessage(resultBean.getCode()));
        List<ObjectError> errors = e.getAllErrors();
        Map<String, String> errorMessages = ErrorMessageUtils.getValidMessage(errors);
        resultBean.setData(errorMessages);
        tracer.currentSpan().tag("bind.exception.error", JSON.toJSONString(resultBean));
        return new ResponseEntity(resultBean, HttpStatus.OK);
    }

    /**
     * 参数校验绑定异常
     *
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("bind exception:{}", e);
        ResultBean<Map<String,String>> resultBean = ResultBean.uncaughtedError();
        resultBean.setCode(CommonReturnConstants.REQUEST_DATA_INVALID);
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        Map<String, String> errorMessages = ErrorMessageUtils.getValidMessage(errors);
        resultBean.setMsg(messageSourceUtil.getMessage(resultBean.getCode()));
        resultBean.setData(errorMessages);
        tracer.currentSpan().tag("bind.exception.error", JSON.toJSONString(resultBean));
        return new ResponseEntity(resultBean, HttpStatus.OK);
    }
}
