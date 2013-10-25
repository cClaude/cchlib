/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/lang/reflect/Mappable.java
** Description   :
** Encodage      : ANSI
**
**  2.01.001 2005.10.24 Claude CHOISNET - version initiale
**  3.02.026 2006.07.19 Claude CHOISNET
**                      MàJ Documentation
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.lang.reflect.Mappable
*/
package cx.ath.choisnet.lang.reflect;

import java.util.Map;

/**
** <P>Permet d'avoir une vue synthétique d'un objet</P>
**
** @author Claude CHOISNET
** @since   2.01.001
** @version 3.02.026
**
** @see Explorable
** @see MappableHelper
** @see MappableHelperFactory
*/
public interface Mappable
{

/**
** <P>
** Retourne des couples de chaînes (nomDeMethode,valeur) à partir des
** observateurs de l'objet courant (en général, il s'agit des méthodes
** sans paramètres commençant par 'get' ou 'is'). Si c'est c'est la classe
** {@link MappableHelper} qui prend en charge la construction du résultat,
** le choix de méthode explorée sera défini par un objet {@link MappableHelperFactory}.
** </P>
**
** @see MappableHelper
** @see MappableHelperFactory
*/
public Map<String,String> toMap(); // -------------------------------------

} // class


