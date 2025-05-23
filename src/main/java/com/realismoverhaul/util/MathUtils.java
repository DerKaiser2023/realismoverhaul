package com.realismoverhaul.util;

public class MathUtils {
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}