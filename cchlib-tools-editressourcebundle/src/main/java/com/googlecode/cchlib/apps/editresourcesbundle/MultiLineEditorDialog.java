package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.I18nString;

/**
 *
 */
public abstract class MultiLineEditorDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( MultiLineEditorDialog.class );
    private boolean lineWrap = true; // TODO add in pref
    private boolean wordWrap = true; // TODO add in pref

    // TODO: I18n !!!
    @I18nString private String msgTxt_Options = "Options";
    @I18nString private String msgTxt_LineWrap = "Line Wrap";
    @I18nString private String msgTxt_WordWrap = "Word Wrap";

    /**
     *
     */
    public MultiLineEditorDialog(
            final Frame                 frame,
            final JTable                jTable,
            final AbstractTableModel    tableModel,
            final String                title,
            final String                txt,
            final int                   rowIndex,
            final int                   columnIndex
            )
    {
        super( frame );

        logger.trace(
            String.format(
                "openMultiLineEditor %d/%d\n",
                rowIndex,
                columnIndex
                )
            );
//        final JDialog dialog = new JDialog( getFrame() );

        final JTextArea jTextArea = new JTextArea();
        jTextArea.setText( txt );

        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        JButton jButtonCancel = new JButton(
                new ImageIcon(
                        getClass().getResource( "close.png" )
                        )
                );
        jButtonCancel.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                dispose();
            }
        });

        JButton jButtonCommit = new JButton(
                new ImageIcon(
                        getClass().getResource( "ok.png" )
                        )
                );
        jButtonCommit.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                setValueAt(
                        jTextArea.getText(),
                        rowIndex,
                        columnIndex
                        );
                int row = jTable.convertRowIndexToModel( rowIndex );
                int col = jTable.convertColumnIndexToModel( columnIndex );

                tableModel.fireTableCellUpdated(
                        row,
                        col
                        );
                dispose();
            }
        });

        final JCheckBoxMenuItem mItemLineWrap = new JCheckBoxMenuItem( msgTxt_LineWrap );
        jTextArea.setLineWrap( lineWrap );
        mItemLineWrap.setSelected( lineWrap );
        mItemLineWrap.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent arg0 )
                {
                    jTextArea.setLineWrap(
                            mItemLineWrap.isSelected()
                            );
                }
            });

        final JCheckBoxMenuItem mItemWordWrap = new JCheckBoxMenuItem( msgTxt_WordWrap );
        jTextArea.setWrapStyleWord( wordWrap );
        mItemWordWrap.setSelected( wordWrap );
        mItemWordWrap.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed( ActionEvent arg0 )
                    {
                        jTextArea.setWrapStyleWord(
                                mItemWordWrap.isSelected()
                                );
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

        JPanel cmdPanel = new JPanel();
        cmdPanel.add(jButtonCancel);
        cmdPanel.add(jButtonCommit);

        add(jScrollPane, BorderLayout.CENTER);
        add(cmdPanel, BorderLayout.SOUTH);
        setSize(320, 240);

        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( title );
        setLocationRelativeTo( frame );
        getContentPane().setPreferredSize( getSize() );
        pack();
        setModal( true );
        setVisible( true );
    }

    protected abstract void setValueAt( String text, int rowIndex, int columnIndex );
}
