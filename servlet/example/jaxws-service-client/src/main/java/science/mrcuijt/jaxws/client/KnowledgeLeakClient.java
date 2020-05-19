/**
 * 
 */
package science.mrcuijt.jaxws.client;

import java.net.MalformedURLException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.service.MobileCodeWS;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class KnowledgeLeakClient {

	private static final Logger logger = LoggerFactory.getLogger(KnowledgeLeakClient.class);

	public static void main(String[] args) {
		try {

			String endpoint = "http://ws.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";
			endpoint = "file:///D:/var/MobileCodeWS.wsdl";
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://WebXml.com.cn/", "MobileCodeWSSoap");
			MobileCodeWS client = service.getPort(qname, MobileCodeWS.class);
//			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
//			logger.info("service class {}", client.getClass());
			List<String> result = client.getDatabaseInfo();
			logger.info("{}", result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
