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

import science.mrcuijt.jaxws.demo2.domain.WeatherbyCityNameResult;

/**
 * @author Administrator
 *
 */
public class WeatherbyCityNameResultTest {

	private static final Logger logger = LoggerFactory.getLogger(WeatherbyCityNameResultTest.class);

	public static void main(String[] args) {

		try {

			String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?><demo><Zone id=\"Zone1\" rowOrder=\"0\"><ID>1</ID><Zone>直辖市</Zone></Zone><Zone id=\"Zone1\" rowOrder=\"0\"><ID>1</ID><Zone>直辖市1</Zone></Zone><Area id=\"Area408\" rowOrder=\"407\"><ID>412</ID><ZoneID>28</ZoneID><Area>伊宁</Area><AreaCode>51431</AreaCode></Area><Area id=\"Area408\" rowOrder=\"407\"><ID>412</ID><ZoneID>28</ZoneID><Area>伊宁1</Area><AreaCode>51431</AreaCode></Area></demo>";

			data = "<diffgr:diffgram xmlns:msdata=\"urn:schemas-microsoft-com:xml-msdata\" xmlns:diffgr=\"urn:schemas-microsoft-com:xml-diffgram-v1\"><Zone diffgr:id=\"Zone1\" msdata:rowOrder=\"0\"><ID>1</ID><Zone>直辖市</Zone></Zone><Zone diffgr:id=\"Zone2\" msdata:rowOrder=\"1\"><ID>2</ID><Zone>特别行政区</Zone></Zone><Area diffgr:id=\"Area1\" msdata:rowOrder=\"0\"><ID>1</ID><ZoneID>1</ZoneID><Area>北京</Area><AreaCode>54511</AreaCode></Area><Area diffgr:id=\"Area78\" msdata:rowOrder=\"77\"><ID>80</ID><ZoneID>8</ZoneID><Area>周口</Area><AreaCode>57195</AreaCode></Area></diffgr:diffgram>";

			data = "<getWeatherbyCityNameResult><string>直辖市</string><string>北京</string><string>54511</string><string>54511.jpg</string><string>2019/3/24 11:57:51</string><string>4℃/19℃</string><string>3月24日 晴</string><string>西南风3-4级转小于3级</string><string>0.gif</string><string>0.gif</string><string>今日天气实况：气温：13℃；风向/风力：西风 2级；湿度：15%；紫外线强度：中等。空气质量：良。</string><string>紫外线指数：中等，涂擦SPF大于15、PA+防晒护肤品。\r\n"
					+ "健臻·血糖指数：不易波动，天气条件好，血糖不易波动，可适时进行户外锻炼。\r\n" + "穿衣指数：较舒适，建议穿薄外套或牛仔裤等服装。\r\n"
					+ "洗车指数：较适宜，无雨且风力较小，易保持清洁度。\r\n" + "空气污染指数：良，气象条件有利于空气污染物扩散。\r\n"
					+ "</string><string>5℃/21℃</string><string>3月25日 晴</string><string>西南风转东北风小于3级</string><string>0.gif</string><string>0.gif</string><string>4℃/19℃</string><string>3月26日 晴</string><string>东南风小于3级</string><string>0.gif</string><string>0.gif</string><string>北京位于华北平原西北边缘，市中心位于北纬39度，东经116度，四周被河北省围着，东南和天津市相接。全市面积一万六千多平方公里，辖12区6县，人口1100余万。北京为暖温带半湿润大陆性季风气候，夏季炎热多雨，冬季寒冷干燥，春、秋短促，年平均气温10-12摄氏度。北京是世界历史文化名城和古都之一。早在七十万年前，北京周口店地区就出现了原始人群部落“北京人”。而北京建城也已有两千多年的历史，最初见于记载的名字为“蓟”。公元前1045年北京成为蓟、燕等诸侯国的都城；公元前221年秦始皇统一中国以来，北京一直是中国北方重镇和地方中心；自公元938年以来，北京又先后成为辽陪都、金上都、元大都、明清国都。1949年10月1日正式定为中华人民共和国首都。北京具有丰富的旅游资源，对外开放的旅游景点达200多处，有世界上最大的皇宫紫禁城、祭天神庙天坛、皇家花园北海、皇家园林颐和园，还有八达岭、慕田峪、司马台长城以及世界上最大的四合院恭王府等各胜古迹。全市共有文物古迹7309项，其中国家文物保护单位42个，市级文物保护单位222个。北京的市树为国槐和侧柏，市花为月季和菊花。另外，北京出产的象牙雕刻、玉器雕刻、景泰蓝、地毯等传统手工艺品驰誉世界。</string></getWeatherbyCityNameResult>";

			ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());

			// 修改返回结果到可解析状态
			JAXBContextImpl jc = (JAXBContextImpl) JAXBContext.newInstance(WeatherbyCityNameResult.class);

			logger.info("{}", Arrays.asList(jc.nameList.localNames));

			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();
			// unmarshal a po instance document into a tree of Java content
			// objects composed of classes from the primer.po package.
			JAXBElement<WeatherbyCityNameResult> poElement = (JAXBElement<WeatherbyCityNameResult>) u
					.unmarshal(new StreamSource(bais), WeatherbyCityNameResult.class);

			WeatherbyCityNameResult demo = poElement.getValue();

			logger.info("{}", demo.getString());

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
