//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.19 at 01:55:38 PM CET 
//


package fr.urssaf.image.commons.xml.jaxb.exemple.sandre.modele.intref;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import fr.urssaf.image.commons.xml.jaxb.exemple.sandre.modele.composantstypes.TextType;


/**
 * Nom donn� � une
 *             unit� ou une structure rattach�e � un unique intervenant sur
 *             le plan de l'organisation du travail, et exer�ant un
 *             ensemble de t�ches sp�cifiques.
 *             La notion de service interne apparait d�s lors qu'un intervenant
 *             d�finit explicitement son mode d'organisation ainsi que la
 *             r�partition des moyens humains et mat�riels en plusierus
 *             unit�s distinctes.
 * 
 *             Cette information permet de pr�ciser davantage l'identit� des
 *             intervenants mis en jeu.
 * 
 * <p>Java class for NomService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NomService">
 *   &lt;simpleContent>
 *     &lt;restriction base="&lt;http://xml.sandre.eaufrance.fr/Composants/1>TextType">
 *     &lt;/restriction>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NomService")
public class NomService
    extends TextType
{


}