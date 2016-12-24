package cx.ath.choisnet.swing.introspection;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;
import cx.ath.choisnet.lang.introspection.method.Introspection;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 * NEEDDOC
 *
 * @param <OBJECT> NEEDDOC
 * @param <OBJECT_ENTRY> NEEDDOC
 */
@SuppressWarnings("squid:S00119")
public class DefaultComponentInitializer<OBJECT,OBJECT_ENTRY extends IntrospectionItem<OBJECT>>
    implements ComponentInitializer
{
    private static final Logger LOGGER = Logger.getLogger(DefaultComponentInitializer.class);

    private final Introspection<OBJECT,OBJECT_ENTRY> introspection;

    /**
     * NEEDDOC
     *
     * @param introspection NEEDDOC
     */
    public DefaultComponentInitializer(
            final Introspection<OBJECT,OBJECT_ENTRY> introspection
            )
    {
        this.introspection = introspection;
    }

    @Override
    public void initComponent(
            final Object componentToInit,
            final String beanname
            )
    throws SwingIntrospectorException
    {
        final OBJECT_ENTRY iItem = this.introspection.getItem( beanname );

        initComponent( componentToInit, iItem, beanname );
    }

    /**
     * NEEDDOC
     *
     * @param componentToInit NEEDDOC
     * @param iItem NEEDDOC
     * @param beanname NEEDDOC
     * @throws SwingIntrospectorException NEEDDOC
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
            final JComboBox<?> c = JComboBox.class.cast( componentToInit );

            final int maxValue  = Integer.class.cast( iItem.getMaxValue() ).intValue();
            final int maxLength = (int)Math.log10( maxValue );

            setModel( c, iItem, maxLength, true );
        }
        else if( componentToInit instanceof JSlider ) {
            final JSlider c = JSlider.class.cast( componentToInit );
            //initComponents( entry.getKey(), javax.swing.JSlider.class.cast( obj ) );
            setModel( c, iItem, beanname );
        }
        else if( componentToInit instanceof LimitedIntegerJTextField ) {
            final LimitedIntegerJTextField c = LimitedIntegerJTextField.class.cast( componentToInit );
            //initComponents( entry.getKey(), LimitedIntegerJTextField.class.cast( obj ) );
            setModel( c, iItem, beanname );
        }
        else if( componentToInit instanceof JCheckBox ) {
            // Ignore: no min/max, no size !
        }
        else if( componentToInit instanceof JSpinner ) {
            final JSpinner c = JSpinner.class.cast(componentToInit);
            setModel( c, iItem, beanname );
        }
        // else if( xx instanceof JTextField ) --> column ?
        else {// Dont't Know how to handle initialization for:
            LOGGER.info( "Dont't Know how to handle initialization for:" + componentToInit.getClass() );
        }
    }

    /**
     * NEEDDOC
     *
     * @param js NEEDDOC
     * @param iItem NEEDDOC
     * @param beanname NEEDDOC
     * @throws SwingIntrospectorException NEEDDOC
     *
     */
    public void setModel(
        final JSlider                 js,
        final IntrospectionItem<?>    iItem,
        final String                  beanname
        ) throws SwingIntrospectorException
    {
        final Object maxValue = iItem.getMaxValue();

        if( maxValue == null ) {
            throw new SwingIntrospectorNoMaxValueException( beanname, iItem );
        }

        js.setMaximum( Integer.class.cast( maxValue ).intValue() );

        final int minValue = Integer.class.cast( iItem.getMinValue() ).intValue();
        js.setMinimum( minValue );

        int defaultValue;

        if( iItem.getDefaultValue() == null ) {
            defaultValue = minValue;
        }
        else {
            defaultValue = Integer.class.cast( iItem.getDefaultValue() ).intValue();
        }

        js.setValue( defaultValue );
     }

    /**
     * NEEDDOC
     *
     * @param lijtf NEEDDOC
     * @param iItem NEEDDOC
     * @param beanname NEEDDOC
     * @throws SwingIntrospectorException NEEDDOC
     */
    public void setModel(
        final LimitedIntegerJTextField    lijtf,
        final IntrospectionItem<?>        iItem,
        final String                      beanname
        ) throws SwingIntrospectorException
    {
        final Object maxValue = iItem.getMaxValue();

        if( maxValue == null ) {
            throw new SwingIntrospectorNoMaxValueException( beanname, iItem );
        }
        lijtf.setMaximum( Integer.class.cast( maxValue ).intValue() );

        final Object defaultValue = iItem.getDefaultValue();
        int    defaultValueInt;

        if( defaultValue == null ) {
            defaultValueInt = 0;
        }
        else {
            defaultValueInt = Integer.class.cast( defaultValue ).intValue();
        }

        lijtf.setValue( defaultValueInt );
    }

    /**
     * Set the model
     *
     * @param <E>
     *            type of model content
     * @param combo
     *            a {@link JComboBox}
     * @param iItem
     *            a {@link IntrospectionItem}
     * @param minLength
     *            minimum length of Strings
     * @param isRightAligned
     *            should right align String content?
     */
    public <E> void setModel(
        final JComboBox<E>          combo,
        final IntrospectionItem<?>  iItem,
        final int                   minLength,
        final boolean               isRightAligned
        )
    {
        final int minValue = Integer.class.cast( iItem.getMinValue() ).intValue();
        final int maxValue = Integer.class.cast( iItem.getMaxValue() ).intValue();

        final ComboBoxModel<String> model = new DefaultComboBoxModel<>(
                //DeprecatedSwingHelpers.initObjectArray( minValue, maxValue, minLength, true )
                //initObjectArray( minValue, maxValue, minLength, true )
                initStringArray( minValue, maxValue, minLength, true )
                );

        // Hack to change type, to initialize content with strings
        @SuppressWarnings("unchecked")
        final
        JComboBox<String> comboString = (JComboBox<String>) combo; // $codepro.audit.disable unnecessaryCast

        comboString.setModel( model );
    }

    /**
     * NEEDDOC
     * @param jSpinner NEEDDOC
     * @param iItem NEEDDOC
     * @param beanname NEEDDOC
     */
    public void setModel(
        final JSpinner                jSpinner,
        final IntrospectionItem<?>    iItem,
        final String                  beanname
        )
    {
        final int minValue = Integer.class.cast( iItem.getMinValue() ).intValue();
        final int defValue = Integer.class.cast( iItem.getDefaultValue() ).intValue();
        final int maxValue = Integer.class.cast( iItem.getMaxValue() ).intValue();

        jSpinner.setModel(
                new SpinnerNumberModel(defValue,minValue,maxValue,1)
                );
    }

    /**
     * NEEDDOC
     *
     * @param min
     *            minimum value in array
     * @param max
     *            maximum value in array
     * @param minLength
     *            minimum length of Strings
     * @param isRightAligned
     *            should right align String content?
     * @return an Object array within String Objects
     */
   public static final String[] initStringArray(
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

      final int      len     = (max - min) + 1;
      final String[] strings = new String[ len ];

      for( int i = 0; i<len; i++ ) {
          String s = Integer.toString( min + i );
          final int    l = s.length();

          if( l < minLength ) {
              if( isRightAligned ) {
                  s = padStr.substring( 0, minLength - l ) + s;
              } else {
                  s = s + padStr.substring( 0, minLength - l );
              }
          }

          strings[ i ] = s;
      }

      return strings;
      }

    /**
     * NEEDDOC
     *
     * @param min
     *            minimum value in array
     * @param max
     *            maximum value in array
     * @param minLength
     *            minimum length of Strings
     * @param isRightAligned
     *            should right align String content?
     * @return an Object array within String Objects
     * @deprecated FIXME why ?
     */
    @Deprecated
    public static Object[] initObjectArray(
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

       final int      len = (max - min) + 1;
       final Object[] object = new Object[ len ];

       for( int i = 0; i<len; i++ ) {
           String s = Integer.toString( min + i );
           final int    l = s.length();

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
