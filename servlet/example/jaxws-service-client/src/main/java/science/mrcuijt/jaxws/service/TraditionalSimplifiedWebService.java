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
@WebService(name = "TraditionalSimplifiedWebService", targetNamespace = "http://webxml.com.cn/", wsdlLocation = "http://ws.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?WSDL")
public interface TraditionalSimplifiedWebService {

	@WebMethod
	@WebResult(name = "toSimplifiedChineseResult", targetNamespace = "http://webxml.com.cn/")
	public String toSimplifiedChinese(
			@WebParam(name = "sText", targetNamespace = "http://webxml.com.cn/") String string);

	@WebMethod
	@WebResult(name = "toTraditionalChineseResult", targetNamespace = "http://webxml.com.cn/")
	public String toTraditionalChinese(
			@WebParam(name = "sText", targetNamespace = "http://webxml.com.cn/") String string);

}
