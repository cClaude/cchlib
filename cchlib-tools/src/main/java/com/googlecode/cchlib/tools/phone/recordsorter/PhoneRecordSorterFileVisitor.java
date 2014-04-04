package com.googlecode.cchlib.tools.phone.recordsorter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.VisibleForTesting;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public final class PhoneRecordSorterFileVisitor implements FileVisitor<Path> {

    private final static Logger LOGGER = Logger.getLogger( PhoneRecordSorterFileVisitor.class );
    //private final static Pattern FILEPATTERN  = Pattern.compile( "[0-9][0-9][0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_(\\w+)_(\\w+)]\\.3gp");
    // private final static Pattern FILEPATTERN  = Pattern.compile( "[0-9][0-9][0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_([0-9]+)_(In|Out)]\\.3gp");
    // private final static Pattern FILEPATTERN_ok = Pattern.compile( "[0-9][0-9][0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_([0-9]+)_In\\.3gp");
    //private final static Pattern FILEPATTERN = Pattern.compile( "[\\d][0-9][0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_[0-9][0-9]_([0-9]+)_In\\.3gp");
    //private final static Pattern FILEPATTERN = Pattern.compile( "[0-9]{4}_[0-9]{2}_[0-9]{2}_[0-9]{2}_[0-9]{2}_([0-9]+)_In\\.3gp");
//    private final static Pattern FILEPATTERN_ok = Pattern.compile( "[0-9]{4}_[0-9]{2}_[0-9]{2}_[0-9]{2}_[0-9]{2}_(\\w+)_In\\.3gp");
//    private final static Pattern FILEPATTERN = Pattern.compile( "[0-9]{4}_[0-9]{2}_[0-9]{2}_[0-9]{2}_[0-9]{2}_(\\w+)_(\\w+)\\.3gp");
    private final static Pattern FILEPATTERN = Pattern.compile( "[0-9]{4}_[0-9]{2}_[0-9]{2}_[0-9]{2}_[0-9]{2}_(\\+{0,1}\\w+)_(\\w+)\\.(\\w+)");

    final private Config config;
    final private NumberNormalizer numberNormalizer;
    final private FileMover fileMover;

    public PhoneRecordSorterFileVisitor(
        final Config config,
        final File   destinationFolder
        )
    {
        this.config             = config;
        this.numberNormalizer   = new NumberNormalizer();
        this.fileMover          = new FileMover( destinationFolder );
    }

    @Override
    public FileVisitResult preVisitDirectory( final Path dir, final BasicFileAttributes attrs
        ) throws IOException
    {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs )
        throws IOException
    {
        final String rawPhoneNumber = getPhoneNumber( file.getFileName().toString() );

        if( rawPhoneNumber == null ) {
            LOGGER.info( "visitFile: can not identify number for file [" + file + ']' );
       } else {
            final String  phoneNumber = this.numberNormalizer.normalizePhoneNumber( rawPhoneNumber );
            final Contact contact     = findContactByNumber( phoneNumber );

            if( contact == null ) {
                LOGGER.info( "visitFile: not contact for " + file.getFileName().toString() + " with number [" + phoneNumber + ']');
            } else {
                try {
                    this.fileMover.move( file, contact );
                }
                catch( FileMoverException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return FileVisitResult.CONTINUE;
    }

    private Contact findContactByNumber( final String phoneNumber )
    {
        if( "hidden".equals( phoneNumber )) {
            return ContactFolder.HIDDEN_CONTACT;
        }

        return this.config.findContactByNumber( phoneNumber );
    }


    @VisibleForTesting
    Matcher getPhoneNumberMatcher( final String filename )
    {
        return FILEPATTERN.matcher( filename );
    }

    @VisibleForTesting
    String getPhoneNumber( final String filename )
    {
        final Matcher matcher = getPhoneNumberMatcher( filename );

        if( matcher.matches() ) {
            return matcher.group( 1 );
        }
        return null;
    }

    @Override
    public FileVisitResult visitFileFailed( final Path file, final IOException exc )
        throws IOException
    {
        LOGGER.warn( "ignore file : " + file, exc );

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory( Path dir, IOException exc )
            throws IOException
    {
        if( exc != null ) {
            LOGGER.warn( "ignore folder : " + dir, exc );
        }

        return FileVisitResult.CONTINUE;
    }
}
