package com.googlecode.cchlib.swing.textfield;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.swing.SafeSwingUtilities;
import com.googlecode.cchlib.swing.i18n.SwingAutoI18nFactory;

/**
 * The class {@link XTextFieldTest} contains tests for the class
 * {@link XTextField}
 */
public class XTextFieldTest
{
    @Before
    public void setUp()
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );
    }

    @Test
    public void testNoI18n()
    {
        final XTextField xTextField = new XTextField( "test only" );

        final String copyText = xTextField.getTextForCopy();

        assertThat( copyText ).as( "Default getTextForCopy() is wrong" ).isEqualTo( "Copy" );

        final String pasteText = xTextField.getTextForPaste();

        assertThat( pasteText ).as( "Default getTextForPaste() is wrong" ).isEqualTo( "Paste" );
    }

    @Test
    public void testI18n_ENGLISH()
    {
        final XTextField xTextField = new XTextField( "test only" );

        Locale.setDefault( Locale.ENGLISH );

        final AutoI18n autoI18n = SwingAutoI18nFactory.getCurrentSwingAutoI18n();
        xTextField.performeI18n( autoI18n );

        final String copyText = xTextField.getTextForCopy();
        assertThat( copyText ).as( "Default getTextForCopy() for ENGLISH is wrong" ).isEqualTo( "Copy" );

        final String pasteText = xTextField.getTextForPaste();
        assertThat( pasteText ).as( "Default getTextForPaste() for ENGLISH is wrong" ).isEqualTo( "Paste" );
    }

    @Test
    public void testI18n_FRENCH()
    {
        final XTextField xTextField = new XTextField( "test only" );

        Locale.setDefault( Locale.FRENCH );

        final AutoI18n autoI18n = SwingAutoI18nFactory.getCurrentSwingAutoI18n();
        xTextField.performeI18n( autoI18n );

        final String copyText = xTextField.getTextForCopy();
        assertThat( copyText ).as( "Default getTextForCopy() for FRENCH is wrong" ).isEqualTo( "Copier" );

        final String pasteText = xTextField.getTextForPaste();
        assertThat( pasteText ).as( "Default getTextForPaste() for FRENCH is wrong" ).isEqualTo( "Coller" );
    }
}
