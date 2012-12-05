package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public abstract class RegExpBuilderPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    //private JPanel contentPane;
    private JTextArea javaRegExp;
    private JTextArea textRegExp;
    private JTextArea textReplaceResults;
    private JTextArea textSubject;
    private JTextArea textResults;
    private JTextArea textReplace;
    private JCheckBox checkCanonEquivalence;
    private JCheckBox checkCaseInsensitive;
    private JCheckBox checkDotAll;
    private JCheckBox checkMultiLine;

    /**
     * Launch the application.
     *
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
    }*/

    /**
     * Create the frame.
     */
    public RegExpBuilderPanel()
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
        this.setLayout( borderLayout1 );
        this.setFont( new java.awt.Font( "Dialog", 0, 12 ) );
        this.setSize( new Dimension(720, 520) );
        JSplitPane      jSplitPaneFrame0           = new JSplitPane();
        jSplitPaneFrame0.setOrientation( JSplitPane.VERTICAL_SPLIT );
        JSplitPane      jSplitPane2           = new JSplitPane();
        jSplitPaneFrame0.setBottomComponent( jSplitPane2 );
        HyperlinkListener hyperlinkListener = new HyperlinkListener()
        {
            @Override
            public void hyperlinkUpdate( HyperlinkEvent event )
            {
                // TODO Auto-generated method stub
            }
        };
        setPreferredSize( new Dimension(747, 632) );
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
        btnMatch.addActionListener( new REBF_btnMatch_actionAdapter( this ) );
        JButton      btnSplit              = new JButton();
        btnSplit.setText( "Split" );
        btnSplit.addActionListener( new REBF_btnSplit_actionAdapter( this ) );
        JButton      btnObjects            = new JButton();
        btnObjects.setAlignmentY( (float)0.5 );
        btnObjects.setActionCommand( "Create Objects" );
        btnObjects.setText( "Create Objects" );
        btnObjects.addActionListener( new REBF_btnObjects_actionAdapter( this ) );
        JButton      btnNextMatch          = new JButton();
        btnNextMatch.setText( "Next Match" );
        btnNextMatch.addActionListener( new REBF_btnNextMatch_actionAdapter( this ) );
        JButton      btnObjReplace         = new JButton();
        btnObjReplace.setSelected( false );
        btnObjReplace.setText( "Obj Replace" );
        btnObjReplace.addActionListener( new REBF_btnObjReplace_actionAdapter( this ) );
        JButton      btnObjSplit           = new JButton();
        btnObjSplit.setText( "Obj Split" );
        btnObjSplit.addActionListener( new REBF_btnObjSplit_actionAdapter( this ) );
        JButton      btnReplace            = new JButton();
        btnReplace.setText( "Replace" );
        btnReplace.addActionListener( new REBF_btnReplace_actionAdapter( this ) );
        JLabel       jLabel3               = new JLabel();
        jLabel3.setPreferredSize( new Dimension( 0, 0 ) );
        jLabel3.setRequestFocusEnabled( true );
        jLabel3.setText( "" );
        JLabel       jLabel4               = new JLabel();
        textSubject           = new JTextArea();
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
        textReplace           = new JTextArea();
        jLabel5.setMaximumSize( new Dimension( 89, 15 ) );
        jLabel5.setLabelFor( textReplace );
        jLabel5.setText( "Replacement Text:" );
        textReplace.setBorder( BorderFactory.createLoweredBevelBorder() );
        textReplace.setToolTipText( "" );
        textReplace.setText( "replacement" );
        textReplace.setLineWrap( true );
        textReplace.setWrapStyleWord( true );
        borderLayout4.setVgap( 4 );
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
        textResults           = new JTextArea();
        jLabel6.setLabelFor( textResults );
        jLabel6.setText( "Results:" );
        textResults.setBorder( BorderFactory.createLoweredBevelBorder() );
        textResults.setText( "" );
        textResults.setLineWrap( true );
        textResults.setWrapStyleWord( true );
        JLabel       jLabel7               = new JLabel();
        textReplaceResults    = new JTextArea();
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
        btnAdvancedReplace.addActionListener( new REBF_btnAdvancedReplace_actionAdapter( this ) );
        add( jSplitPaneFrame0, BorderLayout.CENTER );
        jSplitPaneFrame0.add( jSplitPane2, JSplitPane.RIGHT );
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
        jSplitPaneFrame0.setDividerLocation( 150 );
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
        
        {
        JPanel panel = new JPanel();
        //FIXMEadd(panel, BorderLayout.WEST);
        jSplitPaneFrame0.setTopComponent( panel );
        panel.setLayout(new BorderLayout(0, 0));
        
        {
            JScrollPane scrollPane = new JScrollPane();
            panel.add(scrollPane);
            {
                JPanel          jPanelTopCmd               = new JPanel();
                scrollPane.setViewportView(jPanelTopCmd);
                jPanelTopCmd.setAlignmentY( (float)0.5 );
                GridBagLayout gbl_jPanelTopCmd = new GridBagLayout();
                gbl_jPanelTopCmd.columnWidths = new int[]{0, 0, 0, 0};
                gbl_jPanelTopCmd.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
                gbl_jPanelTopCmd.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
                gbl_jPanelTopCmd.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                jPanelTopCmd.setLayout(gbl_jPanelTopCmd);
                JLabel          jLabel1             = new JLabel();
                GridBagConstraints gbc_jLabel1 = new GridBagConstraints();
                gbc_jLabel1.insets = new Insets(0, 0, 5, 5);
                gbc_jLabel1.fill = GridBagConstraints.BOTH;
                gbc_jLabel1.gridx = 0;
                gbc_jLabel1.gridy = 0;
                jPanelTopCmd.add(jLabel1, gbc_jLabel1);
                jLabel1.setAlignmentX( (float)0.0 );
                jLabel1.setMinimumSize( new Dimension( 97, 15 ) );
                jLabel1.setText( "Regular Expression:" );
                JEditorPane     jEditorPane_Info    = new JEditorPane();
                jEditorPane_Info.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                jEditorPane_Info.setOpaque(false);
                GridBagConstraints gbc_jEditorPane_Info = new GridBagConstraints();
                gbc_jEditorPane_Info.fill = GridBagConstraints.BOTH;
                gbc_jEditorPane_Info.gridwidth = 2;
                gbc_jEditorPane_Info.insets = new Insets(0, 0, 5, 0);
                gbc_jEditorPane_Info.gridx = 1;
                gbc_jEditorPane_Info.gridy = 0;
                jPanelTopCmd.add(jEditorPane_Info, gbc_jEditorPane_Info);
                jEditorPane_Info.addHyperlinkListener( hyperlinkListener  );
                jEditorPane_Info.setEditable(false);
                jEditorPane_Info.setContentType("text/html");
                jEditorPane_Info.setText( "<html>Visit <a href=\"http://www.regular-expressions.info\">http://www.regular-expressions.info</a> for a complete regex tutorial</html>" );
                JLabel label2 = new JLabel();
                label2.setText("Regular Expression for Java source code:");
                label2.setMinimumSize(new Dimension(97, 15));
                label2.setAlignmentX(0.0f);
                GridBagConstraints gbc_label2 = new GridBagConstraints();
                gbc_label2.fill = GridBagConstraints.BOTH;
                gbc_label2.gridwidth = 2;
                gbc_label2.insets = new Insets(0, 0, 5, 5);
                gbc_label2.gridx = 0;
                gbc_label2.gridy = 2;
                jPanelTopCmd.add(label2, gbc_label2);
                javaRegExp = new JTextArea();
                label2.setLabelFor(javaRegExp);
                javaRegExp.setText("Java text Area");
                GridBagConstraints gbc_javaRegExp = new GridBagConstraints();
                gbc_javaRegExp.gridwidth = 3;
                gbc_javaRegExp.insets = new Insets(0, 0, 5, 0);
                gbc_javaRegExp.fill = GridBagConstraints.BOTH;
                gbc_javaRegExp.gridx = 0;
                gbc_javaRegExp.gridy = 3;
                jPanelTopCmd.add(javaRegExp, gbc_javaRegExp);
                checkCanonEquivalence = new JCheckBox();
                GridBagConstraints gbc_checkCanonEquivalence = new GridBagConstraints();
                gbc_checkCanonEquivalence.fill = GridBagConstraints.BOTH;
                gbc_checkCanonEquivalence.insets = new Insets(0, 0, 5, 5);
                gbc_checkCanonEquivalence.gridx = 1;
                gbc_checkCanonEquivalence.gridy = 4;
                jPanelTopCmd.add(checkCanonEquivalence, gbc_checkCanonEquivalence);
                checkCanonEquivalence
                        .setText( "Ignore differences in Unicode encoding" );
                textRegExp           = new JTextArea();
                jLabel1.setLabelFor( textRegExp );
                textRegExp.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
                textRegExp.setBorder( BorderFactory.createLoweredBevelBorder() );
                textRegExp.setText( "t[a-z]+" );
                textRegExp.setLineWrap( true );
                GridBagConstraints gbc_textRegExp = new GridBagConstraints();
                gbc_textRegExp.insets = new Insets(0, 0, 5, 0);
                gbc_textRegExp.gridwidth = 3;
                gbc_textRegExp.fill = GridBagConstraints.BOTH;
                gbc_textRegExp.gridx = 0;
                gbc_textRegExp.gridy = 1;
                jPanelTopCmd.add( textRegExp, gbc_textRegExp );
                checkDotAll           = new JCheckBox();
                GridBagConstraints gbc_checkDotAll = new GridBagConstraints();
                gbc_checkDotAll.fill = GridBagConstraints.BOTH;
                gbc_checkDotAll.insets = new Insets(0, 0, 5, 5);
                gbc_checkDotAll.gridx = 0;
                gbc_checkDotAll.gridy = 4;
                jPanelTopCmd.add(checkDotAll, gbc_checkDotAll);
                checkDotAll.setText( "Dot matches newlines" );
                checkMultiLine        = new JCheckBox();
                GridBagConstraints gbc_checkMultiLine = new GridBagConstraints();
                gbc_checkMultiLine.fill = GridBagConstraints.BOTH;
                gbc_checkMultiLine.insets = new Insets(0, 0, 0, 5);
                gbc_checkMultiLine.gridx = 1;
                gbc_checkMultiLine.gridy = 5;
                jPanelTopCmd.add(checkMultiLine, gbc_checkMultiLine);
                checkMultiLine.setText( "^ and $ match at embedded newlines" );
                checkCaseInsensitive  = new JCheckBox();
                GridBagConstraints gbc_checkCaseInsensitive = new GridBagConstraints();
                gbc_checkCaseInsensitive.fill = GridBagConstraints.BOTH;
                gbc_checkCaseInsensitive.insets = new Insets(0, 0, 0, 5);
                gbc_checkCaseInsensitive.gridx = 0;
                gbc_checkCaseInsensitive.gridy = 5;
                jPanelTopCmd.add(checkCaseInsensitive, gbc_checkCaseInsensitive);
                checkCaseInsensitive.setText( "Case insensitive" );
            }
        }
        }
        
                
    }

    public abstract void btnMatch_actionPerformed( ActionEvent event );
    public abstract void btnSplit_actionPerformed( ActionEvent event );
    public abstract void btnAdvancedReplace_actionPerformed(ActionEvent event );
    public abstract void btnNextMatch_actionPerformed(ActionEvent event );
    public abstract void btnObjects_actionPerformed(ActionEvent event );
    public abstract void btnObjReplace_actionPerformed(ActionEvent event );
    public abstract void btnObjSplit_actionPerformed(ActionEvent event );
    public abstract void btnReplace_actionPerformed(ActionEvent event );

    protected JTextArea getJavaRegExp() {
        return javaRegExp;
    }

    protected JTextArea getTextRegExp() {
        return textRegExp;
    }

    protected JTextArea getTextReplaceResults() {
        return textReplaceResults;
    }
    protected JTextArea getTextSubject() {
        return textSubject;
    }
    protected JTextArea getTextResults() {
        return textResults;
    }
    protected JTextArea getTextReplace() {
        return textReplace;
    }
    protected JCheckBox getCheckCanonEquivalence() {
        return checkCanonEquivalence;
    }
    protected JCheckBox getCheckCaseInsensitive() {
        return checkCaseInsensitive;
    }
    protected JCheckBox getCheckDotAll() {
        return checkDotAll;
    }
    protected JCheckBox getCheckMultiLine() {
        return checkMultiLine;
    }
}
