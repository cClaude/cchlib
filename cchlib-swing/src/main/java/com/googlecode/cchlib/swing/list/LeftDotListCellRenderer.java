package com.googlecode.cchlib.swing.list;

import java.awt.Component;
import java.awt.Container;
import java.awt.FontMetrics;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * TODOC
 */
public class LeftDotListCellRenderer extends DefaultListCellRenderer
{
    private static final long serialVersionUID = 1L;
    private static final String DOTS = "...";
    private Container container;

    /**
     * Create a LeftDotListCellRenderer using JList or JScrollPane
     * to compute width of JList
     *
     * @param jList	JList to customize.
     * @param useParentJScrollPane if true, look for parent JScrollPane
     * that view port is the giving jList to use to compute text size. If
     * false or if JScrollPane is not found use JList to compute that size.
     */
    public LeftDotListCellRenderer(
            final JList<?> jList,
            final boolean  useParentJScrollPane
            )
    {
        if( useParentJScrollPane ) {
            Container c = jList.getParent();

            if( c instanceof JViewport ) {
                c = c.getParent();

                if( c instanceof JScrollPane ) {
                    this.container = c;
                    }
                else {
                    this.container = jList;
                    }
                }
            else {
                this.container = jList;
                }
            }
        else {
            this.container = jList;
            }
    }

    /**
     * Create a LeftDotListCellRenderer using giving JScrollPane to
     * compute size of JList
     *
     * @param jScrollPane JScrollPane to use to compute text size.
     */
    public LeftDotListCellRenderer( final JScrollPane jScrollPane )
    {
        this.container = jScrollPane;
    }

    @Override
    public Component getListCellRendererComponent(
            final JList<?>	list,
            final Object 	value,
            final int 		index,
            final boolean 	isSelected,
            final boolean 	cellHasFocus
            )
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        final int			availableWidth = this.container.getWidth();
        final String 		text 		= getText();
        final FontMetrics 	fm 			= getFontMetrics( getFont() );
        final int   		dotsWidth 	= fm.stringWidth( DOTS );

        if( fm.stringWidth( text ) > availableWidth ) {
            int textWidth 	= dotsWidth;
            int nChars 		= text.length() - 1;

            for(; nChars > 0; nChars--) {
                textWidth += fm.charWidth( text.charAt(nChars) );

                if( textWidth > availableWidth ) {
                    break;
                    }
                }

            setText( DOTS + text.substring(nChars + 1) );
            }

        // Add full text on ToolTip
        setToolTipText( text );

        return this;
    }
}
