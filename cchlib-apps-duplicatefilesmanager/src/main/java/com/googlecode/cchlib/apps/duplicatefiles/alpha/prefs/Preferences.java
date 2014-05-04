package com.googlecode.cchlib.apps.duplicatefiles.alpha.prefs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import com.googlecode.cchlib.apps.duplicatefiles.ConfigMode;

public interface Preferences extends Serializable
{
    /**
     * Returns {@link ConfigMode} according to user level
     * @return {@link ConfigMode} according to user level
     */
    ConfigMode getUserLevel();

    /**
     * Returns expected {@link Locale} for display
     * @return expected {@link Locale} for display
     */
    Locale getLocale();

    /**
     * Returns expected look and feel for display
     * @return expected look and feel for display
     */
    String getLookAndFeelName();

    /**
     * Returns a Collection of RootFile
     * @return a Collection of RootFile
     */
    Collection<RootFile> getCompareRootFileCollection();
}
