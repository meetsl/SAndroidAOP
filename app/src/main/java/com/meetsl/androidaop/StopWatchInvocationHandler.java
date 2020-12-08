package com.meetsl.androidaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class StopWatchInvocationHandler implements InvocationHandler {

    private final Object objectProxied;

    public StopWatchInvocationHandler(Object objectProxied) {
        this.objectProxied = objectProxied;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(objectProxied, args);
        long endTime = System.nanoTime();

        StringBuilder message = new StringBuilder();
        message.append(method.getName());
        message.append(" took ");
        message.append(TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
        message.append(" millis");

        System.out.println(message.toString());

        return result;
    }
}