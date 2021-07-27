package com.example.parking_demo.test.data;

import com.example.parking_demo.data.CarData;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.HashMap;

public class ParameterResolverInquireStatusPositiveCase implements ParameterResolver{
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals( parameter.getParameterizedType().getTypeName(), "java.util.HashMap");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        HashMap<Integer, CarData> parkingLot = new HashMap<>();
        parkingLot.put(1, new CarData(1,"KA-01-HH-1234", "White", 0, ""));
        parkingLot.put(2, new CarData(2,"KA-01-HH-9999", "White", 0, ""));
        parkingLot.put(3, new CarData(3,"KA-01-HH-0001", "Black", 0, ""));
        parkingLot.put(4, new CarData(4,"KA-01-HH-7777", "Red", 0, ""));
        parkingLot.put(5, new CarData(5,"KA-01-HH-2701", "Blue", 0, ""));
        parkingLot.put(6, new CarData(6,"KA-01-HH-3141", "Black", 0, ""));
        return parkingLot;
    }

}
