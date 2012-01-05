/**
 *
 */
package cx.ath.choisnet.swing.introspection.testcase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;
import cx.ath.choisnet.lang.introspection.IntrospectionException;
import cx.ath.choisnet.lang.introspection.IntrospectionInvokeException;
import cx.ath.choisnet.lang.introspection.method.DefaultIntrospectionItem;
import cx.ath.choisnet.swing.LimitedIntegerJTextField;
import cx.ath.choisnet.swing.introspection.SwingIntrospector;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorException;
import cx.ath.choisnet.swing.introspection.SwingIntrospectorIllegalAccessException;

/**
 * @see IntrospectionTest
 * @author Claude CHOISNET
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
class TstFrame
    extends JFrame
        implements MouseWheelListener
{
    private static final long serialVersionUID = 1L;
    private transient SwingIntrospector<TstFrame,TstObject,DefaultIntrospectionItem<TstObject>> introspector;
    private TstObject tstObject = new TstObject();

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
    //private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

    public TstFrame()
    {
        //http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5045691
        addMouseWheelListener(this);
        initComponents();
        initFixComponents();
    }

    private void initFixComponents()
    {
        //jFormattedTextField_TestFMTString$root.setValue( );
        setJFormattedTextFieldValue( 0xA1B2C3D4 );
        try {
            initComponentsWithException();
        }
        catch( SwingIntrospectorIllegalAccessException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch( SwingIntrospectorException e ) {
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
        catch(ParseException e) {
            e.printStackTrace();
            }
        return mask;
    }

    public void setJFormattedTextFieldValue( int intValue )
    {
        jFormattedTextField_TestFMTString$root.setValue( "11-22-33-44" );
    }

    private void initComponents() {
        add(getJScrollPaneMain(), BorderLayout.CENTER);
        //add(getJPanelMain(), BorderLayout.CENTER);
        add(getjPanelBottom(), BorderLayout.SOUTH);
    	setSize(348, 263);
    }

    private JLabel getJLabel_TestIntegerJComboBox() {
    	if (jLabel_TestIntegerJComboBox == null) {
    		jLabel_TestIntegerJComboBox = new JLabel();
    		jLabel_TestIntegerJComboBox.setText("__");
    	}
    	return jLabel_TestIntegerJComboBox;
    }

    public JSpinner getJSpinner_TestIntegerJSpinner() {
    	if (jSpinner_TestIntegerJSpinner$root == null) {
    		jSpinner_TestIntegerJSpinner$root = new JSpinner();
    	}
    	return jSpinner_TestIntegerJSpinner$root;
    }

    private JLabel getJLabel_JSpinner() {
    	if (jLabel_JSpinner == null) {
    		jLabel_JSpinner = new JLabel();
    		jLabel_JSpinner.setText("JSpinner");
    	}
    	return jLabel_JSpinner;
    }

    private JButton getJButtonRandomObject() {
    	if (jButtonRandomObject == null) {
    	    jButtonRandomObject = new JButton();
    	    jButtonRandomObject.setText("Random Object");
    	    jButtonRandomObject.addMouseListener(new MouseAdapter() {

    			public void mousePressed(MouseEvent event) {
    				jButtonRandomObject_MouseMousePressed(event);
    			}
    		});
    	}
    	return jButtonRandomObject;
    }

    private JButton getJButtonPopulateFrame() {
        if (jButtonPopulateFrame == null) {
            jButtonPopulateFrame = new JButton();
            jButtonPopulateFrame.setText("O => Frame");
            jButtonPopulateFrame.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonPopulateFrame_MouseMousePressed(event);
                }
            });
        }
        return jButtonPopulateFrame;
    }

    private JButton getJButtonPopulateObject() {
        if (jButtonPopulateObject == null) {
            jButtonPopulateObject = new JButton();
            jButtonPopulateObject.setText("F => Object");
            jButtonPopulateObject.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent event) {
                    jButtonPopulateObject_MouseMousePressed(event);
                }
            });
        }
        return jButtonPopulateObject;
    }

    public JTextField getJTextField_TestIntegerJSlider() {
        if (jTextField_TestIntegerJSlider == null) {
            jTextField_TestIntegerJSlider = new JTextField();
            jTextField_TestIntegerJSlider.setText("___");
            jTextField_TestIntegerJSlider.setEditable( false );
        }
        return jTextField_TestIntegerJSlider;
    }

    private JLabel getJLabel_JTextField() {
        if (jLabel_JTextField == null) {
            jLabel_JTextField = new JLabel();
            jLabel_JTextField.setText("JTextField");
        }
        return jLabel_JTextField;
    }

    private JLabel getJLabel_JFormattedTextField() {
        if (jLabel_JFormattedTextField == null) {
            jLabel_JFormattedTextField = new JLabel();
            jLabel_JFormattedTextField.setText("JFormattedTextField");
        }
        return jLabel_JFormattedTextField;
    }

    private JLabel getJLabel_LimitedIntegerJTextField() {
        if (jLabel_LimitedIntegerJTextField == null) {
            jLabel_LimitedIntegerJTextField = new JLabel();
            jLabel_LimitedIntegerJTextField.setText("LimitedIntegerJTextField");
        }
        return jLabel_LimitedIntegerJTextField;
    }

    private JLabel getJLabel_JSlider() {
        if (jLabel_JSlider == null) {
            jLabel_JSlider = new JLabel();
            jLabel_JSlider.setText("JSlider");
        }
        return jLabel_JSlider;
    }

    private JLabel getJLabel_JComboBox() {
        if (jLabel_JComboBox == null) {
            jLabel_JComboBox = new JLabel();
            jLabel_JComboBox.setText("JComboBox");
        }
        return jLabel_JComboBox;
    }

    private JLabel getJLabel_JCheckBox() {
        if (jLabel_JCheckBox == null) {
            jLabel_JCheckBox = new JLabel();
            jLabel_JCheckBox.setText("JCheckBox");
        }
        return jLabel_JCheckBox;
    }

    private JPanel getJPanelMain() {
    	if (jPanelMain == null) {
    		jPanelMain = new JPanel();
//    		jPanelMain.setLayout(new GroupLayout());
//    		jPanelMain.add(getJLabel_JCheckBox(), new Constraints(new Leading(14, 12, 12), new Leading(12, 12, 12)));
//    		jPanelMain.add(getJCheckBox_TestBoolean(), new Constraints(new Leading(164, 10, 10), new Leading(8, 10, 217)));
//    		jPanelMain.add(getJLabel_JComboBox(), new Constraints(new Leading(14, 10, 10), new Leading(42, 12, 12)));
//    		jPanelMain.add(getJComboBox_TestIntegerJComboBox(), new Constraints(new Leading(164, 93, 10, 10), new Leading(40, 20, 10, 189)));
//    		jPanelMain.add(getJLabel_TestIntegerJComboBox(), new Constraints(new Leading(263, 12, 12), new Leading(42, 10, 191)));
//    		jPanelMain.add(getJLabel_JFormattedTextField(), new Constraints(new Leading(14, 12, 12), new Leading(76, 12, 12)));
//    		jPanelMain.add(getJFormattedTextField_TestFMTString(), new Constraints(new Leading(164, 93, 12, 12), new Leading(74, 10, 155)));
//    		jPanelMain.add(getJLabel_JSlider(), new Constraints(new Leading(14, 12, 12), new Leading(107, 12, 12)));
//    		jPanelMain.add(getJSlider_TestIntegerJSlider(), new Constraints(new Leading(164, 93, 12, 12), new Leading(105, 20, 10, 124)));
//    		jPanelMain.add(getJLabel_JTextField(), new Constraints(new Leading(14, 12, 12), new Leading(135, 12, 12)));
//    		jPanelMain.add(getJLabel_LimitedIntegerJTextField(), new Constraints(new Leading(14, 12, 12), new Leading(163, 12, 12)));
//    		jPanelMain.add(getJTextField_TestIntegerLimitedIntegerJTextField(), new Constraints(new Leading(164, 93, 12, 12), new Leading(161, 10, 68)));
//    		jPanelMain.add(getJTextField_TestIntegerJSlider(), new Constraints(new Leading(263, 12, 12), new Leading(105, 10, 124)));
//    		jPanelMain.add(getJTextField_TestString(), new Constraints(new Leading(164, 93, 10, 10), new Leading(133, 10, 96)));
//    		jPanelMain.add(getJLabel_JSpinner(), new Constraints(new Leading(14, 12, 12), new Leading(193, 12, 12)));
//    		jPanelMain.add(getJSpinner_TestIntegerJSpinner(), new Constraints(new Leading(164, 93, 12, 12), new Leading(189, 12, 12)));
            jPanelMain.setLayout(new GridLayout(7,3));

            jPanelMain.add(getJLabel_JCheckBox());
            jPanelMain.add(getJCheckBox_TestBoolean());
            jPanelMain.add(Box.createHorizontalGlue());

            jPanelMain.add(getJLabel_JComboBox());
            jPanelMain.add(getJComboBox_TestIntegerJComboBox());
            jPanelMain.add(getJLabel_TestIntegerJComboBox());

            jPanelMain.add(getJLabel_JFormattedTextField());
            jPanelMain.add(getJFormattedTextField_TestFMTString());
            jPanelMain.add(Box.createHorizontalGlue());

            jPanelMain.add(getJLabel_JSlider());
            jPanelMain.add(getJSlider_TestIntegerJSlider());
            jPanelMain.add(getJTextField_TestIntegerJSlider());

            jPanelMain.add(getJLabel_JSpinner());
            jPanelMain.add(getJSpinner_TestIntegerJSpinner());
            jPanelMain.add(Box.createHorizontalGlue());

            jPanelMain.add(getJLabel_LimitedIntegerJTextField());
            jPanelMain.add(getJTextField_TestIntegerLimitedIntegerJTextField());
            jPanelMain.add(Box.createHorizontalGlue());

            jPanelMain.add(getJLabel_JTextField());
            jPanelMain.add(getJTextField_TestString());
            jPanelMain.add(Box.createHorizontalGlue());
    	}
    	return jPanelMain;
    }

    private JPanel getjPanelBottom() {
    	if (jPanelBottom == null) {
    		jPanelBottom = new JPanel();
            jPanelBottom.add(getJButtonRandomObject());
    		jPanelBottom.add(getJButtonPopulateObject());
    		jPanelBottom.add(getJButtonPopulateFrame());
    	}
    	return jPanelBottom;
    }

    private JScrollPane getJScrollPaneMain() {
    	if (jScrollPaneMain == null) {
    		jScrollPaneMain = new JScrollPane();
    		jScrollPaneMain.setViewportView(getJPanelMain());
    		//jScrollPaneMain.addMouseWheelListener(this);
    		jScrollPaneMain.setWheelScrollingEnabled( false );
    	}
    	return jScrollPaneMain;
    }

    public LimitedIntegerJTextField getJTextField_TestIntegerLimitedIntegerJTextField() {
        if (jTextField_TestIntegerLimitedIntegerJTextField$root == null) {
            jTextField_TestIntegerLimitedIntegerJTextField$root = new LimitedIntegerJTextField();
            jTextField_TestIntegerLimitedIntegerJTextField$root.setMaxValue(2147483647);
            jTextField_TestIntegerLimitedIntegerJTextField$root.setRadix(10);
        }
        return jTextField_TestIntegerLimitedIntegerJTextField$root;
    }

    public JFormattedTextField getJFormattedTextField_TestFMTString() {
        if (jFormattedTextField_TestFMTString$root == null) {
            jFormattedTextField_TestFMTString$root = new JFormattedTextField(getMaskFormatter());
            //jFormattedTextField_TestFMTString$root.setText("TestFMTString");
        }
        return jFormattedTextField_TestFMTString$root;
    }

    public JTextField getJTextField_TestString() {
        if (jTextField_TestString$root == null) {
            jTextField_TestString$root = new JTextField();
            jTextField_TestString$root.setText("TestString");
        }
        return jTextField_TestString$root;
    }

    public JSlider getJSlider_TestIntegerJSlider() {
        if (jSlider_TestIntegerJSlider$root == null) {
            jSlider_TestIntegerJSlider$root = new JSlider();
            jSlider_TestIntegerJSlider$root.setMaximum(30);
            jSlider_TestIntegerJSlider$root.setMinimum(20);
            jSlider_TestIntegerJSlider$root.setValue(25);
            jSlider_TestIntegerJSlider$root.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent event) {
                    jSlider_TestIntegerJSlider$root_ChangeStateChanged(event);
                }
            });
        }
        return jSlider_TestIntegerJSlider$root;
    }

    public JComboBox<String> getJComboBox_TestIntegerJComboBox() {
    	if (jComboBox_TestIntegerJComboBox$root == null) {
    		jComboBox_TestIntegerJComboBox$root = new JComboBox<String>();
    		jComboBox_TestIntegerJComboBox$root.setModel(new DefaultComboBoxModel<String>(new String[] { "item0", "item1", "item2", "item3" }));
    		jComboBox_TestIntegerJComboBox$root.setDoubleBuffered(false);
    		jComboBox_TestIntegerJComboBox$root.setBorder(null);
    		jComboBox_TestIntegerJComboBox$root.addItemListener(new ItemListener() {

    			public void itemStateChanged(ItemEvent event) {
    				jComboBox_TestIntegerJComboBox$rootItemItemStateChanged(event);
    			}
    		});
    	}
    	return jComboBox_TestIntegerJComboBox$root;
    }

    public JCheckBox getJCheckBox_TestBoolean() {
        if (jCheckBox_TestBoolean$root == null) {
            jCheckBox_TestBoolean$root = new JCheckBox();
            jCheckBox_TestBoolean$root.setSelected(true);
            jCheckBox_TestBoolean$root.setText("TestBoolean");
        }
        return jCheckBox_TestBoolean$root;
    }

//    @SuppressWarnings("unused")
//    private static void installLnF()
//    {
//        try {
//            String lnfClassname = PREFERRED_LOOK_AND_FEEL;
//            if( lnfClassname == null )
//                lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
//            UIManager.setLookAndFeel( lnfClassname );
//        }
//        catch( Exception e ) {
//            System.err.println( "Cannot install " + PREFERRED_LOOK_AND_FEEL
//                    + " on this platform:" + e.getMessage() );
//        }
//    }

    public static void main( String[] args )
    {
        //installLnF();
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                TstFrame frame = new TstFrame();
                try {
                    frame.initComponentsWithException();
                }
                catch( SwingIntrospectorIllegalAccessException e ) {
                    e.printStackTrace();
                }
                catch( SwingIntrospectorException e ) {
                    e.printStackTrace();
                }
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frame.setTitle( "TstXFrame" );
                frame.getContentPane().setPreferredSize( frame.getSize() );
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
            }
        } );
    }

    private void jSlider_TestIntegerJSlider$root_ChangeStateChanged(ChangeEvent event)
    {
        jTextField_TestIntegerJSlider.setText(
                Integer.toString(
                        jSlider_TestIntegerJSlider$root.getValue()
                        )
                );
    }

    private void jButtonPopulateFrame_MouseMousePressed(MouseEvent event)
    {
        System.out.println("jButtonPopulateFrame_MouseMousePressed");

        try {
            populateFrame();
            }
        catch( IntrospectionInvokeException e ) {
            throw new RuntimeException( e );
            }
        catch( SwingIntrospectorException e ) {
            throw new RuntimeException( e );
            }
    }

    private void jButtonPopulateObject_MouseMousePressed(MouseEvent event)
    {
        System.out.println("jButtonPopulateObject_MouseMousePressed");

        try {
            populateObject();
            }
        catch( SwingIntrospectorException e ) {
            throw new RuntimeException( e );
            }
        catch( IntrospectionException e ) {
            throw new RuntimeException( e );
            }
        System.out.println(tstObject);
    }

    private void jButtonRandomObject_MouseMousePressed(MouseEvent event)
    {
        System.out.println("jButtonRandomObject_MouseMousePressed");
        randomObject();
        System.out.println(tstObject);
    }

    public SwingIntrospector<TstFrame, TstObject, DefaultIntrospectionItem<TstObject>> getSwingIntrospector()
    {
        if( this.introspector == null ) {
            this.introspector = SwingIntrospector.buildSwingIntrospector(
                TstFrame.class,
                TstObject.class
                );
        }

        return this.introspector;
    }

    public TstObject getTstObject()
    {
        return tstObject;
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
        getSwingIntrospector().populateFrameWithException( this, tstObject );
    }

    public void populateObject()
        throws  SwingIntrospectorException,
                IntrospectionException
    {
        getSwingIntrospector().populateObjectWithException( this, tstObject );
    }

    public void randomObject()
    {
        tstObject.randomize();
    }

    private void jComboBox_TestIntegerJComboBox$rootItemItemStateChanged(ItemEvent event)
    {
       int    index = jComboBox_TestIntegerJComboBox$root.getSelectedIndex();
       //String text  = jComboBox_TestIntegerJComboBox$root.getItemAt( index ).toString();
       String text  = jComboBox_TestIntegerJComboBox$root.getItemAt( index );

       jLabel_TestIntegerJComboBox.setText( text );
    }

    // scroll a combobox if it has the focus
    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5045691
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        final Component focusOwner = getFocusOwner();

        System.out.printf( "mouseWheelMoved: %s\n", focusOwner );

        if( focusOwner instanceof JComboBox ) {
				JComboBox<?> combo = JComboBox.class.cast( focusOwner );

                if (e.getWheelRotation() < 0) {
                    // scroll up
                    int newIndex = combo.getSelectedIndex() - 1;
                    if (newIndex >= 0) {
                        combo.setSelectedIndex(newIndex);
                    }
                }
                else {
                    // scroll down
                    int newIndex = combo.getSelectedIndex() + 1;
                    if (newIndex < combo.getItemCount()) {
                        combo.setSelectedIndex(newIndex);
                    }
                }
            }
        else if( focusOwner instanceof JSlider ) {
            JSlider slider = (JSlider)focusOwner;

            if (e.getWheelRotation() < 0) {
                // scroll up
                int newIndex = slider.getValue() - 1;
                if (newIndex >= slider.getMinimum()) {
                    slider.setValue(newIndex);
                }
            }
            else {
                // scroll down
                int newIndex = slider.getValue() + 1;
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
}
