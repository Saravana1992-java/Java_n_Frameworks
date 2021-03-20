package com.learn.free.studios.gbs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.learn.free.studios.commons.GeneralConstants;
import com.learn.free.studios.commons.GeneralUtil;
import com.learn.free.studios.commons.MetaData;
import com.learn.free.studios.gbs.model.Answer;
import com.learn.free.studios.gbs.model.Orfil;
import com.learn.free.studios.gbs.model.Question;

public class ExcelManager {

	private static final Logger logger = LogManager.getLogger(ExcelManager.class);

	private static final String className = "ExcelManager | ";

	public List<Orfil> processExcel(final String fileName, final boolean isFirstRowColName)
			throws IOException, URISyntaxException, InvalidFormatException, ParseException {
		final String mtd = className + "processExcel | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);

		FileInputStream file = new FileInputStream(new File(fileName));
		// Workbook workbook = new XSSFWorkbook(file);
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);
		List<Orfil> orfilList = new ArrayList<Orfil>();
		int rowNum = 0;
		for (Row row : sheet) {
			if (isFirstRowColName && rowNum == 0) {
				loadMetaData(row, true);
			} else {
				if (!GeneralUtil.isMapEmpty(MetaData.columns)) {
					Orfil orfil = loadOrfilData(row);
					loadQuestions(orfil, isFirstRowColName);
					orfilList.add(orfil);
				}
			}
			rowNum++;
		}
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return orfilList;
	}

	public Orfil loadQuestions(Orfil orfil, final boolean isFirstRowColName)
			throws IOException, URISyntaxException, InvalidFormatException {
		final String mtd = className + "loadQuestions | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		final String srcFileName = "template/Audit_Checklist_FinalReport.xlsx";
		File src = GeneralUtil.getFileByFileName(srcFileName);
		FileInputStream file = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = 0;
		HashMap<Integer, Question> questionnaire = new HashMap<Integer, Question>();
		for (Row row : sheet) {
			if (rowNum > 0) {
				String id = getCellValue(row.getCell(0));
				id = GeneralUtil.trimToValidStr(id, GeneralConstants.doubleStrRegex);
				String group = getCellValue(row.getCell(1));
				group = GeneralUtil.trimToValidStr(group, GeneralConstants.alphaNumSplStrRegex);
				String quest = getCellValue(row.getCell(2));
				quest = GeneralUtil.trimToValidStr(quest, GeneralConstants.questionsSplStrRegex);
				// GeneralUtil.log(logger, "id = " + id + " | group=" + group + " | quest=" +
				// quest);
				if (!GeneralUtil.isStringEmpty(id)) {
					id = id.substring(0, id.indexOf("."));
					Question question = new Question(Integer.valueOf(id), quest, "", group,
							new Answer((int) Integer.valueOf(id), "", ""));
					questionnaire.put(Integer.valueOf(id), question);
				}
				// Questionnaire questionnaire = new Questionnaire(question);
			}
			rowNum++;
		}
		orfil.setQuestionnaire(questionnaire);
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return orfil;
	}

	public File createReport(final List<Orfil> orfilAuditList, final String fileName)
			throws IOException, InvalidFormatException {
		final String mtd = className + "createReport | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		FileInputStream inputStream = new FileInputStream(new File(fileName));
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		GeneralUtil.log(logger, "rowCount = " + rowCount);
		int finalDataCount = 3;
		GeneralUtil.log(logger, "orfilAuditList.size() = " + orfilAuditList.size());
		for (Orfil orfil : orfilAuditList) {
			for (int i = 0; i <= rowCount; i++) {
				if (i == 0) {
					sheet = setCell(workbook, sheet, i, finalDataCount, orfil.getApplNo());
				} else {
					String id = getCellValue(getCell(sheet, i, 0));
					if (!GeneralUtil.isStringEmpty(id)) {
						id = id.substring(0, id.indexOf("."));
						int idNum = (int) Integer.valueOf(id);
						if (idNum == 37) {
							sheet = setCell(workbook, sheet, i, finalDataCount, orfil.getAppCreditApproverComments());
						} else {
							Question question = orfil.getQuestionnaire().get(idNum);
							String ans = question.getAnswer().getAnswer();
							GeneralUtil.log(logger, "id = " + id + " | ans=" + ans);
							sheet = setCell(workbook, sheet, i, finalDataCount, ans);
						}
					}else {
						sheet = setCell(workbook, sheet, i, finalDataCount, "EMPTY_ROW");
					}
				}
			}
			finalDataCount++;
		}
		FileOutputStream fileOut = new FileOutputStream(fileName);
		workbook.write(fileOut);
		// workbook.close();
		fileOut.close();
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);

		return new File(fileName);
	}

	private static Sheet setCell(Workbook workbook, Sheet sheet, int rowNum, int colNum, String value) {
		final String mtd = className + "setCell | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.getCell(colNum);
		if (cell == null) {
			cell = row.createCell(colNum);
		}
		row.setHeight((short) 800);
		sheet.setColumnWidth(colNum, 5000);

		// Top Left alignment
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 11);
		font.setItalic(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		// style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// sheet.setColumnWidth(0, value.length()*100);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		if (rowNum == 0) {
			font.setFontName("Calibri");
			style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			font.setColor(IndexedColors.WHITE.getIndex());
			font.setItalic(false);
		} else if ("YES".equalsIgnoreCase(value)) {
			font.setColor(IndexedColors.OLIVE_GREEN.getIndex());
		} else if ("NO".equalsIgnoreCase(value)) {
			font.setColor(IndexedColors.DARK_RED.getIndex());
		}else if ("EMPTY_ROW".equalsIgnoreCase(value)) {
			style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			value = "";
		} else {
			font.setColor(IndexedColors.BLACK.getIndex());
		}
		style.setWrapText(true);
		style.setFont(font);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		sheet.getRow(rowNum).getCell(colNum).setCellStyle(style);
		sheet.getRow(rowNum).getCell(colNum).setCellValue(value);

		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger,
				"Setting sell value for rowNum=" + rowNum + " | colNum=" + colNum + " | value=" + value);
		return sheet;
	}

	private static Cell getCell(Sheet sheet, int rowNum, int colNum) {
		Cell cell = sheet.getRow(rowNum).getCell(colNum);
		return cell;
	}

	private void loadMetaData(Row row, boolean onlyRequiredCol) throws IOException, URISyntaxException {
		final String mtd = className + "loadMetaData | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		String cellVal = "";
		List<String> lines = MetaData.requiredColumns;
		for (Cell cell : row) {
			cellVal = getCellValue(cell);
			if (onlyRequiredCol && GeneralUtil.isListEmpty(lines) && lines.contains(cellVal)) {
				MetaData.columns.put(cell.getColumnIndex(), cellVal);
			} else {
				MetaData.columns.put(cell.getColumnIndex(), cellVal);
			}
			String size = GeneralUtil.isMapEmpty(MetaData.columns) ? "NA" : lines.size() + "";
			message = mtd + " MetaData columns size=" + size;
			GeneralUtil.log(logger, message);
		}
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
	}

	private static String getCellValue(Cell cell) {
		String cellVal = "";
		switch (cell.getCellType()) {
		case 1:
			cellVal = cell.getStringCellValue();
			break;
		case 0:
			cellVal = cell.getNumericCellValue() + "";
			break;
		case 4:
			cellVal = cell.getBooleanCellValue() + "";
			break;
		case 2:
			cellVal = cell.getCellFormula();
			break;
		default:
			cellVal = "";
		}

		return cellVal.trim();
	}

	private static Orfil loadOrfilData(Row row) throws ParseException {
		final String mtd = className + "loadOrfilData | ";
		String message = mtd + GeneralConstants.mtdIn;
		GeneralUtil.log(logger, message);
		Orfil orfil = null;
		if (row != null) {
			orfil = new Orfil();
			for (Map.Entry<Integer, String> entry : MetaData.columns.entrySet()) {
				int key = entry.getKey();
				String val = entry.getValue().trim();
				// String cellVal = row.getCell(key).getStringCellValue();
				String cellVal = getCellValue(row.getCell(key));
				cellVal = GeneralUtil.isStringEmpty(cellVal) ? "" : cellVal.trim();
				GeneralUtil.log(logger, "val=" + val);
				GeneralUtil.log(logger, "cellVal=" + cellVal);
				switch (val) {
				case "Income Noincome":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setIncomeNoIncome(cellVal.trim());
					break;
				case "Loan Amt":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double loanAmt = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setLoanAmt(loanAmt);
					break;
				case "Tenure":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double tenure = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setTenure(tenure);
					break;
				case "Emi":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double emi = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setEmi(emi);
					break;
				case "Income Level":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double incomeLevel = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setIncomeLevel(incomeLevel);
					break;
				case "Crif Score":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double crifScore = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setCrifScore(crifScore);
					break;
				case "House Type":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setHouseType(cellVal);
					break;
				case "Actual Ltv":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double actualLtv = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setActualLtv(actualLtv);
					break;
				case "Category Vehicle":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setCategoryVehicle(cellVal);
					break;
				case "Approval Type":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setApprovalType(cellVal);
					break;
				case "Fe State":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setState(cellVal);
					break;
				case "Id":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setId(cellVal);
					break;
				case "Appno":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setApplNo(cellVal);
					break;
				case "Fi Dis From Branch":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.doubleStrRegex);
					double distance = !GeneralUtil.isStringEmpty(cellVal) ? Double.parseDouble(cellVal.trim()) : 0;
					orfil.setDistance(distance);
					break;
				case "Guarantor Avail":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setGuarantorAvail(cellVal);
					break;
				case "Dob":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.dateTimeSplStrRegex);
					orfil.setDOB(cellVal);
					if (GeneralUtil.isStringEmpty(cellVal)) {
						orfil.setAge(0);
					} else {
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						Date date = formatter.parse(cellVal);
						// Converting obtained Date object to LocalDate object
						Instant instant = date.toInstant();
						ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
						LocalDate givenDate = zone.toLocalDate();
						// Calculating the difference between given date to current date.
						Period period = Period.between(givenDate, LocalDate.now());
						orfil.setAge(period.getYears());
					}

					break;
				case "Fi Residence Ownership":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setFiResidenceOwnership(cellVal);
					break;
				case "App Credit Approver Comments":
					cellVal = GeneralUtil.trimToValidStr(cellVal, GeneralConstants.alphaNumSplStrRegex);
					orfil.setAppCreditApproverComments(cellVal);
					break;

				default:
					break;
				}
			}
		}
		message = mtd + GeneralConstants.mtdOut;
		GeneralUtil.log(logger, message);
		return orfil;
	}
}
