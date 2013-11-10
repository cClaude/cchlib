package com.googlecode.cchlib.apps.editresourcesbundle.prefs;


public interface PreferencesAction
{
    public void onCancel();
    public void onSave( PreferencesCurentSaveParameters saveParams );
}