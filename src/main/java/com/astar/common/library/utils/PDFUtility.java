package com.astar.common.library.utils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

//; TODO ADD MORE
public abstract class PDFUtility {
    //    TODO RECHECK THIS
    public static String getText(File file) {
        try (PDDocument pdDocument = Loader.loadPDF(file)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return pdfTextStripper.getText(pdDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
