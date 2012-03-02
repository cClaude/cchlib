/**
 *
 */
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
 * @author Claude
 * @param <FRAME>
 * @param <OBJECT>
 *
 */
public class DefaultFrameFieldPopulator<FRAME, OBJECT>
    implements FrameFieldPopulator<FRAME, OBJECT>
{
    private static Logger slogger = Logger.getLogger(DefaultFrameFieldPopulator.class);
    private FRAME frame;
    private OBJECT object;

    /**
     * @param frame
     * @param object
     *
     */
    public DefaultFrameFieldPopulator(
            FRAME   frame,
            OBJECT  object
            )
    {
        this.frame  = frame;
        this.object = object;
    }

    @Override
    final public void populateFields(
            SwingIntrospectorRootItem<FRAME>    rootItem,
            IntrospectionItem<OBJECT>           iItem
            )
    throws  SwingIntrospectorIllegalAccessException,
            SwingIntrospectorIllegalArgumentException,
            IntrospectionInvokeException
    {
        Object value = iItem.getObjectValue( object );

        populateFieldsWithValue( rootItem, iItem, value );
    }

    /**
     *
     * @param rootItem
     * @param iItem
     * @param iItemValue
     * @throws SwingIntrospectorIllegalAccessException
     * @throws SwingIntrospectorIllegalArgumentException
     * @throws IntrospectionInvokeException
     */
    public void populateFieldsWithValue(
            SwingIntrospectorRootItem<FRAME>    rootItem,
            final IntrospectionItem<?>          iItem,
            Object                              iItemValue
            )
    throws  SwingIntrospectorIllegalAccessException,
            SwingIntrospectorIllegalArgumentException,
            IntrospectionInvokeException
    {
        if( iItemValue == null ) {
            throw new NullPointerException("Value not set [iItemValue for : " + object + ']' );
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
            slogger.fatal( "Don't kwown how to handle type:" + iItemValue.getClass() );
        }
    }

    /**
     * Handle frame populate operation for String.
     * <BR/>
     * <table class="TableCustomDescription">
     * <tr>
     * <th>Supported type</th>
     * <th>Handle description</th>
     * </tr>
     * <tr>
     * <td>{@link JTextComponent}</td>
     * <td>Set value using {@link JTextComponent#setText(String)}</td>
     * </tr>
     * </table>
     * <BR/>
     *
     * @param swItemIterable
     * @param iItemString
     * @throws SwingIntrospectorIllegalAccessException
     */
    //TODO: warning inconsistency:
    //      DefaulfFrameFieldPopulator - handle JTextComponent
    //      ObjectPopulatorHelper      - handle JTextField
    public void populate(
            final Iterable<SwingIntrospectorItem<FRAME>>    swItemIterable,
            final String                                    iItemString
            )
        throws SwingIntrospectorIllegalAccessException
    {
        for( SwingIntrospectorItem<FRAME> switem : swItemIterable ) {
            Object obj = switem.getFieldObject( frame );

            if( obj instanceof JTextComponent ) {
                JTextComponent c = JTextComponent.class.cast( obj );
                c.setText( iItemString );
            }
            else if( obj instanceof JLabel ) {
                JLabel c = JLabel.class.cast( obj );
                c.setText( iItemString );
            }
            else {
                //TODO: handle case ?
                slogger.fatal( "String: Don't kwown how to Component handle type:" + obj.getClass() );
            }
        }
    }

    /**
     *
     * @param swItemIterable
     * @param iItemBoolean
     * @throws SwingIntrospectorIllegalAccessException
     */
    public void populate(
            final Iterable<SwingIntrospectorItem<FRAME>>    swItemIterable,
            final Boolean                                   iItemBoolean
            )
    throws SwingIntrospectorIllegalAccessException
    {
        boolean first = true;

        for( SwingIntrospectorItem<FRAME> switem : swItemIterable ) {
            Object obj = switem.getFieldObject( frame );

            // set iItemBoolean on first item
            // set !iItemBoolean on all others items
            Boolean setValue;

            if( first ) {
                setValue = iItemBoolean;
                first    = false;
            }
            else {
                setValue = !iItemBoolean;
            }

            if( obj instanceof JTextComponent ) {
                JTextComponent c = JTextComponent.class.cast( obj );
                c.setText( setValue.toString() );
            }
            else if( obj instanceof JLabel ) {
                JLabel c = JLabel.class.cast( obj );
                c.setText( setValue.toString() );
            }
            else if( obj instanceof JCheckBox ) {
                JCheckBox c = JCheckBox.class.cast( obj );
                c.setSelected( setValue );
            }
            else {
                //TODO: handle case ?
                slogger.fatal( "Boolean: Don't kwown how to Component handle type:" + obj.getClass() );
            }
        }
    }

    /**
     *
     * @param swItemIterable
     * @param iItem
     * @param iItemInteger
     * @throws SwingIntrospectorIllegalAccessException
     */
    public void populate(
            final Iterable<SwingIntrospectorItem<FRAME>>    swItemIterable,
            final IntrospectionItem<?>                      iItem,
            final Integer                                   iItemInteger

            )
    throws SwingIntrospectorIllegalAccessException
    {
        for( SwingIntrospectorItem<FRAME> switem : swItemIterable ) {
            Object obj = switem.getFieldObject( frame );

            if( obj instanceof JTextComponent ) {
                JTextComponent jtf = JTextComponent.class.cast( obj );
                jtf.setText( iItemInteger.toString() );
            }
            else if( obj instanceof JLabel ) {
                JLabel c = JLabel.class.cast( obj );
                c.setText( iItemInteger.toString() );
            }
            else if( obj instanceof JComboBox ) {
                JComboBox<?> c = JComboBox.class.cast( obj );
                int index = iItemInteger - Integer.class.cast( iItem.getMinValue() );
                c.setSelectedIndex( index );
            }
            else if( obj instanceof JSlider ) {
                JSlider c = JSlider.class.cast( obj );
                c.setValue( iItemInteger );
            }
            else if( obj instanceof JSpinner ) {
                JSpinner c = JSpinner.class.cast( obj );
                c.setValue( iItemInteger );
            }
            else {
                //TODO: handle case ?
                slogger.fatal( "Integer: Don't kwown how to Component handle type:" + obj.getClass() );
            }
        }
    }}
