package com.example.phenonetwork;

import java.util.HashMap;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class AddTraitPage extends Window {

	public AddTraitPage(HashMap<String, SQLContainer> container, Object target) {
		setContent(new Label("test"));
	}

}
