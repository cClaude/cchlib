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
** Retourne la valeur du param�tre sous forme de tableau de cha�ne.
** </p>
**
** @return la valeur du param�tre si elle existe, retourne null autrement.
**
** @see ParameterValueWrapper#asList(ParameterValue)
*/
public String[] toArray(); // ---------------------------------------------

/**
** <p>
** Retourne la valeur du param�tre sous forme de cha�ne unique.
** </p>
**
** @return la valeur du param�tre (�ventuellement une cha�ne vide) si le
**         param�tre a �t� d�fini, retourne null autrement.
*/
@Override
public String toString(); // ----------------------------------------------

/**
**
**
** @return la valeur du param�tre si elle existe et si elle est non vide,
**         retourne la valeur (defautValue) autrement.
*/
public String toString( String defaultValue ); // -------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme de boolean.
** </p>
**
** @return la valeur sous forme de boolean du param�tre. La valeur par
**         d�fault est false
**
** @see #booleanValue( boolean )
*/
public boolean booleanValue(); // -----------------------------------------

/**
** <p>
** Retourne la valeur sous forme de boolean d'un param�tre.
** </p>
** Pour que la valeur soit 'true', il faut que l'une des conditions
** suivante soit remplie :
** <br/>
** La valeur trouv�e est "on" ou "true" (non sensible � la casse).
** <br/>
** La valeur trouv�e est un entier stritement positif ( v > 0 ).
** <br/>
**
** @return la valeur sous forme de boolean du param�tre, si le param�tre
**         n'existe pas la valeur par d�faut (defautValue) est retourn�e.
**
*/
public boolean booleanValue( boolean defautValue ); // --------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un entier.
** </p>
**
** @return la valeur sous forme d'entier du param�tre. La valeur par
**         d�fault est 0.
*/
public int intValue(); // -------------------------------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un entier.
** </p>
**
** @return la valeur sous forme d'entier du param�tre, si le param�tre n'existe
**         pas la valeur par d�faut (defautValue) est retourn�e.
*/
public int intValue( int defautValue ); // --------------------------------


/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un long.
** </p>
**
** @return la valeur sous forme d'un long du param�tre. La valeur par
**         d�fault est 0.
*/
public long longValue(); // -----------------------------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un long.
** </p>
**
** @return la valeur sous forme d'un long du param�tre, si le param�tre
**         n'existe pas la valeur par d�faut (defautValue) est retourn�e.
*/
public long longValue( long defautValue ); // -----------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un float.
** </p>
**
** @return la valeur sous forme d'un float du param�tre. La valeur par
**         d�fault est 0.
*/
public float floatValue(); // ---------------------------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un float.
** </p>
**
** @return la valeur sous forme d'un float du param�tre, si le param�tre
**         n'existe pas la valeur par d�faut (defautValue) est retourn�e.
*/
public float floatValue( float defautValue ); // --------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un double.
** </p>
**
** @return la valeur sous forme d'un double du param�tre. La valeur par
**         d�fault est 0.
*/
public double doubleValue(); // -------------------------------------------

/**
** <p>
** Retourne la valeur d'un param�tre sous forme d'un double.
** </p>
**
** @return la valeur sous forme d'un double du param�tre, si le param�tre
**         n'existe pas la valeur par d�faut (defautValue) est retourn�e.
*/
public double doubleValue( double defautValue ); // -----------------------

} // class

