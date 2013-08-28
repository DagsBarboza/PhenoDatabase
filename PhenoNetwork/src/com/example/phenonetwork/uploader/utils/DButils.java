package com.example.phenonetwork.uploader.utils;

import java.sql.SQLException;
import java.util.HashMap;

import com.vaadin.data.Container;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.server.ClientConnector.DetachListener;

public class DButils {

	public static HashMap<String, SQLContainer> createContainer() {
		try {
			HashMap<String, SQLContainer> sqlContainers = new HashMap<String, SQLContainer>();

			final JDBCConnectionPool pool = new SimpleJDBCConnectionPool(
					"com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/prototype_db", "root", "dags");

			TableQuery tq = new TableQuery("trait", pool);
			SQLContainer container = new SQLContainer(tq);
			sqlContainers.put("phenotype", container);

			TableQuery tq2 = new TableQuery("germplasm", pool);
			SQLContainer container2 = new SQLContainer(tq2);
			sqlContainers.put("germplasm", container2);

//			TableQuery tq3 = new TableQuery("accession", pool);
//			SQLContainer container3 = new SQLContainer(tq3);
//			sqlContainers.put("accession", container3);

			TableQuery tq4 = new TableQuery("study", pool);
			SQLContainer container4 = new SQLContainer(tq4);
			sqlContainers.put("study", container4);

//			TableQuery tq5 = new TableQuery("observation", pool);
//			SQLContainer container5 = new SQLContainer(tq5);
//			sqlContainers.put("observation", container5);

			TableQuery tq6 = new TableQuery("variates", pool);
			SQLContainer container6 = new SQLContainer(tq6);
			sqlContainers.put("variates", container6);

			TableQuery tq7 = new TableQuery("scalemeasurement", pool);
			SQLContainer container7 = new SQLContainer(tq7);
			sqlContainers.put("scale", container7);

//			FreeformQuery tq8 = new FreeformQuery("call testProc()", pool,
//					"germplasmName");
//			SQLContainer container8 = new SQLContainer(tq8);
//			sqlContainers.put("viewTest", container8);

			return sqlContainers;
		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}

	public static String getStringQuery(String itemId,
			HashMap<String, SQLContainer> container) {

		String concatString = "";

		container.get("variates").addContainerFilter("study_studyId", itemId,
				false, false);
		concatString = getConcatString(itemId, container);
		container.get("variates").removeAllContainerFilters();
		// Object obj = container.get("phenotype").getIdByIndex(1);
		// System.out.println(container.get("phenotype").getContainerProperty(obj,
		// "traitName"));

		String strQuery = "select o.germplasmName, "
				+ concatString
				+ "from trait t left join variates v on t.traitId = v.traitId "
				+ "right join observation o on o.variates_variateId=v.variateId "
				+ "where v.study_studyid=" + itemId
				+ " group by o.germplasmName";

		return strQuery;

	}

	private static String getConcatString(String itemId,
			HashMap<String, SQLContainer> container) {
		String traitName = "";
		StringBuilder concatString = new StringBuilder();
		int i = 0;
		SQLContainer traitContainer = container.get("phenotype");
		SQLContainer variateContainer = container.get("variates");

		while (i < container.get("variates").size()) {
			System.out.println("traitId"
					+ variateContainer.getContainerProperty(
							variateContainer.getIdByIndex(i), "traitId")
							.toString());
			traitContainer.addContainerFilter(
					"traitId",
					variateContainer.getContainerProperty(
							variateContainer.getIdByIndex(i), "traitId")
							.toString(), false, false);
			if (concatString.length() != 0)
				concatString.append(",");
			concatString.append("Max(if(t.traitName = '"
					+ traitContainer.getContainerProperty(
							traitContainer.getIdByIndex(0), "traitName")
							.toString()
					+ "', value, NULL)) as '"
					+ traitContainer.getContainerProperty(
							traitContainer.getIdByIndex(0), "traitName")
							.toString() + "'");

			traitContainer.removeAllContainerFilters();
			i++;
		}
		System.out.println(concatString);

		return concatString.toString();
	}

	public static Container getFreeFormQuery(String itemId,
			HashMap<String, SQLContainer> container2) {

		JDBCConnectionPool pool;
		SQLContainer container = null;

		try {
			pool = new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/prototype_db", "root", "dags");
			FreeformQuery tq = new FreeformQuery(getStringQuery(itemId,
					container2), pool, "germplasmName");
			container = new SQLContainer(tq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return container;
	}

}
