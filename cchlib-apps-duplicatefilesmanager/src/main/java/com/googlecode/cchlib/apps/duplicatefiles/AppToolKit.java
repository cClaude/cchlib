package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.PreferencesControler;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/**
 * Misc methods needing be most JPanels
 */
public interface AppToolKit extends Serializable
{
    void initJFileChooser();

    JFileChooserInitializer getJFileChooserInitializer( Window parentWindow, Component refComponent );
    JFileChooser getJFileChooser( Window parentWindow, Component refComponent );

    void beep();
    void openDesktop( File file );
    Locale getValidLocale();
    void sleep(long ms);
    PreferencesControler getPreferences();
    Frame getMainFrame();
    void setEnabledJButtonCancel( boolean b );
    boolean isEnabledJButtonCancel();
    void initComponentsJPanelConfirm();
    Resources getResources();
    List<File> getRootDirectoriesList();

    Set<AutoI18nConfig> getAutoI18nConfig();
    I18nResourceBundleName getI18nResourceBundleName();
}
