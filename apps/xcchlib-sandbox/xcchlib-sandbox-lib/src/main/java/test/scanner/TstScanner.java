package test.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;
import com.googlecode.cchlib.io.IOHelper;

/**
 *
 */
public class TstScanner
{
    private Scanner scan;

    public TstScanner() throws IOException
    {
        try( FileSystem  fs = FileSystems.getDefault() ) {
            final String first ="/";
            final String more  = "*.html";
            final Path        path = fs.getPath( first, more );
            this.scan = new Scanner( path );
            this.scan.close();
        }
    }

    static void usage()
    {
        System.err.println(
            "java XXX <file> \"<pattern>\""
            );
        System.exit(-1);
    }

    public static void main( final String[] args ) throws IOException
    {
        if( args.length < 2 ) {
            usage();
            }

        final String filename = args[ 0 ];
        final String patternString = args[ 1 ];
        // String patternRegExp = "(.*)" + patternString + "(.*)";
        //String patternRegExp = "[\\s]*" + patternString + "[\\s]*";
        //String patternRegExp = ".*" + patternString + ".*";
        final String patternRegExp = "\\s*" + patternString + "\\s*";

        final File cd   = new File( "." ).getCanonicalFile();
        final File file = new File( cd, filename );

        System.out.println( "file = " + file );
        final Pattern pattern = Pattern.compile( patternRegExp, Pattern.MULTILINE );
        System.out.println( "pattern = " + pattern );

        final String  content      = IOHelper.toString( file );
        final boolean contentMatch = pattern.matcher( content ).matches();
        //System.out.println( "contentMatch0 ? " + (content.indexOf( patternString ) != -1) );
        System.out.println( "contentMatch ? " + contentMatch );
        //System.out.println( "content ? " + content );

        try( Scanner scanner = new Scanner( file ) ) {
            scanner.useDelimiter( pattern );
            int foundCount = -1;

            while( scanner.hasNext() ) {
                foundCount++;
                final String x = scanner.next();
                System.out.println( "x " + x );
                }

            System.out.println( "pattern found ? " + foundCount );
            }
    }
}
