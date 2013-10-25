package com.googlecode.cchlib.sandbox.guice.sample2.multipledepenedencies;


public class Mobile 
{
    private String number;
 
    public Mobile()
    {
        this.number = "988438434";
    }
 
    @Override
    public String toString()
    {
        return "[Mobile: " + number + "]"; 
    }
}