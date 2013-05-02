package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.util.HashMapSet;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.duplicate.DefaultDigestFileCollector} instead
 */
@Deprecated
public class DefaultDigestFileCollector
    implements DigestFileCollector
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    protected ArrayList<DigestEventListener> listeners = new ArrayList<DigestEventListener>();

    /** @serial */
    protected cx.ath.choisnet.util.checksum.MessageDigestFile mdf;
    /** @serial */
    protected HashMapSet<String,File> mapHashFile;

    /** @serial */
    protected int duplicateSetsCount = 0;
    /** @serial */
    protected int duplicateFilesCount = 0;

    /**
     * @throws NoSuchAlgorithmException
     */
    public DefaultDigestFileCollector()
        throws NoSuchAlgorithmException
    {
        this( new cx.ath.choisnet.util.checksum.MessageDigestFile() );
    }

    /**
     * @param messageDigestFile
     */
    public DefaultDigestFileCollector(
            cx.ath.choisnet.util.checksum.MessageDigestFile messageDigestFile
            )
    {
        this.mdf         = messageDigestFile;
        this.mapHashFile = new HashMapSet<String,File>();
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
     * <br/>
     * The DefaultDigestFileCollector will be empty
     * after this call returns.
     */
    public void deepClear()
    {
        this.mapHashFile.deepClear();
        clear();
    }

    /**
     * <pre>
     *  Iterable<File> files = new {@link FileIterator}(
     *          rootDirectory,
     *          {@link FileFilterHelper#fileFileFilter()}
     *          );
     * </pre>
     *
     * @param files
     * @throws com.googlecode.cchlib.util.CancelRequestException
     * @see DigestEventListener#ioError(IOException, File)
     * @see DigestEventListener#computeDigest(File)
     */
    public void add( final Iterable<File> files ) throws com.googlecode.cchlib.util.CancelRequestException
    {
        for(File f:files) {
            notify( f );

            try {
                mdf.compute( f, listeners );
                }
            catch( FileNotFoundException e ) {
                notify( e, f );
                continue;
                }
            catch( IOException e ) {
                notify( e, f );
                continue;
                }

            String    k = mdf.digestString();
            Set<File> c = mapHashFile.get( k );

            if( c == null ) {
                c = new HashSet<File>();
                mapHashFile.put( k, c );
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
        return mapHashFile;
    }

    @Override
    public int removeDuplicate()
    {
        int                 count = 0;
        Iterator<Set<File>> iter  = mapHashFile.values().iterator();

        while(iter.hasNext()) {
            Set<File> s = iter.next();

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
        int                 count = 0;
        Iterator<Set<File>> iter  = mapHashFile.values().iterator();

        while(iter.hasNext()) {
            Set<File> s = iter.next();

            if( s.size() < 2 ) {
                count += s.size();
                iter.remove();
            }
        }

        return count;
    }

    /**
     * Update values return by {@link #getDuplicateSetsCount()}
     * and {@link #getDuplicateFilesCount()},
     * <br/>
     * Useful, if map return by {@link #getFiles()} is
     * modify.
     */
    public void computeDuplicateCount()
    {
        int cs = 0;
        int cf = 0;

        for(Set<File> s:mapHashFile.values()) {
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
    public void addDigestEventListener( DigestEventListener listener )
    {
        if( listener == null ) {
            throw new NullPointerException();
        }
        listeners.add(listener);
    }

    @Override
    public void removeDigestEventListener( DigestEventListener listener )
    {
        listeners.remove(listener);
    }

    /**
     * Notifies all listener the beginning of
     * the calculation of digest.
     *
     * @param f The file
     */
    protected void notify(File f)
    {
        for(DigestEventListener l:listeners) {
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
    protected void notify(IOException e, File f)
    {
        for(DigestEventListener l:listeners) {
            l.ioError( e, f );
        }
    }
}
