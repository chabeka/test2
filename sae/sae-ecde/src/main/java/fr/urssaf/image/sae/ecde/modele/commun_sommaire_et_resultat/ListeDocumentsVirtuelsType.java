//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.07.19 at 02:13:04 PM CEST 
//


package fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Une liste de documents virtuels
 * 
 * <p>Java class for listeDocumentsVirtuelsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listeDocumentsVirtuelsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentVirtuel" type="{http://www.cirtil.fr/sae/commun_sommaire_et_resultat}documentVirtuelType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listeDocumentsVirtuelsType", propOrder = {
    "documentVirtuel"
})
@SuppressWarnings("PMD")
public class ListeDocumentsVirtuelsType {

    protected List<DocumentVirtuelType> documentVirtuel;

    /**
     * Gets the value of the documentVirtuel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentVirtuel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentVirtuel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentVirtuelType }
     * 
     * 
     */
    public List<DocumentVirtuelType> getDocumentVirtuel() {
        if (documentVirtuel == null) {
            documentVirtuel = new ArrayList<DocumentVirtuelType>();
        }
        return this.documentVirtuel;
    }

}
