package com.example.phenonetwork.uploader;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

import com.example.phenonetwork.db.GetTrait;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;

public class UploadVariate {

	@SuppressWarnings("rawtypes")
	public static HashMap<String, HashMap> upload(
			HashMap<String, SQLContainer> container, Object itemId,
			Sheet sheet, int datasetId) {

		SQLContainer variateTable = container.get("variates");
		SQLContainer traitTable = container.get("trait");

		variateTable.addRowIdChangeListener(new RowIdChangeListener() {
			
			@Override
			public void rowIdChange(RowIdChangeEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		HashMap<String, HashMap> traitMap = new HashMap<String, HashMap>();
		
		
		HashMap<String, Integer> traitList = new HashMap<String, Integer>();
		HashMap<String, Integer> traitIds = new HashMap<String, Integer>();

		GetTrait traitsObj = new GetTrait(traitTable);

		for (Cell cell : sheet.getRow(0)) {

			if (traitsObj.countTraitByName(cell.getContents()) > 0
					|| traitsObj.countTraitByAcronym(cell.getContents()) > 0) {

				traitList.put(cell.getContents(), cell.getColumn());

				int traitId = traitsObj.getTraitIdByName(cell.getContents());

				traitIds.put(cell.getContents(), traitId);
			}
		}

		// Iterate through the list of traits

		Iterator it = traitIds.entrySet().iterator();

		Item item2;

		while (it.hasNext()) {

			Map.Entry entry = (Map.Entry) it.next();

			Object variate = variateTable.addItem();
			item2 = variateTable.getItem(variate);

			item2.getItemProperty("studyName").setValue(itemId);

			item2.getItemProperty("datasetId").setValue(datasetId);

			item2.getItemProperty("traitId").setValue(
					(Integer) entry.getValue());

		}

		try {
			variateTable.commit();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL error: uploading of variate values");
			e.printStackTrace();
		}

		traitMap.put("traitList", traitList);
		traitMap.put("traitIds", traitIds);
		
		return traitMap;

	}

}
