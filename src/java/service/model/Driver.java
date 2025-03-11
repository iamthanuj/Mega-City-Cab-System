/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.model;

/**
 *
 * @author Thanuja Fernando
 */
public class Driver {

    private int id;
    private String name;
    private String licenseNumber;
    private int phone;

    public Driver(String name, String licenseNumber, int phone) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Driver [id=" + id + ", name=" + name + ", licenseNumber=" + licenseNumber + ", phone=" + phone + "]";
    }

}
