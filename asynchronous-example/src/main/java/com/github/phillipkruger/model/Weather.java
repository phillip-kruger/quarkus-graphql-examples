package com.github.phillipkruger.model;

/**
 * Weather POJO
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class Weather {
    public String description;
    public double current;
    public double max;
    public double min;
    public double windSpeed;
    public double humidity;
    
    public Weather() {
    }

    public Weather(String description, double current, double max, double min, double windSpeed, double humidity) {
        this.description = description;
        this.current = current;
        this.max = max;
        this.min = min;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }
}
