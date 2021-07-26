package com.example.parking_demo.controller;

import com.example.parking_demo.data.CarData;
import com.example.parking_demo.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class RequestHandler {

    private static final HashMap<Integer, String> NOTIFICATION = new HashMap<>();


    public RequestHandler(){
        NOTIFICATION.put(201, "Parking Slot is Allocated");
    }

    @Autowired
    private ParkingService parkingService;
    /*
    @PostMapping
    public void retrieveIncomingRequest(){

    }*/


    @PostMapping
    @RequestMapping("/api/v1/parking/alocatingspace")
    public void alocateSlot(@RequestBody CarData incomingData, HttpServletResponse response){
        int status = parkingService.alocateSpace(incomingData.getParkingSlot());
        response.setStatus(status);
        //System.out.println("SLOT = "+ slot.getSlot());
    }


}


