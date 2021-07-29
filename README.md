# Parking Lot Demo

## Project Aim 
This is a microservice model for handling parking lot problem. The model retrieves input and emits output through API.

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
     "number": "4" 
     }
    ```
     
- **Expected Result**

   ```console 
    { 
      "status" : "201",
      "message" : "Parking Slot is Allocated."
    }
    ```

- **Expected Result if Parking Space has been Initialized or Occupied**

    ```console 
    { 
      "status" : "403",
      "message" : "Request is Rejected. Parking Slot has been Allocated before."
    }
    ```

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
  
- **Expected Result**
   
    ```console 
    { 
      "status" : "201",
      "message" : "Request is done."
    }
    ```
### 3. Free parking slot (Car Leaving) ### 


- **Endpoint**

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

- **Endpoint**

    ```console
     GET : http://localhost:19996/api/v1/parking/status
    ```

- **Expected Result if the Parking Lot is occupied**

   ```console 
     {
      "":[
          { "Slot Number":"1",
            "Registration Number":"KA-01-HH-1234",
             "Colour":"White"
          },
          { "Slot Number":"2",
            "Registration Number":"KA-01-HH-9999",
            "Colour":"White"
          }
         ]
      }
    ```
 
- **Expected Result if the Parking Lot is Empty**

   ```console 
     {
      "Status" : "400",
      "message" : "Parking Lot isn't initialized yet or empty"
      }
    ```

### 5. Parking Lot Full Notification ### 
- **Endpoint**
  - Same as feature no. 3.
 
- **Expected Result**

   ```console 
     {
      "Status" : "400",
      "message" : "Sorry, There is no Vacant Space."
      }
    ```

### 6. Inquire Registration Number based on Colour ### 

- **Endpoint**
  
  ```console
     GET : http://localhost:19996/api/v1/parking/inquiry/reg?colour=White
    ```
    
- **Expected Result**

   ```console 
     {
        "data" : [
          { 
            "Registration Number":"KA-01-HH-1234",
          },
          {             
             "Registration Number":"KA-01-HH-9999",
          }
        ]
     }
    ```
- **Expected Result if No Matched Car Colour**    
    
    ```console 
      "Status" : "404",
      "message" : "Sorry, There is no Data Matched with Your Request."
      }
     ```

### 7. Inquire Parking Slot Number based on Colour ### 

- **Endpoint**
  
  ```console
     GET : http://localhost:19996/api/v1/parking/inquiry/slot1?colour=White
    ```
    
- **Expected Result**

   ```console 
     {
        "data" : [
          { 
            "Parking Slot":"1",
          },
          {             
             "Parking Slot":"1",
          }
        ]
     }
    ```
- **Expected Result if No Matched Car Colour**    
    
    ```console 
     {
      "Status" : "404",
      "message" : "Sorry, There is no Data Matched with Your Request."
      }
    ```

### 8. Inquire Parking Slot Number  based on Registration Number ### 

- **Endpoint**
  
  ```console
     GET :http://localhost:19996/api/v1/parking/inquiry/slot2?regNo=KA-01-HH-0001
    ```
    
- **Expected Result**

   ```console 
     {
        "data" : [
          { 
            "Parking Slot":"1",
          },
          {             
             "Parking Slot":"1",
          }
        ]
     }
    ```
- **Expected Result if No Matched Car Colour**    
    
    ```console 
    {
      "Status" : "404",
      "message" : "Sorry, There is no Data Matched with Your Request."
      }
   ```

## Unit Testing ##

- The system is able to allocate parking lot based on user input.
- The system is able to refuse the allocation request twice / parking lot cannot be initialize twice.
- The system is able to park a car.
- A car is able to leave parking lot.
- The system is able to reject a request to leave unexisting parking lot.
- The system is unable to park a car because the parking lot has not been initialized yet.
- Test if parking lot has been initialized.
- Test if inside parking lot there are 6 cars.
- Test if parking lot is full and a car unable to park.
- The system is able to provide registration numbers based on car colour.
- The system is unable to provide registration numbers based on car colour / No data found.
- The system is able to provide slot numbers based on car colour.
- The system is unable to provide slot numbers based on car colour / No data found.
- The system is able to provide slot numbers based on registration number.
- The system is unable to provide slot numbers based on registration number / No data found.


## Building Jar with Maven ##

## Running on Docker ##
