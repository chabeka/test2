package fr.urssaf.image.sae.storage.model.storagedocument;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe concrète représentant le résultat de l’insertion en masse<BR>
 * 
 *<li>
 * Attribut storageDocuments : la liste des documents archivés</li> <li>
 * Attribut storageDocumentsOnError : la liste des documents en erreur</li> <li>
 * Attribut uuids : La liste des uuids des documents archivés</li>
 */
public class BulkInsertionResults {

   private StorageDocuments storageDocuments;

   private StorageDocumentsOnError storageDocumentsOnError;

   private List<UUID> uuids;

   /**
    * Retourne la liste des documents archivés
    * 
    * @return Liste des documents archivés
    */
   public final StorageDocuments getStorageDocuments() {
      return storageDocuments;
   }

   /**
    * Retourne la liste des documents en erreur
    * 
    * @param storageDocuments
    *           Liste des documents en erreur
    */
   public final void setStorageDocuments(StorageDocuments storageDocuments) {
      this.storageDocuments = storageDocuments;
   }

   /**
    * Retourne la liste des documents en erreur
    * 
    * @return la liste des documents en erreur
    */
   public final StorageDocumentsOnError getStorageDocumentsOnError() {
      return storageDocumentsOnError;
   }

   /**
    * Initialise la liste des documents en erreur
    * 
    * @param storageDocumentsOnError
    *           Liste des documents en erreur
    */
   public final void setStorageDocumentsOnError(
         StorageDocumentsOnError storageDocumentsOnError) {
      this.storageDocumentsOnError = storageDocumentsOnError;
   }

   /**
    * Retourne la liste des UUID des documents dont l’insertion en masse s’est
    * bien déroulée
    * 
    * @return liste des UUIDs
    */
   public final List<UUID> getUuids() {
      if (uuids == null) {
         return new ArrayList<UUID>();
      }
      return uuids;
   }

   /**
    * Initialise la liste des UUID des documents dont l’insertion en masse s’est
    * bien déroulée
    * 
    * @param uuids
    *           La liste des UUIDS
    */
   public final void setUuids(List<UUID> uuids) {
      this.uuids = uuids;
   }

   /**
    * Constructeur
    * 
    * @param storageDocuments
    *           Les documents archivés
    * @param storageDocumentsOnError
    *           Les documents en erreur
    * @param uuids
    *           Les UUIDs des documents archivés
    */
   public BulkInsertionResults(final StorageDocuments storageDocuments,
         final StorageDocumentsOnError storageDocumentsOnError,
         final List<UUID> uuids) {
      this.storageDocuments = storageDocuments;
      this.storageDocumentsOnError = storageDocumentsOnError;
      this.uuids = uuids;
   }

}
