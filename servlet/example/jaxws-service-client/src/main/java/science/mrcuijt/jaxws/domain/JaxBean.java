/**
 * 
 */
package science.mrcuijt.jaxws.domain;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class JaxBean {

	private GetSupportProvinceResult getSupportProvinceResult;

	/**
	 * @return the getSupportProvinceResult
	 */
	public GetSupportProvinceResult getGetSupportProvinceResult() {
		return getSupportProvinceResult;
	}

	/**
	 * @param getSupportProvinceResult the getSupportProvinceResult to set
	 */
	public void setGetSupportProvinceResult(GetSupportProvinceResult getSupportProvinceResult) {
		this.getSupportProvinceResult = getSupportProvinceResult;
	}

	public static class GetSupportProvinceResult {
		private List<String> string;

		/**
		 * @return the string
		 */
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

}
