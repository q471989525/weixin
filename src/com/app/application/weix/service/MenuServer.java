package com.app.application.weix.service;

import com.app.application.weix.config.PropertiesRead;
import com.app.application.weix.utils.HttpGet;
import com.app.application.weix.utils.VALUE;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 创建自定义菜单
 *
 * @author fuq
 */
public class MenuServer {

    private String readMenuFile() {
        InputStream in = PropertiesRead.class.getResourceAsStream("menu.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 设置菜单，菜单文件来源于menu.json中
     */
    public void setMenu() {
        String url = VALUE.MENU_RUL;
        url = url.replaceAll("@access_token", VALUE.ACCESS_TOKEN);
        String menu = readMenuFile();
        String post = HttpGet.post(url, menu);
        System.out.println(post);
    }

    public static void main(String[] args) throws InterruptedException {
        new VALUE().init();
        Thread.sleep(6000);
        new MenuServer().setMenu();
    }
}
