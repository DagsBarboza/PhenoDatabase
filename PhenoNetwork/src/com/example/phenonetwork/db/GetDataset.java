package com.example.phenonetwork.db;

import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.filter.Compare.Equal;

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

}
