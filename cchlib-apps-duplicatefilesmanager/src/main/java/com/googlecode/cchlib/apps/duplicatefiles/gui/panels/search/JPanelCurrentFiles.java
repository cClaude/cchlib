package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.googlecode.cchlib.lang.StringHelper;

public class JPanelCurrentFiles extends JPanel
{
    private static final long serialVersionUID = 1L;
    private final JPanelCurrentFile panel;
    private final JScrollPane scrollPane;
    private final int nThread;

    /**
     * @wbp.parser.constructor
     */
    public JPanelCurrentFiles()
    {
        this( 2 );
    }

    JPanelCurrentFiles( final int nThread )
    {
        super();

        this.nThread = nThread;

        assert nThread > 0;
        setLayout(new BorderLayout(0, 0));

        this.scrollPane = new JScrollPane();
        add(this.scrollPane, BorderLayout.CENTER);

        this.panel = new JPanelCurrentFile( nThread );
        final GridBagLayout gridBagLayout = (GridBagLayout) this.panel.getLayout();
        gridBagLayout.rowWeights = new double[]{0.0, 0.0};
        gridBagLayout.rowHeights = new int[]{20, 0};
        this.scrollPane.setViewportView(this.panel);
    }

    public void setCurrentDir( final String txtCurrentDir )
    {
        clearCurrentFiles();

        setCurrentFile( txtCurrentDir, 0 );
    }

    public void setCurrentFile( final String currentFileText, final int threadNumber )
    {
        this.panel.setCurrentFile( currentFileText, threadNumber );
    }

    public void clearCurrentFiles()
    {
        for( int threadNumber = 0; threadNumber<this.nThread; threadNumber++ ) {
            clearCurrentFile( threadNumber );
        }
    }

    public void clearCurrentFileLabels()
    {
        setCurrentFileLabels( StringHelper.EMPTY );
    }

    public void setCurrentFileLabels( final String label )
    {
        for( int threadNumber = 0; threadNumber<this.nThread; threadNumber++ ) {
            setCurrentFileLabel( label, threadNumber );
        }
    }

    private void setCurrentFileLabel( final String label, final int threadNumber )
    {
        this.panel.setCurrentFile( label, threadNumber );
    }

    public void clearCurrentFile( final int threadNumber )
    {
        // FXIME: ? Leave name ?
        setCurrentFile( StringHelper.EMPTY, threadNumber );
    }
}
