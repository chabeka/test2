package com.docubase.dfce.toolkit.client.ged;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.docubase.toolkit.exception.ObjectAlreadyExistsException;
import net.docubase.toolkit.exception.ged.TagControlException;
import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.Base;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.service.ServiceProvider;
import net.docubase.toolkit.service.administration.BaseAdministrationService;
import net.docubase.toolkit.service.ged.StoreService;

import org.junit.Test;

import com.docubase.dfce.toolkit.client.AbstractDFCEToolkitClientTest;

public class StoreClientTest extends AbstractDFCEToolkitClientTest {
   private final StoreService storeService = ServiceProvider.getStoreService();
   private final BaseAdministrationService baseAdministrationService = ServiceProvider
         .getBaseAdministrationService();

   @Test
   public void testStoreDocument() throws IOException, TagControlException {
      Base base = baseAdministrationService.getBase("baseId");
      if (base == null) {
         base = ToolkitFactory.getInstance().createBase("baseId");
         try {
            baseAdministrationService.createBase(base);
         } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            fail("base : " + base.getBaseId() + " already exists");
         }
         base = ServiceProvider.getBaseAdministrationService().getBase("baseId");
      }

      Document document = ToolkitFactory.getInstance().createDocumentTag(base);
      document.setType("PDF");
      File file = new File(this.getClass().getResource("doc1.pdf").getPath());
      Document storeDocument = storeService.storeDocument(document, new FileInputStream(file));
      assertNotNull(storeDocument);
   }
}