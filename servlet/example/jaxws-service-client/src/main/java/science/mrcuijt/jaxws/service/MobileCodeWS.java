/**
 * 
 */
package science.mrcuijt.jaxws.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Administrator
 *
 */
@WebService(name = "MobileCodeWS", targetNamespace = "http://WebXml.com.cn/", wsdlLocation = "http://ws.webxml.com.cn/webservices/MobileCodeWS.asmx?WSDL")
public interface MobileCodeWS {

	@WebMethod
	@WebResult(name = "getMobileCodeInfoResult", targetNamespace = "http://WebXml.com.cn/")
	public List<String> getDatabaseInfo();

}
