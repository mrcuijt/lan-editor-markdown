/**
 * 
 */
package science.mrcuijt.jaxws.domain;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class Region {

	public Region() {
	}

	public Region(List<String> alias) {
		this.alias = alias;
	}

	private String code;
	private List<String> alias;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the alias
	 */
	public List<String> getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

}
