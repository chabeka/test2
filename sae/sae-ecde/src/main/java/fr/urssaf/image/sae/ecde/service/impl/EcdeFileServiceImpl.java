package fr.urssaf.image.sae.ecde.service.impl;

import java.io.File;
import java.net.URI;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.ecde.exception.EcdeBadFileException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLException;
import fr.urssaf.image.sae.ecde.exception.EcdeBadURLFormatException;
import fr.urssaf.image.sae.ecde.modele.source.EcdeSource;
import fr.urssaf.image.sae.ecde.service.EcdeFileService;

/**
 * Service de manipulation des URL ECDE et des chemins de fichiers.
 * 
 * {@link EcdeFileService}
 * 
 */

@Service
public class EcdeFileServiceImpl implements EcdeFileService {

   /**
    * Recupération des Constantes
    */
   public static final String ECDE = "ecde";
   public static final String DOCUMENTS = "documents";
   public static final String EXPR_REG = "ecde://.*/.*/(19|20)[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])/.*/documents/.+";
   
   
   @Autowired
   private MessageSource messageSource;
   
   /**
    * Implémentation de la méthode convertFileToURI
    * 
    * @param ecdeFile
    *           fichier ecde
    * @param sources
    *           liste des ecdes
    *           
    * @throws EcdeBadFileException Mauvais chemin de fichier
    * 
    * @return URI uri converti
    * 
    * 
    */
   @Override
   public final URI convertFileToURI(File ecdeFile, EcdeSource... sources)
         throws EcdeBadFileException {
      // TODO
      return null;
   }

   /**
    * Implémentation de la méthode convertURIToFile
    * 
    * @param ecdeURL
    *           url a convertir
    * @param sources
    *           liste des ecdes
    *           
    * @throws EcdeBadURLException mauvaise url 
    * @throws EcdeBadURLFormatException mauvais format d'url
    * 
    * @return File file converti
    * 
    * 
    * */
   @Override
   public final File convertURIToFile(URI ecdeURL, EcdeSource... sources)
         throws EcdeBadURLException, EcdeBadURLFormatException {

      // basePath recuperer a partir de ecdeSource
      String basePath = "";
      
      // boolean pour signaler que authority de l'uri bien trouvé dans sources
      boolean trouve = false;

      // Il faut commencer par vérifier que le ecdeURL respecte le format URL ECDE
      // ecde://ecde.cer69.recouv/numeroCS/dateTraitement/idTraitement/documents/nom_du_fichier
      if ( ! ecdeURL.toString().matches(EXPR_REG) ||
           ! (ecdeURL.getPath().lastIndexOf("..") == -1) // pour verifier qu'il n'y est pas de ../..
         ) {
         throw new EcdeBadURLFormatException(recupererMessage("ecdeBadUrlFormatException.message", ecdeURL));
      }
      
      // il faut maintenant venir parcourir la liste sources afin de recuperer l'ECDE correspondant
      // Parcours donc de la liste sources
      for (EcdeSource ecdeSource : sources) {
         if ( ecdeURL.getAuthority().equals(ecdeSource.getHost()) ) {
             //concordance entre uri et ecdesource donné en paramètre
            basePath = ecdeSource.getBasePath().toString();
            trouve = true;
         }
      }
      
      // levée d'exception car uri non trouve dans sources
      if ( !trouve ){
         throw new EcdeBadURLException(recupererMessage("ecdeBadUrlException.message", ecdeURL));
      }

      // Construire le chemin absolu du fichier
      return new File(basePath + ecdeURL.getPath());
      
   }
   
   // recupere les messages d erreur
   private String recupererMessage(String message, URI ecdeURL) {
      return messageSource.getMessage(message, new Object[] { ecdeURL }, Locale.FRENCH);
   }
 

}
