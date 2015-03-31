
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.datacontract.schemas._2004._07.hoyavideolib.ImageHistoryEntity;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfDeviceEntity9S24Wb1P;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfRoadEntity9S24Wb1P;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfDeviceEntity9S24Wb1P;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfRoadEntity9S24Wb1P;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDevicesByRoadResponseGetDevicesByRoadResult_QNAME = new QName("http://tempuri.org/", "GetDevicesByRoadResult");
    private final static QName _PrevImageResponsePrevImageResult_QNAME = new QName("http://tempuri.org/", "PrevImageResult");
    private final static QName _GetDevicesResponseGetDevicesResult_QNAME = new QName("http://tempuri.org/", "GetDevicesResult");
    private final static QName _SearchImageResponseSearchImageResult_QNAME = new QName("http://tempuri.org/", "SearchImageResult");
    private final static QName _NextImageResponseNextImageResult_QNAME = new QName("http://tempuri.org/", "NextImageResult");
    private final static QName _GetRoadsResponseGetRoadsResult_QNAME = new QName("http://tempuri.org/", "GetRoadsResult");
    private final static QName _GetRoadResponseGetRoadResult_QNAME = new QName("http://tempuri.org/", "GetRoadResult");
    private final static QName _PrevImageImageName_QNAME = new QName("http://tempuri.org/", "imageName");
    private final static QName _GetDeviceResponseGetDeviceResult_QNAME = new QName("http://tempuri.org/", "GetDeviceResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDevicesByRoad }
     * 
     */
    public GetDevicesByRoad createGetDevicesByRoad() {
        return new GetDevicesByRoad();
    }

    /**
     * Create an instance of {@link SearchImageResponse }
     * 
     */
    public SearchImageResponse createSearchImageResponse() {
        return new SearchImageResponse();
    }

    /**
     * Create an instance of {@link NextImageResponse }
     * 
     */
    public NextImageResponse createNextImageResponse() {
        return new NextImageResponse();
    }

    /**
     * Create an instance of {@link GetRoad }
     * 
     */
    public GetRoad createGetRoad() {
        return new GetRoad();
    }

    /**
     * Create an instance of {@link NextImage }
     * 
     */
    public NextImage createNextImage() {
        return new NextImage();
    }

    /**
     * Create an instance of {@link GetRoads }
     * 
     */
    public GetRoads createGetRoads() {
        return new GetRoads();
    }

    /**
     * Create an instance of {@link GetDevices }
     * 
     */
    public GetDevices createGetDevices() {
        return new GetDevices();
    }

    /**
     * Create an instance of {@link GetDevicesByRoadResponse }
     * 
     */
    public GetDevicesByRoadResponse createGetDevicesByRoadResponse() {
        return new GetDevicesByRoadResponse();
    }

    /**
     * Create an instance of {@link PrevImageResponse }
     * 
     */
    public PrevImageResponse createPrevImageResponse() {
        return new PrevImageResponse();
    }

    /**
     * Create an instance of {@link GetRoadsResponse }
     * 
     */
    public GetRoadsResponse createGetRoadsResponse() {
        return new GetRoadsResponse();
    }

    /**
     * Create an instance of {@link GetDevice }
     * 
     */
    public GetDevice createGetDevice() {
        return new GetDevice();
    }

    /**
     * Create an instance of {@link PrevImage }
     * 
     */
    public PrevImage createPrevImage() {
        return new PrevImage();
    }

    /**
     * Create an instance of {@link GetDeviceResponse }
     * 
     */
    public GetDeviceResponse createGetDeviceResponse() {
        return new GetDeviceResponse();
    }

    /**
     * Create an instance of {@link GetRoadResponse }
     * 
     */
    public GetRoadResponse createGetRoadResponse() {
        return new GetRoadResponse();
    }

    /**
     * Create an instance of {@link GetDevicesResponse }
     * 
     */
    public GetDevicesResponse createGetDevicesResponse() {
        return new GetDevicesResponse();
    }

    /**
     * Create an instance of {@link SearchImage }
     * 
     */
    public SearchImage createSearchImage() {
        return new SearchImage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfDeviceEntity9S24Wb1P }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetDevicesByRoadResult", scope = GetDevicesByRoadResponse.class)
    public JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P> createGetDevicesByRoadResponseGetDevicesByRoadResult(JsonResponseOfArrayOfDeviceEntity9S24Wb1P value) {
        return new JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P>(_GetDevicesByRoadResponseGetDevicesByRoadResult_QNAME, JsonResponseOfArrayOfDeviceEntity9S24Wb1P.class, GetDevicesByRoadResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "PrevImageResult", scope = PrevImageResponse.class)
    public JAXBElement<String> createPrevImageResponsePrevImageResult(String value) {
        return new JAXBElement<String>(_PrevImageResponsePrevImageResult_QNAME, String.class, PrevImageResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfDeviceEntity9S24Wb1P }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetDevicesResult", scope = GetDevicesResponse.class)
    public JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P> createGetDevicesResponseGetDevicesResult(JsonResponseOfArrayOfDeviceEntity9S24Wb1P value) {
        return new JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P>(_GetDevicesResponseGetDevicesResult_QNAME, JsonResponseOfArrayOfDeviceEntity9S24Wb1P.class, GetDevicesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImageHistoryEntity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "SearchImageResult", scope = SearchImageResponse.class)
    public JAXBElement<ImageHistoryEntity> createSearchImageResponseSearchImageResult(ImageHistoryEntity value) {
        return new JAXBElement<ImageHistoryEntity>(_SearchImageResponseSearchImageResult_QNAME, ImageHistoryEntity.class, SearchImageResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "NextImageResult", scope = NextImageResponse.class)
    public JAXBElement<String> createNextImageResponseNextImageResult(String value) {
        return new JAXBElement<String>(_NextImageResponseNextImageResult_QNAME, String.class, NextImageResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfRoadEntity9S24Wb1P }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetRoadsResult", scope = GetRoadsResponse.class)
    public JAXBElement<JsonResponseOfArrayOfRoadEntity9S24Wb1P> createGetRoadsResponseGetRoadsResult(JsonResponseOfArrayOfRoadEntity9S24Wb1P value) {
        return new JAXBElement<JsonResponseOfArrayOfRoadEntity9S24Wb1P>(_GetRoadsResponseGetRoadsResult_QNAME, JsonResponseOfArrayOfRoadEntity9S24Wb1P.class, GetRoadsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsonResponseOfRoadEntity9S24Wb1P }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetRoadResult", scope = GetRoadResponse.class)
    public JAXBElement<JsonResponseOfRoadEntity9S24Wb1P> createGetRoadResponseGetRoadResult(JsonResponseOfRoadEntity9S24Wb1P value) {
        return new JAXBElement<JsonResponseOfRoadEntity9S24Wb1P>(_GetRoadResponseGetRoadResult_QNAME, JsonResponseOfRoadEntity9S24Wb1P.class, GetRoadResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "imageName", scope = PrevImage.class)
    public JAXBElement<String> createPrevImageImageName(String value) {
        return new JAXBElement<String>(_PrevImageImageName_QNAME, String.class, PrevImage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "imageName", scope = NextImage.class)
    public JAXBElement<String> createNextImageImageName(String value) {
        return new JAXBElement<String>(_PrevImageImageName_QNAME, String.class, NextImage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JsonResponseOfDeviceEntity9S24Wb1P }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetDeviceResult", scope = GetDeviceResponse.class)
    public JAXBElement<JsonResponseOfDeviceEntity9S24Wb1P> createGetDeviceResponseGetDeviceResult(JsonResponseOfDeviceEntity9S24Wb1P value) {
        return new JAXBElement<JsonResponseOfDeviceEntity9S24Wb1P>(_GetDeviceResponseGetDeviceResult_QNAME, JsonResponseOfDeviceEntity9S24Wb1P.class, GetDeviceResponse.class, value);
    }

}
