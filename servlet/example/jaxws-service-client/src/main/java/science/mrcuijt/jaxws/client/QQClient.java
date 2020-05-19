/**
 * 
 */
package science.mrcuijt.jaxws.client;

import java.net.MalformedURLException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.service.QQCheckOnline;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class QQClient {

	private static final Logger logger = LoggerFactory.getLogger(QQClient.class);

	public static void main(String[] args) {
		try {

			String endpoint = "http://ws.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";
			endpoint = "file:///D:/var/qqOnlineWebService.wsdl";
			Service service = WSDLParserUtil.getService(endpoint);
			QName qname = new QName("http://WebXml.com.cn/", "qqOnlineWebServiceSoap");
			QQCheckOnline client = service.getPort(qname, QQCheckOnline.class);
//			((BindingProvider) client).getBinding().setHandlerChain(newHandlerChain);
//			logger.info("service class {}", client.getClass());
			String result = client.qqCheckOnline("8409035");
			String message = "";
			switch (result) {
			case "Y":
				message = "在线";
				break;
			case "N":
				message = "离线";
				break;
			case "E":
				message = "QQ号码错误";
				break;
			case "A":
				message = "商业用户验证失败";
				break;
			case "V":
				message = "免费用户超过数量";
				break;
			}
			System.out.println(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
