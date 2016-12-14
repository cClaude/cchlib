package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.apps.duplicatefiles.swing.prefs.PreferencesControler;
import com.googlecode.cchlib.apps.duplicatefiles.swing.tools.Resources;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/**
 * Misc methods needing be most JPanels
 */
public interface AppToolKit extends Serializable
{
    /** prepare {@link JFileChooser} components (workaround for win32) */
    void initJFileChooser();

    JFileChooserInitializer getJFileChooserInitializer( Window parentWindow, FileChooserEntryPoint componentName );

    JFileChooser getJFileChooser( Window parentWindow, FileChooserEntryPoint componentName );

    /** Play error sound */
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

    I18nResourceBundleName getI18nResourceBundleName();
}
