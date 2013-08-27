package com.example.phenonetwork.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.example.phenonetwork.uploader.utils.DButils;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class StudyView extends CustomComponent implements View {
	
	public StudyView(final HashMap<String, SQLContainer> container){
		
		VerticalLayout treevLayout = new VerticalLayout();
		treevLayout.setHeight("100%");
		treevLayout.setWidth("350px");
		treevLayout.setMargin(true);
		
		final VerticalLayout vLayout2 = new VerticalLayout();
		vLayout2.setSizeFull();
		vLayout2.setMargin(true);
		
		Tree tree = new Tree("List of Studies");
		
		
		tree.setContainerDataSource(container.get("study"));
		tree.setItemCaptionPropertyId("studyName");
		
		tree.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				Table table = new Table();
				
				table.setContainerDataSource(DButils.getFreeFormQuery(event.getItemId().toString(), container));
				container.get("variates").removeAllContainerFilters();
				
				vLayout2.addComponent(table);
				
			}
		}); 
		
		
		TextField searchStudy = new TextField("Search");
		treevLayout.addComponent(searchStudy);
		treevLayout.addComponent(tree);
		
		HorizontalSplitPanel vSplitPanel = new HorizontalSplitPanel();
		vSplitPanel.setWidth("100%");
		vSplitPanel.setHeight("100%");
		
//		vSplitPanel.setLocked(true);
		
		setCompositionRoot(vSplitPanel);
		
		
		vSplitPanel.addComponent(treevLayout);
		vSplitPanel.addComponent(vLayout2);
		
		vSplitPanel.getFirstComponent().setWidth("20%");
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
