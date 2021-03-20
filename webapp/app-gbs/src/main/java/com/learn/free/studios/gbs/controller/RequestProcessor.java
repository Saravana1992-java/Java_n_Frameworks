package com.learn.free.studios.gbs.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.learn.free.studios.commons.Config;
import com.learn.free.studios.commons.GeneralConstants;
import com.learn.free.studios.commons.GeneralUtil;
import com.learn.free.studios.gbs.model.Orfil;
import com.learn.free.studios.gbs.service.OrfilAuditService;
import com.learn.free.studios.gbs.util.ExcelManager;

public class RequestProcessor {

	private static final Logger logger = LogManager.getLogger(RequestProcessor.class);

	private static final String className = "RequestProcessor | ";

	private OrfilAuditService orfilAuditService;

	public RequestProcessor(OrfilAuditService orfilAuditService) {
		this.orfilAuditService = orfilAuditService;
	}

	public File processOrfilAudit(String inputDataFile, String finalReport)
			throws IOException, URISyntaxException, InvalidFormatException, ParseException {
		final String mtd = className + "processOrfilAudit | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		ExcelManager xlMgr = new ExcelManager();
		GeneralUtil.log(logger, "GeneralUtil.getConfigProps().size()" + Config.configMap.size());

		inputDataFile = GeneralUtil.isStringEmpty(inputDataFile) ? Config.configMap.get("xl.input.filepath")
				: inputDataFile;
		finalReport = GeneralUtil.isStringEmpty(finalReport) ? Config.configMap.get("xl.report.filepath") : finalReport;
		List<Orfil> orfilList = xlMgr.processExcel(inputDataFile, true);
		File file = orfilAuditService.audit(orfilList, finalReport);
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return file;
	}

}
