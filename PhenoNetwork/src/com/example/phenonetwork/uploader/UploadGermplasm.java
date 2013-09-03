package com.example.phenonetwork.uploader;

import java.sql.SQLException;
import java.util.HashMap;

import jxl.Sheet;

import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class UploadGermplasm {
	
	@SuppressWarnings("unchecked")
	public static void upload(SQLContainer sqlContainer, Sheet sheet,
			HashMap<String, String> label)
			throws UnsupportedOperationException, SQLException {

		Item item2;

		int row = 1;
		while (row < sheet.getRows()) {
			Object germplasm = sqlContainer.addItem();
			item2 = sqlContainer.getItem(germplasm);

			item2.getItemProperty("germplasmName").setValue(
					sheet.getCell(Integer.parseInt(label.get("variety name")),
							row).getContents());

			item2.getItemProperty("varietalGroup").setValue(
					sheet.getCell(
							Integer.parseInt(label.get("varietal group")), row)
							.getContents());

			item2.getItemProperty("typeOfSamples")
					.setValue(
							sheet.getCell(
									Integer.parseInt(label
											.get("type of samples")), row)
									.getContents());
			row++;
		}

		sqlContainer.commit();

	}

}
