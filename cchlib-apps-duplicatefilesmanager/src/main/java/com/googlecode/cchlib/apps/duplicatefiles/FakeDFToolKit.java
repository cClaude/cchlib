package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.swing.JFileChooser;
import com.googlecode.cchlib.apps.duplicatefiles.prefs.Preferences;
import com.googlecode.cchlib.swing.filechooser.JFileChooserInitializer;

/** 
 * Fake class for tests
 */
public class FakeDFToolKit implements DFToolKit
{
    private static final long serialVersionUID = 1L;
    private JFileChooserInitializer jFileChooserInitializer;

    public FakeDFToolKit()
    {
    }

    @Override
    public void initJFileChooser()
    {
        Window win = getMainFrame();
        getJFileChooserInitializer( win , win );
    }

    @Override
    public JFileChooserInitializer getJFileChooserInitializer( 
        final Window parentWindow, 
        final Component refComponent
        )
    {
        if( jFileChooserInitializer == null ) {
            jFileChooserInitializer = new JFileChooserInitializer();
            }
        return jFileChooserInitializer;
    }

    @Override
    public JFileChooser getJFileChooser(         
        final Window parentWindow, 
        final Component refComponent
        )
    {
        return getJFileChooserInitializer( parentWindow, refComponent ).getJFileChooser();
    }

//    @Deprecated
//    @Override
//    public JFileChooser getJFileChooser()
//    {
//        return getJFileChooser( null, null );
//    }

    @Override
    public void beep()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void openDesktop( File file )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public Locale getValidLocale()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void sleep( long ms )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public Preferences getPreferences()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Frame getMainFrame()
    {
        // TODO Auto-generated method stub
        return null;
    }

//    @Override
//    public Window getMainWindow()
//    {
//        return null;
//    }

    @Override
    public void setEnabledJButtonCancel( boolean b )
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isEnabledJButtonCancel()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void initComponentsJPanelConfirm()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public String getMessagesBundle()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resources getResources()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<File> getRootDirectoriesList()
    {
        return Collections.emptyList();
    }
}
