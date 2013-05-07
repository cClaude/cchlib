package com.googlecode.cchlib.tools.sortfiles;

import static java.nio.file.FileVisitResult.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.Set;
import com.googlecode.cchlib.tools.sortfiles.filetypes.FileType;

public class SortFiles
{
    private static final FileAttribute<?>[] createDirectoriesFileAttributes = new FileAttribute[ 0 ];
    public static void main(String[] args) throws IOException
    {
        final File baseInputPath = new File( "F:/P1/D!F1" );
        //final File baseInputPath = new File( "F:/P1/" );
        final Path baseOuputPath = new File( "F:/P1/D!F1__" ).toPath();
        
        // doDirectories( baseInputPath.toPath(), baseOuputPath, Integer.MAX_VALUE );
        doDirectories( baseInputPath.toPath(), baseOuputPath,0 );
    }

    public static void doDirectory( final File baseInputPath, final Path baseOuputPath ) throws IOException
    {
 
        for( FileType fileType : FileType.values() ) {
            System.out.println( FileType.toString( fileType ) );

            final Path outputPath = baseOuputPath.resolve( fileType.getFolder() );

            File[] files = baseInputPath.listFiles( fileType.getFileFilter() );

            if( files != null && files.length > 0 ) {
                for( File file : files ) {
                    moveTo( file, outputPath );
                    }
                }
            }
    }
    
    public static void doDirectories( 
            final Path baseInputPath, 
            final Path baseOuputPath,
            final int   maxDepth
            ) throws IOException
    {
        //final FileAttribute<?>[] createDirectoriesFileAttributes = null;
        Set<FileVisitOption> fileVisitOptions = EnumSet.noneOf( FileVisitOption.class );
        
        Files.walkFileTree( baseInputPath,
                /*Set<FileVisitOption>*/ fileVisitOptions ,
                maxDepth,
                new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory( 
                            Path                dir,
                            BasicFileAttributes attrs ) throws IOException
                    {
                        return CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile( Path file,
                            BasicFileAttributes attrs ) throws IOException
                    {
                        File fileFile = file.toFile();
                        
                        for( FileType fileType : FileType.values() ) {
                            if( fileType.getFileFilter().accept( fileFile ) ) {
                                //System.err.println( file );
                                moveTo( file, baseOuputPath, fileType );
                                return CONTINUE;
                            }
                        }

                        //System.out.println( file );
                        
                        return CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed( Path file,
                            IOException exc ) throws IOException
                    {
                        return CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory( Path dir,
                            IOException exc ) throws IOException
                    {
                        return CONTINUE;
                    }});
    }
    
    private static void moveTo( File file, Path outputPath ) throws IOException
    {
        final Path source = file.toPath();
        final Path target = outputPath.resolve( file.getName() );
        
        moveTo( source, target );
//        if( ! Files.isDirectory( outputPath ) ) {
//            Files.createDirectories( outputPath, createDirectoriesFileAttributes );
//            }
//
//        System.out.println( "Move " + source + " TO " + target );
//
//        Files.move( source, target );
    }
    
    private static void moveTo( Path source, Path target ) throws IOException
    {
        Path targetParentPath = target.getParent();
        
        if( ! Files.isDirectory( targetParentPath ) ) {
            Files.createDirectories( targetParentPath, createDirectoriesFileAttributes );
            }

        System.out.println( "Move " + source + " TO " + target );

        Files.move( source, target );
    }

    private static void moveTo( Path source, Path baseOuputPath, FileType fileType ) throws IOException
    {
        final Path outputPath = baseOuputPath.resolve( fileType.getFolder() );
        final Path target = outputPath.resolve( source.getFileName());
        
        moveTo( source, target );
    }
}
