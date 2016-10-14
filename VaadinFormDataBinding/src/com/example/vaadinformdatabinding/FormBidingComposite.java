package com.example.vaadinformdatabinding;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mysql.jdbc.PreparedStatement;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class FormBidingComposite extends CustomComponent {

	private static final long serialVersionUID = 1L;

	private VerticalLayout mainLayout;
	com.vaadin.ui.ComboBox combo = new com.vaadin.ui.ComboBox("Report");
	VerticalLayout myTabRoot3 = new VerticalLayout();
	Table table;
	Connection con;
	PreparedStatement ps;
	Statement cs;
	ResultSet rs;
	static String idNumber = "";
	static Integer Id = 0;
	Form baglanUpdate = new Form();
	KullaniciFormBean kullaniciFormBean = new KullaniciFormBean();
	BeanItem<KullaniciFormBean> beanItem = new BeanItem<KullaniciFormBean>(
			kullaniciFormBean);

	KullaniciFormBean kullaniciFormBeanUpdate = new KullaniciFormBean();
	BeanItem<KullaniciFormBean> beanItemUpdate = new BeanItem<KullaniciFormBean>(
			kullaniciFormBeanUpdate);
	Table table1;

	Table tableGenerate() {

		table1 = new Table();
		// table.setStyleName("iso3166");
		table1.setPageLength(6);
		table1.setSizeFull();
		table1.setSelectable(true);
		table1.setMultiSelect(false);
		table1.setImmediate(true);
		table1.setColumnReorderingAllowed(true);
		table1.setColumnCollapsingAllowed(true);
		table1.refreshRowCache();
		table1.setImmediate(true);
		table1.setEnabled(true);
		table1.addContainerProperty("NAME", String.class, "");
		table1.addContainerProperty("SURNAME", String.class, "");
		table1.addContainerProperty("GENDER", String.class, "");
		table1.addContainerProperty("BIRTH DATE", String.class, "");
		table1.addContainerProperty("BIRTH CITY", String.class, "");
		table1.addContainerProperty("STATUS", Boolean.class, "");
		return table1;

	}

	public void listTab3() throws Exception {
		try {
			System.out.println("listTab3");
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/imona";
			String kullaniciad = "root";
			String sifre = "";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			con = DriverManager.getConnection(url, kullaniciad, sifre);
			st = con.createStatement();
			System.out.println("Baglandi");

			int itemId = 1;
			cs = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			if (combo.getValue().toString().equals("Male Costumer")) {
				System.out.println("MAle");
				rs = cs.executeQuery("select name,surname,gender,birthDate,birthCity,flag from customer where gender= 'Male' ");
				table1.removeAllItems();
			} else {
				System.out.println("ist");
				table1.removeAllItems();
				rs = cs.executeQuery("select * from customer where birthCity= 'istanbul' ");
			}

			String[][] bdy = new String[10][5];
			while (rs.next()) {

				table1.addItem(
						new Object[] { rs.getString(1), rs.getString(2),
								rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getBoolean(6) }, itemId++);
				// System.out.println("List--" + rs.getString(1));
				for (int k = 0; k < 5; k++) {
					// System.out.println("geliyor mu"+itemId+"--"+k);
					bdy[itemId - 2][k] = rs.getString(k + 1);
					// System.out.println(rs.getString(k+1)+"---");
					System.out.println(bdy[itemId - 2][k] + "***");
				}

				createSamplePDF(
						new String[] { "name", rs.getString(2),
								rs.getString(3), rs.getString(4),
								rs.getString(5) }, bdy);
			}
			System.out.println("pdf ok");
			con.close();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş!");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!");
		}
	}

	public static void createSamplePDF(String header[], String body[][])
			throws Exception {
		Document documento = new Document();
		// Create new File
		File file = new File("D:\\filter.pdf");
		file.createNewFile();
		FileOutputStream fop = new FileOutputStream(file);
		PdfWriter.getInstance(documento, fop);
		documento.open();
		// Fonts
		Font fontBody = new Font(Font.COURIER, 12, Font.NORMAL);
		// Table for header
		PdfPTable cabetabla = new PdfPTable(header.length);
		for (int j = 0; j < header.length; j++) {
			Phrase frase = new Phrase(header[j]);
			PdfPCell cell = new PdfPCell(frase);
			cabetabla.addCell(cell);
		}
		documento.add(cabetabla);

		// Tabla for body
		PdfPTable tabla = new PdfPTable(header.length);
		for (int i = 0; i < body.length; i++) {
			for (int j = 0; j < body[i].length; j++) {
				tabla.addCell(new Phrase(body[i][j], fontBody));
			}
		}
		documento.add(tabla);
		documento.close();
		fop.flush();
		fop.close();
	}

	public void listUpdate() {
		try {
			System.out.println("ListUpdate");
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/imona";
			String kullaniciad = "root";
			String sifre = "";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			con = DriverManager.getConnection(url, kullaniciad, sifre);
			st = con.createStatement();
			System.out.println("Baglandi");

			// int itemId = 1;
			cs = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			System.out.println("update List");
			rs = cs.executeQuery("select name,surname,gender,birthCity,flag,birthDate from customer where Id='"
					+ Id + "'");

			while (rs.next()) {

				kullaniciFormBeanUpdate.setName(rs.getString(1));
				kullaniciFormBeanUpdate.setSurname(rs.getString(2));
				kullaniciFormBeanUpdate.setGender(rs.getString(3));
				kullaniciFormBeanUpdate.setBirthCity(rs.getString(4));
				kullaniciFormBeanUpdate.setFlag(rs.getBoolean(5));
				// kullaniciFormBeanUpdate.setBirthDate(rs.getDate(6));

			}

			con.close();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş!");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!");
		}
	}

	public void list() {

		try {
			System.out.println("list");
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/imona";
			String kullaniciad = "root";
			String sifre = "";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			con = DriverManager.getConnection(url, kullaniciad, sifre);
			st = con.createStatement();
			System.out.println("Baglandi");

			cs = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			table.removeAllItems();
			rs = cs.executeQuery("select id,name,surname,gender,birthDate,birthCity,flag from customer");

			int itemId = 1;
			while (rs.next()) {

				table.addItem(
						new Object[] { rs.getInt(1), rs.getString(2),
								rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6),
								rs.getBoolean(7) }, itemId++);
			}

			con.close();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş!");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!");
		}
	}

	public void update() {
		baglanUpdate.setValidationVisible(false);
		try {
			baglanUpdate.validate();
			System.out.println("update");
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/imona";
			String kullaniciad = "root";
			String sifre = "";

			Connection con = null;
			Statement st = null;
			ResultSet rs = null;

			con = DriverManager.getConnection(url, kullaniciad, sifre);
			st = con.createStatement();
			System.out.println("Baglandi");

			cs = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			String query = "update customer set  name= ? , surname=?, gender=?, birthCity=? , flag=? where Id='"
					+ Id + "'";
			
			PreparedStatement preparedStatement = (PreparedStatement) con
					.prepareStatement(query);
			preparedStatement.setString(1, kullaniciFormBeanUpdate.getName());
			preparedStatement
					.setString(2, kullaniciFormBeanUpdate.getSurname());
			preparedStatement.setString(3, kullaniciFormBeanUpdate.getGender());
			preparedStatement.setString(4,
					kullaniciFormBeanUpdate.getBirthCity());
			preparedStatement.setBoolean(5, kullaniciFormBeanUpdate.isFlag());
//			preparedStatement.setString(6, kullaniciFormBean.getBirthDate()
//					.toString());
			preparedStatement.execute();
			con.close();
			getWindow().showNotification("Update Success!");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş! u");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!u");
		} catch (InvalidValueException e) {
			baglanUpdate.setValidationVisible(true);
		}
	}

	public void delete() {
		try {
			System.out.println("sil");
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/imona";
			String kullaniciad = "root";
			String sifre = "";

			Connection con = null;

			con = DriverManager.getConnection(url, kullaniciad, sifre);
			System.out.println("Baglandi");

			String query = "delete from customer where Id = ?";
			PreparedStatement preparedStmt = (PreparedStatement) con
					.prepareStatement(query);
			preparedStmt.setInt(1, Id);

			preparedStmt.execute();
			con.close();

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş!");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!");
		}
	}

	public FormBidingComposite() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private void buildMainLayout() {
		mainLayout = new VerticalLayout();
		Form baglanacakForm = new Form();

		baglanacakForm.setCaption("Amino Customer Form");
		baglanUpdate.setCaption("Update Screen");

		baglanacakForm.setFormFieldFactory(new KullaniciFormFieldFactory());
		baglanacakForm.setItemDataSource(beanItem);

		baglanUpdate.setFormFieldFactory(new KullaniciFormFieldFactory());
		baglanUpdate.setItemDataSource(beanItemUpdate);

		Button buttonSend = new Button("Send");
		Button reset = new Button("Reset");
		buttonSend.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				baglanacakForm.setValidationVisible(false);

				try {
					baglanacakForm.validate();
					try {

						Class.forName("com.mysql.jdbc.Driver");

						String url = "jdbc:mysql://localhost:3306/imona";
						String kullaniciad = "root";
						String sifre = "";

						Connection con = null;
						Statement st = null;
						ResultSet rs = null;

						con = DriverManager.getConnection(url, kullaniciad,
								sifre);
						st = con.createStatement();
						System.out.println("Baglandi");

						String sql = "INSERT INTO customer (name, surname, gender,birthDate,birthCity,flag)"
								+ "VALUES (?, ?, ?, ?, ?, ?)";
						PreparedStatement preparedStatement = (PreparedStatement) con
								.prepareStatement(sql);
						preparedStatement.setString(1,
								kullaniciFormBean.getName());
						preparedStatement.setString(2,
								kullaniciFormBean.getSurname());
						preparedStatement.setString(3,
								kullaniciFormBean.getGender());
						preparedStatement.setString(4, kullaniciFormBean
								.getBirthDate().toString());
						preparedStatement.setString(5,
								kullaniciFormBean.getBirthCity());
						preparedStatement.setBoolean(6,
								kullaniciFormBean.isFlag());

						preparedStatement.executeUpdate();
						con.close();
						getWindow().showNotification("Record Success!");

					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
						System.out.println("Sürücü projeye eklenmemiş!");

					} catch (SQLException ex) {
						ex.printStackTrace();
						System.out
								.println("Veritabanına bağlantı sağlanamadı!");
					}
				} catch (InvalidValueException e) {
					baglanacakForm.setValidationVisible(true);
				}

			}

		});

		reset.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				baglanacakForm.getField("name").setValue("");
				baglanacakForm.getField("surname").setValue("");
				baglanacakForm.getField("gender").setValue(null);
				baglanacakForm.getField("birthDate").setValue(null);
				baglanacakForm.getField("birthCity").setValue(null);
				baglanacakForm.getField("flag").setValue(false);
			}
		});

		// order component
		Vector<String> order = new Vector<String>();
		order.add("name");
		order.add("surname");
		order.add("gender");
		order.add("birthDate");
		order.add("birthCity");
		order.add("flag");

		baglanacakForm.setVisibleItemProperties(order);
		baglanUpdate.setVisibleItemProperties(order);

		baglanacakForm.setRequired(true);
		baglanUpdate.setRequired(true);

		// tab1 start
		TabSheet tabsheet = new TabSheet();

		VerticalLayout myTabRoot = new VerticalLayout();

		myTabRoot.addComponent(baglanacakForm);
		myTabRoot.addComponent(buttonSend);
		myTabRoot.addComponent(reset);

		tabsheet.addTab(myTabRoot);
		tabsheet.getTab(myTabRoot).setCaption("First Tab");

		// tab2 start
		table = new Table();
		table.setStyleName("iso3166");
		table.setPageLength(6);
		table.setSizeFull();
		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);
		table.refreshRowCache();
		table.setImmediate(true);
		table.setEnabled(true);

		table.addContainerProperty("ID", Integer.class, "");
		table.addContainerProperty("NAME", String.class, "");
		table.addContainerProperty("SURNAME", String.class, "");
		table.addContainerProperty("GENDER", String.class, "");
		table.addContainerProperty("BIRTH DATE", String.class, "");
		table.addContainerProperty("BIRTH CITY", String.class, "");
		table.addContainerProperty("STATUS", Boolean.class, "");

		VerticalLayout myTabRoot2 = new VerticalLayout();

		Label labelDelete = new Label("Are You Sure");
		Button deleteButton = new Button("Yes");

		deleteButton.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				delete();
				list();

			}
		});

		VerticalLayout popupContentDelete = new VerticalLayout();

		popupContentDelete.addComponent(labelDelete);
		popupContentDelete.addComponent(deleteButton);

		VerticalLayout popupContentGuncel = new VerticalLayout();

		Button updateOK = new Button("OK");
		popupContentGuncel.addComponent(baglanUpdate);
		popupContentGuncel.addComponent(updateOK);
		updateOK.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				update();
				table.removeAllItems();
				list();

			}
		});

		PopupView popupDelete = new PopupView("Delete", popupContentDelete);
		PopupView popupGuncel = new PopupView("Guncel", popupContentGuncel);

		table.addListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
					// left mouse button clicked on table
					myTabRoot2.addComponent(popupDelete);
					myTabRoot2.addComponent(popupGuncel);
					idNumber = event.getItemId().toString();
					table.setImmediate(true);

					Id = (Integer) table.getContainerProperty(
							event.getItemId(), "ID").getValue();
					listUpdate();
				}
			}
		});

		list();
		tabsheet.addListener(new TabSheet.SelectedTabChangeListener() {

			private static final long serialVersionUID = 1L;

			public void selectedTabChange(SelectedTabChangeEvent event) {
				table.removeAllItems();
				list();

			}
		});

		myTabRoot2.addComponent(table);
		tabsheet.addTab(myTabRoot2);
		tabsheet.getTab(myTabRoot2).setCaption("Second Tab");

		// tab3 starter
		Button generateReport = new Button("Generate");

		combo.addItem("Male Costumer");
		combo.addItem("Customer in Istanbul");
		combo.setNullSelectionAllowed(false);
		generateReport.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					listTab3();
					getApplication().getMainWindow().showNotification(
							"PDF is Generated");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		myTabRoot3.addComponent(combo);
		myTabRoot3.addComponent(generateReport);
		myTabRoot3.addComponent(tableGenerate());
		tabsheet.addTab(myTabRoot3);
		tabsheet.getTab(myTabRoot3).setCaption("Third Tab");

		mainLayout.addComponent(tabsheet);

	}

}