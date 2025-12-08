


package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public static void generateOrderReport(String orderNumber, String productName, String reference,
                                           String quantity, String price, String total) {

        try {
            // Ruta absoluta al directorio de trabajo
            String basePath = System.getProperty("user.dir") + "/reports";

            // Crear carpeta
            File dir = new File(basePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Crear archivo
            String fileName = basePath + "/OrderReport_" + orderNumber + ".txt";
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

