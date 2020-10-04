package com.intellize.nb_food_delervery.database.entity;

public class Driver {

    private String name;
    private String contactNo;
    private String vehicleNo;

    public Driver() {
    }

    public Driver(String name, String contactNo, String vehicleNo) {
        this.name = name;
        this.contactNo = contactNo;
        this.vehicleNo = vehicleNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
