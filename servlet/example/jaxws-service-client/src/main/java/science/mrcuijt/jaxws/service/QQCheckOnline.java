/**
 * 
 */
package science.mrcuijt.jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Administrator
 *
 */
@WebService(name = "qqOnlineWebService", targetNamespace = "http://WebXml.com.cn/", wsdlLocation = "http://ws.webxml.com.cn/webservices/qqOnlineWebService.asmx?WSDL")
public interface QQCheckOnline {

	@WebMethod
	@WebResult(name = "qqCheckOnlineResult", targetNamespace = "http://WebXml.com.cn/")
	public String qqCheckOnline(@WebParam(name = "qqCode", targetNamespace = "http://WebXml.com.cn/") String qqCode);

}
