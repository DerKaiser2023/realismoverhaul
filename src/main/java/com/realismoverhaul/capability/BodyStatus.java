package com.realismoverhaul.capability;

import java.util.HashMap;
import java.util.Map;

public class BodyStatus implements IBodyStatus {
    private final Map<String, Float> bodyParts = new HashMap<>();

    // Temperature system
    private float bodyTemperature = 37.0f; // Celsius, normal
    private boolean isFreezing = false;

    // Needs system
    private float hunger = 20.0f; // max = 20
    private float thirst = 20.0f; // max = 20
    private int sleepTicks = 0;   // counts time awake
    private boolean isSick = false;
    private String diseaseName = "none";

    public BodyStatus() {
        bodyParts.put("head", 100F);
        bodyParts.put("torso", 100F);
        bodyParts.put("left_arm", 100F);
        bodyParts.put("right_arm", 100F);
        bodyParts.put("left_leg", 100F);
        bodyParts.put("right_leg", 100F);
    }

    // Body part health
    @Override
    public float getHealth(String part) {
        return bodyParts.getOrDefault(part, 0F);
    }

    @Override
    public void setHealth(String part, float value) {
        bodyParts.put(part, value);
    }

    // Temperature
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

    // Hunger
    public float getHunger() {
        return hunger;
    }

    public void setHunger(float hunger) {
        this.hunger = Math.max(0F, Math.min(hunger, 20F));
    }

    // Thirst
    public float getThirst() {
        return thirst;
    }

    public void setThirst(float thirst) {
        this.thirst = Math.max(0F, Math.min(thirst, 20F));
    }

    // Sleep/Insomnia
    public int getSleepTicks() {
        return sleepTicks;
    }

    public void addSleepTicks(int ticks) {
        this.sleepTicks += ticks;
    }

    public void resetSleepTicks() {
        this.sleepTicks = 0;
    }

    // Disease
    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        this.isSick = sick;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
}