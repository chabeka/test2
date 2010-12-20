package fr.urssaf.image.sae.webdemo.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.urssaf.image.sae.webdemo.TestController;
import fr.urssaf.image.sae.webdemo.controller.ApplicationDemoController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionController;
import fr.urssaf.image.sae.webdemo.controller.ConnectionFailureController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-servlet.xml",
      "/applicationContext.xml" })
@SuppressWarnings("PMD")
public class ConnectionInterceptorTest {

   private ConnectionInterceptor interceptor;

   private MockHttpServletResponse response;

   private MockHttpServletRequest request;

   @Before
   public void before() {

      request = new MockHttpServletRequest();
      response = new MockHttpServletResponse();

      interceptor = new ConnectionInterceptor();
   }

   @Autowired
   private ConnectionController connectionController;

   @Autowired
   private TestController testController;

   @Test
   public void connection_success() throws Exception {

      String samlResponse = FileUtils.readFileToString(new File(
            "src/test/resources/saml/ctd_rights.xml"), "UTF-8");

      connectionController.createSession(samlResponse, request);
      assertTrue(interceptor.preHandle(request, response, testController));
   }

   @Test
   public void connect_failure() throws Exception {

      assertFalse(interceptor.preHandle(request, response, testController));
      assertEquals("connectionFailure.html", response.getRedirectedUrl());

   }

   @Autowired
   private ConnectionFailureController connectionFailureController;

   @Autowired
   private ApplicationDemoController applicationdemoController;

   @Test
   public void connect_exception() throws Exception {

      assertTrue(interceptor.preHandle(request, response, connectionController));
      assertTrue(interceptor.preHandle(request, response,
            connectionFailureController));
      assertTrue(interceptor.preHandle(request, response,
            applicationdemoController));

   }
}
