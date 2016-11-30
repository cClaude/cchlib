package com.googlecode.cchlib.util.zip;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.zip.ZipEntryBuilder.Reference;

/**
 * {@link Wrappable} object to transform {@link File} into {@link SimpleZipEntry}
 *
 * @deprecated use {@link SimpleZipWrapperFactory} instead
 */
@Deprecated
@SuppressWarnings("squid:S1133") // Deprecated code should be removed eventually
public class DefaultSimpleZipWrapper
    implements Wrappable<File,SimpleZipEntry>
{
    private final Reference reference;

    /**
     * Create a DefaultSimpleZipWrapper based on directory {@link File} object
     *
     * @param refFolderFile Directory {@link File}
     * @throws IOException If an I/O error occurs, which is
     *         possible because the construction of the canonical
     *         pathname may require filesystem queries
     * @throws SecurityException If a required system property value
     *         cannot be accessed, or if a security manager exists
     *         and its java.lang.SecurityManager.checkRead method
     *         denies read access to the file
     * @deprecated use {@link SimpleZipWrapperFactory#wrapperFromFolder(File)} instead
     */
    @Deprecated
    public DefaultSimpleZipWrapper( final File refFolderFile )
        throws IOException
    {
        this.reference = ZipEntryBuilder.computeReference( refFolderFile );
    }

    /**
     * Create a DefaultSimpleZipWrapper based on directory file name
     *
     * @param refFolderName Directory file name
     * @throws IOException If an I/O error occurs, which is
     *         possible because the construction of the canonical
     *         pathname may require filesystem queries
     * @throws SecurityException If a required system property value
     *         cannot be accessed, or if a security manager exists
     *         and its java.lang.SecurityManager.checkRead method
     *         denies read access to the file
     * @deprecated use {@link SimpleZipWrapperFactory#wrapperFromFolder(File)} instead
     */
    @Deprecated
    public DefaultSimpleZipWrapper( final String refFolderName )
        throws IOException
    {
        this( new File( refFolderName ) );
    }

    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    @Deprecated
    public SimpleZipEntry wrap( final File file ) throws WrapperException
    {
        try {
            return wrapper( file );
            }
        catch( final IOException e ) {
            throw new WrapperException( e );
            }
    }

    @Deprecated
    private SimpleZipEntry wrapper( final File file ) throws IOException
    {
        return new SimpleZipEntryImpl( this.reference, file );
    }
}
