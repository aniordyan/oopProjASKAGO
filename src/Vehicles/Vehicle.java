package Vehicles;

public abstract class Vehicle {
    private int id;
    private String brand;
    private String model;
    private int yearOfProduction;
    private int ratePerDay;


    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getBrand(){
        return this.brand;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfProduction() {
        return this.yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public int getRatePerDay() {
        return this.ratePerDay;
    }

    public void setRatePerDay(int ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public void displayInfo() {
        System.out.println("ID: " + id);
        System.out.println("Brand: " + brand);
        System.out.println("Model " + model);
        System.out.println("Year of production: " + yearOfProduction);
        System.out.println("Rental Rate: " + ratePerDay);

    }





}
