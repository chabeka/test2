package com.docubase.dfce.toolkit.base;

import static junit.framework.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import net.docubase.toolkit.exception.ObjectAlreadyExistsException;
import net.docubase.toolkit.exception.ged.ExceededSearchLimitException;
import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.base.Base.DocumentCreationDateConfiguration;
import net.docubase.toolkit.model.base.Base.DocumentOverlayFormConfiguration;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.base.CategoryDataType;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.reference.Category;
import net.docubase.toolkit.model.search.ChainedFilter;
import net.docubase.toolkit.model.search.SearchResult;
import net.docubase.toolkit.service.Authentication;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.ged.SearchService.MetaData;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MultibaseTest extends AbstractBaseTestCase {
   private static final String URL = "http://cer69-ds4int:8080/dfce-webapp/toolkit/";

   private static final String CATA = "MBCode Fournisseur";
   private static final String CATB = "MBNo Serie";
   private static final String CATC = "Prix Vente";
   private static final String CATD = "Facultatif";

   private static Base base1;
   private static Base base2;

   private static final String BASE1 = "BASE 1";
   private static final String BASE2 = "BASE 2";

   private static List<Document> storedDocs;

   @BeforeClass
   public static void setUp() {
      Authentication.openSession(ADM_LOGIN, ADM_PASSWORD, URL);
      storedDocs = new ArrayList<Document>();

      /*
       * base1 et 2 ont les 2 CATA, CATB et CATC mais pas dans le m�me ordre (on
       * verra que cel� n'a pas d'importance) base2 est la seule � avoir CATD.
       */
      base1 = createBase(BASE1, "Base no 1 - dom1", new String[] { CATA, CATC, CATB });
      base2 = createBase(BASE2, "Base no 2 - dom1", new String[] { CATA, CATB, CATC, CATD });
   }

   /**
    * @see TestCase#tearDown()
    */
   @AfterClass
   public static void tearDown() {
      for (Document document : storedDocs) {
         ServiceProvider.getStoreService().deleteDocument(document.getUuid());
      }
      deleteBase(base1);
      deleteBase(base2);

      Authentication.closeSession();
   }

   protected static Base createBase(String baseId, String description, String[] catNames) {

      Base base = ServiceProvider.getBaseAdministrationService().getBase(baseId);

      if (base != null) {
         deleteBase(base);
      }
      base = ToolkitFactory.getInstance().createBase(baseId);

      // D�clare une date de cr�ation disponible mais optionnell
      base.setDocumentCreationDateConfiguration(DocumentCreationDateConfiguration.OPTIONAL);
      // Pas de fond de page
      base.setDocumentOverlayFormConfiguration(DocumentOverlayFormConfiguration.NONE);
      // Pas de groupe de document
      base.setDocumentOwnerDefault(Base.DocumentOwnerType.PUBLIC);
      // Le propri�taire d'un document n'est pas modifiable � post�riori de
      // son injection
      base.setDocumentOwnerModify(false);

      for (int i = 0; i < catNames.length; i++) {
         Category category = ServiceProvider.getStorageAdministrationService()
               .findOrCreateCategory(catNames[i], CategoryDataType.STRING);

         BaseCategory baseCategory = ToolkitFactory.getInstance()
               .createBaseCategory(category, true);
         baseCategory.setMaximumValues((short) 10);
         baseCategory.setEnableDictionary(false);

         base.addBaseCategory(baseCategory);
      }

      try {
         ServiceProvider.getBaseAdministrationService().createBase(base);
      } catch (ObjectAlreadyExistsException e) {
         e.printStackTrace();
         fail("base : " + base.getBaseId() + " already exists");
      }
      ServiceProvider.getBaseAdministrationService().startBase(base);

      return base;
   }

   @Test
   public void testSimple() throws ExceededSearchLimitException, IOException {

      /*
       * On stocke le "m�me" doc sur BASE1 et BASE2.
       */
      Map<String, String> catValues = new HashMap<String, String>();
      catValues.put(CATA, "Docubase");
      catValues.put(CATB, "EE35");
      catValues.put(CATC, "500Euros");

      storeDoc(base1, "doc1", catValues);
      storeDoc(base2, "doc2", catValues);

      /*
       * On v�rifie qu'on les trouve individuellement en requ�te monobase.
       */
      Category categoryA = ServiceProvider.getStorageAdministrationService().getCategory(CATA);
      String query1 = categoryA.getFormattedName() + ":" + catValues.get(CATA);
      searchMono(base1, query1, 10, 1);

      String query2 = categoryA.getFormattedName() + ":" + catValues.get(CATA);
      searchMono(base2, query2, 10, 1);

      /*
       * En requ�te multibase, on doit en trouver 2. (sauf si il y a une autre
       * base dans le syst�me) Et ce que l'on interroge la base1 ou la base2
       */
      List<Document> docs = null;
      String query = query2;
      docs = searchMulti(base1, query, 1000, 2);
      assertEquals(2, docs.size());
      docs = searchMulti(base2, query, 1000, 2);
      assertEquals(2, docs.size());

      /*
       * Meme recherche en passant par base1
       */

      /*
       * docs est ici alors une solution 'multibase'. On va chercher � extraire
       * les documents
       */
      Set<String> baseNames = new HashSet<String>();
      for (int i = 0; i < docs.size(); i++) {
         Document doc = docs.get(i);
         System.out.println("doc " + doc.getTitle() + " est dans la base: " + doc.getBaseId());
         // On v�rifie bien que dans cette liste de solutions de 2 document,
         // les 2 viennent d'une base diff�rente
         assertTrue(baseNames.add(doc.getBaseId()));

         // On r�ussit bien � extraire le fichier
         InputStream documentFile = ServiceProvider.getStoreService().getDocumentFile(doc);

         assertNotNull(documentFile);
         documentFile.close();
      }

      /*
       * On remarque que l'on peut revenir en requ�te monobase en pr�cisant
       * explicitement la base dans les filtres
       */
      ChainedFilter chainedFilter = ToolkitFactory.getInstance().createChainedFilter()
            .addTermFilter(MetaData.BASEUUID.getFormattedName(), base1.getUuid().toString());
      docs = searchMulti(base1, query, 50, 1, chainedFilter);
   }

   @Test(expected = ExceededSearchLimitException.class)
   public void testExceededSearchLimit() throws ExceededSearchLimitException {
      Category category = ServiceProvider.getStorageAdministrationService().getCategory(CATA);
      String query = category.getFormattedName() + ":Docubase";
      ServiceProvider.getSearchService().multiBaseSearch(query, 100, null, 50000);
   }

   private static void storeDoc(Base target, String title, Map<String, String> catValues) {
      Document document = ToolkitFactory.getInstance().createDocumentTag(target);

      document.setTitle(title);
      document.setType("PDF");

      Set<BaseCategory> baseCategories = target.getBaseCategories();
      for (BaseCategory baseCategory : baseCategories) {
         System.out.println("baseCategory : " + baseCategory.getName());
      }

      for (Map.Entry<String, String> ent : catValues.entrySet()) {
         System.out.println(ent.getKey());
         BaseCategory baseCategory = target.getBaseCategory(ent.getKey());
         document.addCriterion(baseCategory, ent.getValue());
      }

      File newDoc = getFile("doc1.pdf", AbstractBaseTestCase.class); // le

      Document storeDoc = storeDoc(document, newDoc, true);
      if (document != null) {
         storedDocs.add(storeDoc);
      }
   }

   private static List<Document> searchMono(Base target, String queryTxt, int limit,
         Integer nbExpectedResults, ChainedFilter chainedFilter)
         throws ExceededSearchLimitException {
      SearchResult searchResult = ServiceProvider.getSearchService().search(queryTxt, limit,
            target, chainedFilter);
      if (nbExpectedResults != null) {
         assertEquals((int) nbExpectedResults, searchResult.getTotalHits());
      }
      return searchResult.getDocuments();
   }

   private static List<Document> searchMono(Base target, String queryTxt, int limit,
         Integer nbExpectedResults) throws ExceededSearchLimitException {
      return searchMono(target, queryTxt, limit, nbExpectedResults, null);
   }

   private static List<Document> searchMulti(Base target, String queryTxt, int limit,
         Integer nbExpectedResults) throws ExceededSearchLimitException {
      return searchMulti(target, queryTxt, limit, nbExpectedResults, null);
   }

   private static List<Document> searchMulti(Base target, String queryTxt, int limit,
         Integer nbExpectedResults, ChainedFilter chainedFilter)
         throws ExceededSearchLimitException {
      SearchResult searchResult = ServiceProvider.getSearchService().multiBaseSearch(queryTxt,
            limit, chainedFilter);
      if (nbExpectedResults != null) {
         assertEquals((int) nbExpectedResults, searchResult.getTotalHits());
      }
      return searchResult.getDocuments();
   }
}