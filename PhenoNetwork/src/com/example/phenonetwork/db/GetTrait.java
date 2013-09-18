package com.example.phenonetwork.db;

import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class GetTrait {

	private SQLContainer traitTable;

	public GetTrait(SQLContainer sqlContainer) {
		traitTable = sqlContainer;
	}

	public int countTraitByAcronym(String contents) {
		int traitSize;
		startInit("traitAcronym", contents);
		traitSize = traitTable.size();
		traitTable.removeAllContainerFilters();
		return traitSize;
	}

	public int countTraitByName(String contents) {
		int traitSize;
		startInit("traitName", contents);
		traitSize = traitTable.size();
		traitTable.removeAllContainerFilters();
		return traitSize;
	}

	private void startInit(String property, String contents) {
		traitTable.removeAllContainerFilters();
		traitTable.addContainerFilter(property, contents, true, true);
		
	}

	public Integer getTraitIdByName(String contents) {
		RowId traitId;
		
		startInit("traitName", contents);
		if (traitTable.size() == 0)
			startInit("traitAcronym", contents);
		
		
		traitId = (RowId) traitTable.getIdByIndex(0);
		
		traitTable.removeAllContainerFilters();
		
		return Integer.parseInt(traitId.toString());
		
	}

	public void getTraitIdByAcronym(String contents) {
		// TODO Auto-generated method stub
		
	}
	
	

}
