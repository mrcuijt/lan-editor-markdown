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

import science.mrcuijt.jaxws.service.KnowledgeLeak;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class MobileCodeClient {

	private static final Logger logger = LoggerFactory.getLogger(MobileCodeClient.class);

	public static void main(String[] args) {
		try {

			String endpoint = "http://ws.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";
			endpoint = "file:///D:/var/MobileCodeWS.wsdl";
			endpoint = "http://www.gcomputer.net/webservices/knowledge.asmx?WSDL";
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://webservices.gcomputer.net/", "KnowledgeLeakSoap");
			KnowledgeLeak client = service.getPort(qname, KnowledgeLeak.class);
//			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
//			logger.info("service class {}", client.getClass());
			String result = client.AboutKnowledgeLeak();
			logger.info("{}", result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
