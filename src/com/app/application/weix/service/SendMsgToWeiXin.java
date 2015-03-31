package com.app.application.weix.service;

import java.util.Date;

/**
 * 被动回复
 *
 * @author fuq
 */
public class SendMsgToWeiXin {

    final static String str = "<xml>\n"
            + "<ToUserName><![CDATA[#user]]></ToUserName>\n"
            + "<FromUserName><![CDATA[#from]]></FromUserName>\n"
            + "<CreateTime>#time</CreateTime>\n"
            + "<MsgType><![CDATA[text]]></MsgType>\n"
            + "<Content><![CDATA[#cont]]></Content>\n"
            + "</xml>";
    public static String sendText(String tuU, String from, String cont) {
        String temp = str;
        temp = temp.replaceAll("#user", tuU);
        temp = temp.replaceAll("#from", from);
        temp = temp.replaceAll("#time", "" + new Date().getTime());
        temp = temp.replaceAll("#cont", cont);
        return temp;
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }
}
