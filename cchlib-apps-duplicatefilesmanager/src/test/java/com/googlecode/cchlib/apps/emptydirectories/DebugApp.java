package com.googlecode.cchlib.apps.emptydirectories;

import java.awt.EventQueue;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.FakeDFToolKit;
import com.googlecode.cchlib.apps.emptydirectories.gui.RemoveEmptyDirectoriesPanel;

public class DebugApp extends DebugFrame
{
    private static final long serialVersionUID = 1L;

    private DFToolKit dfToolKit = new FakeDFToolKit();
    private RemoveEmptyDirectoriesPanel jPanel_RemoveEmptyDirectories;

    public DebugApp()
    {
        this.jPanel_RemoveEmptyDirectories = new RemoveEmptyDirectoriesPanel( dfToolKit, this );

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
