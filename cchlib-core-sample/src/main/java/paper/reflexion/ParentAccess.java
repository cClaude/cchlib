package paper.reflexion;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ParentAccess<C> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Class<C> clazz;

    /**
     *
     */
    public ParentAccess( Class<C> clazz )
    {
        this.clazz = clazz;
    }

    // enclosing
    protected Class<C> getEnclosingClass()
    {
        return this.clazz;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb              = new StringBuilder();
        final List<Field>    visibleFields  = Arrays.asList( clazz.getFields() );
        final List<Field>    hiddenFields   = new ArrayList<Field>();

        for( Field f : clazz.getDeclaredFields() ) {
            if( ! visibleFields.contains( f ) ) {
                hiddenFields.add( f );
                }
            }

        final List<Method> visibleMethods = Arrays.asList( clazz.getMethods() );
        final List<Method> hiddenMethods  = new ArrayList<Method>();

        for( Method m : clazz.getDeclaredMethods() ) {
            if( !visibleMethods.contains( m ) ) {
                hiddenMethods.add( m );
                }
            }

        sb.append( "Class:" )
          .append( this.clazz )
          .append( '\n' );

        for( Field f : hiddenFields ) {
            sb.append( "hidden field : " )
              .append( f )
              .append( '\n' );
            }

        for( Field f : visibleFields ) {
            sb.append( "visible field : " )
              .append( f )
              .append( '\n' );
            }

        for( Method m : clazz.getDeclaredMethods() ) {
            if( !visibleMethods.contains( m ) ) {
                sb.append( "hidden method : " )
                  .append( m )
                  .append( '\n' );
                }
            }

        for( Method m : visibleMethods ) {
            sb.append( "visible method : " )
              .append( m )
              .append( '\n' );
            }

        return sb.toString();
    }

    public <T> void setObject(
        final C         thisObject,
        final String    fieldName,
        final T         value
        )
        throws  NoSuchFieldException,
                SecurityException,
                IllegalArgumentException,
                IllegalAccessException
    {
        final Field f = this.clazz.getDeclaredField( fieldName );

        f.setAccessible( true );

        try {
            f.set( thisObject, value );
            }
        finally {
            f.setAccessible( false );
            }
    }

    public <T> void silentSetObject(
            final C         thisObject,
            final String    fieldName,
            final T         value
            )
    {
        try {
            setObject(thisObject, fieldName, value);
            }
        catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e)
        {
            throw new ParentAccessException( e );
        }
    }

    public <T> T getObject(
            final C         thisObject,
            final String    fieldName,
            Class<T>         returnClass
            )
        throws  NoSuchFieldException,
                SecurityException,
                IllegalArgumentException,
                IllegalAccessException
    {
        final Field f = this.clazz.getDeclaredField( fieldName );

        f.setAccessible( true );

        try {
            return returnClass.cast( f.get( thisObject ) );
            }
        finally {
            f.setAccessible( false );
            }
    }

    public <T> T silentGetObject(
            final C         thisObject,
            final String    fieldName,
            Class<T>         returnClass
            )
    {
        try {
            return this.getObject( thisObject, fieldName, returnClass );
            }
        catch( NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e) {
            throw new ParentAccessException( e );
            }
    }

    public <T> T call(
            final C                     thisObject,
            final Class<? extends T>    resultClass,
            final String                methodName,
            final Object...             values
            )
            throws  NoSuchMethodException,
                    SecurityException,
                    IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException
    {
        final Class<?>[] types = new Class[ values.length ];

        for( int i = 0; i<values.length; i++ ) {
            types[ i ] = values[ i ].getClass();
            }

        final Method m = this.clazz.getDeclaredMethod( methodName, types );
        m.setAccessible( true );

        try {
            return resultClass.cast( m.invoke( thisObject, values ) );
            }
        finally {
            m.setAccessible( false );
            }
   }

    public <T> T silentCall(
            final C                     thisObject,
            final Class<? extends T>    resultClass,
            final String                methodName,
            final Object...             values
            )
    {
        try {
            return call( thisObject, resultClass, methodName, values );
            }
        catch( NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e ) {
            throw new ParentAccessException( e );
            }
    }


}
