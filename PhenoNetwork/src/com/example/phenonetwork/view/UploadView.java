package com.example.phenonetwork.view;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;

import com.example.phenonetwork.MyUploader;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

public class UploadView extends CustomComponent implements View {
	
	String option;
	
	VerticalLayout previewLayout;
	
	
	public UploadView(HashMap<String, SQLContainer> sqlContainer) {
		VerticalSplitPanel content = new VerticalSplitPanel();
		content.setWidth("100%");
		content.setHeight("100%");

		previewLayout = new VerticalLayout();
		
		/********************************/
		Button newButton = new Button("New");

		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

		VerticalLayout newLayout = new VerticalLayout();
		newLayout.setMargin(true);

		Tree dir = new Tree("Directories/Datasets");
		// dir.addItemClickListener(new ItemClickListener() {
		//
		// @Override
		// public void itemClick(ItemClickEvent event) {
		// System.out.println("item click: "+event.getItemId());
		// System.out.println("item click: "+event.getPropertyId());
		// System.out.println("item click: "+event.getSource());
		//
		// }
		// });
		//
		// dir.addItem("test");

		FilesystemContainer filesystemContainer = new FilesystemContainer(
				new File("E:\\Dags"));
		Tree tree = new Tree(null, filesystemContainer);
		tree.setItemCaptionPropertyId(FilesystemContainer.PROPERTY_NAME);
		tree.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {

				File file = new File(event.getItemId().toString());

				if (!file.isDirectory()){
					if (FilenameUtils.getExtension(event.getItemId().toString()).equals("xls")){
						
						getUI().addWindow(new FileProcess());
						
					}
					
					
				}
					
			}
		});
		
		

		content.addComponent(tree);

		previewLayout.addComponent(new Label("test"));
		newLayout.addComponent(newButton);
		newLayout.addComponent(tree);
		splitPanel.addComponent(newLayout);
		splitPanel.addComponent(previewLayout);
		
		
			
		
		
		splitPanel.setWidth("100%");
		splitPanel.setHeight("100%");

		/*******************************/

		VerticalLayout vLayout = new VerticalLayout();
		// vLayout.addComponent(new Label("Study Browser"));
		vLayout.addComponent(new Label("Select dataset to upload:",
				ContentMode.HTML));
		Upload upload = new Upload();
		vLayout.addComponent(upload);
		vLayout.setMargin(true);
		vLayout.setHeight("15%");
		// vLayout.setSizeFull();

		content.addComponent(splitPanel);
		content.addComponent(vLayout);

		setCompositionRoot(content);

		MyUploader uploader = new MyUploader(sqlContainer, "study");

		upload.setReceiver(uploader);
		upload.addListener(uploader);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		previewLayout = new VerticalLayout();
		Label prevLabel;
		
		if (event.getParameters() == null || event.getParameters().equals("")){
			System.out.println("preview:"+ event.getParameters());
			prevLabel = new Label("FILE PREVIEW");
			prevLabel.setWidth("100%");
			
		}else{
			System.out.println("refresh:"+ event.getParameters());
			prevLabel = new Label("refresh");
			prevLabel.setWidth("100%");
			
			
		}
		
		
		
		previewLayout.addComponent(prevLabel);
		previewLayout.setSizeFull();
		previewLayout.setComponentAlignment(prevLabel, Alignment.MIDDLE_CENTER);
	}

}
