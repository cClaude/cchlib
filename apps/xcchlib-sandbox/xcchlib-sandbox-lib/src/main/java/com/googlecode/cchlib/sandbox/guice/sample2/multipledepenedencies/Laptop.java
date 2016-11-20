package com.googlecode.cchlib.sandbox.guice.sample2.multipledepenedencies;

public class Laptop
{
    private String model;
    private String price;
 
    public Laptop()
    {
        this.model = "HP 323233232";
        this.price = "$545034";
    }
    @Override
    public String toString()
    {
        return "[Laptop: " + model + "," + price + "]";
    }
}