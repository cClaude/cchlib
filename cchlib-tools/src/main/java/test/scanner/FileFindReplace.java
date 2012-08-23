package test.scanner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 */
public class FileFindReplace
{
    private Pattern p;

    public FileFindReplace( final String pattern ) throws IOException
    {
        final String patternRegExp = "\\s*" + pattern + "\\s*";

        this.p = Pattern.compile( patternRegExp, Pattern.MULTILINE );
    }

    public boolean contain( final File file ) throws FileNotFoundException
    {
        Scanner scan = new Scanner( file );
        scan.useDelimiter( p );

        int foundCount = -1;

        try {
            while( scan.hasNext() ) {
                foundCount++;
                scan.next();

                if( foundCount > 1 ) {
                    return true;
                    }
                }
            }
        finally {
            scan.close();
            }

        return false;
    }

    public int containCount( final File file ) throws FileNotFoundException
    {
        Scanner scan = new Scanner( file );
        scan.useDelimiter( p );

        int foundCount = -1;

        try {
            while( scan.hasNext() ) {
                foundCount++;
                scan.next();
                }
            }
        finally {
            scan.close();
            }

        if( foundCount == -1 ) {
            return 0; // empty file
            }

        return foundCount;
    }

    public int replaceAll( final File file, final File newFile, final String newValue )
        throws IOException
    {
        final Scanner scan = new Scanner( file );
        scan.useDelimiter( p );

        int foundCount = -1;
        
        try {
            final Writer w = new BufferedWriter( new FileWriter( newFile ) );

            try {
                while( scan.hasNext() ) {
                    if( foundCount >= 0 ) {
                        w.write( newValue );
                        }
                    foundCount++;
                    w.write( scan.next() );
                    }
                }
            finally {
                w.close();
                }
            }
        finally {
            scan.close();
            }

        if( foundCount == -1 ) {
            return 0; // empty file
            }

        return foundCount;
    }

    public static void main( String[] args ) throws IOException
    {
        String filename      = "pom.xml";
        String patternString = "parent";
        String newValue      = "toto";

        String patternRegExp = "\\s*" + patternString + "\\s*";
        File cd   = new File( "." ).getCanonicalFile();
        File file = new File( cd, filename );
        File out  = File.createTempFile( "test", ".xml" );
        out.deleteOnExit();
        
        System.out.println( "file = " + file );
        System.out.println( "patternRegExp = " + patternRegExp );

        FileFindReplace instance = new FileFindReplace( patternRegExp );

        boolean res1 = instance.contain( file );
        System.out.println( "pattern found ? " + res1 );

        int res2 = instance.containCount( file );
        System.out.println( "pattern found ? " + res2 );

        int res3 = instance.replaceAll( file, out, newValue );
        System.out.println( "pattern found ? " + res3 );
        System.out.println( "result in: " + out );
    }
}
