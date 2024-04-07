package Vehicles;

import Operations.Rentable;
import Vehicles.Vehicle;

public class Car extends Vehicle implements Rentable {

    public Car(){}

    @Override
    public boolean isAvailable() {
        return available;
    }
}
