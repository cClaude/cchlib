/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/lang/reflect/AbstractMappable.java
** Description   :
** Encodage      : ANSI
**
**  2.01.001 2005.10.24 Claude CHOISNET - version initiale
**  2.01.028 2005.11.09 Claude CHOISNET
**                      Adaptation à la nouvelle méthode
**                      MappableHelper#toMap permettant de définir le nom
**                      des méthodes à analyser.
**  3.01.012 2006.03.27 Claude CHOISNET
**                      La méthode toXML() a été supprimée, n'ayant rien
**                      à faire ici...
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.lang.reflect.AbstractMappable
*/
package cx.ath.choisnet.lang.reflect;

import java.util.Map;

/**
** <P>Permet d'avoir une vue synthétique d'un objet</P>
**
** @author Claude CHOISNET
** @since   2.01.001
** @version 3.01.012
*/
public abstract class AbstractMappable
    implements Mappable
{

/**
** <P>
** Retourne des couples (nomDeMethode,valeur)
** </P>
**
** Constuit des couples de chaînes à partir des observateurs de l'objet
** courant (méthodes sans paramètres commençant par 'get' ou 'is') et retournant
** des objects ou des tableaux d'objets de type primitif ou d'un des types suivant :
** <ul>
**  <li>java.lang.String</li>
**  <li>java.io.File</li>
**  <li>java.net.URL</li>
** </ul>
**
** @see MappableHelper#toMap
*/
public Map<String,String> toMap() // --------------------------------------
{
 final MappableHelper mappableHelper =
            new MappableHelperFactory()
                .setMethodesNamePattern(
                    "(get|is).*"
                    )
                .addClasses(
                    String.class,
                    java.io.File.class,
                    java.net.URL.class
                    )
                .addAttribute(
                    MappableHelper.Attributes.ALL_PRIMITIVE_TYPE
                    )
                .getInstance();

 return mappableHelper.toMap( this );
}

/**
** <P>
** Retourne une vue XML du résultat de la méthode toMap()
** </P>
**
** @see #toMap()
** @see MappableHelper#toMap
** @see MappableHelper#toXML
public String toXML() // --------------------------------------------------
{
 final StringBuilder sb = new StringBuilder();

 try {
    MappableHelper.toXML( sb, this );
    }
 catch( java.io.IOException ignore ) {
    // ignore
    }

 return sb.toString();
}
*/

} // class


