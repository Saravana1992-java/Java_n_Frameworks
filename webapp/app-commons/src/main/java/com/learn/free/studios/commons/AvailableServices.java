package com.learn.free.studios.commons;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AvailableServices {

	private static final Logger logger = LogManager.getLogger(AvailableServices.class);

	private static final String className = AvailableServices.class.getName() + " | ";

	private static AvailableServices availableServices = null;
	public static Map<String, String> services = null;
	private static final String fileName = "services.properties";

	private AvailableServices() {

	}

	public static AvailableServices getInstance() throws URISyntaxException, IOException {
		if (availableServices == null) {
			availableServices = new AvailableServices();
			availableServices.getAvailableServices();
		}
		return availableServices;
	}

	private Map<String, String> getAvailableServices() throws URISyntaxException, IOException {
		if (services == null) {
			services = new GeneralUtil().getProperties(fileName).entrySet().stream()
					.collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
		}
		return services;
	}
}
