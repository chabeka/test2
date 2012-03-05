/**
 * 
 */
package fr.urssaf.image.sae.services.capturemasse.support.enrichissement;

import fr.urssaf.image.sae.bo.model.bo.SAEDocument;

/**
 * Composant pour l'enrichissement des métadonnées d'un document dans les
 * traitements de capture de masse
 * 
 */
public interface EnrichissementMetadonneeSupport {

   /**
    * Service d'enrichissement d'une metadonnee
    * 
    * @param document
    *           modèle métier du document
    */
   void enrichirMetadonnee(SAEDocument document);
}