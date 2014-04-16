package com.googlecode.cchlib.sandbox.guice.sample3.playerservice;


public class GoodPlayer implements Player
{
    @Override
    public void bat()
    {
        System.out.println("I can hit any ball");
    }
 
    @Override
    public void bowl()
    {
        System.out.println("I can also bowl");
    }
}
