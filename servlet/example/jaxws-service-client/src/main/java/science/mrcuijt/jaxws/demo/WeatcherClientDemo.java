/**
 * 
 */
package science.mrcuijt.jaxws.demo;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.demo.handler.WeatcherClientDemoHandler;
import science.mrcuijt.jaxws.demo.service.WeatherWebServiceDemo;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class WeatcherClientDemo {

	private static final Logger logger = LoggerFactory.getLogger(WeatcherClientDemo.class);

	public static void main(String[] args) {
		try {
			String endpoint = "";
			endpoint = "http://ws.webxml.com.cn/webservices/WeatherWebService.asmx?wsdl";
//			endpoint = "file:///D:/var/WeatherWebService.wsdl";
			WSDLParserUtil.parseWSDL(endpoint);
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://WebXml.com.cn/", "WeatherWebServiceSoap");
			WeatherWebServiceDemo client = service.getPort(qname, WeatherWebServiceDemo.class);
			
			WeatcherClientDemoHandler weatcherClientDemoHandler = new WeatcherClientDemoHandler();
			List<Handler> newHandlerChain = new ArrayList<Handler>();
			newHandlerChain.add(weatcherClientDemoHandler);
			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
			List<String> list = client.getSupportProvince();
			logger.info("{}", list);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
