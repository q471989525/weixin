package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2014-12-22T10:14:54.324+08:00
 * Generated source version: 3.0.3
 * 
 */
@WebServiceClient(name = "HoYaVideoWCFService", 
                  wsdlLocation = "http://192.168.50.24:7000/HoYaVideoWCFService/?wsdl",
                  targetNamespace = "http://tempuri.org/") 
public class HoYaVideoWCFService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://tempuri.org/", "HoYaVideoWCFService");
    public final static QName BasicHttpBindingIHoYaVideoWCFService = new QName("http://tempuri.org/", "BasicHttpBinding_IHoYaVideoWCFService");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.50.24:7000/HoYaVideoWCFService/?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(HoYaVideoWCFService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.50.24:7000/HoYaVideoWCFService/?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public HoYaVideoWCFService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HoYaVideoWCFService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HoYaVideoWCFService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HoYaVideoWCFService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HoYaVideoWCFService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public HoYaVideoWCFService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns IHoYaVideoWCFService
     */
    @WebEndpoint(name = "BasicHttpBinding_IHoYaVideoWCFService")
    public IHoYaVideoWCFService getBasicHttpBindingIHoYaVideoWCFService() {
        return super.getPort(BasicHttpBindingIHoYaVideoWCFService, IHoYaVideoWCFService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IHoYaVideoWCFService
     */
    @WebEndpoint(name = "BasicHttpBinding_IHoYaVideoWCFService")
    public IHoYaVideoWCFService getBasicHttpBindingIHoYaVideoWCFService(WebServiceFeature... features) {
        return super.getPort(BasicHttpBindingIHoYaVideoWCFService, IHoYaVideoWCFService.class, features);
    }

}
