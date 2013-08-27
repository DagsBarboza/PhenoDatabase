package com.example.phenonetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.example.phenonetwork.uploader.utils.ExcelUtils;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class PhenotypeView extends CustomComponent implements View {

	public PhenotypeView(HashMap<String, SQLContainer> container) {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeUndefined();
		layout.setWidth("100%");

		setCompositionRoot(layout);

		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setMargin(true);
		vLayout.addComponent(new Label(
				"This page is for Phenotype Uploads and display"));
		vLayout.addComponent(new Label("<br>Select file to upload:",
				ContentMode.HTML));
		Upload upload = new Upload();
		vLayout.addComponent(upload);
		vLayout.setSizeFull();

		VerticalLayout vLayout2 = new VerticalLayout();
		vLayout2.setSizeFull();
		vLayout2.setMargin(true);

		final TwinColSelect tcs = new TwinColSelect();
		tcs.setSizeFull();
		int i = 0;
		
		while (i < container.get("phenotype").size()) {
			tcs.addItem(container.get("phenotype").getContainerProperty(
					container.get("phenotype").getIdByIndex(i), "traitName"));
			i++;
		}

		
		vLayout2.addComponent(tcs);
		vLayout2.addComponent(new Button("Download Template", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent traitSelect) {
				
				ArrayList traitSelected = new ArrayList();
				traitSelected.addAll((Collection<String>) tcs.getValue());
				try {
					ExcelUtils.excelDownload(traitSelected);
				} catch (IOException e) {
					System.err.println("PROBLEM IN ExcelUtils , excel Download");
					e.printStackTrace();
				}
				
			}
		}));
		
		// TableCreator tb = new TableCreator(container, "phenotype");
		// vLayout2.addComponent(tb);

		layout.addComponent(vLayout2);
		layout.addComponent(vLayout);

		// layout.setExpandRatio(vLayout, 1);
		// layout.setExpandRatio(vLayout2, 2);

		MyUploader uploader = new MyUploader(container, "phenotype");

		upload.setReceiver(uploader);
		upload.addListener(uploader);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
