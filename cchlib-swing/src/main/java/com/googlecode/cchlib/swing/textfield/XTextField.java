package com.googlecode.cchlib.swing.textfield;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.Document;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

/**
 * e<B>X</B>tended <B>TextField</B> is a JTextField with extra features...
 * <ul>
 * <li>Copy/Paste support using context menu</li>
 * </ul>
 */
public class XTextField extends JTextField implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;
    @I18nString private String copyTxt  = "Copy";
    @I18nString private String pasteTxt = "Paste";

    /**
     * Constructs a new TextField. A default model is created,
     * the initial string is null, and the number of columns is set to 0.
     */
    public XTextField()
    {
        init();
    }

    /**
     * Constructs a new TextField initialized with the specified text.
     * A default model is created and the number of columns is 0.
     *
     * @param text the initial string to display, or null
     */
    public XTextField( final String text )
    {
        super( text );

        init();
    }

    /**
     * Constructs a new empty TextField with the specified number of columns.
     * A default model is created and the initial string is set to null.
     *
     * @param columns the number of columns to use to calculate the preferred
     *             width >= 0; if columns is set to zero, the preferred width
     *             will be whatever naturally results from the component
     *             implementation
     */
    public XTextField( final int columns )
    {
        super( columns );

        init();
    }

    /**
     * Constructs a new TextField initialized with the specified text and columns.
     * A default model is created.
     *
     * @param text the initial string to display, or null
     * @param columns the number of columns to use to calculate the preferred
     *             width >= 0; if columns is set to zero, the preferred width
     *             will be whatever naturally results from the component
     *             implementation
     */
    public XTextField( final String text, int columns )
    {
        super( text, columns );

        init();
    }

    /**
     * Constructs a new TextField that uses the given text storage model
     * and the given number of columns. This is the constructor through which
     * the other constructors feed. If the document is null,
     * a default model is created.
     *
     * @param doc the text storage to use; if this is null, a default will
     *            be provided by calling the createDefaultModel method
     * @param text the initial string to display, or null
     * @param columns the number of columns to use to calculate the preferred
     *             width >= 0; if columns is set to zero, the preferred width
     *             will be whatever naturally results from the component
     *             implementation
     * @throws IllegalArgumentException if columns < 0
     */
    public XTextField( final Document doc, String text, int columns )
    {
        super( doc, text, columns );

        init();
    }

    private void init()
    {
        new JPopupMenuForJTextField( XTextField.this )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected JPopupMenu createContextMenu()
            {
                JPopupMenu contextMenu = new JPopupMenu();

                addCopyMenuItem( contextMenu, getTextForCopy() );
                addPasteMenuItem( contextMenu,getTextForPaste() );

                return contextMenu;
            }
        }.addMenu();
    }

    /**
     * TODO default localization
     * @return default text for copy (no localization)
     */
    public String getTextForCopy()
    {
        return copyTxt;
    }

    /**
     * TODO default localization
     * @return default text for paste (no localization)
     */
    public String getTextForPaste()
    {
        return pasteTxt ;
    }

    @Override
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

}
