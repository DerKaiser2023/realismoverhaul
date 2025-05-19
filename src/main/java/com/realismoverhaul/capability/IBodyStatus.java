package com.realismoverhaul.capability;
public interface IBodyStatus {
    float getHealth(String part);
    void setHealth(String part, float value);

    // Temperature system
    float getBodyTemperature();
    void setBodyTemprature(float temp);

    boolean isFreezing();
    void setFreezing(boolean freezing);
}