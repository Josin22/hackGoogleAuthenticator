package com.josin.hackgoogleauthenticator;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;

import com.josin.hackgoogleauthenticator.Utils.Constans;
import com.josin.hackgoogleauthenticator.Utils.LogUtils;
import com.josin.hackgoogleauthenticator.Xposed.HookGoogleAuthenticator;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    private static boolean is_hooked_google_authenticator = false;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (loadPackageParam.appInfo == null
                || (loadPackageParam.appInfo.flags
                & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP))
                == 1) {
            return;
        }
        if (loadPackageParam.packageName.equals(Constans.google_authenticator_package_name)){
            XposedHelpers.findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                   final Context context = (Context) param.args[0];
                    if (loadPackageParam.processName.equals(Constans.google_authenticator_package_name)&&!is_hooked_google_authenticator){
                        is_hooked_google_authenticator = true;
                        LogUtils.d("google_authenticator_hooked");
                       try{
                           new HookGoogleAuthenticator().hook(loadPackageParam,context);
                       } catch (Exception e){
                           LogUtils.e("google_authenticator_hooked_error:"+e.getMessage());
                       }
                    }
                }
            });
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

    }
}
