//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.19 at 06:20:02 PM CET 
//


package org.etsi.uri._01903.v1_3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NoticeReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NoticeReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Organization" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NoticeNumbers" type="{http://uri.etsi.org/01903/v1.3.2#}IntegerListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoticeReferenceType", propOrder = {
    "organization",
    "noticeNumbers"
})
public class NoticeReferenceType {

    @XmlElement(name = "Organization", required = true)
    protected String organization;
    @XmlElement(name = "NoticeNumbers", required = true)
    protected IntegerListType noticeNumbers;

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganization(String value) {
        this.organization = value;
    }

    /**
     * Gets the value of the noticeNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerListType }
     *     
     */
    public IntegerListType getNoticeNumbers() {
        return noticeNumbers;
    }

    /**
     * Sets the value of the noticeNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerListType }
     *     
     */
    public void setNoticeNumbers(IntegerListType value) {
        this.noticeNumbers = value;
    }

}
