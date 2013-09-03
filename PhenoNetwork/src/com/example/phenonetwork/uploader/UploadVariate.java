package com.example.phenonetwork.uploader;

import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;

import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class UploadVariate {
	
	public static void upload(HashMap<String, SQLContainer> container,
			Sheet sheet, HashMap<String, Cell> label, Object itemId2) {

		int columnRows = 0;

		Cell cell;
		
		HashMap<Integer, String> notFound = new HashMap<Integer, String>();

		Item variateItem;
		while (columnRows < sheet.getColumns()) {

			cell = sheet.getCell(columnRows, 0);

			container.get("trait").addContainerFilter(
					new Or(
							new Like("traitName", "%" + cell.getContents()
									+ "%"), new Like("traitAcronym", "%"
									+ cell.getContents() + "%")));

//			if (container.get("trait").size() > 0) {
//				exist.put(columnRows, container.get("phenotype")
//						.getIdByIndex(0).toString());
//				// System.out.println("ID>>"+
//				// container.get("phenotype").getIdByIndex(0));
//			} else
//				notFound.put(columnRows, null);

			container.get("trait").removeAllContainerFilters();

			/*
			 * Adding variate Item
			 */

//			if (exist.get(columnRows) != null
//					&& !exist.get(columnRows).equals("")) {
//
//				Object variateObj = container.get("variates").addItem();
//
//				variateItem = container.get("variates").getItem(variateObj);
//
//				variateItem.getItemProperty("traitId").setValue(
//						Integer.parseInt(exist.get(columnRows).toString()));
//
//				variateItem.getItemProperty("studyName").setValue(itemId2);
//
//			}

			// Adding Variate End//

			columnRows++;
		}

		try {
			container.get("variates").commit();
		} catch (UnsupportedOperationException e) {
			System.err
					.println("Error uploading Dataset (DBUPLOADER CLASS). Unsupported operation");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err
					.println("Error uploading Dataset (DBUPLOADER CLASS). There might be a problem with SQL");
			e.printStackTrace();
		}
	}

}
