

package com.app.application.weix.controller;

import com.app.application.weix.entity.ProductOrder;
import com.app.application.weix.entity.WXinUser;
import com.app.application.weix.service.OrderService;
import com.app.core.controller.BaseController;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *我的订单
 * @author fuq
 */
@Controller
@Scope("prototype")
@RequestMapping("/wx")
public class MyOrderController extends BaseController{
    @Autowired
    OrderService oServer;
     @RequestMapping("/myOrder.do")
   public String myOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
      // response.setCharacterEncoding("utf-8");
//       response.setHeader("content-type","text/html;charset=UTF-8");
//       PrintWriter writer = response.getWriter();
       WXinUser wUser = (WXinUser) request.getSession().getAttribute("wxin_user");
//       writer.write("欢迎您："+wUser.getNickname());
//       writer.close();
        List<ProductOrder> queryByUser = oServer.queryByUser(wUser.getOpenid());
        request.setAttribute("list", queryByUser);
       return "weixin/order_list";
   }
   @RequestMapping("/myOrder1.do")
   public String myOrder1(HttpServletRequest request,HttpServletResponse response) throws IOException{
       response.setCharacterEncoding("utf-8");
       response.setHeader("content-type","text/html;charset=UTF-8");
      
       return "weixin/order_list";
   }
  
}
