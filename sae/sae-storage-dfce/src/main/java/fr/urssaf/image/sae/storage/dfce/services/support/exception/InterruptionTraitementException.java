package fr.urssaf.image.sae.storage.dfce.services.support.exception;

import java.text.MessageFormat;

import fr.urssaf.image.sae.storage.dfce.services.support.model.InterruptionTraitementConfig;

/**
 * Exception levée lorsque la reprise d'un traitement après une interruption a
 * échoué
 * 
 * 
 */
public class InterruptionTraitementException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   private static final String EXCEPTION_MESSAGE = "Après une déconnexion DFCE programmée à {0} il est impossible de reprendre le traitement après {1} secondes et {2} tentatives.";

   private final InterruptionTraitementConfig interruption;

   /**
    * 
    * @param interruption
    *           configuration de l'arrêt du traitement
    * @param cause
    *           cause de l'exception
    */
   public InterruptionTraitementException(
         InterruptionTraitementConfig interruption, Throwable cause) {

      super(cause);
      this.interruption = interruption;

   }

   /**
    * 
    * {@inheritDoc} <br>
    * <br>
    * Le message est formaté sur le modèle {@value #EXCEPTION_MESSAGE}
    * <ul>
    * <li>{0} : <code>{@link InterruptionTraitementConfig#getStart()}</code></li>
    * <li>{1} : <code>{@link InterruptionTraitementConfig#getDelay()}</code></li>
    * <li>{2} :
    * <code>{@link InterruptionTraitementConfig#getTentatives()}</code></li>
    * </ul>
    * 
    * 
    */
   @Override
   public final String getMessage() {

      String message = MessageFormat.format(EXCEPTION_MESSAGE,
            this.interruption.getStart(), this.interruption.getDelay(),
            this.interruption.getTentatives());

      return message;
   }

}
