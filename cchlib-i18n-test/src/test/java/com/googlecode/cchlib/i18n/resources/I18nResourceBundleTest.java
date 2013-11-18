package com.googlecode.cchlib.i18n.resources;

import java.util.Locale;
import java.util.ResourceBundle;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.io.SerializableHelper;


/**
 * The class <code>I18nResourceBundleTest</code> contains tests for the class <code>{@link I18nResourceBundle}</code>.
 */
public class I18nResourceBundleTest
{
    private static final Locale LOCALE = Locale.ENGLISH;
    private static final String RESOURCE_BUNDLE_FULL_BASENAME = REF.class.getName();

    private static final String KEY = "not.use.1";
    private static final String VALUE = "should be not use and not remove !";

    private static final String BAD_KEY = "xxxx";

    private ResourceBundle newResourceBundle(
        final String resourceBundleFullBaseName,
        final Locale locale )
    {
        return   ResourceBundle.getBundle( resourceBundleFullBaseName, locale );
    }

    @Test
    public void testI18nResourceBundle_1()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final Locale locale = LOCALE;

        I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getResourceBundle() ).isNotNull();
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testI18nResourceBundle_2()
        throws Exception
    {
        final String resourceBundleFullBaseName = null;
        final Locale locale = LOCALE;

        I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);

        Assertions.assertThat( result ).isNotNull();
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testI18nResourceBundle_3()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final Locale locale = null;

        I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);

        Assertions.assertThat( result ).isNotNull();
     }

    @Test
    public void testI18nResourceBundle_4()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

        I18nResourceBundle result = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        Assertions.assertThat( result ).isNotNull();
    }

    @Test
    public void testGetResourceBundle()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        ResourceBundle result = fixture.getResourceBundle();

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( resourceBundle );
    }

    @Test
    public void testGetResourceBundleFullBaseName_1()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        String result = fixture.getResourceBundleFullBaseName();

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( RESOURCE_BUNDLE_FULL_BASENAME );
    }

    @Test
    public void testGetString_1()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        String result = fixture.getString( KEY );

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( VALUE );
    }

    @Test(expected = com.googlecode.cchlib.i18n.resources.MissingResourceException.class)
    public void testGetString_2()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        String result = fixture.getString( BAD_KEY );

        Assertions.assertThat( result ).isNotNull();
    }

    @Test
    @Ignore
    public void testSetResourceBundle_1()
        throws Exception
    {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle( "", (Locale)null );
        final String resourceBundleFullBaseName = "";

        final I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        fixture.setResourceBundle(newResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE ), resourceBundleFullBaseName);

        ////
    }

    @Test
    public void testSerializable()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

        final I18nResourceBundle i18nResourceBundle = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        final I18nResourceBundle fixture = SerializableHelper.clone( i18nResourceBundle, I18nResourceBundle.class );

        final String result = fixture.getString( KEY );

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( VALUE );
    }

}
