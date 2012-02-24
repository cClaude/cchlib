package cnamts.gui;

import java.io.File;
import java.util.Enumeration;
import javax.swing.JFrame;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerPanel;

public class B2TransformWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    private BatchRunnerPanel contentPane;


    /**
     * Create the frame.
     * @param txtAddSourceFile
     * @param txtSetDestinationFolder
     * @param txtClearSourceFileList
     * @param txtDoActionButton
     */
    public B2TransformWB(
        String txtAddSourceFile,
        String txtSetDestinationFolder,
        String txtClearSourceFileList,
        String txtDoActionButton
        )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 450, 300 );
        contentPane = new BatchRunnerPanel(
                txtAddSourceFile,
                txtSetDestinationFolder,
                txtClearSourceFileList,
                txtDoActionButton
                );
        setContentPane( contentPane );
    }

    protected int getSourceFilesCount()
    {
        return contentPane.getSourceFilesCount();
    }

    protected Enumeration<File> getSourceFileElements()
    {
        return contentPane.getSourceFileElements();
    }

    protected File getDestinationFolderFile()
    {
        return contentPane.getDestinationFolderFile();
    }

    protected void setCurrentState( String message )
    {
        contentPane.setCurrentState( message );
    }
}
