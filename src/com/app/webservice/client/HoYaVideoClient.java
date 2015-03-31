package com.app.webservice.client;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.datacontract.schemas._2004._07.hoyavideolib.ImageHistoryEntity;
import org.tempuri.IHoYaVideoWCFService;

import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;

public class HoYaVideoClient {

	public static void main(String[] args) throws Exception{
        JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
        svr.setServiceClass(IHoYaVideoWCFService.class);
        svr.setAddress("http://192.168.50.24:7000/HoYaVideoWCFService/?wsdl");
//        HoYaVideoWCFService hw = (HoYaVideoWCFService) svr.create();
        IHoYaVideoWCFService haHoYaVideoWCFService = (IHoYaVideoWCFService) svr.create();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d1 = "2014-12-23 13:02:22";
        String d2 = "2014-12-23 18:02:22";
        
        GregorianCalendar gcal =new GregorianCalendar();
        GregorianCalendar gcal2 =new GregorianCalendar();
        gcal.setTime(format.parse(d1));
        gcal2.setTime(format.parse(d2));
        XMLGregorianCalendar xgcal= DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        XMLGregorianCalendar xgcal2= DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal2);
        System.out.println("xgcal="+xgcal.toString());
        System.out.println("xgcal2="+xgcal2.toString());
        
        ImageHistoryEntity im = haHoYaVideoWCFService.searchImage(238, xgcal, xgcal2);
        JAXBElement<ArrayOfstring> imageList = im.getImageList();
   
        System.out.println("im="+im.getImageList().getValue().getString());
        System.out.println("123w21="+imageList.toString());
        System.out.println("123w21="+imageList.getValue());
        System.out.println("123w21="+imageList.getValue().getString());
        System.out.println("123w21="+imageList.getValue().toString());
	}

}