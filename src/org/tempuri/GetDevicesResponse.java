
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfDeviceEntity9S24Wb1P;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetDevicesResult" type="{http://schemas.datacontract.org/2004/07/HoYaVideoLib}JsonResponseOfArrayOfDeviceEntity9S24Wb1p" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getDevicesResult"
})
@XmlRootElement(name = "GetDevicesResponse")
public class GetDevicesResponse {

    @XmlElementRef(name = "GetDevicesResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P> getDevicesResult;

    /**
     * ��ȡgetDevicesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfDeviceEntity9S24Wb1P }{@code >}
     *     
     */
    public JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P> getGetDevicesResult() {
        return getDevicesResult;
    }

    /**
     * ����getDevicesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfDeviceEntity9S24Wb1P }{@code >}
     *     
     */
    public void setGetDevicesResult(JAXBElement<JsonResponseOfArrayOfDeviceEntity9S24Wb1P> value) {
        this.getDevicesResult = value;
    }

}
