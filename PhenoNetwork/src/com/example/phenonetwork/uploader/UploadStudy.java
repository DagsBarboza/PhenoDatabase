package com.example.phenonetwork.uploader;

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
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;

public class UploadStudy {
	
	static int rowId;
	
	
	public static String upload(SQLContainer sqlContainer, Sheet sheet,
			HashMap<String, Cell> label) throws UnsupportedOperationException, SQLException {
		Item item2;

		String studyName = "";
		
		

		sqlContainer.addRowIdChangeListener(new RowIdChangeListener() {

			@Override
			public void rowIdChange(RowIdChangeEvent row) {
				System.out.println("row" + row.getNewRowId().toString());
				rowId = Integer.parseInt(row.getNewRowId().toString());

			}
		});

		Object study = sqlContainer.addItem();
		item2 = sqlContainer.getItem(study);

		Cell input;

		if (label.get("Investigator") != null) {
			input = (Cell) label.get("Investigator");
			item2.getItemProperty("investigator").setValue(
					sheet.getCell(input.getColumn() + 1, input.getRow())
							.getContents());
		}
		if (label.get("Sampling Date") != null) {
			input = (Cell) label.get("Sampling Date");

			DateFormat formatter = new SimpleDateFormat("mm/dd/yy");

			java.util.Date fromDate = new java.util.Date();
			try {
				fromDate = formatter.parse(sheet.getCell(input.getColumn() + 1,
						input.getRow()).getContents());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			java.sql.Date sqlDate = new java.sql.Date(fromDate.getTime());
			item2.getItemProperty("samplingDate").setValue(sqlDate);
		}
		if (label.get("Study") != null) {
			
			input = (Cell) label.get("Study");
			System.out.println("inserting.."
					+ sheet.getCell(input.getColumn() + 1, input.getRow())
							.getContents());
			item2.getItemProperty("studyName").setValue(
					sheet.getCell(input.getColumn() + 1, input.getRow())
							.getContents());
			
		
			studyName = sheet.getCell(input.getColumn() + 1, input.getRow())
					.getContents();
		}

		sqlContainer.commit();
		
		return studyName;
		
		
	}

}
