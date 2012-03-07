package cx.ath.choisnet.swing;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.Document;
import cx.ath.choisnet.swing.menu.JPopupMenuForJTextField;

/**
 * e<B>X</B>tended <B>TextField</B> is a JTextField with extra features...
 * <p>
 *
 * </P>
 */
public class XTextField extends JTextField
{
    private static final long serialVersionUID = 1L;

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
    public XTextField( String text )
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
    public XTextField( int columns )
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
    public XTextField( String text, int columns )
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
    public XTextField( Document doc, String text, int columns )
    {
        super( doc, text, columns );
        init();
    }

    private void init()
    {
        JPopupMenuForJTextField m = new JPopupMenuForJTextField( XTextField.this )
        {
            @Override
            protected JPopupMenu createContextMenu()
            {
                JPopupMenu contextMenu = new JPopupMenu();

                addCopyMenuItem( contextMenu ); // TODO localization
                addPasteMenuItem( contextMenu );// TODO localization

                return contextMenu;
            }
        };
        m.setMenu();
    }

}
