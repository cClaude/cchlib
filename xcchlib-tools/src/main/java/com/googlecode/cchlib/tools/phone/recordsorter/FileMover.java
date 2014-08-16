package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class FileMover {

    private static final Logger LOGGER = Logger.getLogger( FileMover.class );

    private final File          destinationFolder;
    private final ContactFolder contactFolder;


    public FileMover( final File destinationFolder ) throws CreateDestinationFolderException
    {
        this.destinationFolder = destinationFolder;
        this.contactFolder     = new ContactFolder();

        createDestinationFolder();
    }

    private void createDestinationFolder() throws CreateDestinationFolderException
    {
        try {
            Files.createDirectories( this.destinationFolder.toPath() );
        }
        catch( final IOException e ) {
            throw new CreateDestinationFolderException( this.destinationFolder.getPath(), e );
        }
    }

    public void move( final Path file, final Contact contact ) throws FileMoverException
    {
        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "moveFile : " + file + " to " + contact + " using " + this.destinationFolder );
        }

        final Path target = getTarget( file, contact );

        try {
            //Files.move( file, getTarget( file, contact ), StandardCopyOption.COPY_ATTRIBUTES );
             Files.move( file, target );
        }
        catch( final FileAlreadyExistsException e ) {
            throw new FileMoverException( "FileAlreadyExistsException", file, contact, target, e );
        }
        catch( final IOException e ) {
            throw new FileMoverException( "IOException", file, contact, target, e );
        }
    }

    private Path getTarget( final Path file, final Contact contact )
    {
        final File directory = new File( this.destinationFolder, contactFolder.getFolderName( contact ) );

        if( ! directory.isDirectory() ) {
            directory.mkdirs();
        }

        final File newFile = new File( directory, file.toFile().getName() );

        return newFile.toPath();
    }

}
