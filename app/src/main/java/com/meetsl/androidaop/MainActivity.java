package com.meetsl.androidaop;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.meetsl.aop_aspectj.click.SingleClick;
import com.meetsl.aop_aspectj.login.CheckLogin;
import com.meetsl.aop_aspectj.permission.CheckPermission;

import java.lang.reflect.Proxy;

/**
 * Created by ShiLong on 2020/12/7.
 * <p>
 * desc: ""
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySampleProxied mySampleProxied = new MySampleProxiedImpl();
        MySampleProxied newProxyInstance = (MySampleProxied) Proxy.newProxyInstance(
                MySampleProxied.class.getClassLoader(),
                new Class[]{MySampleProxied.class},
                new StopWatchInvocationHandler(mySampleProxied));
        newProxyInstance.printSomethingCool();
    }

    @CheckPermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void login(View view) {
        System.out.println("main login");
    }
}
