/**
 * 
 */
package science.mrcuijt.jaxws.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class WSDLParserUtil {

	private static final Logger logger = LoggerFactory.getLogger(WSDLParserUtil.class);

	public static void main(String[] args) {
		try {
			String endpoint = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?WSDL";
			endpoint = "http://ws.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";
			endpoint = "http://ws.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?WSDL";
			endpoint = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?WSDL";
//			endpoint = "http://www.gcomputer.net/webservices/knowledge.asmx?WSDL";
			WSDLParserUtil.parseWSDL(new URL(endpoint));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private static URL url = null;

	public static QName getServiceQName(String url) throws MalformedURLException {
		return getServiceQName(new URL(url));
	}
	
	public static QName getServiceQName(URL url) {
		WSDLParserUtil.url = url;
		return getServiceQName();
	}
	
	private static QName getServiceQName() {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(url);
			Element rootElem = document.getRootElement();
			Attribute targetNamespace = rootElem.attribute("targetNamespace");
			logger.info(targetNamespace.getStringValue());
			Element service = rootElem.element("service");
			if (service != null) {
				Attribute serviceName = service.attribute("name");
				logger.info(serviceName.getStringValue());
				return new QName(targetNamespace.getStringValue(), serviceName.getStringValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Service getService(String url) throws MalformedURLException {
		Service service = null;
		service = getService(new URL(url));
		return service;
	}

	public static Service getService(URL url) {
		WSDLParserUtil.url = url;
		QName qname = getServiceQName();
		if (qname == null) {
			logger.error("没有有效服务");
			return null;
		}
		Service service = Service.create(url, qname);
		return service;
	}

	public static void parseWSDL(URL url) {
		WSDLParserUtil.url = url;
		parseAvailableServicePort();
		parseAvailableService();
	}

	public static void parseWSDL(String url) throws MalformedURLException {
		parseWSDL(new URL(url));
	}

	private static void parseAvailableServicePort() {
		Service service = getService(url);
		if (service == null) {
			logger.error("没有有效服务");
			return;
		}
		Iterator<QName> iter = service.getPorts();
		while (iter.hasNext()) {
			QName q = iter.next();
			logger.info("{}", q);
		}
	}

	private static void parseAvailableService() {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(url);
			Element rootElem = document.getRootElement();
			logger.info(rootElem.getName());
			Element portType = rootElem.element("portType");
			logger.info(portType.getName());
			Iterator<Element> iter = portType.elementIterator();
			while (iter.hasNext()) {
				Element elem = iter.next();
				logger.info("{} \t {}", elem.getName(), elem.attribute("name").getValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
