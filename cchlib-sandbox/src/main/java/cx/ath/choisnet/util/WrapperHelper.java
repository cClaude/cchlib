/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/WrapperHelper.java
** Description   :
**
**  2.02.030 2005.12.21 Claude CHOISNET - Version initiale
**  2.02.031 2005.12.22 Claude CHOISNET
**                      Ajout de la classe GenWrapper
**  3.02.008 2006.06.09 Claude CHOISNET
**                      Documentation
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.WrapperHelper
**
*/
package cx.ath.choisnet.util;

/**
** <p>
**  Classe prenant en charge la transformation de type élémentaires.
** </p>
**
** @author Claude CHOISNET
** @since   2.02.030
** @version 3.02.008
**
** @see cx.ath.choisnet.util.Wrappable
** @see cx.ath.choisnet.util.IteratorWrapper
** @see cx.ath.choisnet.servlet.ParameterValueWrapper
*/
public class WrapperHelper //<T,U> implements Wrappable<T,U>
{

    /**
    ** Permet de créer un objet de type {@link Wrappable} gérant
    ** une valeur par défaut en cas d'échec de la transformation.
    */
    static class DefaultValueWrapper<T,U> implements Wrappable<T,U>
    {
        private Wrappable<T,U> wrapper;
        private U defValue;

        public DefaultValueWrapper( Wrappable<T,U> wrapper, U defValue )
        {
            this.wrapper    = wrapper;
            this.defValue   = defValue;
        }

        public U wrappe( T o )
        {
            try {
                return this.wrapper.wrappe( o );
                }
            catch( Exception e ) {
                return defValue;
                }
        }

    }

    static class ToStringWrapper<T> implements Wrappable<T,String>
    {
        public ToStringWrapper()
        {
         // empty
        }

        public String wrappe( final Object o )
        {
            return o.toString();
        }
    }

/**
** Unique instance
*/
private final static Wrappable<String,Long> WRAPPESTRINGTOLONG
         = new Wrappable<String,Long>()
    {
        public Long wrappe( String o )
        {
            return Long.parseLong( o );
        }
    };

/**
**
*/
public final static <T> Wrappable<T,String> wrappeToString() // -----------
{
 return new ToStringWrapper<T>();
}

/**
**
*/
public final static Wrappable<String,Long> wrappeStringToLong() // --------
{
 return WRAPPESTRINGTOLONG;
}

/**
**
*/
public final static Wrappable<String,Long> wrappeString( // ---------------
    final Long defValue
    )
{
 return new DefaultValueWrapper<String,Long>( WRAPPESTRINGTOLONG, defValue );
}

/**
**
*/
public final static Wrappable<String,Integer> wrappeStringToInteger() // --
{
 return new Wrappable<String,Integer>()
    {
        public Integer wrappe( String o )
        {
            return Integer.parseInt( o );
        }
    };
}

/**
**
*/
public final static Wrappable<String,Integer> wrappeString( // ------------
    final Integer defValue
    )
{
 return new DefaultValueWrapper<String,Integer>( wrappeStringToInteger(), defValue );
}

/**
**
*/
public final static Wrappable<String,Float> wrappeStringToFloat() // ------
{
 return new Wrappable<String,Float>()
    {
        public Float wrappe( String o )
        {
            return Float.parseFloat( o );
        }
    };
}

/**
**
*/
public final static Wrappable<String,Float> wrappeString( // --------------
    final Float defValue
    )
{
 return new DefaultValueWrapper<String,Float>( wrappeStringToFloat(), defValue );
}

/**
**
*/
public final static Wrappable<String,Double> wrappeStringToDouble() // ----
{
 return new Wrappable<String,Double>()
    {
        public Double wrappe( String o )
        {
            return Double.parseDouble( o );
        }
    };
}

/**
**
*/
public final static Wrappable<String,Double> wrappeString( // -------------
    final Double defValue
    )
{
 return new DefaultValueWrapper<String,Double>( wrappeStringToDouble(), defValue );
}

} // interface

