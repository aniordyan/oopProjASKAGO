package Operations;

import Vehicles.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.List;
import java.util.Scanner;

public class AddNewCar {
    private static final String FILE_PATH = "database.txt";
 //   private List<Car> cars;

    public void add() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id: ");
        int id = sc.nextInt();
        System.out.println("Enter brand: ");
        String brand = sc.next();
        System.out.println("Enter model: ");
        String model = sc.next();
        System.out.println("Enter year of production: ");
        int yearOfProduction = sc.nextInt();
        System.out.println("Enter rate per hour: ");
        int ratePerHour = sc.nextInt();
        System.out.println("Enter car style (SEDAN, SUV, COUPE, etc.): ");
        String styleInput = sc.next().toUpperCase();

        Car.CarBody style = Car.CarBody.valueOf(styleInput);

        // Parse the user input to CarStyle enum
  /*      Car.CarBody style;
        try {
            style = Car.CarBody.valueOf(styleInput);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid car style. Please enter one of: " + Car.CarBody.values());
            return; // Exit the method
        } */

        // Create a new Car object
        Car car = new Car(id,brand, model, yearOfProduction,ratePerHour,style);

        // Save the car data to the text file
        saveCarData(car);
    }

    private void saveCarData(Car car) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            // Append car data to the file in CSV format
            writer.println(car.getBrand() + "," + car.getModel() + "," + car.getYearOfProduction() + "," + car.getStyle());
            System.out.println("Car added successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred while saving car data: " + e.getMessage());
        }
    }
}
