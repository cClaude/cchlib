package com.googlecode.cchlib.apps.emptydirectories;

import java.awt.EventQueue;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;

public class DebugApp extends DebugFrame
{
    private static final long serialVersionUID = 1L;

    //private AppToolKit dfToolKit = new FakeAppToolKit();
    private RemoveEmptyDirectoriesPanel jPanel_RemoveEmptyDirectories;

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
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                try {
                    DebugApp frame = new DebugApp();

                    frame.setVisible( true );
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

}
