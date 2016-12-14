package com.googlecode.cchlib.swing.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Renders an item in a list. Base on original code from Oracle but that support generic
 *
 * @see javax.swing.DefaultListCellRenderer
 * @since 4.1.7
 */
public class DefaultListCellRenderer<E> extends JLabel
    implements ListCellRenderer<E>, Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * An empty {@link Border}. This field might not be used. To change the
     * {@link Border} used by this renderer override the
     * {@link  #getListCellRendererComponent(JList, Object, int, boolean, boolean)}7
     * method and set the border of the returned component directly.
     */
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

    /**
     * Constructs a default renderer object for an item
     * in a list.
     */
    public DefaultListCellRenderer()
    {
        setOpaque(true);
        setBorder(getNoFocusBorder());
        setName("List.cellRenderer");
    }

    private Border getNoFocusBorder() {
        final Border border = getBorder( "List.cellNoFocusBorder" );

        if (System.getSecurityManager() != null) {
            if (border != null) {
                return border;
            }
            return SAFE_NO_FOCUS_BORDER;
        } else {
            if ((border != null) &&
                    ((noFocusBorder == null) ||
                    (noFocusBorder == DEFAULT_NO_FOCUS_BORDER))) {
                return border;
            }
            return noFocusBorder;
        }
    }

    @Override
    public Component getListCellRendererComponent(
        final JList<? extends E> list,
        final E                  value,
        final int                index,
        final boolean            isSelectedValue,
        final boolean            cellHasFocus
        )
    {
        boolean isSelected = isSelectedValue;

        setComponentOrientation(list.getComponentOrientation());

        Color bg = null;
        Color fg = null;

        final JList.DropLocation dropLocation = list.getDropLocation();
        if ((dropLocation != null)
                && !dropLocation.isInsert()
                && (dropLocation.getIndex() == index)) {

            bg = getColor("List.dropCellBackground");
            fg = getColor("List.dropCellForeground");

            isSelected = true;
        }

        if (isSelected) {
            setBackground(bg == null ? list.getSelectionBackground() : bg);
            setForeground(fg == null ? list.getSelectionForeground() : fg);
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (value instanceof Icon) {
            setIcon((Icon)value);
            setText("");
        }
        else {
            setIcon(null);
            setText((value == null) ? "" : value.toString());
        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());

        Border border = null;
        if (cellHasFocus) {
            if (isSelected) {
                border = getBorder( "List.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = getBorder( "List.focusCellHighlightBorder");
            }
        } else {
            border = getNoFocusBorder();
        }
        setBorder(border);

        return this;
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a> for more information.
     *
     * @return {@code true} if the background is completely opaque and differs
     *         from the JList's background; {@code false} otherwise
     * @since 1.5
     */
    @Override
    public boolean isOpaque() {
        final Color back = getBackground();
        Component p = getParent();
        if (p != null) {
            p = p.getParent();
        }
        // p should now be the JList.
        final boolean colorMatch = (back != null) && (p != null) &&
            back.equals(p.getBackground()) &&
                        p.isOpaque();
        return !colorMatch && super.isOpaque();
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void validate() { /* empty */ }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    *
    * @since 1.5
    */
    @Override
    public void invalidate() {/* empty */}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    *
    * @since 1.5
    */
    @Override
    public void repaint() {/* empty */}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void revalidate() {/* empty */}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void repaint(final long tm, final int x, final int y, final int width, final int height) {/* empty */}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void repaint(final Rectangle r) {/* empty */}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    protected void firePropertyChange(
        final String propertyName,
        final Object oldValue,
        final Object newValue
        )
    {
        // Strings get interned...
        if( updateContent( propertyName, oldValue, newValue ) ) {
            super.firePropertyChange( propertyName, oldValue, newValue );
        }
    }

    private boolean updateContent( final String propertyName, final Object oldValue, final Object newValue )
    {
        return "text".equals( propertyName )
                || (("font".equals( propertyName ) || "foreground".equals( propertyName ))
                    && (oldValue != newValue)
                    && (getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null));
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
            final String propertyName,
            final byte oldValue,
            final byte newValue
            )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final char oldValue,
        final char newValue
        )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final short oldValue,
        final short newValue
        )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final int oldValue,
        final int newValue
        )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final long oldValue,
        final long newValue
        )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final float oldValue,
        final float newValue
        )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final double oldValue,
        final double newValue
        )
    {
        /* empty */
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(
        final String propertyName,
        final boolean oldValue,
        final boolean newValue
        )
    {
        /* empty */
    }

    /**
     * A subclass of DefaultListCellRenderer that implements UIResource.
     * DefaultListCellRenderer doesn't implement UIResource directly so
     * that applications can safely override the cellRenderer property with
     * DefaultListCellRenderer subclasses.
     * <p>
     * <strong>Warning:</strong> Serialized objects of this class will not be
     * compatible with future Swing releases. The current serialization support
     * is appropriate for short term storage or RMI between applications running
     * the same version of Swing. As of 1.4, support for long term storage of
     * all JavaBeans<sup>TM</sup> has been added to the
     * {@code java.beans} package. Please see {@link java.beans.XMLEncoder}.
     */
    @SuppressWarnings({
        "squid:MaximumInheritanceDepth", // Swing
        "squid:S2176" // Hide parent class
        })
    public static class UIResource extends DefaultListCellRenderer<Object>
        implements javax.swing.plaf.UIResource
    {
        private static final long serialVersionUID = 1L;
    }

    private Border getBorder( final String key )
    {
        return UIManager.getBorder( key );
    }

    private Color getColor( final String key )
    {
        return UIManager.getColor( key );
    }
}
