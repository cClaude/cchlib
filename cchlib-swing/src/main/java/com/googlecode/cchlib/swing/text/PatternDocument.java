package com.googlecode.cchlib.swing.text;

import java.awt.Color;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import org.apache.log4j.Logger;

/**
 * PatternDocument is simple {@link javax.swing.text.Document}
 * container for text that need to be edit {@link java.util.regex.Pattern}.
 * <p>
 * This document is base on a {@link JTextField} and
 * change background color of this component if current
 * text is not a valid {@link java.util.regex.Pattern}.
 * </p>
 */
public class PatternDocument extends PlainDocument
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( PatternDocument.class );

    /** @serial */
    private JTextField jtf;
    /** @serial */
    private Color errorColor ;
    /** @serial */
    private Color defaultColor;

    /**
     * Create a PatternDocument
     *
     * @param jTextField editor
     * @param errorColor background color when an current text
     *                   is not a valid Regular Expression
     */
    public PatternDocument(
            JTextField  jTextField,
            Color       errorColor
            )
    {
        this.jtf = jTextField;
        this.errorColor = errorColor;
        //TODO: add listener on LookAndField to invalidate field !
        // create a new JTextField and get color!
        this.defaultColor = jTextField.getBackground();
    }

    /**
     * Define background color when an current text is
     * <B>not</B> a valid Regular Expression
     *
     * @param errorColor background color when an current text
     * is <B>not</B> a valid Regular Expression
     */
    public void setErrorBackgoundColor( Color errorColor )
    {
        this.errorColor = errorColor;
        check(); // Update color
    }

    /**
     * Returns background color when an current text is
     * <B>not</B> a valid Regular Expression
     *
     * @return background color when an current text
     * is <B>not</B> a valid Regular Expression
     */
    public Color getErrorBackgoundColor()
    {
        return this.errorColor;
    }

    /**
     * Define background color when an current text is
     * a valid Regular Expression
     *
     * @param defaultColor background color when an current text
     * is a valid Regular Expression
     */
    public void setDefaultBackgoundColor( Color defaultColor )
    {
        this.defaultColor = defaultColor;
        check(); // Update color
    }

    /**
     * Returns background color when an current text is
     * a valid Regular Expression
     *
     * @return background color when an current text
     * is a valid Regular Expression
     */
    public Color getDefaultBackgoundColor()
    {
        return this.defaultColor;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException
    {
        super.insertString(offs, str, a);
        check();
    }

    @Override
    public void remove(int offs, int len)
        throws BadLocationException
    {
        super.remove(offs, len);
        check();
    }

    private void check()
    {
        try {
            Pattern.compile( jtf.getText() );
            jtf.setBackground( defaultColor );
            }
        catch(PatternSyntaxException e) {
            jtf.setBackground( errorColor );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Bad partern: " + jtf.getText(), e );
                }
            }
        catch(Exception e) {
            jtf.setBackground( errorColor );

            LOGGER.warn( "check error", e );
            }
    }

}
