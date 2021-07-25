package com.example.parking_demo.data;

import org.springframework.stereotype.Component;

import java.util.HashMap;

public class ParkingData {

    private int parkingSlotLength;
    private HashMap<Integer, Integer> vacantSlot =  new HashMap<>();
    private CarData[] parkingData;
    private HashMap<String, CarData> parkingLotData = new HashMap<>();
    private HashMap<Integer, String> parkingLotIndex = new HashMap<>();

    public ParkingData(){}

    public void setParkingSlotLength(int data){
        this.parkingSlotLength = data;
    }
    public void setVacantSlot(int key, int value){
        this.vacantSlot.put(key, value);
    }
    public void setParkingData(int key, int value){
        this.vacantSlot.put(key, value);
    }
    public void setParkingData(String key, CarData value){
        this.parkingLotData.put(key, value);
    }
    public void setParkingIndex(int key, String value){
        this.parkingLotIndex.put(key, value);
    }



}
