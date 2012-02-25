package cnamts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException;
import com.googlecode.cchlib.swing.batchrunner.verylazy.VeryLazyBatchTask;

/**
 *
 */
public class DelNonAlphaChar implements VeryLazyBatchTask
{
    public final static char REPLACEMENT_CHAR = '#';
    public final static int DEFAULT_LINE_SIZE = 128;
    private int remplacementChar;
    private int maxLinePos;

    public DelNonAlphaChar(
        final char  remplacementChar,
        final int   ligneSize
        )
    {
        this.remplacementChar = remplacementChar;
        this.maxLinePos = ligneSize;
    }

    public DelNonAlphaChar()
    {
        this( REPLACEMENT_CHAR, DEFAULT_LINE_SIZE );
    }

    public void delNonAlphaChar(
        final File  inputFile,
        final File  outputFile
        ) throws IOException
    {
        final InputStream in = new BufferedInputStream( new FileInputStream( inputFile ) );

        try {
            final OutputStream out;
            final boolean      closeOut;

            if( outputFile != null ) {
                out = new BufferedOutputStream( new FileOutputStream( outputFile ) );
                closeOut = true;
                }
            else {
                out = System.out;
                closeOut = false;
                }

            try {
                delNonAlphaChar( in, out );
                }
            finally {
                if( closeOut ) {
                    out.close();
                    }
                }
            }
        finally {
            in.close();
            }
    }

    @Override//VeryLazyBatchTask
    public void runTask(
        final InputStream   inputStream,
        final OutputStream  outputStream
        ) throws    IOException,
                    BatchRunnerInterruptedException
    {
        delNonAlphaChar( inputStream, outputStream );
    }

    public void delNonAlphaChar(
            final InputStream   in,
            final OutputStream  out
            ) throws IOException
    {
        int linePos = 0;
        int c;

        while( (c = in.read()) > -1 ) {
            // Eg. C / isalnum() Unix
            if( Character.isLetterOrDigit( c ) && (c < 128) ) {
                out.write( c );
                }
            else {
                switch( c ) {
                    case ' ':
                    case '\'':
                    case ',' :
                    case '.' :
                    case ';' :
                        out.write( c );
                        break;

                    default : // skip
                        out.write( this.remplacementChar );
                        break;
                    }
                }
            linePos++;

            if( linePos >= this.maxLinePos ) {
                out.write( '\n' );
                linePos = 0;
                }
            }
    }


    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if( args.length != 3 ) {
            System.err.println( "Usage: del_nonalpha_char <SOURCE> <DESTINATION>|stdout <SIZE>" );
            System.exit( -1 );
            }

        final File  inputFile   = new File( args[ 0 ] );
        final File  outputFile;
        final int   lineSize;

        if( "stdout".equals( args[ 1 ] ) ) {
            outputFile  = null;
            }
        else {
            outputFile  = new File( args[ 1 ] );
            }

        try {
            lineSize = Integer.parseInt( args[ 2 ] );
            }
        catch( NumberFormatException e ) {
            System.err.println( "<SIZE> is not a valid number" );
            System.exit( -1 );
            return;
            }

        DelNonAlphaChar instance = new DelNonAlphaChar( REPLACEMENT_CHAR, lineSize );

        try {
            instance.delNonAlphaChar(inputFile, outputFile);
            }
        catch( FileNotFoundException e ) {
            System.err.println( "File not found: " + e.getMessage() );
            System.exit( -1 );
            }
        catch( IOException e ) {
            System.err.println( "<SIZE> is not a valid number" );
            System.exit( -1 );
            }
        catch( Exception e ) {
            System.err.println( "Unexpected error: " + e.getMessage() );
            e.printStackTrace( System.err );
            System.exit( -1 );
            }
    }
}
