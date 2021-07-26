package com.example.parking_demo.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import org.springframework.stereotype.Component;

public class CarData {

    private int slotNumber;
    private String registrationNumber;
    private String colour;
    private int parkingSlot;

    public CarData(int slotNumber,
                   @JsonProperty("registrationNumber") String registrationNumber,
                   @JsonProperty("colour") String colour,
                   @JsonProperty("slot") int parkingSlot){
        this.slotNumber = slotNumber;
        this.registrationNumber = registrationNumber;
        this.colour = colour;
        this.parkingSlot = parkingSlot;
    }
    public CarData(){}

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }
    public void setRegistrationNumber(@JsonProperty("registrationNumber") String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public void setColour(@JsonProperty("colour") String colour) {
        this.colour = colour;
    }
    public void setParkingSlot(@JsonProperty("slot") int slot) {
        this.parkingSlot = slot;
    }

    public int getParkingSlot() {
        return this.parkingSlot;
    }

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
