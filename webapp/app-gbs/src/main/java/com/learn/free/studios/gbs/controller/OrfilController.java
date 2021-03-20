package com.learn.free.studios.gbs.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learn.free.studios.commons.Config;
import com.learn.free.studios.commons.GeneralConstants;
import com.learn.free.studios.commons.GeneralUtil;
import com.learn.free.studios.gbs.service.OrfilAuditService;


@RestController
public class OrfilController {
	private static final Logger logger = LogManager.getLogger(OrfilController.class);

	private static final String className = "OrfilController | ";

	private OrfilAuditService orfilAuditService = null;

	@Autowired
	public OrfilController(OrfilAuditService orfilAuditService) {
		this.orfilAuditService = orfilAuditService;
	}

	@RequestMapping(value = "/orfilAudit", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<ByteArrayResource> orfilAudit(@RequestParam("file") MultipartFile uploadedFile,
			RedirectAttributes attributes)
			throws IOException, URISyntaxException, InvalidFormatException, ParseException {
		final String mtd = className + "orfilAudit | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		String inputDataFile = "";

		// check if file is empty
		if (!uploadedFile.isEmpty()) {
			// normalize the file path
			inputDataFile = StringUtils.cleanPath(uploadedFile.getOriginalFilename());
			try {
				final String UPLOAD_DIR = Config.configMap.get("temp.dir");
				inputDataFile = UPLOAD_DIR + File.separator + "data.xlsx";
				Path path = Paths.get(inputDataFile);
				Files.copy(uploadedFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		RequestProcessor requestProcessor = new RequestProcessor(orfilAuditService);
		File file = requestProcessor.processOrfilAudit(inputDataFile, "");
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return ResponseEntity.ok().headers(getHeader(file.getName())).contentLength(file.length())
				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE)).body(resource);
	}

	private HttpHeaders getHeader(String fileName) {
		final String mtd = className + "getHeader | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return header;
	}
}
