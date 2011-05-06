package fr.urssaf.image.sae.igc.service;

import fr.urssaf.image.sae.igc.exception.IgcConfigException;
import fr.urssaf.image.sae.igc.modele.IgcConfig;

/**
 * Manipulation de la configuration des éléments de l'IGC
 * 
 * 
 */
public interface IgcConfigService {

   @SuppressWarnings("PMD.LongVariable")
   String AC_RACINES_REQUIRED = "Le répertoire des certificats des AC racines n’est pas spécifié dans le fichier de configuration ${0}";

   @SuppressWarnings("PMD.LongVariable")
   String AC_RACINES_NOTEXIST = "Le répertoire des certificats des AC racines (${0}) spécifié dans le fichier de configuration (${1}) est introuvable";

   String CRLS_REQUIRED = "Le répertoire de téléchargement des CRL n’est pas spécifié dans le fichier de configuration ${0}";

   String CRLS_NOTEXIST = "Le répertoire de téléchargement des CRL (${0}) spécifié dans le fichier de configuration (${1}) est introuvable";

   String URLS_CRL_REQUIRED = "Il faut spécifier au moins une URL de téléchargement des CRL dans le fichier de configuration ${0} ";

   /**
    * Renvoie la configuration des éléments de l'IGC
    * 
    * @param pathConfigFile
    *           Chemin complet du fichier de configuration de l'IGC
    * @return Configuration des éléments de l'IGC
    * @throws IgcConfigException
    *            Une erreur s'est produite lors de la lecture ou de la
    *            vérification de la configuration de l'IGC
    */
   IgcConfig loadConfig(String pathConfigFile) throws IgcConfigException;
}