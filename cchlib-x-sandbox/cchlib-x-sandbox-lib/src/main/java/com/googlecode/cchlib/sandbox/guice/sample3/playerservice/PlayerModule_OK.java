package com.googlecode.cchlib.sandbox.guice.sample3.playerservice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class PlayerModule_OK implements Module
{
    @Override
    public void configure(Binder binder)
    {
        binder.bind(Player.class).annotatedWith(Names.named("Good")).to( GoodPlayer.class );
        binder.bind(Player.class).annotatedWith(Names.named("Bad")).to( BadPlayer.class );
    }
}
