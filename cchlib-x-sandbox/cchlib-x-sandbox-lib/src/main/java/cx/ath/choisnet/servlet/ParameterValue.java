/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/ParameterValue.java
** Description   :
** Encodage      : ANSI
**
**  2.01.030 2005.11.10 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.ParameterValue
**
*/
package cx.ath.choisnet.servlet;

/**
** <P>
** Outils permettant d'exploiter plus simplement les informations
**  de la classe {@link javax.servlet.http.HttpServletRequest}
** </P>
**
** @author Claude CHOISNET
** @since   2.01.030
** @version 2.01.030
**
** @see javax.servlet.http.HttpServletRequest
** @see ParameterValueWrapper
*/
public interface ParameterValue
{
/**
** <p>
** Retourne la valeur du paramètre sous forme de tableau de chaîne.
** </p>
**
** @return la valeur du paramètre si elle existe, retourne null autrement.
**
** @see ParameterValueWrapper#asList(ParameterValue)
*/
public String[] toArray(); // ---------------------------------------------

/**
** <p>
** Retourne la valeur du paramètre sous forme de chaîne unique.
** </p>
**
** @return la valeur du paramètre (éventuellement une chaîne vide) si le
**         paramètre a été défini, retourne null autrement.
*/
public String toString(); // ----------------------------------------------

/**
**
**
** @return la valeur du paramètre si elle existe et si elle est non vide,
**         retourne la valeur (defautValue) autrement.
*/
public String toString( String defaultValue ); // -------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme de boolean.
** </p>
**
** @return la valeur sous forme de boolean du paramètre. La valeur par
**         défault est false
**
** @see #booleanValue( boolean )
*/
public boolean booleanValue(); // -----------------------------------------

/**
** <p>
** Retourne la valeur sous forme de boolean d'un paramètre.
** </p>
** Pour que la valeur soit 'true', il faut que l'une des conditions
** suivante soit remplie :
** <br/>
** La valeur trouvée est "on" ou "true" (non sensible à la casse).
** <br/>
** La valeur trouvée est un entier stritement positif ( v > 0 ).
** <br/>
**
** @return la valeur sous forme de boolean du paramètre, si le paramètre
**         n'existe pas la valeur par défaut (defautValue) est retournée.
**
*/
public boolean booleanValue( boolean defautValue ); // --------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un entier.
** </p>
**
** @return la valeur sous forme d'entier du paramètre. La valeur par
**         défault est 0.
*/
public int intValue(); // -------------------------------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un entier.
** </p>
**
** @return la valeur sous forme d'entier du paramètre, si le paramètre n'existe
**         pas la valeur par défaut (defautValue) est retournée.
*/
public int intValue( int defautValue ); // --------------------------------


/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un long.
** </p>
**
** @return la valeur sous forme d'un long du paramètre. La valeur par
**         défault est 0.
*/
public long longValue(); // -----------------------------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un long.
** </p>
**
** @return la valeur sous forme d'un long du paramètre, si le paramètre
**         n'existe pas la valeur par défaut (defautValue) est retournée.
*/
public long longValue( long defautValue ); // -----------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un float.
** </p>
**
** @return la valeur sous forme d'un float du paramètre. La valeur par
**         défault est 0.
*/
public float floatValue(); // ---------------------------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un float.
** </p>
**
** @return la valeur sous forme d'un float du paramètre, si le paramètre
**         n'existe pas la valeur par défaut (defautValue) est retournée.
*/
public float floatValue( float defautValue ); // --------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un double.
** </p>
**
** @return la valeur sous forme d'un double du paramètre. La valeur par
**         défault est 0.
*/
public double doubleValue(); // -------------------------------------------

/**
** <p>
** Retourne la valeur d'un paramètre sous forme d'un double.
** </p>
**
** @return la valeur sous forme d'un double du paramètre, si le paramètre
**         n'existe pas la valeur par défaut (defautValue) est retournée.
*/
public double doubleValue( double defautValue ); // -----------------------

} // class

