package com.example.phenonetwork.uploader;

import java.sql.SQLException;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;

public class UploadDataset {

	static int rowId;
	
	public static Integer upload(SQLContainer sqlContainer, Object itemId, String datasetName) {
		
		Object datasetObj = sqlContainer.addItem();
		Item item2 = sqlContainer.getItem(datasetObj);
		
		sqlContainer.addRowIdChangeListener(new RowIdChangeListener() {
			@Override
			public void rowIdChange(RowIdChangeEvent row) {
				rowId = Integer.parseInt(row.getNewRowId().toString());
			}
		});
		
		
		item2.getItemProperty("studyName").setValue(itemId);
		
		if (datasetName != null)
			item2.getItemProperty("dsName").setValue(datasetName);
		
		
		try {
			sqlContainer.commit();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Error: uploading of dataset "+ e.getMessage());
			e.printStackTrace();
		}
		
		return rowId;
	}

}
