package com.googlecode.cchlib.sandbox.guice.sample6.named;

import com.google.inject.Provider;

public class ConnectionProvider implements Provider<MockConnection>
{
    @Override
    public MockConnection get()
    {
        // Do some customization mechanism here.
        MockConnection connection = new MockConnection();

        // Do some customization mechanism here too.
        return connection;
    }
}
