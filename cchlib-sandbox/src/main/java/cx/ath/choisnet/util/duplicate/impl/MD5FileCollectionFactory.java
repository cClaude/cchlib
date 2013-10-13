/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/MD5FileCollectionFactory.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                          cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionFactory
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionFactory
**
*/
package cx.ath.choisnet.util.duplicate.impl;

import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.duplicate.MD5Collection;
import cx.ath.choisnet.util.duplicate.MD5FileCollection;
import java.io.File;

/**
** <p>
** Construction d'un objet {@link MD5FileCollection}
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see MD5FileCollection
*/
public abstract class MD5FileCollectionFactory
    extends MD5CollectionFactory
{

/**
**
*/
public MD5FileCollection getMD5FileCollection( // -------------------------
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

 final MD5FileCollectionImpl instance = new MD5FileCollectionImpl();

 instance.add( md5tree );

 return instance;
}

} // class

