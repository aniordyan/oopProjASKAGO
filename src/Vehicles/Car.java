package Vehicles;

import Operations.Rentable;
import Vehicles.Vehicle;

public class Car extends Vehicle implements Rentable {
    public enum CarBody{
        SEDAN, SUV, CROSSOVER, MICRO
    }

    private boolean available = true;
    private CarBody style;

    public Car(int id,
               String brand,
               String model,
               int yearOfProduction,
               int ratePerDay,
               CarBody style){
        super(id,brand,model,yearOfProduction,ratePerDay);
        this.style = style;
    }

    public CarBody getStyle(){
        return this.style;
    }

    public void setStyle(CarBody style){
        this.style = style;
    }

    @Override
    public void displayInfo() { //probably remove and add to operations
        super.displayInfo();
        System.out.println("Car body: " + style);
    }

    @Override
    public boolean isAvailable() {
        return available;//add logic
    }
}
