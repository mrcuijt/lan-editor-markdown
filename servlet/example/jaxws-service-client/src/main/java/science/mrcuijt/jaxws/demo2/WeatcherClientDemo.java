/**
 * 
 */
package science.mrcuijt.jaxws.demo2;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.demo2.domain.DataSetResult.Zone;
import science.mrcuijt.jaxws.demo2.domain.JaxBean;
import science.mrcuijt.jaxws.demo2.domain.SupportDataSetResult;
import science.mrcuijt.jaxws.demo2.domain.WeatherbyCityNameResult;
import science.mrcuijt.jaxws.demo2.handler.MimeHeaderHandler;
import science.mrcuijt.jaxws.demo2.handler.WeatcherClientDemoHandler;
import science.mrcuijt.jaxws.demo2.service.WeatherWebServiceDemo;
import science.mrcuijt.jaxws.handler.LoggingHandler;
import science.mrcuijt.jaxws.util.SoapUtil;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class WeatcherClientDemo {

	private static final Logger logger = LoggerFactory.getLogger(WeatcherClientDemo.class);

	public static void main(String[] args) {
		getSupportProvinceDemo();
//		getWeatherbyCityName();
//		addHttpHeader();
//		addHttpHeaderDemo();
	}

	public static void addHttpHeaderDemo()  {
		try {
			String endpoint = "";
			endpoint = "http://ws.webxml.com.cn/webservices/WeatherWebService.asmx?wsdl";
			endpoint = "file:///D:/var/WeatherWebService.wsdl";
			WSDLParserUtil.parseWSDL(endpoint);
			Service service = WSDLParserUtil.getService(endpoint);
			QName protName = new QName("http://WebXml.com.cn/", "WeatherWebServiceSoap");
			// Add HTTP request Headers
			Map<String, List<String>> requestHeaders = new HashMap<>();
			requestHeaders.put("Host", Collections.singletonList("www.webxml.com.cn"));
			requestHeaders.put("Username", Collections.singletonList("mkyong"));
			requestHeaders.put("Password", Collections.singletonList("password"));
			
			WeatherWebServiceDemo client = service.getPort(protName, WeatherWebServiceDemo.class);
			Map<String, Object> requestContext = ((BindingProvider)client).getRequestContext();
//			requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
			
//			client.getSupportProvince();
			client.getSupportDataSet();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void addHttpHeader() {
		try {
			String endpoint = "";
			endpoint = "http://ws.webxml.com.cn/webservices/WeatherWebService.asmx?wsdl";
			endpoint = "file:///D:/var/WeatherWebService.wsdl";
			WSDLParserUtil.parseWSDL(endpoint);
			Service service = WSDLParserUtil.getService(endpoint);
			QName protName = new QName("http://WebXml.com.cn/", "WeatherWebServiceSoap");
			Dispatch<SOAPMessage> disp = service.createDispatch(protName, SOAPMessage.class, Service.Mode.MESSAGE);
			// Add HTTP request Headers
			Map<String, List<String>> requestHeaders = new HashMap<>();
			requestHeaders.put("Host", Collections.singletonList("www.webxml.com.cn"));
			requestHeaders.put("Username", Collections.singletonList("mkyong"));
			requestHeaders.put("Password", Collections.singletonList("password"));
			disp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage message = messageFactory.createMessage();
			SOAPMessage response = disp.invoke(message);
			String result = SoapUtil.convertToString(response.getSOAPBody());
//			logger.info(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	public static void getWeatherbyCityName() {
		try {
			String endpoint = "";
			endpoint = "http://ws.webxml.com.cn/webservices/WeatherWebService.asmx?wsdl";
//			endpoint = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?WSDL";
//			endpoint = "file:///D:/var/WeatherWebService.wsdl";
			WSDLParserUtil.parseWSDL(endpoint);
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://WebXml.com.cn/", "WeatherWebServiceSoap");
			WeatherWebServiceDemo client = service.getPort(qname, WeatherWebServiceDemo.class);

			WeatcherClientDemoHandler weatcherClientDemoHandler = new WeatcherClientDemoHandler();
			List<Handler> newHandlerChain = new ArrayList<Handler>();
			LoggingHandler loggingHandler = new LoggingHandler();
			MimeHeaderHandler mimeHeaderHandler = new MimeHeaderHandler();
//			newHandlerChain.add(weatcherClientDemoHandler);
			newHandlerChain.add(loggingHandler);
//			newHandlerChain.add(mimeHeaderHandler);
			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
			Map<String, Object> req_ctx = ((BindingProvider) client).getRequestContext();
			Map<String, List> headers = (Map<String, List>) req_ctx.get(MessageContext.HTTP_REQUEST_HEADERS);
			if (headers != null) {
				for (String key : headers.keySet()) {
					logger.info(key);
				}
			}
			headers = new HashMap<>();
			headers.put("Host", Arrays.asList("www.webxml.com.cn"));
//			req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
//			JaxBean list = client.getSupportProvince();
//			logger.info("{}", list.getString());
//			client.getWeatherbyCityName("哈尔滨");
//			client.getSupportCity("大洋洲");
//			SupportDataSetResult res = client.getSupportDataSet();
//			logger.info(res.getDiffgram().getDataSetResult().getArea().get(0).getArea());
			WeatherbyCityNameResult result = client.getWeatherbyCityName("北京");
			for (String string : result.getString()) {
				logger.info(string);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void getSupportDataSetDemo() {
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
			newHandlerChain.add(weatcherClientDemoHandler);
//			newHandlerChain.add(loggingHandler);
//			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
//			JaxBean list = client.getSupportProvince();
//			logger.info("{}", list.getString());
//			client.getWeatherbyCityName("哈尔滨");
			SupportDataSetResult result = client.getSupportDataSet();
			logger.info("{}", result.getDiffgram());
			logger.info("{}", result.getDiffgram().getDataSetResult());
//			logger.info("{}", result.getDiffgram().getDataSetResult().getArea());
//			logger.info("{}", result.getDiffgram().getDataSetResult().getZone());
			for (Zone zone : result.getDiffgram().getDataSetResult().getZone()) {
				logger.info("{}", zone.getZone());
				logger.info("{}", zone.getID());
				logger.info("");
			}
//			for (Area area : result.getDiffgram().getDataSetResult().getArea()) {
//				logger.info("{}",area.getId());
//				logger.info("{}",area.getZoneId());
//				logger.info("{}",area.getArea());
//				logger.info("{}",area.getAreaCode());
//			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
			newHandlerChain.add(weatcherClientDemoHandler);
//			newHandlerChain.add(loggingHandler);
			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
			JaxBean list = client.getSupportProvince();
			logger.info("{}", list.getString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
