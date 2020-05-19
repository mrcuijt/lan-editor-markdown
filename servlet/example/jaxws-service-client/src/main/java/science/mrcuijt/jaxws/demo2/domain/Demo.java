/**
 * 
 */
package science.mrcuijt.jaxws.demo2.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Administrator
 *
 */
public class Demo {

	@XmlElement(name = "Zone")
	private List<DataSetResult.Zone> zone;

	@XmlElement(name = "Area")
	private List<DataSetResult.Area> area;

	/**
	 * @return the zone
	 */
	@XmlTransient
	public List<DataSetResult.Zone> getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(List<DataSetResult.Zone> zone) {
		this.zone = zone;
	}

	/**
	 * @return the area
	 */
	@XmlTransient
	public List<DataSetResult.Area> getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(List<DataSetResult.Area> area) {
		this.area = area;
	}
	
	

}
