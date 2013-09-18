package com.example.phenonetwork.uploader.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.example.phenonetwork.db.GetDataset;
import com.example.phenonetwork.db.GetStudy;
import com.example.phenonetwork.uploader.UploadDataset;
import com.example.phenonetwork.uploader.UploadGermplasm;
import com.example.phenonetwork.uploader.UploadObservationDate;
import com.example.phenonetwork.uploader.UploadStudy;
import com.example.phenonetwork.uploader.UploadTraits;
import com.example.phenonetwork.uploader.UploadVariate;
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
		Sheet datasheet = workbook.getSheet(1);

		HashMap<String, Cell> excelHeader = new HashMap<String, Cell>();
		excelHeader.put("Experimental location", null);
		excelHeader.put("Description", null);
		excelHeader.put("Study", null);

		excelHeader = ExcelHeaderParser.getLabelValue(sheet, excelHeader);

		Object itemId = new Object();

		//CHECK IF STUDY IS ALREADY EXISTING
		GetStudy gs = new GetStudy(container.get("study"));
		
		if (gs.countStudyByName(sheet, excelHeader) == 0)
			itemId = UploadStudy.upload(container.get("study"), sheet,
					excelHeader);
		else
			itemId = sheet.getCell(excelHeader.get("Study").getColumn() + 1,
					excelHeader.get("Study").getRow()).getContents();

		//CHECK IF DATASET IS ALREADY EXISTING
		int datasetId;
		GetDataset gd = new GetDataset(container.get("dataset"));
		
		if (gd.countDatasetByStudyName(itemId,
				workbook.getSheet(1).getName() ) == 0 )
			datasetId = UploadDataset.upload(container.get("dataset"), itemId,
					workbook.getSheet(1).getName());
		else
			datasetId = gd.getDatasetIdByName(itemId, workbook.getSheet(1).getName());

		
		
		HashMap<String, HashMap> trait = UploadVariate.upload(container,
				itemId, datasheet, datasetId);

		if (itemId != null && !itemId.equals("")){
			//ADD WHAT KING OF OBSERVATION IS TO BE UPLOADED
			UploadObservationDate.upload(container, workbook.getSheet(1), trait,
					itemId, datasetId);
		}

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
