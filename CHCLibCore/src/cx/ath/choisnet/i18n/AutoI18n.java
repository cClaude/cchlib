/**
 * 
 */
package cx.ath.choisnet.i18n;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 * TODO: doc
 * @author Claude CHOISNET
 */
public class AutoI18n 
{
    private static Logger slogger = Logger.getLogger(AutoI18n.class);
    private Object        objectToI18n;
    private Class<?>      objectToI18nClass;
    private String        objectToI18nClassNamePrefix;
    private I18nInterface i18n;
    private AutoI18nExceptionHandler exceptionHandler; 
    
    public AutoI18n()
    {
    }
    
    public AutoI18n( I18nInterface i18n )
    {
        setI18n( i18n );
        setAutoI18nExceptionHandler(null);
    }

    public void setI18n( final I18nInterface i18n )
    {
        this.i18n = i18n;
    }
    
    public void setAutoI18nExceptionHandler( AutoI18nExceptionHandler h)
    {
        this.exceptionHandler = h;
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
    synchronized public <T> void performeI18N( 
            final T                     objectToI18n,
            final Class<? extends T>    clazz
            )
    {        
        if( this.i18n == null ) {
            setI18n( new I18nDefaultImpl() );
        }
        if( this.exceptionHandler == null ) {
            setAutoI18nExceptionHandler( new AutoI18nDefaultExceptionHandler() );
        }
        
        setObjectToI18n(objectToI18n,clazz);
        
        Field[] fields = this.objectToI18nClass.getDeclaredFields();
        
        for( Field f : fields ) {
            I18nIgnore ignoreIt = f.getAnnotation( I18nIgnore.class );
            
            if( ignoreIt != null ) {
                continue;
            }
            I18n anno = f.getAnnotation( I18n.class );
            
            if( anno != null ) {
                setValueFromAnnotation( f, anno );
            }
            else {
                setValueFromField( f );
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
        this.objectToI18nClassNamePrefix = this.objectToI18nClass.getName() + ".";
    }

    // Warning !
    // This method is override by AutoI18UpdateBundle
    // Should be the only entry point to get String from resource bundle
    protected String getKeyValue( AbstractFieldValue abstractFieldValue )
        throws MissingResourceException
    {
        return this.i18n.getString( abstractFieldValue.getKey() );
    }
    
    // Warning !
    // This method is override by AutoI18UpdateBundle
    // Should be the only entry point to get String from resource bundle
    protected void setAutoI18nCustomInterface( AutoI18nCustomInterface autoI18n )
    {
        autoI18n.setI18n( this.i18n );
    }
    
    private String getKey( Field f ) 
    {
        return this.objectToI18nClassNamePrefix + f.getName();
    }
    
    private void setValue( 
            Object      fieldValue, 
            AbstractFieldValue  abstractFieldValue
            ) 
    {
        try {
            if( fieldValue instanceof AutoI18nBasicInterface) {
                AutoI18nBasicInterface.class.cast( fieldValue ).setI18nString( abstractFieldValue.getValue() );
            }
            else if( fieldValue instanceof AutoI18nCustomInterface ) {
                setAutoI18nCustomInterface(AutoI18nCustomInterface.class.cast( fieldValue ));
            }
            else if( fieldValue instanceof JLabel ) {
                JLabel.class.cast( fieldValue ).setText( abstractFieldValue.getValue() );
            }
            else if( fieldValue instanceof AbstractButton ) {
                AbstractButton.class.cast( fieldValue ).setText( abstractFieldValue.getValue() );
            }
            else if( fieldValue instanceof JCheckBox ) {
                JCheckBox.class.cast( fieldValue ).setText( abstractFieldValue.getValue() );
            }
        }
        catch( MissingResourceException e ) {
            slogger.warn( 
                    "* MissingResourceException for:" 
                    + abstractFieldValue.getKey() 
                    + " - " 
                    + e.getLocalizedMessage(),
                    e
                    );
        }
    }

    private void setValueFromField( Field f ) 
    {
        try {
            f.setAccessible( true );
            setValue( f.get( this.objectToI18n ), new ValueFromKey( f ) );
        }
        catch( IllegalArgumentException e ) {
            this.exceptionHandler.handleIllegalArgumentException(e);
        }
        catch( IllegalAccessException e ) {
            this.exceptionHandler.handleIllegalAccessException(e);
        }
    }
    
    private void setValueFromAnnotation( Field f, I18n anno ) 
    {
        if( anno.methodName().length() == 0 ) {
            String key = anno.keyName();
            
            if( key.length() == 0 ) {
                setValue( f, new ValueFromKey( f ) );
            }
            else {
                setValue( f, new ValueFromKey( f, key ) );
            }
        }
        else {
            String methodName = anno.methodName();
            String key        = anno.keyName();
            String keyValue;
            
            if( key.isEmpty() ) {
                key = getKey( f );
            }

            keyValue = getKeyValue( new LaterValue( f, key ) );

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
     }

    protected abstract class AbstractFieldValue
    {
        private Field field;
        public AbstractFieldValue( Field f )
        {
            this.field = f;
        }
        public Field getField()
        {
            return this.field;
        }
        public Object getObjectToI18n()
        {
            return AutoI18n.this.objectToI18n;
        }
        public abstract String getValue();
        public abstract String getKey();
    }
    
    protected class LaterValue extends AbstractFieldValue
    {
        private String key;
        public LaterValue( Field field, String key )
        {
            super( field );
            this.key = key;
        }
        @Override
        public String getValue()
        {
            throw new UnsupportedOperationException();
        }
        @Override
        public String getKey()
        {
            return this.key;
        }
        
    }
    
    protected class ValueFromKey extends AbstractFieldValue
    {
        private String key;
        
        public ValueFromKey(Field field, String key)
        {
            super(field);
            this.key = key;
        }
        public ValueFromKey( Field field ) 
        {
            super(field);
            this.key = AutoI18n.this.getKey( field );
        }
        public String getValue()
        {
            return getKeyValue( this );
        }
        @Override
        public String getKey()
        {
            return this.key;
        }
    }
}