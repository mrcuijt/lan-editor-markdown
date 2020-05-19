/**
 * 
 */
package science.mrcuijt.jaxws.demo.handler;

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
public class WeatcherClientDemoHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger logger = LoggerFactory.getLogger(WeatcherClientDemoHandler.class);

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
			SOAPMessage message = context.getMessage();
			context.getMessage().writeTo(System.out);
			System.out.println();
			message.getSOAPPart().getEnvelope().removeNamespaceDeclaration("SOAP-ENV");
			Iterator<SOAPElement> iter = message.getSOAPBody().getChildElements();
			SOAPElement responseElem = iter.next();
			iter = responseElem.getChildElements();
			SOAPElement element = iter.next();
			iter = element.getChildElements();
			List<String> result = new ArrayList<String>();
			while (iter.hasNext()) {
				SOAPElement stringElem = iter.next();
				result.add(stringElem.getTextContent());
			}
			element.detachNode();
			for (String string : result) {
				responseElem.addChildElement("string").setTextContent(string);
			}
			message.writeTo(System.out);
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
