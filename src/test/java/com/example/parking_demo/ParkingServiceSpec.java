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
public class ParkingServiceSpec {

    @Autowired
    ParkingService parkingService;

    @DisplayName("Alocate Parking Slot: [Positive Case]")
    @Test
    void alocateSlotSpec(){
        int slot = 12;
        int result = parkingService.alocateSpace(slot);
        assertThat(result).isEqualTo(HttpServletResponse.SC_CREATED);
    }

    @DisplayName("Alocate Parking Slot: [Negative Case] " +
            "Parking Lot cannot be Initialize Twice")
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

    @DisplayName("Parking Car: [Positive Case] Parking the First Car")
    @Test
    void parkFirstCarSpec(){

        CarData dummyCar = new CarData(1, "KA-01-HH-1234", "white", 0, "");
        int result = parkingService.parkCar(dummyCar);
        assertThat(result).isEqualTo(HttpServletResponse.SC_OK);
    }

    @DisplayName("Parking Car: [Negative Case] Parking Lot isnt Initiated yet.")
    @Test
    void parkCarNoParkingLotFailedSpec(){
        parkingService.resetParkingLot();
        CarData dummyCar = new CarData(1, "KA-01-HH-1234", "white", 0, "");
        int result = parkingService.parkCar(dummyCar);
        assertThat(result).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    //HashMap<Integer, CarData> parkingLot
    @Nested
    @DisplayName("Inquiring Status: [Positive Case]")
    @ExtendWith(ParameterResolverInquireStatusPositiveCase .class)

    public class StatusInquiryPositiveCaseSpec {

        HashMap<Integer, CarData> ParkingLotDummy;

        @Test
        @DisplayName("Parking Lot is not Null")
        public void InquiryResultNotNullSpec(HashMap parkingLot){
            parkingService.setParkingLot(parkingLot);
            HashMap<Integer, CarData> result = parkingService.inquireStatus();
            assertThat(result).isNotEmpty();
        }

        @Test
        @DisplayName("Parking Lot has 6 Cars Inside")
        public void CheckInquiryResultIndex(HashMap parkingLot){
            parkingService.setParkingLot(parkingLot);
            HashMap<Integer, CarData> result = parkingService.inquireStatus();
            assertThat(result.size()).isEqualTo(6);
        }

        @Test
        @DisplayName("Notification For Full Parking Lot")
        public void errorCodeForPullParkingLot(HashMap parkingLot){
            parkingService.setParkingLot(parkingLot);
            CarData dummyCar = new CarData(1, "KA-01-HH-1234", "white", 0, "");
            int result = parkingService.parkCar(dummyCar);
            assertThat(result).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);

            //HashMap<Integer, CarData> result = parkingService.inquireStatus();
            //assertThat(result.size()).isEqualTo(6);
        }

    }

    @Nested
    @DisplayName("Inquiring Registration Number: [Positive Case]")
    public class regNoInquiry{

    }

    @Nested
    @DisplayName("Inquiring Slot Number: [Positive Case]")
    public class slotInquiry{

    }

}
