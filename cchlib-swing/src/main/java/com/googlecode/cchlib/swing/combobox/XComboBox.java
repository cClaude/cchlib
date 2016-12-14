package com.googlecode.cchlib.swing.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.util.EnumSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;

/**
 * e<B>X</B>tended <B>ComboBox</B> is a JComboBox with extra features...
 *
 * <ul>
 * <li>Unlike JComboBox, by default XComboBox is editable (could be remove using
 * {@link #setEditable(boolean)}),</li>
 * <li>XComboBox handle mouse wheel by default (could be disable or customize using
 * {@link XComboBoxAttribute#NO_MOUSE_WHEEL_LISTENER} and
 * {@link #handleMouseWheelMoved(MouseWheelEvent)},</li>
 * <li>XComboBox handle insertion of <B>new</B> values by default
 * (could be disable or customize using
 * {@link XComboBoxAttribute#NO_DEFAULT_ACTION_LISTENER} and
 * {@link #defaultActionPerformed(ActionEvent)}.</li>
 * <li>XComboBox can control number of entries and limit combo box list to a
 * fixed number of elements {@link #setMaximumItem(int)}, you must consider
 * overwrite {@link #removeOldestItems(int)} to provide your own rules
 * to delete oldest items.</li>
 * </ul>
 *
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
     *
     * @param contentClass
     *            Type of model content
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
     * @param contentClass
     *            Type of model content
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
     * @param contentClass Type of items
     * @param items an array of objects to insert into the combo box
     */
    public XComboBox(
        final Class<E>  contentClass,
        final E[]       items
        )
    {
        this( new DefaultComboBoxModel<E>( items ), contentClass, null );
    }

    /**
     * Creates a XComboBox that contains the elements in the specified Vector.
     * By default the first item in the vector (and therefore the data model)
     * becomes selected.
     *
     * @param contentClass Type of elements
     * @param items an array of vectors to insert into the combo box
     */
    public XComboBox(
        final Class<E>  contentClass,
        @SuppressWarnings("squid:S1149")
        final Vector<E> items
        )
    {
        this( new DefaultComboBoxModel<E>(items), contentClass, null );
    }

    /**
     * Creates a XComboBox that takes its items from an existing ComboBoxModel.
     *
     * @param comboBoxModel
     *            The {@link ComboBoxModel} that provides the displayed
     *            list of items
     * @param contentClass
     *            Type of model content
     * @param attributes
     *            The {@link XComboBoxAttribute} set to configure
     *            the XComboBox
     */
    public XComboBox(
        final ComboBoxModel<E>        comboBoxModel,
        final Class<E>                contentClass,
        final Set<XComboBoxAttribute> attributes
        )
    {
        this.contentClass = contentClass;

        if( comboBoxModel == null ) {
            throw new NullPointerException("defaultComboBoxModel could not be null");
            }

        final EnumSet<XComboBoxAttribute> safeAttributes = getAttributes( attributes );

        super.setEditable( true );
        super.setModel( comboBoxModel );

        if( !safeAttributes.contains( XComboBoxAttribute.NO_MOUSE_WHEEL_LISTENER ) ) {
            super.addMouseWheelListener(
                    event -> handleMouseWheelMoved( event )
                    );
            }

        if( !safeAttributes.contains( XComboBoxAttribute.NO_DEFAULT_ACTION_LISTENER ) ) {
            super.addActionListener(
                    event -> defaultActionPerformed( event )
                    );
            }
    }

    private EnumSet<XComboBoxAttribute> getAttributes(
        final Set<XComboBoxAttribute> attributes
        )
    {
        if( attributes == null ) {
            return EnumSet.noneOf( XComboBoxAttribute.class );
            }
        else {
            return EnumSet.copyOf( attributes );
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
        return this.maximumItem;
    }

    @Override
    @SuppressWarnings("squid:S1066")
    public void intervalAdded( final ListDataEvent e )
    {
        // Track insertion of items
        if( this.maximumItem != -1 ) {
            if( getModel().getSize() > this.maximumItem ) {
                removeOldestItems( getModel().getSize() - this.maximumItem );
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
            final JComboBox<?> jcb = JComboBox.class.cast( s );

            handleMouseWheelMoved( event, jcb );
            }
    }

    private static void handleMouseWheelMoved(
            final MouseWheelEvent event,
            final JComboBox<?>    jcb
            )
    {
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

    /**
     * Handle "comboBoxEdited" action command
     *
     * @param event Current {@link ActionEvent}
     */
    protected void defaultActionPerformed( final ActionEvent event )
    {
        final String cmd = event.getActionCommand();

        if( "comboBoxEdited".equals( cmd ) ) {
            final Object o = this.getSelectedItem();

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
                final E value = this.contentClass.cast( o );

                this.addItem( value );
                }
            }
    }
}
