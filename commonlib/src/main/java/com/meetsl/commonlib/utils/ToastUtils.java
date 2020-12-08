package com.meetsl.commonlib.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.meetsl.commonlib.CommonApp;

import java.lang.reflect.Field;


public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore;

    private static long mBeforeTime;

    private static int mCurrentFragmentId;

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showToast(final CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param stringRes 文本
     */
    public static void showToast(int stringRes) {
        showToast(CommonApp.appContext.getString(stringRes), Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToast(
            @StringRes
                    int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToast(
            @StringRes
                    int resId, Object... args) {
        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToast(
            @StringRes
                    int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToast(
            @StringRes
                    int resId, Object... args) {
        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(
            @StringRes
                    int resId, int duration) {
        showToast(CommonApp.appContext.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(
            @StringRes
                    int resId, int duration, Object... args) {
        showToast(String.format(CommonApp.appContext.getResources().getString(resId), args),
                duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(String format, int duration, Object... args) {
        showToast(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast toast = Toast.makeText(CommonApp.appContext, text, duration);
        // 7.0 在耗时操作中吐司会报错  #Toast$TN$2.handleMessage
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1 &&
                Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setHook(toast);
        }
        toast.show();
    }


    public static void showToastJustOne(CharSequence text, int duration, long currentTime, int fragmentid) {
        if (currentTime == mBeforeTime && Math.abs(currentTime - mBeforeTime) < 1000) {
            return;
        }
        mBeforeTime = currentTime;
        if (mCurrentFragmentId == fragmentid) {
            return;
        }
        mCurrentFragmentId = fragmentid;
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast toast = Toast.makeText(CommonApp.appContext, text, duration);
        // 7.0 在耗时操作中吐司会报错  #Toast$TN$2.handleMessage
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1 &&
                Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setHook(toast);
        }
        toast.show();
    }


    private static Field sField_TN;
    private static Field sField_TN_Handler;

    static {
        //安卓7.0的做处理，其它版本系统的不用处理
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1 && Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            try {
                sField_TN = Toast.class.getDeclaredField("mTN");
                sField_TN.setAccessible(true);
                sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
                sField_TN_Handler.setAccessible(true);
            } catch (Exception e) {
            }
        }
    }

    private static void setHook(Toast toast) {
        //安卓7.0的做处理，其它版本系统的不用处理
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1 && Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            hook(toast);
        }
    }

    /*****
     * 7.0 吐司异常  #Toast$TN$2.handleMessage
     * @param toast
     */
    private static void hook(Toast toast) {
        try {
            Object tn = sField_TN.get(toast);
            Handler preHandler = (Handler) sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn, new SafelyHandlerWarpper(preHandler));
        } catch (Exception e) {
        }
    }

    /****
     * 自定义Handler catch处理异常 #Toast$TN$2.handleMessage
     */
    public static class SafelyHandlerWarpper extends Handler {
        private Handler impl;

        public SafelyHandlerWarpper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);//需要委托给原Handler执行
        }
    }
}
