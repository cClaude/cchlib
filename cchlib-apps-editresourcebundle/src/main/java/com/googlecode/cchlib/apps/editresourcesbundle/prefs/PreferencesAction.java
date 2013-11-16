package com.googlecode.cchlib.apps.editresourcesbundle.prefs;

/* not public */ interface PreferencesAction
{
    void onCancel();
    void onSave( PreferencesCurentSaveParameters saveParams );
}
