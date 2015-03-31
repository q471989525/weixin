package com.app.webservice.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.app.webservice.HelloWorldService;

@WebService
@SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.RPC)
public class HelloWorldServiceImpl implements HelloWorldService {

	public String sayHi(String text) {
		System.out.println("sayHi called");
		return "Hello " + text + " = ";
	}

}