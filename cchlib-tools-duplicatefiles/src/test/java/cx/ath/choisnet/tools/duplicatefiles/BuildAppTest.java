package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LF5Appender;

import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.swing.DialogHelper;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import cx.ath.choisnet.swing.LimitedIntegerJTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Build test folder
 */
public class BuildAppTest extends JFrame
{
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( BuildAppTest.class );
    private final int DIFF_FILES_COUNT = 50;
    private final int DUPLICATE_FILES_COUNT = 3;
    private JPanel contentPane;
    private LimitedIntegerJTextField diffFilesCount;
    private LimitedIntegerJTextField duplicateFilesCount;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BuildAppTest frame = new BuildAppTest();
                    frame.setVisible(true);
                    }
                catch( Exception e ) {
                    e.printStackTrace();
                    }
                }
            });
    }

    /**
     * Create the frame.
     */
    public BuildAppTest()
    {
        LF5Appender swingLogger = new LF5Appender();

        logger.addAppender( swingLogger );

        setTitle("BuildAppTest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 345, 132);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        {
            diffFilesCount = new LimitedIntegerJTextField();
            diffFilesCount.setMaxValue(999999);
            diffFilesCount.setValue( DIFF_FILES_COUNT );
            //diffFilesCount.setText("DIFF_FILES_COUNT");
            GridBagConstraints gbc_diffFilesCount = new GridBagConstraints();
            gbc_diffFilesCount.gridwidth = 2;
            gbc_diffFilesCount.insets = new Insets(0, 0, 5, 5);
            gbc_diffFilesCount.fill = GridBagConstraints.HORIZONTAL;
            gbc_diffFilesCount.gridx = 1;
            gbc_diffFilesCount.gridy = 0;
            contentPane.add(diffFilesCount, gbc_diffFilesCount);
            diffFilesCount.setColumns(7);

            JLabel diffFilesCountLabel = new JLabel("Number of non duplicate files");
            diffFilesCountLabel.setLabelFor( diffFilesCount );
            GridBagConstraints gbc_diffFilesCountLabel = new GridBagConstraints();
            gbc_diffFilesCountLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_diffFilesCountLabel.insets = new Insets(0, 0, 5, 5);
            gbc_diffFilesCountLabel.gridx = 0;
            gbc_diffFilesCountLabel.gridy = 0;
            contentPane.add(diffFilesCountLabel, gbc_diffFilesCountLabel);
        }
        {
            duplicateFilesCount = new LimitedIntegerJTextField();
            duplicateFilesCount.setMaxValue(99999);
            duplicateFilesCount.setValue( DUPLICATE_FILES_COUNT );
            //duplicateFilesCount.setText("DUPLICATE_FILES_COUNT");
            //duplicateFilesCount.setColumns(10);
            GridBagConstraints gbc_duplicateFilesCount = new GridBagConstraints();
            gbc_duplicateFilesCount.gridwidth = 2;
            gbc_duplicateFilesCount.insets = new Insets(0, 0, 5, 5);
            gbc_duplicateFilesCount.fill = GridBagConstraints.HORIZONTAL;
            gbc_duplicateFilesCount.gridx = 1;
            gbc_duplicateFilesCount.gridy = 1;
            contentPane.add(duplicateFilesCount, gbc_duplicateFilesCount);

            JLabel duplicateFilesCountLabel = new JLabel("Number of copy");
            duplicateFilesCountLabel.setLabelFor(duplicateFilesCount);
            GridBagConstraints gbc_duplicateFilesCountLabel = new GridBagConstraints();
            gbc_duplicateFilesCountLabel.fill = GridBagConstraints.HORIZONTAL;
            gbc_duplicateFilesCountLabel.insets = new Insets(0, 0, 5, 5);
            gbc_duplicateFilesCountLabel.gridx = 0;
            gbc_duplicateFilesCountLabel.gridy = 1;
            contentPane.add(duplicateFilesCountLabel, gbc_duplicateFilesCountLabel);
        }
        {
            JButton btnBuildFiles = new JButton("Build files");
            btnBuildFiles.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new Thread( new Runnable() {
                        @Override
                        public void run()
                        {
                            try {
                                test_BuidTst(
                                    diffFilesCount.getValue(),
                                    duplicateFilesCount.getValue()
                                    );
                                }
                            catch( IOException e ) {
                                DialogHelper.showMessageExceptionDialog( "IOException", e );
                                }
                        }
                    }).start();
                }
            });
            GridBagConstraints gbc_btnBuildFiles = new GridBagConstraints();
            gbc_btnBuildFiles.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnBuildFiles.insets = new Insets(0, 0, 0, 5);
            gbc_btnBuildFiles.gridx = 1;
            gbc_btnBuildFiles.gridy = 3;
            contentPane.add(btnBuildFiles, gbc_btnBuildFiles);
        }

    }

    //@Test
    public void test_BuidTst(
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

        logger.info( "BuildAppTest in: " + tstRootFolder );

        {
            final File refDir = new File( tstRootFolder, "REF" );

            refDir.mkdirs();

            for( int i = 0; i<strings.length; i++ ) {
                strings[ i ] = Integer.toHexString( i );

                File file = new File( refDir, "file-" + i );
                IOHelper.toFile( file, strings[ i ] );
                }

            logger.info( "BuildAppTest ref done" );
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
                File file = new File( tstFolder, "file-" + i );

                IOHelper.toFile( file, strings[ i ] );
                }

            logger.info( "BuildAppTest pass " + d + " done" );
           }

        logger.info( "BuildAppTest done" );
    }
/*
    public static void main(String[] args) throws IOException
    {
        new BuildAppTest().test_BuidTst();
    }*/
}
