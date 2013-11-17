package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;

/**
 * Appears as a special pane within the FindOptions tabbed panel.
 * The only one that does not generate a FindFilter.
 */
class FindResultsJPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FindResultsJPanel.class );

    protected DefaultListModel<File>  model = null;
    protected JList<File>             fileList = null;

    /**
        Construct a search results pane with a scrollable list of files.
        When an item is double-clicked the FindAccessory controller will
        be instructed to select the file in the parent JFileChooser's item
        display.
    */
    FindResultsJPanel( final JFileChooserSelector jFileChooserSelector )
    {
        super();

        setLayout(new BorderLayout());

        model = new DefaultListModel<File>();
        fileList = new JList<File>(model);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setCellRenderer(new FindResultsCellRenderer());
        add(fileList,BorderLayout.CENTER);

        // Double click listener
        MouseListener mouseListener = new MouseAdapter()
        {
            @Override
            public void mouseClicked (MouseEvent e)
            {
                if (e.getClickCount() == 2) {
                    try {
                        int  index = fileList.locationToIndex(e.getPoint());
                        File f     = model.elementAt(index);
                        jFileChooserSelector.goTo(f);
                    }
                    catch (Throwable err) {
                        LOGGER.warn( "FindResultsJPanel - MouseListener", err );
                    }
                }
            }
        };
        fileList.addMouseListener(mouseListener);
    }

    /**
        Add a file to the results list.

        @param f file found
    */
    public void append( File f )
    {
        if (f == null) {
            return;
        }
        model.addElement(f);
    }

    /**
        Clear all items from the results list.
    */
    public void clear ()
    {
        if (model != null) {
            model.removeAllElements();
            invalidate();
            repaint();
        }
    }

    /**
        Convenience class for rendering cells in the results list.
    */
    class FindResultsCellRenderer extends JLabel implements ListCellRenderer<File>
    {
        private static final long serialVersionUID = 1L;

        FindResultsCellRenderer()
        {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent (
                            JList<? extends File> list,
                            /*Object*/File value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus)
        {
            if (index == -1)
            {
                // This shouldn't happen since we won't be using this
                // renderer in a combo box
                int selected = list.getSelectedIndex();
                if (selected == -1) {
                    return this;
                }
                else {
                    index = selected;
                }
            }

            setBorder(new EmptyBorder(1,2,1,2));
            setFont(new Font("Helvetica",Font.PLAIN,10));

            // show absolute path of file
            File file = model.elementAt( index );
            setText( file.getAbsolutePath() );

            // selection characteristics
            if( isSelected ) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                }
            else {
                setBackground(Color.white);
                setForeground(Color.black);
                }
            return this;
        }
    }
}
