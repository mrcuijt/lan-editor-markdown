/**
 * 
 */
package science.mrcuijt.jaxws.demo2.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Administrator
 *
 */
public class WeatherbyCityNameResult {

	@XmlElement(name = "string", namespace = "http://WebXml.com.cn/")
	private List<String> string;

	/**
	 * @return the string
	 */
	@XmlTransient
	public List<String> getString() {
		return string;
	}

	/**
	 * @param string the string to set
	 */
	public void setString(List<String> string) {
		this.string = string;
	}

}
