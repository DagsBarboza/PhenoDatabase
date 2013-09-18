package com.example.test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class testUploadDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String input = "2012-06-12";

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		try {
			date = sdf1.parse(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date sqlStartDate = new Date(date.getTime());

		System.out.println(sqlStartDate);

	}

}
