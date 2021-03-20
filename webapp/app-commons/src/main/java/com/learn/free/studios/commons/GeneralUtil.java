package com.learn.free.studios.commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class GeneralUtil {

	private static final Logger logger = LogManager.getLogger(GeneralUtil.class);

	private static final String className = GeneralUtil.class.getName()+" | ";

	//public static Map<String, String> configMap = null;

	public static List<String> readFileAsList(final File file) throws IOException {
		final String mtd = className + "readFileAsList | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
		String size = isListEmpty(lines) ? "NA" : lines.size() + "";
		message = mtd + " lines size=" + size;
		GeneralUtil.log(logger, message);
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return lines;
	}

	/*public static Map<String, String> getConfigProps() {
		final String mtd = className + "getConfigProps | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		final String fileName = "config.properties";
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
*/
	public Properties getProperties(String fileName) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = this.getClass().getClassLoader().getResourceAsStream(fileName);
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static File getFileByFileName(String fileName) throws URISyntaxException, IOException {
		return new File(new GeneralUtil().getFilePathByName(fileName));
	}

	public String getFilePathByName(String fileName) throws URISyntaxException, IOException {
		final String mtd = className + "getFilePathByName | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		String dest = Config.configMap.get("temp.dir") + File.separator + fileName;
		File file = new File(dest);
		if (file.exists()) {
			message = mtd + " is file deleted from =" + dest + " ? " + file.delete();
		}
		FileUtils.copyInputStreamToFile(inputStream, new File(dest));
		message = mtd + " fliePath=" + dest;
		GeneralUtil.log(logger, message);
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return dest;
	}

	public static boolean isStringEmpty(String s) {
		final String mtd = className + "isStringEmpty | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		if (s != null && !s.isEmpty()) {
			message = mtd + GeneralConstants.mtdOut;
			GeneralUtil.log(logger, message);
			return false;
		} else {
			message = mtd + GeneralConstants.mtdOut;
			GeneralUtil.log(logger, message);
			return true;
		}
	}

	public static String trimToValidStr(String s, String regex) {
		String s1 = !GeneralUtil.isStringEmpty(s) ? s.trim() : "";
		if (!s1.isEmpty()) {
			s1 = s1.replaceAll(regex, "");
		}
		return s1;
	}

	public static boolean isListEmpty(List l) {
		final String mtd = className + "isListEmpty | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		if (l != null && !l.isEmpty() && l.size() > 0) {
			message = mtd + GeneralConstants.mtdOut;
			GeneralUtil.log(logger, message);
			return false;
		} else {
			message = mtd + GeneralConstants.mtdOut;
			GeneralUtil.log(logger, message);
			return true;
		}
	}

	public static boolean isMapEmpty(Map m) {
		final String mtd = className + "isListEmpty | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		if (m != null && !m.isEmpty() && m.size() > 0) {
			message = mtd + GeneralConstants.mtdOut;
			GeneralUtil.log(logger, message);
			return false;
		} else {
			message = mtd + GeneralConstants.mtdOut;
			GeneralUtil.log(logger, message);
			return true;
		}
	}

	public static void copyFile(File src, File dest) throws IOException {
		final String mtd = className + "copyFile | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		// FileUtils.copyFile(src, dest);
		Path from = src.toPath(); // convert from File to Path
		// Path to = Paths.get(strTarget); //convert from String to Path
		Path to = dest.toPath();
		GeneralUtil.log(logger, "from = " + from);
		GeneralUtil.log(logger, "to = " + to);
		Path path = Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
		GeneralUtil.log(logger, "path = " + path);
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
	}

	public static void log(Logger logger, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		} else if (logger.isInfoEnabled()) {
			logger.info(message);
		} else if (logger.isTraceEnabled()) {
			logger.trace(message);
		} else {
			logger.warn("Only warn error log level enabled | " + message);
		}
	}

	public static void logErr(Logger logger, Exception errMsg) {
		logger.error("Error : ", errMsg);
	}

}
