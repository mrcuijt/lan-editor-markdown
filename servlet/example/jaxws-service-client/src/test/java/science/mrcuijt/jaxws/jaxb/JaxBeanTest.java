/**
 * 
 */
package science.mrcuijt.jaxws.jaxb;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.bind.v2.runtime.JAXBContextImpl;

import science.mrcuijt.jaxws.demo2.domain.DataSetResult;
import science.mrcuijt.jaxws.demo2.domain.Demo;
import science.mrcuijt.jaxws.demo2.domain.SupportDataSetResult;
import science.mrcuijt.jaxws.domain.JaxBean.GetSupportProvinceResult;
import science.mrcuijt.jaxws.demo2.domain.DataSetResult.Zone;

/**
 * @author Administrator
 *
 */
public class JaxBeanTest {

	private static final Logger logger = LoggerFactory.getLogger(JaxBeanTest.class);
	
	public static void main(String[] args) {
		try {
			String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?><demo><Zone id=\"Zone1\" rowOrder=\"0\"><ID>1</ID><Zone>直辖市</Zone></Zone><Zone id=\"Zone1\" rowOrder=\"0\"><ID>1</ID><Zone>直辖市1</Zone></Zone><Area id=\"Area408\" rowOrder=\"407\"><ID>412</ID><ZoneID>28</ZoneID><Area>伊宁</Area><AreaCode>51431</AreaCode></Area><Area id=\"Area408\" rowOrder=\"407\"><ID>412</ID><ZoneID>28</ZoneID><Area>伊宁1</Area><AreaCode>51431</AreaCode></Area></demo>";
			
			data = "<diffgr:diffgram xmlns:msdata=\"urn:schemas-microsoft-com:xml-msdata\" xmlns:diffgr=\"urn:schemas-microsoft-com:xml-diffgram-v1\"><Zone diffgr:id=\"Zone1\" msdata:rowOrder=\"0\"><ID>1</ID><Zone>直辖市</Zone></Zone><Zone diffgr:id=\"Zone2\" msdata:rowOrder=\"1\"><ID>2</ID><Zone>特别行政区</Zone></Zone><Area diffgr:id=\"Area1\" msdata:rowOrder=\"0\"><ID>1</ID><ZoneID>1</ZoneID><Area>北京</Area><AreaCode>54511</AreaCode></Area><Area diffgr:id=\"Area78\" msdata:rowOrder=\"77\"><ID>80</ID><ZoneID>8</ZoneID><Area>周口</Area><AreaCode>57195</AreaCode></Area></diffgr:diffgram>";
			
			ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
			
			// 修改返回结果到可解析状态
			JAXBContextImpl jc = (JAXBContextImpl) JAXBContext.newInstance(DataSetResult.class,Demo.class);
			
			logger.info("{}",Arrays.asList(jc.nameList.localNames));
			
			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();
			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			JAXBElement<Demo> poElement = (JAXBElement<Demo>) u.unmarshal(new StreamSource(bais),
					Demo.class);

			Demo demo = poElement.getValue();

			logger.info("{}", demo.getZone().get(1).getZone());
			logger.info("{}", demo.getArea().get(1).getArea());
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
}
