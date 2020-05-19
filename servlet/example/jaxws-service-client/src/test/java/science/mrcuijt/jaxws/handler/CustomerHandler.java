/**
 * 
 */
package science.mrcuijt.jaxws.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.domain.JaxBean;
import science.mrcuijt.jaxws.util.SoapUtil;

/**
 * @author Administrator
 *
 */
public class CustomerHandler implements SOAPHandler<SOAPMessageContext> {

	private static final Logger logger = LoggerFactory.getLogger(LoggingHandler.class);

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		// https://stackoverflow.com/questions/30129827/add-http-headers-to-jax-ws-service-response
		boolean response = ((Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY)).booleanValue();

		if (!response) {
			try {
				context.getMessage().writeTo(System.out);
				System.out.println();

				SOAPMessage soapMessage = context.getMessage();

				String data = SoapUtil.convertToString(soapMessage.getSOAPBody());

				logger.info(data);

				ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());

				// 修改返回结果到可解析状态
				JAXBContext jc = JAXBContext.newInstance(JaxBean.class);
				// create an Unmarshaller
				Unmarshaller u = jc.createUnmarshaller();
				// unmarshal a po instance document into a tree of Java content
				// objects composed of classes from the primer.po package.
				JAXBElement<JaxBean> poElement = (JAXBElement<JaxBean>) u.unmarshal(new StreamSource(bais),
						JaxBean.class);

				JaxBean demo = poElement.getValue();

				logger.info("{}", demo);
				logger.info("{}", demo.getGetSupportProvinceResult());
				logger.info("{}", demo.getGetSupportProvinceResult().getString());

			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return true;
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
