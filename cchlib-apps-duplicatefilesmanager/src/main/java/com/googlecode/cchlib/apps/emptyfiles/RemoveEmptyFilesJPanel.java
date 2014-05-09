package com.googlecode.cchlib.apps.emptyfiles;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.File;
import java.nio.file.FileVisitOption;
import java.nio.file.LinkOption;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.AppToolKitService;
import com.googlecode.cchlib.apps.duplicatefiles.Resources;
import com.googlecode.cchlib.apps.emptyfiles.interfaces.FileInfoFormater;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingJPanel;
import com.googlecode.cchlib.apps.emptyfiles.panel.remove.WorkingTableModel;
import com.googlecode.cchlib.apps.emptyfiles.panel.select.SelectDirecoriesJPanel;
import com.googlecode.cchlib.apps.emptyfiles.tasks.FindTask;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

@I18nName("emptyfiles.RemoveEmptyFilesJPanel")
public class RemoveEmptyFilesJPanel extends JPanel implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( RemoveEmptyFilesJPanel.class );

    private final JPanel                mainJPanel;
    private final CardLayout            cardLayout;

    private final SelectDirecoriesJPanel selecDirecoriesJPanel;

    private final WorkingJPanel workingJPanel;
    private WorkingTableModel   tableModel;

    @I18nString private String findFilesFmt;
    @I18nString private String fileLengthFmt;
    @I18nString private String fileAttributsDelete;

    private final AppToolKit dfToolKit;

    /**
     * Create the panel.
     */
    public RemoveEmptyFilesJPanel()
    {
        beSurNonFinal();

        this.dfToolKit = AppToolKitService.getInstance().getAppToolKit();
        init();

        setLayout(new BorderLayout(0, 0));

        this.mainJPanel = new JPanel();
        add(this.mainJPanel);

        this.cardLayout = new CardLayout(0, 0);
        this.mainJPanel.setLayout( this.cardLayout );

        this.selecDirecoriesJPanel = new SelectDirecoriesJPanel( this );
        this.mainJPanel.add( this.selecDirecoriesJPanel, "SelecDirecoriesJPanel" );

        this.workingJPanel = new WorkingJPanel( this, tableModel );
        this.mainJPanel.add( this.workingJPanel, "WorkingJPanel" );
     }

    private void beSurNonFinal()
    {
        this.findFilesFmt = "%d files in list";
        this.fileLengthFmt = "%d bytes";
        this.fileAttributsDelete = "Deleted";
    }

    private void init()
    {
        getDFToolKit().initJFileChooser();

        final FileInfoFormater fileInfoFormater = new FileInfoFormater()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public String formatAttributs( final File file )
            {
                return "TODO"; // TODO formatAttributs
            }

            @Override
            public String formatAttributsDelete()
            {
                return fileAttributsDelete;
            }

            @Override
            public String formatLength( final File file )
            {
                return String.format( fileLengthFmt, Long.valueOf( file.length() ) );
            }
        };
        this.tableModel = new WorkingTableModel( fileInfoFormater );
    }

    public void doFindFiles( final Collection<File> directoryFiles, final JProgressBar progressBar )
    {
        final Set<FileVisitOption> fileVisitOption = EnumSet.noneOf( FileVisitOption.class ); // TODO fileVisitOption
        final int                  maxDepth        = 150; // TODO maxDepth
        final LinkOption           linkOption      = LinkOption.NOFOLLOW_LINKS; // TODO linkOption

        progressBar.setEnabled( true );
        progressBar.setIndeterminate( true );
        progressBar.setStringPainted( true );

        final TableModelListener tableModelListener = (final TableModelEvent e) -> {
            progressBar.setString( String.format( findFilesFmt, Integer.valueOf( tableModel.getRowCount() ) ) );
        };
        this.tableModel.addTableModelListener( tableModelListener  );

        final FindTask findTask = new FindTask(this.tableModel, fileVisitOption, maxDepth, linkOption);

        findTask.start( directoryFiles );

        this.tableModel.removeTableModelListener( tableModelListener  );
        this.tableModel.commit();

        progressBar.setStringPainted( false );
        progressBar.setIndeterminate( false );
        progressBar.setEnabled( false );

        this.cardLayout.next( this.mainJPanel );
    }

    public void restart()
    {
        this.tableModel.clear();
        this.selecDirecoriesJPanel.autoSetEnabled();

        this.cardLayout.previous( this.mainJPanel );
    }

    protected AppToolKit getDFToolKit()
    {
        return dfToolKit;
    }

    public Resources getResources()
    {
        return getDFToolKit().getResources();
    }

    public void doImport( final DefaultListModel<File> directoriesJListModel )
    {
        LOGGER.info( "doImport()" );

        final Runnable importDirectories = () -> {
            final List<File> dirs = getDFToolKit().getRootDirectoriesList();

            for( final File file : dirs ) {
                directoriesJListModel.addElement( file );
            }

            LOGGER.info( "doImport() done" );
        };
        new Thread( importDirectories, "doImport():importDirectories" ).start();
    }

    public JFileChooser getJFileChooser()
    {
        return getDFToolKit().getJFileChooser( dfToolKit.getMainFrame(), this );
    }

    @Override
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );

        autoI18n.performeI18n( this.selecDirecoriesJPanel, SelectDirecoriesJPanel.class );
        autoI18n.performeI18n( this.workingJPanel, WorkingJPanel.class );
        autoI18n.performeI18n( this.tableModel, WorkingTableModel.class );
    }
}
