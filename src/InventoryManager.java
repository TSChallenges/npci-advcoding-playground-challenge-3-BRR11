import java.io.*;
import java.util.*;

public class InventoryManager {

    public static void main(String[] args) {
        // Entry point for the program
        // TODO: Implement menu-driven program for inventory management

        Scanner scanner = new Scanner(System.in);
        String fileName = "inventory.txt";

        while (true) {
            System.out.println("\nInventory Management System");
            System.out.println("1.Display Inventory");
            System.out.println("2.Add New Item");
            System.out.println("3.Update Existing Item");
            System.out.println("4.Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    readInventory(fileName);
                    break;
                case 2:
                    System.out.print("Enter item name: ");
                    String newItem = scanner.nextLine().trim();
                    System.out.print("Enter item count: ");
                    int newCount = scanner.nextInt();
                    addItem(fileName, newItem, newCount);
                    break;
                case 3:
                    System.out.print("Enter item name: ");
                    String existingItem = scanner.nextLine().trim();
                    System.out.print("Enter new item count: ");
                    int updatedCount = scanner.nextInt();
                    updateItem(fileName, existingItem, updatedCount);
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void readInventory(String fileName) {
        // TODO: Read and display inventory from file
          File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Inventory file not found. Creating a new one.");
            createInventoryFile(fileName);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isEmpty = true;

            System.out.println("\nCurrent Inventory:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    System.out.printf("%s: %s\n", parts[0], parts[1]);
                    isEmpty = false;
                }
            }

            if (isEmpty) {
                System.out.println("Inventory is empty.");
            }
        } catch (IOException e) {
            System.err.println("Error reading the inventory file: " + e.getMessage());
        }
    }

    public static void addItem(String fileName, String itemName, int itemCount) {
        // TODO: Add a new item to the inventory
          Map<String, Integer> inventory = loadInventory(fileName);

        if (inventory.containsKey(itemName)) {
            System.out.println("Item already exists. Use the update option to modify it.");
        } else {
            inventory.put(itemName, itemCount);
            saveInventory(fileName, inventory);
            System.out.println("Item added successfully.");
        }
    }

    public static void updateItem(String fileName, String itemName, int itemCount) {
        // TODO: Update the count of an existing item
         Map<String, Integer> inventory = loadInventory(fileName);

        if (inventory.containsKey(itemName)) {
            inventory.put(itemName, itemCount);
            saveInventory(fileName, inventory);
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item does not exist. Use the add option to create it.");
        }
    }

     private static Map<String, Integer> loadInventory(String fileName) {
        Map<String, Integer> inventory = new HashMap<>();

        File file = new File(fileName);
        if (!file.exists()) {
            createInventoryFile(fileName);
            return inventory;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    inventory.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the inventory file: " + e.getMessage());
        }

        return inventory;
    }

    private static void saveInventory(String fileName, Map<String, Integer> inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the inventory file: " + e.getMessage());
        }
    }

    private static void createInventoryFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Create an empty file
        } catch (IOException e) {
            System.err.println("Error creating the inventory file: " + e.getMessage());
        }
    }
}
