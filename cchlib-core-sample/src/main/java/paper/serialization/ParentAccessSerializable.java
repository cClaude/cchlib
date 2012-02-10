package paper.serialization;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import paper.reflexion.ParentAccess;

/**
 *
 */
public class ParentAccessSerializable<C>
    extends ParentAccess<C>
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private transient List<Field> visibleFields;
    private transient List<Field> hiddenFields;
    private transient List<Method> visibleMethods;
    private transient List<Method> hiddenMethods;

    /**
     *
     */
    public ParentAccessSerializable( Class<C> clazz )
    {
        super( clazz );

        initTransientFields();
    }

    private void initTransientFields()
    {
        this.visibleFields  = Arrays.asList( super.getEnclosingClass().getFields() );
        this.hiddenFields   = new ArrayList<Field>();

        for( Field f : super.getEnclosingClass().getDeclaredFields() ) {
            if( ! visibleFields.contains( f ) ) {
                this.hiddenFields.add( f );
                }
            }

        this.visibleMethods = Arrays.asList( super.getEnclosingClass().getMethods() );
        this.hiddenMethods  = new ArrayList<Method>();

        for( Method m : super.getEnclosingClass().getDeclaredMethods() ) {
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

    private void writeObject( java.io.ObjectOutputStream out )
        throws IOException
    {
        // Default serialization process (store only 'clazz' field (on parent class) )
        out.defaultWriteObject();

        // nothing to do : no special needs
        // method could be removed in this case
    }

    private void readObject( java.io.ObjectInputStream in )
        throws IOException, ClassNotFoundException
    {
        // Default serialization process  (restore only 'clazz' (on parent class) field)
        in.defaultReadObject();

        // restore transient fields
        this.initTransientFields();
    }
}
