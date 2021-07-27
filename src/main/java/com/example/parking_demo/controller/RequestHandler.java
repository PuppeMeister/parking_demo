package com.example.parking_demo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.example.parking_demo.data.CarData;
import com.example.parking_demo.service.ParkingService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @GetMapping
    @RequestMapping("/api/v1/parking/inquiry/reg")
    public String  inquryRegNoBasedOnColour(@RequestParam String colour, HttpServletResponse response){

        ArrayList<String> result = parkingService.inquryRegNoBasedOnColour(colour);
        JsonObject jsonResponse =  new JsonObject();
        JsonArray jsonArray =  new JsonArray();

        if(result != null){
            return result.toString();
        }else{
            jsonResponse.addProperty("Status", Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
            jsonResponse.addProperty("Status", "Data is not Found.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return jsonResponse.toString();
        }
    }

    @GetMapping
    @RequestMapping("/api/v1/parking/inquiry/slot1")
    public String inqurySlotNoBasedOnColour(@RequestParam String colour, HttpServletResponse response){
        ArrayList<String> result = parkingService.inqurySlotNoBasedOnColour(colour);
        JsonObject jsonResponse =  new JsonObject();
        JsonArray jsonArray =  new JsonArray();

        if(result != null){
            return result.toString();
        }else{
            jsonResponse.addProperty("Status", Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
            jsonResponse.addProperty("Status", "Data is not Found.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return jsonResponse.toString();
        }
    }

    @GetMapping
    @RequestMapping("/api/v1/parking/inquiry/slot2")
    public String inqurySlotNoBasedOnRegNo(@RequestParam String regNo, HttpServletResponse response){
        ArrayList<String> result = parkingService.inqurySlotNoBasedOnRegNo(regNo);
        JsonObject jsonResponse =  new JsonObject();
        JsonArray jsonArray =  new JsonArray();

        if(result != null){
            return result.toString();
        }else{
            jsonResponse.addProperty("Status", Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
            jsonResponse.addProperty("Status", "Data is not Found.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return jsonResponse.toString();
        }
    }

    /* This endpoint is just for testing the last three features; inquiryRegNoBasedOnColour, inqurySlotNoBasedOnColour
     * and inqurySlotNoBasedOnRegNo, to full the parking lot with a lot of data quicly.
     */
    @PostMapping
    @RequestMapping("/api/v1/parking/batchData")
    public String batchData(@RequestBody String batchData, HttpServletResponse response){

        int status = parkingService.insertBatchData(batchData);
        response.setStatus(status);
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("status", Integer.toString(status));
        jsonResponse.addProperty("message", status == 201 ? "Parking Lot is Successfully Created" : "Failed");

        return jsonResponse.toString();
    }
}


