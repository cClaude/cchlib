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

/**
 *
 */
@Deprecated
public class DelNonAlphaChar implements com.googlecode.cchlib.swing.batchrunner.verylazy.VeryLazyBatchTask
{
    public static final char REPLACEMENT_CHAR = '#';
    public static final int DEFAULT_LINE_SIZE = 128;
    private int remplacementChar;
    private boolean useRemplacementChar;
    private int maxLinePos;
    private boolean useMaxLinePos;

    public DelNonAlphaChar(
            final Character remplacementChar,
            final Integer   ligneSize
            )
        {
            if( remplacementChar == null ) {
                this.remplacementChar = 0;
                this.useRemplacementChar = false;
                }
            else {
                this.remplacementChar = remplacementChar.charValue();
                this.useRemplacementChar = true;
                }
            if( ligneSize == null ) {
                this.maxLinePos = -1;
                this.useMaxLinePos = false;
                }
            else {
                this.maxLinePos = ligneSize.intValue();
                this.useMaxLinePos = true;
                }
        }
    /*
    public DelNonAlphaChar(
        final char  remplacementChar,
        final int   ligneSize
        )
    {
        this.remplacementChar = remplacementChar;
        this.useRemplacementChar = true;
        this.maxLinePos = ligneSize;
        this.useMaxLinePos = true;
    }*/

    public DelNonAlphaChar()
    {
        this(     new Character( REPLACEMENT_CHAR ),
                new Integer( DEFAULT_LINE_SIZE )
                );
    }

    @SuppressWarnings("resource")
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
        ) throws IOException,
                 com.googlecode.cchlib.swing.batchrunner.BatchRunnerInterruptedException
    {
        delNonAlphaChar( inputStream, outputStream );
    }

    public void delNonAlphaChar(
            final InputStream   in,
            final OutputStream  out
            ) throws IOException
    {
        long linePos = 0;
        int  c;

        while( (c = in.read()) > -1 ) {
            // Eg. C / isalnum() Unix
            if( (Character.isLetterOrDigit( c ) && (c < 128)) || !useRemplacementChar ) {
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

            if( (linePos >= this.maxLinePos) && this.useMaxLinePos ) {
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
            exit( -1 );
            return; // avoid warns
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
            exit( -1 );
            return; // avoid warns
            }

        DelNonAlphaChar instance = new DelNonAlphaChar(
                new Character( REPLACEMENT_CHAR ),
                new Integer( lineSize )
                );

        try {
            instance.delNonAlphaChar(inputFile, outputFile);
            }
        catch( FileNotFoundException e ) {
            System.err.println( "File not found: " + e.getMessage() );
            exit( -1 );
            }
        catch( IOException e ) {
            System.err.println( "<SIZE> is not a valid number" );
            exit( -1 );
            }
        catch( Exception e ) {
            System.err.println( "Unexpected error: " + e.getMessage() );
            e.printStackTrace( System.err );
            exit( -1 );
            }
    }

    private static void exit( int error )
    {
        System.exit( error );
    }
}
