package com.example.phenonetwork;

import java.util.HashMap;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class DescriptorsPage extends Window {

	@PropertyId("germplasmName")
	private TextField germplasmName;
	@PropertyId("accessionId")
	private TextField accessionId;
	@PropertyId("taxonomy")
	private TextField taxonomy;
	@PropertyId("varietalGroup")
	private TextField varietalGroup;
	@PropertyId("biologicalStatus")
	private TextField biologicalStatus;
	@PropertyId("sourceCountry")
	private TextField sourceCountry;
	@PropertyId("donorCountry")
	private TextField donorCountry;
	@PropertyId("typeOfSamples")
	private TextField typeOfSamples;
	@PropertyId("culturalType")
	private TextField culturalType;
	@PropertyId("specialCharacteristic")
	private TextField specialCharacteristic;
	
	public DescriptorsPage(HashMap<String, SQLContainer> sqlContainer, Object target) {
		setCaption("Germplasm Description");
		setModal(true);
		setWidth("50%");
		setHeight("75%");
		setResizable(false);
		
		String id = target.toString();
		Item item = sqlContainer.get("accession").getItem(new RowId(new Object[] {id}));
		Item item2 = sqlContainer.get("germplasm").getItem(new RowId(new Object[] {id}));
		
		
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		germplasmName = new TextField("GERMPLASM NAME");
		germplasmName.setNullRepresentation("");
		germplasmName.setWidth("50%");
		germplasmName.setReadOnly(true);
		layout.addComponent(germplasmName);
		
		accessionId = new TextField("ACCESSION ID (IRGC)");
		accessionId.setNullRepresentation("");
		accessionId.setWidth("50%");
		layout.addComponent(accessionId);
		
		varietalGroup = new TextField("VARIETAL GROUP");
		varietalGroup.setNullRepresentation("");
		varietalGroup.setWidth("50%");
		layout.addComponent(varietalGroup);
		
		biologicalStatus = new TextField("BIOLOGICAL STATUS");
		biologicalStatus.setNullRepresentation("");
		biologicalStatus.setWidth("50%");
		layout.addComponent(biologicalStatus);
		
		taxonomy = new TextField("TAXONOMY");
		taxonomy.setNullRepresentation("");
		taxonomy.setWidth("50%");
		layout.addComponent(taxonomy);
		
		sourceCountry = new TextField("SOURE COUNTRY");
		sourceCountry.setNullRepresentation("");
		sourceCountry.setWidth("50%");
		layout.addComponent(sourceCountry);
		
		donorCountry = new TextField("DONOR COUNTRY");
		donorCountry.setNullRepresentation("");
		donorCountry.setWidth("50%");
		layout.addComponent(donorCountry);
		
		typeOfSamples = new TextField("TYPE OF SAMPLES");
		typeOfSamples.setNullRepresentation("");
		typeOfSamples.setWidth("50%");
		layout.addComponent(typeOfSamples);
		
		culturalType = new TextField("CULTURE TYPE");
		culturalType.setNullRepresentation("");
		culturalType.setWidth("50%");
		layout.addComponent(culturalType);
		
		specialCharacteristic = new TextField("SPECIAL CHARACTERICTIC");
		specialCharacteristic.setNullRepresentation("");
		specialCharacteristic.setWidth("50%");
		layout.addComponent(specialCharacteristic);
		
		layout.addComponent(new Label("<br> ", ContentMode.HTML));
		layout.addComponent(new Button("Ok", new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
//				getUI().removeWindow(DescriptorsPage.this);
				close();

			}
		}));
		
		FieldGroup fieldGroup = new FieldGroup(item);
		fieldGroup.bindMemberFields(this);
		
		FieldGroup fieldGroup2 = new FieldGroup(item2);
		fieldGroup2.bindMemberFields(this);
		
		setContent(layout);
	}

}
