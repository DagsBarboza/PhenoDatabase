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

	static int rowId;

	@SuppressWarnings("unchecked")
	public static Object uploadStudyDefinition(SQLContainer sqlContainer,
			Sheet sheet, HashMap<String, Cell> label)
			throws UnsupportedOperationException, SQLException {

		Item item2;

		System.out.println("Now Uploading in the Database");
		// sqlContainer.setAutoCommit(true);

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
		if (label.get("Study Name") != null) {
			input = (Cell) label.get("Study Name");
			item2.getItemProperty("studyName").setValue(
					sheet.getCell(input.getColumn() + 1, input.getRow())
							.getContents());
		}

		sqlContainer.commit();
		return rowId;
	}

	@SuppressWarnings("unchecked")
	public static void uploadAccession(SQLContainer sqlContainer, Sheet sheet,
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

	@SuppressWarnings("unchecked")
	public static void uploadGermplasm(SQLContainer sqlContainer, Sheet sheet,
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

	public static Boolean uploadDateset(
			HashMap<String, SQLContainer> container, Sheet sheet,
			HashMap<String, Cell> label, Object itemId2) {

		int columnRows = 0;

		// Fetch in study table using studyId that is inserted
		// Item item = container.get("study").getItem(new RowId(new Object[]
		// {itemId2}));

		Cell cell;
		HashMap<Integer, String> exist = new HashMap<Integer, String>();
		HashMap<Integer, String> notFound = new HashMap<Integer, String>();

		Item variateItem;
		while (columnRows < sheet.getColumns()) {

			cell = sheet.getCell(columnRows, 0);

			container.get("phenotype").addContainerFilter(
					new Or(
							new Like("traitName", "%" + cell.getContents()
									+ "%"), new Like("traitAcronym", "%"
									+ cell.getContents() + "%")));

			if (container.get("phenotype").size() > 0) {
				exist.put(columnRows, container.get("phenotype")
						.getIdByIndex(0).toString());
				// System.out.println("ID>>"+
				// container.get("phenotype").getIdByIndex(0));
			} else
				notFound.put(columnRows, null);

			

			container.get("phenotype").removeAllContainerFilters();

			/*
			 * Adding variate Item
			 */

			if (exist.get(columnRows) != null
					&& !exist.get(columnRows).equals("")) {
				
				Object variateObj = container.get("variates").addItem();

				variateItem = container.get("variates").getItem(variateObj);
				
				variateItem.getItemProperty("traitId").setValue(Integer.parseInt(exist.get(columnRows).toString()));

				variateItem.getItemProperty("study_studyId").setValue(itemId2);

				
			}

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

		int row = 1;
		int column;
		String germplasmId = "";
		while (row < sheet.getRows()) {

			column = 1;

			// TO DO: this is still somewhat hardcoded. replace it with
			// searching of germplasm IDs or accession ids in an excel file
			if (sheet.getCell(0, 0).getContents().equals("AccessionNo")) {
				container.get("accession").addContainerFilter("accessionId",
						sheet.getCell(0, row).getContents(), true, false);
				if (container.get("accession").size() > 0)
					germplasmId = container.get("accession").getIdByIndex(0)
							.toString();

				Item item2;

				if (!germplasmId.equals("") && germplasmId != null) {
					while (column < sheet.getColumns()) {
						Object observation = container.get("observation")
								.addItem();

						item2 = container.get("observation").getItem(
								observation);

						item2.getItemProperty("germplasmName").setValue(
								germplasmId);

						// VARIATE ITEM (BUILDING)
						
						container.get("variates")
								.addContainerFilter(
										new And(new SimpleStringFilter(
												"traitId", exist.get(column),
												true, false),
												new SimpleStringFilter(
														"study_studyId",
														itemId2.toString(),
														true, false)));

						item2.getItemProperty("variates_variateId").setValue(
								Integer.parseInt(container.get("variates")
										.getIdByIndex(0).toString()));

						container.get("variates").removeAllContainerFilters();

						// END VARIATE ITEM (BUILDING)

						System.out.println("adding: "+ germplasmId + ":"+ Integer.parseInt(container.get("variates")
										.getIdByIndex(0).toString()));
						
						item2.getItemProperty("value").setValue(
								sheet.getCell(column, row).getContents());

						column++;
					}

					try {
						container.get("observation").commit();
					} catch (UnsupportedOperationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			container.get("accession").removeAllContainerFilters();

			row++;

		}

		return true;

	}
}
