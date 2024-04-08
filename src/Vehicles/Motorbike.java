package Vehicles;

import Operations.Rentable;

public class Motorbike extends Vehicle implements Rentable {
    public enum MotorbikeBody{SCOOTER, STANDARD, SPORTBIKE, CRUISER};
    private boolean available = true;
    private MotorbikeBody style;

    public Motorbike(int id,
                     String brand,
                     String model,
                     int yearOfProduction,
                     int ratePerDay,
                     MotorbikeBody style){
        super(id,brand,model,yearOfProduction,ratePerDay);
        this.style = style;
    }
    public MotorbikeBody getStyle(){return this.style;}

    public void setStyle(MotorbikeBody style){this.style = style;}

    public void displayInfo(){
        super.displayInfo();
        System.out.println("Motorbike body: " + style);
    }

    @Override
    public boolean isAvailable() {
        return available; //no logic
    }
}
