package Operations;

import java.util.Scanner;

public class AddNewCar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter brand: ");
        String brand = sc.next();
        System.out.println("Enter model: ");
        String model = sc.next();
        System.out.println("Enter year of production: ");
        int yearOfProduction = sc.nextInt();
    }
}
