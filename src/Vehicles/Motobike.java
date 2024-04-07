package Vehicles;

import Operations.Rentable;
import Vehicles.Vehicle;

public class Motobike extends Vehicle implements Rentable {

    public Motobike(){}

    @Override
    public boolean isAvailable() {
        return available;
    }
}
