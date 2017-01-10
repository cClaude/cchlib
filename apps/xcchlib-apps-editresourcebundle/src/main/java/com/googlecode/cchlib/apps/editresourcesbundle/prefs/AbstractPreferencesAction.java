package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

import java.awt.Window;

//Not public
abstract class AbstractPreferencesAction implements PreferencesAction
{
    private Window window;

    /*package*/ final void setWindow( final Window window )
    {
        this.window = window;
    }

    @Override
    public final void onCancel()
    {
        this.window.dispose();
    }

    public final void dispose()
    {
        this.window.dispose();
    }
}
