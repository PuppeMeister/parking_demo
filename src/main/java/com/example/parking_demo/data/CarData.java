package com.example.parking_demo.data;

import jdk.jfr.DataAmount;
import org.springframework.stereotype.Component;

public class CarData {

    private int slotNumber;
    private String registrationNumber;
    private String colour;

    public CarData(int slotNumber, String registrationNumber, String colour){
        this.slotNumber = slotNumber;
        this.registrationNumber = registrationNumber;
        this.colour = colour;
        }

    /* public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public void setColour(String colour) {
        this.colour = colour;
    } */

    public int getSlotNumber() {
        return this.slotNumber;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public String getColour() {
        return this.colour;
    }


}
