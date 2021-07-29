package com.example.parking_demo.service;

import com.example.parking_demo.data.CarData;
import com.example.parking_demo.data.ParkingDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ParkingService implements ParkingDao {

    private HashMap<Integer, CarData> parkingLot = new HashMap<>();

    public ParkingService(){}

    public void resetParkingLot(){
        parkingLot.clear();
    }

    public HashMap<Integer, CarData> getParkingLot(){
        return parkingLot;
    }

    public void setParkingLot(HashMap parkingLot){
        this.parkingLot = parkingLot;
    }

    public int alocateSpace(int slot){
        if(parkingLot.isEmpty()){

            for(int i=0; i<slot; i++){
                parkingLot.put(i, new CarData());
            }

            return HttpServletResponse.SC_CREATED;
        }else{
            return HttpServletResponse.SC_FORBIDDEN;
        }
    };

    public int parkCar(CarData car){
        int indexParkingLot = 0;
        StringBuilder sb = new StringBuilder();

        if(parkingLot.isEmpty()){
            return HttpServletResponse.SC_BAD_REQUEST;
        }else{
            //parkingLot.entrySet().stream().filter(slot -> slot.getValue().getRegistrationNumber() == null).findFirst().ifPresent(slot -> sb.append(slot.getKey()));
            parkingLot.entrySet().stream().filter(slot -> slot.getValue().getRegistrationNumber() == "vacant").
                    findFirst().ifPresent(slot -> sb.append(slot.getKey()));

            if(sb.toString().isEmpty()){
                return HttpServletResponse.SC_BAD_REQUEST;
            }else{

                indexParkingLot = Integer.parseInt(sb.toString());
                car.setSlotNumber(indexParkingLot+1);
                parkingLot.replace(indexParkingLot, car);
                return HttpServletResponse.SC_OK;
            }
        }
        //parkingLot.entrySet().stream().filter(slot -> slot.getValue().getRegistrationNumber() == null).findFirst().ifPresent(slot -> System.out.println(slot.getKey()));

        //parkingLot.entrySet().stream().filter(slot -> slot.getValue().getRegistrationNumber() == null).findFirst().ifPresent(slot -> sb.append(slot.getKey()));

        //System.out.println("Ini --> "+ Integer.parseInt("0"));
    };

    public int leaveCar(int slotNumber){

        int status = 0;
        int slotNo = slotNumber - 1;
        try{

            /*parkingLot.entrySet().stream().filter(slot -> slot.getValue().getSlotNumber() ==slotNo).
                    findFirst().ifPresent());*/
            if(parkingLot.get(slotNo) != null){

                /*
                System.out.println("Before leaving");
                System.out.println(parkingLot.get(slotNumber - 1).getSlotNumber());
                System.out.println(parkingLot.get(slotNumber - 1).getRegistrationNumber());
                System.out.println(parkingLot.get(slotNumber - 1).getColour());
                 */
                //parkingLot.remove((slotNumber - 1));
                parkingLot.put((slotNumber - 1), new CarData(slotNumber, "vacant", "vacant", 0, "") );
                /*
                System.out.println("After leaving");
                System.out.println(parkingLot.get(slotNumber - 1).getSlotNumber());
                System.out.println(parkingLot.get(slotNumber - 1).getRegistrationNumber());
                System.out.println(parkingLot.get(slotNumber - 1).getColour());
                */
                status = HttpServletResponse.SC_OK;

            }else{
                status = HttpServletResponse.SC_FORBIDDEN;
            }

        }catch(Exception e){
            System.out.println("e : "+e);
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        finally {
            return status;
        }

    }

    public HashMap<Integer, CarData> inquireStatus(){
          return parkingLot;
    }

    public HashMap<Integer, CarData> AinquireStatus(){
        Map<Integer, CarData> result;
        result = parkingLot.entrySet().stream().filter(parkingData -> !parkingData.getValue().getRegistrationNumber().equals("vacant"))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return new HashMap<Integer, CarData>(result);
    }

    @Override
    public ArrayList<String> inquryRegNoBasedOnColour(String colour) {

        ArrayList<String> result = new ArrayList<>();
        if(!parkingLot.isEmpty()){
            parkingLot.entrySet().stream().filter(car -> car.getValue().getColour().equals(colour)).forEach(car -> result.add(car.getValue().getRegistrationNumber()));
        }
        return result;
    }

    @Override
    public ArrayList<String>inqurySlotNoBasedOnColour(String colour) {

        ArrayList<String> result = new ArrayList<>();
        if(!parkingLot.isEmpty()){
            parkingLot.entrySet().stream().filter(car -> car.getValue().getColour().equals(colour)).forEach(car -> result.add(Integer.toString((car.getKey()+1))));
        }
        return result;
    }

    @Override
    public ArrayList<String> inqurySlotNoBasedOnRegNo(String regNo) {

        ArrayList<String> result = new ArrayList<>();
        if(!parkingLot.isEmpty()) {
            parkingLot.entrySet().stream().filter(car -> car.getValue().getRegistrationNumber().equals(regNo)).forEach(car -> result.add(Integer.toString((car.getValue().getSlotNumber()))));
        }
        return result;
    }

    @Override
    public int insertBatchData(String batchData) {

        JsonObject jsonObject = new JsonParser().parse(batchData).getAsJsonObject();
        Gson gson = new Gson();
        JsonObject[]  carList = gson.fromJson(jsonObject.get("batchData"), JsonObject[].class);

        HashMap<Integer, CarData> dataParkingLot =  new HashMap<>();
        int i = 0;
        for(JsonObject obj : carList){

            dataParkingLot.put(i, new CarData((i+1), obj.get("registrationNumber").toString().replace("\"", ""),
                    obj.get("colour").toString().replace("\"", ""),
                    0, "") );
            i++;
        }

        this.parkingLot = dataParkingLot;
        /*System.out.println("Macross");
        parkingLot.entrySet().forEach(slot -> System.out.println(slot.getValue().getRegistrationNumber() +" || " +slot.getValue().getColour()));*/

        return HttpServletResponse.SC_CREATED;
    }


}
