package science.mrcuijt.jaxws.demo2.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlTransient;

public class SupportDataSetResult {

	@XmlElement(name = "diffgram", namespace = "urn:schemas-microsoft-com:xml-diffgram-v1")
	private Diffgram diffgram;

	/**
	 * @return the diffgram
	 */
	@XmlTransient
	public Diffgram getDiffgram() {
		return diffgram;
	}

	/**
	 * @param diffgram the diffgram to set
	 */
	public void setDiffgram(Diffgram diffgram) {
		this.diffgram = diffgram;
	}

	public static class Diffgram {

		@XmlElement(name = "NewDataSet")
		private DataSetResult dataSetResult;

		/**
		 * @return the dataSetResult
		 */
		@XmlTransient
		public DataSetResult getDataSetResult() {
			return dataSetResult;
		}

		/**
		 * @param dataSetResult the dataSetResult to set
		 */
		public void setDataSetResult(DataSetResult dataSetResult) {
			this.dataSetResult = dataSetResult;
		}

	}

}
