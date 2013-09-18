package com.example.phenonetwork.db;

import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.filter.Compare.Equal;

public class GetStudy {

	private static SQLContainer container;
	
	public GetStudy(SQLContainer sqlContainer) {
		container = sqlContainer;
	}

	public int countStudyByName(Sheet sheet,
			HashMap<String, Cell> excelHeader) {
		
		String studyName = sheet.getCell(excelHeader.get("Study").getColumn() + 1,
				excelHeader.get("Study").getRow()).getContents();
		container.removeAllContainerFilters();
		container.addContainerFilter(new Equal((Object) "studyName", (Object) studyName));
		
		int studySize = container.size();
		container.removeAllContainerFilters();
		
		return studySize;
	}

}
