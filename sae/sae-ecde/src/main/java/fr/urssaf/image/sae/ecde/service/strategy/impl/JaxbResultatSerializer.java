package fr.urssaf.image.sae.ecde.service.strategy.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.SAEError;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocumentOnError;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.BatchModeType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ErreurType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.FichierType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeDocumentsVirtuelsType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeErreurType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.ListeNonIntegratedDocumentsType;
import fr.urssaf.image.sae.ecde.modele.commun_sommaire_et_resultat.NonIntegratedDocumentType;
import fr.urssaf.image.sae.ecde.modele.resultats.ObjectFactory;
import fr.urssaf.image.sae.ecde.modele.resultats.Resultats;
import fr.urssaf.image.sae.ecde.modele.resultats.ResultatsType;
import fr.urssaf.image.sae.ecde.service.ResultatsXmlService;
import fr.urssaf.image.sae.ecde.service.strategy.ResultatSerializerStrategy;

/**
 * 
 * Classe permettant d'implementer la stratégie ResultatSerializerStrategy
 * 
 */
@Service
public class JaxbResultatSerializer implements ResultatSerializerStrategy {

   @Autowired
   private ResultatsXmlService resultatsXmlS;
   
   // utilisé lors du formatage du cheminEtNomDeFichier
   private String ecdeDirectory = "";
   
   private static final String DOCUMENTS = "documents";
   private static final String FILE_SEPARATOR = System.getProperty("file.separator");
   
   /**
    * {@inheritDoc}
    * @throws EcdeXsdException 
    * @throws IOException 
    */
   @Override
   public final void serializeResultat(Resultats resultat) throws EcdeXsdException, IOException {
      ecdeDirectory = resultat.getEcdeDirectory();
      
      ObjectFactory objFactory = new ObjectFactory();
      ResultatsType resultatsType = objFactory.createResultatsType();
      
      resultatsType.setBatchMode(BatchModeType.fromValue(resultat.getBatchMode()));
      resultatsType.setInitialDocumentsCount(resultat.getInitialDocumentsCount());
      resultatsType.setInitialVirtualDocumentsCount(resultat.getInitialVirtualDocumentsCount());
      resultatsType.setIntegratedDocumentsCount(resultat.getIntegratedDocumentsCount());
      resultatsType.setIntegratedVirtualDocumentsCount(resultat.getIntegratedVirtualDocumentsCount());
      resultatsType.setNonIntegratedDocumentsCount(resultat.getNonIntegratedDocumentsCount());
      resultatsType.setNonIntegratedVirtualDocumentsCount(resultat.getNonIntegratedVirtualDocumentsCount());
      // pour le moment pas de traitement sur les documents virtuels
      resultatsType.setNonIntegratedVirtualDocuments(new ListeDocumentsVirtuelsType());
      
      List<UntypedDocumentOnError> untypedsDError = resultat.getNonIntegratedDocuments();
      // Mapping des objets metiers UntypedDocumentOnError en objet JAXB
      ListeNonIntegratedDocumentsType listNIDType = mapToNonIntegratedDType(untypedsDError);
      resultatsType.setNonIntegratedDocuments(listNIDType);
      
      resultatsType.setErreurBloquanteTraitement(null);
      
      resultatsXmlS.writeResultatsXml(resultatsType, new File(ecdeDirectory + FILE_SEPARATOR + "resultats.xml"));
      
      //creation du fichier drapeau.
      createFlag(ecdeDirectory);
   }
   
   // Mapping des objets metiers UntypedDocumentOnError en objet JAXB
   private ListeNonIntegratedDocumentsType mapToNonIntegratedDType(List<UntypedDocumentOnError> untypedDError) {
      
      ListeNonIntegratedDocumentsType nonIntegratedDocs = new ListeNonIntegratedDocumentsType();
      NonIntegratedDocumentType nonIntegratedDoc = new NonIntegratedDocumentType();
     
      for (UntypedDocumentOnError untypedDocumentOnError : untypedDError) {
         // affectations des erreurs
         List<SAEError> saeErrors = untypedDocumentOnError.getErrors();
         ListeErreurType erreursType = convertSaeErrors(saeErrors);
         nonIntegratedDoc.setErreurs(erreursType);
         nonIntegratedDoc.setObjetNumerique(affectObjetNumToUntypedDoc(untypedDocumentOnError));
         nonIntegratedDocs.getNonIntegratedDocument().add(nonIntegratedDoc);
      }
      
      return nonIntegratedDocs;
   }
   // convertion des SAEErrors en ErreurType
   private ListeErreurType convertSaeErrors(List<SAEError> saeErrors) {
      ListeErreurType erreursType = new ListeErreurType();
      ErreurType erreur = new ErreurType();
      for (SAEError saeError : saeErrors) {
         erreur.setCode(saeError.getCode());
         erreur.setLibelle(saeError.getMessage());
         erreursType.getErreur().add(erreur);
      }
      return erreursType;
   }

   // affecter hashValeur, hashAlgo et cheminEtnomDuFichier a un untypedDocumentOnError
   private FichierType affectObjetNumToUntypedDoc(UntypedDocumentOnError udocOnError) {
      
      List<UntypedMetadata> untypedMetadatas = udocOnError.getUMetadatas();
      
      FichierType fichierType = new FichierType();
      
      for (UntypedMetadata untypedMetadata : untypedMetadatas) {
         if ( "TypeHash".equals(untypedMetadata.getLongCode()) ) {
            // affectation de l'objet Numerique
            fichierType.setHashAlgo(untypedMetadata.getValue());
         }
         if ( "Hash".equals(untypedMetadata.getLongCode()) ) {
            // affectation de l'objet Numerique
            fichierType.setHashValeur(untypedMetadata.getValue());
         }
      }
      fichierType.setCheminEtNomDuFichier(getCheminAbsolu(udocOnError.getFilePath()));
      return fichierType;
   }
   // recupération du chemin absolu a partir d'un chemin relatif.
   private String getCheminAbsolu(String chemin) {
      
      String partieAEfface = ecdeDirectory + FILE_SEPARATOR + DOCUMENTS + FILE_SEPARATOR;
      return chemin.substring(partieAEfface.length());
      
   }
   
   /**
    * Création du fichier flag pour signaler la fin du traitement
    * @throws IOException 
    */
   private void createFlag(String ecdeDirectory) throws IOException {
      try {
         File flag = new File(FilenameUtils.concat(ecdeDirectory,"fin_traitement.flag"));
         flag.delete();
         flag.createNewFile();
      } catch (IOException except) {
         throw new IOException("Erreur de création du fichier flag.", except);
      }
   }
}