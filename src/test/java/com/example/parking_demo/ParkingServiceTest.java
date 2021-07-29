package com.example.parking_demo;

import com.example.parking_demo.data.CarData;
import com.example.parking_demo.test.data.ParameterResolverInquireStatusPositiveCase ;
import com.example.parking_demo.data.ParkingData;
import com.example.parking_demo.service.ParkingService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@DisplayName("Parking Service")
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@ExtendWith(ParameterResolverParkingDataSpec.class) //Calling or tie this class with Parameter Resolver (Mock Data)
public class ParkingServiceTest {

    @Autowired
    ParkingService parkingService;

    @DisplayName("Test if the system is able to allocate parking lot based on user input. ")
    @Test
    void alocateSlotSpec(){
        int slot = 12;
        int result = parkingService.alocateSpace(slot);
        assertThat(result).isEqualTo(HttpServletResponse.SC_CREATED);
    }

    @DisplayName("Test if the system is able to refuse the allocation request twice / " +
            "parking lot cannot be initialize twice.")
    @Test
    void alocatedSlotTwiceFailedSpec(){
        int slot = 47;
        int result = parkingService.alocateSpace(slot);
        assertThat(result).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
    }

    /*@DisplayName("Alocate Parking Slot:[Negative Case] Input is not Integer but String")
    @Test
    void alocatedSlotStringFailedSpec(){
        int slot = 47;
        int result = parkingService.alocateSpace(slot);
        assertThat(result).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
    }*/

    @DisplayName("Test if the system is able to park a car.")
    @Test
    void parkFirstCarSpec(){

        CarData dummyCar = new CarData(1, "KA-01-HH-1234", "white", 0, "");
        int result = parkingService.parkCar(dummyCar);
        assertThat(result).isEqualTo(HttpServletResponse.SC_OK);
    }

    @DisplayName("Test if the system is unable to park a car because the parking lot has not been initialized yet.")
    @Test
    void parkCarNoParkingLotFailedSpec(){
        parkingService.resetParkingLot();
        CarData dummyCar = new CarData(1, "KA-01-HH-1234", "white", 0, "");
        int result = parkingService.parkCar(dummyCar);
        assertThat(result).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    //HashMap<Integer, CarData> parkingLot
    @Nested
    //@DisplayName("Inquiring Status: [Positive Case]")
    @ExtendWith(ParameterResolverInquireStatusPositiveCase .class)

    public class StatusInquiryPositiveCaseSpec {

        HashMap<Integer, CarData> ParkingLotDummy;

        @Test
        @DisplayName("Test if parking lot has been initialized.")
        public void InquiryResultNotNullSpec(HashMap parkingLot){
            parkingService.setParkingLot(parkingLot);
            HashMap<Integer, CarData> result = parkingService.inquireStatus();
            assertThat(result).isNotEmpty();
        }

        @Test
        @DisplayName("Test if inside parking lot there are 6 cars.")
        public void CheckInquiryResultIndex(HashMap parkingLot){
        //public void CheckInquiryResultIndex(){
            parkingService.setParkingLot(parkingLot);
            HashMap<Integer, CarData> result = parkingService.inquireStatus();
            assertThat(result.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Test if parking lot is full and a car unable to park.")
        //public void errorCodeForPullParkingLot(HashMap parkingLot){
        public void errorCodeForPullParkingLot(){
            //parkingService.setParkingLot(parkingLot);
            CarData dummyCar = new CarData(1, "KA-01-HH-1234", "white", 0, "");
            int result = parkingService.parkCar(dummyCar);
            assertThat(result).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
            //HashMap<Integer, CarData> result = parkingService.inquireStatus();
            //assertThat(result.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Test if the system is able to provide registration numbers based on car colour.")
        //public void regNoInquryBasedOnColour(HashMap parkingLot){
        public void regNoInquryBasedOnColour(){
            //parkingService.setParkingLot(parkingLot);
            ArrayList<String> result = parkingService.inquryRegNoBasedOnColour("Black");
            assertThat(result).isNotEmpty();
        }

        @Test
        @DisplayName("Test if the system is unable to provide registration numbers based on car colour / No data found.")
        public void failedToRetrieveRegNoBasedOnColour(){
            parkingService.resetParkingLot();
            ArrayList<String> result = parkingService.inquryRegNoBasedOnColour("Black");
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Test if the system is able to provide slot numbers based on car colour.")
        public void slotNoInquryBasedOnColour(HashMap parkingLot){
            parkingService.setParkingLot(parkingLot);
            ArrayList<String> result = parkingService.inqurySlotNoBasedOnColour("Black");
            assertThat(result).isNotEmpty();
        }

        @Test
        @DisplayName("Test if the system is unable to provide slot numbers based on car colour / No data found.")
        public void failedToRetrieveSlotNoBasedOnColour(HashMap parkingLot){
            parkingService.resetParkingLot();
            ArrayList<String> result = parkingService.inqurySlotNoBasedOnColour("Black");
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("Test if the system is able to provide slot numbers based on registration number.")
        public void slotNoInquryBasedOnRegNo(HashMap parkingLot){
            parkingService.setParkingLot(parkingLot);
            ArrayList<String> result = parkingService.inqurySlotNoBasedOnRegNo("KA-01-HH-7777");
            assertThat(result).isNotEmpty();
        }

        @Test
        @DisplayName("Test if the system is unable to provide slot numbers based on registration number / No data found.")
        public void failedToRetrieveSlotNoInquryBasedOnRegNo(HashMap parkingLot){
            parkingService.resetParkingLot();
            ArrayList<String> result = parkingService.inqurySlotNoBasedOnRegNo("KA-01-HH-7777");
            assertThat(result).isEmpty();
        }

    }

    /*
    @Nested
    @DisplayName("Inquiring Registration Number: [Positive Case]")
    public class regNoInquiry{

    }

    @Nested
    @DisplayName("Inquiring Slot Number: [Positive Case]")
    public class slotInquiry{

    }*/

}
