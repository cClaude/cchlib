package com.googlecode.cchlib.misc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * com.googlecode.cchlib.apps.sample2.Sample2
 */
public class SplitMails implements Iterable<InputStream>
{
    public static final String TESTINFILE_REL_FROM_USERHOME = "Bureau/jup60/jup60.mails2";
    public static final String TESTOUTDIR = "C:/temp/";

    private MailLineInputStream thunderbirdMails;

    /**
     *
     * @param thunderbirdMailFile
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public SplitMails(
        final File thunderbirdMailFile
        )
        throws FileNotFoundException, IOException
    {
        this.thunderbirdMails = new MailLineInputStream(
                new BufferedInputStream(
                    new FileInputStream( thunderbirdMailFile )
                    )
                );
     }

    /**
     *
     *
     * /
    public static void main( String[] args ) throws IOException
    {

        final File inFile        = new File( TESTINFILE );
        final File outputDirFile = new File( TESTOUTDIR );

        p.info( "In File: " + inFile );
        p.info( "Out Dir: " + outputDirFile );

        SplitMails instance  = new SplitMails( inFile );
        int        mailCount = 0;


        for( InputStream in : instance ) {
            File outFile = new File( outputDirFile, Integer.toString( mailCount++ ) );
            p.info( "out File: " + outFile );

//            OutputStream out = new BufferedOutputStream(
//                new FileOutputStream( outFile )
//                );

            byte[] buffer = new byte[ 409600 ];
            int    index  = 0;
            int    c;

            while( (c = in.read()) != -1 ) {
                buffer[ index++ ] = (byte)c;
                }

            p.info(
                    String.format(
                        "MSG (%d - %d):",
                        index,
                        instance.thunderbirdMails.getLineNumber(),
                        new String( buffer, 0, index )
                        )
                    );
            }

        p.info( "Done : mail count = " + mailCount );
        //DialogHelper.showMessageExceptionDialog( "OK", new Exception( "OK") );
    }*/

    @Override
    public Iterator<InputStream> iterator()
    {
        return new Iterator<InputStream>()
            {
                @Override
                public boolean hasNext()
                {
                    return thunderbirdMails.isEOF();
                }
                @Override
                public InputStream next()
                {
                    return new MailInputStream( thunderbirdMails );
               }
                @Override
                public void remove()
                {
                    throw new UnsupportedOperationException();
                }
            };
    }
}
