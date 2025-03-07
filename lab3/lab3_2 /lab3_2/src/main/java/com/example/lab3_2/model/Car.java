package com.example.lab3_2.model;

public class Car {
    private String maker;
    private String model;
    private Long carId;

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car() {
    }


    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Car{" +
                "maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                ", carId=" + carId +
                '}';
    }
}
