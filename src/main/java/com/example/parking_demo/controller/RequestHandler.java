package com.example.parking_demo.controller;

import com.google.gson.JsonArray;
import com.example.parking_demo.data.CarData;
import com.example.parking_demo.service.ParkingService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RequestHandler {

    private static final HashMap<Integer, String> NOTIFICATION = new HashMap<>();

    public RequestHandler(){
        NOTIFICATION.put(201, "Parking Slot is Allocated");
        NOTIFICATION.put(200, "Request is done.");
    }

    @Autowired
    private ParkingService parkingService;

    @PostMapping
    @RequestMapping("/api/v1/parking/alocatingspace")
    public String alocateSlot(@RequestBody CarData incomingData, HttpServletResponse response){

        int status = parkingService.alocateSpace(incomingData.getParkingSlot());
        response.setStatus(status);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", Integer.toString(status));
        jsonResponse.addProperty("message", NOTIFICATION.get(status));
        return jsonResponse.toString();
    }

    @PostMapping
    @RequestMapping("/api/v1/parking/car")
    public String parkingOneCar(@RequestBody CarData incomingData, HttpServletResponse response){

        int status = parkingService.parkCar(incomingData);
        response.setStatus(status);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", Integer.toString(status));
        jsonResponse.addProperty("message", NOTIFICATION.get(status));
        return jsonResponse.toString();
    }

    @PostMapping
    @RequestMapping("/api/v1/parking/leave")
    public String leaveCar(@RequestBody CarData incomingData, HttpServletResponse response){

        int status = parkingService.leaveCar(incomingData.getSlotNumber());
        response.setStatus(status);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", Integer.toString(status));
        jsonResponse.addProperty("message", NOTIFICATION.get(status));
        return jsonResponse.toString();
    }

    @GetMapping
    @RequestMapping("/api/v1/parking/status")
    public String inquireStatus(HttpServletResponse response){

        HashMap<Integer, CarData> result = parkingService.inquireStatus();
        JsonObject jsonResponse =  new JsonObject();
        JsonArray jsonArray =  new JsonArray();

        if(result != null){

            //Please this iterate should be modified with java 8
            for (Map.Entry<Integer, CarData> car : result.entrySet()) {
                JsonObject jsonObject = new JsonObject();
                /*System.out.println("Slot Number : "+car.getValue().getSlotNumber());
                System.out.println("Slot Number : "+car.getValue().getRegistrationNumber());
                System.out.println("Slot Number : "+car.getValue().getColour());*/
                jsonObject.addProperty("Slot Number", Integer.toString(car.getKey()+1));
                jsonObject.addProperty("Registration Number", car.getValue().getRegistrationNumber());
                jsonObject.addProperty("Colour", car.getValue().getColour());
                jsonArray.add(jsonObject);
            }
            jsonResponse.add("", jsonArray);
            response.setStatus(HttpServletResponse.SC_OK);

        }else{
            jsonResponse.addProperty("Status", Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
            jsonResponse.addProperty("Status", "Parking Lot isn't initialized yet.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return jsonResponse.toString();

        /*response.setStatus(status);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", Integer.toString(status));
        jsonResponse.addProperty("message", NOTIFICATION.get(status));
        return jsonResponse.toString();*/
    }

}


