/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5CollectionXMLException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.MD5CollectionXMLException
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.MD5CollectionXMLException
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5CollectionXMLException
**
*/
package cx.ath.choisnet.util.duplicate;


/**
** <p>
** Exception déclancher lors d'un problème lors de la création d'un
** objet {@link MD5CollectionXML}
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see MD5Collection
** @see MD5CollectionXML
*/
public class MD5CollectionXMLException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public MD5CollectionXMLException( final String msg ) // -------------------
{
 super( msg );
}

/**
**
*/
public MD5CollectionXMLException( // --------------------------------------
    final String    msg,
    final Throwable cause
    )
{
 super( msg, cause );
}

} // class
