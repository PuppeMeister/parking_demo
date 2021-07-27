package com.example.parking_demo.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import org.springframework.stereotype.Component;

public class CarData {

    private int slotNumber;
    private String registrationNumber;
    private String colour;
    private int parkingSlot;
    private String batchData;

    public CarData(@JsonProperty("slotNumber") int slotNumber,
                   @JsonProperty("registrationNumber") String registrationNumber,
                   @JsonProperty("colour") String colour,
                   @JsonProperty("slot") int parkingSlot,
                   @JsonProperty("batchData") String batchData){
        this.slotNumber = slotNumber;
        this.registrationNumber = registrationNumber;
        this.colour = colour;
        this.parkingSlot = parkingSlot;
    }
    public CarData(){
        this.slotNumber = 0;
        this.registrationNumber = "vacant";
        this.colour = "vacant";
    }

    public void setBatchData(String batchData) {
        this.batchData = batchData;
    }

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

    public String getBatchData() {
        return this.batchData;
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
