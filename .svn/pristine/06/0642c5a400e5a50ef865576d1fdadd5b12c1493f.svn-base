package com.fixit.utility;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.fixit.domain.bo.AsugUserData;

public class ExcelAsugDataSummary extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> asugData = (Map<String, Object>) model.get("asugData");

		response.setHeader("Content-Disposition", "attachment; filename=\"Revenue Report.xls\"");

		// create a wordsheet
		HSSFSheet sheet = workbook.createSheet("Revenue Report");

		HSSFRow rowMerge = sheet.createRow(0);

		String realPath = request.getSession().getServletContext().getRealPath("/images/excelImageLogo.png");

		final FileInputStream stream = new FileInputStream(realPath);
		byte[] bytes = IOUtils.toByteArray(stream);
		final CreationHelper helper = workbook.getCreationHelper();
		final Drawing drawing = sheet.createDrawingPatriarch();

		final ClientAnchor anchor = helper.createClientAnchor();
		anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);

		final int pictureIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

		anchor.setCol1(0);
		anchor.setRow1(0);
		anchor.setRow2(0);
		anchor.setCol2(0);

		final Picture pict = drawing.createPicture(anchor, pictureIndex);
		short width = (short) pict.getImageDimension().getWidth();
		short height = (short) pict.getImageDimension().getHeight();

		pict.resize(1.0, 1.0);
		Cell imgCell = rowMerge.createCell(1);
		rowMerge.setHeight((short)1500);

		Cell cell = rowMerge.createCell(3);
		cell.setCellValue("ASUG MEMBER/AFFILIATE TRACKING FOR THE MONTH OF MAY");
		cell.setCellStyle(setFontsAndStyle(workbook, "IS_HEADING"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 8));
		HSSFRow header = sheet.createRow(2);

		header.createCell(0).setCellValue("");
		
		header.createCell(1).setCellValue("");
		header.createCell(2).setCellValue("");

		header.createCell(3).setCellValue("ASUG Member");
		header.getCell(3).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_HEADING"));

		header.createCell(4).setCellValue("Credits Purchased");
		header.getCell(4).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_HEADING"));

		header.createCell(5).setCellValue("Revenue");
		header.getCell(5).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_HEADING"));

		header.createCell(6).setCellValue("Payment to Fixer");
		header.getCell(6).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_HEADING"));

		header.createCell(7).setCellValue("Payment to ASUG");
		header.getCell(7).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_HEADING"));

		sheet.setDefaultColumnWidth(30);
		int rowNum = 3;
		for (Map.Entry<String, Object> entry : asugData.entrySet()) {
			// create the row data
			if (entry.getKey().equals("asugUserData")) {
				List<AsugUserData> asugUserDataList = (ArrayList<AsugUserData>) entry.getValue();
				HSSFRow row;
				for (AsugUserData asugUserData : asugUserDataList) {
					row = sheet.createRow(rowNum++);

					row.createCell(0).setCellValue("");
					row.createCell(1).setCellValue("");
					row.createCell(2).setCellValue("");

					row.createCell(3).setCellValue(asugUserData.getUserName());
					row.getCell(3).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_DATA"));
					
					row.createCell(4).setCellValue(asugUserData.getCreditPurchased());
					row.getCell(4).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_DATA"));

					
					row.createCell(5).setCellValue(asugUserData.getRevenue());
					row.getCell(5).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_DATA"));

					
					row.createCell(6).setCellValue(asugUserData.getPaymentToFixer());
					row.getCell(6).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_DATA"));

					
					row.createCell(7).setCellValue(asugUserData.getPaymentToAsug());
					row.getCell(7).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_DATA"));

				}

			}
		}
		HSSFRow row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue("");
		row.createCell(1).setCellValue("");
		row.createCell(2).setCellValue("");
		
		row.createCell(3).setCellValue("TOTAL FOR MONTH");
		row.getCell(3).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_FOOTER"));


		row.createCell(4).setCellValue(""+ asugData.get("totalCredits"));
		row.getCell(4).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_FOOTER"));

		row.createCell(5).setCellValue("" + asugData.get("totalrevenue"));
		row.getCell(5).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_FOOTER"));

		row.createCell(6).setCellValue("" + asugData.get("totalPaymentToFixer"));
		row.getCell(6).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_FOOTER"));

		row.createCell(7).setCellValue("" + asugData.get("totalPaymentToasug"));
		row.getCell(7).setCellStyle(setFontsAndStyle(workbook, "IS_TABLE_FOOTER"));


	}

	public static HSSFCellStyle setFontsAndStyle(HSSFWorkbook workbook, String setStyleBoldFont) {

		HSSFFont font = workbook.createFont();

		if (setStyleBoldFont.equals("IS_HEADING")) {

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			font.setFontHeight((short) (20 * 20));

		}

		if (setStyleBoldFont.equals("IS_TABLE_HEADING")) {

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			font.setFontHeight((short) (11 * 20));

		}
		if (setStyleBoldFont.equals("IS_TABLE_DATA")) {

			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			font.setFontHeight((short) (11 * 20));

		}
		if (setStyleBoldFont.equals("IS_TABLE_FOOTER")) {

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			font.setFontHeight((short) (11 * 20));

		}

		HSSFCellStyle style = workbook.createCellStyle();

		style.setFont(font);
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

		return style;

	}

}