package com.example.phenonetwork.uploader;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

import com.example.phenonetwork.data.ExcelDefinitionColumns;
import com.example.phenonetwork.data.VarietyNameDefinition;
import com.example.phenonetwork.db.GetVariate;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class UploadObservationDate {

	@SuppressWarnings("rawtypes")
	public static void upload(HashMap<String, SQLContainer> container,
			Sheet sheet, HashMap<String, HashMap> trait, Object studyName,
			int datasetId) {

		HashMap<String, Integer> traitInSheet = new HashMap<String, Integer>();
		HashMap<String, Integer> traitIds = new HashMap<String, Integer>();
		HashMap<String, Integer> variateValues = new HashMap<String, Integer>();

		SQLContainer numericTable = container.get("observationDate");

		traitIds = trait.get("traitIds");
		traitInSheet = trait.get("traitList");

		int row = 1;

		// FETCH ALL IDENTIFICATION COLUMNS FOR VARIETY
		HashMap<String, Integer> identification = new HashMap<String, Integer>();

		for (Cell cell : sheet.getRow(0)) {
			if (ExcelDefinitionColumns.contains(cell.getContents()
					.toUpperCase())) {
				identification.put(cell.getContents().toUpperCase().trim(),
						cell.getColumn());

			}
		}

		// GET VARIATE VALUE
		Iterator it = traitIds.entrySet().iterator();

		GetVariate gv = new GetVariate(container.get("variates"));

		int variateId = 0;
		while (it.hasNext()) {

			Map.Entry entry = (Map.Entry) it.next();

			variateId = gv.getVariateIdByTraitStudyDataset(studyName,
					datasetId, Integer.parseInt(entry.getValue().toString()));

			variateValues.put(entry.getKey().toString(), variateId);
		}

		// GET COLUMNS WHERE VARIETY VALUE WILL BE TAKEN
		Item item;

		String varietyName = "";

		for (VarietyNameDefinition vnd : VarietyNameDefinition.values()) {

			if (identification.containsKey(vnd.toString())) {

				varietyName = vnd.toString();
			}
		}

		while (row < sheet.getRows()) {

			Iterator it2 = traitInSheet.entrySet().iterator();

			while (it2.hasNext()) {

				Map.Entry entry = (Map.Entry) it2.next();

				Object observation = numericTable.addItem();
				item = numericTable.getItem(observation);

				item.getItemProperty("varietyId").setValue(
						sheet.getCell(identification.get(varietyName), row)
								.getContents().toString());

				if (identification.get("REP") != null)
					item.getItemProperty("replicate").setValue(
							sheet.getCell(identification.get("REP"), row)
									.getContents().toString());

				item.getItemProperty("variateId").setValue(
						Integer.parseInt(variateValues.get(entry.getKey())
								.toString()));

				if (!sheet
						.getCell(Integer.parseInt(entry.getValue().toString()),
								row).getContents().toString().equals(""))
					item.getItemProperty("value").setValue(
							getDate(sheet
									.getCell(
											Integer.parseInt(entry.getValue()
													.toString()), row)
									.getContents().toString()));

			}
			row++;

		}

		try {
			numericTable.commit();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Date getDate(String inputDate) {

		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
		java.util.Date date = new java.util.Date();
		try {
			date = sdf1.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date sqlStartDate = new Date(date.getTime());
		return sqlStartDate;
	}

}
