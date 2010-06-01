package fr.urssaf.image.commons.webservice.exemple.rpc.literal.modele;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.6
 * Tue Jun 01 14:07:30 CEST 2010
 * Generated source version: 2.2.6
 * 
 */
 
@WebService(targetNamespace = "http://literal.rpc.service.exemple.spring.webservice.commons.image.urssaf.fr/", name = "DocService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DocService {

    @WebResult(name = "return", targetNamespace = "http://literal.rpc.service.exemple.spring.webservice.commons.image.urssaf.fr/", partName = "return")
    @WebMethod
    public DocumentArray allDocuments();

    @WebResult(name = "return", targetNamespace = "http://literal.rpc.service.exemple.spring.webservice.commons.image.urssaf.fr/", partName = "return")
    @WebMethod
    public Document getDocument(
        @WebParam(partName = "id", name = "id")
        int id
    );

    @WebMethod
    public void save(
        @WebParam(partName = "document", name = "document")
        Document document
    );
}
