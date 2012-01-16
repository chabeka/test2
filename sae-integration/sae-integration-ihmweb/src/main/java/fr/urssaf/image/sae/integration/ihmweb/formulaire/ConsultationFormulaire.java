package fr.urssaf.image.sae.integration.ihmweb.formulaire;

import fr.urssaf.image.sae.integration.ihmweb.modele.ResultatTest;

/**
 * Classe de sous-formulaire pour un test du WS SaeService, opération "consultation"<br>
 * <br>
 * Un objet de cette classe s'associe au tag "consultation.tag" (attribut "objetFormulaire")
 */
public class ConsultationFormulaire {

   private ResultatTest resultats = new ResultatTest();
   
   private String idArchivage;
   
   
   /**
    * Les résultats de l'appel à l'opération
    * 
    * @return Les résultats de l'appel à l'opération
    */
   public final ResultatTest getResultats() {
      return this.resultats;
   }


   /**
    * Les résultats de l'appel à l'opération
    * 
    * @param resultats Les résultats de l'appel à l'opération
    */
   public final void setResultats(ResultatTest resultats) {
      this.resultats = resultats;
   }
   
   
   /**
    * L'identifiant d'archivage de l'archive que l'on souhaite consulter
    * 
    * @return L'identifiant d'archivage de l'archive que l'on souhaite consulter
    */
   public final String getIdArchivage() {
      return idArchivage;
   }

   
   /**
    * L'identifiant d'archivage de l'archive que l'on souhaite consulter
    * 
    * @param idArchivage L'identifiant d'archivage de l'archive que l'on souhaite consulter
    */
   public final void setIdArchivage(String idArchivage) {
      this.idArchivage = idArchivage;
   }

   
}