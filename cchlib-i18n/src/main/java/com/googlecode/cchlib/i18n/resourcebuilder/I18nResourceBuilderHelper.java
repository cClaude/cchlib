package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleNameFactory;

@SuppressWarnings("ucd") // API
public class I18nResourceBuilderHelper
{
    private I18nResourceBuilderHelper()
    {
        // All static
    }

    public static File newOutputFile( final Class<?> referenceType ) throws IOException
    {
        final I18nResourceBundleName rbn
            = I18nResourceBundleNameFactory.newI18nResourceBundleName( referenceType );
        final String filenamePrefix = rbn.getName();

        return newFileFromCurrentDirectory( filenamePrefix );
    }

    public static File newOutputFile( final Package referencePackage ) throws IOException
    {
        final I18nResourceBundleName rbn
            = I18nResourceBundleNameFactory.newI18nResourceBundleName( referencePackage );
        final String filenamePrefix = rbn.getName();

        return newFileFromCurrentDirectory( filenamePrefix );
    }

    private static File getCurrentDirectory() throws IOException
    {
        return new File( "." ).getCanonicalFile();
    }

    private static File newFileFromCurrentDirectory( final String filenamePrefix ) throws IOException
    {
        return new File( getCurrentDirectory(), filenamePrefix + ".properties" );
    }

    public static void fmtAll(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        fmtLocalizedFields( ps, result );
        fmtIgnoredFields( ps, result );
        fmtMissingProperties( ps, result );
        fmtUnusedProperties( ps, result );
    }

    public static void fmtLocalizedFields(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        ps.println( "# localizedFieldMap (key:usageCount) " );

        fmtData( ps, "# localized : ", " : ", result.getLocalizedFields() );

        ps.println( '#' );
    }

    public static void fmtIgnoredFields(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        ps.println( "# ignoredFields (key:usageCount) " );

        fmtData( ps, "# ignored : ", " : ", result.getIgnoredFields() );

        ps.println( '#' );
    }

    public static void fmtMissingProperties(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        ps.println( "# missingProperties (key:usageCount) " );

        fmtData( ps, "", " : ", result.getMissingProperties() );

        ps.println( '#' );
        ps.flush();
    }

    public static void fmtUnusedProperties( final PrintStream ps, final I18nResourceBuilderResult result )
    {
        fmtKeyValue( ps, "unusedProperties", result.getUnusedProperties() );
    }

    private static void fmtKeyValue(
        final PrintStream        ps ,
        final String             name,
        final Map<String,String> keysValues
        )
    {
        ps.println( "# " + name + " (key=value) " );

        fmtData( ps, "", "=", keysValues );

        ps.println( '#' );
    }

    private static void fmtData(
        final PrintStream   ps ,
        final String        linePrefix,
        final String        separator,
        final Map<String,?> keyValues
        )
    {
        for( final Map.Entry<String,?> entry : keyValues.entrySet() ) {
            ps.println( linePrefix + entry.getKey() + separator + entry.getValue() );
        }
    }
}
