package fr.urssaf.image.sae.vi.exception;

/**
 * Une erreur a été détectée lors de la vérification du VI
 * 
 * 
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class VIVerificationException extends Exception {

   private static final long serialVersionUID = 1L;

   protected VIVerificationException(Exception exception) {
      super(exception);
   }
   
   protected VIVerificationException(String msg) {
      super(msg);
   }

   /**
    * 
    * @return code du SoapFault
    */
   public abstract String getSoapFaultCode();

   /**
    * 
    * @return message du SoapFault
    */
   public abstract String getSoapFaultMessage();

}