package com.googlecode.cchlib.sandbox.guice.sample6.named;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ConnectionTest
{
    public static void main(String args[])
    {
        Injector injector = Guice.createInjector(
            new Module() {
                @Override
                public void configure(Binder binder) {
                    binder.bind(MockConnection.class).toProvider( ConnectionProvider.class );
                }
            }
        );

        MockConnection connection = injector.getInstance(MockConnection.class);
        connection.connect();
        connection.disConnect();
    }
}
