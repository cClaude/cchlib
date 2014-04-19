package com.googlecode.cchlib.apps.emptydirectories;

import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;
import java.awt.EventQueue;

public class DebugApp extends DebugFrame
{
    private static final long serialVersionUID = 1L;

    //private AppToolKit dfToolKit = new FakeAppToolKit();
    private final RemoveEmptyDirectoriesPanel jPanel_RemoveEmptyDirectories;

    public DebugApp()
    {
        this.jPanel_RemoveEmptyDirectories = new RemoveEmptyDirectoriesPanel( this );

        addToContentPane( jPanel_RemoveEmptyDirectories );
    }

    /**
     * Launch the application.
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater( () -> {
            try {
                DebugApp frame = new DebugApp();
                
                frame.setVisible( true );
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        });
    }

}
