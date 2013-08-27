package com.example.phenonetwork.uploader.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.vaadin.data.Item;
import com.vaadin.data.util.filter.SimpleStringFilter;
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
		DbUploader.uploadGermplasm(sqlContainer, sheet, label);
		DbUploader.uploadAccession(container.get("accession"), sheet,
				accessionlabel);

		return true;
	}

	public static Boolean checkStudyIfUploaded(String fileDirectory,
			HashMap<String, SQLContainer> container, String data)
			throws UnsupportedOperationException, SQLException, BiffException,
			IOException {
		Workbook workbook;

		System.out.println(fileDirectory);
		workbook = Workbook.getWorkbook(new File(fileDirectory));
		System.out.println(">>>");
		Sheet sheet = workbook.getSheet(0);
		System.out.println(">>>?");
		// Identify what to expect in the excel for germplasm Information
		HashMap<String, Cell> label = new HashMap<String, Cell>();
		label.put("Investigator", null);
		label.put("Sampling Date", null);
		label.put("Study Name", null);

		// getLabel Location in the excel

		label = ExcelHeaderParser.getLabelValue(sheet, label);
		// label = getLabelLocation(sheet, label);

		// SQLContainer sqlContainer = container.get(data);
		Object itemId = DbUploader.uploadStudyDefinition(
				container.get("study"), sheet, label);

		System.out.println("id:" + itemId);

		container.get("study").addContainerFilter(
				new SimpleStringFilter("studyId", itemId.toString(), true,
						false));

		Boolean exist = false;

		if (container.get("study").size() > 0) {
			container.get("study").removeAllContainerFilters();
			exist = true;
		}

		if (exist)
			DbUploader.uploadDateset(container, workbook.getSheet(1), label,
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

		Item item;
		Item scaleItem;
		Item variateItem;

		// Get data from excel file
		workbook = Workbook.getWorkbook(new File(fileDirectory));
		Sheet sheet = workbook.getSheet(0);
		Cell[] cells = sheet.getColumn(0);

		container.get("phenotype").addRowIdChangeListener(
				new RowIdChangeListener() {

					@Override
					public void rowIdChange(RowIdChangeEvent event) {
						traitId = Integer.parseInt(event.getNewRowId()
								.toString());
					}
				});

		container.get("scale").addRowIdChangeListener(
				new RowIdChangeListener() {

					@Override
					public void rowIdChange(RowIdChangeEvent event) {
						scaleId = Integer.parseInt(event.getNewRowId()
								.toString());
					}
				});

		String scaleUnit = "";

		// write data to container
		for (Cell cell : cells) {
			if (!cell.getContents().equals("")) {

				scaleId = 0;

				Object newTrait = container.get("phenotype").addItem();
				item = container.get("phenotype").getItem(newTrait);

//				Object variate = container.get("variates").addItem();
//				variateItem = container.get("variates").getItem(variate);

				item.getItemProperty("traitName").setValue(cell.getContents());

				Cell acc = sheet.findCell(cell.getContents());
				item.getItemProperty("traitAcronym").setValue(
						sheet.getCell(acc.getColumn() + 1, acc.getRow())
								.getContents());

				// adding to scale

				scaleUnit = sheet.getCell(acc.getColumn() + 2, acc.getRow())
						.getContents();

				if (!scaleUnit.equals("") && scaleUnit != null) {

					container.get("scale").addContainerFilter(
							new SimpleStringFilter("scaleName", scaleUnit,
									true, false));

					if (container.get("scale").size() == 0) {
						container.get("scale").removeAllContainerFilters();
						System.out.println(scaleUnit);
						Object newScale = container.get("scale").addItem();
						scaleItem = container.get("scale").getItem(newScale);

						scaleItem.getItemProperty("scaleName").setValue(
								scaleUnit);

						container.get("scale").commit();
					} else {
						System.out.println(container.get("scale").getIdByIndex(
								0));
						scaleId = Integer.parseInt(container.get("scale")
								.getIdByIndex(0).toString());
					}

					container.get("scale").removeAllContainerFilters();

				}

				container.get("phenotype").commit();

//				variateItem.getItemProperty("traitId").setValue(traitId);
//
//				if (!scaleUnit.equals("") && scaleUnit != null)
//					variateItem.getItemProperty("scaleId").setValue(scaleId);
//
//				container.get("variates").commit();

			}
		}

		return true;
	}

}
