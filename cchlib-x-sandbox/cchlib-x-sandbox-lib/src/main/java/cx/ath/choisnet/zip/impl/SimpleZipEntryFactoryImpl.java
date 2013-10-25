/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/zip/impl/SimpleZipEntryFactoryImpl.java
** Description   :
** Encodage      : ANSI
**
**  2.01.019 2005.10.18 Claude CHOISNET - Version initiale
**  2.01.020 2005.10.19 Claude CHOISNET
**                      Refonte de la classe.
**                      G�re le cas des dossiers.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.zip.impl.SimpleZipEntryFactoryImpl
**
*/
package cx.ath.choisnet.zip.impl;

import cx.ath.choisnet.zip.SimpleZipEntry;
import java.io.File;
import java.util.zip.ZipEntry;

/**
** <p>
** </p>
**
** @author Claude CHOISNET
** @since   2.01.019
** @version 2.01.020
*/
public class SimpleZipEntryFactoryImpl
    implements cx.ath.choisnet.util.Wrappable<File,SimpleZipEntry>
{
/** */
private String refFolder;

/** */
private int refFolderLen;

/**
**
*/
public SimpleZipEntryFactoryImpl( File refFolderFile ) // -----------------
{
 this.refFolder     = refFolderFile.getAbsolutePath().replace( '\\', '/' ) + "/";
 this.refFolderLen  = this.refFolder.length();
}

/**
**
*/
public SimpleZipEntryFactoryImpl( String refFolder ) // -------------------
{
 this( new File( refFolder ) );
}

/**
**
*/
public SimpleZipEntry wrappe( File file ) // ------------------------------
{
 String name = file.getPath().replace( '\\', '/' );

//System.out.println( "this.refFolder " + this.refFolder );
//System.out.println( "name           " + name );

 if( file.isAbsolute() && name.startsWith( this.refFolder ) ) {
    name = name.substring( refFolderLen );
    }

// name = name.replace( '\\', '/' );

 if( file.isDirectory() ) {
    if( ! name.endsWith( "/" ) ) {
        name += '/';
        }
    }

//System.out.println( "name " + name );

 final ZipEntry zipEntry = new ZipEntry( name );

 zipEntry.setTime( file.lastModified() );

 return new SimpleZipEntryImpl( file, zipEntry );
}

} // class

