package com.example.w22comp1011w2;

import java.util.Arrays;
import java.util.List;

/**
 * This is our model class. It stores the information we would typically track about a camera
 */

public class Camera {
    private int resolution;
    private String make, model;
    private boolean slr;
    private double price;

    public Camera(String make, String model, int res, boolean slr, double price) {
        setResolution(res);
        setMake(make);
        setModel(model);
        setSlr(slr);
        setPrice(price);
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        if (resolution >= 2 && resolution <= 100)
            this.resolution = resolution;
        else
            throw new IllegalArgumentException("resolution must be in range 2-100");

    }

    public String getMake() {
        return make;
    }

    /**
     * This method will validate that the manufacturer/make is either Canon, nikon, sony or fUJIfILM
     * @param make - the manufacturer of the camera
     */

    public void setMake(String make) {
        List<String> validMakes = Arrays.asList("Canon", "Nikon", "Sony", "Fujifilm");
        if (validMakes.contains(make))
            this.make = make;
        else
            throw new IllegalArgumentException("Make must be in the list of: " + validMakes);
    }

    public String getModel() {
        return model;
    }

    /**
     * Valid models are Canon, Nikon or Sony
     *
     * @param model
     */
    public void setModel(String model) {
        if (!model.isBlank())
            this.model = model;
        else
            throw new IllegalArgumentException("Model cant be blank");
    }

    public boolean isSlr() {
        return slr;
    }

    public void setSlr(boolean slr) {
        this.slr = slr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 10 && price <= 5000)
            this.price = price;
        else
            throw new IllegalArgumentException("Price must be in the range 10-5000");
    }
}