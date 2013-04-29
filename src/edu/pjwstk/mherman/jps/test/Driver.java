package edu.pjwstk.mherman.jps.test;

import java.util.List;

public class Driver {

    private String name;
    private Address address;
    private List<Car> cars;

    public Driver(String name, Address address, List<Car> cars) {
        this.name = name;
        this.address = address;
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

}
