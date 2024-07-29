import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SafeInput {

    // Method A
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();

        } while (retString.length() == 0);

        return retString;
    }

    // Method B
    public static int getInt(Scanner pipe, String prompt) {

        int value = 0;
        boolean validInput = false;


        while (!validInput) {
            System.out.print(prompt);
            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                validInput = true;
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash + ". Please enter a valid integer.");
            }
            pipe.nextLine();
        }

        return value;
    }

    // Method C
    public static double getDouble(Scanner pipe, String prompt){

        double value = 0.0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            if (pipe.hasNextDouble()) {
                value = pipe.nextDouble();
                validInput = true;
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash + ". Please enter a valid double.");
            }
            pipe.nextLine();
        }

        return value;
    }

    // Method D
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high){

        int value = 0;
        boolean validInput = false;

        while(!validInput) {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                if (value >= low && value <= high) {
                    validInput = true;

                } else {
                    System.out.println("Invalid input: " + value + ". Please enter a value between " + low + " and " + high + ".");

                }

            } else {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash + ". Please enter a valid integer.");

            }
            pipe.nextLine();
        }

        return value;
    }

    // Method E
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {

        double value = 0.0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                value = pipe.nextDouble();
                if (value >= low && value <=  high) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input: " + value + ". Please enter a value between " + low + " and " + high + ".");
                }
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash + ". Please enter a valid double.");
            }
            pipe.nextLine();
        }

        return value;
    }

    // Method F
    public static boolean getYNConfirm(Scanner pipe, String prompt) {

        boolean validInput = false;
        boolean response = false;

        while (!validInput) {
            System.out.print(prompt + " [Y/N]: ");
            String input = pipe.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("n")) {
                validInput = true;
                response = input.equals("y");
            } else {
                System.out.println("Invalid input: " + input + ".Please enter 'Y' or 'N'.");
            }
        }
        return response;
    }

    // Method G
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {

        boolean validInput = false;
        String input = "";

        while (!validInput) {
            System.out.print(prompt + " ");
            if (pipe.hasNextLine()) {
                input = pipe.nextLine().trim();
                if (input.matches(regEx)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input: " + input + ". Please enter a string that matches the pattern: " + regEx);
                }
            }
        }
        return input;
    }

    // Pretty header method
    public static void prettyHeader(String msg) {
        int totalWidth = 60;
        int starsWidth = 3;
        int maxMessageWidth = totalWidth - starsWidth * 2 - 2; // 2 for the spaces on each side of the message

        // Print the top border
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();

        // Print the message lines
        while (msg.length() > 0) {
            String line;
            if (msg.length() > maxMessageWidth) {
                line = msg.substring(0, maxMessageWidth);
                msg = msg.substring(maxMessageWidth);
            } else {
                line = msg;
                msg = "";
            }

            int paddingWidth = (totalWidth - starsWidth * 2 - line.length()) / 2;

            System.out.print("***");
            for (int i = 0; i < paddingWidth; i++) {
                System.out.print(" ");
            }
            System.out.print(line);
            for (int i = 0; i < paddingWidth; i++) {
                System.out.print(" ");
            }
            // Add an extra space if the total width is odd
            if ((line.length() + starsWidth * 2) % 2 != 0) {
                System.out.print(" ");
            }
            System.out.println("***");
        }

        // Print the bottom border
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

}

