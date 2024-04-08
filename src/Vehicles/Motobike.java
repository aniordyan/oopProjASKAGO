package Vehicles;

import Operations.Rentable;
import Vehicles.Vehicle;

public class Motobike extends Vehicle implements Rentable {
    private boolean available = true;

    public Motobike(){
        super();
    }



    @Override
    public boolean isAvailable() {
        return available;
    }
}
