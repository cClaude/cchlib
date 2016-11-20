package com.googlecode.cchlib.apps.duplicatefiles.console.taskhash;

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

    public HashComputeTaskListener createQuietListener()
    {
        return new QuietHashComputeListener();
    }

    public HashComputeTaskListener createVerboseListener()
    {
        return new VerboseHashComputeListener();
    }

    public HashComputeTaskListener createDefaultListener()
    {
        return new DefaultHashComputeListener();
    }
}
