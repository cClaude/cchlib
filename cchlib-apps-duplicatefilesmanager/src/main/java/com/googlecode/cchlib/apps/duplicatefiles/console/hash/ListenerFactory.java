package com.googlecode.cchlib.apps.duplicatefiles.console.hash;

public class ListenerFactory
{
    private ListenerFactory()
    {
        // private access
    }

    public static ListenerFactory getBuilder()
    {
        return new ListenerFactory();
    }

    public HashComputeListener createQuietListener()
    {
        return new QuietHashComputeListener();
    }

    public HashComputeListener createVerboseListener()
    {
        return new VerboseHashComputeListener();
    }

    public HashComputeListener createDefaultListener()
    {
        return new DefaultHashComputeListener();
    }
}
