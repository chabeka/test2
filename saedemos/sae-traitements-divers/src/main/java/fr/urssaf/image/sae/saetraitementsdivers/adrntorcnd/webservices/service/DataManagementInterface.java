/**
 * 
 */
package fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.service;

import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.exception.AdrnToRcndException;
import fr.urssaf.image.sae.saetraitementsdivers.adrntorcnd.webservices.modele.RNDTypeDocument;

/**
 * Stockage des données
 * 
 */
public interface DataManagementInterface {

   /**
    * Sauvegarde dans un fichier XML la miste des documents supportés
    * 
    * @param typesDocuments
    *           les documents à sauvegarder
    * @param version
    *           version de compatibilité
    * @throws AdrnToRcndException
    *            exception lors du traitement
    */
   void saveDocuments(RNDTypeDocument[] typesDocuments, String version)
         throws AdrnToRcndException;

}
