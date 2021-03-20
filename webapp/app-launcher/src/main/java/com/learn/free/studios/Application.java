package com.learn.free.studios;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.learn.free.studios.commons.AvailableServices;
import com.learn.free.studios.commons.Config;
import com.learn.free.studios.commons.GeneralConstants;
import com.learn.free.studios.commons.GeneralUtil;
import com.learn.free.studios.commons.MetaData;

@SpringBootApplication
@ComponentScan("com.learn.free.studios")
public class Application {


	private static final Logger logger = LogManager.getLogger(Application.class);

	private static final String className = Application.class.getName() + " | ";

	public static void main(String[] args) throws URISyntaxException, IOException {
		// TODO Auto-generated method stub
		final String mtd = className + "main | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		init();
		System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
		//String directoryName = GeneralUtil.getConfigProps().get("temp.dir");
		String directoryName = Config.configMap.get("temp.dir");
		File directory = new File(directoryName);
		if (!directory.exists()) {
			GeneralUtil.log(logger, "Creating directory=" + directory + " as it does not exists!!");
			directory.mkdir();
		}
		SpringApplication.run(Application.class, args);
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);

	}
	
	private static void init() throws URISyntaxException, IOException {
		Config.getInstance();
		MetaData.getInstance();
		AvailableServices.getInstance();
	}

	@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}



}
