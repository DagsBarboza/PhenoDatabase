package com.example.phenonetwork.db;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class GetVariate {

	SQLContainer container;

	public GetVariate(SQLContainer sqlContainer) {
		container = sqlContainer;
	}

	public int getVariateIdByTraitStudyDataset(Object studyName, int datasetId,
			Object traitId) {

		int variateId = 0;

		container.removeAllContainerFilters();

		container.addContainerFilter(new And(new Equal((Object) "studyName",
				(Object) studyName), new Equal((Object) "datasetId",
				(Object) datasetId), new Equal((Object) "traitId",
				(Object) traitId)));

		if (container.size() > 0)
			variateId = Integer.parseInt(container.getIdByIndex(0).toString());

		container.removeAllContainerFilters();
		
		return variateId;

	}

	private void startInit(Object studyName, int datasetId, Object traitId) {

	}

}
