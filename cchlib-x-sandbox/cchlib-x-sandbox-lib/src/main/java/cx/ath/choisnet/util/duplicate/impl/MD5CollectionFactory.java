/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/MD5CollectionFactory.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.impl.MD5CollectionFactory
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.impl.MD5CollectionFactory
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.MD5CollectionFactory
**
*/
package cx.ath.choisnet.util.duplicate.impl;

import java.io.File;
import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.duplicate.MD5Collection;

/**
** <p>
** Construction d'un objet {@link MD5Collection}
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see MD5Collection
*/
public abstract class MD5CollectionFactory
    implements MD5Tree.ExceptionHandler
{

/**
**
*/
public MD5Collection getMD5Collection( // ---------------------------------
    final File folder
    )
    throws java.io.IOException
{
 final MD5Tree md5tree = new MD5Tree();

 try {
    md5tree.load( folder, this );
    }
 catch( java.io.IOException inattendue ) {
    throw new RuntimeException( "inattendue !", inattendue );
    }

 return new MD5CollectionImpl( md5tree, folder );
}

/**
**
*/
@Override
public abstract void handleIOException( // --------------------------------
        File                file,
        java.io.IOException cause
        )
    throws java.io.IOException;

} // class
