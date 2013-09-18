package com.example.phenonetwork.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.example.phenonetwork.db.GetDataset;
import com.example.phenonetwork.db.GetStudy;
import com.example.phenonetwork.uploader.utils.DButils;
import com.vaadin.data.util.sqlcontainer.RowId;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class StudyView extends CustomComponent implements View {
	
	GetDataset gd;
	GetStudy study;
	
	public StudyView(final HashMap<String, SQLContainer> container){
		
		HorizontalLayout layout = new HorizontalLayout();
		
		
		VerticalLayout treevLayout = new VerticalLayout();
		treevLayout.setHeight("100%");
		treevLayout.setWidth("350px");
		treevLayout.setMargin(true);
		
		final VerticalLayout vLayout2 = new VerticalLayout();
		vLayout2.setSizeFull();
		vLayout2.setMargin(true);
		
		List result= new ArrayList();
		
		Tree tree = new Tree("List of Studies");
		
		study = new GetStudy(container.get("study"));
		
		result = study.getAllStudyName();
		
		Iterator iter = result.iterator();
		
		gd = new GetDataset(container.get("dataset"));
		
		while(iter.hasNext()){
			RowId name = (RowId) iter.next();
			result = gd.getDatasetByStudyName(name.toString());
			tree.addItem(name.toString());
			if (result.size() > 0)
				setTreeParent(tree, name.toString(),result);
		}
		
		
		tree.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				Table table = new Table();
				
//				table.setContainerDataSource(DButils.getFreeFormQuery(event.getItemId().toString(), container));
				
				table.setContainerDataSource(container.get("observationDate"));
				vLayout2.removeAllComponents();
				vLayout2.addComponent(table);
				vLayout2.addComponent(new Button("Download"));
				
			}
		}); 
		
		
		layout.addComponent(treevLayout);
		layout.addComponent(vLayout2);
		
		TextField searchStudy = new TextField("Search");
		treevLayout.addComponent(searchStudy);
		treevLayout.addComponent(tree);
		
		
		setCompositionRoot(layout);
		
	}

	private void setTreeParent(Tree tree, String studyName, List result) {
		Iterator iter = result.iterator();
		
		
		while(iter.hasNext()){
			RowId id = (RowId) iter.next();
			String name = gd.getDatasetNameById(id);
			tree.addItem(name);
			tree.setParent(name, studyName);
			
			
		}
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
