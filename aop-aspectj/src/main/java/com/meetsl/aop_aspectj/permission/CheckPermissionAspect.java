package com.meetsl.aop_aspectj.permission;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * Created by ShiLong on 2020/12/8.
 * <p>
 * desc: ""
 */
@Aspect
public class CheckPermissionAspect {
    /**
     * 以 @annotation(checkPermission) 这种方式申明 Advice 要用到的注解参数
     */
    @Pointcut("execution(@com.meetsl.aop_aspectj.permission.CheckPermission * *(..)) && @annotation(checkPermission)")
    public void annotationMethod(CheckPermission checkPermission) {
    }

    @Before("annotationMethod(checkPermission)")
    public void beforePoint(JoinPoint joinPoint, CheckPermission checkPermission) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Log.e("aspectj", "short string" + joinPoint.toShortString());
        Log.e("aspectj", "long string" + joinPoint.toLongString());
        Log.e("aspectj", "args" + Arrays.toString(args));
        String[] permissions = checkPermission.permissions();
        Log.e("aspectj", Arrays.toString(permissions));
        return;
    }
}
