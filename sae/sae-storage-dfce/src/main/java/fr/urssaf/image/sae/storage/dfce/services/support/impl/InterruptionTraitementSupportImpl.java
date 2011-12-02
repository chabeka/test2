package fr.urssaf.image.sae.storage.dfce.services.support.impl;

import java.text.MessageFormat;
import java.util.Date;

import me.prettyprint.cassandra.utils.Assert;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.urssaf.image.sae.storage.dfce.services.support.InterruptionTraitementSupport;
import fr.urssaf.image.sae.storage.dfce.services.support.exception.InterruptionTraitementException;
import fr.urssaf.image.sae.storage.dfce.utils.LocalTimeUtils;
import fr.urssaf.image.sae.storage.services.StorageServiceProvider;

/**
 * Implémentation du service {@link InterruptionTraitementSupport}
 * 
 * 
 */
@Component
public class InterruptionTraitementSupportImpl implements
      InterruptionTraitementSupport {

   private final StorageServiceProvider storageProvider;

   private static final String EXCEPTION_MESSAGE = "Après une déconnexion DFCE programmée à {0} il est impossible de reprendre le traitement après {1} secondes et {2} tentatives.";

   /**
    * 
    * @param storageProvider
    *           ensemble des services de manipulation de la surcouche de DFCE
    */
   @Autowired
   public InterruptionTraitementSupportImpl(
         StorageServiceProvider storageProvider) {

      Assert.notNull(storageProvider, "'storageProvider' is required");

      this.storageProvider = storageProvider;
   }

   private static final int DELAY = 120;

   private int delay = DELAY;

   /**
    * 
    * @param delay
    *           temps d'attente en secondes entre chaque tentatives après la
    *           première
    */
   public final void setDelay(int delay) {
      this.delay = delay;
   }

   /**
    * {@inheritDoc} <br>
    * Après la première tentative le delai d'attente entre chaque tentative est
    * fixé par {@value #setDelay(int)} en secondes.<br>
    * Par défaut cette valeur est fixé à {@value #DELAY}
    * 
    */
   @Override
   public final void interruption(String start, int delay, int tentatives) {

      Date currentDate = new Date();

      interruption(currentDate, start, delay, tentatives);

   }

   protected final void interruption(Date currentDate, String startTime,
         int delay, int tentatives) {

      LocalTime startLocalTime = LocalTimeUtils.parse(startTime);

      LocalDateTime currentLocalDate = LocalDateTime
            .fromDateFields(currentDate);

      if (LocalTimeUtils.isSameTime(currentLocalDate, startLocalTime, delay)) {

         storageProvider.closeConnexion();

         String exceptionMessage = MessageFormat.format(EXCEPTION_MESSAGE,
               startTime, delay, tentatives);

         pause(delay, exceptionMessage, null, tentatives);
      }
   }

   private void pause(long delay, String exceptionMessage,
         InterruptionTraitementException lastException, int tentatives) {

      if (tentatives > 0) {

         pause(delay);

         try {

            storageProvider.openConnexion();

         } catch (Exception e) {

            InterruptionTraitementException newException = new InterruptionTraitementException(
                  exceptionMessage, new Exception());

            int newTentatives = tentatives - 1;

            pause(this.delay, exceptionMessage, newException, newTentatives);
         }

         return;
      }

      throw lastException;

   }

   private void pause(long delay) {

      try {
         Thread.sleep(delay * 1000);
      } catch (InterruptedException e) {
         throw new InterruptionTraitementException(e);
      }
   }

}