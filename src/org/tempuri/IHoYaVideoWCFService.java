package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2014-12-22T10:14:54.312+08:00
 * Generated source version: 3.0.3
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "IHoYaVideoWCFService")
@XmlSeeAlso({com.microsoft.schemas._2003._10.serialization.ObjectFactory.class, ObjectFactory.class, com.microsoft.schemas._2003._10.serialization.arrays.ObjectFactory.class, org.datacontract.schemas._2004._07.hoyavideolib.ObjectFactory.class})
public interface IHoYaVideoWCFService {

    @WebMethod(operationName = "GetDevicesByRoad", action = "http://tempuri.org/IHoYaVideoWCFService/GetDevicesByRoad")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/GetDevicesByRoad", output = "http://tempuri.org/IHoYaVideoWCFService/GetDevicesByRoadResponse")
    @RequestWrapper(localName = "GetDevicesByRoad", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDevicesByRoad")
    @ResponseWrapper(localName = "GetDevicesByRoadResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDevicesByRoadResponse")
    @WebResult(name = "GetDevicesByRoadResult", targetNamespace = "http://tempuri.org/")
    public org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfDeviceEntity9S24Wb1P getDevicesByRoad(
        @WebParam(name = "roadId", targetNamespace = "http://tempuri.org/")
        java.lang.Integer roadId
    );

    @WebMethod(operationName = "GetDevices", action = "http://tempuri.org/IHoYaVideoWCFService/GetDevices")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/GetDevices", output = "http://tempuri.org/IHoYaVideoWCFService/GetDevicesResponse")
    @RequestWrapper(localName = "GetDevices", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDevices")
    @ResponseWrapper(localName = "GetDevicesResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDevicesResponse")
    @WebResult(name = "GetDevicesResult", targetNamespace = "http://tempuri.org/")
    public org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfDeviceEntity9S24Wb1P getDevices();

    @WebMethod(operationName = "GetRoad", action = "http://tempuri.org/IHoYaVideoWCFService/GetRoad")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/GetRoad", output = "http://tempuri.org/IHoYaVideoWCFService/GetRoadResponse")
    @RequestWrapper(localName = "GetRoad", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetRoad")
    @ResponseWrapper(localName = "GetRoadResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetRoadResponse")
    @WebResult(name = "GetRoadResult", targetNamespace = "http://tempuri.org/")
    public org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfRoadEntity9S24Wb1P getRoad(
        @WebParam(name = "RoadNo", targetNamespace = "http://tempuri.org/")
        java.lang.Integer roadNo
    );

    @WebMethod(operationName = "PrevImage", action = "http://tempuri.org/IHoYaVideoWCFService/PrevImage")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/PrevImage", output = "http://tempuri.org/IHoYaVideoWCFService/PrevImageResponse")
    @RequestWrapper(localName = "PrevImage", targetNamespace = "http://tempuri.org/", className = "org.tempuri.PrevImage")
    @ResponseWrapper(localName = "PrevImageResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.PrevImageResponse")
    @WebResult(name = "PrevImageResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String prevImage(
        @WebParam(name = "deviceId", targetNamespace = "http://tempuri.org/")
        java.lang.Integer deviceId,
        @WebParam(name = "imageName", targetNamespace = "http://tempuri.org/")
        java.lang.String imageName
    );

    @WebMethod(operationName = "GetDevice", action = "http://tempuri.org/IHoYaVideoWCFService/GetDevice")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/GetDevice", output = "http://tempuri.org/IHoYaVideoWCFService/GetDeviceResponse")
    @RequestWrapper(localName = "GetDevice", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDevice")
    @ResponseWrapper(localName = "GetDeviceResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetDeviceResponse")
    @WebResult(name = "GetDeviceResult", targetNamespace = "http://tempuri.org/")
    public org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfDeviceEntity9S24Wb1P getDevice(
        @WebParam(name = "deviceId", targetNamespace = "http://tempuri.org/")
        java.lang.Integer deviceId
    );

    @WebMethod(operationName = "GetRoads", action = "http://tempuri.org/IHoYaVideoWCFService/GetRoads")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/GetRoads", output = "http://tempuri.org/IHoYaVideoWCFService/GetRoadsResponse")
    @RequestWrapper(localName = "GetRoads", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetRoads")
    @ResponseWrapper(localName = "GetRoadsResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetRoadsResponse")
    @WebResult(name = "GetRoadsResult", targetNamespace = "http://tempuri.org/")
    public org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfRoadEntity9S24Wb1P getRoads();

    @WebMethod(operationName = "SearchImage", action = "http://tempuri.org/IHoYaVideoWCFService/SearchImage")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/SearchImage", output = "http://tempuri.org/IHoYaVideoWCFService/SearchImageResponse")
    @RequestWrapper(localName = "SearchImage", targetNamespace = "http://tempuri.org/", className = "org.tempuri.SearchImage")
    @ResponseWrapper(localName = "SearchImageResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.SearchImageResponse")
    @WebResult(name = "SearchImageResult", targetNamespace = "http://tempuri.org/")
    public org.datacontract.schemas._2004._07.hoyavideolib.ImageHistoryEntity searchImage(
        @WebParam(name = "deviceId", targetNamespace = "http://tempuri.org/")
        java.lang.Integer deviceId,
        @WebParam(name = "dtBegin", targetNamespace = "http://tempuri.org/")
        javax.xml.datatype.XMLGregorianCalendar dtBegin,
        @WebParam(name = "dtEnd", targetNamespace = "http://tempuri.org/")
        javax.xml.datatype.XMLGregorianCalendar dtEnd
    );

    @WebMethod(operationName = "NextImage", action = "http://tempuri.org/IHoYaVideoWCFService/NextImage")
    @Action(input = "http://tempuri.org/IHoYaVideoWCFService/NextImage", output = "http://tempuri.org/IHoYaVideoWCFService/NextImageResponse")
    @RequestWrapper(localName = "NextImage", targetNamespace = "http://tempuri.org/", className = "org.tempuri.NextImage")
    @ResponseWrapper(localName = "NextImageResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.NextImageResponse")
    @WebResult(name = "NextImageResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String nextImage(
        @WebParam(name = "deviceId", targetNamespace = "http://tempuri.org/")
        java.lang.Integer deviceId,
        @WebParam(name = "imageName", targetNamespace = "http://tempuri.org/")
        java.lang.String imageName
    );
}
