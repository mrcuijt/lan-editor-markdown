/**
 * 
 */
package science.mrcuijt.jaxws.client;

import java.net.MalformedURLException;
import java.util.List;

import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import science.mrcuijt.jaxws.domain.Region;
import science.mrcuijt.jaxws.service.ComplexTypeService;
import science.mrcuijt.jaxws.util.WSDLParserUtil;

/**
 * @author Administrator
 *
 */
public class ComplexTypeServiceClient {

	private static Logger logger = LoggerFactory.getLogger(ComplexTypeServiceClient.class);

	public static void main(String[] args) throws MalformedURLException {

		String endpoint = "";

		endpoint = "http://localhost:8080/ComplexTypeService?wsdl";

		endpoint = "file:///D:/var/ComplexTypeService.wsdl";

		Service service = WSDLParserUtil.getService(endpoint);

		ComplexTypeService client = service.getPort(ComplexTypeService.class);

		List<String> list = client.getList();

		for (String string : list) {
			logger.info(string);
		}

		List<Region> regionList = client.getRegionList("Demo");
		for (Region region : regionList) {
			logger.info("{}", region.getAlias());
		}
		
		
	}

}
