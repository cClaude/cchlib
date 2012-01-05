/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

import java.text.ParseException;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;
import cx.ath.choisnet.swing.LimitedIntegerJTextField;

/**
 *
 * @author CC
 */
public class ObjectPopulatorHelper 
{
    private ObjectPopulatorHelper()
    {//All static
    }
    
    /**
     * <p>
     * Transform field object into it's content using
     * following rules
     * </p>
     * <table class="TableCustomDescription">
     * <tr>
     * <th>Supported type</th>
     * <th>Handle description</th>
     * <th>return type</th>
     * </tr>
     * <tr>
     * <td>{@link JCheckBox}</td>
     * <td>Get value using {@link JCheckBox#isSelected()}</td>
     * <td>{@link Boolean}</td>
     * </tr>
      * <tr>
     * <td>{@link JComboBox}</td>
     * <td>Get value using {@link JComboBox#getSelectedIndex()}</td>
     * <td>{@link Integer}</td>
     * </tr>
     * <tr>
     * <td>{@link JSlider}</td>
     * <td>Get value using {@link JSlider#getValue()}</td>
     * <td>{@link Integer}</td>
     * </tr>
     * <tr>
     * <td>{@link JSpinner}</td>
     * <td>Get value using {@link JSpinner#getValue()}</td>
     * <td>returns the current value of the model, 
     * typically this value is displayed by the editor.
     * </td>
     * </tr>
     * <tr>
     * <td>{@link JTextField}</td>
     * <td>Get value using {@link JTextField#getText()}</td>
     * <td>{@link String}</td>
     * </tr>
     * <tr>
     * <td>{@link JFormattedTextField}</td>
     * <td>Call {@link JFormattedTextField#commitEdit()},
     * if a {@link ParseException} occur than goes throw a
     * {@link SwingIntrospectorParseException},
     * otherwise get value using {@link JFormattedTextField#getValue()}
     * </td>
     * <td>Returns the last valid value. Based on the editing policy
     * of the AbstractFormatter this may not return the current value.
     * </td>
     * </tr>
     * <tr>
     * <td>{@link LimitedIntegerJTextField}</td>
     * <td>Get value using {@link LimitedIntegerJTextField#getValue()}</td>
     * <td>{@link Integer}</td>
     * </tr>
     * </table>
     * 
     * @param <OBJECT>
     * @param fieldObject - this is typical result from {@link SwingIntrospectorItem#getFieldObject(Object)}
     * @param iItem 
     *
     * @return return value representing by this field
     *         (only some swing components are supported)
     * @throws SwingIntrospectorUnsupportedClassException if don't know how
     *         to handle type.
     * @throws SwingIntrospectorParseException 
     */
    public static <OBJECT> Object getFieldValue( 
            final Object                fieldObject,
            IntrospectionItem<OBJECT>   iItem
            )
        throws SwingIntrospectorUnsupportedClassException,
               SwingIntrospectorParseException
               //TODO: warning inconsistency:
               //      DefaulfFrameFieldPopulator - handle JTextComponent
               //      ObjectPopulatorHelper      - handle JTextField
    {
        Object value;

        if( fieldObject instanceof LimitedIntegerJTextField ) {
            LimitedIntegerJTextField f = LimitedIntegerJTextField.class.cast( fieldObject );
            value = new Integer( f.getValue() );
        }
        else if( fieldObject instanceof JFormattedTextField ) {
            JFormattedTextField f = JFormattedTextField.class.cast( fieldObject );
            //TODO: don't work !!!!!!
            
            try {
                f.commitEdit();
            }
            catch( ParseException e ) {
                throw new SwingIntrospectorParseException( e );
            }
            value = f.getValue();
        }
        else if( fieldObject instanceof JCheckBox ) {
            JCheckBox f = JCheckBox.class.cast( fieldObject );
            value = new Boolean( f.isSelected() );
        }
        else if( fieldObject instanceof JComboBox ) {
            JComboBox<?> f = JComboBox.class.cast( fieldObject );
            // Value is a relative value !
            int    index = f.getSelectedIndex();
            Object min   = iItem.getMinValue();
            
            if( min instanceof Integer ) {
                // Value should be modify if (min != 0) 
                index += Integer.class.cast( min );
            }
            
            value = new Integer( index );
        }
//        else if( fieldObject instanceof javax.swing.JLabel ) {
//
//        }
//        else if( fieldObject instanceof javax.swing.JMenu ) {
//
//        }
//        else if( fieldObject instanceof javax.swing.JMenuBar ) {
//
//        }
//        else if( fieldObject instanceof javax.swing.JMenuItem ) {
//
//        }
//        else if( fieldObject instanceof javax.swing.JPanel ) {
//
//        }
        else if( fieldObject instanceof JSlider ) {
            JSlider f = JSlider.class.cast( fieldObject );
            // Value is real value !
            value = new Integer( f.getValue() );
        }
        else if( fieldObject instanceof JSpinner ) {
            JSpinner f = JSpinner.class.cast( fieldObject );
            value = f.getValue();
        }
//        else if( fieldObject instanceof javax.swing.JTabbedPane ) {
//
//        }
        else if( fieldObject instanceof JTextField ) {
            JTextField f = JTextField.class.cast( fieldObject );
            value = f.getText();
        }
        else {
            throw new SwingIntrospectorUnsupportedClassException( fieldObject.getClass().getName() );
        }

        return value;
    }
}
