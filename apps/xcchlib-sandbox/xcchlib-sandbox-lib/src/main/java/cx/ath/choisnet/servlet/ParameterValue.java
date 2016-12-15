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
*/
public interface ParameterValue
{
/**
** <p>
** Retourne la valeur du parametre sous forme de tableau de chaene.
** </p>
**
** @return la valeur du parametre si elle existe, retourne null autrement.
**
** @see ParameterValueWrapper#asList(ParameterValue)
*/
public String[] toArray(); // ---------------------------------------------

/**
** <p>
** Retourne la valeur du parametre sous forme de chaene unique.
** </p>
**
** @return la valeur du parametre (eventuellement une chaene vide) si le
**         parametre a ete defini, retourne null autrement.
*/
@Override
public String toString(); // ----------------------------------------------

/**
**
**
** @return la valeur du parametre si elle existe et si elle est non vide,
**         retourne la valeur (defautValue) autrement.
*/
public String toString( String defaultValue ); // -------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme de boolean.
** </p>
**
** @return la valeur sous forme de boolean du parametre. La valeur par
**         default est false
**
** @see #booleanValue( boolean )
*/
public boolean booleanValue(); // -----------------------------------------

/**
** <p>
** Retourne la valeur sous forme de boolean d'un parametre.
** </p>
** Pour que la valeur soit 'true', il faut que l'une des conditions
** suivante soit remplie :
** <br>
** La valeur trouvee est "on" ou "true" (non sensible e la casse).
** <br>
** La valeur trouvee est un entier stritement positif ( v > 0 ).
** <br>
**
** @return la valeur sous forme de boolean du parametre, si le parametre
**         n'existe pas la valeur par defaut (defautValue) est retournee.
**
*/
public boolean booleanValue( boolean defautValue ); // --------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un entier.
** </p>
**
** @return la valeur sous forme d'entier du parametre. La valeur par
**         default est 0.
*/
public int intValue(); // -------------------------------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un entier.
** </p>
**
** @return la valeur sous forme d'entier du parametre, si le parametre n'existe
**         pas la valeur par defaut (defautValue) est retournee.
*/
public int intValue( int defautValue ); // --------------------------------


/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un long.
** </p>
**
** @return la valeur sous forme d'un long du parametre. La valeur par
**         default est 0.
*/
public long longValue(); // -----------------------------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un long.
** </p>
**
** @return la valeur sous forme d'un long du parametre, si le parametre
**         n'existe pas la valeur par defaut (defautValue) est retournee.
*/
public long longValue( long defautValue ); // -----------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un float.
** </p>
**
** @return la valeur sous forme d'un float du parametre. La valeur par
**         default est 0.
*/
public float floatValue(); // ---------------------------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un float.
** </p>
**
** @return la valeur sous forme d'un float du parametre, si le parametre
**         n'existe pas la valeur par defaut (defautValue) est retournee.
*/
public float floatValue( float defautValue ); // --------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un double.
** </p>
**
** @return la valeur sous forme d'un double du parametre. La valeur par
**         default est 0.
*/
public double doubleValue(); // -------------------------------------------

/**
** <p>
** Retourne la valeur d'un parametre sous forme d'un double.
** </p>
**
** @return la valeur sous forme d'un double du parametre, si le parametre
**         n'existe pas la valeur par defaut (defautValue) est retournee.
*/
public double doubleValue( double defautValue ); // -----------------------

} // class

