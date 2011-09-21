package fr.urssaf.image.sae.services.controles.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.MetadataError;
import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.exception.capture.DuplicatedMetadataEx;
import fr.urssaf.image.sae.exception.capture.EmptyDocumentEx;
import fr.urssaf.image.sae.exception.capture.InvalidValueTypeAndFormatMetadataEx;
import fr.urssaf.image.sae.exception.capture.NotSpecifiableMetadataEx;
import fr.urssaf.image.sae.exception.capture.RequiredArchivableMetadataEx;
import fr.urssaf.image.sae.exception.capture.RequiredStorageMetadataEx;
import fr.urssaf.image.sae.exception.capture.UnknownHashCodeEx;
import fr.urssaf.image.sae.exception.capture.UnknownMetadataEx;
import fr.urssaf.image.sae.metadata.control.services.MetadataControlServices;
import fr.urssaf.image.sae.services.controles.SAEControlesCaptureService;
import fr.urssaf.image.sae.services.enrichment.dao.impl.SAEMetatadaFinderUtils;
import fr.urssaf.image.sae.services.enrichment.xml.model.SAEArchivalMetadatas;
import fr.urssaf.image.sae.services.util.ResourceMessagesUtils;
import fr.urssaf.image.sae.storage.dfce.utils.Utils;

/**
 * Classe de contrôle des métadonnées.
 * 
 * @author rhofir.
 */
@Service
@Qualifier("saeControlesCaptureService")
public class SAEControlesCaptureServiceImpl implements
      SAEControlesCaptureService {

   @Autowired
   @Qualifier("metadataControlServices")
   private MetadataControlServices metadataCS;

   /*
    * (non-Javadoc)
    * 
    * @see
    * fr.urssaf.image.sae.services.document.controles.SAEControlesCaptureService
    * #checkSaeMetadataForCapture(fr.urssaf.image.sae.bo.model.bo.SAEDocument)
    */
   @Override
   public void checkSaeMetadataForCapture(SAEDocument saeDocument)
         throws NotSpecifiableMetadataEx, RequiredArchivableMetadataEx {
      List<MetadataError> errorsList = metadataCS
            .checkArchivableMetadata(saeDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         List<String> codeLongErrors = buildLongCodeError(errorsList);
         throw new NotSpecifiableMetadataEx(ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.interdites", codeLongErrors));
      }
      errorsList = metadataCS.checkRequiredForArchivalMetadata(saeDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         List<String> codeLongErrors = buildLongCodeError(errorsList);
         throw new RequiredArchivableMetadataEx(ResourceMessagesUtils
               .loadMessage("capture.metadonnees.archivage.obligatoire",
                     codeLongErrors));
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void checkSaeMetadataForStorage(SAEDocument sAEDocument)
         throws RequiredStorageMetadataEx {
      List<MetadataError> errorsList = metadataCS
            .checkRequiredForStorageMetadata(sAEDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         List<String> codeLongErrors = buildLongCodeError(errorsList);
         throw new RequiredStorageMetadataEx(ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.stockage.obligatoire", codeLongErrors));
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void checkHashCodeMetadataForStorage(SAEDocument saeDocument)
         throws UnknownHashCodeEx {
      String hashCodeValue = SAEMetatadaFinderUtils.codeMetadataFinder(saeDocument
            .getMetadatas(), SAEArchivalMetadatas.HASHDOC.getLongCode());
      if (!DigestUtils.shaHex(saeDocument.getContent()).equals(hashCodeValue)) {
         throw new UnknownHashCodeEx(ResourceMessagesUtils.loadMessage(
               "capture.hash.erreur", saeDocument.getFilePath()));
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void checkUntypedDocument(UntypedDocument untypedDocument)
         throws EmptyDocumentEx {
      if (untypedDocument.getContent() == null
            || untypedDocument.getContent().length == 0) {
         throw new EmptyDocumentEx(ResourceMessagesUtils.loadMessage(
               "capture.fichier.vide", untypedDocument.getFilePath()));
      }

   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void checkUntypedMetadata(UntypedDocument untypedDocument)
         throws UnknownMetadataEx, DuplicatedMetadataEx,
         InvalidValueTypeAndFormatMetadataEx {
      List<MetadataError> errorsList = metadataCS
            .checkExistingMetadata(untypedDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         List<String> codeLongErrors = buildLongCodeError(errorsList);
         throw new UnknownMetadataEx(ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.inconnu", codeLongErrors));
      }
      errorsList = metadataCS.checkMetadataValueTypeAndFormat(untypedDocument);
      if (CollectionUtils.isNotEmpty(errorsList)) {
         List<String> codeLongErrors = buildLongCodeError(errorsList);
         throw new InvalidValueTypeAndFormatMetadataEx(ResourceMessagesUtils
               .loadMessage("capture.metadonnees.format.type.non.valide",
                     codeLongErrors));
      }
      errorsList = metadataCS.checkDuplicateMetadata(untypedDocument
            .getUMetadatas());
      if (CollectionUtils.isNotEmpty(errorsList)) {
         List<String> codeLongErrors = buildLongCodeError(errorsList);
         throw new DuplicatedMetadataEx(ResourceMessagesUtils.loadMessage(
               "capture.metadonnees.doublon", codeLongErrors));
      }
   }

   /**
    * Construire une list de code long.
    * 
    * @param errorsList
    *           : Liste de de type {@link MetadataError}
    * @return Liste de code long à partir d'une liste de de type
    *         {@link MetadataError}
    */
   private List<String> buildLongCodeError(List<MetadataError> errorsList) {
      List<String> codeLongErrors = new ArrayList<String>();
      for (MetadataError metadataError : Utils.nullSafeIterable(errorsList)) {
         codeLongErrors.add(metadataError.getLongCode());
      }
      return codeLongErrors;
   }
}