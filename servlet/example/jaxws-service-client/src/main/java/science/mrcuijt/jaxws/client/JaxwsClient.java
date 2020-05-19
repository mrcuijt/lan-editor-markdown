/**
 * 
 */
package science.mrcuijt.jaxws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.RespectBindingFeature;

import com.sun.xml.ws.api.WSService;
import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.wsdl.writer.document.Service;

import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class JaxwsClient {

	public static void main(String[] args) throws MalformedURLException {

		String endpoint = "";

		endpoint = "file:///D:/var/WeatherWebService.wsdl";

		URL url = new URL(endpoint);

		QName serviceName = WSDLParserUtil.getServiceQName(url);

		RespectBindingFeature feature = new RespectBindingFeature();

		WebServiceFeatureList featureList = new WebServiceFeatureList();
		
		featureList.add(feature);
		
//		WSService service = new WSServiceDelegate(url, serviceName, (Class<? extends javax.xml.ws.Service>) Service.class, feature);
//
//		service.create();

	}

}
