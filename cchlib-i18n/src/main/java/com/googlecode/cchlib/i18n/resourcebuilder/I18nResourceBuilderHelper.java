package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.File;
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

    public static File newOutputFile( final Class<?> referenceType )
    {
        final File currentDirectory = new File( "." ).getAbsoluteFile();
        final I18nResourceBundleName rbn
            = I18nResourceBundleNameFactory.newI18nResourceBundleName( referenceType );
        final String filename = rbn.getName();

        return new File( currentDirectory, filename );
    }

    public static void fmtAll(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        fmtLocalizedFields( ps, result );
        fmtIgnoredFields( ps, result );
        fmtMissingProperties( ps, result );
    }

    public static void fmtLocalizedFields(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        fmt( ps, "localizedFieldMap", result.getLocalizedFields() );
    }

    public static void fmtIgnoredFields(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        fmt( ps, "ignoredFields", result.getIgnoredFields() );
    }

    private static void fmtMissingProperties(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        fmt( ps, "missingProperties", result.getMissingProperties() );
    }

    private static void fmt(
        final PrintStream         ps ,
        final String              name,
        final Map<String,Integer> fieldUsageCounts
        )
    {
        ps.println( "# " + name + " (key:usageCount) " );

        for( final Map.Entry<String,Integer> entry : fieldUsageCounts.entrySet() ) {
            ps.println( "lf: " + entry.getKey() + " : " + entry.getValue() );
        }

        ps.println( '#' );
    }
}
