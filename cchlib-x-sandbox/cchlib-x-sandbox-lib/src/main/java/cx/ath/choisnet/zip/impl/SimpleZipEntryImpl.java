/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/zip/impl/SimpleZipEntryImpl.java
** Description   :
** Encodage      : ANSI
**
**  2.01.019 2005.10.18 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.zip.impl.SimpleZipEntryImpl
**
*/
package cx.ath.choisnet.zip.impl;

import cx.ath.choisnet.zip.SimpleZipEntry;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
** <p>
** </p>
**
** @author Claude CHOISNET
** @since   2.01.019
** @version 2.01.019
*/
public class SimpleZipEntryImpl
    implements SimpleZipEntry
{
private File file;
private ZipEntry zipEntry;

/**
**
*/
public SimpleZipEntryImpl( File file, ZipEntry zipEntry ) // --------------
{
 this.file      = file;
 this.zipEntry  = zipEntry;
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
    throws java.io.FileNotFoundException
{
 return new BufferedInputStream( new FileInputStream( file ) );
}

} // class

