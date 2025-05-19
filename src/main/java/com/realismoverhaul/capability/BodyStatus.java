package com.realismoverhaul.capability

import java.util.HashMap;
import java.util.Map;

public class BodyStatus implements IBodyStatus {
    private final Map<String, Float> bodyParts = new HashMap<>();

    // Temperature system
    private float bodyTemperature = 37.0f; // Celsius, normal temp
    private boolean isFreezing = false;

    public BodyStatus() {
        bodyParts.put("head", 100F);
        bodyParts.put("torso", 100F);
        bodyParts.put("left_arm", 100F);
        bodyParts.put("right_arm", 100F);
        bodyParts.put("left_leg", 100F);
        bodyParts.put("right_leg", 100F); // my dumbass realized i fucked up LMAO
    }

    @Override
    public float getHealth(String part) {
        return bodyParts.getOrDefault(part, 0F);
    }

    @Override
    public void setHealth(String part, float value) {
        bodyParts.put(part, value); // part.value causes a compile error
    }

    // Temperature methods
    @Override
    public float getBodyTemperature() {
        return bodyTemperature;
    }

    @Override
    public void setBodyTemprature(float temp) {
        this.bodyTemperature = temp;
    }

    @Override
    public boolean isFreezing() {
        return isFreezing;
    }

    @Override
    public void setFreezing(boolean freezing) {
        this.isFreezing = freezing;
    }
}