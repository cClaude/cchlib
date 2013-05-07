package com.googlecode.cchlib.tools.sortfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.attribute.FileAttribute;
import com.googlecode.cchlib.tools.sortfiles.filetypes.FileType;

public class SortFiles
{
    public static void main(String[] args) throws IOException
    {
        final File baseInputPath = new File( "E:\\Jerome\\Toshiba\\Dossier inconnu" );
        final Path baseOuputPath = new File( "E:\\Jerome\\Toshiba\\D!!" ).toPath();

        main( baseInputPath, baseOuputPath );
    }

    public static void main( final File baseInputPath, final Path baseOuputPath ) throws IOException
    {
        //final FileAttribute<?>[] createDirectoriesFileAttributes = null;

        for( FileType fileType : FileType.values() ) {
            System.out.println( FileType.toString( fileType ) );

            final Path outputPath = baseOuputPath.resolve( fileType.getFolder() );

            File[] files = baseInputPath.listFiles( fileType.getFileFilter() );

            if( files != null && files.length > 0 ) {
                for( File file : files ) {
                    final Path source = file.toPath();
                    final Path target = outputPath.resolve( file.getName() );

                    if( ! Files.isDirectory( outputPath ) ) {
                        Files.createDirectories( outputPath/*, createDirectoriesFileAttribute*/ );
                        }

                    System.out.println( "Move " + source + " TO " + target );

                    Files.move( source, target );
                    }
                }
            }
    }
}
