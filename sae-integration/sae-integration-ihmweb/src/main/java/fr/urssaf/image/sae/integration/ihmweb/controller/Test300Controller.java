package fr.urssaf.image.sae.integration.ihmweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.urssaf.image.sae.integration.ihmweb.formulaire.RechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.formulaire.TestWsRechercheFormulaire;
import fr.urssaf.image.sae.integration.ihmweb.modele.CodeMetadonneeList;
import fr.urssaf.image.sae.integration.ihmweb.modele.TestStatusEnum;
import fr.urssaf.image.sae.integration.ihmweb.service.referentiels.ReferentielMetadonneesService;


/**
 * Test 300<br>
 * <br>
 * Test libre de la recherche => pas de résultat attendu
 */
@Controller
@RequestMapping(value = "test300")
public class Test300Controller extends AbstractTestWsController<TestWsRechercheFormulaire> {

   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final String getNumeroTest() {
      return "300";
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final TestWsRechercheFormulaire getFormulairePourGet() {
      
      TestWsRechercheFormulaire formulaire = new TestWsRechercheFormulaire();
      RechercheFormulaire formRecherche = formulaire.getRecherche();
      
      formRecherche.setRequeteLucene(getCasTest().getLuceneExemple());
      
      CodeMetadonneeList codesMeta = ReferentielMetadonneesService.getMetadonneesExemplePourRecherche();
      formRecherche.setCodeMetadonnees(codesMeta);
      formRecherche.getResultats().setStatus(TestStatusEnum.SansStatus);

      return formulaire;
      
   }
   
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected final void doPost(TestWsRechercheFormulaire formulaire) {
      recherche(
            formulaire.getUrlServiceWeb(),
            formulaire.getRecherche());
   }
   
   
   private void recherche(
         String urlWebService,
         RechercheFormulaire formulaire) {
      
      getRechercheTestService().appelWsOpRechercheTestLibre(
            urlWebService, 
            formulaire);
      
   }
   
 
}