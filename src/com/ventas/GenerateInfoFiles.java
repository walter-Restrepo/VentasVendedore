package com.ventas;
import java.io.*;
import java.util.*;
/**
 * Class to generate seller and product information files.
 */
public class GenerateInfoFiles {

    public static String[] NOMBRES = {"Juan", "Ana", "Luis", "Maria", "Pedro", "Laura", "Jorge", "Marta"};
    public static final String[] APELLIDOS = {"Perez", "Gomez", "Rodriguez", "Lopez", "Diaz", "Martinez"};
    public static final String[] PRODUCTOS = {"Laptop", "Telefono", "Tablet", "Monitor", "Teclado", "Raton"};

    // Folder where the files will be generated
    private static final String INFO_FOLDER = "informacion/";

    /**
     * Generates a sales file for a salesperson.
     * 
     * @param randomSalesCount Number of random sales..
     * @param name Name of the seller.
     * @param id Seller ID.
     * @throws IOException If an error occurs while writing the file.
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        try (FileWriter writer = new FileWriter(INFO_FOLDER + "vendedor_" + id + ".txt")) {
            Random random = new Random();
            writer.write("ID_Producto;Cantidad\n"); // Header for sales file
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(PRODUCTOS.length) + 1;
                int cantidad = random.nextInt(10) + 1;
                writer.write(productId + ";" + cantidad + "\n");
            }
        }
        System.out.println("Archivo de ventas para " + name + " generado.");
    }

    /**
     * Generates a file with product information.
     * 
     * @param productsCount Number of products to generate.
     * @throws IOException If an error occurs while writing the file.
     */
    public static void createProductsFile(int productsCount) throws IOException {
        try (FileWriter writer = new FileWriter(INFO_FOLDER + "productos.csv")) {
            writer.write("'ID del Producto;Nombre del Producto;Precio\n");  // Header CSV
            Random random = new Random();
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;
                String productName = PRODUCTOS[random.nextInt(PRODUCTOS.length)];
                double price = 100 + (500 - 100) * random.nextDouble(); // Prices between 100 and 500
                writer.write(productId + ";" + productName + ";" + String.format("%.2f", price) + "\n");
            }
        }
        System.out.println("Archivo de productos generado.");
    }

    /**
     * Generate a file with seller information.
     * 
     * @param salesmanCount Number of sellers to generate.
     * @throws IOException If an error occurs while writing the file.
     * @return List of IDs generated for sellers.
     */
    public static List<Long> createSalesManInfoFile(int salesmanCount) throws IOException {
        List<Long> ids = new ArrayList<>();
        try (FileWriter writer = new FileWriter(INFO_FOLDER + "vendedores.csv")) {
            writer.write("Tipo de Documento;ID del Vendedor;Nombre;Apellido\n");  // Header CSV
            Random random = new Random();
            for (int i = 0; i < salesmanCount; i++) {
                String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
                long id = 1000000000L + random.nextInt(900000000);
                ids.add(id);
                writer.write("CC;" + id + ";" + nombre + ";" + apellido + "\n");
            }
        }
        System.out.println("Archivo de vendedores generado.");
        return ids;
    }

    /**
     * Main method to execute file generation.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            new File(INFO_FOLDER).mkdir();  // Make sure the folder exists
            List<Long> ids = createSalesManInfoFile(10);
            createProductsFile(6);

            // Generate sales files for each salesperson (with 5 sales per salesperson)
            for (int i = 0; i < ids.size(); i++) {
                createSalesMenFile(5, NOMBRES[i % NOMBRES.length], ids.get(i));
            }
        } catch (IOException e) {
            System.err.println("Error al generar archivos: " + e.getMessage());
        }
    }
}