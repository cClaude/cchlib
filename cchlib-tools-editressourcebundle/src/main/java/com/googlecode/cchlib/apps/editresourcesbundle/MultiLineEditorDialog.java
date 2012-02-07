package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.I18nString;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 */
public class MultiLineEditorDialog
    extends JDialog
        implements ActionListener
{
    public interface StoreResult
    {
        public void setValueAt( String text, int rowIndex, int columnIndex );
    }

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( MultiLineEditorDialog.class );
    //private boolean lineWrap = true; // TO DO add in pref
    //private boolean wordWrap = true; // TO DO add in pref

    private String ACTIONCMD_OK  = "ACTIONCMD_OK";
    private String ACTIONCMD_CANCEL = "ACTIONCMD_CANCEL";

    // TODO: I18n !!!
    @I18nString private String msgTxt_Options = "Options";
    @I18nString private String msgTxt_LineWrap = "Line Wrap";
    @I18nString private String msgTxt_WordWrap = "Word Wrap";
    private CompareResourcesBundleFrame frame;
    private JTextArea jTextArea;
    private StoreResult storeResult;
    private int rowIndex;
    private int columnIndex;

    /**
     *
     */
    public MultiLineEditorDialog(
            final CompareResourcesBundleFrame   frame,
//            final JTable                        jTable, // TO DO: remove this
//            final AbstractTableModel            tableModel, // TO DO: remove this
            final StoreResult                    storeResult,
            final String                        title,
            final String                        contentText,
            final int                           rowIndex,
            final int                           columnIndex
            )
    {
        super( frame );
        this.frame = frame;
        this.storeResult = storeResult;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;

        logger.trace(
            String.format(
                "openMultiLineEditor %d/%d\n",
                rowIndex,
                columnIndex
                )
            );

        final JCheckBoxMenuItem mItemLineWrap = new JCheckBoxMenuItem( msgTxt_LineWrap );
        mItemLineWrap.setSelected( this.frame.getPreferences().getMultiLineEditorLineWrap() );
        mItemLineWrap.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent arg0 )
                {
                    boolean lw = mItemLineWrap.isSelected();

                    jTextArea.setLineWrap( lw );
                    frame.getPreferences().setMultiLineEditorLineWrap( lw );
                }
            });

        final JCheckBoxMenuItem mItemWordWrap = new JCheckBoxMenuItem( msgTxt_WordWrap );
        mItemWordWrap.setSelected(  this.frame.getPreferences().getMultiLineEditorWordWrap() );
        mItemWordWrap.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed( ActionEvent arg0 )
                    {
                        boolean ww = mItemWordWrap.isSelected();

                        jTextArea.setWrapStyleWord( ww );
                        frame.getPreferences().setMultiLineEditorWordWrap( ww );
                    }
                });

        JMenuBar    menuBar = new JMenuBar();
        JMenu       menu    = new JMenu( msgTxt_Options );
        menu.add( mItemLineWrap );
        menu.add( mItemWordWrap );
        menuBar.add( menu );
        setJMenuBar( menuBar  );

        setFont(new Font("Dialog", Font.PLAIN, 12));
        setBackground(Color.white);
        setForeground(Color.black);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        //        final JDialog dialog = new JDialog( getFrame() );

        jTextArea = new JTextArea();
        jTextArea.setText( contentText );

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        ///        jTextArea.setLineWrap( lineWrap );
        jTextArea.setLineWrap( this.frame.getPreferences().getMultiLineEditorLineWrap() );
        //jTextArea.setWrapStyleWord( wordWrap );
        jTextArea.setWrapStyleWord( this.frame.getPreferences().getMultiLineEditorWordWrap() );

        GridBagConstraints gbc_jScrollPane = new GridBagConstraints();
        gbc_jScrollPane.gridwidth = 4;
        gbc_jScrollPane.fill = GridBagConstraints.BOTH;
        gbc_jScrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_jScrollPane.gridx = 0;
        gbc_jScrollPane.gridy = 0;
        getContentPane().add(jScrollPane, gbc_jScrollPane);
        setSize(320, 240);

        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( title );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );

        JButton jButtonCommit = new JButton(
                new ImageIcon(
                        getClass().getResource( "ok.png" )
                        )
                );
        jButtonCommit.addActionListener( this );
        jButtonCommit.setActionCommand( ACTIONCMD_OK );
        GridBagConstraints gbc_jButtonCommit = new GridBagConstraints();
        gbc_jButtonCommit.fill = GridBagConstraints.BOTH;
        gbc_jButtonCommit.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCommit.gridx = 1;
        gbc_jButtonCommit.gridy = 1;
        getContentPane().add(jButtonCommit, gbc_jButtonCommit);
//        jButtonCommit.addMouseListener(new MouseAdapter()
//        {
//            public void mousePressed(MouseEvent event)
//            {
//
//            }
//        });

        JButton jButtonCancel = new JButton(
                new ImageIcon(
                        getClass().getResource( "close.png" )
                        )
                );
        jButtonCancel.addActionListener( this );
        jButtonCancel.setActionCommand( ACTIONCMD_CANCEL  );
        GridBagConstraints gbc_jButtonCancel = new GridBagConstraints();
        gbc_jButtonCancel.fill = GridBagConstraints.BOTH;
        gbc_jButtonCancel.insets = new Insets(0, 0, 0, 5);
        gbc_jButtonCancel.gridx = 2;
        gbc_jButtonCancel.gridy = 1;
        getContentPane().add(jButtonCancel, gbc_jButtonCancel);
//        jButtonCancel.addMouseListener(new MouseAdapter()
//        {
//            public void mousePressed(MouseEvent event)
//            {
//                dispose();
//            }
//        });
        pack();
        setModal( true );
        setVisible( true );
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        String c = event.getActionCommand();

        if( ACTIONCMD_OK.equals( c ) ) {
            storeResult.setValueAt(
                    jTextArea.getText(),
                    rowIndex,
                    columnIndex
                    );
//            int row = jTable.convertRowIndexToModel( rowIndex );
//            int col = jTable.convertColumnIndexToModel( columnIndex );
//
//            tableModel.fireTableCellUpdated(
//                    row,
//                    col
//                    );
            dispose();
            }
        else if( ACTIONCMD_CANCEL.equals( c ) ) {
            dispose();
            }
    }
}
