
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfRoadEntity9S24Wb1P;


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
 *         &lt;element name="GetRoadsResult" type="{http://schemas.datacontract.org/2004/07/HoYaVideoLib}JsonResponseOfArrayOfRoadEntity9S24Wb1p" minOccurs="0"/>
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
    "getRoadsResult"
})
@XmlRootElement(name = "GetRoadsResponse")
public class GetRoadsResponse {

    @XmlElementRef(name = "GetRoadsResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<JsonResponseOfArrayOfRoadEntity9S24Wb1P> getRoadsResult;

    /**
     * ��ȡgetRoadsResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfRoadEntity9S24Wb1P }{@code >}
     *     
     */
    public JAXBElement<JsonResponseOfArrayOfRoadEntity9S24Wb1P> getGetRoadsResult() {
        return getRoadsResult;
    }

    /**
     * ����getRoadsResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JsonResponseOfArrayOfRoadEntity9S24Wb1P }{@code >}
     *     
     */
    public void setGetRoadsResult(JAXBElement<JsonResponseOfArrayOfRoadEntity9S24Wb1P> value) {
        this.getRoadsResult = value;
    }

}
