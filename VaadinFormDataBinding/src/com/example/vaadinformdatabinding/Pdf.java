package com.example.vaadinformdatabinding;

import java.io.File;
import java.io.FileOutputStream;

import javafx.scene.paint.Color;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Pdf {

	public static void main(String[] args) throws Exception {
		createSamplePDF(new String[]{"name", "surname","gender","birthDate","birthCity","flag"}, new String[][]{{"rw11", "rw12"},{"rw21", "rw22"},{"rw31", "rw32"}});
		System.out.println("basarili");
	}

	public static void createSamplePDF(String header[], String body[][])
			throws Exception {
		Document documento = new Document();
		// Create new File
		File file = new File("aa.pdf");
		file.createNewFile();
		FileOutputStream fop = new FileOutputStream(file);
		PdfWriter.getInstance(documento, fop);
		documento.open();
		// Fonts
		//Font fontHeader = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
		//Font fontBody = new Font(Font.COURIER, 12, Font.NORMAL);
		// Table for header
		PdfPTable cabetabla = new PdfPTable(header.length);
		for (int j = 0; j < header.length; j++) {
			Phrase frase = new Phrase(header[j]);
			PdfPCell cell = new PdfPCell(frase);
			//cell.setBackgroundColor(new BaseColor(Color.lightGray.getRGB()));
			cabetabla.addCell(cell);
		}
		documento.add(cabetabla);
		// Tabla for body
		PdfPTable tabla = new PdfPTable(header.length);
		for (int i = 0; i < body.length; i++) {
			for (int j = 0; j < body[i].length; j++) {
				tabla.addCell(new Phrase(body[i][j]));
			}
		}
		documento.add(tabla);
		documento.close();
		fop.flush();
		fop.close();
	}
	
	
	
	

}
