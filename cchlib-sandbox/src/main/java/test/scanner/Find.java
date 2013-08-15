package test.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Find
{
    static void usage()
    {
        System.err.println(
            "java Find <path> -name \"<glob_pattern>\""
            );
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException
    {
        if( args.length < 3 || !args[1].equals("-name") ) {
            usage();
            }

        Path startingDir = Paths.get(args[0]);
        String pattern = args[2];

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }
}
