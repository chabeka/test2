package fr.urssaf.image.commons.util.resource;

import java.net.URISyntaxException;

public class ResourceUtil {

   /**
    * Renvoie le chemin complet d'un fichier de ressource
    * @param o l'objet en cours
    * @param resourcePath le chemin de la ressource au sein d'un répertoire de ressource 
    * (exemple, si on a un fichier dans src\main\resources\toto.xsd, il faut passer la chaîne "/toto.xsd"
    * @return le chemin complet du fichier ressource
    * @throws URISyntaxException
    */
   public static String getResourceFullPath(Object o,String resourcePath) throws URISyntaxException
   {
      return o.getClass().getResource(resourcePath).toURI().getPath().toString();
   }
   
}