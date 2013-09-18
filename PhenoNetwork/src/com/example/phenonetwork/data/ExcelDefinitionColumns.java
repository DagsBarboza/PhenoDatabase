package com.example.phenonetwork.data;

public enum ExcelDefinitionColumns {
	
	DESIGNATION, ACCESSION, REP;
	
	public static boolean contains(String s) {
        try {
        	ExcelDefinitionColumns.valueOf(s);
            return true;
        } catch (Exception e) {
            return false;
        }
	}
}
