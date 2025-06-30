package com.realismoverhaul.capability;

public interface IBodyStatus {
    // Body part health
    float getHealth(String part);
    void setHealth(String part, float value);

    // Temperature system
    float getBodyTemperature();
    void setBodyTemprature(float temp);

    boolean isFreezing();
    void setFreezing(boolean freezing);

    // Hunger
    float getHunger();
    void setHunger(float hunger);

    // Thirst
    float getThirst();
    void setThirst(float thirst);

    // Sleep/Insomnia
    int getSleepTicks();
    void addSleepTicks(int ticks);
    void resetSleepTicks();

    // Disease system
    boolean isSick();
    void setSick(boolean sick);
    String getDiseaseName();
    void setDiseaseName(String name);
}