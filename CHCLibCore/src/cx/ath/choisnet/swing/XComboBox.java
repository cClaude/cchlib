package cx.ath.choisnet.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EnumSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;

/**
 * TODO: Doc!
 * <P>
 * e<B>X</B>tended <B>ComboBox</B> is a JComboBox with extra features...
 * </P>
 * <P>
 * - Unlike JComboBox, by default XComboBox is editable (could be remove using {@link #setEditable(boolean)}),
 * <BR/>
 * - XComboBox handle mouse wheel by default (could be disable or customize
 * using {@link XComboBox.Attribute#NO_MOUSE_WHEEL_LISTENER} and
 * {@link #handleMouseWheelMoved(MouseWheelEvent)},
 * <BR/>
 * - XComboBox handle insertion of <B>new</B> values by default (could be disable or customize
 * using {@link XComboBox.Attribute#NO_DEFAULT_ACTION_LISTENER} and
 * {@link #defaultActionPerformed(ActionEvent)}.
 * <BR/>
 * </P>
 *
 * @author Claude CHOISNET
 */
public class XComboBox extends JComboBox
{
    /**
     * This enum is design to customized {@link XComboBox},
     * mainly to remove default extra implementations.
     * 
     * @author Claude CHOISNET
     */
    public enum Attribute {
        /**
         * Don't add default MouseWheelListener for
         * this XComboBox.
         * <BR/>
         * Current implementation handle mouse wheel only
         * when mouse is on XComboBox, if you wan't to handle
         * mouse wheel when XComboBox has focus, you must disable
         * this feature.
         * 
         * @see XComboBox#handleMouseWheelMoved(MouseWheelEvent)
         */
        NO_MOUSE_WHEEL_LISTENER,
        
        /**
         * TODO: extra Doc<br/>
         * Don't add default MouseWheelListener for
         * this XComboBox.
         * 
         * @see XComboBox#defaultActionPerformed(ActionEvent)
         */
        NO_DEFAULT_ACTION_LISTENER
    }
    private static final long serialVersionUID = 1L;
    //private static final Logger slogger = Logger.getLogger( XComboBox.class );
    private DefaultComboBoxModel defaultComboBoxModel;
    private int maximumItem = -1;
    
    public XComboBox()
    {
        this( new DefaultComboBoxModel(), null );
    }

    public XComboBox( DefaultComboBoxModel defaultComboBoxModel )
    {
        this( defaultComboBoxModel, null );
    }

    /**
     * 
     * @param items
     */
    public XComboBox( Object[] items )
    {
        this( new DefaultComboBoxModel(items), null );
    }

    /**
     * 
     * @param items
     */
    public XComboBox( Vector<?> items )
    {
        this( new DefaultComboBoxModel(items), null );
    }

    /**
     * TODO: Doc!
     *
     * @param defaultComboBoxModel 
     * @param attrib
     */
    public XComboBox(
            DefaultComboBoxModel    defaultComboBoxModel,
            EnumSet<Attribute>      attrib
            )
    {
        if( defaultComboBoxModel == null ) {
            throw new NullPointerException("defaultComboBoxModel could not be null");
        }
        if( attrib == null ) {
            attrib = EnumSet.noneOf( Attribute.class );
        }
        super.setEditable(true);
        this.defaultComboBoxModel = defaultComboBoxModel;
        super.setModel( defaultComboBoxModel );

        if( !attrib.contains( Attribute.NO_MOUSE_WHEEL_LISTENER ) ) {
            super.addMouseWheelListener(
                    new MouseWheelListener()
                    {
                        @Override
                        public void mouseWheelMoved(MouseWheelEvent event)
                        {
                            handleMouseWheelMoved(event);
                        }
                    });
        }
        if( !attrib.contains( Attribute.NO_DEFAULT_ACTION_LISTENER ) ) {
            super.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event)
                        {
                            defaultActionPerformed(event);
                        }
                    });
        }

//        super.addItemListener(new ItemListener() 
//        {
//            @Override
//            public void itemStateChanged(ItemEvent event) 
//            {
//                jComboBox0ItemItemStateChanged(event);
//            }
//        });
//        
//        super.addPropertyChangeListener(new PropertyChangeListener() 
//        {
//            @Override
//            public void propertyChange(PropertyChangeEvent event) 
//            {
//                jComboBox0PropertyChangePropertyChange(event);
//            }
//        });
    }

    /**
     * Set the maximumItem could be insert in this XComboBox
     * @param maximumItem the maximumItem could be insert in this XComboBox, if value is
     * -1 there is no limit.
     * @throws IllegalArgumentException is maximumItem is negative and
     * not equals to -1.
     */
    public void setMaximumItem( int maximumItem )
    {
        if( maximumItem == -1 ) {
            this.maximumItem = maximumItem;
        }
        else if( maximumItem < 0 ) {
            throw new IllegalArgumentException();
        }
        else {
            this.maximumItem = maximumItem;
        }
    }

    /**
     * Returns the maximumItem could be insert in this XComboBox
     * @return the maximumItem could be insert in this XComboBox, if value is
     * -1 there is no limit.
     */
    public int getMaximumItem()
    {
        return maximumItem;
    }
    
    @Override
    public void intervalAdded(ListDataEvent e) 
    {
        // Track insertion of items
        if( maximumItem != -1 ) {
            if( getModel().getSize() > maximumItem ) {
                removeOldestItems( getModel().getSize() - maximumItem );
            }
        }

        super.intervalAdded( e );
    }

    /**
     * 
     */
    protected void removeOldestItems(int itemsToRemove)
    {// remove oldest items
        //slogger.info( ">>itemsToRemove:" + itemsToRemove );
        
        for(int i=0;i<itemsToRemove;i++) {
            removeItemAt(0);
        }
    }
    
    /**
     * Handle mouse wheel for {@link JComboBox}, could be
     * use for any JComboBox
     *
     * @param event current MouseWheelEvent
     */
    public static void handleMouseWheelMoved(MouseWheelEvent event)
    {
        final Object s = event.getSource();

        if( s instanceof JComboBox ) {
            JComboBox jcb = JComboBox.class.cast( s );

            if( event.getWheelRotation() > 0 ) {
                int index = jcb.getSelectedIndex();
                if( index == -1 ) {
                    index = 0;
                }
                else {
                    index++;
                }
                if( index < jcb.getModel().getSize() ) {
                    jcb.setSelectedIndex( index );
                }
            }
            else if( event.getWheelRotation() < 0 ) {
                int index = jcb.getSelectedIndex();
                if( index == -1 ) {
                    index = 0;
                }
                else {
                    index--;
                    if( index < 0 ) {
                        index = 0;
                    }
                }
                if( index < jcb.getItemCount() ) {
                    jcb.setSelectedIndex( index );
                }
            }
        }
    }

    /**
     *
     * @param event
     */
    protected void defaultActionPerformed(ActionEvent event)
    {
        //slogger.info( "ActionEvent:" + event );
        String cmd = event.getActionCommand();
        //slogger.info( "cmd:" + cmd );

        if( "comboBoxEdited".equals( cmd ) ) {
            Object o = this.getSelectedItem();
            int    i = defaultComboBoxModel.getIndexOf( o );
            //slogger.info( "i:" + i );
            if( i==-1 ) {
                // Add entry only if not already exist
                this.addItem( o );
            }
        }
    }

//    private void jComboBox0PropertyChangePropertyChange(PropertyChangeEvent event)
//    {
//        slogger.info( "PropertyChangeEvent:" + event );
//    }
//    private void jComboBox0ItemItemStateChanged(ItemEvent event)
//    {
//        slogger.info( "ItemEvent:" + event );
//        Object o = event.getItem();
//        slogger.info( "o:" + o );
//        int index = super.getSelectedIndex();
//        if( index == -1 ) {
//            // Not in list... Add it !
//            //defaultComboBoxModel0.addElement( o );
//        }
////        slogger.info( "index:" + index );
////        Object o2 = jComboBox0.getSelectedItem();
////        slogger.info( "Select item:" + o2 );
//        //jComboBox0.setSelectedItem( anObject );
//        //jComboBox0.addItem( anObject );
//    }

}
