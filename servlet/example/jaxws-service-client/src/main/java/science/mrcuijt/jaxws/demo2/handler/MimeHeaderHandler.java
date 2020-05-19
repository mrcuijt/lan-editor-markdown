/**
 * 
 */
package science.mrcuijt.jaxws.demo2.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class MimeHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger logger = LoggerFactory.getLogger(MimeHeaderHandler.class);

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		// https://stackoverflow.com/questions/30129827/add-http-headers-to-jax-ws-service-response
		boolean request = ((Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();

		if (request) {
			modifyDemo(context);
		}
		return true;
	}

	public static void modifyDemo(SOAPMessageContext context) {
		SOAPMessage message = context.getMessage();
//		String[] header = message.getMimeHeaders().getHeader("Host");
//		logger.info("{}", header);
//		message.getMimeHeaders().removeHeader("Host");
//		header = message.getMimeHeaders().getHeader("Host");
//		logger.info("{}", header);
//		message.getMimeHeaders().addHeader("Host", "www.webxml.com.cn");
//		message.getMimeHeaders().addHeader("Demo", "www.webxml.com.cn");
//		header = message.getMimeHeaders().getHeader("Host");
//		logger.info("{}", header);
		Map<String, List<String>> headers = (Map<String, List<String>>) context
				.get(MessageContext.HTTP_REQUEST_HEADERS);
		for (String key : headers.keySet()) {
			logger.info(" key : {} value : {}", key, headers.get(key));
		}
		logger.info("{}", context);
		Iterator iter = message.getMimeHeaders().getAllHeaders();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof MimeHeader) {
				MimeHeader header = (MimeHeader) obj;
				logger.info("{} \t {}", header.getName(), header.getValue());
			}
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
