package com.googlecode.cchlib.swing.batchrunner.ihm;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.JFileChooser;
import org.apache.log4j.Logger;

/***
 * Basic implementation of {@link ActionListener} for {@link BRPanel}
 *
 * @since 4.1.8
 */
// not public
abstract class AbstractBRActionListener  implements ActionListener
{
    private static final Logger LOGGER = Logger.getLogger( AbstractBRActionListener.class );

    private BRPanel panel;

    public void setSBRPanel( final BRPanel panel )
    {
        assert panel != null;

        this.panel = panel;
    }

    protected void setDestinationFolderFile( final File file )
    {
        this.panel.setDestinationFolderFile( file );
    }

    protected Enumeration<File> getSourceFileElements()
    {
        return this.panel.getSourceFileElements();
    }

    protected void setCursor( final Cursor cursor )
    {
        this.panel.setCursor( cursor );
    }

    protected void setCursor( final int cursorType )
    {
        setCursor( Cursor.getPredefinedCursor( cursorType ) );
    }

    protected BRPanelLocaleResources getSBRLocaleResources()
    {
        return this.panel.getSBRLocaleResources();
    }

    protected int getSourceFilesCount()
    {
        return this.panel.getSourceFilesCount();
    }

    protected File getOutputFolderFile()
    {
        return this.panel.getOutputFolderFile();
    }

    protected void setCurrentMessage( final String message )
    {
        this.panel.setCurrentMessage( message );
    }

    protected void addSourceFile( final File file )
    {
        this.panel.addSourceFile( file );
    }

    protected Component getTopLevelAncestor()
    {
        return this.panel.getTopLevelAncestor();
    }

    protected void fireStateChanged( final boolean enable )
    {
        this.panel.fireStateChanged( enable );
    }

    protected void setCurrentTaskNumber( final int value )
    {
        this.panel.setCurrentTaskNumber( value );
    }

    @Override
    public void actionPerformed( final ActionEvent event )
    {
        final String c = event.getActionCommand();

        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "ActionCommand = " + c );
            }

        new Thread( (Runnable)( ) -> {
            switch( c ) {
                case BRPanel.ACTIONCMD_SELECT_SOURCE_FILES :
                    selectSourceFiles();
                    break;

                case BRPanel.ACTIONCMD_DO_ACTION :
                    executeBatch();
                    break;

                case BRPanel.ACTIONCMD_SELECT_DESTINATION_FOLDER:
                    selectDestinationFolder();
                    break;

                default :
                    LOGGER.warn( "Unknown event action command: " + c );
                    LOGGER.warn( "Event : " + event );
                    break;
                }
        },"AbstractSBRActionListener.actionPerformed").start();
    }

    /**
     * Open a {@link JFileChooser} to select destination directory folder
     */
    protected abstract void selectDestinationFolder();

    /**
     * Invoke when execute button is clicked, must execute user batch.
     */
    protected abstract void executeBatch();

    /**
     * Open a {@link JFileChooser} to select sources files
     */
    protected abstract void selectSourceFiles();
}
