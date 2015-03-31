/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.application.weix.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

/**
 *
 * @author fuq
 */
public class HttpGet {

    private static final Logger logger = Logger.getLogger(HttpGet.class);

    /**
     * 功能： 以post方式发送请求
     *
     * @param url
     * @param parm
     * @return String 返回的消息
     *
     */
    public static String post(String url, Map parm) {
        StringBuilder sb = new StringBuilder();

        // 核心应用类
        HttpClient httpClient = new DefaultHttpClient();

        // HTTP请求
        try {

            List formParams = new ArrayList();
            if (parm != null && !parm.isEmpty()) {
                Iterator iterator = parm.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next().toString();
                    formParams.add(new BasicNameValuePair(key, parm.get(key).toString()));
                }

            }

            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            // 发送请求，返回响应
            HttpResponse response = httpClient.execute(request);

            // 打印响应信息
            //System.out.println(response.getStatusLine());
            InputStream content = response.getEntity().getContent();
            BufferedReader br = new LineNumberReader(new InputStreamReader(content, "utf-8"));

            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            //System.out.println(sb.toString());
        } catch (ClientProtocolException e) {
            // 协议错误
            logger.error("协议错误", e);
        } catch (IOException e) {
            // 网络异常
            logger.error("网络异常", e);
        }
        return sb.toString();
    }

    public static String post(String url) {
       return post(url,new HashMap());
    }
      /**
     * 功能： 以post方式发送请求－附带字符串实体数据流
     *
     * @param url
     * @param strEntity 字符串流
     * @return String 返回的消息
     *
     */
    public static String post(String url,String strEntity) {
        StringBuilder sb = new StringBuilder();

        // 核心应用类
        HttpClient httpClient = new DefaultHttpClient();

        // HTTP请求
        try {

            StringEntity params =new StringEntity(strEntity,"UTF-8");
//            HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            HttpPost request = new HttpPost(url);
            request.setEntity(params);
            // 发送请求，返回响应
            HttpResponse response = httpClient.execute(request);

            // 打印响应信息
            //System.out.println(response.getStatusLine());
            InputStream content = response.getEntity().getContent();
            BufferedReader br = new LineNumberReader(new InputStreamReader(content, "utf-8"));

            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            //System.out.println(sb.toString());
        } catch (ClientProtocolException e) {
            // 协议错误
            logger.error("协议错误", e);
        } catch (IOException e) {
            // 网络异常
            logger.error("网络异常", e);
        }
        return sb.toString();
    }

    
    
    public static void main(String[] args) {
        Map pram = new HashMap<String, String>();
        pram.put("para", "你大爷");
        System.out.println(HttpGet.post("http://www.xiaohuangji.com/ajax.php", pram));
    }
}
