package com.example.test;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			Workbook workbook = Workbook.getWorkbook(new File("E:\\PRAY Indica Accessions.xls"));
			Sheet sheet = workbook.getSheet(0);

			/**
			 * Getting per value for the whole row
			 * */
			
//			Cell[] cells = sheet.getRow(1);
//			
//			for (Cell cell : cells){
//				System.out.println(cell.getContents());
//				
//				
//				
//			}
			
			Cell cell = sheet.getCell("A1");
			System.out.println(cell.getContents());
				
			workbook.close();
		}catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 

	}

}
