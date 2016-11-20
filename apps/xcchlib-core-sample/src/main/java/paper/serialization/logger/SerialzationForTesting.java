package paper.serialization.logger;

import java.io.IOException;
import java.io.Serializable;
import com.googlecode.cchlib.io.SerializableHelper;

abstract class SerialzationForTesting implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String      aValue; // final values could be serialize

    public SerialzationForTesting( final String aValue )
    {
        this.aValue = aValue;
    }

    protected String getaValue()
    {
        return aValue;
    }

    protected String getMessage()
    {
        return "Test serialisation for " + this + " - Value:" + aValue;
    }

    public abstract void doJob();

    public static final <T extends SerialzationForTesting> boolean testClone( final T instance, final Class<T> clazz ) throws ClassNotFoundException, IOException
    {
        instance.doJob();

        T newInstance = SerializableHelper.clone( instance,clazz );

        newInstance.doJob();

        final boolean isSerializationWork = instance.getaValue().equals( newInstance.getaValue() );

        assert isSerializationWork : "Serialization issue";

        return isSerializationWork;
    }

}
