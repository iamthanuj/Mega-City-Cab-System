/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.factory;

import service.model.Car;
import service.model.Flex;
import service.model.Van;
import service.model.Vehicle;

/**
 *
 * @author Thanuja Fernando
 */
public class VehicleFactory {

    public static Vehicle getVehicle(String type) {
        if (type.equalsIgnoreCase("Car")) {
            return new Car();
        } else if (type.equalsIgnoreCase("Van")) {
            return new Van();
        } else if (type.equalsIgnoreCase("Flex")) {
            return new Flex();
        }
        return null;
    }

}
