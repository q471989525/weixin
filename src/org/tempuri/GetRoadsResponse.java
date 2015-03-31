
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.hoyavideolib.JsonResponseOfArrayOfRoadEntity9S24Wb1P;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取getRoadsResult属性的值。
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
     * 设置getRoadsResult属性的值。
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
