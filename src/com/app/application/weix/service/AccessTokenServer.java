package com.app.application.weix.service;

import com.app.application.weix.utils.HttpGet;
import com.app.application.weix.utils.VALUE;
import org.json.JSONObject;

/**
 *
 * @author fuq
 */
public class AccessTokenServer {


    
    /**
     * 只可调用一次.
     * 主动获取Token，有效期7200秒，7200秒内自动重新获取
     */
    public void getToken() {
        if(VALUE.isRun){
            return;
        }
        VALUE.isRun=true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    String url = VALUE.TOKEN_URL;
                    System.out.println(VALUE.APP_ID);
                    url = url.replaceAll("@appid", VALUE.APP_ID);
                    url = url.replaceAll("@secret", VALUE.SECRET);
                    String post = HttpGet.post(url);
//                    System.out.println(post);
                    JSONObject json=new JSONObject(post);
                    String access_token = json.getString("access_token");
                    VALUE.ACCESS_TOKEN=access_token;
                   System.out.println(access_token);
                    try {
                        Thread.sleep(1000 * 7140);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }).start();
    }
    
    
    

    public static void main(String[] args) {

        // String post = HttpGet.post(MENU_RUL, MENU);
        //System.out.println(post);
    }
}
