/**
 * 
 */
package science.mrcuijt.jaxws.demo3.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name = "getSupportProvinceResult")
public class JaxBean {

	@XmlElement(name = "string")
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
