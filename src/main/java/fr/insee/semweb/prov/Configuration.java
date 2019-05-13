package fr.insee.semweb.prov;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class Configuration {


	// Input files
	public static final Path RESOURCE_PATH = Paths.get("src/main/resources/data");

	// Constants for naming
	/** Base URI for the record linkage example */
	public static String RL_EXAMPLE_BASE_URI = "http://example.org/linkage/";
	/** Base URI for activities */
	public static String RL_EXAMPLE_ACTIVITY_BASE_URI = RL_EXAMPLE_BASE_URI + "activity/";
	/** Base URI for activities */
	public static String RL_EXAMPLE_PRODUCT_BASE_URI = RL_EXAMPLE_BASE_URI + "product/";

	/** URI for Istat */
	public static String ISTAT_URI = "http://id.istat.it/istat";
	/** URI for RELAIS */
	public static String RELAIS_URI = "http://id.istat.it/software/relais";

	/** URI for an activity */
	public static String uneceActivityURI(String activityLabel) {
		return RL_EXAMPLE_ACTIVITY_BASE_URI + toLowerCamelCase(activityLabel);
	}

	public static String uneceProductURI(String entityLabel) {
		return RL_EXAMPLE_PRODUCT_BASE_URI + toLowerCamelCase(entityLabel);
	}

	private static String toLowerCamelCase(String entityLabel) {
		return StringUtils.uncapitalize(WordUtils.capitalizeFully(entityLabel, null).replaceAll(" ", ""));
	}
}
