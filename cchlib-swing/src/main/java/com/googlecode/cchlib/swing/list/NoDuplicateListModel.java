package com.googlecode.cchlib.swing.list;

import javax.swing.DefaultListModel;

/**
 * TODOC
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
     * TODOC
     */
    private boolean removeIfExist( E element )
    {
        int index = indexOf( element );
        
        if( index >=0 ) {
            remove( index );
            return true;
            }
        
        return false;
    }
    
    /**
     * TODOC
     */
    @Override
    public void add(int index, E element)
    {
        removeIfExist( element );
        super.add( index, element );
    }
    
    /**
     * TODOC
     */
    @Override
    public void addElement(E element)
    {
        removeIfExist( element );
        super.addElement( element );
    }
    
    /**
     * TODOC
     */
    @Override
    public E set(int index, E element)
    {
        removeIfExist( element );
        return super.set( index, element );
    }
    
    /**
     * TODOC
     */
    @Override
    public void setElementAt(E element, int index)
    {
        removeIfExist( element );
        super.setElementAt( element, index );
    }
}
