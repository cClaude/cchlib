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
    private final Pattern p;

    public FileFindReplace( final String pattern ) throws IOException
    {
        final String patternRegExp = "\\s*" + pattern + "\\s*";

        this.p = Pattern.compile( patternRegExp, Pattern.MULTILINE );
    }

    public boolean contain( final File file ) throws FileNotFoundException
    {
        int foundCount = -1;

        try( Scanner scan = new Scanner( file ) ) {
            scan.useDelimiter( this.p );

            while( scan.hasNext() ) {
                foundCount++;
                scan.next();

                if( foundCount > 1 ) {
                    return true;
                    }
                }
            }

        return false;
    }

    public int containCount( final File file ) throws FileNotFoundException
    {
        int foundCount = -1;

        try( Scanner scan = new Scanner( file ) ) {
            scan.useDelimiter( this.p );

            while( scan.hasNext() ) {
                foundCount++;
                scan.next();
                }
            }

        if( foundCount == -1 ) {
            return 0; // empty file
            }

        return foundCount;
    }

    public int replaceAll( final File file, final File newFile, final String newValue )
        throws IOException
    {
        int foundCount = -1;

        try( final Scanner scan = new Scanner( file ) ) {
            scan.useDelimiter( this.p );

            try( final Writer w = new BufferedWriter( new FileWriter( newFile ) ) ) {
                while( scan.hasNext() ) {
                    if( foundCount >= 0 ) {
                        w.write( newValue );
                        }
                    foundCount++;
                    w.write( scan.next() );
                    }
                }
            }

        if( foundCount == -1 ) {
            return 0; // empty file
            }

        return foundCount;
    }

    public static void main( final String[] args ) throws IOException
    {
        final String filename      = "pom.xml";
        final String patternString = "parent";
        final String newValue      = "toto";

        final String patternRegExp = "\\s*" + patternString + "\\s*";
        final File cd   = new File( "." ).getCanonicalFile();
        final File file = new File( cd, filename );
        final File out  = File.createTempFile( "test", ".xml" );
        out.deleteOnExit();

        System.out.println( "file = " + file );
        System.out.println( "patternRegExp = " + patternRegExp );

        final FileFindReplace instance = new FileFindReplace( patternRegExp );

        final boolean res1 = instance.contain( file );
        System.out.println( "pattern found ? " + res1 );

        final int res2 = instance.containCount( file );
        System.out.println( "pattern found ? " + res2 );

        final int res3 = instance.replaceAll( file, out, newValue );
        System.out.println( "pattern found ? " + res3 );
        System.out.println( "result in: " + out );
    }
}
