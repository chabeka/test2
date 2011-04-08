package fr.urssaf.image.sae.vi.service;

import java.net.URI;
import java.security.KeyStore;
import java.security.cert.X509CRL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang.time.DateFormatUtils;
import org.w3c.dom.Element;

import fr.urssaf.image.sae.saml.data.SamlAssertionData;
import fr.urssaf.image.sae.saml.exception.SamlFormatException;
import fr.urssaf.image.sae.saml.exception.signature.SamlSignatureException;
import fr.urssaf.image.sae.saml.service.SamlAssertionVerificationService;
import fr.urssaf.image.sae.vi.exception.VIAppliClientException;
import fr.urssaf.image.sae.vi.exception.VIFormatTechniqueException;
import fr.urssaf.image.sae.vi.exception.VIInvalideException;
import fr.urssaf.image.sae.vi.exception.VINivAuthException;
import fr.urssaf.image.sae.vi.exception.VIPagmIncorrectException;
import fr.urssaf.image.sae.vi.exception.VIServiceIncorrectException;
import fr.urssaf.image.sae.vi.exception.VISignatureException;

/**
 * Classe de validation d'une assertion SAML 2.0
 * 
 * 
 */
public class WebServiceVIValidateService {

   private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

   private final SamlAssertionVerificationService checkService;

   /**
    * instanciation de {@link SamlAssertionVerificationService}
    */
   public WebServiceVIValidateService() {

      checkService = new SamlAssertionVerificationService();
   }

   /**
    * 
    * Validation de la structure XML de l'assertion et de sa signature
    * électronique du jeton SAML
    * 
    * @param identification
    *           jeton SAML
    * @param keystore Les
    *           certificats des autorités de certification qui sont reconnues
    *           pour être autorisées à délivrer des certificats de signature de
    *           VI, ainsi que leur chaîne de certification
    * @param crl
    *           les CRL
    * @throws VIFormatTechniqueException
    *            Une erreur technique sur le format du VI a été détectée
    * @throws VISignatureException
    *            La signature électronique du VI est incorrecte
    */
   public final void validate(
      Element identification, 
      KeyStore keystore,
      List<X509CRL> crl)
   throws 
      VIFormatTechniqueException, 
      VISignatureException {

      try {
         checkService.verifierAssertion(identification, keystore, crl);
      } catch (SamlFormatException e) {
         throw new VIFormatTechniqueException(e);
      } catch (SamlSignatureException e) {
         throw new VISignatureException(e);
      }

   }

   /**
    * Validation supplémentaires des informations extraites du jeton SAML
    * 
    * <ul>
    * <li>date systeme postérieure à notOnBefore</li>
    * <li>date systeme strictement antérieure à notOnOrAfte</li>
    * <li>serviceVise identique à audience</li>
    * <li>idAppliClient identique à issuer</li>
    * <li>methodAuthn2</li>
    * <li>PAGM</li>
    * </ul>
    * 
    * @param data
    *           information du jeton SAML à vérifier
    * @param serviceVise
    *           URI décrivant le service visé
    * @param idAppliClient
    *           Identifiant de l'application consommatrice du service
    * @param systemDate
    *           date du système
    * @throws VIInvalideException
    *            Le VI est invalide
    * @throws VIAppliClientException
    *            Le service visé ne correspond pas au service indiqué dans
    *            l'assertion
    * @throws VINivAuthException
    *            Le niveau d'authentification initial n'est pas conforme au
    *            contrat d'interopérabilité
    * @throws VIPagmIncorrectException
    *            Le ou les PAGM présents dans le VI sont invalides
    * @throws VIServiceIncorrectException
    *            Le service visé ne correspond pas au service indiqué dans
    *            l'assertion
    */
   public final void validate(SamlAssertionData data, URI serviceVise,
         String idAppliClient, Date systemDate) throws VIInvalideException,
         VIAppliClientException, VINivAuthException, VIPagmIncorrectException,
         VIServiceIncorrectException {

      // la date systeme doit être postérieure à NotOnBefore
      Date notOnBefore = data.getAssertionParams().getCommonsParams()
            .getNotOnBefore();

      if (systemDate.compareTo(notOnBefore) < 0) {

         Map<String, String> args = new HashMap<String, String>();
         args.put("0", DateFormatUtils.format(notOnBefore, DATE_PATTERN));
         args.put("1", DateFormatUtils.format(systemDate, DATE_PATTERN));

         String message = "L'assertion n'est pas encore valable: elle ne sera active qu'à partir de ${0} alors que nous sommes le ${1}";

         throw new VIInvalideException(StrSubstitutor.replace(message, args));
      }

      // la date systeme doit être strictement antérieure à NotOnOrAfter
      Date notOnOrAfter = data.getAssertionParams().getCommonsParams()
            .getNotOnOrAfter();
      if (systemDate.compareTo(notOnOrAfter) >= 0) {

         Map<String, String> args = new HashMap<String, String>();
         args.put("0", DateFormatUtils.format(notOnOrAfter, DATE_PATTERN));
         args.put("1", DateFormatUtils.format(systemDate, DATE_PATTERN));

         String message = "L'assertion a expirée : elle n'était valable que jusqu’au ${0}, hors nous sommes le ${1}";

         throw new VIInvalideException(StrSubstitutor.replace(message, args));

      }

      // serviceVise doit être égal à Audience

      if (!serviceVise.equals(data.getAssertionParams().getCommonsParams()
            .getAudience())) {

         throw new VIServiceIncorrectException(serviceVise, data
               .getAssertionParams().getCommonsParams().getAudience());
      }

      // idAppliClient doit être égal à Issuer
      if (!idAppliClient.equals(data.getAssertionParams().getCommonsParams()
            .getIssuer())) {

         throw new VIAppliClientException(data.getAssertionParams()
               .getCommonsParams().getIssuer());
      }

      // MethodAuth2 doit être égal à
      // 'urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified'
      if (!VIConfiguration.METHOD_AUTH2.equals(data.getAssertionParams()
            .getMethodAuthn2())) {

         throw new VINivAuthException(data.getAssertionParams()
               .getMethodAuthn2());
      }

      // les PAGMS doivent exister
      // TODO vérification des PAGM
      if (CollectionUtils.isEmpty(data.getAssertionParams().getCommonsParams()
            .getPagm())) {

         throw new VIPagmIncorrectException();
      }
   }
}