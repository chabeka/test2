package fr.urssaf.image.sae.webdemo.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.vi.schema.DroitType;
import fr.urssaf.image.sae.vi.schema.SaeJetonAuthentificationType;
import fr.urssaf.image.sae.webdemo.ControllerAssert;
import fr.urssaf.image.sae.webdemo.ControllerTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@SuppressWarnings("PMD")
public class ConnectionControllerTest extends
      ControllerTestSupport<ConnectionController> {

   @Autowired
   private ConnectionController servlet;

   private ControllerAssert<ConnectionController> controllerAssert;

   static final String SAML_FIELD = "SAMLResponse";

   private static String SAML_VALUE;

   static final String RELAY_FIELD = "RelayState";

   private static final String RELAY_VALUE = "/accueil.html";

   private static final String FORWARD_403 = "error/erreur403_viko";

   private static final String FORWARD_403_AUTH = "error/erreur403_viformatko";

   private static final String FORWARD_404 = "error/erreur404_serviceinexistant";

   @Before
   public void init() throws IOException {

      controllerAssert = new ControllerAssert<ConnectionController>(this,
            servlet);

      SAML_VALUE = FileUtils.readFileToString(new File(
            "src/test/resources/saml/ctd_rights.txt"), "UTF-8");

   }

   @Test(expected = IllegalStateException.class)
   public void connectException() {

      new ConnectionController(null);

   }

   @Test
   public void connectGet() {

      this.initGet();

      this.handle(servlet);

      assertEquals(404, this.getStatut());

   }

   @Test
   public void connectSuccess() {

      this.initPost();

      this.assertConnectSuccess(RELAY_VALUE, "accueil.html");
      this.assertConnectSuccess("/", "");

   }

   private void assertConnectSuccess(String relayValue, String redirect) {

      this.initParameter(SAML_FIELD, SAML_VALUE);
      this.initParameter(RELAY_FIELD, relayValue);

      controllerAssert.assertView("redirect:" + redirect);

      SaeJetonAuthentificationType jeton = (SaeJetonAuthentificationType) this
            .getAttributeSession("SaeJetonAuthentification");

      assertEquals("AGENT-CTD", jeton.getIdentiteUtilisateur().getNom());
      assertEquals("Prenom", jeton.getIdentiteUtilisateur().getPrenom());

      assertDroit(jeton.getDroits().getDroit().get(0),
            "GESTIONNAIREACCESCOMPLET", "URSSAF - Code organisme", "CER69");
      assertDroit(jeton.getDroits().getDroit().get(1),
            "GESTIONNAIREACCESCOMPLET", "URSSAF - Code organisme", "UR030");
      assertDroit(jeton.getDroits().getDroit().get(2), "GESTIONNAIRESRVRH",
            "URSSAF - Code organisme", "UR710");
      assertDroit(jeton.getDroits().getDroit().get(3), "GESTIONNAIRESRVRH",
            "URSSAF - Code organisme", "UR730");

      assertEquals(4, jeton.getDroits().getDroit().size());

   }

   private void assertDroit(DroitType droit, String code, String type,
         String value) {
      assertEquals(code, droit.getCode());
      assertEquals(type, droit.getPerimetre().getCodeType());
      assertEquals(value, droit.getPerimetre().getValeur());
   }

   @Test
   public void connectFailure_SAML() {

      this.initPost();

      this.assertFailure_SAML("");
      this.assertFailure_SAML(" ");
      this.assertFailure_SAML(null);

   }

   private void assertFailure_SAML(String saml) {

      this.initParameter(RELAY_FIELD, RELAY_VALUE);
      controllerAssert.assertError(SAML_FIELD, saml, "connectionForm",
            "NotEmpty");

      controllerAssert.assertView(FORWARD_403);
      assertEquals(403, this.getStatut());

   }

   @Test
   public void connectFailure_Relay() {

      this.initPost();

      this.assertFailure_Relay("");
      this.assertFailure_Relay(" ");
      this.assertFailure_Relay(null);
   }

   private void assertFailure_Relay(String relay) {

      this.initParameter(SAML_FIELD, SAML_VALUE);
      controllerAssert.assertError("relayState", relay, "connectionForm",
            "NotEmpty");

      controllerAssert.assertView(FORWARD_403);
      assertEquals(403, this.getStatut());

   }

   @Test
   public void connectFailure_service() {

      this.initPost();

      this.initParameter(SAML_FIELD, SAML_VALUE);
      this.initParameter(RELAY_FIELD, "\\service_inconnu.html");

      controllerAssert.assertView(FORWARD_404);
      assertEquals(404, this.getStatut());

   }

   @Test
   public void connectFailure_auth_noright() throws IOException {

      this.initPost();

      String saml = FileUtils.readFileToString(new File(
            "src/test/resources/saml/ctd_0_right.txt"), "UTF-8");
      assertFailure_auth(saml);

   }

   @Test
   public void connectFailure_auth_noxml() {

      this.initPost();

      assertFailure_auth(Base64.encodeBase64String("no xml".getBytes()));

   }

   @Test
   public void connectFailure_auth_nobase64() {

      this.initPost();

      assertFailure_auth("no base 64");

   }

   private void assertFailure_auth(String saml) {

      this.initParameter(SAML_FIELD, saml);
      this.initParameter(RELAY_FIELD, RELAY_VALUE);

      controllerAssert.assertView(FORWARD_403_AUTH);
      assertEquals(403, this.getStatut());

   }
}
