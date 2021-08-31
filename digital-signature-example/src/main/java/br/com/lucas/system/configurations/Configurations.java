package br.com.lucas.system.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configurations {
	private static final Logger logger = LogManager.getLogger(Configurations.class);
	final static private String configFile = new String("./config/configurations.properties");
	private Properties configurations;

	public Configurations() {
		configurations = new Properties();
		refresh();
	}

	private void refresh() {
		File conf = new File(configFile);
		if (conf.exists()) {
			try (FileInputStream fio = new FileInputStream(configFile)) {
				configurations.load(fio);
			} catch (IOException ioe) {
				logger.error("Failed to load configuration file", ioe);
			}
		}
	}

	public boolean configFileExists() {
		File conf = new File(configFile);
		return conf.exists();
	}

	public boolean createConfigFile() throws IOException {
		File conf = new File(configFile);
		FileUtils.createParentDirectories(conf);
		return conf.createNewFile();
	}

	public void setConfiguration(String cName, String cValue) {
		configurations.setProperty(cName, cValue);
	}

	public String getConfiguration(String cName, String defaultValue) {
		refresh();
		return configurations.getProperty(cName, defaultValue);
	}

	public void save() throws IOException {
		try (FileOutputStream fio = new FileOutputStream(configFile)) {
			configurations.store(fio, "Providers folders");
		}
	}
}
