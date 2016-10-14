package com.example.vaadinformdatabinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DBConnection {

	public static void main(String[] args) {
		try {

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

			String sql = "INSERT INTO customer (name, surname, gender,birthDate,birthCity,flag)"
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = (PreparedStatement) con
					.prepareStatement(sql);
			preparedStatement.setString(1, "kalite");
			preparedStatement.setString(2, "v");
			preparedStatement.setString(3, "y");
			preparedStatement.setString(4, null);
			preparedStatement.setString(5, "v");
			preparedStatement.setBoolean(6, true);
			
			preparedStatement.executeUpdate();

			// veri tabanina kayit yapiliyor

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("Sürücü projeye eklenmemiş!");

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Veritabanına bağlantı sağlanamadı!");
		}

	}

}
