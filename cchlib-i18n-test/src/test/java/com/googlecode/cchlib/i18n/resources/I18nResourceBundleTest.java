package com.googlecode.cchlib.i18n.resources;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.Test;
import com.googlecode.cchlib.i18n.I18NResources;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;

/**
 * The class I18nResourceBundleTest contains tests for the class
 * {@link I18nResourceBundle}.
 */
public class I18nResourceBundleTest
{
    private static final Locale LOCALE    = Locale.ENGLISH;
    private static final Locale LOCALE_FR = Locale.FRENCH;

    private static final String RESOURCE_BUNDLE_FULL_BASENAME = REF.class.getName();

    private static final String TEST_KEY                      = "key.test";
    private static final String TEST_VALUE                    = "value.test.for.I18nResourceBundleTest";
    private static final String TEST_VALUE_FR                 = "value.test.for.I18nResourceBundleTest(FR)";

    private static final String A_KEY   = "not.use.1";
    private static final String A_VALUE = "should be not use and not remove !";

    private static final String BAD_KEY = "xxxx";

    private ResourceBundle newResourceBundle(
        final String resourceBundleFullBaseName,
        final Locale locale
        )
    {
        return ResourceBundle.getBundle( resourceBundleFullBaseName, locale );
    }

    @Test
    public void testI18nResourceBundle_1() throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final Locale locale                     = LOCALE;

        final I18nResourceBundle result = new I18nResourceBundle( resourceBundleFullBaseName, locale );

        assertThat( result ).isNotNull();
        assertThat( result.getResourceBundle() ).isNotNull();

        assertThat( result.getResourceBundle().getLocale() ).isEqualTo( LOCALE );

        // Last test could be false if there is no file REF_en.properties,
        // in that case ResourceBundle use fall back, which is REF.properties
        // and for this file have no LOCALE !
        // If so you should use :
        // assertThat( result.getResourceBundle().getLocale() ).isEqualTo( Locale.ROOT );
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testI18nResourceBundle_2() throws Exception
    {
        final String resourceBundleFullBaseName = null;
        final Locale locale                     = LOCALE;

        final I18nResourceBundle result = new I18nResourceBundle( resourceBundleFullBaseName, locale );

        assertThat( result ).isNotNull();
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testI18nResourceBundle_3() throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final Locale locale                     = null;

        final I18nResourceBundle result = new I18nResourceBundle( resourceBundleFullBaseName, locale );

        assertThat( result ).isNotNull();
     }

    @Test
    public void testI18nResourceBundle_4() throws Exception
    {
        final I18nResourceBundle result = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );

        assertThat( result ).isNotNull();
    }

    @Test
    public void testGetResourceBundle()
        throws Exception
    {
        final ResourceBundle     resourceBundle = newResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );
        final I18nResourceBundle fixture        = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );

        final ResourceBundle result = fixture.getResourceBundle();

        assertThat( result ).isNotNull();
        assertThat( result ).isEqualTo( resourceBundle );
    }

    @Test
    public void testGetResourceBundleFullBaseName_1() throws Exception
    {
        final I18nResourceBundle fixture = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );

        final String result = fixture.getResourceBundleFullBaseName();

        assertThat( result ).isNotNull();
        assertThat( result ).isEqualTo( RESOURCE_BUNDLE_FULL_BASENAME );
    }

    @Test
    public void testGetString_1() throws Exception
    {
        final I18nResourceBundle fixture = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );
        final String             result  = fixture.getString( TEST_KEY );

        assertThat( result ).isNotNull();
        assertThat( result ).isEqualTo( TEST_VALUE );
    }

    @Test(expected = com.googlecode.cchlib.i18n.resources.MissingResourceException.class)
    public void testGetString_2() throws Exception
    {
        final I18nResourceBundle fixture = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );

        final String result = fixture.getString( BAD_KEY );

        assertThat( result ).isNotNull();
    }

    @Test
    //@Ignore
    public void testSetResourceBundle_1() throws Exception
    {
        final I18nResourceBundle fixture = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );
        final String result1 = fixture.getString( TEST_KEY );

        assertThat( result1 ).isNotNull();
        assertThat( result1 ).isEqualTo( TEST_VALUE );

//        assertThat( result ).isEqualTo( VALUE );
//
//        assertThat( result3 ).isNotNull();
//        assertThat( result3 ).isEqualTo( TEST_VALUE );
//

        fixture.setResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE_FR );

        final String result2 = fixture.getString( TEST_KEY );

        assertThat( result2 ).isNotNull();
        assertThat( result2 ).isEqualTo( TEST_VALUE_FR );
    }

    @Test
    public void testSerializable1() throws Exception
    {
        final I18nResourceBundle i18nResourceBundle = new I18nResourceBundle( RESOURCE_BUNDLE_FULL_BASENAME, LOCALE );
        final I18nResourceBundle fixture            = SerializableHelper.clone( i18nResourceBundle, I18nResourceBundle.class );

        final String result1 = fixture.getString( TEST_KEY );

        assertThat( result1 ).isNotNull();
        assertThat( result1 ).isEqualTo( TEST_VALUE );

        final String result2 = fixture.getString(  A_KEY );

        assertThat( result2 ).isNotNull();
        assertThat( result2 ).isEqualTo( A_VALUE );
    }

    @Test
    public void testSerializable2() throws Exception
    {
        // Use I18NResources.properties
        final String    resourceBundleBaseName  = I18NResources.class.getName();
        final Locale    localeEn                = Locale.ENGLISH;

        // Prepare object to test
        final I18nResourceBundle original = new I18nResourceBundle( resourceBundleBaseName, localeEn );

        // Launch serialization
        final I18nResourceBundle copy = SerializableTestCaseHelper.cloneOverSerialization( original );

        assertThat( copy.getString( TEST_KEY ) ).isEqualTo( original.getString( TEST_KEY ) );
    }

}
