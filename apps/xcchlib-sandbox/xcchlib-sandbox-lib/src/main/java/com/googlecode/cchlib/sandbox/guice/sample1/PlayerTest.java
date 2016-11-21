package com.googlecode.cchlib.sandbox.guice.sample1;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PlayerTest 
{
    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector();
        Player player = injector.getInstance(Player.class);
        player.name = "David Boon";
        System.out.println(player);
    }
}
