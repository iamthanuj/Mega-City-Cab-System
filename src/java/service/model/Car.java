/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.model;

/**
 *
 * @author Thanuja Fernando
 */
public class Car implements Vehicle{
    private String name;
    private int seats;
    private boolean airConditioned;
    
    
    public Car() {
        this.name = "Toyota Prius";
        this.seats = 4;
        this.airConditioned = true;
    }
    
    @Override
    public String getType(){
        return "Car";
    }

    @Override
    public double getRatePerKm() {
        return 50.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSeats() {
        return seats;
    }

    @Override
    public boolean isAirConditioned() {
        return airConditioned;
    }
    
}
