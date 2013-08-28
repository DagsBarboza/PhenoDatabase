package com.example.phenonetwork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;

import jxl.read.biff.BiffException;

import com.example.phenonetwork.uploader.utils.ExcelTemplateParser;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class MyUploader implements Receiver, SucceededListener {

	public File file;

	private Boolean uploaded;

	public String fileDirectory;

	public String data;
	
	private HashMap<String, SQLContainer> container;

	public MyUploader(HashMap<String, SQLContainer> sqlContainer,
			String dataString) {
		container = sqlContainer;
		data = dataString;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {

		try {
			if (data.equals("germplasm"))
				uploaded = ExcelTemplateParser.checkGermplasmIfUploaded(
						fileDirectory, container, data);
			if (data.equals("phenotype"))
				uploaded = ExcelTemplateParser.checkPhenotypeIfUploaded(
						fileDirectory, container, data);
			if (data.equals("accession"))
				uploaded = ExcelTemplateParser.checkPhenotypeIfUploaded(
						fileDirectory, container, data);
			if (data.equals("study"))

				uploaded = ExcelTemplateParser.checkStudyIfUploaded(
						fileDirectory, container, data);
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!uploaded) {
			File delFile = new File(fileDirectory);

			if (delFile.delete()) {
				System.out.println(delFile.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed."
						+ fileDirectory);
			}

		}
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {

		FileOutputStream fos = uploadFileToDirectory(filename);

		return fos;

	}

	private FileOutputStream uploadFileToDirectory(String filename) {

		FileOutputStream fos = null;

		String location = "E:\\phenodb_files";
		File folder = new File(location);
		boolean created = true;
		try {
			// Open the file for writing.
			if (!folder.exists())
				created = folder.mkdir();

			if (created) {
				file = new File(location + "" + filename);
				fos = new FileOutputStream(file);
				fileDirectory = location + "" + filename;
			}
		} catch (final java.io.FileNotFoundException e) {
			Notification.show("Could not open file<br/>", e.getMessage(),
					Notification.TYPE_ERROR_MESSAGE);
			return null;
		}

		return fos;

	}

}
