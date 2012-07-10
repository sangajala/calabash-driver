package sh.calaba.driver.server;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sh.calaba.driver.CalabashCapabilities;
import sh.calaba.driver.server.exceptions.CalabashConfigurationException;

/**
 * Calabash Driver Configuration.
 * 
 * @author ddary
 * 
 */
public class CalabashNodeConfiguration {
	public static final String CAPABILITIES = "capabilities";
	public static final String CONFIGURATION = "configuration";

	/**
	 * Reads the the driver configuration form the specified file. The file is
	 * expected to be in JSON format.
	 * 
	 * @param driverConfigurationFile
	 *            The file name of the driver configuration file.
	 * @return The Calabash node configuration.
	 * @throws CalabashConfigurationException
	 *             On IO and Parsing errors.
	 * @throws InvalidParameterException
	 *             if parameter is null or empty
	 */
	public static CalabashNodeConfiguration readConfig(
			String driverConfigurationFile)
			throws CalabashConfigurationException {
		if (driverConfigurationFile == null
				|| driverConfigurationFile.isEmpty()) {
			throw new InvalidParameterException(
					"Calabash-Driver Configuration-File is missing. Pls specifiy name like: calabashNode.json");
		}
		String driverConfiguration;
		try {
			driverConfiguration = FileUtils.readFileToString(new File(
					driverConfigurationFile));
		} catch (IOException e1) {
			throw new CalabashConfigurationException(
					"Error reading file content. Did you have specified the right file name and path?",
					e1);
		}

		try {
			return new CalabashNodeConfiguration(new JSONObject(
					driverConfiguration));
		} catch (JSONException e) {
			throw new CalabashConfigurationException(
					"Error occured during parsing json file: '"
							+ driverConfigurationFile
							+ "'. Pls make sure you are using a valid JSON file!",
					e);
		}
	}
	private List<CalabashCapabilities> capabilities = new ArrayList<CalabashCapabilities>();

	private String driverHost = null;
	private String mobileAppPath = null;
	private String mobileTestAppPath = null;
	private int driverMaxSession;
	private int driverPort;
	private boolean driverRegistrationEnabled;
	private String hubHost = null;

	private int hubPort;

	protected CalabashNodeConfiguration(JSONObject config)
			throws JSONException, CalabashConfigurationException {
		readDriverConfiguration(config.getJSONObject(CONFIGURATION));
		readCapabilities(config.getJSONArray(CAPABILITIES));
	}

	/**
	 * @return the capabilities
	 */
	public List<CalabashCapabilities> getCapabilities() {
		return capabilities;
	}

	/**
	 * @return the driverHost
	 */
	public String getDriverHost() {
		return driverHost;
	}

	/**
	 * @return the driverMaxSession
	 */
	public int getDriverMaxSession() {
		return driverMaxSession;
	}

	/**
	 * @return the driverPort
	 */
	public int getDriverPort() {
		return driverPort;
	}

	/**
	 * @return the mobileAppPath
	 */
	public String getMobileAppPath() {
		return mobileAppPath;
	}

	/**
	 * @return the mobileTestAppPath
	 */
	public String getMobileTestAppPath() {
		return mobileTestAppPath;
	}

	/**
	 * @return the hubHost
	 */
	public String getHubHost() {
		return hubHost;
	}

	/**
	 * @return the hubPort
	 */
	public int getHubPort() {
		return hubPort;
	}

	/**
	 * @return the driverRegistrationEnabled
	 */
	public boolean isDriverRegistrationEnabled() {
		return driverRegistrationEnabled;
	}

	private void readCapabilities(JSONArray jsonArray) throws JSONException,
			CalabashConfigurationException {
		if (jsonArray == null || jsonArray.length() == 0) {
			throw new CalabashConfigurationException(
					"No capabilities are specified.");
		}
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject capa = jsonArray.getJSONObject(i);
		
			capabilities.add(CalabashCapabilities.fromJSON(capa));
		}

	}

	private void readDriverConfiguration(JSONObject configuration)
			throws JSONException {
		hubHost = configuration.getString("hubHost");
		hubPort = configuration.getInt("hubPort");
		driverHost = configuration.getString("host");
		driverPort = configuration.getInt("port");
		driverRegistrationEnabled = configuration.getBoolean("register");
		driverMaxSession = configuration.getInt("maxSession");
		mobileAppPath = configuration.getString("autApk");
		mobileTestAppPath = configuration.getString("autTestApk");
	}

}