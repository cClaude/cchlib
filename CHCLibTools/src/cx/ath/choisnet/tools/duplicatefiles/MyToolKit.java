package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.util.Locale;
import javax.swing.JFileChooser;

public interface MyToolKit 
{
    public JFileChooser getJFileChooser();
    public void beep();
    public void openDesktop( File file );
    public Locale getLocale();
    public ConfigMode getConfigMode();
    public void sleep(long ms);
}
