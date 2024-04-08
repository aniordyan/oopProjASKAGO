package People;

import Operations.AddNewCar;

import java.util.Scanner;

public class Admin {
    public void EntryPoint() {// should it be static or not
        while (true) {
            System.out.println();
            System.out.println("===== Welcome to ASKAGO Vehicle Rental System Admin Portal=====");
            System.out.println("1. View Cars");
            System.out.println("1. View Motobikes");
            System.out.println("3. Add Vehicle");
            System.out.println("4. Remove Vehicle");
            System.out.println("4. Show Rents");
            System.out.println();
            System.out.print("Enter your choice: ");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 3:
                    AddNewCar addNewCar = new AddNewCar();
                    addNewCar.add();
            }
        }
    }
}
