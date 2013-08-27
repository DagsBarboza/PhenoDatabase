package com.example.phenonetwork.view;

import java.util.HashMap;

import com.example.phenonetwork.DetectedPage;
import com.example.phenonetwork.MyUploader;
import com.example.phenonetwork.TableCreator;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class DefaultView extends CustomComponent implements View {
	
	private TableCreator tb;
	
	public DefaultView(final HashMap<String, SQLContainer> container){
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeUndefined();
		layout.setWidth("100%");
		
		setCompositionRoot(layout);
		
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setMargin(true);
		vLayout.addComponent(new Label("This page is for Germplasm Uploads and display"));
		vLayout.addComponent(new Label("<br>Select file to upload:", ContentMode.HTML));
		Upload upload = new Upload();
		vLayout.addComponent(upload);
		vLayout.addComponent(new Button("Launch Scanner", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				DetectedPage dp = new DetectedPage();
				
				
			}
		}));
		
		
		
		vLayout.setSizeFull();
		
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setSizeFull();
		leftLayout.setMargin(true);
		
		
//		HorizontalLayout searchLayout = new HorizontalLayout();
//		searchLayout.setSizeFull();
//		searchLayout.addComponent(new Label("Search: "));
//		searchLayout.addComponent(new TextField());
//		searchLayout.addComponent(new Button("Go", new ClickListener() {
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				Notification.show("This is not Yet Implemented...");
//				
//			}
//		}));
		
		
		 tb = new TableCreator(container, "germplasm");
		
		TextField searchField = new TextField("Search: ");
		searchField.addTextChangeListener(new TextChangeListener() {
			
			@Override
			public void textChange(TextChangeEvent event) {
				tb.searchFieldChange(event.getText(), container.get("germplasm"));
				
			}
		});
		
		leftLayout.addComponent(searchField);
		leftLayout.addComponent(tb);
		
		
		
		layout.addComponent(leftLayout);
		layout.addComponent(vLayout);
		
		MyUploader uploader = new MyUploader(container, "germplasm");
		
		upload.setReceiver(uploader);
		upload.addListener(uploader);
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
