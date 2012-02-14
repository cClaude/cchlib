package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.Serializable;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;

/**
 * Misc tools for this project
 */
public interface DFToolKit extends Serializable
{
    public JFileChooser getJFileChooser();
    public void beep();
    public void openDesktop( File file );
    public Locale getValidLocale();
//    public Locale getLocale();
//    public void setLocale( Locale locale );
//    public ConfigMode getConfigMode();
    public void sleep(long ms);
    public Image getImage(String name);
    public Icon getIcon(String name);
    public Preferences getPreferences();
    public Window getMainWindow();
    public void setEnabledJButtonCancel( boolean b );
    public boolean isEnabledJButtonCancel();
    public void initComponentsJPanelConfirm();
}
