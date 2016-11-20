// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.regexbuilder;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkListener;

//not public
abstract class RegExpBuilderPanel extends JPanel
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
        catch( final Exception e ) {
            e.printStackTrace();
            }
    }

    private void initComponents()
    {
        final BorderLayout    borderLayout1         = new BorderLayout();
        this.setLayout( borderLayout1 );
        this.setFont( new java.awt.Font( "Dialog", 0, 12 ) );
        this.setSize( new Dimension(720, 520) );
        final JSplitPane      jSplitPaneFrame0           = new JSplitPane();
        jSplitPaneFrame0.setOrientation( JSplitPane.VERTICAL_SPLIT );
        final JSplitPane      jSplitPane2           = new JSplitPane();
        jSplitPaneFrame0.setBottomComponent( jSplitPane2 );
        final HyperlinkListener hyperlinkListener = event -> {
            // TODO Auto-generated method stub
        };
        setPreferredSize( new Dimension(747, 632) );
        final JPanel       jPanel4               = new JPanel();
        final BorderLayout borderLayout4         = new BorderLayout();
        jPanel4.setLayout( borderLayout4 );
        final JPanel       jPanel5               = new JPanel();
        final JPanel       jPanel6               = new JPanel();
        final GridLayout   gridLayout2           = new GridLayout();
        final BorderLayout borderLayout5         = new BorderLayout();
        jPanel6.setAlignmentX( (float)0.5 );
        jPanel6.setLayout( borderLayout5 );
        jPanel5.setLayout( gridLayout2 );
        gridLayout2.setColumns( 5 );
        gridLayout2.setHgap( 2 );
        gridLayout2.setRows( 2 );
        gridLayout2.setVgap( 2 );
        final JButton      btnMatch              = new JButton();
        btnMatch.setText( "Match Test" );
        btnMatch.addActionListener( new REBF_btnMatch_actionAdapter( this ) );
        final JButton      btnSplit              = new JButton();
        btnSplit.setText( "Split" );
        btnSplit.addActionListener( new REBF_btnSplit_actionAdapter( this ) );
        final JButton      btnObjects            = new JButton();
        btnObjects.setAlignmentY( (float)0.5 );
        btnObjects.setActionCommand( "Create Objects" );
        btnObjects.setText( "Create Objects" );
        btnObjects.addActionListener( new REBF_btnObjects_actionAdapter( this ) );
        final JButton      btnNextMatch          = new JButton();
        btnNextMatch.setText( "Next Match" );
        btnNextMatch.addActionListener( new REBF_btnNextMatch_actionAdapter( this ) );
        final JButton      btnObjReplace         = new JButton();
        btnObjReplace.setSelected( false );
        btnObjReplace.setText( "Obj Replace" );
        btnObjReplace.addActionListener( new REBF_btnObjReplace_actionAdapter( this ) );
        final JButton      btnObjSplit           = new JButton();
        btnObjSplit.setText( "Obj Split" );
        btnObjSplit.addActionListener( new REBF_btnObjSplit_actionAdapter( this ) );
        final JButton      btnReplace            = new JButton();
        btnReplace.setText( "Replace" );
        btnReplace.addActionListener( new REBF_btnReplace_actionAdapter( this ) );
        final JLabel       jLabel3               = new JLabel();
        jLabel3.setPreferredSize( new Dimension( 0, 0 ) );
        jLabel3.setRequestFocusEnabled( true );
        jLabel3.setText( "" );
        final JLabel       jLabel4               = new JLabel();
        this.textSubject           = new JTextArea();
        jLabel4.setLabelFor( this.textSubject );
        jLabel4.setText( "Test Subject:" );
        this.textSubject.setBorder( BorderFactory.createLoweredBevelBorder() );
        this.textSubject.setToolTipText( "" );
        this.textSubject
                .setText( "This is the default test subject for our regex test." );
        this.textSubject.setLineWrap( true );
        this.textSubject.setWrapStyleWord( true );
        final JPanel       jPanel8               = new JPanel();
        final GridLayout   gridLayout3           = new GridLayout();
        jPanel8.setLayout( gridLayout3 );
        gridLayout3.setColumns( 2 );
        gridLayout3.setHgap( 4 );
        final JPanel       jPanel7               = new JPanel();
        final BorderLayout borderLayout6         = new BorderLayout();
        jPanel7.setLayout( borderLayout6 );
        final JLabel       jLabel5               = new JLabel();
        this.textReplace           = new JTextArea();
        jLabel5.setMaximumSize( new Dimension( 89, 15 ) );
        jLabel5.setLabelFor( this.textReplace );
        jLabel5.setText( "Replacement Text:" );
        this.textReplace.setBorder( BorderFactory.createLoweredBevelBorder() );
        this.textReplace.setToolTipText( "" );
        this.textReplace.setText( "replacement" );
        this.textReplace.setLineWrap( true );
        this.textReplace.setWrapStyleWord( true );
        borderLayout4.setVgap( 4 );
        borderLayout1.setHgap( 0 );
        borderLayout1.setVgap( 0 );
        final JPanel       jPanel9               = new JPanel();
        final GridLayout   gridLayout4           = new GridLayout();
        jPanel9.setLayout( gridLayout4 );
        gridLayout4.setColumns( 2 );
        gridLayout4.setHgap( 4 );
        jSplitPane2.setOrientation( JSplitPane.VERTICAL_SPLIT );
        final JPanel       jPanel10              = new JPanel();
        final JPanel       jPanel11              = new JPanel();
        final BorderLayout borderLayout7         = new BorderLayout();
        final BorderLayout borderLayout8         = new BorderLayout();
        jPanel10.setLayout( borderLayout7 );
        jPanel11.setLayout( borderLayout8 );
        final JLabel       jLabel6               = new JLabel();
        jLabel6.setToolTipText( "" );
        this.textResults           = new JTextArea();
        jLabel6.setLabelFor( this.textResults );
        jLabel6.setText( "Results:" );
        this.textResults.setBorder( BorderFactory.createLoweredBevelBorder() );
        this.textResults.setText( "" );
        this.textResults.setLineWrap( true );
        this.textResults.setWrapStyleWord( true );
        final JLabel       jLabel7               = new JLabel();
        this.textReplaceResults    = new JTextArea();
        jLabel7.setLabelFor( this.textReplaceResults );
        jLabel7.setText( "Replacement Results:" );
        this.textReplaceResults.setBorder( BorderFactory.createLoweredBevelBorder() );
        this.textReplaceResults.setText( "" );
        this.textReplaceResults.setLineWrap( true );
        this.textReplaceResults.setWrapStyleWord( true );
        final JLabel       jLabel8               = new JLabel();
        jLabel8.setRequestFocusEnabled( true );
        jLabel8.setText( "" );
        final JButton btnAdvancedReplace = new JButton();
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
        jPanel6.add( this.textSubject, BorderLayout.CENTER );
        jPanel4.add( jPanel8, BorderLayout.CENTER );
        jPanel8.add( jPanel6, null );
        jSplitPaneFrame0.setDividerLocation( 150 );
        jSplitPane2.setDividerLocation( 200 );
        jPanel8.add( jPanel7, null );
        jPanel7.add( jLabel5, BorderLayout.NORTH );
        jPanel7.add( this.textReplace, BorderLayout.CENTER );
        jSplitPane2.add( jPanel9, JSplitPane.RIGHT );
        jPanel9.add( jPanel10, null );
        jPanel10.add( jLabel6, BorderLayout.NORTH );
        jPanel10.add( this.textResults, BorderLayout.CENTER );
        jPanel9.add( jPanel11, null );
        jPanel11.add( jLabel7, BorderLayout.NORTH );
        jPanel11.add( this.textReplaceResults, BorderLayout.CENTER );

        { // NOSONAR
        final JPanel panel = new JPanel();
        //FIXMEadd(panel, BorderLayout.WEST);
        jSplitPaneFrame0.setTopComponent( panel );
        panel.setLayout(new BorderLayout(0, 0));

        { // NOSONAR
            final JScrollPane scrollPane = new JScrollPane();
            panel.add(scrollPane);
            { // NOSONAR
                final JPanel          jPanelTopCmd               = new JPanel();
                scrollPane.setViewportView(jPanelTopCmd);
                jPanelTopCmd.setAlignmentY( (float)0.5 );
                final GridBagLayout gbl_jPanelTopCmd = new GridBagLayout(); // NOSONAR
                gbl_jPanelTopCmd.columnWidths = new int[]{0, 0, 0, 0};
                gbl_jPanelTopCmd.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
                gbl_jPanelTopCmd.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
                gbl_jPanelTopCmd.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
                jPanelTopCmd.setLayout(gbl_jPanelTopCmd);
                final JLabel          jLabel1             = new JLabel();
                final GridBagConstraints gbc_jLabel1 = new GridBagConstraints(); // NOSONAR
                gbc_jLabel1.insets = new Insets(0, 0, 5, 5);
                gbc_jLabel1.fill = GridBagConstraints.BOTH;
                gbc_jLabel1.gridx = 0;
                gbc_jLabel1.gridy = 0;
                jPanelTopCmd.add(jLabel1, gbc_jLabel1);
                jLabel1.setAlignmentX( (float)0.0 );
                jLabel1.setMinimumSize( new Dimension( 97, 15 ) );
                jLabel1.setText( "Regular Expression:" );
                final JEditorPane     jEditorPane_Info    = new JEditorPane(); // NOSONAR
                jEditorPane_Info.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                jEditorPane_Info.setOpaque(false);
                final GridBagConstraints gbc_jEditorPane_Info = new GridBagConstraints(); // NOSONAR
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
                final JLabel label2 = new JLabel();
                label2.setText("Regular Expression for Java source code:");
                label2.setMinimumSize(new Dimension(97, 15));
                label2.setAlignmentX(0.0f);
                final GridBagConstraints gbc_label2 = new GridBagConstraints(); // NOSONAR
                gbc_label2.fill = GridBagConstraints.BOTH;
                gbc_label2.gridwidth = 2;
                gbc_label2.insets = new Insets(0, 0, 5, 5);
                gbc_label2.gridx = 0;
                gbc_label2.gridy = 2;
                jPanelTopCmd.add(label2, gbc_label2);
                this.javaRegExp = new JTextArea();
                label2.setLabelFor(this.javaRegExp);
                this.javaRegExp.setText("Java text Area");
                final GridBagConstraints gbc_javaRegExp = new GridBagConstraints(); // NOSONAR
                gbc_javaRegExp.gridwidth = 3;
                gbc_javaRegExp.insets = new Insets(0, 0, 5, 0);
                gbc_javaRegExp.fill = GridBagConstraints.BOTH;
                gbc_javaRegExp.gridx = 0;
                gbc_javaRegExp.gridy = 3;
                jPanelTopCmd.add(this.javaRegExp, gbc_javaRegExp);
                this.checkCanonEquivalence = new JCheckBox();
                final GridBagConstraints gbc_checkCanonEquivalence = new GridBagConstraints(); // NOSONAR
                gbc_checkCanonEquivalence.fill = GridBagConstraints.BOTH;
                gbc_checkCanonEquivalence.insets = new Insets(0, 0, 5, 5);
                gbc_checkCanonEquivalence.gridx = 1;
                gbc_checkCanonEquivalence.gridy = 4;
                jPanelTopCmd.add(this.checkCanonEquivalence, gbc_checkCanonEquivalence);
                this.checkCanonEquivalence
                        .setText( "Ignore differences in Unicode encoding" );
                this.textRegExp           = new JTextArea();
                jLabel1.setLabelFor( this.textRegExp );
                this.textRegExp.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
                this.textRegExp.setBorder( BorderFactory.createLoweredBevelBorder() );
                this.textRegExp.setText( "t[a-z]+" );
                this.textRegExp.setLineWrap( true );
                final GridBagConstraints gbc_textRegExp = new GridBagConstraints(); // NOSONAR
                gbc_textRegExp.insets = new Insets(0, 0, 5, 0);
                gbc_textRegExp.gridwidth = 3;
                gbc_textRegExp.fill = GridBagConstraints.BOTH;
                gbc_textRegExp.gridx = 0;
                gbc_textRegExp.gridy = 1;
                jPanelTopCmd.add( this.textRegExp, gbc_textRegExp );
                this.checkDotAll           = new JCheckBox();
                final GridBagConstraints gbc_checkDotAll = new GridBagConstraints(); // NOSONAR
                gbc_checkDotAll.fill = GridBagConstraints.BOTH;
                gbc_checkDotAll.insets = new Insets(0, 0, 5, 5);
                gbc_checkDotAll.gridx = 0;
                gbc_checkDotAll.gridy = 4;
                jPanelTopCmd.add(this.checkDotAll, gbc_checkDotAll);
                this.checkDotAll.setText( "Dot matches newlines" );
                this.checkMultiLine        = new JCheckBox();
                final GridBagConstraints gbc_checkMultiLine = new GridBagConstraints(); // NOSONAR
                gbc_checkMultiLine.fill = GridBagConstraints.BOTH;
                gbc_checkMultiLine.insets = new Insets(0, 0, 0, 5);
                gbc_checkMultiLine.gridx = 1;
                gbc_checkMultiLine.gridy = 5;
                jPanelTopCmd.add(this.checkMultiLine, gbc_checkMultiLine);
                this.checkMultiLine.setText( "^ and $ match at embedded newlines" );
                this.checkCaseInsensitive  = new JCheckBox();
                final GridBagConstraints gbc_checkCaseInsensitive = new GridBagConstraints(); // NOSONAR
                gbc_checkCaseInsensitive.fill = GridBagConstraints.BOTH;
                gbc_checkCaseInsensitive.insets = new Insets(0, 0, 0, 5);
                gbc_checkCaseInsensitive.gridx = 0;
                gbc_checkCaseInsensitive.gridy = 5;
                jPanelTopCmd.add(this.checkCaseInsensitive, gbc_checkCaseInsensitive);
                this.checkCaseInsensitive.setText( "Case insensitive" );
            }
        }
        }


    }

    public abstract void btnMatch_actionPerformed( ActionEvent event ); // NOSONAR
    public abstract void btnSplit_actionPerformed( ActionEvent event ); // NOSONAR
    public abstract void btnAdvancedReplace_actionPerformed(ActionEvent event ); // NOSONAR
    public abstract void btnNextMatch_actionPerformed(ActionEvent event ); // NOSONAR
    public abstract void btnObjects_actionPerformed(ActionEvent event ); // NOSONAR
    public abstract void btnObjReplace_actionPerformed(ActionEvent event ); // NOSONAR
    public abstract void btnObjSplit_actionPerformed(ActionEvent event ); // NOSONAR
    public abstract void btnReplace_actionPerformed(ActionEvent event ); // NOSONAR

    protected JTextArea getJavaRegExp() {
        return this.javaRegExp;
    }

    protected JTextArea getTextRegExp() {
        return this.textRegExp;
    }

    protected JTextArea getTextReplaceResults() {
        return this.textReplaceResults;
    }
    protected JTextArea getTextSubject() {
        return this.textSubject;
    }
    protected JTextArea getTextResults() {
        return this.textResults;
    }
    protected JTextArea getTextReplace() {
        return this.textReplace;
    }
    protected JCheckBox getCheckCanonEquivalence() {
        return this.checkCanonEquivalence;
    }
    protected JCheckBox getCheckCaseInsensitive() {
        return this.checkCaseInsensitive;
    }
    protected JCheckBox getCheckDotAll() {
        return this.checkDotAll;
    }
    protected JCheckBox getCheckMultiLine() {
        return this.checkMultiLine;
    }
}
