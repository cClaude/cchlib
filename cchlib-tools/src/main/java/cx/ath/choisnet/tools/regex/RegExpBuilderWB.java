package cx.ath.choisnet.tools.regex;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class RegExpBuilderWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegExpBuilderWB frame = new RegExpBuilderWB();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RegExpBuilderWB()
    {
/*
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
*/
        enableEvents( AWTEvent.WINDOW_EVENT_MASK );

        try {
            initComponents();
            }
        catch( Exception e ) {
            e.printStackTrace();
            }
    }

    private void initComponents()
    {
        BorderLayout    borderLayout1         = new BorderLayout();
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout( borderLayout1 );
        this.setFont( new java.awt.Font( "Dialog", 0, 12 ) );
        this.setSize( new Dimension( 629, 523 ) );
        this.setTitle( "Regular Expressions Tester" );
        JPanel          jPanel1               = new JPanel();
        jPanel1.setAlignmentY( (float)0.5 );
        BorderLayout    borderLayout2         = new BorderLayout();
        jPanel1.setLayout( borderLayout2 );
        JSplitPane      jSplitPane1           = new JSplitPane();
        jSplitPane1.setOrientation( JSplitPane.VERTICAL_SPLIT );
        JSplitPane      jSplitPane2           = new JSplitPane();
        jSplitPane1.setBottomComponent( jSplitPane2 );
        jSplitPane1.setLeftComponent( jPanel1 );
        jSplitPane1.setRightComponent( null );
        BorderLayout    borderLayout3       = new BorderLayout();
        JPanel          jPanel2             = new JPanel();
        JLabel          jLabel1             = new JLabel();
        jPanel2.setLayout( borderLayout3 );
        jLabel1.setAlignmentX( (float)0.0 );
        jLabel1.setMinimumSize( new Dimension( 97, 15 ) );
        JTextArea       textRegex           = new JTextArea();
        jLabel1.setLabelFor( textRegex );
        jLabel1.setText( "Regular Expression:" );
        JEditorPane     jEditorPane_Info    = new JEditorPane();
        HyperlinkListener hyperlinkListener = new HyperlinkListener()
        {
            @Override
            public void hyperlinkUpdate( HyperlinkEvent event )
            {
                // TODO Auto-generated method stub
            }
        };
        jEditorPane_Info.addHyperlinkListener( hyperlinkListener  );
        jEditorPane_Info.setEditable(false);
        jEditorPane_Info.setContentType("text/html");
        jEditorPane_Info.setText( "<html>Visit <a href=\"http://www.regular-expressions.info\">http://www.regular-expressions.info</a> for a complete regex tutorial</html>" );
        textRegex.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
        textRegex.setBorder( BorderFactory.createLoweredBevelBorder() );
        textRegex.setText( "t[a-z]+" );
        textRegex.setLineWrap( true );
        JPanel          jPanel3               = new JPanel();
        GridLayout      gridLayout1           = new GridLayout();
        jPanel3.setLayout( gridLayout1 );
        gridLayout1.setColumns( 2 );
        gridLayout1.setHgap( 2 );
        gridLayout1.setRows( 2 );
        gridLayout1.setVgap( 2 );
        JCheckBox       checkDotAll           = new JCheckBox();
        JCheckBox       checkCanonEquivalence = new JCheckBox();
        JCheckBox       checkMultiLine        = new JCheckBox();
        JCheckBox    checkCaseInsensitive  = new JCheckBox();
        checkDotAll.setText( "Dot matches newlines" );
        checkCanonEquivalence
                .setText( "Ignore differences in Unicode encoding" );
        checkMultiLine.setText( "^ and $ match at embedded newlines" );
        checkCaseInsensitive.setText( "Case insensitive" );
        contentPane.setPreferredSize( new Dimension( 438, 142 ) );
        JPanel       jPanel4               = new JPanel();
        BorderLayout borderLayout4         = new BorderLayout();
        jPanel4.setLayout( borderLayout4 );
        JPanel       jPanel5               = new JPanel();
        JPanel       jPanel6               = new JPanel();
        GridLayout   gridLayout2           = new GridLayout();
        BorderLayout borderLayout5         = new BorderLayout();
        jPanel6.setAlignmentX( (float)0.5 );
        jPanel6.setLayout( borderLayout5 );
        jPanel5.setLayout( gridLayout2 );
        gridLayout2.setColumns( 5 );
        gridLayout2.setHgap( 2 );
        gridLayout2.setRows( 2 );
        gridLayout2.setVgap( 2 );
        JButton      btnMatch              = new JButton();
        btnMatch.setText( "Match Test" );
        btnMatch.addActionListener( new FrameRegexDemo_btnMatch_actionAdapter( this ) );
        JButton      btnSplit              = new JButton();
        btnSplit.setText( "Split" );
        btnSplit.addActionListener( new FrameRegexDemo_btnSplit_actionAdapter( this ) );
        JButton      btnObjects            = new JButton();
        btnObjects.setAlignmentY( (float)0.5 );
        btnObjects.setActionCommand( "Create Objects" );
        btnObjects.setText( "Create Objects" );
        btnObjects.addActionListener( new FrameRegexDemo_btnObjects_actionAdapter( this ) );
        JButton      btnNextMatch          = new JButton();
        btnNextMatch.setText( "Next Match" );
        btnNextMatch.addActionListener( new FrameRegexDemo_btnNextMatch_actionAdapter( this ) );
        JButton      btnObjReplace         = new JButton();
        btnObjReplace.setSelected( false );
        btnObjReplace.setText( "Obj Replace" );
        btnObjReplace.addActionListener( new FrameRegexDemo_btnObjReplace_actionAdapter( this ) );
        JButton      btnObjSplit           = new JButton();
        btnObjSplit.setText( "Obj Split" );
        btnObjSplit.addActionListener( new FrameRegexDemo_btnObjSplit_actionAdapter( this ) );
        JButton      btnReplace            = new JButton();
        btnReplace.setText( "Replace" );
        btnReplace.addActionListener( new FrameRegexDemo_btnReplace_actionAdapter( this ) );
        JLabel       jLabel3               = new JLabel();
        jLabel3.setPreferredSize( new Dimension( 0, 0 ) );
        jLabel3.setRequestFocusEnabled( true );
        jLabel3.setText( "" );
        JLabel       jLabel4               = new JLabel();
        JTextArea    textSubject           = new JTextArea();
        jLabel4.setLabelFor( textSubject );
        jLabel4.setText( "Test Subject:" );
        textSubject.setBorder( BorderFactory.createLoweredBevelBorder() );
        textSubject.setToolTipText( "" );
        textSubject
                .setText( "This is the default test subject for our regex test." );
        textSubject.setLineWrap( true );
        textSubject.setWrapStyleWord( true );
        JPanel       jPanel8               = new JPanel();
        GridLayout   gridLayout3           = new GridLayout();
        jPanel8.setLayout( gridLayout3 );
        gridLayout3.setColumns( 2 );
        gridLayout3.setHgap( 4 );
        JPanel       jPanel7               = new JPanel();
        BorderLayout borderLayout6         = new BorderLayout();
        jPanel7.setLayout( borderLayout6 );
        JLabel       jLabel5               = new JLabel();
        JTextArea    textReplace           = new JTextArea();
        jLabel5.setMaximumSize( new Dimension( 89, 15 ) );
        jLabel5.setLabelFor( textReplace );
        jLabel5.setText( "Replacement Text:" );
        textReplace.setBorder( BorderFactory.createLoweredBevelBorder() );
        textReplace.setToolTipText( "" );
        textReplace.setText( "replacement" );
        textReplace.setLineWrap( true );
        textReplace.setWrapStyleWord( true );
        borderLayout4.setVgap( 4 );
        borderLayout2.setVgap( 4 );
        borderLayout1.setHgap( 0 );
        borderLayout1.setVgap( 0 );
        JPanel       jPanel9               = new JPanel();
        GridLayout   gridLayout4           = new GridLayout();
        jPanel9.setLayout( gridLayout4 );
        gridLayout4.setColumns( 2 );
        gridLayout4.setHgap( 4 );
        jSplitPane2.setOrientation( JSplitPane.VERTICAL_SPLIT );
        JPanel       jPanel10              = new JPanel();
        JPanel       jPanel11              = new JPanel();
        BorderLayout borderLayout7         = new BorderLayout();
        BorderLayout borderLayout8         = new BorderLayout();
        jPanel10.setLayout( borderLayout7 );
        jPanel11.setLayout( borderLayout8 );
        JLabel       jLabel6               = new JLabel();
        jLabel6.setToolTipText( "" );
        JTextArea    textResults           = new JTextArea();
        jLabel6.setLabelFor( textResults );
        jLabel6.setText( "Results:" );
        textResults.setBorder( BorderFactory.createLoweredBevelBorder() );
        textResults.setText( "" );
        textResults.setLineWrap( true );
        textResults.setWrapStyleWord( true );
        JLabel       jLabel7               = new JLabel();
        JTextArea    textReplaceResults    = new JTextArea();
        jLabel7.setLabelFor( textReplaceResults );
        jLabel7.setText( "Replacement Results:" );
        textReplaceResults.setBorder( BorderFactory.createLoweredBevelBorder() );
        textReplaceResults.setText( "" );
        textReplaceResults.setLineWrap( true );
        textReplaceResults.setWrapStyleWord( true );
        JLabel       jLabel8               = new JLabel();
        jLabel8.setRequestFocusEnabled( true );
        jLabel8.setText( "" );
        JButton btnAdvancedReplace = new JButton();
        btnAdvancedReplace.setText( "Advanced Replace" );
        btnAdvancedReplace.addActionListener( new FrameRegexDemo_btnAdvancedReplace_actionAdapter( this ) );
        contentPane.add( jSplitPane1, BorderLayout.CENTER );
        jSplitPane1.add( jSplitPane2, JSplitPane.RIGHT );
        jSplitPane1.add( jPanel1, JSplitPane.LEFT );
        jPanel1.add( jPanel2, BorderLayout.NORTH );
        jPanel2.add( jLabel1, BorderLayout.CENTER );
        jPanel2.add( jEditorPane_Info, BorderLayout.EAST );
        jPanel1.add( textRegex, BorderLayout.CENTER );
        jPanel1.add( jPanel3, BorderLayout.SOUTH );
        jPanel3.add( checkDotAll, null );
        jPanel3.add( checkCaseInsensitive, null );
        jPanel3.add( checkMultiLine, null );
        jPanel3.add( checkCanonEquivalence, null );
        jSplitPane2.add( jPanel4, JSplitPane.LEFT );
        jPanel4.add( jPanel5, BorderLayout.SOUTH );
        jPanel5.add( btnMatch, null );
        jPanel5.add( jLabel3, null );
        jPanel5.add( jLabel8, null );
        jPanel5.add( btnReplace, null );
        jPanel5.add( btnSplit, null );
        jPanel5.add( btnObjects, null );
        jPanel5.add( btnNextMatch, null );
        jPanel5.add( btnObjReplace, null );
        jPanel5.add( btnAdvancedReplace, null );
        jPanel5.add( btnObjSplit, null );
        jPanel6.add( jLabel4, BorderLayout.NORTH );
        jPanel6.add( textSubject, BorderLayout.CENTER );
        jPanel4.add( jPanel8, BorderLayout.CENTER );
        jPanel8.add( jPanel6, null );
        jSplitPane1.setDividerLocation( 150 );
        jSplitPane2.setDividerLocation( 200 );
        jPanel8.add( jPanel7, null );
        jPanel7.add( jLabel5, BorderLayout.NORTH );
        jPanel7.add( textReplace, BorderLayout.CENTER );
        jSplitPane2.add( jPanel9, JSplitPane.RIGHT );
        jPanel9.add( jPanel10, null );
        jPanel10.add( jLabel6, BorderLayout.NORTH );
        jPanel10.add( textResults, BorderLayout.CENTER );
        jPanel9.add( jPanel11, null );
        jPanel11.add( jLabel7, BorderLayout.NORTH );
        jPanel11.add( textReplaceResults, BorderLayout.CENTER );
    }

}
