
package org.datacontract.schemas._2004._07.hoyavideolib;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>JsonResponseOfRoadEntity9S24Wb1p complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="JsonResponseOfRoadEntity9S24Wb1p">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Data" type="{http://schemas.datacontract.org/2004/07/HoYaVideoLib}RoadEntity" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JsonResponseOfRoadEntity9S24Wb1p", propOrder = {
    "data",
    "message",
    "success"
})
public class JsonResponseOfRoadEntity9S24Wb1P {

    @XmlElementRef(name = "Data", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<RoadEntity> data;
    @XmlElementRef(name = "Message", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> message;
    @XmlElement(name = "Success")
    protected Boolean success;

    /**
     * ��ȡdata���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RoadEntity }{@code >}
     *     
     */
    public JAXBElement<RoadEntity> getData() {
        return data;
    }

    /**
     * ����data���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RoadEntity }{@code >}
     *     
     */
    public void setData(JAXBElement<RoadEntity> value) {
        this.data = value;
    }

    /**
     * ��ȡmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMessage() {
        return message;
    }

    /**
     * ����message���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMessage(JAXBElement<String> value) {
        this.message = value;
    }

    /**
     * ��ȡsuccess���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * ����success���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccess(Boolean value) {
        this.success = value;
    }

}
