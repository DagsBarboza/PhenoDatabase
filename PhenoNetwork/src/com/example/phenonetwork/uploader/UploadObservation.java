package com.example.phenonetwork.uploader;

import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;

import com.example.phenonetwork.uploader.utils.ExcelHeaderParser;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class UploadObservation {

	public static void upload(HashMap<String, SQLContainer> container,
			Sheet sheet, HashMap<String, Cell> label, Object itemId) {
		
		int row = 1;
		int column;
		String germplasmId = "";
		
		for (Cell cell : sheet.getRow(0))
		{
			System.out.println("values "+ cell.getContents());
		}
//		while (row < sheet.getRows()) {
//
//			column = 1;
//
//			// TO DO: this is still somewhat hardcoded. replace it with
//			// searching of germplasm IDs or accession ids in an excel file
//			if (sheet.getCell(0, 0).getContents().equals("AccessionNo")) {
//				container.get("accession").addContainerFilter("accessionId",
//						sheet.getCell(0, row).getContents(), true, false);
//				if (container.get("accession").size() > 0)
//					germplasmId = container.get("accession").getIdByIndex(0)
//							.toString();
//
//				Item item2;
//
//				if (!germplasmId.equals("") && germplasmId != null) {
//					while (column < sheet.getColumns()) {
//						Object observation = container.get("observation")
//								.addItem();
//
//						item2 = container.get("observation").getItem(
//								observation);
//
//						item2.getItemProperty("germplasmName").setValue(
//								germplasmId);
//
//						// VARIATE ITEM (BUILDING)
//
//						//Uncomment this :D
////						container.get("variates")
////								.addContainerFilter(
////										new And(new SimpleStringFilter(
////												"traitId", exist.get(column),
////												true, false),
////												new SimpleStringFilter(
////														"studyName",
////														itemId2.toString(),
////														true, false)));
////
////						item2.getItemProperty("variates_variateId").setValue(
////								Integer.parseInt(container.get("variates")
////										.getIdByIndex(0).toString()));
////
////						container.get("variates").removeAllContainerFilters();
//
//						// END VARIATE ITEM (BUILDING)
//
//						System.out.println("adding: "
//								+ germplasmId
//								+ ":"
//								+ Integer.parseInt(container.get("variates")
//										.getIdByIndex(0).toString()));
//
//						item2.getItemProperty("value").setValue(
//								sheet.getCell(column, row).getContents());
//
//						column++;
//					}
//
//					try {
//						container.get("observation").commit();
//					} catch (UnsupportedOperationException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//			}
//
//			container.get("accession").removeAllContainerFilters();
//
//			row++;
//
//		}

		
	}

}
