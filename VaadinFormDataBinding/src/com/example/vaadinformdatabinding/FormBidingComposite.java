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
import com.vaadin.ui.TextField;
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
	// TextField tf= new TextField();
	TextField tf = new TextField("wek");
	KullaniciFormBean kullaniciFormBean = new KullaniciFormBean();
	BeanItem beanItem = new BeanItem(kullaniciFormBean);

	KullaniciFormBean kullaniciFormBeanUpdate = new KullaniciFormBean();
	BeanItem beanItemUpdate = new BeanItem(kullaniciFormBeanUpdate);
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
			System.out.println("sira");
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

			String[][] bdy= new String[10][5];
			while (rs.next()) {
				
				table1.addItem(
						new Object[] { rs.getString(1), rs.getString(2),
								rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getBoolean(6) }, itemId++);
				//System.out.println("List--" + rs.getString(1));
				for(int k=0; k<5;k++){
					//System.out.println("geliyor mu"+itemId+"--"+k);
					bdy[itemId-2][k]=rs.getString(k+1);
					//		System.out.println(rs.getString(k+1)+"---");
					System.out.println(bdy[itemId-2][k]+"***");
				}
				
				
			createSamplePDF(new String[] { "name", rs.getString(2),rs.getString(3),
						rs.getString(4),
						rs.getString(5)}, bdy);
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
		File file = new File("C:\\filter.pdf");
		file.createNewFile();
		FileOutputStream fop = new FileOutputStream(file);
		PdfWriter.getInstance(documento, fop);
		documento.open();
		// Fonts
		Font fontHeader = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
		Font fontBody = new Font(Font.COURIER, 12, Font.NORMAL);
		// Table for header
		PdfPTable cabetabla = new PdfPTable(header.length);
		for (int j = 0; j < header.length; j++) {
			Phrase frase = new Phrase(header[j]);
			PdfPCell cell = new PdfPCell(frase);
			// cell.setBackgroundColor(new BaseColor(Color.lightGray.getRGB()));
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
			System.out.println("sira");
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

			System.out.println("update List");
			rs = cs.executeQuery("select name,surname,gender,birthCity,flag from customer where Id='"
					+ Id + "'");

			while (rs.next()) {

				System.out.println("Update1--" + rs.getString(1));
				System.out.println("Update2--" + rs.getString(2));
				System.out.println("Update3--" + rs.getString(3));
				// System.out.println("Update4--" + rs.getDate(4));
				System.out.println("Update5--" + rs.getString(4));
				System.out.println("Update6--" + rs.getBoolean(5));
				// tf.setValue(rs.getString(1));

				tf.setValue(rs.getString(1));
				tf.addListener(new Listener() {

					@Override
					public void componentEvent(Event event) {
						// System.out.println("amac ne ");
						System.out.println(tf.getValue() + "**");
					}
				});
				System.out.println(tf.getValue() + "--");
				kullaniciFormBeanUpdate.setName(rs.getString(1));
				kullaniciFormBeanUpdate.setSurname(rs.getString(2));
				kullaniciFormBeanUpdate.setGender(rs.getString(3));
				// kullaniciFormBean.setBirthDate(rs.getDate(4));
				kullaniciFormBeanUpdate.setBirthCity(rs.getString(4));
				kullaniciFormBeanUpdate.setFlag(rs.getBoolean(5));
				// System.out.println("date:" + rs.getDate(4));
			}
			// baglanUpdate.setWriteThrough(false);
			System.out.println(kullaniciFormBean.getName() + "dd"
					+ kullaniciFormBeanUpdate.name);

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
			System.out.println("sira");
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
		} catch (Exception ex) {
			System.out.println("hata veritaban Silme");
		}
	}

	public void update() {

		try {
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

			String query = "update customer set  name= ? where Id='" + Id + "'";
			// surname = ? gender = ? birthDate = ? birthCity = ? flag = ? ;
			// sorgu.setInt(1, silinecekOnKayitDomain.getId());

			// table.setEditable(true);
			String newName = kullaniciFormBeanUpdate.getName();
			System.out.println("NEWNAME:" + newName);
			PreparedStatement preparedStatement = (PreparedStatement) con
					.prepareStatement(query);
			preparedStatement.setString(1, kullaniciFormBeanUpdate.getName());
			System.out.println("A:" + kullaniciFormBean.getName());
			// preparedStatement.setString(2, kullaniciFormBean.getSurname());
			// preparedStatement.setString(3, kullaniciFormBean.getGender());
			// preparedStatement.setString(4, kullaniciFormBean.getBirthDate()
			// .toString());
			// preparedStatement.setString(5, kullaniciFormBean.getBirthCity());
			// preparedStatement.setBoolean(6, kullaniciFormBean.isFlag());
			//
			preparedStatement.execute();
			System.out.println("uptade ayakları:" + Id);
			con.close();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş! u");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!u");
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
			// Statement st = null;
			ResultSet rs = null;

			con = DriverManager.getConnection(url, kullaniciad, sifre);
			// st = con.createStatement();
			System.out.println("Baglandi");
			// int name = 28;
			// st.executeUpdate("delete from customer where Id= '" + name +
			// "'");
			String query = "delete from customer where Id = ?";

			// sorgu.setInt(1, silinecekOnKayitDomain.getId());
			PreparedStatement preparedStmt = (PreparedStatement) con
					.prepareStatement(query);
			preparedStmt.setInt(1, Id);

			// execute the preparedstatement
			preparedStmt.execute();
			con.close();

			System.out.println("sildim :)");
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

		Button btnGonder = new Button("Send");
		Button input = new Button("Reset");
		btnGonder.addListener(new ClickListener() {

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

				// System.out.println("Name:" + kullaniciFormBean.getName()
				// + "\nSurname:" + kullaniciFormBean.getSurname()
				// + "\nGender:" + kullaniciFormBean.getGender()
				// + "\nDate:" + kullaniciFormBean.getBirthDate()
				// + "\nBirth City" + kullaniciFormBean.getBirthCity()
				// + "\nState" + kullaniciFormBean.isFlag());
			}

		});

		input.addListener(new ClickListener() {
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

		TabSheet tabsheet = new TabSheet();

		VerticalLayout myTabRoot = new VerticalLayout();
		// myTabRoot.addComponent(baglanacakForm);
		myTabRoot.addComponent(baglanacakForm);
		myTabRoot.addComponent(btnGonder);
		myTabRoot.addComponent(input);
		tabsheet.addTab(myTabRoot);
		tabsheet.getTab(myTabRoot).setCaption("First Tab");

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

		Button updateButton = new Button("Update");
		VerticalLayout myTabRoot2 = new VerticalLayout();
		VerticalLayout popupContent = new VerticalLayout();
		popupContent.addComponent(updateButton);
		updateButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				update();
				table.removeAllItems();
				list();

			}
		});

		Label labelDelete = new Label("Are You Sure");
		Button deleteButton = new Button("Yes");

		deleteButton.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				delete();
				list();

			}
		});

		VerticalLayout popupContentDelete = new VerticalLayout();
		popupContentDelete.addComponent(labelDelete);
		popupContentDelete.addComponent(deleteButton);

		VerticalLayout popupContentGuncel = new VerticalLayout();

		// TextField tf = new TextField();
		// for(int i=0;i<6;i++){
		// //tf.setCaption("S"+i);
		// popupContentGuncel.addComponent(new TextField());
		// }

		Button update = new Button("OK");
		popupContentGuncel.addComponent(baglanUpdate);
		popupContentGuncel.addComponent(update);
		popupContentGuncel.addComponent(tf);
		update.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("1");
				listUpdate();
				update();
				System.out.println("2");
				// table.removeAllItems();
				System.out.println("3");
				// list();

			}
		});

		// The component itself
		PopupView popupDelete = new PopupView("Delete", popupContentDelete);
		PopupView popupGuncel = new PopupView("Guncel", popupContentGuncel);

		PopupView popupUptade = new PopupView("Uptade", popupContent);

		table.addListener(new ItemClickEvent.ItemClickListener() {
			public void itemClick(ItemClickEvent event) {
				if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
					// Right mouse button clicked, do greatThings!
					System.out.println(event.getItemId().toString());
					myTabRoot2.addComponent(popupDelete);
					myTabRoot2.addComponent(popupUptade);
					myTabRoot2.addComponent(popupGuncel);
					myTabRoot2.addComponent(tf);
					idNumber = event.getItemId().toString();
					table.setImmediate(true);
					System.out.println("f" + event.getPropertyId());
					System.out.println("ada" + event.getButton() + "-"
							+ event.toString() + "*" + event.getItemId());

					// Object rowId = table.getValue();
					// grid.getColumn("fullName").setEditable(false);
					Id = (Integer) table.getContainerProperty(
							event.getItemId(), "ID").getValue();
					System.out.println("aa:" + Id);
					listUpdate();
				}
			}
		});

		/*
		 * Define the names and data types of columns. The "default value"
		 * parameter is meaningless here.
		 */
		table.addContainerProperty("ID", Integer.class, "");
		table.addContainerProperty("NAME", String.class, "");
		table.addContainerProperty("SURNAME", String.class, "");
		table.addContainerProperty("GENDER", String.class, "");
		table.addContainerProperty("BIRTH DATE", String.class, "");
		table.addContainerProperty("BIRTH CITY", String.class, "");
		table.addContainerProperty("STATUS", Boolean.class, "");

		System.out.println("aaaa" + table.getContainerProperty(1, "Id"));
		list();
		tabsheet.addListener(new TabSheet.SelectedTabChangeListener() {
			public void selectedTabChange(SelectedTabChangeEvent event) {
				getApplication().getMainWindow().showNotification(
						"Caught Event");

				// table.setImmediate(true);
				// table.setImmediate(true);
				// table.sort();
				// table.refreshRowCache();
				table.removeAllItems();
				list();

			}
		});
		myTabRoot2.addComponent(table);
		tabsheet.addTab(myTabRoot2);
		tabsheet.getTab(myTabRoot2).setCaption("Second Tab");

		Button generateReport = new Button("Generate");

		combo.addItem("Male Costumer");
		combo.addItem("Customer in Istanbul");
		combo.setNullSelectionAllowed(false);
		generateReport.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					listTab3();
				} catch (Exception e) {
					// TODO Auto-generated catch block
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