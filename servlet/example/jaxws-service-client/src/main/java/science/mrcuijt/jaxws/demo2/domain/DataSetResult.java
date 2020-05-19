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
public class DataSetResult {

	@XmlElement(name = "Zone")
	private List<Zone> zone;
	@XmlElement(name = "Area")
	private List<Area> area;

	/**
	 * @return the zone
	 */
	@XmlTransient
	public List<Zone> getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(List<Zone> zone) {
		this.zone = zone;
	}

	/**
	 * @return the area
	 */
	@XmlTransient
	public List<Area> getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(List<Area> area) {
		this.area = area;
	}

	public static class Zone {

		private int ID;
		@XmlElement(name = "Zone")
		private String zone;

		/**
		 * @return the iD
		 */
		public int getID() {
			return ID;
		}

		/**
		 * @param iD the iD to set
		 */
		public void setID(int iD) {
			ID = iD;
		}

		/**
		 * @return the zone
		 */
		@XmlTransient
		public String getZone() {
			return zone;
		}

		/**
		 * @param zone the zone to set
		 */
		public void setZone(String zone) {
			this.zone = zone;
		}

	}

	public static class Area {
		@XmlElement(name = "ID")
		private int id;
		@XmlElement(name = "ZoneID")
		private int zoneId;
		@XmlElement(name = "Area")
		private String area;
		@XmlElement(name = "AreaCode")
		private String areaCode;

		/**
		 * @return the id
		 */
		@XmlTransient
		public int getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}

		/**
		 * @return the zoneId
		 */
		@XmlTransient
		public int getZoneId() {
			return zoneId;
		}

		/**
		 * @param zoneId the zoneId to set
		 */
		public void setZoneId(int zoneId) {
			this.zoneId = zoneId;
		}

		/**
		 * @return the area
		 */
		@XmlTransient
		public String getArea() {
			return area;
		}

		/**
		 * @param area the area to set
		 */
		public void setArea(String area) {
			this.area = area;
		}

		/**
		 * @return the areaCode
		 */
		@XmlTransient
		public String getAreaCode() {
			return areaCode;
		}

		/**
		 * @param areaCode the areaCode to set
		 */
		public void setAreaCode(String areaCode) {
			this.areaCode = areaCode;
		}

	}

}
