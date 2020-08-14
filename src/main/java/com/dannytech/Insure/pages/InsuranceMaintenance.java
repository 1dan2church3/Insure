package com.dannytech.Insure.pages;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.dannytech.Insure.AddressTraverson;
import com.dannytech.Insure.InsuranceTraverson;
import com.dannytech.Insure.ZipTraverson;
import com.dannytech.Insure.pojo.Address;
import com.dannytech.Insure.pojo.Insurance;
import com.dannytech.Insure.pojo.Zip;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
public class InsuranceMaintenance extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private Binder<Insurance> binder = new Binder<>(Insurance.class);
	private Insurance insurance = new Insurance();

	@Autowired
	public InsuranceMaintenance(ZipTraverson zipTraverson, InsuranceTraverson insTraverson,
			AddressTraverson addrTraverson) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		insurance.setAddress(new Address());
		binder.setBean(insurance);
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new ResponsiveStep("300px", 3));
		form.setWidth("600px");

		TextField name = new TextField("Name");
		binder.forField(name).asRequired("Name is required.").bind(Insurance::getName, Insurance::setName);

		TextField street = new TextField("Street Address");
		binder.forField(street).asRequired("Street Address is required").bind("address.streetAddress");

		TextField state = new TextField("State");
		state.setReadOnly(true);

		TextField city = new TextField("City");
		city.setReadOnly(true);

		ComboBox<Zip> zip = new ComboBox<Zip>();
		binder.forField(zip).bind("address.zip");
		zip.setItemLabelGenerator(Zip::getCode);
		zip.setPlaceholder("Please Select...");
		zip.setLabel("Zip");
		zip.addValueChangeListener(z -> {

			if (z.getValue() != null) {
				Zip foundZip = zipTraverson.findByCode(z.getValue().getCode());
				city.setValue(foundZip.getCity());
				state.setValue(foundZip.getState());
			}
		});

		ArrayList<Zip> zipsList = zipTraverson.getAll();
		zip.setItems(zipsList);

		Grid<Insurance> grid = new Grid<>(Insurance.class);
		grid.removeAllColumns();
		Grid.Column<Insurance> nameColumn = grid.addColumn("name").setHeader("Name");
		Grid.Column<Insurance> streetColumn = grid.addColumn("address.streetAddress").setHeader("Street");
//		Grid.Column<Insurance> cityColumn = grid.addColumn("address.zip.city").setHeader("City");
//		Grid.Column<Insurance> stateColumn = grid.addColumn("address.zip.state").setHeader("State");
//		Grid.Column<Insurance> codeColumn = grid.addColumn("address.zip.code").setHeader("Zip Code");
		grid.setWidth("1000px");

		HeaderRow filterRow = grid.appendHeaderRow();
		TextField nameField = new TextField();
//		nameField.addValueChangeListener(event -> dataProvider
//				.addFilter(insurance -> StringUtils.containsIgnoreCase(insurance.getName(), nameField.getValue())));
		nameField.setValueChangeMode(ValueChangeMode.EAGER);
		filterRow.getCell(nameColumn).setComponent(nameField);
		nameField.setSizeFull();
		nameField.setPlaceholder("Filter");

		Button save = new Button("Add Insurance");
		save.addClickListener(e -> {
			try {
				binder.writeBean(insurance);
				insurance.getAddress().setZipLink("http://localhost:8080/zips/1");
				System.out.println(insurance.getAddress().getZip());
				addrTraverson.addAddress(insurance.getAddress());
				binder.getFields().forEach(f -> f.clear());
				new Notification("Insurance added!").open();

			} catch (ValidationException e1) {
				new Notification("An error occurred.").open();
				e1.printStackTrace();
			}
		});

		form.add(name, street, zip, city, state, save);
		add(form, grid);
	}
}
