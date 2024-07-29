import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LineMakerEditor {
    private static boolean needsToBeSaved = false;
    private static String currentFilename = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        boolean keepRunning = true;

        while (keepRunning) {
            printList(list);
            printMenu();
            String command = SafeInput.getRegExString(scanner, "Enter a command", "[AaDdIiMmOoPpQqSsCcVv]").toUpperCase();

            switch (command) {
                case "A":
                    addItem(scanner, list);
                    break;
                case "D":
                    deleteItem(scanner, list);
                    break;
                case "I":
                    insertItem(scanner, list);
                    break;
                case "M":
                    moveItem(scanner, list);
                    break;
                case "O":
                    if (needsToBeSaved && SafeInput.getYNConfirm(scanner, "You have unsaved changes. Do you want to save them first?")) {
                        saveList(scanner, list);
                    } else if (needsToBeSaved) {
                        if (!SafeInput.getYNConfirm(scanner, "Are you sure you want to discard unsaved changes?")) {
                            break;
                        }
                    }
                    openList(scanner, list);
                    break;
                case "S":
                    saveList(scanner, list);
                    break;
                case "C":
                    clearList(scanner, list);
                    break;
                case "V":
                    printList(list);
                    break;
                case "Q":
                    keepRunning = quit(scanner, list);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }

        System.out.println("Exiting program...");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("V - View the list");
        System.out.println("Q - Quit the program");
    }

    private static void addItem(Scanner scanner, ArrayList<String> list) {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter the item to add");
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added to the list.");
    }

    private static void deleteItem(Scanner scanner, ArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }
        int index = SafeInput.getRangedInt(scanner, "Enter the item number to delete", 1, list.size()) - 1;
        list.remove(index);
        needsToBeSaved = true;
        System.out.println("Item deleted from the list.");
    }

    private static void insertItem(Scanner scanner, ArrayList<String> list) {
        int index = SafeInput.getRangedInt(scanner, "Enter the position number to insert the item at", 1, list.size() + 1) - 1;
        String item = SafeInput.getNonZeroLenString(scanner, "Enter the item to insert");
        list.add(index, item);
        needsToBeSaved = true;
        System.out.println("Item inserted into the list.");
    }

    private static void moveItem(Scanner scanner, ArrayList<String> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to move.");
            return;
        }
        int fromIndex = SafeInput.getRangedInt(scanner, "Enter the item number to move", 1, list.size()) - 1;
        int toIndex = SafeInput.getRangedInt(scanner, "Enter the new position number for the item", 1, list.size()) - 1;
        String item = list.remove(fromIndex);
        list.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved in the list.");
    }

    private static void openList(Scanner scanner, ArrayList<String> list) {
        System.out.print("Enter the filename to open: ");
        String filename = scanner.nextLine().trim();
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        list.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            currentFilename = filename;
            needsToBeSaved = false;
            System.out.println("List loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveList(Scanner scanner, ArrayList<String> list) {
        if (currentFilename.isEmpty()) {
            System.out.print("Enter the filename to save as: ");
            String filename = scanner.nextLine().trim();
            if (!filename.endsWith(".txt")) {
                filename += ".txt";
            }
            currentFilename = filename;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilename))) {
            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
            needsToBeSaved = false;
            System.out.println("List saved to " + currentFilename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList(Scanner scanner, ArrayList<String> list) {
        if (SafeInput.getYNConfirm(scanner, "Are you sure you want to clear the list?")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }

    private static boolean quit(Scanner scanner, ArrayList<String> list) {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(scanner, "You have unsaved changes. Do you want to save them?")) {
                saveList(scanner, list);
            } else if (!SafeInput.getYNConfirm(scanner, "Are you sure you want to discard unsaved changes?")) {
                return true;  // Continue running the program
            }
        }
        return !SafeInput.getYNConfirm(scanner, "Are you sure you want to quit?");
    }

    private static void printList(ArrayList<String> list) {
        System.out.println("\nCurrent List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ": " + list.get(i));
        }
    }
}