package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.Serializable;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;

/**
 * Misc methods needing be most JPanels
 */
public interface DFToolKit extends Serializable
{
    public JFileChooser getJFileChooser();
    public void beep();
    public void openDesktop( File file );
    public Locale getValidLocale();
    public void sleep(long ms);
    public Image getImage(String name);
    public Icon getIcon(String name);
    public Preferences getPreferences();
    public Window getMainWindow();
    public void setEnabledJButtonCancel( boolean b );
    public boolean isEnabledJButtonCancel();
    public void initComponentsJPanelConfirm();
}
