package com.googlecode.cchlib.sandbox.guice.sample1;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class PlayerTestApp
{
    private PlayerTestApp()
    {
        // App
    }

    @SuppressWarnings("squid:S106") // CLI App
    public static void main( final String[] args )
    {
        final Injector injector = Guice.createInjector();
        final Player   player   = injector.getInstance( Player.class );

        player.setName( "David Boon" );
        System.out.println( player );
    }
}
