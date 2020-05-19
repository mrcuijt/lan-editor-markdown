/**
 * 
 */
package science.mrcuijt.jaxws.demo3;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.demo3.handler.WeatcherClientDemoHandler;
import science.mrcuijt.jaxws.demo3.service.WeatherWebServiceDemo;
import science.mrcuijt.jaxws.handler.LoggingHandler;
import science.mrcuijt.jaxws.demo3.domain.JaxBean;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class WeatcherClientDemo {

	private static final Logger logger = LoggerFactory.getLogger(WeatcherClientDemo.class);

	public static void main(String[] args) {
		getSupportProvinceDemo();
	}

	public static void getSupportProvinceDemo() {
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
			LoggingHandler loggingHandler = new LoggingHandler();
//			newHandlerChain.add(weatcherClientDemoHandler);
//			newHandlerChain.add(loggingHandler);
//			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
//			JaxBean list = client.getSupportProvince();
//			logger.info("{}", list.getString());
			List<String> res = client.getWeatherbyCityName("哈尔滨");
			logger.info("{}", res);
//			client.getSupportDataSet();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
