package com.example.phenonetwork.view;

import java.util.ArrayList;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class FileProcess extends Window {
	
	

	
	
	public FileProcess() {
		
		setModal(true);
		setWidth("300px");
		setHeight("300px");
		setCaption("CHOOSE FILE PROCESS");
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.addComponent(new Label("What would you like to do with your dataset"));
		layout.addComponent(new Label("<br>", ContentMode.HTML));
	     
         ArrayList<String> values = new ArrayList<String>();
         
         values.add("option1");
         values.add("option2");
         values.add("option3");
         values.add("option4");
         values.add("option5");
         final OptionGroup og = new OptionGroup("Choose one:",values);
         og.setItemCaption("option1", "Process 1");
         og.setItemCaption("option2", "Process 2");
         og.setItemCaption("option3", "Process 3");
         og.setItemCaption("option4", "Process 4");
         og.setItemCaption("option5", "Process 5");
         
         layout.addComponent(og);
         layout.addComponent(new Label("<br>", ContentMode.HTML));
         
         Button goButton = new Button("Go", new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo("UploadView/"+og.getValue().toString());
				close();
			}
		});
         
         layout.addComponent(goButton);
         
         
		setContent(layout);
	}

	

	
	

}
