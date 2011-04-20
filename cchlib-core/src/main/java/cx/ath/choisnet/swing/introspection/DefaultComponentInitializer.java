/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.method.Introspection;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;
import cx.ath.choisnet.swing.LimitedIntegerJTextField;

/**
 * @author Claude
 * @param <OBJECT> 
 * @param <OBJECT_ENTRY> 
 * 
 */
public class DefaultComponentInitializer<OBJECT,OBJECT_ENTRY extends IntrospectionItem<OBJECT>>
    implements ComponentInitializer
{
    private static Logger slogger = Logger.getLogger(DefaultComponentInitializer.class);
    private Introspection<OBJECT,OBJECT_ENTRY> introspection;

    public DefaultComponentInitializer(
            Introspection<OBJECT,OBJECT_ENTRY> introspection
            )
    {
        this.introspection = introspection;
    }
    
    @Override
    public void initComponent( 
            Object componentToInit, 
            String beanname 
            )
    throws SwingIntrospectorException
    {
        OBJECT_ENTRY iItem = introspection.getItem( beanname );

        initComponent( componentToInit, iItem, beanname );
    }

    /**
     * @param componentToInit 
     * @param iItem 
     * @param beanname 
     * @throws SwingIntrospectorException 
     * 
     */
    public void initComponent(
            final Object                componentToInit,
            final IntrospectionItem<?>  iItem,
            final String                beanname
            ) 
    throws SwingIntrospectorException
    {
        if( componentToInit instanceof JComboBox ) {
            JComboBox c = JComboBox.class.cast( componentToInit );
            
            int maxValue  = Integer.class.cast( iItem.getMaxValue() );
            int maxLength = (int)Math.log10( maxValue );
            
            setModel( c, iItem, maxLength, true );
        }
        else if( componentToInit instanceof JSlider ) {
            JSlider c = JSlider.class.cast( componentToInit );
            //initComponents( entry.getKey(), javax.swing.JSlider.class.cast( obj ) );
            setModel( c, iItem, beanname );
        }
        else if( componentToInit instanceof LimitedIntegerJTextField ) {
            LimitedIntegerJTextField c = LimitedIntegerJTextField.class.cast( componentToInit );
            //initComponents( entry.getKey(), LimitedIntegerJTextField.class.cast( obj ) );
            setModel( c, iItem, beanname );
        }
        else if( componentToInit instanceof JCheckBox ) {
            // Ignore: no min/max, no size !
        }
        else if( componentToInit instanceof JSpinner ) {
            JSpinner c = JSpinner.class.cast(componentToInit);
            setModel( c, iItem, beanname );
        }
        // else if( xx instanceof JTextField ) --> column ?
        else {// Dont't Know how to handle initialization for:
            slogger.info( "Dont't Know how to handle initialization for:" + componentToInit.getClass() );
        }
    }

    /**
     * @param js 
     * @param iItem 
     * @param beanname 
     * @throws SwingIntrospectorException 
     * 
     */
    public void setModel( 
            JSlider                 js, 
            IntrospectionItem<?>    iItem,
            String                  beanname 
            )
        throws SwingIntrospectorException
    {
        Object maxValue = iItem.getMaxValue();
        
        if( maxValue == null ) {
            throw new SwingIntrospectorNoMaxValueException( beanname, iItem );
        }
        
        js.setMaximum( Integer.class.cast( maxValue ) );

        int minValue = Integer.class.cast( iItem.getMinValue() );
        js.setMinimum( minValue );
        
        int defaultValue;
        
        if( iItem.getDefaultValue() == null ) {
            defaultValue = minValue;
        }
        else {
            defaultValue = Integer.class.cast( iItem.getDefaultValue() );
        }
        
        js.setValue( defaultValue );
     }

    /**
     * 
     * @param lijtf
     * @param iItem
     * @param beanname
     * @throws SwingIntrospectorException
     */
    public void setModel( 
            LimitedIntegerJTextField    lijtf, 
            IntrospectionItem<?>        iItem,
            String                      beanname 
            )
            throws SwingIntrospectorException
    {
        Object maxValue = iItem.getMaxValue();
        
        if( maxValue == null ) {
            throw new SwingIntrospectorNoMaxValueException( beanname, iItem );
        }
        lijtf.setMaxValue( Integer.class.cast( maxValue ) );

        Object defaultValue = iItem.getDefaultValue();
        int    defaultValueInt;
        
        if( defaultValue == null ) {
            defaultValueInt = 0;
        }
        else {
            defaultValueInt = Integer.class.cast( defaultValue );
        }

        lijtf.setValue( defaultValueInt );
    }

    /**
     * @param combo
     * @param iItem 
     * @param minLength
     * @param isRightAligned
     */
    public void setModel( 
            final JComboBox             combo, 
            final IntrospectionItem<?>  iItem,
            final int                   minLength, 
            final boolean               isRightAligned
            )
    {
        int minValue = Integer.class.cast( iItem.getMinValue() );
        int maxValue = Integer.class.cast( iItem.getMaxValue() );

        combo.setModel(
                new DefaultComboBoxModel(
                        //DeprecatedSwingHelpers.initObjectArray( minValue, maxValue, minLength, true )
                        initObjectArray( minValue, maxValue, minLength, true )
                        )
                );
    }
    
    public void setModel( 
            JSpinner                jSpinner, 
            IntrospectionItem<?>    iItem,
            String                  beanname 
            )
    {
        int minValue = Integer.class.cast( iItem.getMinValue() );
        int defValue = Integer.class.cast( iItem.getDefaultValue() );
        int maxValue = Integer.class.cast( iItem.getMaxValue() );
        
        jSpinner.setModel( 
                new SpinnerNumberModel(defValue,minValue,maxValue,1)
                );
    }

    /**
     *
     * @param min - minimum value in array
     * @param max - maximum value in array
     * @param minLength - minimum length of Strings
     * @param isRightAligned - should right align String content?
     * @return an Object array within String Objects
     */
    public final static Object[] initObjectArray( 
           final int       min,
           final int       max, 
           final int       minLength,
           final boolean   isRightAligned
           )
   {
       final StringBuilder padding = new StringBuilder();

       if( minLength > 0 ) {
           for( int i = 0; i<minLength; i++ ) {
               padding.append( ' ' );
           }
       }

       final String padStr = padding.toString();

       final int      len = max - min + 1;
       final Object[] object = new Object[ len ];

       for( int i = 0; i<len; i++ ) {
           String s = Integer.toString( min + i );
           int    l = s.length();

           if( l < minLength ) {
               if( isRightAligned ) {
                   s = padStr.substring( 0, minLength - l ) + s;
               } else {
                   s = s + padStr.substring( 0, minLength - l );
               }
           }

           object[ i ] = s;
       }

       return object;
   }    
}  
