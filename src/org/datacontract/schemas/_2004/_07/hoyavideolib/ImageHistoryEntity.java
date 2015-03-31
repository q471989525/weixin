
package org.datacontract.schemas._2004._07.hoyavideolib;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;


/**
 * <p>ImageHistoryEntity complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ImageHistoryEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DeviceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImageList" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageHistoryEntity", propOrder = {
    "deviceId",
    "deviceName",
    "imageList"
})
public class ImageHistoryEntity {

    @XmlElement(name = "DeviceId")
    protected Integer deviceId;
    @XmlElementRef(name = "DeviceName", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> deviceName;
    @XmlElementRef(name = "ImageList", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfstring> imageList;

    /**
     * ��ȡdeviceId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     * ����deviceId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeviceId(Integer value) {
        this.deviceId = value;
    }

    /**
     * ��ȡdeviceName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDeviceName() {
        return deviceName;
    }

    /**
     * ����deviceName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDeviceName(JAXBElement<String> value) {
        this.deviceName = value;
    }

    /**
     * ��ȡimageList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getImageList() {
        return imageList;
    }

    /**
     * ����imageList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setImageList(JAXBElement<ArrayOfstring> value) {
        this.imageList = value;
    }

}
