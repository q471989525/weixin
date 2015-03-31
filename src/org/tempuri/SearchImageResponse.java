
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.hoyavideolib.ImageHistoryEntity;


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
 *         &lt;element name="SearchImageResult" type="{http://schemas.datacontract.org/2004/07/HoYaVideoLib}ImageHistoryEntity" minOccurs="0"/>
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
    "searchImageResult"
})
@XmlRootElement(name = "SearchImageResponse")
public class SearchImageResponse {

    @XmlElementRef(name = "SearchImageResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ImageHistoryEntity> searchImageResult;

    /**
     * ��ȡsearchImageResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ImageHistoryEntity }{@code >}
     *     
     */
    public JAXBElement<ImageHistoryEntity> getSearchImageResult() {
        return searchImageResult;
    }

    /**
     * ����searchImageResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ImageHistoryEntity }{@code >}
     *     
     */
    public void setSearchImageResult(JAXBElement<ImageHistoryEntity> value) {
        this.searchImageResult = value;
    }

}
