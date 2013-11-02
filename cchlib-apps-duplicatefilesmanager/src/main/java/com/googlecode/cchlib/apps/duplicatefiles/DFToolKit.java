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
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/**
 * Misc methods needing be most JPanels
 */
public interface DFToolKit extends Serializable
{
    public void initJFileChooser();

    public JFileChooserInitializer getJFileChooserInitializer( Window parentWindow, Component refComponent );
    public JFileChooser getJFileChooser( Window parentWindow, Component refComponent );
    
    public void beep();
    public void openDesktop( File file );
    public Locale getValidLocale();
    public void sleep(long ms);
    public Preferences getPreferences();
    public Frame getMainFrame();
    public void setEnabledJButtonCancel( boolean b );
    public boolean isEnabledJButtonCancel();
    public void initComponentsJPanelConfirm();
    public Resources getResources();
    public List<File> getRootDirectoriesList();

    public Set<AutoI18nConfig> getAutoI18nConfig();
    public I18nResourceBundleName getI18nResourceBundleName();
}
