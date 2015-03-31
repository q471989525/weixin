/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.application.weix.controller;

import com.app.application.weix.entity.Product;
import com.app.application.weix.service.ProductServer;
import com.app.core.controller.BaseController;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author fuq
 */
@Controller
@Scope("prototype")
@RequestMapping("/pd")
public class ProductController extends BaseController {

    @Resource(name = "productServer")
    ProductServer productServer;

    @RequestMapping("/toList.do")
    public String toProductList(HttpServletRequest request, HttpServletResponse response) {
        List<Product> findAll = productServer.findAll();
        request.setAttribute("list", findAll);
        return "weixin/index_1";
    }
}
