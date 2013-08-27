package com.example.phenonetwork.uploader.utils;

import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

public class ExcelHeaderParser {

	public static HashMap<String, String> getLabelLocation(Sheet sheet,
			HashMap<String, String> label) {
		
		Cell[] cells = sheet.getRow(0);

		for (Cell cell : cells) {
			if (label.containsKey(cell.getContents().toLowerCase()))
				label.put(cell.getContents().toLowerCase(),
						Integer.toString(cell.getColumn()));
		}

		return label;
	}
	
	public static HashMap<String, Cell> getLabelValue(Sheet sheet,
			HashMap<String, Cell> label) {
		

		for (Map.Entry<String, Cell> entry : label.entrySet()) {
			Cell cell = sheet.findCell(entry.getKey().toString());
			
			if (cell != null)
				label.put(entry.getKey().toString(), cell);
		}

		return label;
	}


}
