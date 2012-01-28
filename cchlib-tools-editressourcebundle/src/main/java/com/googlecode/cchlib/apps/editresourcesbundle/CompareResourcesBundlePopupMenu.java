package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;


import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.swing.table.JPopupMenuForJTable;

/**
 *
 * @author Claude CHOISNET
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 */
class CompareResourcesBundlePopupMenu
    extends JPopupMenuForJTable
{
    private final static transient Logger logger = Logger.getLogger( CompareResourcesBundlePopupMenu.class );
    private Frame               frame;
    private AbstractTableModel  abstractTableModel;
    /** @serial */
    private CompareResourcesBundleTableModel.Colunms colunms;

    private boolean lineWrap = true; // TODO add in pref
    private boolean wordWrap = true; // TODO add in pref

    // TODO: i18n
    @I18nString
    private String txtHTMLPreview = "HTML Preview";
    // TODO: i18n
    @I18nString
    private String txtEditLines = "Edit lines";
    // TODO: i18n
    @I18nString
    private String txtOptions = "Options";

    /**
     * @param jTable
     * @param abstractTableModel
     * @param colunms
     */
    public CompareResourcesBundlePopupMenu(
            JTable                                      jTable,
            AbstractTableModel                          abstractTableModel,
            CompareResourcesBundleTableModel.Colunms    colunms
            )
    {
        super( jTable );

        this.abstractTableModel = abstractTableModel;
        this.colunms            = colunms;
    }

    @Override
    protected JPopupMenu createContextMenu(
            final int rowIndex,
            final int columnIndex
            )
    {
        //JPopupMenu contextMenu = super.createContextMenu(rowIndex, columnIndex);
        JPopupMenu contextMenu = new JPopupMenu();

        addCopyMenuItem(contextMenu, rowIndex, columnIndex);
        addPasteMenuItem(contextMenu, rowIndex, columnIndex);
        contextMenu.addSeparator();

        addShowHTMLMenuItem(contextMenu, rowIndex, columnIndex);
        addEditMultiLineMenuItem(contextMenu, rowIndex, columnIndex);

//        switch( columnIndex ) {
//            case ExampleTableModel.COLUMN_NAME:
//                break;
//            case ExampleTableModel.COLUMN_PRICE:
//                break;
//            case ExampleTableModel.COLUMN_QUANTITY:
//                contextMenu.addSeparator();
//                ActionListener changer = new ActionListener() {
//
//                    public void actionPerformed( ActionEvent e )
//                    {
//                        JMenuItem sourceItem = (JMenuItem)e.getSource();
//                        Object value = sourceItem
//                                .getClientProperty( PROP_CHANGE_QUANTITY );
//                        if( value instanceof Integer ) {
//                            Integer changeValue = (Integer)value;
//                            Integer currentValue = (Integer)getTableModel()
//                                    .getValueAt( rowIndex, columnIndex );
//                            getTableModel().setValueAt(
//                                    new Integer( currentValue.intValue()
//                                            + changeValue.intValue() ),
//                                    rowIndex, columnIndex );
//                        }
//                    }
//                };
//                JMenuItem changeItem = new JMenuItem();
//                changeItem.setText( "+1" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( 1 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "-1" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( -1 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "+10" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( 10 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "-10" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( -10 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = null;
//                break;
//            case ExampleTableModel.COLUMN_AMOUNT:
//                break;
//            default:
//                break;
//        }
        return contextMenu;
    }

    protected void addShowHTMLMenuItem(
            JPopupMenu  contextMenu,
            final int   rowIndex,
            final int   columnIndex
            )
    {
        if( columnIndex == colunms.colunmKey) {
            return;
            }
        else if( columnIndex == colunms.colunmLeftLine ) {
            return;
            }
        else if( columnIndex == colunms.colunmRightLine ) {
            return;
            }

        JMenuItem copyMenu = new JMenuItem();

        copyMenu.setText( txtHTMLPreview );
        copyMenu.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getValueAt( rowIndex, columnIndex );

                        if( value instanceof String ) {
                            openHTLMPreview(
                                    txtHTMLPreview,
                                    "<html>" + value + "<html>"
                                    );
                        }
                    }
                });

        contextMenu.addSeparator();
        contextMenu.add( copyMenu );
    }

    protected void addEditMultiLineMenuItem(
            JPopupMenu  contextMenu,
            final int   rowIndex,
            final int   columnIndex
            )
    {
        if( columnIndex == colunms.colunmKey ) {
            return;
            }
        else if( columnIndex == colunms.colunmLeftLine ) {
            return;
            }
        else if( columnIndex == colunms.colunmRightLine ) {
            return;
            }

        JMenuItem copyMenu = new JMenuItem();

        copyMenu.setText( txtEditLines );
        copyMenu.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getValueAt( rowIndex, columnIndex );

                        if( value instanceof String ) {
                            openMultiLineEditor(
                                    txtEditLines,
                                    String.class.cast( value ),
                                    rowIndex,
                                    columnIndex
                                    );
                        }
                    }
                });

        contextMenu.addSeparator();
        contextMenu.add( copyMenu );
    }

    private Frame getFrame()
    {
        if( frame == null ) {
            Container c = getJTable();

            while( c != null ) {
                if( c instanceof Frame ) {
                    frame = Frame.class.cast( c );
                    break;
                }
                c = c.getParent();
            }
        }

        return frame;
    }

    public void openHTLMPreview(
            String  title,
            String  html
            )
    {
        final JDialog dialog = new JDialog( getFrame() );

        JEditorPane htmlComponent = new JEditorPane();
        htmlComponent.setEditable( false );
        htmlComponent.setContentType( "text/html" );
//        htmlComponent.putClientProperty(
//                JEditorPane.W3C_LENGTH_UNITS,
//                Boolean.TRUE
//                );
//        htmlComponent.putClientProperty(
//                JEditorPane.HONOR_DISPLAY_PROPERTIES,
//                Boolean.TRUE
//                );
        htmlComponent.setText( html );

        JScrollPane jScrollPane = new JScrollPane(htmlComponent);

        JButton jButton = new JButton(
                new ImageIcon(
                        getClass().getResource( "close.png" )
                        )
                );
        //jButton.setText("OK");
        jButton.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                dialog.dispose();
            }
        });

        JPanel cmdPanel = new JPanel();
        cmdPanel.add(jButton);

        dialog.setFont(new Font("Dialog", Font.PLAIN, 12));
        dialog.setBackground(Color.white);
        dialog.setForeground(Color.black);
        dialog.add(jScrollPane, BorderLayout.CENTER);
        dialog.add(cmdPanel, BorderLayout.SOUTH);
        dialog.setSize(320, 240);

        dialog.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        dialog.setTitle( title );
        dialog.setLocationRelativeTo( getFrame() );
        dialog.getContentPane().setPreferredSize( dialog.getSize() );
        dialog.pack();
        dialog.setVisible( true );
    }

    public void openMultiLineEditor(
            String      title,
            String      txt,
            final int   rowIndex,
            final int   columnIndex
            )
    {
        logger.trace(
            String.format(
                "openMultiLineEditor %d/%d\n",
                rowIndex,
                columnIndex
                )
            );
        final JDialog dialog = new JDialog( getFrame() );

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
                dialog.dispose();
            }
        });

        JButton jButtonCommit = new JButton(
                new ImageIcon(
                        getClass().getResource( "commit.png" )
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
                int row = getJTable().convertRowIndexToModel( rowIndex );
                int col = getJTable().convertColumnIndexToModel( columnIndex );

                abstractTableModel.fireTableCellUpdated(
                        row,
                        col
                        );
                dialog.dispose();
            }
        });

        final JCheckBoxMenuItem mItemLineWrap = new JCheckBoxMenuItem("Line Wrap");
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

        final JCheckBoxMenuItem mItemWordWrap = new JCheckBoxMenuItem("Word Wrap");
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
        JMenu       menu    = new JMenu( txtOptions  );
        menu.add( mItemLineWrap );
        menu.add( mItemWordWrap );
        menuBar.add( menu );
        dialog.setJMenuBar( menuBar  );

        dialog.setFont(new Font("Dialog", Font.PLAIN, 12));
        dialog.setBackground(Color.white);
        dialog.setForeground(Color.black);

        JPanel cmdPanel = new JPanel();
        cmdPanel.add(jButtonCancel);
        cmdPanel.add(jButtonCommit);

        dialog.add(jScrollPane, BorderLayout.CENTER);
        dialog.add(cmdPanel, BorderLayout.SOUTH);
        dialog.setSize(320, 240);

        dialog.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        dialog.setTitle( title );
        dialog.setLocationRelativeTo( getFrame() );
        dialog.getContentPane().setPreferredSize( dialog.getSize() );
        dialog.pack();
        dialog.setModal( true );
        dialog.setVisible( true );
    }
}
