package com.googlecode.cchlib.swing.textfield;

import java.util.Locale;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.swing.i18n.SwingAutoI18nCoreFactory;

/**
 * The class {@link XTextFieldTest} contains tests for the class
 * {@link XTextField}
 */
public class XTextFieldTest
{
    @Test
    public void testNoI18n()
    {
        final XTextField xTextField = new XTextField("test only");

        final String copyText = xTextField.getTextForCopy();
        Assert.assertEquals( "Copy", copyText );

        final String pasteText = xTextField.getTextForPaste();
        Assert.assertEquals( "Paste", pasteText );
    }

    @Test
    public void testI18n_ENGLISH()
    {
        final XTextField xTextField = new XTextField("test only");

        Locale.setDefault( Locale.ENGLISH );
        final AutoI18nCore autoI18n = SwingAutoI18nCoreFactory.getCurrentSwingAutoI18nCore();
        xTextField.performeI18n( autoI18n );

        final String copyText = xTextField.getTextForCopy();
        Assert.assertEquals( "Copy", copyText );

        final String pasteText = xTextField.getTextForPaste();
        Assert.assertEquals( "Paste", pasteText );
    }

    @Test
    public void testI18n_FRENCH()
    {
        final XTextField xTextField = new XTextField("test only");

        Locale.setDefault( Locale.FRENCH );
        final AutoI18nCore autoI18n = SwingAutoI18nCoreFactory.getCurrentSwingAutoI18nCore();
        xTextField.performeI18n( autoI18n );

        final String copyText = xTextField.getTextForCopy();
        Assert.assertEquals( "Copier", copyText );

        final String pasteText = xTextField.getTextForPaste();
        Assert.assertEquals( "Coller", pasteText );
    }
}
