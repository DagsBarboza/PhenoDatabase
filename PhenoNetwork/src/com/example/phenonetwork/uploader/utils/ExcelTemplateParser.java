package com.example.phenonetwork.uploader.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.example.phenonetwork.uploader.UploadGermplasm;
import com.example.phenonetwork.uploader.UploadObservation;
import com.example.phenonetwork.uploader.UploadStudy;
import com.example.phenonetwork.uploader.UploadTraits;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;

public class ExcelTemplateParser {

	static int traitId;
	static int scaleId;

	public static Boolean checkGermplasmIfUploaded(String fileDirectory,
			HashMap<String, SQLContainer> container, String data)
			throws BiffException, IOException, UnsupportedOperationException,
			SQLException {
		Workbook workbook;

		workbook = Workbook.getWorkbook(new File(fileDirectory));
		Sheet sheet = workbook.getSheet(0);

		// Identify what to expect in the excel for germplasm Information
		HashMap<String, String> label = new HashMap<String, String>();
		label.put("variety name", "");
		label.put("varietal group", "");
		label.put("type of samples", "");

		// Identify what to expect in the excel for accession
		HashMap<String, String> accessionlabel = new HashMap<String, String>();
		accessionlabel.put("variety name", "");
		accessionlabel.put("irgc acc. no.", "");
		accessionlabel.put("taxonomy", "");
		accessionlabel.put("source country", "");
		accessionlabel.put("donor country", "");
		accessionlabel.put("biological status", "");
		accessionlabel.put("cultural type", "");
		accessionlabel.put("special characteristic", "");

		// getLabel Location in the excel
		label = ExcelHeaderParser.getLabelLocation(sheet, label);
		accessionlabel = ExcelHeaderParser.getLabelLocation(sheet,
				accessionlabel);

		SQLContainer sqlContainer = container.get(data);
		UploadGermplasm.upload(sqlContainer, sheet, label);

		// DbUploader.uploadAccession(container.get("accession"), sheet,
		// accessionlabel);

		return true;
	}

	public static Boolean checkStudyIfUploaded(String fileDirectory,
			HashMap<String, SQLContainer> container, String data)
			throws UnsupportedOperationException, SQLException, BiffException,
			IOException {
		Workbook workbook;

		workbook = Workbook.getWorkbook(new File(fileDirectory));
		Sheet sheet = workbook.getSheet(0);
		// Identify what to expect in the excel for germplasm Information
		HashMap<String, Cell> label = new HashMap<String, Cell>();
		label.put("Experimental location", null);
		label.put("Description", null);
		label.put("Study", null);

		label = ExcelHeaderParser.getLabelValue(sheet, label);

		

		
		Object itemId = UploadStudy
				.upload(container.get("study"), sheet, label);

		// container.get("study").addContainerFilter(
		// new SimpleStringFilter("studyId", itemId.toString(), true,
		// false));
		//
		// Boolean exist = false;
		//
		// if (container.get("study").size() > 0) {
		// container.get("study").removeAllContainerFilters();
		// exist = true;
		// }

		
		if (itemId != null && !itemId.equals(""))

			UploadObservation.upload(
					container,
					workbook.getSheet(1),
					label,
					itemId);

		container.get("study").removeAllContainerFilters();

		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean checkPhenotypeIfUploaded(String fileDirectory,
			HashMap<String, SQLContainer> container, String data)
			throws BiffException, IOException, UnsupportedOperationException,
			SQLException {
		Workbook workbook;

		Item scaleItem;
		Item variateItem;

		// Get data from excel file
		workbook = Workbook.getWorkbook(new File(fileDirectory));
		Sheet sheet = workbook.getSheet(1);

		UploadTraits.upload(container.get("trait"), sheet, null);

		return true;
	}

}
