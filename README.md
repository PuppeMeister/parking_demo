# Parking Lot Demo

## Project Aim 
**This is a microservice model for handling parking lot problem.
The model retrieves input and emits output through API.**

**This is built with :**

***Java 8***

***Spring Boot***

***jUnit5***

## Features ##

### Creating / Alocating Parking Space ### 

***input***

```console
http://localhost:3000/api/v1/parking/alocatingspace
```

***body***


```console 
{
"number": "" // Required
}
```

***Expecting Positive Outcome***
