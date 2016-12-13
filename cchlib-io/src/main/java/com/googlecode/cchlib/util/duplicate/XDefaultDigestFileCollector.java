package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.HashMapSet;

/**
 * @deprecated use {@link DuplicateFileFinder} instead.
 */
@Deprecated
public class XDefaultDigestFileCollector
    implements XDigestFileCollector
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    protected ArrayList<DigestEventListener> listeners = new ArrayList<>();

    /** @serial */
    protected XMessageDigestFile mdf;
    /** @serial */
    protected HashMapSet<String,File> mapHashFile;

    /** @serial */
    protected int duplicateSetsCount = 0;
    /** @serial */
    protected int duplicateFilesCount = 0;

    /**
     * deprecated
     *
     * @throws NoSuchAlgorithmException deprecated
     */
    public XDefaultDigestFileCollector()
        throws NoSuchAlgorithmException
    {
        this( new XMessageDigestFile() );
    }

    /**
     * deprecated
     *
     * @param xMessageDigestFile deprecated
     */
    public XDefaultDigestFileCollector(
            final XMessageDigestFile xMessageDigestFile
            )
    {
        this.mdf         = xMessageDigestFile;
        this.mapHashFile = new HashMapSet<>();
    }

    @Override
    public void clear()
    {
        this.mapHashFile.clear();
        this.duplicateSetsCount = 0;
        this.duplicateFilesCount = 0;
    }

    /**
     * Removes all of the mappings from this
     * DefaultDigestFileCollector, but also perform
     * a {@link Set#clear()} on each set of values.
     * <BR>
     * The DefaultDigestFileCollector will be empty
     * after this call returns.
     */
    public void deepClear()
    {
        this.mapHashFile.deepClear();
        clear();
    }

    /**
     * deprecated
     *
     * <pre>
     *  Iterable&lt;File&gt; files = new {@link FileIterator}(
     *          rootDirectory,
     *          {@link FileFilterHelper#fileFileFilter()}
     *          );
     * </pre>
     *
     * @param files to add
     * @throws CancelRequestException if any listeners ask to cancel operation
     * @see DigestEventListener#ioError(IOException, File)
     * @see DigestEventListener#computeDigest(File)
     */
    public void add( final Iterable<File> files ) throws CancelRequestException
    {
        for(final File f:files) {
            notify( f );

            try {
                this.mdf.compute( f, this.listeners );
                }
            catch( final IOException e ) {
                notify( e, f );
                continue;
            }

            final String    k = this.mdf.digestString();
            Set<File> c = this.mapHashFile.get( k );

            if( c == null ) {
                c = new HashSet<>();
                this.mapHashFile.put( k, c );
                }
            else {
                if( c.size() < 2 ) {
                    this.duplicateSetsCount++;
                    this.duplicateFilesCount += 2;
                    }
                else {
                    this.duplicateFilesCount++;
                    }
                }
            c.add( f );
        }
    }

    @Override
    public Map<String,Set<File>> getFiles()
    {
        return this.mapHashFile;
    }

    @Override
    public int removeDuplicate()
    {
        int                 count = 0;
        final Iterator<Set<File>> iter  = this.mapHashFile.values().iterator();

        while(iter.hasNext()) {
            final Set<File> s = iter.next();

            if( s.size() > 1 ) {
                count += s.size();
                iter.remove();
            }
        }

        this.duplicateSetsCount = 0;
        this.duplicateFilesCount = 0;

        return count;
    }

    @Override
    public int removeNonDuplicate()
    {
        return DuplicateHelpers.removeNonDuplicate( this.mapHashFile );
    }

    /**
     * Update values return by {@link #getDuplicateSetsCount()}
     * and {@link #getDuplicateFilesCount()},
     * <BR>
     * Useful, if map return by {@link #getFiles()} is
     * modify.
     */
    public void computeDuplicateCount()
    {
        int cs = 0;
        int cf = 0;

        for(final Set<File> s:this.mapHashFile.values()) {
            if( s.size() > 1 ) {
                cs++;
                cf += s.size();
            }
        }

        this.duplicateSetsCount = cs;
        this.duplicateFilesCount = cf;
    }

    @Override
    public int getDuplicateSetsCount()
    {
        return this.duplicateSetsCount;
    }

    @Override
    public int getDuplicateFilesCount()
    {
        return this.duplicateFilesCount;
    }

    @Override
    public void addDigestEventListener( final DigestEventListener listener )
    {
        if( listener == null ) {
            throw new NullPointerException();
        }
        this.listeners.add(listener);
    }

    @Override
    public void removeDigestEventListener( final DigestEventListener listener )
    {
        this.listeners.remove(listener);
    }

    /**
     * Notifies all listener the beginning of
     * the calculation of digest.
     *
     * @param f The file
     */
    protected void notify(final File f)
    {
        for(final DigestEventListener l:this.listeners) {
            l.computeDigest( f );
        }
    }

    /**
     * Notifies all listener that an IOException
     * had occur.
     *
     * @param e exception that append.
     * @param f current file.
     */
    protected void notify(final IOException e, final File f)
    {
        for(final DigestEventListener l:this.listeners) {
            l.ioError( e, f );
        }
    }
}
