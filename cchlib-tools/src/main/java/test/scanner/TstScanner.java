package test.scanner;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 */
public class TstScanner
{
    private Scanner scan;

    public TstScanner() throws IOException
    {
        FileSystem  fs = FileSystems.getDefault();
        String first ="/";
        String more  = "*.html";
        Path        path = fs.getPath( first, more );
        scan = new Scanner( path );
    }

    public static void main( String[] args )
    {

    }
}
