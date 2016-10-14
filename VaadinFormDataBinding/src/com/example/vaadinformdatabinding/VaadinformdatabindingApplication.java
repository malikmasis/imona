package com.example.vaadinformdatabinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;
import com.vaadin.Application;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 * Main application class.
 */
public class VaadinformdatabindingApplication extends Application {

	@Override
	public void init() {
		Window mainWindow = new Window("Vaadin Form Data Binding Application");
		FormBidingComposite formBindingComposite = new FormBidingComposite();

		mainWindow.addComponent(formBindingComposite);
		setMainWindow(mainWindow);

	}

}
