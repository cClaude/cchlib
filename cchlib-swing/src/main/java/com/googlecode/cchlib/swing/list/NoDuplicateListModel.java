package com.googlecode.cchlib.swing.list;

import javax.swing.DefaultListModel;

/**
 * NEEDDOC
 */
public class NoDuplicateListModel<E> extends DefaultListModel<E>
{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public NoDuplicateListModel()
    {
    }

    /**
     * NEEDDOC
     */
    private boolean removeIfExist( final E element )
    {
        final int index = indexOf( element );

        if( index >=0 ) {
            remove( index );
            return true;
            }

        return false;
    }

    /**
     * NEEDDOC
     */
    @Override
    public void add(final int index, final E element)
    {
        removeIfExist( element );
        super.add( index, element );
    }

    /**
     * NEEDDOC
     */
    @Override
    public void addElement(final E element)
    {
        removeIfExist( element );
        super.addElement( element );
    }

    /**
     * NEEDDOC
     */
    @Override
    public E set(final int index, final E element)
    {
        removeIfExist( element );
        return super.set( index, element );
    }

    /**
     * NEEDDOC
     */
    @Override
    public void setElementAt(final E element, final int index)
    {
        removeIfExist( element );
        super.setElementAt( element, index );
    }
}
