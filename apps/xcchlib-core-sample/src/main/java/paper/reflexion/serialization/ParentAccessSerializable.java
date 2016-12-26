package paper.reflexion.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import paper.reflexion.ParentAccess;

public class ParentAccessSerializable<C>
    extends ParentAccess<C>
        implements Serializable
{
    private static final long serialVersionUID = 1L;

    private transient List<Field>  visibleFields;
    private transient List<Field>  hiddenFields;

    private transient List<Method> visibleMethods;
    private transient List<Method> hiddenMethods;

    public ParentAccessSerializable( final Class<C> clazz )
    {
        super( clazz );

        initTransientFields();
    }

    private void initTransientFields()
    {
        this.visibleFields  = createVisibleFields(
                super.getEnclosingClass().getFields()
                );
        this.hiddenFields   = createHiddenFields(
                super.getEnclosingClass().getDeclaredFields(),
                this.visibleFields
                );

        this.visibleMethods = createVisibleMethods(
                super.getEnclosingClass().getMethods()
                );
        this.hiddenMethods  = createHiddenMethods(
                super.getEnclosingClass().getDeclaredMethods(),
                this.visibleMethods
                );
    }

    private static List<Field> createVisibleFields( final Field[] fields )
    {
        return Collections.unmodifiableList( Arrays.asList( fields ) );
    }

    private static List<Field> createHiddenFields(
        final Field[]     declaredFields,
        final List<Field> visibleFields
        )
    {
        final ArrayList<Field> hiddenFields = new ArrayList<>();

        for( final Field field : declaredFields ) {
            if( ! visibleFields.contains( field ) ) {
                hiddenFields.add( field );
                }
            }

        return Collections.unmodifiableList( hiddenFields );
    }

    private static List<Method> createVisibleMethods( final Method[] methods )
    {
        return Collections.unmodifiableList( Arrays.asList( methods ) );
    }

    private static List<Method> createHiddenMethods(
        final Method[]     declaredMethods,
        final List<Method> visibleMethods
        )
    {
        final List<Method> hiddenMethods  = new ArrayList<>();

        for( final Method m : declaredMethods ) {
            if( ! visibleMethods.contains( m ) ) {
                hiddenMethods.add( m );
                }
            }

        return Collections.unmodifiableList( hiddenMethods );
    }

    public List<Field> getVisibleFields()
    {
        return this.visibleFields;
    }

    public List<Field> getHiddenFields()
    {
        return this.hiddenFields;
    }

    public List<Method> getVisibleMethods()
    {
        return this.visibleMethods;
    }

    public List<Method> getHiddenMethods()
    {
        return this.hiddenMethods;
    }

    private void writeObject( final ObjectOutputStream out ) throws IOException
    {
        // Default serialization process (store only 'clazz' field (on parent class) )
        out.defaultWriteObject();

        // nothing to do : no special needs
        // method could be removed in this case
    }

    private void readObject( final ObjectInputStream in ) throws IOException, ClassNotFoundException
    {
        // Default serialization process  (restore only 'clazz' (on parent class) field)
        in.defaultReadObject();

        // restore transient fields
        this.initTransientFields();
    }
}
