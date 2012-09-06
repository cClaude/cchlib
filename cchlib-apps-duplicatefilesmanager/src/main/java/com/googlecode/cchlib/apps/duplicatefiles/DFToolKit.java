package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Window;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/**
 * Misc methods needing be most JPanels
 */
public interface DFToolKit extends Serializable
{
    public JFileChooserInitializer getJFileChooserInitializer( Window parentWindow );
    public JFileChooser getJFileChooser( Window parentWindow );
    public JFileChooser getJFileChooser();
    public void beep();
    public void openDesktop( File file );
    public Locale getValidLocale();
    public void sleep(long ms);
    //public Image getImage(String name);
    //public Icon getIcon(String name);
    public Preferences getPreferences();
    public Window getMainWindow();
    public void setEnabledJButtonCancel( boolean b );
    public boolean isEnabledJButtonCancel();
    public void initComponentsJPanelConfirm();
    public String getMessagesBundle();
    public Resources getResources();
    public List<File> getRootDirectoriesList();
}
