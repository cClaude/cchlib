package com.googlecode.cchlib.sandbox.guice.sample3.playerservice;

public class BadPlayer implements Player
{
    @Override
    public void bat() 
    {
        System.out.println("I think i can face the ball");
    }
 
    @Override
    public void bowl()
    {
        System.out.println("I dont know bowling");
    }
}
