package cx.ath.choisnet.swing.introspection;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;
import org.apache.log4j.Logger;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 *
 * @param <FRAME> Type of a frame object
 * @param <OBJECT> Type of the corresponding data object to bind on frame
 */
@SuppressWarnings("squid:S00119")
public class DefaultFrameFieldPopulator<FRAME, OBJECT>
    implements FrameFieldPopulator<FRAME, OBJECT>
{
    private static final Logger LOGGER = Logger.getLogger(DefaultFrameFieldPopulator.class);

    private final FRAME  frame;
    private final OBJECT object;

    /**
     * @param frame  A frame object
     * @param object Object to bind on frame
     */
    public DefaultFrameFieldPopulator(
        final FRAME   frame,
        final OBJECT  object
        )
    {
        this.frame  = frame;
        this.object = object;
    }

    @Override
    public final void populateFields(
            final SwingIntrospectorRootItem<FRAME>    rootItem,
            final IntrospectionItem<OBJECT>           iItem
            )
    throws  SwingIntrospectorIllegalAccessException,
            SwingIntrospectorIllegalArgumentException,
            IntrospectionInvokeException
    {
        final Object value = iItem.getObjectValue( this.object );

        populateFieldsWithValue( rootItem, iItem, value );
    }

    /**
     *
     * @param rootItem NEEDDOC
     * @param iItem NEEDDOC
     * @param iItemValue NEEDDOC
     * @throws SwingIntrospectorIllegalAccessException if any
     * @throws SwingIntrospectorIllegalArgumentException if any
     * @throws IntrospectionInvokeException if any
     */
    @SuppressWarnings("squid:S1160")
    public void populateFieldsWithValue(
        final SwingIntrospectorRootItem<FRAME>  rootItem,
        final IntrospectionItem<?>              iItem,
        final Object                            iItemValue
        ) throws
            SwingIntrospectorIllegalAccessException,
            SwingIntrospectorIllegalArgumentException,
            IntrospectionInvokeException
    {
        if( iItemValue == null ) {
            throw new NullPointerException("Value not set [iItemValue for : " + this.object + ']' );
        }
        else if( iItemValue instanceof String ) {
            populate( rootItem, String.class.cast( iItemValue ) );
        }
        else if( iItemValue instanceof Boolean ) {
            populate( rootItem, Boolean.class.cast( iItemValue ) );
        }
        else if( iItemValue instanceof Integer ) {
            populate( rootItem, iItem, Integer.class.cast( iItemValue ) );
        }
        else {
            //TODO: handle error ?
            LOGGER.fatal( "Don't kwown how to handle type:" + iItemValue.getClass() );
        }
    }

    /**
     * Handle frame populate operation for String.
     * <p>
     * <table class="TableCustomDescription">
     * <caption>Supported types / implementation</caption>
     * <tr>
     * <th>Supported type</th>
     * <th>Handle description</th>
     * </tr>
     * <tr>
     * <td>{@link JTextComponent}</td>
     * <td>Set value using {@link JTextComponent#setText(String)}</td>
     * </tr>
     * </table>
     * <BR>
     *
     * @param swItemIterable NEEDDOC
     * @param iItemString NEEDDOC
     * @throws SwingIntrospectorIllegalAccessException if any
     */
    //TODO: warning inconsistency:
    //      DefaulfFrameFieldPopulator - handle JTextComponent
    //      ObjectPopulatorHelper      - handle JTextField
    public void populate(
        final Iterable<SwingIntrospectorItem<FRAME>>    swItemIterable,
        final String                                    iItemString
        ) throws SwingIntrospectorIllegalAccessException
    {
        for( final SwingIntrospectorItem<FRAME> switem : swItemIterable ) {
            final Object obj = switem.getFieldObject( this.frame );

            if( obj instanceof JTextComponent ) {
                final JTextComponent c = JTextComponent.class.cast( obj );
                c.setText( iItemString );
            }
            else if( obj instanceof JLabel ) {
                final JLabel c = JLabel.class.cast( obj );
                c.setText( iItemString );
            }
            else {
                //TODO: handle case ?
                LOGGER.fatal( "String: Don't kwown how to Component handle type:" + obj.getClass() );
            }
        }
    }

    /**
     *
     * @param swItemIterable NEEDDOC
     * @param iItemBoolean NEEDDOC
     * @throws SwingIntrospectorIllegalAccessException if any
     */
    public void populate(
        final Iterable<SwingIntrospectorItem<FRAME>>    swItemIterable,
        final Boolean                                   iItemBoolean
        ) throws SwingIntrospectorIllegalAccessException
    {
        boolean first = true;

        for( final SwingIntrospectorItem<FRAME> switem : swItemIterable ) {
            final Object obj = switem.getFieldObject( this.frame );

            // set iItemBoolean on first item
            // set !iItemBoolean on all others items
            boolean setValue;

            if( first ) {
                setValue = iItemBoolean.booleanValue();
                first    = false;
                }
            else {
                setValue = !iItemBoolean.booleanValue();
                }

            if( obj instanceof JTextComponent ) {
                final JTextComponent c = JTextComponent.class.cast( obj );
                c.setText( Boolean.toString( setValue ) );
                }
            else if( obj instanceof JLabel ) {
                final JLabel c = JLabel.class.cast( obj );
                c.setText( Boolean.toString( setValue) );
                }
            else if( obj instanceof JCheckBox ) {
                final JCheckBox c = JCheckBox.class.cast( obj );
                c.setSelected( setValue );
                }
            else {
                //TODO: handle case ?
                LOGGER.fatal( "Boolean: Don't kwown how to Component handle type:" + obj.getClass() );
                }
        }
    }

    /**
     *
     * @param swItemIterable NEEDDOC
     * @param iItem NEEDDOC
     * @param iItemInteger NEEDDOC
     * @throws SwingIntrospectorIllegalAccessException if any
     */
    public void populate(
        final Iterable<SwingIntrospectorItem<FRAME>>    swItemIterable,
        final IntrospectionItem<?>                      iItem,
        final Integer                                   iItemInteger
        ) throws SwingIntrospectorIllegalAccessException
    {
        for( final SwingIntrospectorItem<FRAME> switem : swItemIterable ) {
            final Object obj = switem.getFieldObject( this.frame );

            if( obj instanceof JTextComponent ) {
                final JTextComponent jtf = JTextComponent.class.cast( obj );
                jtf.setText( iItemInteger.toString() );
                }
            else if( obj instanceof JLabel ) {
                final JLabel c = JLabel.class.cast( obj );
                c.setText( iItemInteger.toString() );
                }
            else if( obj instanceof JComboBox ) {
                final JComboBox<?> c = JComboBox.class.cast( obj );
                final int index = iItemInteger.intValue() - Integer.class.cast( iItem.getMinValue() ).intValue();
                c.setSelectedIndex( index );
                }
            else if( obj instanceof JSlider ) {
                final JSlider c = JSlider.class.cast( obj );
                c.setValue( iItemInteger.intValue() );
                }
            else if( obj instanceof JSpinner ) {
                final JSpinner c = JSpinner.class.cast( obj );
                c.setValue( iItemInteger );
                }
            else {
                //TODO: handle case ?
                LOGGER.fatal( "Integer: Don't kwown how to Component handle type:" + obj.getClass() );
                }
        }
    }
}
