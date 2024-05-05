package com.example.demo.Service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

@Service
public class BarService {

    public byte[] generateBarcode(String barcodeData, int width, int height) throws Exception {
        
    	System.out.println(barcodeData);
    /*	if (barcodeData!=null) {
            throw new IllegalArgumentException("Barcode data cannot be empty");
        }
*/
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(barcodeData, BarcodeFormat.EAN_8, width, height);
        BufferedImage image = toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
