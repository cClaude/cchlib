package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
import com.googlecode.cchlib.swing.XComboBoxPattern;

/**
 *
 */
public class XComboBoxPatternBuilder implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Color 				errorColor	= null;
    private ArrayList<String> 	regExpList	= new ArrayList<String>();

    /**
     * @param errorColor
     * @param regExps
     */
    public XComboBoxPatternBuilder()
    {
        // empty
    }

    /**
     *
     * @param errorColor
     * @return
     */
    public XComboBoxPatternBuilder setErrorColor(
        final Color errorColor
        )
    {
        this.errorColor = errorColor;
        return this;
    }

    /**
     *
     * @param regExps
     * @return
     */
    public XComboBoxPatternBuilder add(
        final String...regExps
        )
    {
        for( String s : regExps ) {
            this.regExpList.add( s );
            }

        return this;
    }

    /**
     *
     * @param regExpCollection
     * @return
     */
    public XComboBoxPatternBuilder add(
        final Collection<String> regExpCollection
        )
    {
        this.regExpList.addAll( regExpCollection );
        return this;
    }

    /**
     *
     * @param pattern
     * @return
     */
    public XComboBoxPatternBuilder add( final Pattern pattern )
    {
        final String regExp = pattern.pattern();
        this.regExpList.add( regExp );
        return this;
    }

    /**
     *
     * @param patternCollection
     * @return
     */
    public XComboBoxPatternBuilder addPatternCollection(
        final Collection<Pattern> patternCollection
        )
    {
        for( Pattern p : patternCollection ) {
            this.regExpList.add( p.pattern() );
            }
        return this;
    }

    /**
     *
     * @return
     */
    public XComboBoxPattern createXComboBoxPattern()
    {
        final XComboBoxPattern xcbp = new XComboBoxPattern();

        if( errorColor != null ) {
            xcbp.setErrorBackGroundColor( errorColor );
            }

        for( String regExp : regExpList ) {
            xcbp.addItem( regExp );
            }

        return xcbp;
    }
}
