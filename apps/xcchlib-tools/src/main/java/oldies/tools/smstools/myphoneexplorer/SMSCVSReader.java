package oldies.tools.smstools.myphoneexplorer;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;

public class SMSCVSReader implements Closeable
{
    private static final Logger LOGGER = Logger.getLogger( SMSCVSReader.class );

    /**
      * CVS Separator value.
      */
     public static final char CSV_SEPARATOR = ';';

     private final File     srcFile;
     private ArrayList<SMS> smsList;
     private CSVReader      csvr;

    public SMSCVSReader( final File srcFile ) throws FileNotFoundException
    {
        this.srcFile = srcFile;
        this.csvr    = new CSVReader(
            new BufferedReader( new FileReader( srcFile ) ),
            CSV_SEPARATOR
            );
    }

    @Override
    public void close() throws IOException
    {
        if( this.csvr != null ) {
            this.csvr.close();
            this.csvr = null;
        }
    }

    private boolean readEntry() throws IOException, ParseException
    {
        final String[] line = this.csvr.readNext();

        LOGGER.trace( "line:" + String.valueOf( line ) );

        if( line == null ) {
            return false; // eof
            }

        if( (line.length != 6) && (line.length != 8)) {
            throw new IOException(
                "Can't read entry #" + (this.smsList.size() + 1)
                );
        }

        //
        // "From";"To";"Body";"Time";"Storage";"PDU"
        //
        final SMS sms = new SMS();

        sms.setFrom   ( line[0] );
        sms.setTo     ( line[1] );
        sms.setBody   ( line[2] );
        sms.setTime   ( line[3] );
        sms.setStorage( line[4] );
        sms.setPdu    ( line[5] );

        if( line.length == 8) {
            sms.setXtraDate  ( line[6] );
            sms.setXtraSource( line[7] );
        }
        else {
            // File source
            sms.setXtraSource( this.srcFile.getName() );
        }

        this.smsList.add( sms );

        return true;
    }

    private int readAll() throws IOException, ParseException
    {
        int count = 0;

        while( readEntry() ) {
            count++;
        }

        LOGGER.info( "readAll:" + count );

        return count;
    }

    /**
     * Returns list of SMS
     * @return list of SMS
     * @throws IOException if any
     * @throws ParseException if any
     */
    @SuppressWarnings({
        "squid:S1160", // More than on exception
        "squid:RedundantThrowsDeclarationCheck",
        })
    public List<SMS> getSMSList() throws IOException, ParseException
    {
        if( this.smsList == null ) {
            this.smsList = new ArrayList<>();

            readAll();
        }

        return Collections.unmodifiableList( this.smsList );
    }
}
