package com.example.phenonetwork;

import java.util.HashMap;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Table;

public class TableCreator extends Table {

	private static final Action SHOW = new Action("Show Descriptors");
	private static final Action MORPHOLOGY = new Action("Show Morphology");
	private static final Action PHENOLOGY = new Action("Show Phenology");
	private static final Action ADD = new Action("Add trait");

	SQLContainer sqlContainer;

	public TableCreator(final HashMap<String, SQLContainer> container,
			final String data) {
		sqlContainer = container.get(data);
		sqlContainer.setPageLength(25);
		setContainerDataSource(sqlContainer);
		setSizeFull();
		setCaption("Sample Table");
		setPageLength(15);
		

		if (data.equals("trait")) {
			setMultiSelect(true);
			setSelectable(true);
		}

		addActionHandler(new Handler() {

			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if (action == SHOW) {
					getUI().addWindow(new DescriptorsPage(container, target));
				}
				if (action == ADD) {
					getUI().addWindow(new AddTraitPage(container, target));
				}

			}

			@Override
			public Action[] getActions(Object target, Object sender) {
				if (data.equals("germplasm"))
					return new Action[] { SHOW, PHENOLOGY, MORPHOLOGY };
				if (data.equals("trait"))
					return new Action[] { ADD };
				return null;

			}
		});

	}

	public void searchFieldChange(String string, SQLContainer container) {
		if (string == null || string.isEmpty())
			sqlContainer.removeAllContainerFilters();
		sqlContainer.addContainerFilter("germplasmName", string, true, false);

	}

}
