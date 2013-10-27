/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/ParameterValueWrapper.java
** Description   :
** Encodage      : ANSI
**
**  2.02.030 2005.12.21 Claude CHOISNET - Version initiale
**  2.02.033 2005.12.23 Claude CHOISNET
**                      Ajout de asList( HttpServletRequest, String )
**                      Mes méthodes asList() peuvent retournée null.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.ParameterValueWrapper
**
**
*/
package cx.ath.choisnet.servlet;

import cx.ath.choisnet.util.Wrappable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
**
** @author Claude CHOISNET
** @version 2.02.030
** @since   2.02.033
**
** @see cx.ath.choisnet.util.WrapperHelper
** @see cx.ath.choisnet.servlet.ParameterValue
*/
public class ParameterValueWrapper<T>
{
/** */
private final Wrappable<String,T> wrapper;

/**
**
*/
public ParameterValueWrapper( Wrappable<String,T> wrapper ) // ------------
{
 this.wrapper = wrapper;
}

/**
** <p>
** Retourne les valeurs du paramétre sous forme d'une liste typée.
** </p>
**
** @return une liste contenant les valeurs du paramétre s'il existe,
**         null autrement
*/
public List<T> asList( // -------------------------------------------------
    final HttpServletRequest    request,
    final String                paramName
    )
{
 return asList( request.getParameterValues( paramName ) );
}

/**
** <p>
** Retourne les valeurs du paramétre sous forme d'une liste typée.
** </p>
** <p>
** Cette méthode s'appuit sur {@link ParameterValue#toArray()} pour
** retrouver les valeurs du paramétres.
** </p>
**
** @return une liste contenant les valeurs du paramétre s'il existe,
**         null autrement
*/
public List<T> asList( final ParameterValue paramValue ) // ---------------
{
 return asList( paramValue.toArray() );
}

/**
** <p>
** Transforme un tableau de chaéne en sont équivalant sous forme
** de liste en accort avec le wrapper courant.
** </p>
** @param values tableau de chaénes é traiter.
**
** @return une liste contenant les valeurs wrappées, ou null si le paramétre
**         values est null.
*/
private List<T> asList( final String[] values ) // ------------------------
{
 if( values == null ) {
    return null;
    }

 final int      len     = values.length;
 final List<T>  list    = new java.util.ArrayList<T>( len );

 for( int i = 0; i<len; i++ ) {
    list.add( this.wrapper.wrappe( values[ i ] ) );
    }

 return list;
}

} // class

