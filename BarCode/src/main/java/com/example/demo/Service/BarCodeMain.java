package com.example.demo.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BarCodeMain {

    public static void main(String[] args) throws BarcodeException, OutputException {
        Barcode barcode = BarcodeFactory.createEAN13("be the coder");
        barcode.setBarHeight(10);
        barcode.setBarWidth(1);
        File imgfile=new File("testbar1.png");
        int width = 200; // Set the width of the image (in pixels)
        int height = 100; // Set the height of the image (in pixels)

        BarcodeImageHandler.savePNG(barcode, imgfile);
    }
}
