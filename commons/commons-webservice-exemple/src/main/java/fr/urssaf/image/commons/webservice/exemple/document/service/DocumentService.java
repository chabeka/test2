package fr.urssaf.image.commons.webservice.exemple.document.service;

import java.rmi.RemoteException;
import java.util.List;

import fr.urssaf.image.commons.webservice.exemple.document.modele.Document;

public interface DocumentService {

	public List<Document> allDocuments() throws RemoteException;
}