package com.example.vaadinformdatabinding;

import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class KullaniciFormFieldFactory implements FormFieldFactory {

	private static final long serialVersionUID = 1L;

	public Field createField(Item item, Object propertyId, Component uiContext) {
		String propertyName = (String) propertyId;
		if (propertyName.equals("birthCity")) {
			Select select = new Select("Birth City");
			select.setRequired(true);
			select.setNullSelectionAllowed(false);
			select.setRequiredError("Please Select City");
			select.addItem("istanbul");
			select.addItem("Ankara");
			select.addItem("izmir");
			select.addItem("Antalya");
			select.addItem("Konya");
			return select;

		} else if (propertyName.equals("name")) {
			TextField textField = new TextField("Name");
			textField.setRequired(true);
			textField.setRequiredError("Name is missing!!");
			return textField;
		} else if (propertyName.equals("surname")) {
			TextField textField = new TextField("Surname");
			textField.setRequired(true);
			textField.setRequiredError("SurName is missing!!");
			return textField;
		} else if (propertyName.equals("gender")) {
			com.vaadin.ui.ComboBox combo = new com.vaadin.ui.ComboBox("Gender");
			combo.addItem("Male");
			combo.addItem("Female");
			combo.setRequired(true);
			combo.setNullSelectionAllowed(false);
			combo.setRequiredError("Please Select Gender");
			combo.setValue(combo.getItemIds().iterator().next());
			return combo;
		} else if (propertyName.equals("birthDate")) {
			DateField dateField= new DateField("Birth Date");
			dateField.setRequired(true);
			dateField.setRequiredError("Birth Date is missing!!");
			return dateField;
		} else if (propertyName.equals("flag")) {
			CheckBox checkBox = new CheckBox("Status");
			return checkBox;
		}

		return null;
	}

}