package fr.urssaf.image.sae.storage.services.storagedocument;

import java.util.List;

import fr.urssaf.image.sae.storage.exception.RetrievalServiceRtEx;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageDocument;
import fr.urssaf.image.sae.storage.model.storagedocument.StorageMetadata;
import fr.urssaf.image.sae.storage.model.storagedocument.searchcriteria.UUIDCriteria;

/**
 * Fournit les services de récupération des éléments d’un document ou d’un
 * document
 */
public interface RetrivalService {

   /**
    * Permet de récupérer le contenu d’un document à partir du critère «
    * UUIDCriteria ».
    * 
    * @param uuidCriteria
    *           L'identifiant unique du document
    * 
    * @return Le contenu du document
    * 
    * @throws RetrievalServiceRtEx
    *            Runtime exception
    */
   Byte[] retrieveStorageDocumentContentByUUID(UUIDCriteria uuidCriteria)
         throws RetrievalServiceRtEx;

   /**
    * Permet de récupérer les métadonnées d’un document à partir du critère «
    * UUIDCriteria »
    * 
    * @param uuidCriteria
    *           L'identifiant unique du document
    * 
    * @return Une liste de metadonnées
    * 
    * @throws RetrievalServiceRtEx
    *            Runtime exception
    */
   List<StorageMetadata> retrieveStorageDocumentMetaDatasByUUID(
         UUIDCriteria uuidCriteria) throws RetrievalServiceRtEx;

   /**
    * Permet de récupérer un document à partir du critère « UUIDCriteria »
    * 
    * @param uuidCriteria
    *           L'identifiant universel unique du document
    * 
    * @return Le document et ses métas données
    * 
    * @throws RetrievalServiceRtEx
    *            Runtime exception
    */
   StorageDocument retrieveStorageDoculentsByUUID(
         UUIDCriteria uuidCriteria) throws RetrievalServiceRtEx;

}
