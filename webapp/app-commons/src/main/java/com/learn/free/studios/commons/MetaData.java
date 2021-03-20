package com.learn.free.studios.commons;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetaData {

	private static final Logger logger = LogManager.getLogger(MetaData.class);

	private static final String className = MetaData.class.getName() + " | ";

	private static MetaData metaData = null;
	public static Map<Integer, String> columns = new HashMap<>();
	private static final String fileName = "RequiredColumns.txt";
	public static List<String> requiredColumns = null;

	private MetaData() {

	}

	public static MetaData getInstance() {
		if (metaData == null) {
			metaData = new MetaData();
			metaData.getRequiredColumns();
		}

		return metaData;
	}

	private List<String> getRequiredColumns() {
		final String mtd = className + "getRequiredColumns | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		if (requiredColumns == null) {
			try {
				File file = GeneralUtil.getFileByFileName(fileName);
				requiredColumns = GeneralUtil.readFileAsList(file);
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				GeneralUtil.logErr(logger, e);
			}
		}
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return requiredColumns;
	}
}
