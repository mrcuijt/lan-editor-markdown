/**
 * 
 */
package science.mrcuijt.jaxws.client;

import java.net.MalformedURLException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.service.TraditionalSimplifiedWebService;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class TraditionalSimplifiedClient {

	private static final Logger logger = LoggerFactory.getLogger(TraditionalSimplifiedClient.class);

	public static void main(String[] args) {
		try {

			String endpoint = "http://ws.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";
			endpoint = "file:///D:/var/TraditionalSimplifiedWebService.wsdl";
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://webxml.com.cn/", "TraditionalSimplifiedWebServiceSoap");
			TraditionalSimplifiedWebService client = service.getPort(qname, TraditionalSimplifiedWebService.class);
//			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
//			logger.info("service class {}", client.getClass());
			String result = client.toTraditionalChinese("中国");
			logger.info(result);
			result = client.toSimplifiedChinese("中國");
			logger.info(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
