/**
 * 
 */
package science.mrcuijt.jaxws.client;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.domain.Province;
import science.mrcuijt.jaxws.handler.LoggingHandler;
import science.mrcuijt.jaxws.handler.PrefixsHandler;
import science.mrcuijt.jaxws.service.WeatherWebService;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class WeatherClient {

	private static final Logger logger = LoggerFactory.getLogger(WeatherClient.class);

	public static void main(String[] args) {

		try {
			String endpoint = "";
			endpoint = "http://ws.webxml.com.cn/webservices/WeatherWebService.asmx?wsdl";
//			endpoint = "file:///D:/var/WeatherWebService.wsdl";
			WSDLParserUtil.parseWSDL(endpoint);
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://WebXml.com.cn/", "WeatherWebServiceSoap");
			WeatherWebService client = service.getPort(qname, WeatherWebService.class);

			LoggingHandler loggingHandler = new LoggingHandler();
			PrefixsHandler prefixsHandler = new PrefixsHandler();
			List<Handler> newHandlerChain = new ArrayList<Handler>();
//			newHandlerChain.add(loggingHandler);
			newHandlerChain.add(prefixsHandler);
			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);

//			String[] province = client.getSupportProvince();
//			List<String> provinceList = Arrays.asList(province);
//			List<String> provinceList = client.getSupportProvince();
//			Province province = client.getSupportProvince();
//			logger.info("{}", province);
//			for (String string : province.getString()) {
//				logger.info(string);
//			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
