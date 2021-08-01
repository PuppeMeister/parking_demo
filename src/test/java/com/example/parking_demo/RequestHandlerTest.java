package com.example.parking_demo;

import com.example.parking_demo.data.CarData;
import com.example.parking_demo.data.ParkingData;
import com.example.parking_demo.service.ParkingService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.parking_demo.controller.RequestHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DisplayName("Request Handler Test")
//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestHandlerTest {

    MockMvc mockMvc;

    @InjectMocks
    RequestHandler requestHandler;

    @MockBean
    ParkingService parkingService;

    @BeforeAll
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(requestHandler).build();
    }


    @Test
    @DisplayName("It should able to allocate / initialize the parking lot.")
    void allocateParkingLot() throws Exception {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("slot", "6");

        when(parkingService.alocateSpace(6)).thenReturn(201);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parking/alocatingspace").contentType("application/json").content(jsonObject.toString()))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("It should able to park a car.")
    void parkACar() throws Exception {

       when(parkingService.parkCar(isA(CarData.class))).thenReturn(200);
       JsonObject jsonObject = new JsonObject();
       jsonObject.addProperty("registrationNumber", "KA-01-HH-1234");
       jsonObject.addProperty("colour", "White");

       mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parking/car").contentType("application/json").
                        content(jsonObject.toString()).characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("It should process request for a car to leave the parking lot.")
    void leaveAcar() throws Exception {

        when(parkingService.leaveCar(1)).thenReturn(200);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("slotNumber", "1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parking/leave").contentType("application/json").content(jsonObject.toString()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("It should reject a request of a car to park because the parking lot is full.")
    void fullNotification() throws Exception {

        //CarData  data = new CarData(0, "KA-01-HH-1234", "White", 0, "");
        when(parkingService.parkCar(isA(CarData.class))).thenReturn(400);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("registrationNumber", "KA-01-HH-1234");
        jsonObject.addProperty("colour", "White");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parking/car").contentType("application/json").content(jsonObject.toString()))
                //.andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("Status Inquiry")
    void inquiryStatus() throws Exception {

        HashMap<Integer, CarData> mockResult = new HashMap<>();
        CarData  data = new CarData(1, "KA-01-HH-1234", "White", 0, "");
        mockResult.put(0, data);
        when(parkingService.inquireStatus()).thenReturn(mockResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/status")).andDo(print())
                    .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("It should fail because parking lot isn't uninitialized yet.")
    void failedInquiryStatus() throws Exception {

        HashMap<Integer, CarData> mockResult = new HashMap<>();
        when(parkingService.inquireStatus()).thenReturn(mockResult);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/status")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("It should be able to give the registration number info based on colour")
    void inquiryRegNoBasedOnColour() throws Exception
    {
        ArrayList<String> result = new ArrayList<>();
        result.add("KA-01-HH-1234");
        when(parkingService.inquryRegNoBasedOnColour("White")).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/inquiry/reg?colour=White")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("It should fail the registration number inquiry based on colour because data not found or the parking lot uninitialzed.")
    void failedInquiryRegNoBasedOnColour() throws Exception {

        ArrayList<String> result = new ArrayList<>();
        when(parkingService.inquryRegNoBasedOnColour("White")).thenReturn(result);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/inquiry/reg?colour=White")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("It should be able to give the slot number info based on registration number.")
    void inquirySlotNumberBasedOnRegNo() throws Exception
    {
        ArrayList<String> result = new ArrayList<>();
        result.add("8");
        when(parkingService.inqurySlotNoBasedOnRegNo("KA-01-HH-123")).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/inquiry/slot2?regNo=KA-01-HH-123")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("It should fail the slot number inquiry based on reg no because data not found or the parking lot uninitialzed.")
    void failedInquirySlotNumberBasedOnRegNo() throws Exception
    {
        ArrayList<String> result = new ArrayList<>();
        when(parkingService.inqurySlotNoBasedOnRegNo("KA-01-HH-123")).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/inquiry/slot2?regNo=KA-01-HH-123")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("It should be able to give the slot number info based on colour.")
    void inquirySlotNumberBasedOnColour() throws Exception
    {
        ArrayList<String> result = new ArrayList<>();
        result.add("8");
        when(parkingService.inqurySlotNoBasedOnColour("White")).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/inquiry/slot1?colour=White")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("It should fail the slot number inquiry based on colour because data not found or the parking lot uninitialzed.")
    void failedInquirySlotNumberBasedOnColour() throws Exception
    {
        ArrayList<String> result = new ArrayList<>();
        when(parkingService.inqurySlotNoBasedOnColour("White")).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parking/inquiry/slot1?colour=White")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
