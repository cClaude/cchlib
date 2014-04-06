package com.googlecode.cchlib.sandbox.guice.sample3.playerservice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
 
public class PlayerClient
{
    public static void main(String[] args) 
    {
        //PlayerModule_FAIL module = new PlayerModule_FAIL(); // Should FAIL !
        PlayerModule_OK module = new PlayerModule_OK(); 
        Injector injector = Guice.createInjector(new Module[]{module});
 
        // Should FAIL if PlayerModule_FAIL 
        @Good Player player = injector.getInstance(Player.class);
        player.bat();
        player.bowl();
    }
}
