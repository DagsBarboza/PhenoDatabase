package com.example.phenonetwork.uploader.utils;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;

import com.vaadin.data.Item;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;

public class DbUploader {

	
	@SuppressWarnings("unchecked")
	public static void uploadAccession(SQLContainer sqlContainer, Sheet sheet,
			HashMap<String, String> label)
			throws UnsupportedOperationException, SQLException {

		Item item2;

		int row = 1;

		while (row < sheet.getRows()) {
			System.out.println("ROW"+ row);
			Object germplasm = sqlContainer.addItem();
			item2 = sqlContainer.getItem(germplasm);

			item2.getItemProperty("germplasmName").setValue(
					sheet.getCell(Integer.parseInt(label.get("variety name")),
							row).getContents());
			item2.getItemProperty("accessionId").setValue(
					sheet.getCell(Integer.parseInt(label.get("irgc acc. no.")),
							row).getContents());
			item2.getItemProperty("taxonomy").setValue(
					sheet.getCell(Integer.parseInt(label.get("taxonomy")), row)
							.getContents());
			item2.getItemProperty("sourceCountry").setValue(
					sheet.getCell(
							Integer.parseInt(label.get("source country")), row)
							.getContents());
			item2.getItemProperty("donorCountry").setValue(
					sheet.getCell(Integer.parseInt(label.get("donor country")),
							row).getContents());
			item2.getItemProperty("biologicalStatus").setValue(
					sheet.getCell(
							Integer.parseInt(label.get("biological status")),
							row).getContents());

			item2.getItemProperty("culturalType").setValue(
					sheet.getCell(Integer.parseInt(label.get("cultural type")),
							row).getContents());
			item2.getItemProperty("specialCharacteristic").setValue(
					sheet.getCell(
							Integer.parseInt(label
									.get("special characteristic")), row)
							.getContents());
			row++;
		}

		sqlContainer.commit();
	}

	

	
}
