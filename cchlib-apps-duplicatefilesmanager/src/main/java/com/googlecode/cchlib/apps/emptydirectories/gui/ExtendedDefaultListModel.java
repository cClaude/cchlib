package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.util.Enumeration;
import javax.swing.DefaultListModel;
import com.googlecode.cchlib.lang.Enumerable;

/**
 * @see DefaultListModel
 * @see Enumerable
 */
public class ExtendedDefaultListModel<E>
    extends DefaultListModel<E>
        implements Enumerable<E>
{
    private static final long serialVersionUID = 1L;

    /**
     * @see DefaultListModel
     */
    public ExtendedDefaultListModel()
    {
        super();
    }

    @Override
    public Enumeration<E> enumeration()
    {
        return elements();
    }

    /**
     * Adds the specified component to the end of this list if this element
     * is not already present.
     */
    @Override
    public void addElement( final E element )
    {
        if( indexOf( element ) == -1 ) {
            super.addElement( element );
            }
    }
}
