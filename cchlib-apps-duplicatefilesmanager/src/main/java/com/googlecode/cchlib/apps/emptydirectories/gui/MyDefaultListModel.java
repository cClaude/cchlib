package com.googlecode.cchlib.apps.emptydirectories.gui;

import java.util.Enumeration;
import javax.swing.DefaultListModel;
import com.googlecode.cchlib.lang.Enumerable;

/**
 * @see DefaultListModel
 * @see Enumerable
 */
public class MyDefaultListModel<E>
    extends DefaultListModel<E> 
        implements Enumerable<E> 
{
    private static final long serialVersionUID = 1L;

    /**
     * @see DefaultListModel
     */
    public MyDefaultListModel()
    {
        super();
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.lang.Enumerable#enumeration()
     */
    @Override
    public Enumeration<E> enumeration()
    {
        return elements();
    }

}
