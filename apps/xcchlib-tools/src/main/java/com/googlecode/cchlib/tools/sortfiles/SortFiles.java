package com.googlecode.cchlib.tools.sortfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.tools.sortfiles.filetypes.FileType;

public class SortFiles
{
    private static final FileAttribute<?>[] createDirectoriesFileAttributes = new FileAttribute[ 0 ];
    private static final int RENAME_MAX_NUMBER = 10000;
    private static final Set<FileVisitOption> fileVisitOptions = EnumSet.noneOf( FileVisitOption.class );

    public static void main(String[] args) throws IOException
    {
        FileType.check();
        {
        final File baseInputPath = new File( "F:/P1_Unsorted" );
        final Path baseOuputPath = new File( "F:/P1_Sorted" ).toPath();

        doDirectories( baseInputPath.toPath(), baseOuputPath, 150 );
        //findExtensions( baseInputPath.toPath(), 150 );
        }
        {
        final File baseInputPath = new File( "F:/P2_Unsorted" );
        final Path baseOuputPath = new File( "F:/P2_Sorted" ).toPath();

        doDirectories( baseInputPath.toPath(), baseOuputPath, 150 );
        findExtensions( baseInputPath.toPath(), 150 );
        }
        //doDirectories( baseInputPath.toPath(), baseOuputPath,0 );

        System.out.println( "done." );
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
            final int  maxDepth
            ) throws IOException
    {
        Files.walkFileTree( baseInputPath,
                fileVisitOptions,
                maxDepth,
                new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(
                            Path                dir,
                            BasicFileAttributes attrs ) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile( Path file,
                            BasicFileAttributes attrs ) throws IOException
                    {
                        File fileFile = file.toFile();

                        for( FileType fileType : FileType.values() ) {
                            if( fileType.getFileFilter().accept( fileFile ) ) {
                                moveTo( file, baseOuputPath, fileType );
                                return FileVisitResult.CONTINUE;
                                }
                            }

                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed( Path file,
                            IOException exc ) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory( Path dir,
                            IOException exc ) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }});
    }

    public static void findExtensions(
            final Path baseInputPath,
            final int  maxDepth
            ) throws IOException
    {
        final Set<String> extensions = new HashSet<>();

        Files.walkFileTree( baseInputPath,
                fileVisitOptions,
                maxDepth,
                new FileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(
                            Path                dir,
                            BasicFileAttributes attrs ) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile( Path file,
                            BasicFileAttributes attrs ) throws IOException
                    {
                        String filename = file.toFile().getName();
                        int    extPos   = filename.lastIndexOf( '.' );

                        if( extPos >= 0 ) {
                            String ext = filename.substring( extPos );

                            extensions.add( ext );
                            }

                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed( Path file,
                            IOException exc ) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory( Path dir,
                            IOException exc ) throws IOException
                    {
                        return FileVisitResult.CONTINUE;
                    }});

        for( String ext : extensions ) {
            String extension = ext.substring( 1 );
            System.out.println( "    zzz_" + extension
                    + "( new IgnoreCaseExtensionsFileFilter( \"" + extension +"\" ) ),"
                    );
            }
    }
    private static void moveTo( File file, Path outputPath ) throws IOException
    {
        final Path source = file.toPath();
        final Path target = outputPath.resolve( file.getName() );

        moveTo( source, target );
    }

    private static void moveTo( Path source, Path baseOuputPath, FileType fileType ) throws IOException
    {
        final Path outputPath = baseOuputPath.resolve( fileType.getFolder() );
        final Path target     = outputPath.resolve( source.getFileName());

        moveTo( source, target );
    }

    private static void moveTo( Path source, Path target ) throws IOException
    {
        Path targetParentPath = target.getParent();

        if( ! Files.isDirectory( targetParentPath ) ) {
            Files.createDirectories( targetParentPath, createDirectoriesFileAttributes );
            }


        if( ! Files.exists( target ) ) {
            System.out.println( "Move " + source + " TO " + target );

            move( source, target );
            }
        else {
            String filename = target.toFile().getName();
            int    extPos   = filename.lastIndexOf( '.' );
            String ext;
            String name;

            if( extPos >= 0 ) {
                ext  = filename.substring( extPos );
                name = filename.substring( 0, extPos );
                }
            else {
                ext  = StringHelper.EMPTY;
                name = filename;
                }

            System.out.println( "*** Move" + source + " TO " + target + " need rename" );

            for( int i = 2; i<RENAME_MAX_NUMBER; i++ ) {
                 Path newTarget = targetParentPath.resolve( name + '(' + i + ')' + ext );

                 //System.out.println( "*** Try" + newTarget );
                 if( ! Files.exists( newTarget ) ) {
                    System.out.println( "MoveAndRename " + source + " TO " + newTarget );

                    move( source, newTarget );
                    break;
                    }
                }
            }
    }

    private static void move( Path source, Path target ) throws IOException
    {
        try {
            Files.move( source, target );
            }
        catch( NoSuchFileException e ) {
            System.err.println( "*** " + source + " : " + e.getMessage() );
            }
    }
}
