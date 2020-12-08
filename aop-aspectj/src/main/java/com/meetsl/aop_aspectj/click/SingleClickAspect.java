package com.meetsl.aop_aspectj.click;

import android.util.Log;

import com.meetsl.commonlib.utils.ClickUtil;
import com.meetsl.commonlib.utils.ToastUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by ShiLong on 2020/12/8.
 * <p>
 * desc: ""
 */
@Aspect
public class SingleClickAspect {
    @Pointcut("execution(@com.meetsl.aop_aspectj.click.SingleClick * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (ClickUtil.isFastDoubleClick()) {
            ToastUtils.showShortToast("fast click");
            return;
        }
        joinPoint.proceed();//执行原方法
    }
}
