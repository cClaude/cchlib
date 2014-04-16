package com.googlecode.cchlib.sandbox.guice.sample3.playerservice;

import com.google.inject.Binder;
import com.google.inject.Module;

public class PlayerModule_FAIL implements Module
{
    @Override
    public void configure(Binder binder)
    {
        binder.bind(Player.class).annotatedWith(Good.class).to( GoodPlayer.class );
        binder.bind(Player.class).annotatedWith(Bad.class).to( BadPlayer.class );
    }
}
