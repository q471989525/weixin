
package org.datacontract.schemas._2004._07.hoyavideolib;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DeviceEntity complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DeviceEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeviceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DeviceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RoadName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RoadNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="StationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="StationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VC_IP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VC_Mac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ftpPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="httpPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="n_Channel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="n_Port" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="n_positionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="vc_LoginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vc_Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vc_Protocol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceEntity", propOrder = {
    "deviceId",
    "deviceName",
    "remark",
    "roadName",
    "roadNo",
    "stationId",
    "stationName",
    "status",
    "vcip",
    "vcMac",
    "ftpPath",
    "httpPath",
    "nChannel",
    "nPort",
    "nPositionId",
    "vcLoginName",
    "vcPassword",
    "vcProtocol"
})
public class DeviceEntity {

    @XmlElement(name = "DeviceId")
    protected Integer deviceId;
    @XmlElementRef(name = "DeviceName", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> deviceName;
    @XmlElementRef(name = "Remark", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> remark;
    @XmlElementRef(name = "RoadName", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> roadName;
    @XmlElement(name = "RoadNo")
    protected Integer roadNo;
    @XmlElement(name = "StationId")
    protected Integer stationId;
    @XmlElementRef(name = "StationName", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> stationName;
    @XmlElement(name = "Status")
    protected Boolean status;
    @XmlElementRef(name = "VC_IP", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vcip;
    @XmlElementRef(name = "VC_Mac", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vcMac;
    @XmlElementRef(name = "ftpPath", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ftpPath;
    @XmlElementRef(name = "httpPath", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> httpPath;
    @XmlElement(name = "n_Channel")
    protected Integer nChannel;
    @XmlElement(name = "n_Port")
    protected Integer nPort;
    @XmlElement(name = "n_positionId")
    protected Integer nPositionId;
    @XmlElementRef(name = "vc_LoginName", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vcLoginName;
    @XmlElementRef(name = "vc_Password", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vcPassword;
    @XmlElementRef(name = "vc_Protocol", namespace = "http://schemas.datacontract.org/2004/07/HoYaVideoLib", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vcProtocol;

    /**
     * 获取deviceId属性的值。
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
     * 设置deviceId属性的值。
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
     * 获取deviceName属性的值。
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
     * 设置deviceName属性的值。
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
     * 获取remark属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRemark() {
        return remark;
    }

    /**
     * 设置remark属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRemark(JAXBElement<String> value) {
        this.remark = value;
    }

    /**
     * 获取roadName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRoadName() {
        return roadName;
    }

    /**
     * 设置roadName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRoadName(JAXBElement<String> value) {
        this.roadName = value;
    }

    /**
     * 获取roadNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRoadNo() {
        return roadNo;
    }

    /**
     * 设置roadNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRoadNo(Integer value) {
        this.roadNo = value;
    }

    /**
     * 获取stationId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStationId() {
        return stationId;
    }

    /**
     * 设置stationId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStationId(Integer value) {
        this.stationId = value;
    }

    /**
     * 获取stationName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStationName() {
        return stationName;
    }

    /**
     * 设置stationName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStationName(JAXBElement<String> value) {
        this.stationName = value;
    }

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStatus(Boolean value) {
        this.status = value;
    }

    /**
     * 获取vcip属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVCIP() {
        return vcip;
    }

    /**
     * 设置vcip属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVCIP(JAXBElement<String> value) {
        this.vcip = value;
    }

    /**
     * 获取vcMac属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVCMac() {
        return vcMac;
    }

    /**
     * 设置vcMac属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVCMac(JAXBElement<String> value) {
        this.vcMac = value;
    }

    /**
     * 获取ftpPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFtpPath() {
        return ftpPath;
    }

    /**
     * 设置ftpPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFtpPath(JAXBElement<String> value) {
        this.ftpPath = value;
    }

    /**
     * 获取httpPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHttpPath() {
        return httpPath;
    }

    /**
     * 设置httpPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHttpPath(JAXBElement<String> value) {
        this.httpPath = value;
    }

    /**
     * 获取nChannel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNChannel() {
        return nChannel;
    }

    /**
     * 设置nChannel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNChannel(Integer value) {
        this.nChannel = value;
    }

    /**
     * 获取nPort属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNPort() {
        return nPort;
    }

    /**
     * 设置nPort属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNPort(Integer value) {
        this.nPort = value;
    }

    /**
     * 获取nPositionId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNPositionId() {
        return nPositionId;
    }

    /**
     * 设置nPositionId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNPositionId(Integer value) {
        this.nPositionId = value;
    }

    /**
     * 获取vcLoginName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVcLoginName() {
        return vcLoginName;
    }

    /**
     * 设置vcLoginName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVcLoginName(JAXBElement<String> value) {
        this.vcLoginName = value;
    }

    /**
     * 获取vcPassword属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVcPassword() {
        return vcPassword;
    }

    /**
     * 设置vcPassword属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVcPassword(JAXBElement<String> value) {
        this.vcPassword = value;
    }

    /**
     * 获取vcProtocol属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVcProtocol() {
        return vcProtocol;
    }

    /**
     * 设置vcProtocol属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVcProtocol(JAXBElement<String> value) {
        this.vcProtocol = value;
    }

}
