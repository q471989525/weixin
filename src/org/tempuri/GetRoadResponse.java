
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfRoadEntity9S24Wb1P;


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
 *         &lt;element name="GetRoadResult" type="{http://schemas.datacontract.org/2004/07/HoYaVideoLib}JsonResponseOfRoadEntity9S24Wb1p" minOccurs="0"/>
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
    "getRoadResult"
})
@XmlRootElement(name = "GetRoadResponse")
public class GetRoadResponse {

    @XmlElementRef(name = "GetRoadResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<JsonResponseOfRoadEntity9S24Wb1P> getRoadResult;

    /**
     * ��ȡgetRoadResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JsonResponseOfRoadEntity9S24Wb1P }{@code >}
     *     
     */
    public JAXBElement<JsonResponseOfRoadEntity9S24Wb1P> getGetRoadResult() {
        return getRoadResult;
    }

    /**
     * ����getRoadResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JsonResponseOfRoadEntity9S24Wb1P }{@code >}
     *     
     */
    public void setGetRoadResult(JAXBElement<JsonResponseOfRoadEntity9S24Wb1P> value) {
        this.getRoadResult = value;
    }

}
