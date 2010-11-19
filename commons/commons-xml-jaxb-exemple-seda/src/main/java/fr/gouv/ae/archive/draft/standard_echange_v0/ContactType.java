//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.19 at 06:20:02 PM CET 
//


package fr.gouv.ae.archive.draft.standard_echange_v0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import fr.gouv.ae.archive.draft.standard_echange_v0_2.qualifieddatatype._1.ArchivesIDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._6.TextType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;OCAAES000003&lt;/ccts:UniqueID&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Acronym xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;ABIE&lt;/ccts:Acronym&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Contact&lt;/ccts:DictionaryEntryName&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Version xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;1.0&lt;/ccts:Version&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Informations relatives � une personne ou � une organisation qui agit comme point de contact avec une autre personne ou une autre organisation.&lt;/ccts:Definition&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:ObjectClassTerm xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Contact&lt;/ccts:ObjectClassTerm&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ccts:QualifierTerm xmlns:ccts="urn:un:unece:uncefact:documentation:standard:CoreComponentsTechnicalSpecification:2" xmlns="fr:gouv:ae:archive:draft:standard_echange_v0.2" xmlns:qdt="fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1" xmlns:udt="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Contact&lt;/ccts:QualifierTerm&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DepartmentName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6}TextType" minOccurs="0"/>
 *         &lt;element name="Identification" type="{fr:gouv:ae:archive:draft:standard_echange_v0.2:QualifiedDataType:1}ArchivesIDType" minOccurs="0"/>
 *         &lt;element name="PersonName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6}TextType" minOccurs="0"/>
 *         &lt;element name="Responsibility" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:6}TextType" minOccurs="0"/>
 *         &lt;element name="Address" type="{fr:gouv:ae:archive:draft:standard_echange_v0.2}AddressType" minOccurs="0"/>
 *         &lt;element name="Communication" type="{fr:gouv:ae:archive:draft:standard_echange_v0.2}CommunicationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactType", propOrder = {
    "departmentName",
    "identification",
    "personName",
    "responsibility",
    "address",
    "communication"
})
public class ContactType {

    @XmlElement(name = "DepartmentName")
    protected TextType departmentName;
    @XmlElement(name = "Identification")
    protected ArchivesIDType identification;
    @XmlElement(name = "PersonName")
    protected TextType personName;
    @XmlElement(name = "Responsibility")
    protected TextType responsibility;
    @XmlElement(name = "Address")
    protected AddressType address;
    @XmlElement(name = "Communication")
    protected List<CommunicationType> communication;

    /**
     * Gets the value of the departmentName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the value of the departmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDepartmentName(TextType value) {
        this.departmentName = value;
    }

    /**
     * Gets the value of the identification property.
     * 
     * @return
     *     possible object is
     *     {@link ArchivesIDType }
     *     
     */
    public ArchivesIDType getIdentification() {
        return identification;
    }

    /**
     * Sets the value of the identification property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchivesIDType }
     *     
     */
    public void setIdentification(ArchivesIDType value) {
        this.identification = value;
    }

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setPersonName(TextType value) {
        this.personName = value;
    }

    /**
     * Gets the value of the responsibility property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getResponsibility() {
        return responsibility;
    }

    /**
     * Sets the value of the responsibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setResponsibility(TextType value) {
        this.responsibility = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the communication property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communication property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunication().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommunicationType }
     * 
     * 
     */
    public List<CommunicationType> getCommunication() {
        if (communication == null) {
            communication = new ArrayList<CommunicationType>();
        }
        return this.communication;
    }

}
