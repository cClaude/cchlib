package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.Window;

/* not public */ abstract class AbstractPreferencesAction implements PreferencesAction
{
    private Window window;

    /*package*/ final void setWindow( final Window window )
    {
        this.window = window;
    }

    @Override
    public final void onCancel()
    {
        window.dispose();
    }

    public final void dispose()
    {
        window.dispose();
    }
}
