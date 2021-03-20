package com.learn.free.studios.gbs.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learn.free.studios.commons.GeneralConstants;
import com.learn.free.studios.commons.GeneralUtil;
import com.learn.free.studios.gbs.model.Orfil;
import com.learn.free.studios.gbs.util.ExcelManager;

@Component("orfilAuditService")
public class OrfilAuditService {

	private static final Logger logger = LogManager.getLogger(OrfilAuditService.class);

	private static final String className = "OrfilAuditService | ";

	private final KieContainer kieContainer;

	@Autowired
	public OrfilAuditService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public File audit(final List<Orfil> orfilList, final String finalReport)
			throws IOException, URISyntaxException, InvalidFormatException {
		final String mtd = className + "audit | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		List<Orfil> orfilAuditList = new ArrayList<>();
		ExcelManager xlMgr = new ExcelManager();
		final String srcFileName = "template/Audit_Checklist_FinalReport.xlsx";
		File src = GeneralUtil.getFileByFileName(srcFileName);
		GeneralUtil.copyFile(src, new File(finalReport));
		// get the stateful session
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		ArrayList<String> feStates = new ArrayList<String>(
				Arrays.asList("AndhraPradesh", "Karnataka", "Telangana", "Kerala"));
		ArrayList<String> typeOfIncome = new ArrayList<String>(Arrays.asList("Income", "No Income"));
		ArrayList<String> status = new ArrayList<String>(Arrays.asList("Yes", "No"));
		kieSession.setGlobal("feStates", feStates);
		kieSession.setGlobal("typeOfIncome", typeOfIncome);
		kieSession.setGlobal("status", status);
		GeneralUtil.log(logger, message);
		int i = 0;
		for (Orfil orfil : orfilList) {
			GeneralUtil.log(logger, "================================== START ==============================");
			GeneralUtil.log(logger, orfil.toString());
			GeneralUtil.log(logger, "================================== END ==============================");
			FactHandle factHandle = kieSession.insert(orfil);
			int result = kieSession.fireAllRules();
			GeneralUtil.log(logger, "result=" + result);
			orfilAuditList.add(i, orfil);
			i++;
		}
		File file = xlMgr.createReport(orfilAuditList, finalReport);
		kieSession.dispose();
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return file;
	}
}
