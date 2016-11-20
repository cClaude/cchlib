/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/zip/SimpleZipEntry.java
** Description   :
** Encodage      : ANSI
**
**  2.01.019 2005.10.18 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.zip.SimpleZipEntry
**
*/
package cx.ath.choisnet.zip;

import java.util.zip.ZipEntry;
import java.io.InputStream;

/**
** <p>
** </p>
**
** @author Claude CHOISNET
** @since   2.01.019
** @version 2.01.019
**
** @see cx.ath.choisnet.zip.impl.SimpleZipEntryFactoryImpl
*/
public interface SimpleZipEntry
{

/** */
public ZipEntry getZipEntry(); // -----------------------------------------

/** */
public InputStream getInputStream() // ------------------------------------
    throws java.io.IOException;

} // class

