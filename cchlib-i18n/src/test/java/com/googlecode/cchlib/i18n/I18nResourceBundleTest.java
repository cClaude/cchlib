package com.googlecode.cchlib.i18n;

import static org.fest.assertions.Assertions.assertThat;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundle;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;

/**
 * Serialization tests for I18nResourceBundle
 */
public class I18nResourceBundleTest
{
    private static final String KEY_TEST = "key.test";

    @Test
    public void testSerilization1() throws ClassNotFoundException, IOException
    {
        final String    resourceBundleBaseName  = I18nResourceBundleTest.class.getName();
        final Locale    localeEn                = Locale.ENGLISH;

        final ResourceBundle  resourceBundle = ResourceBundle.getBundle( resourceBundleBaseName, localeEn );

        // Prepare object to test
        I18nResourceBundle irb = new I18nResourceBundle( resourceBundle, resourceBundleBaseName );

        // Launch serialization
        I18nResourceBundle copy = SerializableTestCaseHelper.cloneOverSerialization( irb );

        assertThat( copy.getString( KEY_TEST ) ).isEqualTo( irb.getString( KEY_TEST ) );

    }
}
