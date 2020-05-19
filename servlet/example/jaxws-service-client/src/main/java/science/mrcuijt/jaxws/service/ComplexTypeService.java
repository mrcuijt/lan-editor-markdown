/**
 * 
 */
package science.mrcuijt.jaxws.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import science.mrcuijt.jaxws.domain.Region;

/**
 * @author Administrator
 *
 */
@WebService(name = "ComplexTypeService", targetNamespace = "http://service.jaxws.mrcuijt.science", wsdlLocation = "http://localhost:8080/ComplexTypeService?wsdl")
public interface ComplexTypeService {

	@WebMethod
	@WebResult(name = "string", targetNamespace = "http://service.jaxws.mrcuijt.science")
	public List<String> getList();

	@WebMethod
	@WebResult(name = "region", targetNamespace = "http://service.jaxws.mrcuijt.science")
	public List<Region> getRegionList(
			@WebParam(name="demo") 
			String demo);

	@WebMethod
	public String sayHello();

	@WebMethod
	public byte[] getFile();

	@WebMethod
	public byte[] getFileByName(String file);

}
