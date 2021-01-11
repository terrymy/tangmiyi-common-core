package com.tangmiyi.future.core.aspect;

import com.tangmiyi.future.core.annotation.RedissonLock;
import com.tangmiyi.future.core.utils.aliyun.DingMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@Order(2)
public class RedissonLockAspect {

    @Autowired
    private RedissonClient redisson;
	@Autowired
	private DingMsgUtils dingMsgUtils;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) throws Throwable {
        Object obj = null;
        // 方法内的所有参数
        Object[] params = joinPoint.getArgs();
        int lockIndex = redissonLock.lockIndex();
        String key = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint
                .getSignature().getName();
        // -1代表锁整个方法，而非具体锁哪条数据
        if (lockIndex != -1) {
            key += params[lockIndex];
        }
        log.info("redis_lock_key ---> "+key);
        // the maximum time to wait for the lock
        // int leaseTime = redissonLock.leaseTime();
        // 可重入锁
        boolean waiting = redissonLock.waiting();
        RLock rLock = redisson.getLock(key);
        if(waiting){
            boolean result = rLock.tryLock();
            //获得锁
            if(result){
	            try {
		            obj = joinPoint.proceed();
	            } catch (Exception e) {
		            throw e;
	            } finally {
	            	//释放锁
		            rLock.unlock();
	            }
            }else{
                dingMsgUtils.sendDingMsg(key+"未获得分布式锁,请稍后再试");
            }
        }else{
            try {
                rLock.lock();
                obj = joinPoint.proceed();
            } catch (Exception e) {
                dingMsgUtils.sendDingMsg(key+"rLock.lock() exception");
                throw e;
            } finally {
                //释放锁
                rLock.unlock();
            }
        }

        return obj;
    }
}
