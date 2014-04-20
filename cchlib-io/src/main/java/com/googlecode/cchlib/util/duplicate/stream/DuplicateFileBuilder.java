package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public final class DuplicateFileBuilder
{
    private final static class FileVisitorCollector<T> implements FileVisitor<T> {

        private final FileVisitor<T> visitor;
        private final List<T> collector = new ArrayList<>();

        public FileVisitorCollector( final FileVisitor<T> visitor )
        {
            this.visitor = visitor;
        }

        public List<T> toList()
        {
            return collector;
        }

        @Override
        public FileVisitResult preVisitDirectory( final T dir, final BasicFileAttributes attrs ) throws IOException
        {
            return visitor.preVisitDirectory( dir, attrs );
        }

        @Override
        public FileVisitResult visitFile( final T file, final BasicFileAttributes attrs ) throws IOException
        {
            final FileVisitResult result = visitor.visitFile( file, attrs );

            if( result == FileVisitResult.CONTINUE || result == FileVisitResult.TERMINATE) {
                collector.add( file );
            }
            return result;
        }

        @Override
        public FileVisitResult visitFileFailed( final T file, final IOException exc ) throws IOException
        {
            return visitor.visitFileFailed( file, exc );
        }

        @Override
        public FileVisitResult postVisitDirectory( final T dir, final IOException exc ) throws IOException
        {
            return visitor.postVisitDirectory( dir, exc );
        }
    }

    private static PrepareDuplicateFile newInstance(
            final Stream<File>  filesStream,
            final boolean       ignoreEmptyFiles
            )
        {
            return ( ) -> {
                final Stream<File> workStream;

                if( ignoreEmptyFiles ) {
                    workStream = filesStream.filter( f -> f.length() > 0 );
                } else {
                    workStream = filesStream;
                }

                if( workStream.isParallel() ) {
                    return workStream.collect( Collectors.groupingByConcurrent(File::length, ConcurrentHashMap::new, Collectors.toSet()));
                } else  {
                    return workStream.collect( Collectors.groupingBy(File::length, HashMap::new, Collectors.toSet()));
                }
            };
        }

    @NeedDoc
    public static PrepareDuplicateFile createFromCollection( final Collection<File> files, final boolean ignoreEmptyFiles )
    {
        return newInstance( files.parallelStream(), ignoreEmptyFiles );
    }

    @NeedDoc
    public static PrepareDuplicateFile createFromStreamOfFiles( final Stream<File> filesStream, final boolean ignoreEmptyFiles)
    {
        return newInstance( filesStream, ignoreEmptyFiles );
    }

    @NeedDoc
    public static PrepareDuplicateFile createFromStreamOfPaths( final Stream<Path> pathsStream, final boolean ignoreEmptyFiles )
    {
        return newInstance( pathsStream.map( p -> p.toFile() ), ignoreEmptyFiles );
    }

    @NeedDoc
    public static PrepareDuplicateFile createFromFileVisitor(
            final FileVisitor<Path>     visitor,
            final boolean               ignoreEmptyFiles,
            final Path...               startPaths
            ) throws IOException
    {
        return createFromFileVisitor( visitor, EnumSet.noneOf( FileVisitOption.class ), Integer.MAX_VALUE, ignoreEmptyFiles, startPaths );
    }

    @NeedDoc
    public static PrepareDuplicateFile createFromFileVisitor(
            final FileVisitor<Path>     visitor,
            final Set<FileVisitOption>  options,
            final int                   maxDepth,
            final boolean               ignoreEmptyFiles,
            final Path...               startPaths
            ) throws IOException
    {
        final FileVisitorCollector<Path> visitorDelegator = new FileVisitorCollector<>( visitor );

        for( final Path start : startPaths ) {
            Files.walkFileTree(start, options, maxDepth, visitorDelegator);
        }

        return newInstance( visitorDelegator.toList().parallelStream().map( Path::toFile ), ignoreEmptyFiles );
    }

    @NeedDoc
    public static PrepareDuplicateFile createFromFileVisitor( //
            final Stream<Path>              startPathsStream, //
            final FileVisitor<Path>         visitor, //
            final EnumSet<FileVisitOption>  options, //
            final int                       maxDepth, //
            final boolean                   ignoreEmptyFiles //
            ) throws IOException
    {
        final FileVisitorCollector<Path> visitorDelegator = new FileVisitorCollector<>( visitor );

        startPathsStream.forEach( p -> {
            try {
                Files.walkFileTree( p, options, maxDepth, visitorDelegator );
            }
            catch( final IOException walkIOE ) {
                try {
                    visitor.visitFileFailed( p, walkIOE );
                }
                catch( final Exception visitorIOE ) {
                    // Should not occur
                    visitorIOE.printStackTrace();
                }
            }
        } );

        return newInstance( visitorDelegator.toList().parallelStream().map( Path::toFile ), ignoreEmptyFiles );
    }

}
