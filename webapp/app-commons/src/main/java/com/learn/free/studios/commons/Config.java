package com.learn.free.studios.commons;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {

	private static final Logger logger = LogManager.getLogger(Config.class);

	private static Config config = null;
	private static final String className = Config.class.getName() + " | ";
	private static final String fileName = "config.properties";
	public static Map<String, String> configMap = null;

	private Config() {

	}

	public static Config getInstance() {
		if (config == null) {
			config = new Config();
			config.getConfigProps();
		}
		return config;
	}

	private Map<String, String> getConfigProps() {
		final String mtd = className + "getConfigProps | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);

		if (configMap == null) {
			configMap = new GeneralUtil().getProperties(fileName).entrySet().stream()
					.collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
			for (Map.Entry<String, String> entry : configMap.entrySet()) {
				message = mtd + "Before update:" + entry.getKey() + "----" + entry.getValue();
				GeneralUtil.log(logger, message);
				String key = entry.getKey();
				String val = entry.getValue();
				if (val.contains("$")) {
					do {
						if (val.indexOf("$") == 0) {
							String keyToReplace = val.substring(val.indexOf("$") + 2, val.indexOf("}"));
							val = configMap.get(keyToReplace) + val.substring(val.indexOf("}") + 1);
						} else {
							String keyToReplace = val.substring(val.indexOf("$") + 2, val.indexOf("}"));
							val = val.substring(0, val.indexOf("$")) + configMap.get(keyToReplace)
									+ val.substring(val.indexOf("}") + 1);
						}
						entry.setValue(val);

					} while (val.contains("$"));
				}
				message = mtd + "After update:" + entry.getKey() + "----" + entry.getValue();
				GeneralUtil.log(logger, message);
			}
		}
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return configMap;
	}

}
