package com.example.phenonetwork.uploader;

import java.sql.SQLException;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;

import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;

public class UploadTraits {

	static int traitId;
	
	public static void upload(SQLContainer sqlContainer, Sheet sheet,
			HashMap<String, Cell> label) throws UnsupportedOperationException, SQLException {
		Item item;
		
		

		sqlContainer.addRowIdChangeListener(
				new RowIdChangeListener() {

					@Override
					public void rowIdChange(RowIdChangeEvent event) {
						traitId = Integer.parseInt(event.getNewRowId()
								.toString());
					}
				});

//		container.get("scale").addRowIdChangeListener(
//				new RowIdChangeListener() {
//
//					@Override
//					public void rowIdChange(RowIdChangeEvent event) {
//						scaleId = Integer.parseInt(event.getNewRowId()
//								.toString());
//					}
//				});

		//String scaleUnit = "";

		// write data to container
		/**
		 * Change this:
		 * Change to accept dynamic values
		 * 
		 * Values are static and following precisely the excel template 
		 * 
		 * */
		
		int row = 8;
		int colVar = 0;
		int colTermName = 2;
		int colDef = 3;
		int maxRow = 57;
		
	
		for(int i=row; i<=maxRow; i++) {
			
		
				
				
				Object newTrait = sqlContainer.addItem();
				item = sqlContainer.getItem(newTrait);


				item.getItemProperty("traitName").setValue(sheet.getCell(colTermName,i ).getContents());

				//Cell acc = sheet.findCell(cell.getContents());
				item.getItemProperty("traitAcronym").setValue(sheet.getCell(colVar, i).getContents());
				item.getItemProperty("traitDescription").setValue(sheet.getCell(colDef, i).getContents());

				// adding to scale

//				scaleUnit = sheet.getCell(acc.getColumn() + 2, acc.getRow())
//						.getContents();

//				if (!scaleUnit.equals("") && scaleUnit != null) {
//
//					container.get("scale").addContainerFilter(
//							new SimpleStringFilter("scaleName", scaleUnit,
//									true, false));
//
//					if (container.get("scale").size() == 0) {
//						container.get("scale").removeAllContainerFilters();
//						Object newScale = container.get("scale").addItem();
//						scaleItem = container.get("scale").getItem(newScale);
//
//						scaleItem.getItemProperty("scaleName").setValue(
//								scaleUnit);
//
//						container.get("scale").commit();
//					} else {
//						System.out.println(container.get("scale").getIdByIndex(
//								0));
//						scaleId = Integer.parseInt(container.get("scale")
//								.getIdByIndex(0).toString());
//					}
//
//					container.get("scale").removeAllContainerFilters();
//
//				
				
			
		}
//		System.out.println("saving trait...");
		sqlContainer.commit();

		
	}

			
		

	
}
