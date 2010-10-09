package cx.ath.choisnet.tools.i18n;


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
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author Claude
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 */
public class CompareRessourceBundlePopupMenu
    extends JPopupMenuForJTable
{
    private Frame               frame;
    private AbstractTableModel  abstractTableModel;

    /**
     * @param jTable
     * @param abstractTableModel 
     */
    public CompareRessourceBundlePopupMenu( 
            JTable              jTable,
            AbstractTableModel  abstractTableModel
            )
    {
        super( jTable );
        
        this.abstractTableModel = abstractTableModel;
    }

    @Override
    protected JPopupMenu createContextMenu( 
            final int rowIndex,
            final int columnIndex 
            )
    {
        JPopupMenu contextMenu = super.createContextMenu(rowIndex, columnIndex);

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
        if( columnIndex == 0 ) {
            return;
        }
        
        JMenuItem copyMenu = new JMenuItem();
        
        copyMenu.setText( "HTML Preview" );
        copyMenu.addActionListener(
                new ActionListener() 
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getTableModel()
                            .getValueAt( rowIndex, columnIndex );
                        
                        if( value instanceof String ) {
                            openHTLMPreview(
                                    "HTML Preview",
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
        if( columnIndex == 0 ) {
            return;
        }
        
        JMenuItem copyMenu = new JMenuItem();
        
        copyMenu.setText( "Edit lines" );
        copyMenu.addActionListener(
                new ActionListener() 
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getTableModel()
                            .getValueAt( rowIndex, columnIndex );
                        
                        if( value instanceof String ) {
                            openMultiLineEditor(
                                    "Edit Lines",
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
            Container   c       = getJTable();
            
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
            String      title,
            String      html
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
        
        dialog.setFont(new Font("Dialog", Font.PLAIN, 12));
        dialog.setBackground(Color.white);
        dialog.setForeground(Color.black);
        dialog.add(jScrollPane, BorderLayout.CENTER);
        dialog.add(jButton, BorderLayout.SOUTH);
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
        final JDialog dialog = new JDialog( getFrame() );

        final JTextArea jTextArea = new JTextArea();
        jTextArea.setText( txt );
        
        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        JButton jButtonCancel = new JButton(
                new ImageIcon(
                        getClass().getResource( "close.png" )
                        )
                );
        //jButtonCancel.setText("Cancel");
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
        //jButtonCommit.setText("Valid");
        jButtonCommit.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent event) 
            {
                abstractTableModel.setValueAt( 
                        jTextArea.getText(), 
                        rowIndex, 
                        columnIndex
                        );
                abstractTableModel.fireTableCellUpdated(
                        rowIndex, 
                        columnIndex
                        ); 
                dialog.dispose();
            }
        });
        
        dialog.setFont(new Font("Dialog", Font.PLAIN, 12));
        dialog.setBackground(Color.white);
        dialog.setForeground(Color.black);
        //TODO: better layout
        dialog.add(jScrollPane, BorderLayout.CENTER);
        dialog.add(jButtonCancel, BorderLayout.NORTH);
        dialog.add(jButtonCommit, BorderLayout.SOUTH);
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
