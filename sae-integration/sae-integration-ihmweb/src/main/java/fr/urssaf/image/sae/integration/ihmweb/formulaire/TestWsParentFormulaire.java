package fr.urssaf.image.sae.integration.ihmweb.formulaire;


/**
 * Classe mère pour les classes de formulaire utilisées dans
 * les contrôleurs qui testent les opérations du service web SaeService
 */
public class TestWsParentFormulaire {

   
   private String urlServiceWeb;
   
   private String etape;
   
   
   /**
    * URL du service web SaeService
    * 
    * @return URL du service web SaeService
    */
   public final String getUrlServiceWeb() {
      return urlServiceWeb;
   }

   
   /**
    * URL du service web SaeService
    * 
    * @param urlServiceWeb URL du service web SaeService
    */
   public final void setUrlServiceWeb(String urlServiceWeb) {
      this.urlServiceWeb = urlServiceWeb;
   }
   
   
   /**
    * Le numéro de l'étape du test
    * 
    * @return Le numéro de l'étape du test
    */
   public final String getEtape() {
      return etape;
   }

   
   /**
    * Le numéro de l'étape du test
    * 
    * @param etape Le numéro de l'étape du test
    */
   public final void setEtape(String etape) {
      this.etape = etape;
   }
   
   
}