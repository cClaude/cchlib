package com.googlecode.cchlib.tools.sortfiles.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fest.util.VisibleForTesting;
import com.googlecode.cchlib.tools.sortfiles.FileParserException;
import com.googlecode.cchlib.tools.sortfiles.ParsedFilename;
import com.googlecode.cchlib.tools.sortfiles.RecordFileParser;

public class DefaultRecordFileParser implements RecordFileParser
{
    private final static Pattern numberPattern = Pattern.compile( "_([0-9]+)_(Out|In)\\." );

    @Override
    public ParsedFilename parse( final File file ) throws FileParserException
    {
        return parse( file.getName() );
    }

    public ParsedFilename parse( final String filename ) throws FileParserException
    {
        final ParsedFilename parsedFilename = new DefaultParsedFilename();

        parsedFilename.setRawPhoneNumber( buildRawPhoneNumber( filename ) );
        parsedFilename.setRawDate( buildRawPhoneNumber( filename ) );

        return parsedFilename;
    }

    /**
     * @deprecated Use {@link #buildRawPhoneNumber(String)} instead
     */
    @VisibleForTesting
    /* package */ final String buildRawNumber( final String filename ) throws FileParserException
    {
        return buildRawPhoneNumber( filename );
    }

    @VisibleForTesting
    /* package */ final String buildRawPhoneNumber( final String filename ) throws FileParserException
    {
        final Matcher matcher = numberPattern.matcher( filename );

        if( matcher.find() ) {
            final String rawPhoneNumber = matcher.group( 1 );

            return rawPhoneNumber;
        }
        throw new FileParserException( "Can't build raw number - groupCount = " + matcher.groupCount() );
    }

    @VisibleForTesting
    /* package */ final String buildRawDate( final String filename ) throws FileParserException
    {
        try {
            final String rawDate = filename.substring( 0, 16 );

            return rawDate;
        } catch( IndexOutOfBoundsException e ) {
            throw new FileParserException( "Can't build raw date ", e );
        }
    }

}
