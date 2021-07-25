package com.example.parking_demo;

import com.example.parking_demo.data.CarData;
import com.example.parking_demo.data.ParkingData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@DisplayName("Parking Service")
@ExtendWith(ParameterResolverParkingDataSpec.class) //Calling or tie this class with Parameter Resolver (Mock Data)
public class ParkingServiceSpec {

    @Autowired
    ParkingData parkingData;

    @BeforeAll
    void init(ParkingData parkingData){

        this.parkingData = parkingData;
    }

    @DisplayName("Initiate Parking Lot")
    @Test
    void initiateParkingLot(){

    }



}
