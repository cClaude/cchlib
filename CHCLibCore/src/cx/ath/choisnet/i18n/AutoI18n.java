/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.awt.Window;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.MissingResourceException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * AutoI18n is design to assist internalization process.
 * AutoI18n use reflection to identify {@link Field} that
 * should be localized.
 * <p>
 * You can customize AutoI18n by many ways:
 * <br/>
 * - Using annotations {@link I18n}, {@link I18nIgnore}, 
 * {@link I18nString};
 * <br/>
 * - Using custom process by extending {@link AutoI18nBasicInterface},
 * {@link AutoI18nCustomInterface} or {@link  I18nInterface};
 * <br/>
 * - Defines {@link AutoI18n.Attribute attributes} on constructor.
 * </p>
 * <p>
 * <b>How to use:</b>
 * <pre>
 *     // on main object (JFrame)
 *     private AutoI18n autoI18n = {@code <<init_AutoI18n>>}
 *     
 *     private void init()
 *     {
 *       ...
 *       // after all initialization ...
 *       // Localize current class
 *       autoI18n.performeI18n(this,this.getClass());
 *       
 *       // but also you can Localize some extra fields
 *       autoI18n.performeI18n(this.myPanel,this.myPanel.getClass());
 *     }
 * </pre>
 * <b>How to initialize:</b>
 * <pre>
 *     AutoI18n autoI18n = new AutoI18n( 
            new {@link I18nSimpleResourceBundle}( "{@code <<MessageBundleFullName>>}" ),
            new {@link AutoI18nLog4JExceptionHandler}(),
            EnumSet.of( 
                AutoI18n.Attribute.DO_DEEP_SCAN
                )
            );
 * </pre>
 * </p>
 * 
 * @author Claude CHOISNET
 */
public class AutoI18n implements Serializable
{
    private static final long serialVersionUID = 1L;
    private transient Object      objectToI18n;
    private transient Class<?>    objectToI18nClass;
    private transient String      objectToI18nClassNamePrefix;
    private I18nInterface i18n;
    protected AutoI18nExceptionHandler exceptionHandler; 
    private EnumSet<Attribute> config;
    private final static Class<?>[] ignoredClasses = {
        Object.class,
        JFrame.class,
        JDialog.class,
        JWindow.class,
        Window.class,
        JPanel.class,
        };
    private AutoI18nTypes types;
    /**
     * How to select Fields:
     * <p>
     * By default, I18n process inspect all the fields
     * declared by the class or interface represented. 
     * by 'objectToI18n' ({@link AutoI18n#performeI18n(Object, Class)}.
     * This includes public, protected, default (package)
     * access, and private fields, but excludes inherited 
     * fields.
     * </p>
     * 
     * @author Claude CHOISNET
     */
    public enum Attribute
    {
        /**
         * Select only field objects reflecting all the
         * accessible public fields of the class or
         * interface represented by 'objectToI18n' 
         * ({@link AutoI18n#performeI18n(Object, Class)}.
         */
        ONLY_PUBLIC,
        /**
         * Also get inspect Fields from super class.
         * <br/>
         * Recurse process into super classes since
         * super class is one of {@link Object},
         * {@link JFrame}, {@link JDialog},
         * {@link JWindow}, {@link Window}
         */
        DO_DEEP_SCAN,
    }

//    /**
//     * Create an AutoI18n object using {@link I18nSimpleResourceBundle}
//     * and {@link AutoI18nSystemErrExceptionHandler}
//     * 
//     * @param resourceBundleBaseName
//     */
//    public AutoI18n( String resourceBundleBaseName )
//    {
//        this( new I18nSimpleResourceBundle(resourceBundleBaseName));
//    }
//    
//    /**
//     * @param i18n {@link I18nInterface} to use
//     */
//    public AutoI18n( I18nInterface i18n )
//    {
//        this(
//            i18n, 
//            new AutoI18nSystemErrExceptionHandler(),
//            null
//            );
//    }
    
    /**
     * Create an AutoI18n object using {@link I18nInterface},
     * 
     * <p>
     * i18n : Could be initialized using default implementation
     * {@link I18nSimpleResourceBundle}
     * </p>
     * <p>
     * handler : Could be initialized using default implementation
     * {@link AutoI18nSystemErrExceptionHandler}
     * </p>
     * 
     * @param i18n I18nInterface to use
     * @param autoI18nTypes AutoI18nTypes to use (could be null to use defaults)
     * @param handler AutoI18nExceptionHandler to use
     * @param attributes Customize Auto18n (could be null to
     * use defaults).
     */
    public AutoI18n( 
            I18nInterface               i18n,
            AutoI18nTypes               autoI18nTypes,
            AutoI18nExceptionHandler    handler,
            EnumSet<Attribute>          attributes
            )
    {
        if( autoI18nTypes == null ) {
            this.types = new DefaultAutoI18nTypes();
        }
        else {
            this.types = autoI18nTypes;
        }
        setI18n( i18n );
        setAutoI18nExceptionHandler( handler );
        
        if( attributes == null ) {
            this.config = EnumSet.noneOf( Attribute.class );
        }
        else {
            this.config = EnumSet.copyOf( attributes );
        }
    }

    /**
     * Change {@link I18nInterface} to use
     * 
     * @param i18n {@link I18nInterface} to use
     */
    public void setI18n( final I18nInterface i18n )
    {
        if( i18n == null ) {
            throw new NullPointerException();
        }
        this.i18n = i18n;
    }
    
    /**
     * Change {@link AutoI18nExceptionHandler} to use
     * 
     * @param handler {@link AutoI18nExceptionHandler} to use
     */
    public void setAutoI18nExceptionHandler( AutoI18nExceptionHandler handler )
    {
        if( handler == null ) {
            throw new NullPointerException();
        }
        this.exceptionHandler = handler;
    }

    /**
     * 'key' value for RessourceBundle is build using full Field name:
     * i.e. package.path.ClassName.aFieldName
     * <br/>
     * Then try to make I18N use these rules :
     * <pre>
     *   1. Looking for @I18n, @I18nIgnore Annotation
     *      1.1  If annotation I18nIgnore is define, just ignore
     *           this field
     *      1.1. If a keyName is define, use it to find value in
     *           resource bundle.
     *      1.2. If a methodName is define, use it to set
     *           value: objectToI18n.methodName(value);
     *   2  Try to customize using default rules, getting Fields
     *      values.
     *      2.1. If field value is an instance of AutoI18nBasicInterface
     *           use it to set value.
     *      2.2. If field value is an instance of AutoI18nCustomInterface
     *           use it to set value
     *      2.3. If field value is an instance of javax.swing.JLabel
     *           use {@link javax.swing.JLabel#setText(String)}
     *      2.4. If field value is an instance of javax.swing.AbstractButton
     *           use {@link javax.swing.AbstractButton#setText(String)}
     *      2.5. If field value is an instance of javax.swing.JCheckBox
     *           use {@link javax.swing.JCheckBox#setText(String)}
     * </pre>
     * @param <T> 
     * @param objectToI18n Object to I18n
     * @param clazz        Class to use for I18n
     * @see AutoI18nBasicInterface
     * @see AutoI18nBasicInterface#getI18nString()
     * @see AutoI18nCustomInterface
     * @see AutoI18nCustomInterface#getI18n(I18nInterface)
     */
    synchronized public <T> void performeI18n( 
            final T                     objectToI18n,
            final Class<? extends T>    clazz
            )
    {        
        if( this.exceptionHandler == null ) {
            setAutoI18nExceptionHandler( new AutoI18nLog4JExceptionHandler() );
        }
        
        setObjectToI18n(objectToI18n,clazz);
        Class<?> currentClass = objectToI18nClass;
        
        while(currentClass != null) {
            boolean stop = false;
            
            for(Class<?> c:ignoredClasses) {
                if( currentClass.equals( c )) {
                    stop = true;
                    break;
                }
            }

            if( stop ) {
                break;
            }
            
            //
            //
            //
            Field[] fields;
            
            if( config.contains( Attribute.ONLY_PUBLIC ) ) {
                fields = currentClass.getFields();
            }
            else {
                fields = currentClass.getDeclaredFields();
            }

            for( Field f : fields ) {
                if( f.isSynthetic() ) {
                    continue; // ignore member that was introduced by the compiler.
                }
                Class<?> ftype = f.getType();
                
                if( ftype.isAnnotation() ) {
                    continue; // ignore annotations
                }
                if( ftype.isPrimitive() ) {
                    continue; // ignore primitive (numbers)
                }
                if( ftype.isAssignableFrom( Number.class )) {
                    continue; // ignore numbers
                }
                //TODO: ignore some Fields types like EnumSet
                
                I18nIgnore ignoreIt = f.getAnnotation( I18nIgnore.class );
                
                if( ignoreIt != null ) {
                    continue;
                }

                setValue( f );
            }
            if( config.contains( Attribute.DO_DEEP_SCAN )) {
                currentClass = currentClass.getSuperclass();
            }
            else {
                break;
            }
        } 
    }
    
    private <T> void setObjectToI18n(
            T                   objectToI18n,
            Class<? extends T>  clazz
            )
    {
        this.objectToI18n = objectToI18n;
        this.objectToI18nClass = clazz;//objectToI18n.getClass();
        this.objectToI18nClassNamePrefix = this.objectToI18nClass.getName() + '.';
    }

    private void setValue( Field f )
    {
        // WARNING: 'key' must be always valid
        // before resolve 'value'
        String key = null; // 'key' is use by catch statement !

        try {
            I18n anno = f.getAnnotation( I18n.class );

            if( anno != null ) {
                if( anno.methodName().length() == 0 ) {
                    key = anno.keyName();

                    if( key.length() == 0 ) {
                        key = getKey( f );
                    }
                    setValueFromKey( f, key );
                }
                else {
                    key = anno.keyName();

                    if( key.length() == 0 ) {
                        key = getKey( f );
                    }
                    setValueFromAnnotation( f, key, anno );
                }
            } 
            else if( f.getType().isArray() ) {
                if( f.getAnnotation( I18nString.class ) != null ) {
                    Class<?> ac = f.getType().getComponentType();

                    if( ac.equals( String.class ) ) {
                        f.setAccessible( true );
                        Object o = f.get( objectToI18n );
                        String prefix = getKey( f ) + '.';
                        int len = Array.getLength( o );

                        for( int i = 0; i < len; i++ ) {
                            key = prefix + i;
                            Array.set( o, i, this.i18n.getString( key ) );
                        }
                    }
                }
            } 
            else if( f.getType().isAssignableFrom( AutoI18nCustomInterface.class ) ) {
                Object o = f.get( objectToI18n );

                setAutoI18nCustomInterface( AutoI18nCustomInterface.class.cast( o ) );
            } 
            else {
                // setValueFromField( f );
                key = getKey( f );
                setValueFromKey( f, key );
            }
        }
        catch( MissingResourceException e ) {
            this.exceptionHandler.handleMissingResourceException( e, f, key );
        }
        catch( IllegalArgumentException e ) {
            this.exceptionHandler.handleIllegalArgumentException( e );
        }
        catch( IllegalAccessException e ) {
            this.exceptionHandler.handleIllegalAccessException( e );
        }
    }

    // Warning !
    // Warning !
    // Warning !
    /**
     * This method is override by AutoI18UpdateBundle
     * Should be the only entry point to get String from resource bundle
     */
    protected void setAutoI18nCustomInterface( AutoI18nCustomInterface autoI18n )
    {
        autoI18n.setI18n( this.i18n );
    }
    
    protected String getKey( Field f ) 
    {
        return this.objectToI18nClassNamePrefix + f.getName();
    }

    private void setValueFromKey(
            Field   f,
            String  k
            ) 
    throws  IllegalArgumentException,
            IllegalAccessException 
    {
        if( f.getAnnotation( I18nString.class ) != null ) {
            f.setAccessible( true );
            Object o = f.get( objectToI18n );
            
            if( o instanceof String ) {
                f.set( this.objectToI18n, i18n.getString( k ) );
                return;
            }
        }
        Class<?> fclass = f.getType();
        
        if( fclass.isAssignableFrom( AutoI18nBasicInterface.class ) ) {
            f.setAccessible( true );
            Object o = f.get( objectToI18n );
            
            AutoI18nBasicInterface.class.cast( o ).setI18nString( 
                    i18n.getString( k )
                    );
        }

        Key key = new Key( k );
        
        try {
            for(AutoI18nTypes.Type t:types) {
                if( fclass.isAssignableFrom( t.getType() ) ) {
                    f.setAccessible( true );
                    t.setText( 
                            f.get( objectToI18n ), 
                            key
                            );
                }
            }        
        }
        catch( MissingResourceException e ) {
            this.exceptionHandler.handleMissingResourceException( e, f, key );
       }
    }

    private void setValueFromAnnotation( Field f, String key, I18n anno ) 
        throws java.util.MissingResourceException
    {
        String methodName = anno.methodName();
        String keyValue   = this.i18n.getString( key );

        try {
            Method m = this.objectToI18nClass.getMethod( methodName, String.class );
            
            m.invoke( this.objectToI18n, keyValue );
        }
        catch( SecurityException e ) {
            this.exceptionHandler.handleSecurityException(e);
        }
        catch( NoSuchMethodException e ) {
            this.exceptionHandler.handleNoSuchMethodException(e);
        }
        catch( IllegalArgumentException e ) {
            this.exceptionHandler.handleIllegalArgumentException(e);
        }
        catch( IllegalAccessException e ) {
            this.exceptionHandler.handleIllegalAccessException(e);
        }
        catch( InvocationTargetException e ) {
            this.exceptionHandler.handleInvocationTargetException(e);
        }
     }

//    /**
//     * @return the AutoI18nExceptionHandler
//     */
//    protected final AutoI18nExceptionHandler getExceptionHandler()
//    {
//        return exceptionHandler;
//    }

    /**
     * @return the objectToI18n
     */
    protected final Object getObjectToI18n()
    {
        return objectToI18n;
    }

    /**
     * @return the objectToI18nClass
     */
    protected Class<?> getObjectToI18nClass()
    {
        return objectToI18nClass;
    }

//    /**
//     * @return the objectToI18nClassNamePrefix
//     */
//    protected String getObjectToI18nClassNamePrefix()
//    {
//        return objectToI18nClassNamePrefix;
//    }

    /**
     * @return the AutoI18nTypes
     */
    protected AutoI18nTypes getAutoI18nTypes()
    {
        return types;
    }
    
    public class Key
    {
        private String key;
        public Key( String key )
        {
            this.key = key;
        }
        public String getKey()
        {
            return key;
        }
        public String getKey(int index)
        {
            return key + '.' + index;
        }
        public String getValue()
            throws java.util.MissingResourceException
        {
            return i18n.getString( key );
        }
        public String getValue(int index)
            throws java.util.MissingResourceException
        {
            return i18n.getString( getKey(index) );
        }
    }

}


/*
private void setValueFromField( Field f ) 
{
//    try {
        f.setAccessible( true );
        //setValue( f.get( this.objectToI18n ), new ValueFromKey( f ) );
        setValue( f, new ValueFromKey( f ) );
//    }
//    catch( IllegalArgumentException e ) {
//        this.exceptionHandler.handleIllegalArgumentException(e);
//    }
//    catch( IllegalAccessException e ) {
//        this.exceptionHandler.handleIllegalAccessException(e);
//    }
}
*/


//protected abstract class AbstractFieldValue
//{
//  private Field field;
//  public AbstractFieldValue( Field f )
//  {
//      this.field = f;
//  }
//  public Field getField()
//  {
//      return this.field;
//  }
////  public Object getObjectToI18n()
////  {
////      return AutoI18n.this.objectToI18n;
////  }
//  public abstract String getValue();
//  public abstract String getKey();
//}
//
//protected class LaterValue extends AbstractFieldValue
//{
//  private String key;
//  public LaterValue( Field field, String key )
//  {
//      super( field );
//      this.key = key;
//  }
//  @Override
//  public String getValue()
//  {
//      throw new UnsupportedOperationException();
//  }
//  @Override
//  public String getKey()
//  {
//      return this.key;
//  }
//  
//}
//
//protected class ValueFromKey extends AbstractFieldValue
//{
//  private String key;
//  
//  public ValueFromKey(Field field, String key)
//  {
//      super(field);
//      this.key = key;
//  }
//  public ValueFromKey( Field field ) 
//  {
//      super(field);
//      this.key = AutoI18n.this.getKey( field );
//  }
//  public String getValue()
//  {
//      return getKeyValue( this );
//  }
//  @Override
//  public String getKey()
//  {
//      return this.key;
//  }
//}
/*
// Warning !
// This method is override by AutoI18UpdateBundle
// Should be the only entry point to get String from resource bundle
protected String getKeyValue( AbstractFieldValue abstractFieldValue )
    throws MissingResourceException
{
    return this.i18n.getString( abstractFieldValue.getKey() );
}
*/    
/*    
private void setValue0(
        Field               f,
        AbstractFieldValue  abstractFieldValue
        ) 
{
    Object fieldValue;
    
    try {
        fieldValue = f.get( this.objectToI18n );
    }
    catch( IllegalArgumentException e ) {
        this.exceptionHandler.handleIllegalArgumentException(e);
        return;
    }
    catch( IllegalAccessException e ) {
        this.exceptionHandler.handleIllegalAccessException(e);
        return;
    }

    try {
        if( fieldValue instanceof AutoI18nBasicInterface) {
            AutoI18nBasicInterface.class.cast( fieldValue ).setI18nString( abstractFieldValue.getValue() );
        }
        else if( fieldValue instanceof AutoI18nCustomInterface ) {
            setAutoI18nCustomInterface(AutoI18nCustomInterface.class.cast( fieldValue ));
        }
//        else if( fieldValue instanceof JLabel ) {
//            JLabel.class.cast( fieldValue ).setText( abstractFieldValue.getValue() );
//        }
//        else if( fieldValue instanceof AbstractButton ) {
//            AbstractButton.class.cast( fieldValue ).setText( abstractFieldValue.getValue() );
//        }
//        else if( fieldValue instanceof JCheckBox ) {
//            JCheckBox.class.cast( fieldValue ).setText( abstractFieldValue.getValue() );
//        }
//        else if( fieldValue instanceof String ) {
//            if( f.getAnnotation( I18nString.class ) != null ) {
//                f.set( this.objectToI18n, abstractFieldValue.getValue() );
//            }
//        }
//        else if( f.getType().isArray() ) {
//            if( f.getAnnotation( I18nString.class ) != null ) {
//                Class<?> ac = f.getType().getComponentType();
//                
//                if( ac.equals( String.class ) ) {
//                    int len = Array.getLength( fieldValue );
//                    
//                    for( int i=0; i<len; i++ ) {
//                        Array.set( fieldValue, i, value );
//                         
//                    }
//                }
//            }
//        }
    }
    catch( MissingResourceException e ) {
        this.exceptionHandler.handleMissingResourceException(
                e,
                f,
                abstractFieldValue.getKey()
                );
    }
    catch( IllegalArgumentException e ) {
        this.exceptionHandler.handleIllegalArgumentException( e );
    }
    catch( IllegalAccessException e ) {
        this.exceptionHandler.handleIllegalAccessException( e );
    }
}
*/