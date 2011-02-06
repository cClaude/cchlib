package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import cx.ath.choisnet.util.CancelRequestException;
import cx.ath.choisnet.util.HashMapSet;
import cx.ath.choisnet.util.checksum.MessageDigestFile;

/**
 * Perform 2 pass to check duplicate.
 * <p>
 * Do not use {@link #add(Iterable)} method if you plan
 * to call more it than one time (without clearing {@link #clear()}).
 * <br/>
 * The most efficient ways to use this object is to
 * call {@link #pass1Add(Iterable)} for each
 * Iterable<File> (directory) you need to explore, and
 * then call {@link #pass2()} once.
 * <br/>
 * If can't respect this rules, you must call
 * {@link #removeNonDuplicate()} before getting
 * duplicate list of file {@link #getFiles()}.
 * <br/>
 * To use more than one this this object, call
 * {@link #clear()}
 *
 * @author Claude CHOISNET
 */
public class DuplicateFileCollector
    extends DefaultDigestFileCollector
{
    private static final long serialVersionUID = 2L;
    /** @serial */
    private HashMapSet<Long,File> mapLengthFiles;
    /** @serial */
    private boolean ignoreEmptyFile;
    /** @serial */
    private boolean cancelProcess;
    /** @serial */
    private boolean alreadyCallPass2;

    /**
     * Create a new DuplicateFileCollector
     *
     * @param messageDigestFile MessageDigestFile to use
     * to compute hash code for files.
     * @param ignoreEmptyFile ignore empty files (length = 0) if true.
     */
    public DuplicateFileCollector(
            MessageDigestFile   messageDigestFile,
            boolean             ignoreEmptyFile
            )
    {
        super( messageDigestFile );
        this.mapLengthFiles = new HashMapSet<Long,File>();
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
    public void add( Iterable<File> files )
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
    synchronized
    public void pass1Add(Iterable<File> files)
    {
        if( this.alreadyCallPass2 ) {
            throw new IllegalStateException();
        }
        for(File f:files) {
            long size = f.length();

            if( ignoreEmptyFile && size == 0 ) {
                continue;
            }

            mapLengthFiles.add( new Long(size), f );

            if( cancelProcess ) {
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
//     * Warning:<br/>
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
     * Warning:<br/>
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

        for(Set<File> s:mapLengthFiles.values()) {
            if( s.size() > 1 ) {
                c += s.size();
                for(File f:s) {
                    l += f.length();
                }
            }
        }
        final int  files  = c;
        final long length = l;

        return new Stats()
        {
            @Override
            public int getPass2Files()
            {
                return files;
            }
            @Override
            public long getPass2Bytes()
            {
                return length;
            }
        };
    }

    /**
     * Perform second pass.
     *
     * @see MessageDigestFile#compute(File)
     * @throws IllegalStateException if {@link #pass2()} already call.
     */
    synchronized public void pass2()
    {
        if( this.alreadyCallPass2 ) {
            throw new IllegalStateException();
        }

        this.alreadyCallPass2 = true;

        if( cancelProcess ) {
            internalClear();
            return;
        }

        for(Set<File> s:mapLengthFiles.values()) {
            if( s.size() > 1 ) {
                for(File f:s) {
                    notify( f );

                    try {
                        mdf.compute( f, listeners );

                        String    k = mdf.digestString();
                        Set<File> c = super.mapHashFile.get( k );

                        if( c == null ) {
                            c = new HashSet<File>();
                            super.mapHashFile.put( k, c );
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
                    catch(IOException e) {
                        notify(e,f);
                        }
                    catch( CancelRequestException e ) {
                        setCancelProcess( true );
                        }

                    // Done with current file (or cancel)
                    // Check cancel state
                    if( cancelProcess ) {
                        internalClear();
                        return;
                    }
                }
            }
        }

        mapLengthFiles.clear();
        super.removeNonDuplicate();
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
        alreadyCallPass2 = false;
    }

    // Should not stop cancel here, ensure cancel
    // process will continue.
    private void internalClear()
    {
        mapLengthFiles.deepClear();
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
    public void setCancelProcess(boolean cancel)
    {
        cancelProcess = cancel;
    }

    /**
     * TODO: Doc !
     *
     * @return true if
     */
    public boolean isCancelProcess()
    {
        return cancelProcess;
    }


    public interface Stats
    {
        /**
         * Returns numbers of files need to be read by pass2
         * @return numbers of files need to be read by pass2
         */
        public int getPass2Files();

        /**
         * Returns numbers of bytes need to be read by pass2
         * @return numbers of bytes need to be read by pass2
         */
        public long getPass2Bytes();
    }
}
