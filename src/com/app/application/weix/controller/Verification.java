/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.application.weix.controller;

import com.app.application.weix.entity.WXinRes;
import com.app.application.weix.entity.WXinUser;
import com.app.application.weix.service.AllServer;
import com.app.application.weix.service.GetWXinMsg;
import com.app.application.weix.service.SendMsgToWeiXin;
import com.app.application.weix.utils.HttpGet;
import com.app.application.weix.utils.VALUE;
import com.app.core.controller.BaseController;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.dom4j.DocumentException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * main
 *
 * @author fuq
 */
@Controller
@Scope("prototype")
@RequestMapping("/")
public class Verification extends BaseController {

    private AllServer aserver = new AllServer();

    @RequestMapping("/verif.do")
    public void verif(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        ServletInputStream in = request.getInputStream();

        byte b[] = new byte[1024];
        int len = 0;
        int temp = 0;          //所有读取的内容都使用temp接收  
        while ((temp = in.read()) != -1) {    //当没有读取完时，继续读取  
            b[len] = (byte) temp;
            len++;
        }
        String reqContent = new String(b, "utf-8");
        if (reqContent != null) {
            reqContent = reqContent.trim();
        }
        WXinRes psMsg = GetWXinMsg.psMsg(reqContent);
        System.out.println(psMsg.getFromUserName() + " say: " + psMsg.getContent());
        SendMsgToWeiXin.sendText(psMsg.getFromUserName(), psMsg.getToUserName(), psMsg.getFromUserName() + " say: " + psMsg.getContent());
        PrintWriter writer = response.getWriter();

        writer.write(request.getParameter("echostr"));
        writer.close();
    }

    /**
     * web页面授权 微信授权成功后跳转到此处保存用户信息
     *
     * @param request
     * @param response
     */
    @RequestMapping("/authorize.do")
    public void webAuthorize(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        WXinUser wUser = (WXinUser) session.getAttribute("wxin_user");
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String toString = parameterNames.nextElement().toString();
            System.out.println(toString + ":" + request.getParameter(toString));
        }
        String to = request.getParameter("to");
        String code = request.getParameter("code");
        if (code != null) {
            String openId = aserver.getOpenId(code);
            wUser = aserver.getUserInfo(openId);
            session.setAttribute("wxin_user", wUser);

        } //获取session中的用户,不存在则跳转到授权页面
        else if (wUser == null || wUser.isEmpty()) {
            System.out.println("未授权，跳转到授权页面");
            //去授权 
            String url = VALUE.AURHOEIZE_URL;
            String redirect_uri = URLEncoder.encode("http://58.248.64.174/wxin/authorize.do?to=" + to, "UTF-8");
            url = url.replaceAll("@appid", VALUE.APP_ID).replaceAll("@redirect_uri", redirect_uri).replaceAll("@state", "other");
            System.out.println(url);
            response.sendRedirect(url);
            return;
        }
        //存在用户信息则根据参数判断跳转到哪个页面
        if (null == to) {
            writer.write("参数错误!");
        } else {
            //跳转页面判断
            if (to.equals("orderlist")) {
                response.sendRedirect("wx/myOrder.do");
                //request.getRequestDispatcher("/myOrder.do").forward(request, response);
            } else if (to.equals("neworder")) {
                response.sendRedirect("wx/order.do");
            } else if (to.equals("userinfo")) {
                response.sendRedirect("user/user.do");
            }else if (to.equals("lsit")) {
                response.sendRedirect("pd/toList.do");
            }
        }

        // writer.write(wUser==null?"-":wUser.toString());
        writer.close();

    }

    public static void main(String[] args) {
        HttpGet.post("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET", new HashMap());
    }
}
