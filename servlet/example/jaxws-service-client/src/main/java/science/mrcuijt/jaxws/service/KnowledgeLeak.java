/**
 * 
 */
package science.mrcuijt.jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Administrator
 *
 */
@WebService(name = "KnowledgeLeak", targetNamespace = "http://webservices.gcomputer.net/", wsdlLocation = "http://www.gcomputer.net/webservices/knowledge.asmx?WSDL")
public interface KnowledgeLeak {

	@WebMethod
	@WebResult(name = "AboutKnowledgeLeakResult", targetNamespace = "http://webservices.gcomputer.net/")
	public String AboutKnowledgeLeak();

}
