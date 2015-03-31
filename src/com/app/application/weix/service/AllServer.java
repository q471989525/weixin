package com.app.application.weix.service;

import com.app.application.weix.entity.WXinUser;
import com.app.application.weix.utils.HttpGet;
import com.app.application.weix.utils.VALUE;
import org.json.JSONObject;

/**
 * 其他接口调用
 *
 * @author fuq
 */
public class AllServer {

    /**
     * 根据OpenId 获取用户的信息
     *
     * @param openid opneId
     * @return WXinUser
     */
    public WXinUser getUserInfo(String openid) {
        WXinUser wuser;
        String url = VALUE.USERINFO_URL;
        url = url.replaceAll("@access_token", VALUE.ACCESS_TOKEN).replaceAll("@openid", openid);
        String post = HttpGet.post(url);
        System.out.println(post);
        //<editor-fold defaultstate="collapsed" desc="demo">
        /*
         {
         "subscribe": 1,
         "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
         "nickname": "Band",
         "sex": 1,
         "language": "zh_CN",
         "city": "广州",
         "province": "广东",
         "country": "中国",
         "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
         "subscribe_time": 1382694957,
         "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
         }
         */
//</editor-fold>
        try {
            JSONObject jsonObject = new JSONObject(post);
            wuser = new WXinUser();
            wuser.setSubscribe(jsonObject.get("subscribe").toString());
            wuser.setOpenid(jsonObject.get("openid").toString());
            wuser.setNickname(jsonObject.get("nickname").toString());
            wuser.setSex(jsonObject.get("sex").toString());
            wuser.setLanguage(jsonObject.get("language").toString());
            wuser.setCity(jsonObject.get("city").toString());
            wuser.setProvince(jsonObject.get("province").toString());
            wuser.setCountry(jsonObject.get("country").toString());
            wuser.setHeadimgurl(jsonObject.get("headimgurl").toString());
            wuser.setSubscribe_time(jsonObject.get("subscribe_time").toString());
            //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
           // wuser.setUnionid(jsonObject.get("unionid").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return wuser;
    }

    public String getOpenId(String code) {
        String value = null;
        String url = VALUE.WEB_TOKEN_URL;
        url = url.replaceAll("@appid", VALUE.APP_ID).replaceAll("@secret", VALUE.SECRET).replaceAll("@code", code);
        String post = HttpGet.post(url);
        System.out.println(post);
        //<editor-fold defaultstate="collapsed" desc="demo">
        /*
         {
         "access_token":"ACCESS_TOKEN",
         "expires_in":7200,
         "refresh_token":"REFRESH_TOKEN",
         "openid":"OPENID",
         "scope":"SCOPE",
         "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
         }
         */
//</editor-fold>
        try {
            JSONObject jsonObject = new JSONObject(post);
            value = jsonObject.getString("openid");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        new VALUE().init();
        Thread.sleep(6000);
        new AllServer().getUserInfo("0119822bef9ec40cdb6171f97350d92t");

    }
}
