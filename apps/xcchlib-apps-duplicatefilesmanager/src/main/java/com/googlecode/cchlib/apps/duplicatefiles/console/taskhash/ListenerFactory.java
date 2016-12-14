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

    public FileComputeTaskListener createQuietListener()
    {
        return new QuietFileComputeListener();
    }

    public FileComputeTaskListener createVerboseListener()
    {
        return new VerboseFileComputeListener();
    }

    public FileComputeTaskListener createDefaultListener()
    {
        return new DefaultFileComputeListener();
    }
}
