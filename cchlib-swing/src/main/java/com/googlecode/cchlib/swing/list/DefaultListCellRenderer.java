package com.googlecode.cchlib.swing.list;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;

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
     * An empty <code>Border</code>. This field might not be used. To change the
     * <code>Border</code> used by this renderer override the
     * <code>getListCellRendererComponent</code> method and set the border
     * of the returned component directly.
     */
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

    /**
     * Constructs a default renderer object for an item
     * in a list.
     */
    public DefaultListCellRenderer() {
        super();
        setOpaque(true);
        setBorder(getNoFocusBorder());
        setName("List.cellRenderer");
    }

    private Border getNoFocusBorder() {
        Border border = getBorder( "List.cellNoFocusBorder" );
        
        if (System.getSecurityManager() != null) {
            if (border != null) return border;
            return SAFE_NO_FOCUS_BORDER;
        } else {
            if (border != null &&
                    (noFocusBorder == null ||
                    noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) {
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
        boolean                  isSelected,
        final boolean            cellHasFocus
        )
    {
        setComponentOrientation(list.getComponentOrientation());

        Color bg = null;
        Color fg = null;

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

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
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     * @return <code>true</code> if the background is completely opaque
     *         and differs from the JList's background;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean isOpaque() {
        Color back = getBackground();
        Component p = getParent();
        if (p != null) {
            p = p.getParent();
        }
        // p should now be the JList.
        boolean colorMatch = (back != null) && (p != null) &&
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
    public void validate() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    *
    * @since 1.5
    */
    @Override
    public void invalidate() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    *
    * @since 1.5
    */
    @Override
    public void repaint() {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void revalidate() {}
   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void repaint(long tm, int x, int y, int width, int height) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void repaint(Rectangle r) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Strings get interned...
        if (propertyName.equals( "text" )
                || ((propertyName.equals( "font" ) || propertyName.equals( "foreground" ))
                    && oldValue != newValue
                    && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, char oldValue, char newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, short oldValue, short newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, long oldValue, long newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, float oldValue, float newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, double oldValue, double newValue) {}

   /**
    * Overridden for performance reasons.
    * See the <a href="#override">Implementation Note</a>
    * for more information.
    */
    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}

    /**
     * A subclass of DefaultListCellRenderer that implements UIResource.
     * DefaultListCellRenderer doesn't implement UIResource
     * directly so that applications can safely override the
     * cellRenderer property with DefaultListCellRenderer subclasses.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     */
    public static class UIResource extends DefaultListCellRenderer<Object>
        implements javax.swing.plaf.UIResource
    {
        private static final long serialVersionUID = 1L;
    }

    private Border getBorder( String key )
    {
        //return sun.swing.DefaultLookup.getBorder( this, ui, key );
        return UIManager.getBorder( key );
    }

    private Color getColor( String key )
    {
        //return sun.swing.DefaultLookup.getColor( this, ui, key );
        return UIManager.getColor( key );
    }
}
