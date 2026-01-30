package com.sumver.fdtune;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import android.content.Context;

public class hackvip implements IXposedHookLoadPackage {

    private static final String TAG = "HackVip";

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        // 检查加载的包是否是目标应用 "com.fd.tune"
        if (!lpparam.packageName.equals("com.fd.tune")) {
            return; // 如果不是目标应用，则不执行任何操作
        }

        XposedBridge.log(TAG + ": 找到吉他调音器+");

        try {
            XposedHelpers.findAndHookMethod(
                    "o4.c",
                    lpparam.classLoader,
                    "w",
                    Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(true);
                            XposedBridge.log(TAG + ": VIP 检查被绕过！方法c.w()被强制返回true。");
                        }
                    }
            );

        } catch (NoSuchMethodError e) {
            XposedBridge.log(TAG + ": NoSuchMethodError: " + e.getMessage());
        } catch (XposedHelpers.ClassNotFoundError e) {
            XposedBridge.log(TAG + ": ClassNotFoundError: " + e.getMessage());
        } catch (Exception e) {
            XposedBridge.log(TAG + ": Hook 过程中发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

