package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.HashMapSet;
import com.googlecode.cchlib.util.duplicate.digest.FileDigest;

/**
 * @since 4.2
 */
public class DFFPass2Impl extends AbstractDFFPass2WithFileDigestListener implements DFFPass2 {

    private static final Logger LOGGER = Logger.getLogger( DFFPass2Impl.class );

    public DFFPass2Impl( final DFFConfig config )
    {
        super( config );
    }

    @Override
    public void find()
    {
        if( getConfig().isCancelProcess() ) {
            return;
        }

        LOGGER.info( "start pass 2" );

        try {
            final Iterator<Map.Entry<Long, Set<File>>> iter = getConfig().getMapLengthFiles().entrySet().iterator();

            while( iter.hasNext() ) {
                final Map.Entry<Long, Set<File>> entry = iter.next();

                if( entry.getValue().size() > 1 ) {
                    find( entry );
                }

                if( getConfig().isCancelProcess() ) {
                    return;
                }

                iter.remove();
            }

            // source must be consume !
            assert getConfig().getMapLengthFiles().size() == 0;
        }
        finally {
            pass2RemoveNoneDuplicate();
        }
    }

    /** Handle all files with same size */
    protected void find( final Map.Entry<Long, Set<File>> entryForThisLength )
    {
        final Set<File>                 setForThisLength    = entryForThisLength.getValue();
        final long                      length              = entryForThisLength.getKey().longValue();
        final HashMapSet<String,File>   mapSetForThisLength = new HashMapSet<>();

        for( final File file : setForThisLength ) {
            final String hashString = handlePass2ForFile( file );

            // if hashString is null, then file is lock or user has cancel task
            if( hashString != null ) {
//                Set<File> setForThisHash = mapSetForThisLength.get( hashString );
//
//                if( setForThisHash == null ) {
//                    setForThisHash = new HashSet<>();
//                    mapSetForThisLength.put( hashString, setForThisHash );
//                }
//
//                setForThisHash.add( file );
                mapSetForThisLength.add( hashString, file );
            }

            // Done with current file (or cancel)
            // Check cancel state
            if( getConfig().isCancelProcess() ) {
                break;
            }
        }

        // Check if we have new duplicates.
        final Iterator<Map.Entry<String, Set<File>>> entryIterator = mapSetForThisLength.entrySet().iterator();

        while( entryIterator.hasNext() ) {
            final Map.Entry<String, Set<File>> entry = entryIterator.next();
            final Set<File>                    set   = entry.getValue();

            if( set.size() > 1 ) {
                final String hashString = entry.getKey();

                assert hashString != null : "hashString is null";
                assert ! getConfig().getMapHashFiles().containsKey( hashString ) : getAssertErrorMsg( hashString, getConfig().getMapHashFiles().get( hashString ), set );

                getConfig().getMapHashFiles().put( hashString, set );
                getConfig().duplicateSetsCountAdd( 1 );
                getConfig().duplicateFilesCountAdd( set.size() );
                getConfig().duplicateBytesCountAdd( set.size() * length );
            } else {
                set.clear();
            }
            entryIterator.remove();
        }
    }

    private String getAssertErrorMsg( final String hashString, final Set<File> set1, final Set<File> set2 )
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Error already found hash for a other size !\n" );
        sb.append( hashString );
        sb.append( "\n => \n" );
        sb.append( set1 );
        sb.append( "\n <=> \n" );
        sb.append( set2 );

        return sb.toString();
    }

    /** handle pass 2 for a single file (file per file) */
    private String handlePass2ForFile( final File file )
    {
        notify_analysisStart( file );

        String hashString = null;

        try {
            final FileDigest fd = getConfig().getFileDigest();

            fd.computeFile( file, getFileDigestListener() );

            hashString = fd.digestString();
            }
        catch( final IOException ioe ) {
            notify_ioError( file, ioe );
            }
        catch( final CancelRequestException e ) { // $codepro.audit.disable logExceptions
            getConfig().setCancelProcess( true );
            }
        finally {
            notify_analysisDone( file, hashString );
        }
        return hashString;
    }

    private void pass2RemoveNoneDuplicate()
    {
        final int count = DuplicateHelpers.removeNonDuplicate( getConfig().getMapHashFiles() );

        assert count == 0;
    }
}
