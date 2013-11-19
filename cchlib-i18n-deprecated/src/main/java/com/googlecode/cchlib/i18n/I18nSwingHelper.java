package com.googlecode.cchlib.i18n;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Provide some tools for internationalization of swings components.
 */
public final class I18nSwingHelper 
{
    private I18nSwingHelper()
    {//All static
    }

    private static final TitledBorder getTitledBorder(JComponent c)
    {
        Border b = c.getBorder();
        
        if( b instanceof TitledBorder ) {
            return TitledBorder.class.cast( b );
            }
        return null;
    }

    /**
     * Set title on TitledBorder of giving JComponent.
     * <p>
     * Retrieve {@link Border} of JComponent, if there
     * is a {@link TitledBorder} set on it set new title.
     * </p>
     * @param c JComponent to find TitledBorder
     * @param title new title to set
     */
    public static final void setTitledBorderTitle(
        final JComponent    c, 
        final String        title
        )
    {
        TitledBorder b = getTitledBorder(c);
        
        if( b != null ) {
            b.setTitle( title );
            }
    }
    /**
     * Get title on TitledBorder of giving JComponent.
     * <p>
     * Retrieve {@link Border} of JComponent, if there
     * is a {@link TitledBorder} set on it set new title.
     * </p>
     * @param c JComponent to find TitledBorder
     * @return current title of TitledBorder, null if
     * no Border or not a TitledBorder.
     */
    public static final String getTitledBorderTitle(JComponent c)
    {
        TitledBorder b = getTitledBorder( c );
        
        if( b != null ) {
            return b.getTitle();
            }
        return null;
    }
}
