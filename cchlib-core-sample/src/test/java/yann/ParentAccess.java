package yann;

import java.io.IOException;
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
    private transient List<Field> visibleFields;
    private transient List<Field> hiddenFields;
    private transient List<Method> visibleMethods;
    private transient List<Method> hiddenMethods;

    /**
     *
     */
    public ParentAccess( Class<C> clazz )
    {
        this.clazz = clazz;

        initTransientFields();
    }

    private void initTransientFields()
    {
        this.visibleFields  = Arrays.asList( clazz.getFields() );
        this.hiddenFields   = new ArrayList<Field>();

        for( Field f : clazz.getDeclaredFields() ) {
            if( ! visibleFields.contains( f ) ) {
                this.hiddenFields.add( f );
                }
            }

        this.visibleMethods = Arrays.asList( clazz.getMethods() );
        this.hiddenMethods  = new ArrayList<Method>();

        for( Method m : clazz.getDeclaredMethods() ) {
            if( !visibleMethods.contains( m ) ) {
                this.hiddenMethods.add( m );
                }
            }
    }

    public List<Field> getHiddenFieldList()
    {
        return this.hiddenFields;
    }

    public List<Method> getHiddenMethodList()
    {
        return this.hiddenMethods;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( "Class:" )
          .append( this.clazz )
          .append( "\n" );

        for( Field f : hiddenFields ) {
            sb.append( "hidden field : " )
              .append( f )
              .append( "\n" );
            }

        for( Field f : visibleFields ) {
            sb.append( "visible field : " )
              .append( f )
              .append( "\n" );
            }

        for( Method m : clazz.getDeclaredMethods() ) {
            if( !visibleMethods.contains( m ) ) {
                sb.append( "hidden method : " )
                  .append( m )
                  .append( "\n" );
                }
            }

        for( Method m : visibleMethods ) {
            sb.append( "visible method : " )
              .append( m )
              .append( "\n" );
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
        //final Field f = this.clazz.getField( fieldName ); !!! Visible fields only
        final Field f = this.clazz.getDeclaredField( fieldName );

        f.setAccessible( true );

        try {
            f.set( thisObject, value );
            }
        finally {
            f.setAccessible( false );
            }
    }

    public void setInt(
            final C         thisObject,
            final String    fieldName,
            final int       value
            )
            throws  NoSuchFieldException,
                    SecurityException,
                    IllegalArgumentException,
                    IllegalAccessException
        {
            final Field f = this.clazz.getDeclaredField( fieldName );

            f.setAccessible( true );

            try {
                f.setInt( thisObject, value );
                }
            finally {
                f.setAccessible( false );
                }
        }

    public <T> T Call(
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

    public <T> T CallNoException(
            final C                     thisObject,
            final Class<? extends T>    resultClass,
            final String                methodName,
            final Object...             values
            )
    {
        try {
            return this.Call( thisObject, resultClass, methodName, values );
            }
        catch( NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e ) {
            throw new RuntimeException( e );
            }
    }

    private void writeObject( java.io.ObjectOutputStream out )
        throws IOException
    {
        // Default serialization process (store only 'clazz' field)
        out.defaultWriteObject();

        // nothing to do : no special needs
        // method could be removed in this case
    }

    private void readObject( java.io.ObjectInputStream in )
        throws IOException, ClassNotFoundException
    {
        // Default serialization process  (restore only 'clazz' field)
        in.defaultReadObject();

        // restore transient fields
        this.initTransientFields();
    }

}
