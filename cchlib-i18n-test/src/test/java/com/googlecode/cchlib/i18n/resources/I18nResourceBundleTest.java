package com.googlecode.cchlib.i18n.resources;

<<<<<<< HEAD
=======
import static org.fest.assertions.Assertions.assertThat;
import java.io.IOException;
>>>>>>> cchlib-pre4-1-8
import java.util.Locale;
import java.util.ResourceBundle;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
<<<<<<< HEAD
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.io.SerializableHelper;
=======
import com.googlecode.cchlib.i18n.I18NResources;
import com.googlecode.cchlib.i18n.unit.REF;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;
>>>>>>> cchlib-pre4-1-8


/**
 * The class <code>I18nResourceBundleTest</code> contains tests for the class <code>{@link I18nResourceBundle}</code>.
 */
public class I18nResourceBundleTest
{
    private static final Locale LOCALE = Locale.ENGLISH;
    private static final String RESOURCE_BUNDLE_FULL_BASENAME = REF.class.getName();
<<<<<<< HEAD
=======
    private static final String KEY_TEST = "key.test";
>>>>>>> cchlib-pre4-1-8

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

<<<<<<< HEAD
        I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);
=======
        final I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result.getResourceBundle() ).isNotNull();
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testI18nResourceBundle_2()
        throws Exception
    {
        final String resourceBundleFullBaseName = null;
        final Locale locale = LOCALE;

<<<<<<< HEAD
        I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);
=======
        final I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testI18nResourceBundle_3()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final Locale locale = null;

<<<<<<< HEAD
        I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);
=======
        final I18nResourceBundle result = new I18nResourceBundle(resourceBundleFullBaseName, locale);
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
     }

    @Test
    public void testI18nResourceBundle_4()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

<<<<<<< HEAD
        I18nResourceBundle result = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );
=======
        final I18nResourceBundle result = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
    }

    @Test
    public void testGetResourceBundle()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

<<<<<<< HEAD
        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        ResourceBundle result = fixture.getResourceBundle();
=======
        final I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        final ResourceBundle result = fixture.getResourceBundle();
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( resourceBundle );
    }

    @Test
    public void testGetResourceBundleFullBaseName_1()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

<<<<<<< HEAD
        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        String result = fixture.getResourceBundleFullBaseName();
=======
        final I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        final String result = fixture.getResourceBundleFullBaseName();
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( RESOURCE_BUNDLE_FULL_BASENAME );
    }

    @Test
    public void testGetString_1()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

<<<<<<< HEAD
        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        String result = fixture.getString( KEY );
=======
        final I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        final String result = fixture.getString( KEY );
>>>>>>> cchlib-pre4-1-8

        Assertions.assertThat( result ).isNotNull();
        Assertions.assertThat( result ).isEqualTo( VALUE );
    }

    @Test(expected = com.googlecode.cchlib.i18n.resources.MissingResourceException.class)
    public void testGetString_2()
        throws Exception
    {
        final String resourceBundleFullBaseName = RESOURCE_BUNDLE_FULL_BASENAME;
        final ResourceBundle resourceBundle = newResourceBundle( resourceBundleFullBaseName, LOCALE );

<<<<<<< HEAD
        I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        String result = fixture.getString( BAD_KEY );
=======
        final I18nResourceBundle fixture = new I18nResourceBundle( resourceBundle, resourceBundleFullBaseName );

        final String result = fixture.getString( BAD_KEY );
>>>>>>> cchlib-pre4-1-8

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

<<<<<<< HEAD
=======
    @Test
    public void testSerilization1() throws ClassNotFoundException, IOException, MissingResourceException
    {
        final String    resourceBundleBaseName  = I18NResources.class.getName();
        final Locale    localeEn                = Locale.ENGLISH;

        final ResourceBundle  resourceBundle = ResourceBundle.getBundle( resourceBundleBaseName, localeEn );

        // Prepare object to test
        final I18nResourceBundle irb = new I18nResourceBundle( resourceBundle, resourceBundleBaseName );

        // Launch serialization
        final I18nResourceBundle copy = SerializableTestCaseHelper.cloneOverSerialization( irb );

        assertThat( copy.getString( KEY_TEST ) ).isEqualTo( irb.getString( KEY_TEST ) );

    }

>>>>>>> cchlib-pre4-1-8
}
