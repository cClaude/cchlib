package cx.ath.choisnet.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EnumSet;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;

/**
 * e<B>X</B>tended <B>ComboBox</B> is a JComboBox with extra features...
 * </P>
 * - Unlike JComboBox, by default XComboBox is editable (could be remove using {@link #setEditable(boolean)}),
 * <BR/>
 * <BR/>
 * - XComboBox handle mouse wheel by default (could be disable or customize
 * using {@link XComboBox.Attribute#NO_MOUSE_WHEEL_LISTENER} and
 * {@link #handleMouseWheelMoved(MouseWheelEvent)},
 * <BR/>
 * <BR/>
 * - XComboBox handle insertion of <B>new</B> values by default (could be disable or customize
 * using {@link XComboBox.Attribute#NO_DEFAULT_ACTION_LISTENER} and
 * {@link #defaultActionPerformed(ActionEvent)}.
 * <BR/>
 * <BR/>
 * - XComboBox can control number of entries and limit
 * combo box list to a fixed number of elements {@link #setMaximumItem(int)},
 * you must consider overwrite {@link #removeOldestItems(int)}
 * to provide your own rules to delete oldest items.
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
    /** @serial */
    private int maximumItem = -1;

    /**
     * Create an empty XComboBox
     */
    public XComboBox()
    {
        this( new DefaultComboBoxModel(), null );
    }

    /**
     * TODO:Doc!
     *
     * @param comboBoxModel
     */
    public XComboBox( ComboBoxModel comboBoxModel )
    {
        this( comboBoxModel, null );
    }

    /**
     * TODO:Doc!
     *
     * @param items
     */
    public XComboBox( Object[] items )
    {
        this( new DefaultComboBoxModel(items), null );
    }

    /**
     * TODO:Doc!
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
     * @param comboBoxModel
     * @param attrib
     */
    public XComboBox(
            ComboBoxModel       comboBoxModel,
            EnumSet<Attribute>  attrib
            )
    {
        if( comboBoxModel == null ) {
            throw new NullPointerException("defaultComboBoxModel could not be null");
        }
        if( attrib == null ) {
            attrib = EnumSet.noneOf( Attribute.class );
        }
        super.setEditable(true);
        super.setModel( comboBoxModel );

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

            // Check if needed to remove some items
            if( getModel().getSize() > maximumItem ) {
                removeOldestItems( getModel().getSize() - maximumItem );
            }
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
     * This default implementation expect that new items
     * are always added at the end of the list. So it
     * remove items on the top of the list.
     *
     * @param itemsToRemove number of item need to be
     * remove according to {@link #getMaximumItem()}.
     */
    protected void removeOldestItems(int itemsToRemove)
    {
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
     * TODO: Doc!
     *
     * @param event
     */
    protected void defaultActionPerformed(ActionEvent event)
    {
        String cmd = event.getActionCommand();

        if( "comboBoxEdited".equals( cmd ) ) {
            Object o = this.getSelectedItem();

            // Look if this element already exist in model
            final int size  = getModel().getSize();
            boolean   found = false;

            for(int i=0;i<size;i++) {
                if( o.equals( getModel().getElementAt( i ) ) ) {
                    found = true;
                    break;
                }
            }
            if( !found ) {
                this.addItem( o );
            }
        }
    }
}
