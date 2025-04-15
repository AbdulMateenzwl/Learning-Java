package org.example;

class Loops {
    public static void main(String[] args) {

        // 1. For loop
        System.out.println("For loop:");
        for (int i = 0; i < 5; i++) {
            System.out.println("i = " + i);
        }

        // 2. While loop
        System.out.println("\nWhile loop:");
        int j = 0;
        while (j < 5) {
            System.out.println("j = " + j);
            j++;
        }

        // 3. Do-While loop
        System.out.println("\nDo-While loop:");
        int k = 0;
        do {
            System.out.println("k = " + k);
            k++;
        } while (k < 5);

        // 4. Enhanced For loop (for-each loop)
        System.out.println("\nEnhanced For loop:");
        int[] numbers = {10, 20, 30, 40, 50};
        for (int num : numbers) {
            System.out.println("num = " + num);
        }

        // 5. Nested loop (for loop inside for loop)
        System.out.println("\nNested loop:");
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                System.out.print("(" + row + "," + col + ") ");
            }
            System.out.println(); // Newline after each row
        }

    }
}
