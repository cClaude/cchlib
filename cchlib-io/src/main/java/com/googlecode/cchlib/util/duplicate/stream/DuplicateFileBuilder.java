package com.googlecode.cchlib.util.duplicate.stream;

import com.googlecode.cchlib.NeedDoc;
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
            final boolean       ignoreEmptyFile
            )
        {
            return ( ) -> {
                final Stream<File> workStream;

                if( ignoreEmptyFile ) {
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
    public static PrepareDuplicateFile createFromCollection( final Collection<File> files, final boolean ignoreEmptyFile )
    {
        return newInstance( files.parallelStream(), ignoreEmptyFile );
    }

    @NeedDoc
    public static PrepareDuplicateFile createFromStreamOfFiles( final Stream<File> filesStream, final boolean ignoreEmptyFile)
    {
        return newInstance( filesStream, ignoreEmptyFile );
    }

    public static PrepareDuplicateFile createFromStreamOfPaths( final Stream<Path> pathsStream, final boolean ignoreEmptyFile )
    {
        return newInstance( pathsStream.map( p -> p.toFile() ), ignoreEmptyFile );
    }

    public static PrepareDuplicateFile createFromFileVisitor(
            final FileVisitor<Path>     visitor,
            final Set<FileVisitOption>  options,
            final int                   maxDepth,
            final Path...               startPaths
            ) throws IOException
    {
        final FileVisitorCollector<Path> visitorDelegator = new FileVisitorCollector<>( visitor );

        for( final Path start : startPaths ) {
            Files.walkFileTree(start, options, maxDepth, visitorDelegator);
        }

        return newInstance( visitorDelegator.toList().parallelStream().map( Path::toFile ), false );
    }

    public static PrepareDuplicateFile createFromFileVisitor(
            final FileVisitor<Path>     visitor,
            final Path...               startPaths
            ) throws IOException
    {
        return createFromFileVisitor( visitor, EnumSet.noneOf( FileVisitOption.class ), Integer.MAX_VALUE, startPaths );
    }

}
