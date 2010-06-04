package fr.urssaf.image.commons.webservice.exemple.ssl.document.generate;

import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.apache.velocity.texen.util.PropertiesUtil;

import fr.urssaf.image.commons.webservice.exemple.ssl.base.RPCSSLContextFactory;
import fr.urssaf.image.commons.webservice.generate.GenerateSourceAxis;

public class SSLGenerateSource extends GenerateSourceAxis{

	public SSLGenerateSource(String path,String url) {
		super(path,url);

	}

	public static void main(String[] args){

		HttpsURLConnection.setDefaultSSLSocketFactory(RPCSSLContextFactory.getSSLContext()
				.getSocketFactory());
		
		PropertiesUtil util = new PropertiesUtil();
		Properties prop = util.load("document.properties");

		String url = prop.getProperty("url");
		String path = prop.getProperty("path");

		SSLGenerateSource generateSource = new SSLGenerateSource(path,url);
		generateSource.generate();

	}
}