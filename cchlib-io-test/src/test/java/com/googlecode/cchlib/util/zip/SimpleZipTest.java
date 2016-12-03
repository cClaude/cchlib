package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.io.IO;
import com.googlecode.cchlib.util.CollectionHelper;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.iterator.IteratorWrapper;

/**
 * Test {@link SimpleZip} and {@link SimpleUnZip}
 */
public class SimpleZipTest
{
    private static final int  EXPECTED_FILE_COUNT     = 87;
    private static final long EXPECTED_ZIP_MIN_LENGTH = 100_000L;

    private static final Logger LOGGER = Logger.getLogger( SimpleZipTest.class );

    private static final String ZIP_SOURCE_DIRNAME = "src";

    private static final String getZipSourceDirectoryName()
    {
        return ZIP_SOURCE_DIRNAME;
    }

    private static final File getZipSourceDirectoryFile()
    {
        return new File( new File("."), getZipSourceDirectoryName() );
    }

    private static final File getZipDestinationFile()
    {
        return IO.createTempFile( SimpleZipTest.class, "mysrc.zip" );
    }

    private static Iterator<SimpleZipEntry> getEntries(
        final File sourceFolderFile
        ) throws IOException
    {
        final Wrappable<File, SimpleZipEntry> wrapper =
                SimpleZipWrapperFactory.wrapperFromFolder( sourceFolderFile );

        return new IteratorWrapper<>(
            new FileIterator( sourceFolderFile ),
            wrapper
            );
    }

    private static final void delete( final File file ) throws IOException
    {
        // Could crash if file is use by a other process (i.e. git indexing)
        Files.delete( file.toPath() );

        Assertions.assertThat( file ).doesNotExist();
    }

    @Test
    @Deprecated
    public void test_SimpleZip_new_DefaultSimpleZipWrapper() throws IOException
    {
       final LogZipListener listener           = new LogZipListener();
       final File           destinationZipFile = getZipDestinationFile();
       final File           sourceFolderFile   = getZipSourceDirectoryFile();

       try( final FileOutputStream os = new FileOutputStream( destinationZipFile ) ) {
            try( final SimpleZip instance = new SimpleZip( os ) ) {
                instance.addZipListener( listener );
                instance.addFolder(
                        sourceFolderFile,
                        new DefaultSimpleZipWrapper( sourceFolderFile )
                        );
                instance.removeZipListener( listener );
                }
            }

        final long unzipCount = SimpleUnZip.computeFilesCount( destinationZipFile );
        final long zipLength  = destinationZipFile.length();

        delete( destinationZipFile );

        LOGGER.info( "Zip " + listener.getCount() + " items" );
        LOGGER.info( "unzipCount = " + unzipCount );

        Assertions.assertThat( listener.getCount() ).isEqualTo( unzipCount );
        Assertions.assertThat( listener.getCount() ).isEqualTo( EXPECTED_FILE_COUNT );
        Assertions.assertThat( zipLength ).isGreaterThan( EXPECTED_ZIP_MIN_LENGTH );
    }

    @Test
    public void test_SimpleZip_addFolder() throws IOException
    {
        final LogZipListener listener           = new LogZipListener();
        final File           destinationZipFile = getZipDestinationFile();
        final File           sourceFolderFile   = getZipSourceDirectoryFile();

        try( final FileOutputStream os = new FileOutputStream( destinationZipFile ) ) {
            try( final SimpleZip instance = new SimpleZip( os ) ) {
                instance.addZipListener( listener );
                instance.addFolder( sourceFolderFile );
                instance.removeZipListener( listener );
                }
            }

        final long unzipCount = SimpleUnZip.computeFilesCount( destinationZipFile );
        final long zipLength  = destinationZipFile.length();

        delete( destinationZipFile );

        LOGGER.info( "Zip " + listener.getCount() + " items" );
        LOGGER.info( "unzipCount = " + unzipCount );

        Assertions.assertThat( listener.getCount() ).isEqualTo( unzipCount );
        Assertions.assertThat( listener.getCount() ).isEqualTo( EXPECTED_FILE_COUNT );
        Assertions.assertThat( zipLength ).isGreaterThan( EXPECTED_ZIP_MIN_LENGTH );
    }

    @Test
    public void test_SimpleZip_addFolder2() throws IOException
    {
        final LogZipListener listener           = new LogZipListener();
        final File           destinationZipFile = getZipDestinationFile();
        final File           sourceFolderFile   = getZipSourceDirectoryFile();

        final Wrappable<File, SimpleZipEntry> wrapper    = SimpleZipWrapperFactory.wrapperFromFolder( sourceFolderFile );
        final Set<FileVisitOption>            options    = EnumSet.noneOf( FileVisitOption.class );
        final int                             maxDepth   = Integer.MAX_VALUE;
        final boolean                         ignoreRoot = false;

        try( final FileOutputStream os = new FileOutputStream( destinationZipFile ) ) {
            try( final SimpleZip instance = new SimpleZip( os ) ) {
                instance.addZipListener( listener );
                instance.addFolder(
                        sourceFolderFile,
                        wrapper,
                        options,
                        maxDepth,
                        ignoreRoot
                        );
                instance.removeZipListener( listener );
                }
            }

        final long unzipCount = SimpleUnZip.computeFilesCount( destinationZipFile );
        final long zipLength  = destinationZipFile.length();

        delete( destinationZipFile );

        LOGGER.info( "Zip " + listener.getCount() + " items" );
        LOGGER.info( "unzipCount = " + unzipCount );

        Assertions.assertThat( listener.getCount() ).isEqualTo( unzipCount );
        Assertions.assertThat( listener.getCount() ).isEqualTo( EXPECTED_FILE_COUNT + 1 /* root folder */ );
        Assertions.assertThat( zipLength ).isGreaterThan( EXPECTED_ZIP_MIN_LENGTH );
    }

    @Test
    public void test_SimpleZip_Custom_Iterator() throws IOException
    {
        final LogZipListener listener           = new LogZipListener();
        final File           destinationZipFile = getZipDestinationFile();
        final File           sourceFolderFile   = getZipSourceDirectoryFile();

        final List<SimpleZipEntry>     entriesList = CollectionHelper.newList( getEntries( sourceFolderFile ) );
        final Iterator<SimpleZipEntry> entries     = entriesList.iterator();

        try( final FileOutputStream os = new FileOutputStream( destinationZipFile ) ) {
            try( final SimpleZip instance = new SimpleZip( os ) ) {
                instance.addZipListener( listener );
                instance.addAll( entries );
                instance.removeZipListener( listener );
                }
            }

        final long unzipCount = SimpleUnZip.computeFilesCount( destinationZipFile );
        final long zipLength  = destinationZipFile.length();

        delete( destinationZipFile );

        LOGGER.info( "Found " + entriesList.size() + " items" );
        LOGGER.info( "Zip " + listener.getCount() + " items" );
        LOGGER.info( "unzipCount = " + unzipCount );

        Assertions.assertThat( listener.getCount() ).isEqualTo( entriesList.size() );
        Assertions.assertThat( listener.getCount() ).isEqualTo( unzipCount );
        Assertions.assertThat( listener.getCount() ).isEqualTo( EXPECTED_FILE_COUNT );
        Assertions.assertThat( zipLength ).isGreaterThan( EXPECTED_ZIP_MIN_LENGTH );
    }
}
