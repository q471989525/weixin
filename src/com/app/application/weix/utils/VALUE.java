/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.application.weix.utils;

import com.app.application.weix.config.PropertiesRead;
import com.app.application.weix.service.AccessTokenServer;

/**
 *
 * @author fuq
 */
public class VALUE {

    private PropertiesRead conf;
    public static boolean isRun=false;
    public static String APP_ID;
    public static String SECRET;
    public static String ACCESS_TOKEN;
    //获取token Url
    public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=@appid&secret=@secret";
    // 设置菜单Url
    public static final String MENU_RUL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=@access_token";
    //用户授权 url
    public static final String AURHOEIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=@appid&redirect_uri=@redirect_uri&response_type=code&scope=snsapi_base&state=@state#wechat_redirect";
    //根据code 获取webToken和openid
    public static final String WEB_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=@appid&secret=@secret&code=@code&grant_type=authorization_code";
    //根据Openid获取用户信息
    public static final String USERINFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=@access_token&openid=@openid&lang=zh_CN";
    public VALUE() {
        System.out.println("-------------------------------------------------------"
                + "\n====================初始化微信参数...====================="
                + "\n--------------------------------------------------------");
        try {
            conf = conf == null ? new PropertiesRead() : conf;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        APP_ID = conf.getMessageByCode("appID");
        SECRET = conf.getMessageByCode("appsecret");
    }

    /**
     * 初始化微信必要的参数
     */
    public void init() {
        new AccessTokenServer().getToken();
    }
}
