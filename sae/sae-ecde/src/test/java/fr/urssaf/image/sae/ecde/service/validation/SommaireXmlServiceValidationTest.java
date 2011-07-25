package fr.urssaf.image.sae.ecde.service.validation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.ecde.exception.EcdeXsdException;
import fr.urssaf.image.sae.ecde.service.SommaireXmlService;


/**
 * Classe permettant de tester l'aspect sur la validation des paramètres d'entree
 * des méthodes de la classe SommaireXmlServiceValidation
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-sae-ecde.xml")
@SuppressWarnings({"PMD.MethodNamingConventions","PMD.TooManyMethods", "PMD"})
public class SommaireXmlServiceValidationTest {

   @Autowired
   private SommaireXmlService service;
   
   // utilisation pour la convertion
   private static InputStream input1;
   
   private static File inputFile;
   
   private static final String MESSAGE_INATTENDU = "message inattendu";
   
   
   @BeforeClass
   public static void init() throws URISyntaxException, FileNotFoundException {
      input1 = null;
      inputFile = null;
   }
   
   // inputStream à null
   @Test
   public void readSommaireXml_failure() throws EcdeXsdException {
      try {
         service.readSommaireXml(input1);
      }catch (IllegalArgumentException e) {
         assertEquals(MESSAGE_INATTENDU, "L'argument input doit être renseigné.", e.getMessage());
      }
   }
   
   // inputFile à null
   @Test
   public void readSommaireXml_failure_file() throws EcdeXsdException {
      try {
         service.readSommaireXml(inputFile);
      }catch (IllegalArgumentException e) {
         assertEquals(MESSAGE_INATTENDU, "L'argument input doit être renseigné.", e.getMessage());
      }
   }   
   
   
}