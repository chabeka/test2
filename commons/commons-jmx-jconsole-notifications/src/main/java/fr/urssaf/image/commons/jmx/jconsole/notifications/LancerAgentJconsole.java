package fr.urssaf.image.commons.jmx.jconsole.notifications;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import fr.urssaf.image.commons.jmx.jconsole.notifications.mbean.PremierNotification;

/**
 *  Classe permettant de consomer les différents Managed Bean 
 *  dans le client JMX, Jconsole. 
 *
 */
public class LancerAgentJconsole {

  public static void main(String[] args) {
     try {
        
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        LocateRegistry.createRegistry(9998);
        ObjectName name = new ObjectName("fr.urssaf.image.commons.jmx.mbean.PremierNotification" +
        		                             ":type=PremierNotificationMBean");
        
        PremierNotification premier= new PremierNotification();
        
        mbs.registerMBean(premier, name);
        
        String serviceUrl = "service:jmx:rmi:///jndi/rmi://localhost:9998/server";
        JMXServiceURL url = new JMXServiceURL(serviceUrl);
        
        JMXConnectorServer jmxConnector = JMXConnectorServerFactory.
                                                                 newJMXConnectorServer(url, null, mbs);
      
        jmxConnector.start();
        
        while (true) {
           Thread.sleep(1500);
        }
        
     } catch (Exception e) {
         e.printStackTrace();
     } 
  }
}