# Parking Lot Demo

## Project Aim 
**This is a microservice model for handling parking lot problem.
The model retrieves input and emits output through API.**

**This is built with :**

1. Java 8
2. Spring Boot
3. jUnit5

## Features ##

### 1. Creating / Alocating Parking Space ### 

 - **Endpoint**

    ```console
     POST : http://localhost:19996/api/v1/parking/alocatingspace
    ```

- **Request Body**

    ```console 
     {
     "number": "" // Required
     }
    ```
     
- **Expecting Positive Outcome**

### 2. Park a Car ### 

- **Endpoint**

    ```console
    POST : http://localhost:19996/api/v1/parking/car
    ```

- **Request Body**

    ```console 
     {
         "registrationNumber" : "KA-01-HH-1234",
         "colour" : "white"
     }
    ```
  
- **Expecting Positive Outcome**

### 3. Free parking slot (Car Leaving) ### 


- ***Endpoint**

    ```console
     POST : http://localhost:19996//api/v1/parking/leave
    ```
- **Request Body**

    ```console 
     {
         "slotNumber" : "1"
     }
    ```

### 4. Inquire Parking Lot Status ###

-**Endpoint**

    ```console
     GET : http://localhost:19996/api/v1/parking/status
    ```


### 5. Parking Lot Full Notification ### 


### 6. Inquire Registration Number based on Colour ### 


### 7. Inquire Parking Slot Number based on Colour ### 

### 8. Inquire Parking Slot Number  based on Registration Number ### 

## Unit Testing ##
