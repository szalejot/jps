package edu.pjwstk.mherman.jps.test;

public class Car {

    private String brand;
    private int yearOfProduction;
    private CarType type;
    
    public Car(CarType type, String brand, int yearOfProduction) {
        this.type = type;
        this.brand = brand;
        this.yearOfProduction = yearOfProduction;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public enum CarType {
        PASSENGER,
        DELIVERY
    }
}
