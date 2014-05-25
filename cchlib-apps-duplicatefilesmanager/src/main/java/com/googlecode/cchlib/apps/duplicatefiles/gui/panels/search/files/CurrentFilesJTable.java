package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.io.File;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;

public class CurrentFilesJTable extends JTable implements CurrentFiles
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( CurrentFilesJTable.class );

    /**
     * @wbp.parser.constructor
     */
    public CurrentFilesJTable()
    {
        super();

        final CurrentFilesTableModel dataModel = new CurrentFilesTableModel2();
        this.setModel( dataModel );


        // table "unselectable" (step 2)
        this.setFocusable( false );
        this.setRowSelectionAllowed( false );

        dataModel.setColumnName( CurrentFile.COLUMN_LABEL, "#" );
        this.setDefaultRenderer( CurrentFile.class, new CurrentFileProgressCellRenderer() );


        // handle size
        this.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

//        System.err.println( "getMaximumSize()=" + getMaximumSize() );
//        System.err.println( "getMinimumSize()=" + getMinimumSize() );
//        System.err.println( "getSize()=" + getSize() );
//        System.err.println( "getPreferredSize()=" + getPreferredSize() );

        final int firstColumnWith = 15; // FIXME compute this size, update according look and feel
        setColumnWith( CurrentFile.COLUMN_LABEL, firstColumnWith, false );
        setColumnWith( CurrentFile.COLUMN_FILE, 4096 - firstColumnWith, true );

//        final Dimension size = new Dimension( 600, getSize().height );
//        setSize( size );
//        super.setMinimumSize( size );
    }

//    @Override
//    public void setMaximumSize( final Dimension maximumSize )
//    {
//        super.setMaximumSize( maximumSize );
//
//        setCellsWith( maximumSize.width );
//    }
//
//    @Override
//    public void setMinimumSize( final Dimension minimumSize )
//    {
//        super.setMinimumSize( minimumSize );
//
//        setCellsWith( minimumSize.width );
//    }
//
//    @Override
//    public void setSize( final Dimension size )
//    {
//        super.setSize( size );
//
//        setCellsWith( size.width );
//    }
//
//    @Override
//    public void setPreferredSize( final Dimension preferredSize )
//    {
//        super.setPreferredSize( preferredSize );
//
//        setCellsWith( preferredSize.width );
//   }
//
//    private void setCellsWith( final int width )
//    {
//        final int firstColumnWith = 15; // FIXME compute this size, update according look and feel
//
//        System.err.println( "getMaximumSize()=" + getMaximumSize() );
//        System.err.println( "getMinimumSize()=" + getMinimumSize() );
//        System.err.println( "getSize()=" + getSize() );
//        System.err.println( "getPreferredSize()=" + getPreferredSize() );
//
//
//        setColumnWith( CurrentFile.COLUMN_LABEL, firstColumnWith, false );
//        setColumnWith( CurrentFile.COLUMN_FILE, width - firstColumnWith, true );
//    }

    private void setColumnWith( final int columnIndex, final int labelWidth, final boolean isResizable )
    {
        final TableColumn column = this.getColumnModel().getColumn( columnIndex );

        //column.setMinWidth( labelWidth  );
        //column.setMaxWidth( labelWidth );
        column.setPreferredWidth( labelWidth );
        column.setResizable( isResizable );
    }

    @Override // CurrentFiles
    public void setCurrentFileLabels( final String label )
    {
        if( SwingUtilities.isEventDispatchThread() ) {
            invokeOutsideEventDispatchThread( () -> setCurrentFileLabels( label ) );

            LOGGER.warn( "setCurrentFileLabels(...) called from EventDispatchThread" );
        } else {
            final CurrentFilesTableModel model = getCurrentFilesTableModel();

            model.setColumnName( CurrentFile.COLUMN_FILE, label );

            LOGGER.info( "model.setColumnName " + CurrentFile.COLUMN_FILE + ", " + label );
        }
    }

    @Override // CurrentFiles
    public void setCurrentFile( final int threadNumber, final File file )
    {
        if( SwingUtilities.isEventDispatchThread() ) {
            invokeOutsideEventDispatchThread( () -> setCurrentFile( threadNumber, file ) );

            LOGGER.warn( "setCurrentFile(...) called from EventDispatchThread" );
        } else {
            this.setValueAt( file, threadNumber, CurrentFile.COLUMN_FILE );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "table.setValueAt " + file + ", " + threadNumber + ", " + CurrentFile.COLUMN_FILE );
            }
        }
    }

    @Override // CurrentFiles
    public void setCurrentFileNewLength( final int threadNumber, final File file, final long lengthToInc )
    {
        if( SwingUtilities.isEventDispatchThread() ) {
            invokeOutsideEventDispatchThread( () -> setCurrentFileNewLength( threadNumber, file, lengthToInc ) );

            LOGGER.warn( "setCurrentFileNewLength(...) called from EventDispatchThread" );
        } else {
            assert  this.getValueAt( threadNumber, CurrentFile.COLUMN_FILE ) != null;
            assert this.getValueAt( threadNumber, CurrentFile.COLUMN_FILE ) instanceof CurrentFile;

            if( getCurrentFileAt( threadNumber ).getFile().equals( file ) ) {
                this.setValueAt( Long.valueOf( lengthToInc ), threadNumber, CurrentFile.COLUMN_FILE );

                if( LOGGER.isTraceEnabled() ) {
                    LOGGER.trace( "table.setValueAt " + Long.valueOf( lengthToInc ) + ", " + threadNumber + ", " + CurrentFile.COLUMN_FILE );
                }
            }
            // else : too late for this file, next file is here.

        }
    }

    private CurrentFile getCurrentFileAt( final int threadNumber )
    {
        return CurrentFile.class.cast( this.getValueAt( threadNumber, CurrentFile.COLUMN_FILE ) );
    }

    @Override // CurrentFiles
    public void setCurrentDirLabel( final String currentDirLabel )
    {
        if( SwingUtilities.isEventDispatchThread() ) {
            invokeOutsideEventDispatchThread( () -> setCurrentDirLabel( currentDirLabel ) );

            LOGGER.warn( "setCurrentDirLabel(...) called from EventDispatchThread" );
        } else {
            getCurrentFilesTableModel().setColumnName( CurrentFile.COLUMN_FILE, currentDirLabel );
        }
    }

    @Override // CurrentFiles
    public void setCurrentDir( final File currentDir )
    {
        if( SwingUtilities.isEventDispatchThread() ) {
            invokeOutsideEventDispatchThread( () -> setCurrentDir( currentDir ) );

            LOGGER.warn( "setCurrentDir(...) called from EventDispatchThread" );
        } else {
            setCurrentFile( 0, currentDir );

        }
    }

    @Override
    public void clear()
    {
        if( SwingUtilities.isEventDispatchThread() ) {
            invokeOutsideEventDispatchThread( () -> clear() );

            LOGGER.warn( "clear() called from EventDispatchThread" );
        } else {
            getCurrentFilesTableModel().clear();
        }
    }

    private void invokeOutsideEventDispatchThread( final Runnable runnable )
    {
        new Thread( runnable ).start();
    }

    private CurrentFilesTableModel getCurrentFilesTableModel()
    {
        return (CurrentFilesTableModel)this.getModel();
    }
}
