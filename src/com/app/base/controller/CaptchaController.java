package com.app.base.controller;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 验证码生成类
 * @author ZF
 * @Dec 5, 2010
 */
@Controller
@Scope("prototype")
public class CaptchaController {
	
	@RequestMapping("/j_captcha.do")
    public ModelAndView createImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        String capText = captchaProducer.createText();  
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < capText.length(); i++) {
        	sb.append("  ");
			sb.append(capText.charAt(i));
		}
        BufferedImage bi = captchaProducer.createImage(sb.toString());  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }
	
	
	private Producer captchaProducer = null;  
	  
    @Resource(name="captchaProducer")
    public void setCaptchaProducer(Producer captchaProducer) {  
        this.captchaProducer = captchaProducer;  
    }  
}
