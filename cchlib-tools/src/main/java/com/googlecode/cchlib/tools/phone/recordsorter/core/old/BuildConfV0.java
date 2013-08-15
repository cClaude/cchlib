package samples.batchrunner.tel.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import com.googlecode.cchlib.io.filefilter.DirectoryFileFilter;

public class BuildConfV0
{
    private Properties prop = new Properties();

    public static void main( String[] args ) throws IOException
    {
        BuildConfV0 instance = new BuildConfV0();

        instance.loadFolder( new File( args[0] ) );
        instance.save( new File( args[1] ) );
    }

    private void save( File outputFile ) throws IOException
    {
        try( OutputStream out = new FileOutputStream( outputFile ) ) {
            prop.store( out, null );
        }
    }

    @SuppressWarnings("unused")
    private void loadFile( File inputFile ) throws FileNotFoundException, IOException
    {
        try( BufferedReader reader = new BufferedReader( new FileReader( inputFile ) ) ) {
            for (;;) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                    }
                addLine( line );
            }
        }
    }

    private void loadFolder( File folderFile )
    {
        File[] files = folderFile.listFiles( new DirectoryFileFilter() );

        if( files != null ) {
            for( File file : files ) {
                addLine( file.getName() );
            }
        }
    }

    private void addLine( String line )
    {
        String[] content = line.split( "\\." );
        boolean  remove  = line.startsWith( "zempty_" );

        System.out.println( "*** " + line + " " + content.length + " * " + remove);

        for( int i = 1; i<content.length; i++) {
            addEntry( content[ i ], line, remove );
        }
    }

    private void addEntry( String number, String folder, boolean remove )
    {
        System.out.println( "[" + number + "] = " + folder );

        prop.put( number, folder );

        if( remove ) {
            prop.put( number + ".remove", Boolean.TRUE.toString() );
        }
    }

}
