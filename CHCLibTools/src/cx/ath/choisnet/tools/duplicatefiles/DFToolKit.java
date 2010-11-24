package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.Image;
import java.io.File;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.JFileChooser;

public interface DFToolKit 
{
    public JFileChooser getJFileChooser();
    public void beep();
    public void openDesktop( File file );
    public Locale getLocale();
    public ConfigMode getConfigMode();
    public void sleep(long ms);
    public Image getImage(String name);
    public Icon getIcon(String name);
}
