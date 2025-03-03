/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.model;

import java.time.LocalDateTime;

/**
 *
 * @author Thanuja Fernando
 */
public class Booking {

    private int id;
    private int userId;
    private Vehicle vehicle; // Now uses the Vehicle interface
    private double distance;
    private double totalCost;
    private String startLocation;
    private String endLocation;
    private LocalDateTime datetime;
    private String address;

    public Booking(int userId, Vehicle vehicle, double distance, double totalCost,
            String startLocation, String endLocation, LocalDateTime datetime, String address) {
        this.userId = userId;
        this.vehicle = vehicle;
        this.distance = distance;
        this.totalCost = totalCost;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.datetime = datetime;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private double calculateTotalCost() {
        return vehicle.getRatePerKm() * distance;
    }

    public int getUserId() {
        return userId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getDistance() {
        return distance;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getAddress() {
        return address;
    }

}
