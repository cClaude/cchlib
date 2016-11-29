package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.HashMapSet;

/**
 * Perform 2 pass to check duplicate.
 * <p>
 * Do not use {@link #add(Iterable)} method if you plan
 * to call more it than one time (without clearing {@link #clear()}).
 * <BR>
 * The most efficient ways to use this object is to
 * call {@link #pass1Add(Iterable)} for each
 * Iterable&lt;File&gt; (directory) you need to explore, and
 * then call {@link #pass2()} once.
 * <BR>
 * If can't respect this rules, you must call
 * {@link #removeNonDuplicate()} before getting
 * duplicate list of file {@link #getFiles()}.
 * <BR>
 * To use more than one this this object, call
 * {@link #clear()}
 * @deprecated use {@link DuplicateFileFinder} instead.
 */
@Deprecated
public class XDuplicateFileCollector
    extends XDefaultDigestFileCollector
{
    private static final long serialVersionUID = 2L;
    /** @serial */
    private final HashMapSet<Long,File> mapLengthFiles;
    /** @serial */
    private final boolean ignoreEmptyFile;
    /** @serial */
    private boolean cancelProcess;
    /** @serial */
    private boolean alreadyCallPass2;

    /**
     * Create a new DuplicateFileCollector
     *
     * @param xMessageDigestFile MessageDigestFile to use
     * to compute hash code for files.
     * @param ignoreEmptyFile ignore empty files (length = 0) if true.
     */
    public XDuplicateFileCollector(
        final XMessageDigestFile   xMessageDigestFile,
        final boolean             ignoreEmptyFile
        )
    {
        super( xMessageDigestFile );

        this.mapLengthFiles = new HashMapSet<>();
        this.ignoreEmptyFile = ignoreEmptyFile;
        this.cancelProcess = false;
        this.alreadyCallPass2 = false;
    }

    /**
     * <p>
     * Perform full process on files.
     * </p>
     * <p>
     * Be sure to read general informations if you
     * use this method.
     * </p>
     * <p>
     *  It first call {@link #pass1Add(Iterable)} and
     *  then {@link #pass2()}.
     * </p>
     * @param files
     * @throws IllegalStateException if {@link #pass2()} already call.
    */
    @Override
    public void add( final Iterable<File> files )
    {
        pass1Add(files);
        pass2();
    }

    /**
     * Perform first pass. Just store (length,file) couple,
     * into internal Map.
     * <p>
     *  Collect all files that you want to inspect. You can
     *  call this method many times before
     *  calling {@link #pass2()}.
     * </p>
     *
     * @param files file Iterable (collection) to add.
     * @throws IllegalStateException if {@link #pass2()} already call.
     * @see #pass2()
     */
    public synchronized void pass1Add(final Iterable<File> files)
    {
        if( this.alreadyCallPass2 ) {
            throw new IllegalStateException();
        }
        for(final File f:files) {
            final long size = f.length();

            if( this.ignoreEmptyFile && (size == 0) ) {
                continue;
            }

            this.mapLengthFiles.add( Long.valueOf( size ), f );

            if( this.cancelProcess ) {
                internalClear();
                return;
            }
        }
    }

//    /**
//     * Returns number of Set of File that should be examined
//     * by {@link #pass2()}. Return potential duplicate
//     * Set.
//     * <p>
//     * Warning:<BR>
//     * This value is only valid after doing all
//     * call to {@link #pass1Add(Iterable)} and
//     * before calling {@link #pass2()}.
//     * </p>
//     *
//     * @return number of Set of File that should be examined
//     * by {@link #pass2()}.
//     */
//    public int getPass1SetToCheckSize()
//    {
//        return mapLengthFiles.size();
//    }

    /**
     * Returns intermediate informations, statistics
     * informations on files that should be examined
     * by {@link #pass2()}.
     * <p>
     * Warning:<BR>
     * This value is only valid after doing all
     * call to {@link #pass1Add(Iterable)} and
     * before calling {@link #pass2()}.
     * </p>
     *
     * @return Statistics informations on files that
     *         should be examined by {@link #pass2()}.
     */
    public Stats getPass1Stats()
    {
        int  c = 0;
        long l = 0;

        for(final Set<File> s:this.mapLengthFiles.values()) {
            if( s.size() > 1 ) {
                c += s.size();
                for(final File f:s) {
                    l += f.length();
                }
            }
        }
        final int  files  = c;
        final long length = l;

        return new PStats( length, files );
    }

    /**
     * Perform second pass.
     *
     * @see XMessageDigestFile#compute(File)
     * @throws IllegalStateException if {@link #pass2()} already call.
     */
    public synchronized void pass2()
    {
        if( this.alreadyCallPass2 ) {
            throw new IllegalStateException();
        }

        this.alreadyCallPass2 = true;

        if( this.cancelProcess ) {
            internalClear();
            return;
        }

        for( final Set<File> set : this.mapLengthFiles.values() ) {
            if( set.size() > 1 ) {
                for( final File file : set ) {
                    handlePass2ForFile( file );

                    // Done with current file (or cancel)
                    // Check cancel state
                    if( this.cancelProcess ) {
                        internalClear();
                        return;
                    }
                }
            }
        }

        this.mapLengthFiles.clear();
        super.removeNonDuplicate();
    }

    private void handlePass2ForFile( final File f )
    {
        notify( f );

        try {
            this.mdf.compute( f, this.listeners );

            final String key = this.mdf.digestString();
            Set<File>    set = super.mapHashFile.get( key );

            if( set == null ) {
                   set = new HashSet<>();
                super.mapHashFile.put( key, set );
                }
            else {
                if( set.size() < 2 ) {
                    this.duplicateSetsCount++;
                    this.duplicateFilesCount += 2;
                    }
                else {
                    this.duplicateFilesCount++;
                    }
                }
               set.add( f );
            }
        catch(final IOException e) {
            notify(e,f);
            }
        catch( final CancelRequestException e ) { // $codepro.audit.disable logExceptions
            setCancelProcess( true );
            }
    }

    /**
     * Clear internals object, to be ready
     * to use a other time.
     */
    @Override
    public void clear()
    {
        internalClear();
        setCancelProcess(false);
        this.alreadyCallPass2 = false;
    }

    // Should not stop cancel here, ensure cancel
    // process will continue.
    private void internalClear()
    {
        this.mapLengthFiles.deepClear();
        super.clear();
    }

    /**
     * Define if process should be canceled.
     * <p>
     * This method must be call by a other thread
     * to cancel as soon as possible current tasks,
     * and then invoke {@link #clear()}, since currents
     * values can not be used.
     * </p>
     * <p>
     * You must set to false, before reuse this
     * object after setting cancel to true.
     * </p>
     * @param cancel true, ask process to cancel.
     * @see #clear()
     */
    public void setCancelProcess(final boolean cancel)
    {
        this.cancelProcess = cancel;
    }

    /**
     * @return true if cancel was set by {@link #setCancelProcess(boolean)}
     */
    public boolean isCancelProcess()
    {
        return this.cancelProcess;
    }

    private static final class PStats implements Stats
    {
        private final long length;
        private final int  files;

        private PStats( final long length, final int files )
        {
            this.length = length;
            this.files = files;
        }

        @Override
        public int getPass2Files()
        {
            return this.files;
        }

        @Override
        public long getPass2Bytes()
        {
            return this.length;
        }
    }


    public interface Stats
    {
        /**
         * Returns numbers of files need to be read by pass2
         * @return numbers of files need to be read by pass2
         */
        int getPass2Files();

        /**
         * Returns numbers of bytes need to be read by pass2
         * @return numbers of bytes need to be read by pass2
         */
        long getPass2Bytes();
    }
}
