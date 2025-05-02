package com.student.utils;

import java.util.List;
import java.util.Scanner;

public class InputHelper implements IInputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public int readInt(String message) {
        int value = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(message);
                value = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
        return value;
    }

    @Override
    public double readDouble(String message) {
        double value = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print(message);
                value = Double.parseDouble(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal number. Try again.");
            }
        }
        return value;
    }

    @Override
    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    @Override
    public <T> T chooseFromList(List<T> options, String message) {
        if (options == null || options.isEmpty()) {
            System.out.println("No options available.");
            return null;
        }

        System.out.println(message);
        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }

        int choice = readInt("Enter your choice: ");
        if (choice < 1 || choice > options.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return options.get(choice - 1);
    }

    @Override
    public boolean readYesNo(String message) {
        while (true) {
            String input = readString(message + " (y/n): ").trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }
}
