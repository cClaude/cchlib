package cx.ath.choisnet.swing.introspection;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.ParseException;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.text.MaskFormatter;
import com.googlecode.cchlib.swing.textfield.LimitedIntegerJTextField;
import cx.ath.choisnet.lang.introspection.IntrospectionException;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;

/**
 * @see IntrospectionTest
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
@SuppressWarnings({
    "squid:MaximumInheritanceDepth", // Swing !
    "squid:S00100", // naming convention for methods (generated code)
    "squid:S00116", // naming convention for fields (swing.introspection convention)
    })
class TstFrameApp
    extends JFrame
        implements MouseWheelListener
{
    private static final long serialVersionUID = 1L;

    private transient SwingIntrospector<TstFrameApp,TstObject,DefaultIntrospectionItem<TstObject>> introspector;

    private final TstObject tstObject = new TstObject();

    private JLabel                      jLabel_JCheckBox;
    private JCheckBox                   jCheckBox_TestBoolean$root;
    private JLabel                      jLabel_JComboBox;
    private JComboBox<String>           jComboBox_TestIntegerJComboBox$root;
    private JLabel                      jLabel_TestIntegerJComboBox;
    private JLabel                      jLabel_JFormattedTextField;
    private JFormattedTextField         jFormattedTextField_TestFMTString$root;
    private JLabel                      jLabel_JSlider;
    private JSlider                     jSlider_TestIntegerJSlider$root;
    private JTextField                  jTextField_TestIntegerJSlider;
    private JLabel                      jLabel_JTextField;
    private JTextField                  jTextField_TestString$root;
    private JLabel                      jLabel_LimitedIntegerJTextField;
    private LimitedIntegerJTextField    jTextField_TestIntegerLimitedIntegerJTextField$root;
    private JLabel                      jLabel_JSpinner;
    private JSpinner                    jSpinner_TestIntegerJSpinner$root;
    private JScrollPane                 jScrollPaneMain;
    private JPanel                      jPanelMain;
    private JPanel                      jPanelBottom;
    private JButton                     jButtonRandomObject;
    private JButton                     jButtonPopulateFrame;
    private JButton                     jButtonPopulateObject;

    public TstFrameApp()
    {
        //http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5045691
        addMouseWheelListener(this);

        initComponents();
        initFixComponents();
    }

    private void initFixComponents()
    {
        setJFormattedTextFieldValue( 0xA1B2C3D4 );

        try {
            initComponentsWithException();
        }
        catch( final SwingIntrospectorIllegalAccessException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( final SwingIntrospectorException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private MaskFormatter getMaskFormatter()
    {
        MaskFormatter mask = null;

        try {
            mask = new MaskFormatter("HH-HH-HH-HH");
            mask.setPlaceholderCharacter('_');
            //mask.setValueClass( Integer.class );
            //mask.install( jFormattedTextField_TestFMTString$root );
            }
        catch(final ParseException e) {
            e.printStackTrace();
            }

        return mask;
    }

    public void setJFormattedTextFieldValue( final int intValue )
    {
        this.jFormattedTextField_TestFMTString$root.setValue( "11-22-33-44" );
    }

    private void initComponents()
    {
        add(getJScrollPaneMain(), BorderLayout.CENTER);
        add(getjPanelBottom(), BorderLayout.SOUTH);
        setSize(348, 263);
    }

    private JLabel getJLabel_TestIntegerJComboBox() {
        if (this.jLabel_TestIntegerJComboBox == null) {
            this.jLabel_TestIntegerJComboBox = new JLabel();
            this.jLabel_TestIntegerJComboBox.setText("__");
        }
        return this.jLabel_TestIntegerJComboBox;
    }

    public JSpinner getJSpinner_TestIntegerJSpinner() {
        if (this.jSpinner_TestIntegerJSpinner$root == null) {
            this.jSpinner_TestIntegerJSpinner$root = new JSpinner();
        }
        return this.jSpinner_TestIntegerJSpinner$root;
    }

    private JLabel getJLabel_JSpinner() {
        if (this.jLabel_JSpinner == null) {
            this.jLabel_JSpinner = new JLabel();
            this.jLabel_JSpinner.setText("JSpinner");
        }
        return this.jLabel_JSpinner;
    }

    private JButton getJButtonRandomObject() {
        if (this.jButtonRandomObject == null) {
            this.jButtonRandomObject = new JButton();
            this.jButtonRandomObject.setText("Random Object");
            this.jButtonRandomObject.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent event) {
                    jButtonRandomObject_MouseMousePressed(event);
                }
            });
        }
        return this.jButtonRandomObject;
    }

    private JButton getJButtonPopulateFrame() {
        if (this.jButtonPopulateFrame == null) {
            this.jButtonPopulateFrame = new JButton();
            this.jButtonPopulateFrame.setText("O => Frame");
            this.jButtonPopulateFrame.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent event) {
                    jButtonPopulateFrame_MouseMousePressed(event);
                }
            });
        }
        return this.jButtonPopulateFrame;
    }

    private JButton getJButtonPopulateObject() {
        if (this.jButtonPopulateObject == null) {
            this.jButtonPopulateObject = new JButton();
            this.jButtonPopulateObject.setText("F => Object");
            this.jButtonPopulateObject.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(final MouseEvent event) {
                    jButtonPopulateObject_MouseMousePressed(event);
                }
            });
        }
        return this.jButtonPopulateObject;
    }

    public JTextField getJTextField_TestIntegerJSlider() {
        if (this.jTextField_TestIntegerJSlider == null) {
            this.jTextField_TestIntegerJSlider = new JTextField();
            this.jTextField_TestIntegerJSlider.setText("___");
            this.jTextField_TestIntegerJSlider.setEditable( false );
        }
        return this.jTextField_TestIntegerJSlider;
    }

    private JLabel getJLabel_JTextField() {
        if (this.jLabel_JTextField == null) {
            this.jLabel_JTextField = new JLabel();
            this.jLabel_JTextField.setText("JTextField");
        }
        return this.jLabel_JTextField;
    }

    private JLabel getJLabel_JFormattedTextField() {
        if (this.jLabel_JFormattedTextField == null) {
            this.jLabel_JFormattedTextField = new JLabel();
            this.jLabel_JFormattedTextField.setText("JFormattedTextField");
        }
        return this.jLabel_JFormattedTextField;
    }

    private JLabel getJLabel_LimitedIntegerJTextField() {
        if (this.jLabel_LimitedIntegerJTextField == null) {
            this.jLabel_LimitedIntegerJTextField = new JLabel();
            this.jLabel_LimitedIntegerJTextField.setText("LimitedIntegerJTextField");
        }
        return this.jLabel_LimitedIntegerJTextField;
    }

    private JLabel getJLabel_JSlider() {
        if (this.jLabel_JSlider == null) {
            this.jLabel_JSlider = new JLabel();
            this.jLabel_JSlider.setText("JSlider");
        }
        return this.jLabel_JSlider;
    }

    private JLabel getJLabel_JComboBox() {
        if (this.jLabel_JComboBox == null) {
            this.jLabel_JComboBox = new JLabel();
            this.jLabel_JComboBox.setText("JComboBox");
        }
        return this.jLabel_JComboBox;
    }

    private JLabel getJLabel_JCheckBox() {
        if (this.jLabel_JCheckBox == null) {
            this.jLabel_JCheckBox = new JLabel();
            this.jLabel_JCheckBox.setText("JCheckBox");
        }
        return this.jLabel_JCheckBox;
    }

    private JPanel getJPanelMain() {
        if (this.jPanelMain == null) {
            this.jPanelMain = new JPanel();

            this.jPanelMain.setLayout(new GridLayout(7,3));

            this.jPanelMain.add(getJLabel_JCheckBox());
            this.jPanelMain.add(getJCheckBox_TestBoolean());
            this.jPanelMain.add(Box.createHorizontalGlue());

            this.jPanelMain.add(getJLabel_JComboBox());
            this.jPanelMain.add(getJComboBox_TestIntegerJComboBox());
            this.jPanelMain.add(getJLabel_TestIntegerJComboBox());

            this.jPanelMain.add(getJLabel_JFormattedTextField());
            this.jPanelMain.add(getJFormattedTextField_TestFMTString());
            this.jPanelMain.add(Box.createHorizontalGlue());

            this.jPanelMain.add(getJLabel_JSlider());
            this.jPanelMain.add(getJSlider_TestIntegerJSlider());
            this.jPanelMain.add(getJTextField_TestIntegerJSlider());

            this.jPanelMain.add(getJLabel_JSpinner());
            this.jPanelMain.add(getJSpinner_TestIntegerJSpinner());
            this.jPanelMain.add(Box.createHorizontalGlue());

            this.jPanelMain.add(getJLabel_LimitedIntegerJTextField());
            this.jPanelMain.add(getJTextField_TestIntegerLimitedIntegerJTextField());
            this.jPanelMain.add(Box.createHorizontalGlue());

            this.jPanelMain.add(getJLabel_JTextField());
            this.jPanelMain.add(getJTextField_TestString());
            this.jPanelMain.add(Box.createHorizontalGlue());
        }
        return this.jPanelMain;
    }

    private JPanel getjPanelBottom() {
        if (this.jPanelBottom == null) {
            this.jPanelBottom = new JPanel();
            this.jPanelBottom.add(getJButtonRandomObject());
            this.jPanelBottom.add(getJButtonPopulateObject());
            this.jPanelBottom.add(getJButtonPopulateFrame());
        }
        return this.jPanelBottom;
    }

    private JScrollPane getJScrollPaneMain() {
        if (this.jScrollPaneMain == null) {
            this.jScrollPaneMain = new JScrollPane();
            this.jScrollPaneMain.setViewportView( getJPanelMain() );
            this.jScrollPaneMain.setWheelScrollingEnabled( false );
        }
        return this.jScrollPaneMain;
    }

    public LimitedIntegerJTextField getJTextField_TestIntegerLimitedIntegerJTextField() {
        if (this.jTextField_TestIntegerLimitedIntegerJTextField$root == null) {
            this.jTextField_TestIntegerLimitedIntegerJTextField$root = new LimitedIntegerJTextField();
            this.jTextField_TestIntegerLimitedIntegerJTextField$root.setMaximum( 2147483647 );
            this.jTextField_TestIntegerLimitedIntegerJTextField$root.setRadix( 10 );
        }
        return this.jTextField_TestIntegerLimitedIntegerJTextField$root;
    }

    public JFormattedTextField getJFormattedTextField_TestFMTString() {
        if (this.jFormattedTextField_TestFMTString$root == null) {
            this.jFormattedTextField_TestFMTString$root = new JFormattedTextField(getMaskFormatter());
        }
        return this.jFormattedTextField_TestFMTString$root;
    }

    public JTextField getJTextField_TestString() {
        if (this.jTextField_TestString$root == null) {
            this.jTextField_TestString$root = new JTextField();
            this.jTextField_TestString$root.setText("TestString");
        }
        return this.jTextField_TestString$root;
    }

    public JSlider getJSlider_TestIntegerJSlider() {
        if (this.jSlider_TestIntegerJSlider$root == null) {
            this.jSlider_TestIntegerJSlider$root = new JSlider();
            this.jSlider_TestIntegerJSlider$root.setMaximum(30);
            this.jSlider_TestIntegerJSlider$root.setMinimum(20);
            this.jSlider_TestIntegerJSlider$root.setValue(25);
            this.jSlider_TestIntegerJSlider$root.addChangeListener(
                this::jSlider_TestIntegerJSlider$root_ChangeStateChanged
                );
        }
        return this.jSlider_TestIntegerJSlider$root;
    }

    public JComboBox<String> getJComboBox_TestIntegerJComboBox()
    {
        if (this.jComboBox_TestIntegerJComboBox$root == null) {
            this.jComboBox_TestIntegerJComboBox$root = new JComboBox<>();
            this.jComboBox_TestIntegerJComboBox$root.setModel(new DefaultComboBoxModel<String>(new String[] { "item0", "item1", "item2", "item3" }));
            this.jComboBox_TestIntegerJComboBox$root.setDoubleBuffered(false);
            this.jComboBox_TestIntegerJComboBox$root.setBorder(null);
            this.jComboBox_TestIntegerJComboBox$root.addItemListener(
                this::jComboBox_TestIntegerJComboBox$rootItemItemStateChanged
                );
        }
        return this.jComboBox_TestIntegerJComboBox$root;
    }

    public JCheckBox getJCheckBox_TestBoolean() {
        if (this.jCheckBox_TestBoolean$root == null) {
            this.jCheckBox_TestBoolean$root = new JCheckBox();
            this.jCheckBox_TestBoolean$root.setSelected(true);
            this.jCheckBox_TestBoolean$root.setText("TestBoolean");
        }
        return this.jCheckBox_TestBoolean$root;
    }

    private void jSlider_TestIntegerJSlider$root_ChangeStateChanged(final ChangeEvent event)
    {
        this.jTextField_TestIntegerJSlider.setText(
                Integer.toString(
                        this.jSlider_TestIntegerJSlider$root.getValue()
                        )
                );
    }

    private void jButtonPopulateFrame_MouseMousePressed(final MouseEvent event)
    {
        System.out.println("jButtonPopulateFrame_MouseMousePressed");

        try {
            populateFrame();
            }
        catch( final IntrospectionInvokeException e ) {
            throw new RuntimeException( e );
            }
        catch( final SwingIntrospectorException e ) {
            throw new RuntimeException( e );
            }
    }

    private void jButtonPopulateObject_MouseMousePressed(final MouseEvent event)
    {
        System.out.println("jButtonPopulateObject_MouseMousePressed");

        try {
            populateObject();
            }
        catch( final SwingIntrospectorException e ) {
            throw new RuntimeException( e );
            }
        catch( final IntrospectionException e ) {
            throw new RuntimeException( e );
            }
        System.out.println(this.tstObject);
    }

    private void jButtonRandomObject_MouseMousePressed(final MouseEvent event)
    {
        System.out.println("jButtonRandomObject_MouseMousePressed");
        randomObject();
        System.out.println(this.tstObject);
    }

    public SwingIntrospector<TstFrameApp, TstObject, DefaultIntrospectionItem<TstObject>> getSwingIntrospector()
    {
        if( this.introspector == null ) {
            this.introspector = SwingIntrospector.buildSwingIntrospector(
                TstFrameApp.class,
                TstObject.class
                );
        }

        return this.introspector;
    }

    public TstObject getTstObject()
    {
        return this.tstObject;
    }

    public void initComponentsWithException()
        throws  SwingIntrospectorIllegalAccessException,
                SwingIntrospectorException
    {
        getSwingIntrospector().initComponentsWithException( this );
    }

    public void populateFrame()
        throws  IntrospectionInvokeException,
                SwingIntrospectorException
    {
        getSwingIntrospector().populateFrameWithException( this, this.tstObject );
    }

    public void populateObject()
        throws  SwingIntrospectorException,
                IntrospectionException
    {
        getSwingIntrospector().populateObjectWithException( this, this.tstObject );
    }

    public void randomObject()
    {
        this.tstObject.randomize();
    }

    private void jComboBox_TestIntegerJComboBox$rootItemItemStateChanged(final ItemEvent event)
    {
       final int    index = this.jComboBox_TestIntegerJComboBox$root.getSelectedIndex();
       final String text  = this.jComboBox_TestIntegerJComboBox$root.getItemAt( index );

       this.jLabel_TestIntegerJComboBox.setText( text );
    }

    // scroll a combobox if it has the focus
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5045691
    @Override
    public void mouseWheelMoved(final MouseWheelEvent e)
    {
        final Component focusOwner = getFocusOwner();

        System.out.printf( "mouseWheelMoved: %s%n", focusOwner );

        if( focusOwner instanceof JComboBox ) {
                final JComboBox<?> combo = JComboBox.class.cast( focusOwner );

                if (e.getWheelRotation() < 0) {
                    // scroll up
                    final int newIndex = combo.getSelectedIndex() - 1;
                    if (newIndex >= 0) {
                        combo.setSelectedIndex(newIndex);
                    }
                }
                else {
                    // scroll down
                    final int newIndex = combo.getSelectedIndex() + 1;
                    if (newIndex < combo.getItemCount()) {
                        combo.setSelectedIndex(newIndex);
                    }
                }
            }
        else if( focusOwner instanceof JSlider ) {
            final JSlider slider = (JSlider)focusOwner;

            if (e.getWheelRotation() < 0) {
                // scroll up
                final int newIndex = slider.getValue() - 1;
                if (newIndex >= slider.getMinimum()) {
                    slider.setValue(newIndex);
                }
            }
            else {
                // scroll down
                final int newIndex = slider.getValue() + 1;
                if (newIndex <= slider.getMaximum()) {
                    slider.setValue(newIndex);
                }
            }
        }
//        else if( focusOwner instanceof JFormattedTextField ) {
//            JFormattedTextField jFormattedTextField = (JFormattedTextField)focusOwner;
//            //System.out.println( "JFormattedTextField: " + jFormattedTextField.getComponentCount());
//        }
//        else if( focusOwner instanceof JSpinner ) {
//            JSpinner spinner = (JSpinner)focusOwner;
//            System.out.println( "JSpinner");
//            if (e.getWheelRotation() < 0) {
//                // scroll up
//                Object newValue = spinner.getPreviousValue();
//
//                if( newValue != null ) {
//                    spinner.setValue( newValue );
//                }
//            }
//            else {
//                // scroll down
//                Object newValue = spinner.getNextValue();
//
//                if( newValue != null ) {
//                    spinner.setValue( newValue );
//                }
//            }
//        }
    }

    public static void main( final String[] args )
    {
        SwingUtilities.invokeLater( () -> {
            final TstFrameApp frame = new TstFrameApp();

            try {
                frame.initComponentsWithException();
            }
            catch( final SwingIntrospectorIllegalAccessException e ) {
                e.printStackTrace();
            }
            catch( final SwingIntrospectorException e ) {
                e.printStackTrace();
            }

            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setTitle( "TstXFrame" );
            frame.getContentPane().setPreferredSize( frame.getSize() );
            frame.pack();
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );
        });
    }
}
