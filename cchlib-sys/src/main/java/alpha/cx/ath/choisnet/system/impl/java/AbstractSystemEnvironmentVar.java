package alpha.cx.ath.choisnet.system.impl.java;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import alpha.cx.ath.choisnet.system.SystemEnvironmentVar;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.base64.Base64Decoder;
import com.googlecode.cchlib.util.base64.Base64Encoder;
import com.googlecode.cchlib.util.iterable.XIterables;
import com.googlecode.cchlib.util.iterator.Selectable;

/**
 *
 *
 */
public abstract class AbstractSystemEnvironmentVar implements SystemEnvironmentVar
{
    private static final char SEPARATOR = '\0';

    private final Wrappable<String,Serializable>         toSerializable = new StringToSerializable();
    private final Selectable<Serializable>               filterString   = new SelectString();
    private final Wrappable<? super Serializable,String> castToString   = new CastToString();

    protected AbstractSystemEnvironmentVar()
    {
    }

    private String transformSerializableToString( final Serializable object )
    {
        if( object instanceof String ) {
            return (String)object;
            }

        try {
            final byte[] bytes = SerializableHelper.toByteArray( object );

            return object.getClass().getName() + SEPARATOR + Base64Encoder.encode( bytes );
            }
        catch( final IOException e ) {
            throw new SerializationException( e );
            }
    }

    static Serializable transformStringToSerializable( final ClassLoader classLoader, final String valueString )
    {
        final int separatorIndex = valueString.indexOf( SEPARATOR );

        if( separatorIndex > 0 ) {
            try {
                final Class<? extends Serializable> clazz = getClass( classLoader, valueString.substring( 0, separatorIndex ) );

                final byte[] bytes = Base64Decoder.decode( valueString.substring( separatorIndex + 1 ).toCharArray() );

                return SerializableHelper.toObject( bytes, clazz );
                }
            catch( final UnsupportedEncodingException  e ) {
                return valueString;
                }
            catch( final ClassNotFoundException | IOException e ) {
                throw new SerializationException( e );
                }
            }
        else {
            return valueString;
            }
    }

    private static Class<? extends Serializable> getClass(
        final ClassLoader classLoader,
        final String      className
        ) throws ClassNotFoundException
    {
        @SuppressWarnings("unchecked")
        final Class<? extends Serializable> result = (Class<? extends Serializable>)Class.forName( className, true, classLoader );
        return result;
    }

    @Override
    public Serializable getVarObject( final Serializable key )
    {
        final String keyString   = transformSerializableToString( key );
        final String ValueString = getVar( keyString );

        if( ValueString == null ) {
            return null;
        } else {
            return transformStringToSerializable( this.getClass().getClassLoader(), ValueString );
        }
    }

    @Override
    public void setVarObject( final Serializable key, final Serializable value )
    {
        final String keyString   = transformSerializableToString( key );
        final String ValueString = transformSerializableToString( value );

        setVar( keyString, ValueString );
    }

    @Override
    public void deleteVarObject( final Serializable key )
        throws UnsupportedOperationException
    {
        final String keyString = transformSerializableToString( key );

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
