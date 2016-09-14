package com.googlecode.cchlib.xutil.google.googlecontact;

import groovy.json.JsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.util.iterable.Iterables;
import com.googlecode.cchlib.util.iterator.Selectable;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContacAnalyserException;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;
import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.DefaultCSVEntryParser;

/**
 * Handle google contact cvs files.
 */
public class GoogleContacts
{
    private GoogleContacts()
    {
        // Static methods
    }

    /**
     * @return a {@link List} of {@link GoogleContact} from a {@link Reader}
     *
     * @throws IOException if an error occur when reading data
     * @throws GoogleContacAnalyserException if an error occur when analyze data
     */
    public static List<GoogleContact> createGoogleContacts( @Nonnull final Reader reader )
        throws IOException, GoogleContacAnalyserException  // NOSONAR
    {
        final List<String[]> data = loadCSV( reader );

        return convertCSVIntoGoogleContacts( data );
    }

    private static List<String[]> loadCSV( final Reader reader ) throws IOException
    {
        final CSVStrategy              strategy  = new CSVStrategy(',', '"', '\0', false, false);
        final CSVEntryParser<String[]> parser    = new DefaultCSVEntryParser();

        try( final CSVReader<String[]> csvParser = new CSVReaderBuilder<String[]>( reader ).strategy( strategy ).entryParser( parser ).build() ) {
            return csvParser.readAll();
        }
    }

    private static List<GoogleContact> convertCSVIntoGoogleContacts(
        final List<String[]> data
        ) throws GoogleContacAnalyserException
    {
        final List<GoogleContact> list     = new ArrayList<>();
        final Iterator<String[]>  iterator = data.iterator();

        if( iterator.hasNext() ) {
            final String[]             headers = iterator.next();
            final GoogleContactFactory factory = new GoogleContactFactory( headers, true );

            while( iterator.hasNext() ) {
                final GoogleContact contact = factory.newGoogleContact( iterator.next() );

                list.add( contact );
                }
            }

            return list;
        }

    /**
     * @return a {@link List} of {@link GoogleContact} from a {@link InputStream}, assume
     * stream was encoding using UTF-16
     *
     * @throws IOException if an error occur when reading data
     * @throws GoogleContacAnalyserException if an error occur when analyze data
     */
    public static List<GoogleContact> createGoogleContacts( @Nonnull final InputStream inStream )
        throws UnsupportedEncodingException, IOException // NOSONAR
    {
        final List<GoogleContact> list;

        try( final Reader reader = new InputStreamReader( inStream, "UTF-16" ) ){
            list = GoogleContacts.createGoogleContacts( reader );
        }
        catch( final GoogleContacAnalyserException e ) {
            throw new IOException( e );
        }

        return list;
    }

    /**
     * @return a {@link List} of {@link GoogleContact} from a {@link File}, assume
     * file was encoding using UTF-16
     *
     * @throws IOException if an error occur when reading data
     * @throws GoogleContacAnalyserException if an error occur when analyze data
     */
    public static List<GoogleContact> createGoogleContacts( @Nonnull final File file )
        throws FileNotFoundException, IOException // NOSONAR
    {
        try ( final InputStream inStream = new FileInputStream( file ) ) {
            return createGoogleContacts( inStream );
        }
    }

    /**
     * Convert multiple values use in google contact, into single values. Order in this
     * list could be important for some entries.
     *
     * @param rawValue cell content use by google
     * @return an unmodifiable {@link List} of {@link String}, return an empty list if rawValue is null
     */
    public static List<String> toValues( @Nullable final String rawValue )
    {
        if( rawValue == null ) {
            return Collections.emptyList();
        } else {
            final String[] values = rawValue.split( " ::: ", 0 );

            return Collections.unmodifiableList( Arrays.asList( values ) );
        }
    }

    @NeedDoc
    public static Iterable<String> getPhoneValues( final GoogleContact googleContact )
    {
        final Collection<BasicEntry> phones = googleContact.getPhones();

        return getValues( phones );
    }

    @NeedDoc
    public static List<String> getValues( final Collection<BasicEntry> entries )
    {
        if( ! entries.isEmpty() ) {
            final List<String> list = new ArrayList<>();

            for( final BasicEntry entry : entries ) {
                addToList( list, entry );
            }

            return list;
          } else {
            return Collections.emptyList();
        }
    }

    private static void addToList(
        final List<String> list,
        final BasicEntry   entry
        )
    {
        final String rawValue = entry.getValue();

        if( !rawValue.isEmpty() ) {
            for( final String singleValue : GoogleContacts.toValues( rawValue ) ) {
                list.add( singleValue );
            }
        }
    }

    @NeedDoc
    public static List<String> getValues( final BasicEntry entry )
    {
        final List<String> list = new ArrayList<>();

        addToList( list, entry );

        return list;
    }

    public static JsonBuilder toJSON( final List<GoogleContact> contacts )
    {
       return new JsonBuilder( contacts );
    }

    public static JsonBuilder toJSON( final GoogleContact googleContact )
    {
        return new JsonBuilder( googleContact );
    }

    public static String toPrettyString( final List<GoogleContact> contacts )
    {
        return toJSON( contacts ).toPrettyString();
    }

    public static String toString( final List<GoogleContact> contacts )
    {
        return toJSON( contacts ).toString();
    }

    /**
     * @deprecated no replacement use java 8
     */
    @Deprecated
    public static Iterable<GoogleContact> all(
        final List<GoogleContact>       contacts,
        final Selectable<GoogleContact> filter
        )
    {
        return Iterables.filter( contacts, filter );
    }
}
