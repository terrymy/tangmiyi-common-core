package com.tangmiyi.future.core.aspect;

import com.alibaba.fastjson.JSONObject;
import com.tangmiyi.future.core.annotation.ServiceLogAop;
import com.tangmiyi.future.core.bean.ResultBean;
import com.tangmiyi.future.core.exception.BusinessException;
import com.tangmiyi.future.core.exception.DisableNoticeBusinessException;
import com.tangmiyi.future.core.utils.aliyun.DingMsgUtils;
import com.tangmiyi.future.core.utils.base.ExUtils;
import com.tangmiyi.future.core.utils.message.LocaleMessageSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
@Aspect
@Slf4j
@Order(1)
public class ServiceLogAopAspet {

    @Autowired
    private LocaleMessageSourceUtils localeMessageSourceUtils;

    @Autowired
    private DingMsgUtils dingMsgUtils;

    /**
     * 切面执行
     *
     * @param joinPoint
     * @return
     */
    @Around("@within(serviceLogAop)")
    public Object service(ProceedingJoinPoint joinPoint, ServiceLogAop serviceLogAop) {
        Object result;
        Long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
            resultLog(joinPoint, result, startTime,serviceLogAop);
        }
        catch (DisableNoticeBusinessException e) {
            exceptionLog(joinPoint,e,startTime);
            return setMsg(ResultBean.error(e.getErrorCode(),e.getErrorMsg()));
        }
        catch (BusinessException e) {
            exceptionLog(joinPoint,e,startTime);
            dingMsgUtils.sendDingMsg(ExUtils.getEDetail(e));
            return setMsg(ResultBean.error(e.getErrorCode(),e.getErrorMsg()));
        }
        catch (Throwable e) {
            exceptionLog(joinPoint,e,startTime);
            dingMsgUtils.sendDingMsg(ExUtils.getEDetail(e));
            return ResultBean.uncaughtedError();
        }
        return result;
    }

    /**
     * 异常处理，输出日志
     *
     * @param joinPoint
     * @param e
     */
    private void exceptionLog(ProceedingJoinPoint joinPoint, Throwable e,Long startTime) {
        String prefix = genLogPrefix(joinPoint);
        String arg = genArgLog(joinPoint);
        Long endTime = System.currentTimeMillis();
        // 输出错误日志
        log.error("{} do exception, {}  startTime:{}, endTime:{}, costTime:{} e:", prefix, arg,  startTime, endTime, endTime - startTime, e);
    }

    /**
     * 执行结果日志输出
     *
     * @param joinPoint
     * @param result
     */
    private void resultLog(ProceedingJoinPoint joinPoint, Object result, Long startTime
    ,ServiceLogAop serviceLogAop) {
//        if (!(result instanceof ResultBean)) {
//            /** 符合标准格式返回的，无法区分是否执行成功，当做成功处理 **/
//            successLog(joinPoint, result, startTime);
//            return;
//        }
        ResultBean resultBean = (ResultBean) result;
        resultBean = setMsg(resultBean);
        if (!resultBean.hasError()) {
            successLog(joinPoint, result, startTime,serviceLogAop);
        } else {
            errorLog(joinPoint, result, startTime);
        }
    }

    /**
     * 设置resultBean返回的msg
     */
    private ResultBean setMsg(ResultBean resultBean){
        if(StringUtils.isNotBlank(resultBean.getCode())){
            if (StringUtils.isBlank(resultBean.getMsg())) {
                resultBean.setMsg(localeMessageSourceUtils.getMessage(resultBean.getCode()));
            } else {
                // 占位符替换
                resultBean.setMsg(localeMessageSourceUtils.getMessage(resultBean.getCode(), new Object[]{(resultBean.getMsg())}));
            }
        }
        return resultBean;
    }

    /**
     * 执行成功日志
     *
     * @param joinPoint
     * @param result
     */
    private void successLog(ProceedingJoinPoint joinPoint, Object result, Long startTime
    ,ServiceLogAop serviceLogAop) {
        // 先判断全局是否打印日志
        if (!serviceLogAop.logInfoOutput()) {
            return;
        }

        // 再判断方法是否打印日志
        if (!logInfoOutput(joinPoint)) {
            return;
        }

        String prefix = genLogPrefix(joinPoint);
        String arg = genArgLog(joinPoint);
        Long endTime = System.currentTimeMillis();
        log.info("{} {}  result: {}  startTime:{}, endTime:{}, costTime:{}",
                prefix, arg, JSONObject.toJSONString(result), startTime, endTime, endTime - startTime);
    }

    /**
     * 日志是否打开
     *
     * @param joinPoint
     * @return
     */
    private Boolean logInfoOutput(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLogAop annotation = method.getAnnotation(ServiceLogAop.class);
        if(annotation == null){
            return true;
        }
        return annotation.logInfoOutput();
    }

    /**
     * 执行失败日志
     *
     * @param joinPoint
     * @param result
     */
    private void errorLog(ProceedingJoinPoint joinPoint, Object result, Long startTime) {
        String prefix = genLogPrefix(joinPoint);
        String arg = genArgLog(joinPoint);
        Long endTime = System.currentTimeMillis();
        log.error("{} do fail, {}, result:{}  startTime:{}, endTime:{}, costTime:{}",
                prefix, arg, JSONObject.toJSONString(result), startTime, endTime, endTime - startTime);
    }

    /**
     * 获取log前缀：类名+方法名
     *
     * @param joinPoint
     * @return
     */
    private String genLogPrefix(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        return String.format("%s#%s", className, methodName);
    }

    /**
     * 获取入参的log信息
     *
     * @param joinPoint
     * @return
     */
    private String genArgLog(ProceedingJoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        StringBuilder arg = new StringBuilder();
        if (Objects.nonNull(arguments)) {
            for (int i = 0; i < arguments.length; i++) {
                arg.append(String.format("param[%d]: %s", i, JSONObject.toJSONString(arguments[i])));
            }
        }
        return arg.toString();
    }
}
