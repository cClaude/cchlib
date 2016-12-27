package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LF5Appender;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.DialogHelper;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;

/**
 * Build test folder
 */
public class BuildAppTestApp extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( BuildAppTestApp.class );
    private static final int DIFF_FILES_COUNT = 50;
    private static final int DUPLICATE_FILES_COUNT = 3;

    private final JPanel contentPane;
    private LimitedIntegerJTextField diffFilesCount;
    private LimitedIntegerJTextField duplicateFilesCount;

    /**
     * Create the frame.
     */
    public BuildAppTestApp()
    {
        final LF5Appender swingLogger = new LF5Appender();

        LOGGER.addAppender( swingLogger );

        setTitle("BuildAppTest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 345, 132);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.contentPane);
        final GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        this.contentPane.setLayout(gbl_contentPane);

        {
            this.diffFilesCount = new LimitedIntegerJTextField();
            this.diffFilesCount.setMaximum( 999999 );
            this.diffFilesCount.setValue( DIFF_FILES_COUNT );
            //diffFilesCount.setText("DIFF_FILES_COUNT");
            final GridBagConstraints gbc_diffFilesCount = new GridBagConstraints();
            gbc_diffFilesCount.gridwidth = 2;
            gbc_diffFilesCount.insets = new Insets(0, 0, 5, 5);
            gbc_diffFilesCount.fill = GridBagConstraints.HORIZONTAL;
            gbc_diffFilesCount.gridx = 1;
            gbc_diffFilesCount.gridy = 0;
            this.contentPane.add(this.diffFilesCount, gbc_diffFilesCount);
            this.diffFilesCount.setColumns(7);

            final JLabel diffFilesCountLabel = new JLabel("Number of non duplicate files");
            diffFilesCountLabel.setLabelFor( this.diffFilesCount );
            final GridBagConstraints gbc_diffFilesCountLabel = new GridBagConstraints();
            gbc_diffFilesCountLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_diffFilesCountLabel.insets = new Insets(0, 0, 5, 5);
            gbc_diffFilesCountLabel.gridx = 0;
            gbc_diffFilesCountLabel.gridy = 0;
            this.contentPane.add(diffFilesCountLabel, gbc_diffFilesCountLabel);
        }
        {
            this.duplicateFilesCount = new LimitedIntegerJTextField();
            this.duplicateFilesCount.setMaximum( 99999 );
            this.duplicateFilesCount.setValue( DUPLICATE_FILES_COUNT );
            //duplicateFilesCount.setText("DUPLICATE_FILES_COUNT");
            //duplicateFilesCount.setColumns(10);
            final GridBagConstraints gbc_duplicateFilesCount = new GridBagConstraints();
            gbc_duplicateFilesCount.gridwidth = 2;
            gbc_duplicateFilesCount.insets = new Insets(0, 0, 5, 5);
            gbc_duplicateFilesCount.fill = GridBagConstraints.HORIZONTAL;
            gbc_duplicateFilesCount.gridx = 1;
            gbc_duplicateFilesCount.gridy = 1;
            this.contentPane.add(this.duplicateFilesCount, gbc_duplicateFilesCount);

            final JLabel duplicateFilesCountLabel = new JLabel("Number of copy");
            duplicateFilesCountLabel.setLabelFor(this.duplicateFilesCount);
            final GridBagConstraints gbc_duplicateFilesCountLabel = new GridBagConstraints();
            gbc_duplicateFilesCountLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_duplicateFilesCountLabel.insets = new Insets(0, 0, 5, 5);
            gbc_duplicateFilesCountLabel.gridx = 0;
            gbc_duplicateFilesCountLabel.gridy = 1;
            this.contentPane.add(duplicateFilesCountLabel, gbc_duplicateFilesCountLabel);
        }
        {
            final JButton btnBuildFiles = new JButton("Build files");
            btnBuildFiles.addActionListener(e -> Threads.start( () -> doBuildFiles( btnBuildFiles ) ) );
            final GridBagConstraints gbc_btnBuildFiles = new GridBagConstraints();
            gbc_btnBuildFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnBuildFiles.insets = new Insets(0, 0, 0, 5);
            gbc_btnBuildFiles.gridx = 1;
            gbc_btnBuildFiles.gridy = 3;
            this.contentPane.add(btnBuildFiles, gbc_btnBuildFiles);
        }

    }

    private void doBuildFiles( final JButton btnBuildFiles )
    {
        try {
            btnBuildFiles.setEnabled( false );

            test_BuidTst(
                BuildAppTestApp.this.diffFilesCount.getValue(),
                BuildAppTestApp.this.duplicateFilesCount.getValue()
                );
            }
        catch( final IOException e1 ) {
            DialogHelper.showMessageExceptionDialog( "IOException", e1 );
            }
        finally {
            btnBuildFiles.setEnabled( true );
            }
        }

    private void test_BuidTst(
        final int diffFilesCount,
        final int duplicateFilesCount
        ) throws IOException
    {
        final File tstRootFolder = new File(
                    FileHelper.getTmpDirFile(),
                    ".duplicatefiles-tst"
                    );
        //final String[] strings = new String[ DIFF_FILES_COUNT ];
        final String[] strings = new String[ diffFilesCount ];

        LOGGER.info( "BuildAppTest in: " + tstRootFolder );

        {
            final File refDir = new File( tstRootFolder, "REF" );

            refDir.mkdirs();

            for( int i = 0; i<strings.length; i++ ) {
                strings[ i ] = Integer.toHexString( i );

                final File file = new File( refDir, "REF-file-" + i );
                IOHelper.toFile( strings[ i ], file );
                }

            LOGGER.info( "BuildAppTest ref done" );
        }

        final File toDelDir = new File( tstRootFolder, "DEL" );

        //for( int d = 0; d<DUPLICATE_FILES_COUNT; d++ ) {
        for( int d = 0; d< duplicateFilesCount; d++ ) {
            final File tstFolder = new File(
                    toDelDir,
                    "dir-" + d
                    );

            tstFolder.mkdirs();

            for( int i = 0; i<strings.length; i++ ) {
                final File file = new File( tstFolder, "file-" + i );

                IOHelper.toFile( strings[ i ], file );
                }

            LOGGER.info( "BuildAppTest pass " + d + " done" );
           }

        LOGGER.info( "BuildAppTest done" );
    }

    /**
     * Launch the application.
     *
     * @param CLI parameters, ignore
     */
    public static void main( final String[] args )
    {
        EventQueue.invokeLater(( ) -> {
            try {
                final BuildAppTestApp frame = new BuildAppTestApp();
                frame.setVisible(true);
                }
            catch( final Exception e ) {
                e.printStackTrace();
                }
            });
    }
}
