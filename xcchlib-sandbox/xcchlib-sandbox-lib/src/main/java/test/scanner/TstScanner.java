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
            String first ="/";
            String more  = "*.html";
            Path        path = fs.getPath( first, more );
            scan = new Scanner( path );
            scan.close();
        }
    }

    static void usage()
    {
        System.err.println(
            "java XXX <file> \"<pattern>\""
            );
        System.exit(-1);
    }

    public static void main( String[] args ) throws IOException
    {
        if( args.length < 2 ) {
            usage();
            }

        String filename = args[ 0 ];
        String patternString = args[ 1 ];
        // String patternRegExp = "(.*)" + patternString + "(.*)";
        //String patternRegExp = "[\\s]*" + patternString + "[\\s]*";
        //String patternRegExp = ".*" + patternString + ".*";
        String patternRegExp = "\\s*" + patternString + "\\s*";
        
        File cd   = new File( "." ).getCanonicalFile();
        File file = new File( cd, filename );

        System.out.println( "file = " + file );
        Pattern pattern = Pattern.compile( patternRegExp, Pattern.MULTILINE );
        System.out.println( "pattern = " + pattern );

        String  content      = IOHelper.toString( file );
        boolean contentMatch = pattern.matcher( content ).matches();
        //System.out.println( "contentMatch0 ? " + (content.indexOf( patternString ) != -1) );
        System.out.println( "contentMatch ? " + contentMatch );
        //System.out.println( "content ? " + content );

        Scanner scanner = new Scanner( file );
        scanner.useDelimiter( pattern );
        int foundCount = -1;

        while( scanner.hasNext() ) {
            foundCount++;
            String x = scanner.next();
            System.out.println( "x " + x );
            }

        System.out.println( "pattern found ? " + foundCount );
        scanner.close();
    }
}
