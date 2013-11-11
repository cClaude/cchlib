package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

/* not public */ interface PreferencesAction
{
    public void onCancel();
    public void onSave( PreferencesCurentSaveParameters saveParams );
}
