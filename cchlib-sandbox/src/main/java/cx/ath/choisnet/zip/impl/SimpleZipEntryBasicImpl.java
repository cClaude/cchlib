/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/zip/impl/SimpleZipEntryBasicImpl.java
** Description   :
** Encodage      : ANSI
**
**  2.01.020 2005.10.20 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.zip.impl.SimpleZipEntryBasicImpl
**
*/
package cx.ath.choisnet.zip.impl;

import cx.ath.choisnet.zip.SimpleZipEntry;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
** <p>
** Implémentation minimal de {@link SimpleZipEntry}
** </p>
**
** @author Claude CHOISNET
** @since   2.01.020
** @version 2.01.020
*/
public class SimpleZipEntryBasicImpl
    implements SimpleZipEntry
{
private InputStream inputStream;
private ZipEntry    zipEntry;

/**
**
*/
public SimpleZipEntryBasicImpl( // ----------------------------------------
    InputStream inputStream,
    ZipEntry    zipEntry
    )
{
 this.inputStream   = inputStream;
 this.zipEntry      = zipEntry;
}

/**
**
*/
public SimpleZipEntryBasicImpl( // ----------------------------------------
    InputStream inputStream,
    String      zipEntryName
    )
{
 this( inputStream, new ZipEntry( zipEntryName ) );
}

/**
**
*/
public ZipEntry getZipEntry() // ------------------------------------------
{
 return zipEntry;
}

/**
**
*/
public InputStream getInputStream() // ------------------------------------
{
 return inputStream;
}

} // class

