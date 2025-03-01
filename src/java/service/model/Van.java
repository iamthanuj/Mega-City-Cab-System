/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.model;

/**
 *
 * @author Thanuja Fernando
 */
public class Van implements Vehicle{
    
    private String name;
    private int seats;
    private boolean airConditioned;
    
    
    public Van() {
        this.name = "Toyota KDH";
        this.seats = 12;
        this.airConditioned = true;
    }
    

    @Override
    public String getType() {
        return "Van";
    }

    @Override
    public double getRatePerKm() {
        return 75.0;
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
