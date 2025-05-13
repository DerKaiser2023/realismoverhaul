package com.realisticdamage.realisticdamage.capability

import java.util.HashMap;
import java.util.Map;

public class BodyStatus implements IBodyStatus {
    private final Map<String, Float> bodyParts = new HashMap<>();

    public BodyStatus() {
        bodyParts.put("head", 100F);
        bodyParts.put("torso", 100F);
        bodyParts.put("left_arm", 100F);
        bodyParts.put("right_arm", 100F);
        bodyParts.put("left_leg", 100F);
        bodyParts.put("left_leg", 100F);
    }

    @Override
    public float getHealth(String part) {
        return bodyParts.getOrDefault(part, 0F);
    }

    @Override
    public void setHealth(String part, float value) {
        bodyParts.put(part.value);
    }
}