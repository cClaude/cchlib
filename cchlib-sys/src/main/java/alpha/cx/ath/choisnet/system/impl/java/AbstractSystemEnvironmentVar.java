package alpha.cx.ath.choisnet.system.impl.java;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.base64.Base64Decoder;
import com.googlecode.cchlib.util.base64.Base64Encoder;
import com.googlecode.cchlib.util.iterable.XIterables;
import com.googlecode.cchlib.util.iterator.Selectable;
import alpha.cx.ath.choisnet.system.SystemEnvironmentVar;

/**
 *
 *
 */
public abstract class AbstractSystemEnvironmentVar implements SystemEnvironmentVar
{
    private final static char SEPARATOR = '\0';
    
    private Wrappable<String,Serializable>         toSerializable = new StringToSerializable();
    private Selectable<Serializable>               filterString   = new SelectString();
    private Wrappable<? super Serializable,String> castToString   = new CastToString();

    protected AbstractSystemEnvironmentVar()
    {
    }

    private String transformSerializableToString( Serializable object )
    {
        if( object instanceof String ) {
            return (String)object;
            }

        try {
            byte[] bytes = SerializableHelper.toByteArray( object );

            return object.getClass().getName() + SEPARATOR + Base64Encoder.encode( bytes );
            }
        catch( IOException e ) {
            throw new RuntimeException( e ); // FIXME
            }
    }

    static Serializable transformStringToSerializable( final ClassLoader classLoader, final String valueString )
    {
        int separatorIndex = valueString.indexOf( SEPARATOR );

        if( separatorIndex > 0 ) {
            try {
                Class<? extends Serializable> clazz = getClass( classLoader, valueString.substring( 0, separatorIndex ) );

                byte[] bytes = Base64Decoder.decode( valueString.substring( separatorIndex + 1 ).toCharArray() );

                return SerializableHelper.toObject( bytes, clazz );
                }
            catch( UnsupportedEncodingException  e ) {
                return valueString;
                }
            catch( ClassNotFoundException | IOException e ) {
                throw new RuntimeException( e ); // FIXME
                }
            }
        else {
            return valueString;
            }
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Serializable> getClass(
        final ClassLoader classLoader,
        final String      className
        ) throws ClassNotFoundException
    {
        return (Class<? extends Serializable>)Class.forName( className, true, classLoader );
    }

    @Override
    public Serializable getVarObject( Serializable key )
    {
        String keyString   = transformSerializableToString( key );
        String ValueString = getVar( keyString );

        if( ValueString == null ) {
            return null;
        } else {
            return transformStringToSerializable( this.getClass().getClassLoader(), ValueString );
        }
    }

    @Override
    public void setVarObject( Serializable key, Serializable value )
    {
        String keyString   = transformSerializableToString( key );
        String ValueString = transformSerializableToString( value );

        setVar( keyString, ValueString );
    }

    @Override
    public void deleteVarObject( Serializable key )
        throws UnsupportedOperationException
    {
        String keyString = transformSerializableToString( key );

        deleteVar( keyString );
    }

    protected abstract Iterable<String> getStringKeys();

    @Override
    public Iterable<Serializable> getVarObjectKeys()
        throws UnsupportedOperationException
    {
        return XIterables.transform( getStringKeys(), toSerializable );
    }

    @Override
    public Iterable<String> getVarNames() throws UnsupportedOperationException
    {
        return XIterables.transform( getStringKeys(), toSerializable ).filter( filterString ).wrap( castToString );
    }
}
