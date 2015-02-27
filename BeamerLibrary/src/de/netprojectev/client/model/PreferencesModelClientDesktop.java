package de.netprojectev.client.model;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.utils.HelperMethods;
import de.netprojectev.utils.LoggerBuilder;

public class PreferencesModelClientDesktop extends PreferencesModelClient {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(PreferencesModelClientDesktop.class);

	public PreferencesModelClientDesktop(MessageProxyClient proxy) {
		super(proxy);
		this.clientProperties = generateClientDesktopDefaultProps();
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
			log.log(Level.WARNING, "Error during saving default properties to disk.", e);
		}

		return defProps;
	}

	@Override
	public void loadProperties() throws IOException {
		log.info("Loading properties");
		this.clientProperties = new Properties(generateClientDesktopDefaultProps());
		Properties propsLoaded = HelperMethods.loadPropertiesFromDisk(ConstantsClient.SAVE_PATH, ConstantsClient.FILENAME_PROPERTIES);
		this.clientProperties.putAll(propsLoaded);
	}

	@Override
	public void saveProperties() throws IOException {
		log.info("Saving properties");
		HelperMethods.savePropertiesToDisk(this.clientProperties, ConstantsClient.SAVE_PATH, ConstantsClient.FILENAME_PROPERTIES);
	}
}
