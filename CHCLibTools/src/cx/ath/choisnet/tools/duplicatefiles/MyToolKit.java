package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import javax.swing.JFileChooser;

public interface MyToolKit 
{
    public JFileChooser getJFileChooser();
    public void beep();
    public void openDesktop( File file );
}
