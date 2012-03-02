package com.googlecode.cchlib.swing;

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
 * using {@link XComboBoxAttribute#NO_MOUSE_WHEEL_LISTENER} and
 * {@link #handleMouseWheelMoved(MouseWheelEvent)},
 * <BR/>
 * <BR/>
 * - XComboBox handle insertion of <B>new</B> values by default (could be disable or customize
 * using {@link XComboBoxAttribute#NO_DEFAULT_ACTION_LISTENER} and
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
 * @since 4.1.6
 */
public class XComboBox<E> extends JComboBox<E>
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private int maximumItem = -1;
    /** @serial */
    private Class<E> contentClass;

    /**
     * Create an empty XComboBox with a default data model.
     */
    public XComboBox( final Class<E> contentClass )
    {
        this( new DefaultComboBoxModel<E>(), contentClass, null );
    }

    /**
     * Creates a XComboBox that takes its items from an existing ComboBoxModel.
     *
     * @param comboBoxModel the {@link ComboBoxModel} that provides the displayed
     *        list of items
     */
    public XComboBox(
        final ComboBoxModel<E>  comboBoxModel,
        final Class<E>          contentClass
        )
    {
        this( comboBoxModel, contentClass, null );
    }

    /**
     * Creates a XComboBox that contains the elements in the specified array.
     * By default the first item in the array (and therefore the data model)
     * becomes selected.
     *
     * @param items an array of objects to insert into the combo box
     */
    public XComboBox(
        final Class<E>  contentClass,
        final E[]       items
        )
    {
        this( new DefaultComboBoxModel<E>(items), contentClass, null );
    }

    /**
     * Creates a XComboBox that contains the elements in the specified Vector.
     * By default the first item in the vector (and therefore the data model)
     * becomes selected.
     *
     * @param items an array of vectors to insert into the combo box
     */
    public XComboBox(
        final Class<E>  contentClass,
        final Vector<E> items
        )
    {
        this( new DefaultComboBoxModel<E>(items), contentClass, null );
    }

    /**
     * Creates a XComboBox that takes its items from an existing ComboBoxModel.
     *
     * @param comboBoxModel the {@link ComboBoxModel} that provides the displayed
     *        list of items
     * @param attrib  the {@link XComboBoxAttribute} set to configure the XComboBox
     */
    public XComboBox(
            final ComboBoxModel<E>  comboBoxModel,
            final Class<E>          contentClass,
            EnumSet<XComboBoxAttribute>      attrib
            )
    {
        this.contentClass = contentClass;

        if( comboBoxModel == null ) {
            throw new NullPointerException("defaultComboBoxModel could not be null");
            }
        if( attrib == null ) {
            attrib = EnumSet.noneOf( XComboBoxAttribute.class );
            }
        super.setEditable(true);
        super.setModel( comboBoxModel );

        if( !attrib.contains( XComboBoxAttribute.NO_MOUSE_WHEEL_LISTENER ) ) {
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
        if( !attrib.contains( XComboBoxAttribute.NO_DEFAULT_ACTION_LISTENER ) ) {
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
     *
     * @param maximumItem the maximumItem could be insert in this XComboBox,
     *          if value is -1 there is no limit.
     * @throws IllegalArgumentException is maximumItem is negative and
     *          not equals to -1.
     */
    public void setMaximumItem( final int maximumItem )
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
     *
     * @return the maximumItem could be insert in this XComboBox, if value is
     *          -1 there is no limit.
     */
    public int getMaximumItem()
    {
        return maximumItem;
    }

    @Override
    public void intervalAdded( final ListDataEvent e )
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
     * This default implementation expect that new items are always added
     * at the end of the list. So it remove items on the top of the list.
     *
     * @param itemsToRemove number of item need to be remove according
     *        to {@link #getMaximumItem()}.
     */
    protected void removeOldestItems( final int itemsToRemove )
    {
        for( int i=0; i<itemsToRemove; i++ ) {
            removeItemAt(0);
        }
    }

    /**
     * Handle mouse wheel for {@link XComboBox}, could be use for any XComboBox
     *
     * @param event current {@link MouseWheelEvent}
     */
    public static void handleMouseWheelMoved( final MouseWheelEvent event )
    {
        final Object s = event.getSource();

        if( s instanceof JComboBox ) {
            JComboBox<?> jcb = JComboBox.class.cast( s );

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
     * Handle "comboBoxEdited" action command
     *
     * @param event Current {@link ActionEvent}
     */
    protected void defaultActionPerformed( final ActionEvent event )
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
                E value = contentClass.cast( o );

                this.addItem( value );
                }
            }
    }
}
