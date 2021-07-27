package com.example.parking_demo.data;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.ArrayList;

public interface ParkingDao {

   int alocateSpace(int slot);
   int parkCar(CarData car);
   void resetParkingLot();
   HashMap<Integer, CarData> getParkingLot();
   void setParkingLot(HashMap parkingLot);
   int leaveCar(int slotNumber);
   HashMap<Integer, CarData> inquireStatus();
   ArrayList<String> inquryRegNoBasedOnColour(String colour);
   ArrayList<String>inqurySlotNoBasedOnColour(String colour);
   ArrayList<String>inqurySlotNoBasedOnRegNo(String regNo);
   int insertBatchData(String batchData);
}
