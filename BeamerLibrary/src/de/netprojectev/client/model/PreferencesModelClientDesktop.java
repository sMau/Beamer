package de.netprojectev.client.model;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.utils.HelperMethods;
import de.netprojectev.utils.LoggerBuilder;

public class PreferencesModelClientDesktop extends PreferencesModelClient {
	
	private static final Logger log = LoggerBuilder.createLogger(PreferencesModelClientDesktop.class);

	
	public PreferencesModelClientDesktop(ClientMessageProxy proxy) {
		super(proxy);
		clientProperties = generateClientDesktopDefaultProps();
	}

	public void saveProperties() throws IOException {
		log.info("Saving properties");
		HelperMethods.savePropertiesToDisk(clientProperties, ConstantsClient.SAVE_PATH, ConstantsClient.FILENAME_PROPERTIES);
	}

	public void loadProperties() throws IOException {
		log.info("Loading properties");
		clientProperties = new Properties(generateClientDesktopDefaultProps());
		Properties propsLoaded = HelperMethods.loadPropertiesFromDisk(ConstantsClient.SAVE_PATH, ConstantsClient.FILENAME_PROPERTIES);
		clientProperties.putAll(propsLoaded);
	}
	

	private Properties generateClientDesktopDefaultProps() {
		Properties defProps = new Properties();

		defProps.setProperty(ConstantsClient.PROP_PREVIEW_SCALE_WIDTH, "" + ConstantsClient.DEFAULT_PREVIEW_SCALE_WIDTH);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR, "" + ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE, ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE + "pt");
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE, ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT, "" + ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP, "" + ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP);

		try {
			HelperMethods.savePropertiesToDisk(defProps, ConstantsClient.SAVE_PATH, ConstantsClient.FILENAME_DEFAULT_PROPERTIES);
		} catch (IOException e) {
			log.warn("Error during saving default properties to disk.", e);
		}

		return defProps;
	}
}
