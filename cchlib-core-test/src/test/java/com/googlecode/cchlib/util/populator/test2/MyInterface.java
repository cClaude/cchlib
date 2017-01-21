package com.googlecode.cchlib.util.populator.test2;

import com.googlecode.cchlib.util.populator.Populator;

public interface MyInterface
{
    @Populator(defaultValue=Const.DEFAULT_SCHEMA_NAME)
    String getSchemaName();

    @Populator
    String getHostname();

    @Populator(defaultValue=Const.DEFAULT_PORT)
    int getPort();

    @Populator(defaultValue=Const.DEFAULT_USERNAME)
    String getUsername();

    @Populator(defaultValue=Const.DEFAULT_PASSWORD)
    String getPassword();
}
