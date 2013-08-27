package com.example.phenonetwork;

import java.util.HashMap;

import com.example.phenonetwork.uploader.utils.DButils;
import com.example.phenonetwork.view.DefaultView;
import com.example.phenonetwork.view.SearchView;
import com.example.phenonetwork.view.StudyView;
import com.example.phenonetwork.view.UploadView;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Main UI class
 */
@Theme("phenonetworktheme")
@SuppressWarnings("serial")
public class PhenonetworkUI extends UI {

	Window notifications;
	
	@Override
	protected void init(VaadinRequest request) {

		
		VerticalLayout maintLayout = new VerticalLayout();
		maintLayout.setSizeFull();

		setContent(maintLayout);

		MarginInfo m = new MarginInfo(false, true, false, false);

		HorizontalLayout topBar = new HorizontalLayout();
		topBar.setWidth("100%");

		topBar.addStyleName("topbar");

		HorizontalLayout titleBar = new HorizontalLayout();
		Label title = new Label("Phenotyping Network");
		title.addStyleName("heading");
		titleBar.addComponent(title);
		titleBar.setMargin(true);

		HorizontalLayout menuBar = new HorizontalLayout();
		menuBar.setMargin(true);
		menuBar.addStyleName("menu-options");

		
		HorizontalLayout germplasmLayout = new HorizontalLayout();
		germplasmLayout.addComponent(new Label("Germplasm"));
		germplasmLayout.setMargin(m);
		germplasmLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("");
				// homeButton.setCaption("Germplasm");

			}
		});
		menuBar.addComponent(germplasmLayout);

		
		HorizontalLayout phenotypeLayout = new HorizontalLayout();
		phenotypeLayout.addComponent(new Label("Phenotypes"));
		phenotypeLayout.setMargin(m);
		phenotypeLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("PhenotypeView");

			}
		});
		menuBar.addComponent(phenotypeLayout);

		

		HorizontalLayout studyLayout = new HorizontalLayout();
		studyLayout.addComponent(new Label("Study"));
		studyLayout.setMargin(m);
		studyLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("StudyView");

			}
		});
		menuBar.addComponent(studyLayout);

		

		HorizontalLayout uploadLayout = new HorizontalLayout();
		uploadLayout.addComponent(new Label("Manage Datasets"));
		uploadLayout.setMargin(m);
		uploadLayout.addStyleName("foobar");
		uploadLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("UploadView");

			}
		});
		menuBar.addComponent(uploadLayout);

		
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.addComponent(new Label("Search"));
		searchLayout.setMargin(m);
		searchLayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				getUI().getNavigator().navigateTo("SearchView");

			}
		});
		menuBar.addComponent(searchLayout);

		HorizontalLayout acclayout = new HorizontalLayout();
		acclayout.addComponent(new Label("Account"));
		acclayout.setMargin(m);
		acclayout.addLayoutClickListener(new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				buildNotifications(event);
                getUI().addWindow(notifications);
                notifications.focus();
                
				

			}

			private void buildNotifications(LayoutClickEvent event) {
				notifications = new Window("Notifications");
				VerticalLayout l = new VerticalLayout();
				l.setMargin(true);
				l.setSpacing(true);
				l.addComponent(new Button("close", new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						notifications.close();
						
					}
				}));
				
				notifications.setContent(l);
				notifications.setWidth("300px");
				notifications.setHeight("300px");
				notifications.addStyleName("Notification");
				notifications.setClosable(false);
				notifications.setResizable(false);
				notifications.setDraggable(false);
				notifications.setPositionX(event.getClientX()
						- event.getRelativeX()-250);
				notifications.setPositionY(event.getClientY()
						- event.getRelativeY()+20);
				notifications.setCloseShortcut(KeyCode.ESCAPE, null);
				
				
				System.out.println("window should popup");
				
			}
		});
		menuBar.addComponent(acclayout);

		topBar.addComponent(titleBar);
		topBar.addComponent(menuBar);

		VerticalLayout bodyContent = new VerticalLayout();
		bodyContent.setSizeFull();

		maintLayout.addComponent(topBar);
		maintLayout.addComponent(bodyContent);
		maintLayout.setExpandRatio(bodyContent, 2);
		getPage().setTitle("Pheno Network");

		HashMap<String, SQLContainer> sqlContainer = DButils.createContainer();

		Navigator navigator = new Navigator(this, bodyContent);
		navigator.addView("PhenotypeView", new PhenotypeView(sqlContainer));
		navigator.addView("StudyView", new StudyView(sqlContainer));
		navigator.addView("UploadView", new UploadView(sqlContainer));
		navigator.addView("SearchView", new SearchView(sqlContainer));
		navigator.addView("", new DefaultView(sqlContainer));

	}

}