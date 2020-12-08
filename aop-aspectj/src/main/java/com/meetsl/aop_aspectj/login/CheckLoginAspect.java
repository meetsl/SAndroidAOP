package com.meetsl.aop_aspectj.login;

import com.meetsl.commonlib.utils.ToastUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by ShiLong on 2020/12/7.
 * <p>
 * desc: ""
 */
@Aspect
public class CheckLoginAspect {
    @Pointcut("execution(@com.meetsl.aop_aspectj.login.CheckLogin * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Before("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(JoinPoint joinPoint) throws Throwable {
        ToastUtils.showShortToast("登录校验");
    }
}
