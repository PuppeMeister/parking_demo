package com.example.parking_demo.data;

import javax.servlet.http.HttpServletResponse;

public interface ParkingDao {

   int alocateSpace(int slot);
   void parkCar(CarData car);
}