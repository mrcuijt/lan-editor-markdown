/**
 * 
 */
package science.mrcuijt.jaxws.demo2.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import science.mrcuijt.jaxws.demo2.domain.JaxBean;
import science.mrcuijt.jaxws.demo2.domain.SupportDataSetResult;
import science.mrcuijt.jaxws.demo2.domain.WeatherbyCityNameResult;

/**
 * @author Administrator
 *
 */
@WebService(name = "WeatherWebService", targetNamespace = "http://WebXml.com.cn/", wsdlLocation = "http://ws.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl")
public interface WeatherWebServiceDemo {

	@WebMethod
	@WebResult(name = "getSupportCityResult", targetNamespace = "http://WebXml.com.cn/")
	public List<String> getSupportCity(
			@WebParam(name = "byProvinceName", targetNamespace = "http://WebXml.com.cn/") String byProvinceName);

	@WebMethod
	@WebResult(name = "getSupportProvinceResult", targetNamespace = "http://WebXml.com.cn/")
	public JaxBean getSupportProvince();

	@WebMethod
	@WebResult(name = "getSupportDataSetResult", partName = "NewDataSet", targetNamespace = "http://WebXml.com.cn/")
	public SupportDataSetResult getSupportDataSet();

	@WebMethod
	@WebResult(name = "getWeatherbyCityNameResult", partName = "getWeatherbyCityNameResult", targetNamespace = "http://WebXml.com.cn/")
	public WeatherbyCityNameResult getWeatherbyCityName(
			@WebParam(name = "theCityName", targetNamespace = "http://WebXml.com.cn/") String theCityName);

	@WebMethod
	@WebResult(name = "getWeatherbyCityNameProResult", targetNamespace = "http://WebXml.com.cn/")
	public String getWeatherbyCityNamePro(
			@WebParam(name = "theCityName", targetNamespace = "http://WebXml.com.cn/") String theCityName,
			@WebParam(name = "theUserID", targetNamespace = "http://WebXml.com.cn/") String theUserID);

}
