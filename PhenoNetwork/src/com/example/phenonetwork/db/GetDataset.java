package com.example.phenonetwork.db;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class GetDataset {

	private static SQLContainer container;

	public GetDataset(SQLContainer sqlContainer) {
		container = sqlContainer;
	}

	public static int countDatasetByStudyName(Object itemId, String name) {

		container.removeAllContainerFilters();
		container.addContainerFilter(new And(new Equal((Object) "studyName",
				itemId), new Equal((Object) "dsName", (Object) name)));

		int dsSize = container.size();
		container.removeAllContainerFilters();

		return dsSize;
	}

	public int getDatasetIdByName(Object itemId, String name) {
		container.removeAllContainerFilters();
		container.addContainerFilter(new And(new Equal((Object) "studyName",
				itemId), new Equal((Object) "dsName", (Object) name)));

		int dsId = Integer.parseInt(container.getIdByIndex(0).toString());
		container.removeAllContainerFilters();
		return dsId;
	}

	public static List getDatasetByStudyName(String studyName) {
		List result = new ArrayList();
		container.removeAllContainerFilters();
		container.addContainerFilter(new SimpleStringFilter("studyName", studyName, true, true));
		result = container.getItemIds(0, container.size());
		
		return result;
	}

	public String getDatasetNameById(RowId id) {
		container.removeAllContainerFilters();
		String name = container.getContainerProperty(id, "dsName").toString();
		return name;
		
	}

}
