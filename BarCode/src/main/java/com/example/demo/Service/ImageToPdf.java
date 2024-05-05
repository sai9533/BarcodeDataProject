package com.example.demo.Service;

import com.itextpdf.text.pdf.PdfDocument;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class ImageToPdf {
    public static void main(String[] args) {
        String imagePath = "testbar1.png";
        String pdfPath = "pdfdata.pdf";

        try {
            // Create a Document object
            Document document = new Document();

            // Create a PdfWriter instance
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

            // Open the document
            document.open();

            // Add an image to the PDF
            Image image = Image.getInstance(imagePath);
            document.add(image);

            // Close the document
            document.close();

            System.out.println("PDF created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating PDF: " + e.getMessage());
        }
    }
}

