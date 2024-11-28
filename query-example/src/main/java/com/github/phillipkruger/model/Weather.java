package com.github.phillipkruger.model;

/**
 * Holds information about the wather
 * @author Phillip Kruger (phillip.kruger@gmail.com)
 */
public record Weather(String description, double currentTemp, double minTemp, double maxTemp) {
    
}
