package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.stream.Stream;
import com.googlecode.cchlib.i18n.resources.I18nResourceFactory;
import com.googlecode.cchlib.i18n.resources.ResourceBundleHelper;

@SuppressWarnings("ucd") // API
public class I18nResourceBuilderHelper
{
    private static final class I18nResourceBuilderResultFormat
    {
        private final PrintStream ps;
        private final String      header;
        private final String      linePrefix;
        private final String      separator;

        I18nResourceBuilderResultFormat(
            final PrintStream ps,
            final String      header,
            final String      linePrefix,
            final String      separator
            )
        {
            this.ps           = ps;
            this.header       = header;
            this.linePrefix   = linePrefix;
            this.separator    = separator;
        }

        public void print( final Map<String,Integer> keysUsageCount )
        {
            this.ps.println( this.header );

            keysUsageCount.entrySet().stream().forEachOrdered( this::print );

            this.ps.println( '#' );
            this.ps.flush();
        }

        public void print( final Stream<Map.Entry<String,String>> keysValuesStream )
        {
            this.ps.println( this.header );

            keysValuesStream.forEachOrdered( this::print );

            this.ps.println( '#' );
            this.ps.flush();
        }

        private void print( final Map.Entry<String,?> e )
        {
            this.ps.println( this.linePrefix + e.getKey() + this.separator + e.getValue() );
        }
   }

    private I18nResourceBuilderHelper()
    {
        // All static
    }

    /**
     * Create a properties file base of {@code referenceType} class name.
     *
     * @param referenceType Type to use
     * @return a properties file
     * @throws IOException if can not build canonical path for current directory
     * @see ResourceBundleHelper#newName(Class)
     */
    public static File newOutputFile( final Class<?> referenceType ) throws IOException
    {
        return newFileFromCurrentDirectory(
                ResourceBundleHelper.newName( referenceType )
                );
    }

    /**
     * Create a properties file base of {@code referencePackage} package name.
     * <p>
     * File name will be prefix by {@link I18nResourceFactory#DEFAULT_MESSAGE_BUNDLE_BASENAME}
     * value.
     *
     * @param referencePackage Package to use
     * @return a properties file
     * @throws IOException if can not build canonical path for current directory
     * @see ResourceBundleHelper#newName(Package)
     */
    public static File newOutputFile( final Package referencePackage ) throws IOException
    {
        return newFileFromCurrentDirectory(
                ResourceBundleHelper.newName( referencePackage )
                );
    }

    private static File getCurrentDirectoryCanonicalFile() throws IOException
    {
        return new File( "." ).getCanonicalFile();
    }

    private static File newFileFromCurrentDirectory( final String filenamePrefix )
        throws IOException
    {
        return new File( getCurrentDirectoryCanonicalFile(), filenamePrefix + ".properties" );
    }

    /**
     * Just an alias that call {@link #fmtLocalizedFields(PrintStream, I18nResourceBuilderResult)},
     * then {@link #fmtIgnoredFields(PrintStream, I18nResourceBuilderResult)},
     * and then {@link #fmtMissingProperties(PrintStream, I18nResourceBuilderResult)}
     * and finally {@link #fmtUnusedProperties(PrintStream, I18nResourceBuilderResult)}
     *
     * @param ps
     *            {@link PrintStream} for output.
     * @param result
     *            Value from {@link I18nResourceBuilder#getResult()}
     */
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

    /**
     * Format to {@code ps} ordered values from
     * {@link I18nResourceBuilderResult#getLocalizedFields()}
     * (discover order)
     *
     * @param ps
     *            {@link PrintStream} for output.
     * @param result
     *            Value from {@link I18nResourceBuilder#getResult()}
     */
    public static void fmtLocalizedFields(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        new I18nResourceBuilderResultFormat(
                ps,
                "# localizedFieldMap (key:usageCount)",
                "# localized : ",
                " : "
                ).print( result.getLocalizedFields() );
    }

    /**
     * Format to {@code ps} ordered values from
     * {@link I18nResourceBuilderResult#getIgnoredFields()}
     * (discover order)
     *
     * @param ps
     *            {@link PrintStream} for output.
     * @param result
     *            Value from {@link I18nResourceBuilder#getResult()}
     */
    public static void fmtIgnoredFields(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        new I18nResourceBuilderResultFormat(
                ps,
                "# ignoredFields (key:usageCount)",
                "# ignored : ",
                " : " ).print( result.getIgnoredFields() );
    }

    /**
     * Format to {@code ps} ordered values from
     * {@link I18nResourceBuilderResult#getMissingProperties()}
     * (discover order)
     *
     * @param ps
     *            {@link PrintStream} for output.
     * @param result
     *            Value from {@link I18nResourceBuilder#getResult()}
     */
    public static void fmtMissingProperties(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        new I18nResourceBuilderResultFormat(
                ps,
                "# missingProperties (key:usageCount)",
                "",
                " : "
                ).print( result.getMissingProperties() );
    }

    /**
     * Format to {@code ps} ordered values from
     * {@link I18nResourceBuilderResult#getUnusedProperties()}
     * (natural order on key)
     *
     * @param ps
     *            {@link PrintStream} for output.
     * @param result
     *            Value from {@link I18nResourceBuilder#getResult()}
     */
    public static void fmtUnusedProperties(
        final PrintStream               ps,
        final I18nResourceBuilderResult result
        )
    {
        new I18nResourceBuilderResultFormat(
                ps,
                "# unusedProperties (key=value)",
                "unused : ",
                "=" ).print(
                        result.getUnusedProperties().entrySet().stream().sorted(
                                (e1,e2) -> e1.getKey().compareTo( e2.getKey() )
                                )
                        );
    }
}
