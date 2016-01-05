// $codepro.audit.disable avoidInstantiationInLoops, numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JPanelCurrentFile extends JPanel
{
    private static final long serialVersionUID = 1L;
    private final JLabel[]     jLabelCurrentFiles ;
    private final JTextField[] jTextFieldCurrentFiles;

    /**
     * @wbp.parser.constructor
     */
    public JPanelCurrentFile()
    {
        this( 2 );
    }

    JPanelCurrentFile( final int nThread )
    {
        jLabelCurrentFiles     = new JLabel[nThread];
        jTextFieldCurrentFiles = new JTextField[nThread];

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 200, 0};
        gridBagLayout.rowHeights = newRowHeight( nThread, 20 );
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = newRowWeight( nThread, 0.0 );
        setLayout(gridBagLayout);

        int i = 0;
        //$hide>>$
        for(; i<nThread; i++ )
        //$hide<<$
        {
            final JLabel jLabel = newLabelCurrentFile( i );
            final GridBagConstraints gbc_jLabelCurrentFile = new GridBagConstraints();
            gbc_jLabelCurrentFile.anchor = GridBagConstraints.EAST;
            gbc_jLabelCurrentFile.insets = new Insets( 0, 0, 5, 0 );
            gbc_jLabelCurrentFile.gridx = 0;
            gbc_jLabelCurrentFile.gridy = i;
            add( jLabel, gbc_jLabelCurrentFile );

            final JTextField jTextField = newTextFieldCurrentFile();
            final GridBagConstraints gbc_jTextFieldCurrentFile = new GridBagConstraints();
            gbc_jTextFieldCurrentFile.fill = GridBagConstraints.HORIZONTAL;
            gbc_jTextFieldCurrentFile.insets = new Insets(0, 0, 5, 0);
            gbc_jTextFieldCurrentFile.gridx = 1;
            gbc_jTextFieldCurrentFile.gridy = i;
            add( jTextField, gbc_jTextFieldCurrentFile );

            jLabelCurrentFiles[ i ] = jLabel;
            jTextFieldCurrentFiles[ i ] = jTextField;
        }
    }

    private double[] newRowWeight( final int nThread, final double value )
    {
        final double[] doubles = new double[ nThread + 1 ];

        for( int i = 0; i < nThread; i++ ) {
            doubles[ i ] = value;
        }
        doubles[ nThread ] = Double.MIN_VALUE;
        return doubles;
     }

    private int[] newRowHeight( final int nThread, final int value  )
    {
        final int[] columnWidths = new int[ nThread + 1 ];

        for( int i = 0; i < nThread; i++ ) {
            columnWidths[ i ] = value;
        }
        columnWidths[ nThread ] = 0;
        return columnWidths;
    }

    private JTextField newTextFieldCurrentFile()
    {
        final JTextField txtField = new JTextField();
        txtField.setEditable( false );
        return txtField;
    }

    private JLabel newLabelCurrentFile( final int i )
    {
        final JLabel jLabel = new JLabel( getTxtLabelCurrentFile( i ) );
        jLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        return jLabel;
    }

    private String getTxtLabelCurrentFile( final int threadIndex )
    {
        final Integer threadNumber = Integer.valueOf( threadIndex + 1 );

        return String.format( "File on thread %d :", threadNumber );
    }

    public void setCurrentFile( final String currentFileText, final int threadNumber )
    {
        jTextFieldCurrentFiles[ threadNumber ].setText( currentFileText );
    }

    public void setCurrentFileLabel( final String label, final int threadNumber )
    {
        jLabelCurrentFiles[ threadNumber ].setText( label );
    }

}
