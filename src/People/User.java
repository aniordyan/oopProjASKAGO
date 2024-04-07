package People;

import java.util.Scanner;

public class User {

    public static void EntryPoint() {
        while (true) {
            //Options for the user
            System.out.println();
            System.out.println("===== Welcome to Askago Vehicle Rental System =====");
            System.out.println("1. View Cars");
            System.out.println("1. View Motobikes");
            System.out.println("3. Rent a Vehicle");
            System.out.println("4. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

            }
        }
    }
}
