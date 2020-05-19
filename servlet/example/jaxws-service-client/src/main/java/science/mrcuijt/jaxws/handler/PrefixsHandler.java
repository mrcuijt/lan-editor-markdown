/**
 * 
 */
package science.mrcuijt.jaxws.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;

/**
 * @author Administrator
 *
 */
public class PrefixsHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger logger = LoggerFactory.getLogger(PrefixsHandler.class);

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		// https://stackoverflow.com/questions/30129827/add-http-headers-to-jax-ws-service-response
		boolean response = ((Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();

		if (!response) {
			modifyDemo(context);
		}
		return true;
	}

	public static void modifyDemo(SOAPMessageContext context) {
		try {

			List<String> list = Arrays.asList(context.getRoles().toArray(new String[context.getRoles().size()]));
			for (String string : list) {
				logger.info(string);
			}

			SOAPMessage message = context.getMessage();
			context.getMessage().writeTo(System.out);
			System.out.println();
			Iterator<SOAPElement> iter = message.getSOAPBody().getChildElements();
			iter = iter.next().getChildElements();
			SOAPElement element = iter.next();
			iter = element.getChildElements();
			
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPElement soapResponse = soapEnvelope.addChildElement("getSupportProvinceResponse", "ns2", "http://WebXml.com.cn/");
			SOAPElement result = soapResponse.addChildElement("result", "ns2");
			while(iter.hasNext()) {
				SOAPElement elem = iter.next();
				result.addChildElement("string").setTextContent(elem.getTextContent());
			}
			soapMessage.writeTo(System.out);
			System.out.println();
			context.setMessage(soapMessage);
//			System.out.println();
//			message.getSOAPPart().getEnvelope().addNamespaceDeclaration("ns2", "http://WebXml.com.cn/");
//			SOAPBody soapBody = message.getSOAPBody();
//			soapBody.addNamespaceDeclaration("ns2", "http://WebXml.com.cn/");
//			Iterator<SOAPElement> iter = soapBody.getChildElements();
//			SOAPElement elem = iter.next();
//			logger.info(elem.getTagName());
//			elem.setPrefix("ns2");
//			elem.addNamespaceDeclaration("ns2", "http://WebXml.com.cn/");
//			elem.removeAttribute("xmlns");

//			SOAPElement elm = (SOAPElement) elem.getChildElements().next();
//			elm.setPrefix("ns2");
//			elem.addNamespaceDeclaration("ns2", "http://WebXml.com.cn/");
//			context.getMessage().writeTo(System.out);
//			System.out.println();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void modifyDemo2(SOAPMessageContext context) {
		try {
			SOAPMessage message = context.getMessage();
			context.getMessage().writeTo(System.out);
			System.out.println();
			message.getSOAPPart().getEnvelope().addNamespaceDeclaration("ns2", "http://WebXml.com.cn/");
			SOAPBody soapBody = message.getSOAPBody();
			Iterator<SOAPElement> iter = soapBody.getChildElements();
			if (iter.hasNext()) {
				SOAPElement elem = iter.next();
				logger.info(elem.getTagName());
				elem.setPrefix("ns3");
				elem.addNamespaceDeclaration("ns3", "http://WebXml.com.cn/");
				elem.removeAttribute("xmlns");
//				logger.info(elem.getAttribute("xmlns"));
				iter = elem.getChildElements();
				if (iter.hasNext()) {
					SOAPElement children = iter.next();
					children.setPrefix("ns3");
				}
			}
			context.getMessage().writeTo(System.out);
			System.out.println();
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
