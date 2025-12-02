package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public static void generateOrderReport(String orderNumber, String productName, String reference,
                                           String quantity, String price, String total) {

        try {
            // Crear carpeta si no existe
            File dir = new File("reports");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Ruta final: reports/OrderReport_XXXX.txt
            String fileName = "reports/OrderReport_" + orderNumber + ".txt";
            FileWriter writer = new FileWriter(fileName);

            writer.write("=== ORDER REPORT ===\n");
            writer.write("Order Number: " + orderNumber + "\n");
            writer.write("Product: " + productName + "\n");
            writer.write("Reference: " + reference + "\n");
            writer.write("Quantity: " + quantity + "\n");
            writer.write("Price: " + price + "\n");
            writer.write("Total Order: " + total + "\n");

            writer.close();
            System.out.println("📄 Report generated: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
