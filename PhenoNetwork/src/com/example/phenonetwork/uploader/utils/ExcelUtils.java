package com.example.phenonetwork.uploader.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.phenonetwork.data.StudyDefinitionColumns;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {

	public static void excelDownload(ArrayList traitSelected) throws IOException {
		WritableWorkbook workBookWrite = Workbook.createWorkbook(new File(
				"E:\\Dags\\TemplateDownload\\TemplateDownload.xls"));
		WritableSheet writableSheet = workBookWrite
				.createSheet("Study Definition", 0);
		WritableSheet writableSheet2 = workBookWrite
				.createSheet("Traits And Values", 1);
		
		WritableCellFormat studyDefFormat = new WritableCellFormat();
		WritableCellFormat TraitsDefFormat = new WritableCellFormat();
		
		try {
			studyDefFormat.setBackground(Colour.LIGHT_GREEN);
			studyDefFormat.setBorder(Border.RIGHT, BorderLineStyle.NONE);
			studyDefFormat.setLocked(true);
			
			TraitsDefFormat.setBackground(Colour.YELLOW);
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// STUDY DEFINITION TEMPLATE
		
		int i = 0;
		for (StudyDefinitionColumns p : StudyDefinitionColumns.values()) {
			try {
				writableSheet.addCell(new Label(0, i, p.toString().replace("_", " "),studyDefFormat));
				
			} catch (RowsExceededException e) {
				System.err.println("EXCEL Utils (STUDY DEFINITION): Rows Exceeded");
				e.printStackTrace();
			} catch (WriteException e) {
				System.err.println("EXCEL Utils (STUDY DEFINITION): WriteException");
				e.printStackTrace();
			}
			i++;
		}
		
		// TRAITS AND VALUES
		i=1;
		for (Object trait : traitSelected){
			try {
				writableSheet2.addCell(new Label(i, 0, trait.toString(),TraitsDefFormat));
			} catch (RowsExceededException e) {
				System.err.println("EXCEL Utils (TRAITS): Rows Exceeded");
				e.printStackTrace();
			} catch (WriteException e) {
				System.err.println("EXCEL Utils (TRAITS): WriteException");
				e.printStackTrace();
			}
			i++;
		}
		
		workBookWrite.write();
		try {
			workBookWrite.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}





