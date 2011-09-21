package fr.urssaf.image.sae.webservices.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cirtil.www.saeservice.ArchivageUnitaire;
import fr.cirtil.www.saeservice.ArchivageUnitaireResponse;
import fr.cirtil.www.saeservice.MetadonneeType;
import fr.urssaf.image.sae.services.capture.SAECaptureService;
import fr.urssaf.image.sae.services.capture.exception.SAECaptureException;
import fr.urssaf.image.sae.webservices.exception.CaptureAxisFault;
import fr.urssaf.image.sae.webservices.service.WSCaptureService;
import fr.urssaf.image.sae.webservices.service.factory.ObjectArchivageUnitaireFactory;

/**
 * Implémentation de {@link WSCaptureService}<br>
 * L'implémentation est annotée par {@link Service}
 * 
 */
@Service
public class WSCaptureServiceImpl implements WSCaptureService {

   @Autowired
   private SAECaptureService captureService;

   /**
    * {@inheritDoc}
    * 
    */
   @Override
   public final ArchivageUnitaireResponse archivageUnitaire(
         ArchivageUnitaire request) throws CaptureAxisFault {

      // vérification que la liste des métadonnées n'est pas vide
      if (request.getArchivageUnitaire().getMetadonnees().getMetadonnee() == null) {

         throw new CaptureAxisFault("CaptureMetaDonneesVide",
               "Archivage impossible. La liste des métadonnées est vide.");
      }

      ArchivageUnitaireResponse response;

      URI ecdeURL = URI.create(request.getArchivageUnitaire().getEcdeUrl()
            .getEcdeUrlType().toString());

      Map<String, String> metadatas = new HashMap<String, String>();

      for (MetadonneeType metadonnee : request.getArchivageUnitaire()
            .getMetadonnees().getMetadonnee()) {

         metadatas.put(metadonnee.getCode().getMetadonneeCodeType(), metadonnee
               .getValeur().getMetadonneeValeurType());
      }

      UUID uuid;
      try {
         uuid = captureService.capture(metadatas, ecdeURL);
      } catch (SAECaptureException e) {
         throw new CaptureAxisFault("ErreurInterneCapture", e);
      }

      response = ObjectArchivageUnitaireFactory
            .createArchivageUnitaireResponse(uuid);

      return response;
   }

}