package com.example.demo.Service;

/*mport com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;

public class BarcodeScanner {
    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println("Found port: " + port.getSystemPortName());
            if (port.getSystemPortName().equals("COM3")) { // Replace with your port name
                port.openPort();
                System.out.println("Port opened");
                InputStream in = port.getInputStream();
                try {
                    while (true) {
                        if (in.available() > 0) {
                            char c = (char) in.read();
                            // Handle the received character (barcode data)
                            System.out.print(c);
                            // Add your logic here to process the barcode data
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}*/
