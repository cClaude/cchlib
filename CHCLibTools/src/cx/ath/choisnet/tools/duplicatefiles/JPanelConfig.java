package cx.ath.choisnet.tools.duplicatefiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18n;
import cx.ath.choisnet.i18n.I18nString;
import cx.ath.choisnet.i18n.I18nSwingHelper;
import cx.ath.choisnet.lang.ToStringBuilder;
import cx.ath.choisnet.swing.XComboBoxPattern;

/**
 *
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelConfig extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JPanelConfig.class );
    private ConfigMode mode;

    private JPanel jPanelMisc;
    private JPanel jPanelMiscLeft;
 
    @I18n(methodSuffixName="I18nTileUseFilesFilters")
    private JPanel jPanelUseFilesFilters;
    private JCheckBox jCheckBoxUseFilesFilters;
    private JComboBox jComboBoxFilesFilters;

    @I18n(methodSuffixName="I18nTileUseDirsFilters")
    private JPanel jPanelUseDirsFilters;
    private JCheckBox jCheckBoxUseDirsFilters;
    private JComboBox jComboBoxDirsFilters;

    @I18n(methodSuffixName="I18nTileIgnore")
    private JPanel jPanelIgnore;
    private JCheckBox jCheckBoxIgnoreEmptyFiles;
    private JCheckBox jCheckBoxFFIgnoreHidden;
    private JCheckBox jCheckBoxFDIgnoreHidden;
    private JCheckBox jCheckBoxIgnoreReadOnlyFiles;

    private ArrayList<FileTypeCheckBox> incFilesType = new ArrayList<FileTypeCheckBox>();
    private ArrayList<FileTypeCheckBox> excFilesType = new ArrayList<FileTypeCheckBox>();
    private ArrayList<FileTypeCheckBox> excDirsType  = new ArrayList<FileTypeCheckBox>();

    @I18n(methodSuffixName="I18nTileIncFilesFilter")
    private JScrollPane jScrollPaneIncFilesFilter;
    private JPanel jPanelIncFilesFilter;
    private JCheckBox jCheckBoxIFFUseRegEx;
    //private JTextField jTextFieldIFFRegEx;
    private XComboBoxPattern xComboBoxPatternIFFRegEx;

    @I18n(methodSuffixName="I18nTileExcFilesFilter")
    private JScrollPane jScrollPaneExcFilesFilter;
    private JPanel jPanelExcFilesFilter;
    private JCheckBox jCheckBoxEFFUseRegEx;
    //private JTextField jTextFieldEFFRegEx;
    private XComboBoxPattern xComboBoxPatternEFFRegEx;

    @I18n(methodSuffixName="I18nTileExcDirsFilter")
    private JScrollPane jScrollPaneExcDirsFilter;
    private JPanel jPanelExcDirsFilter;
    private JCheckBox jCheckBoxEFDUseRegEx;
    //private JTextField jTextFieldEFDRegEx;
    private XComboBoxPattern xComboBoxPatternEFDRegEx;

    @I18n(methodSuffixName="I18nTileIncDirsFilter")
    private JScrollPane jScrollPaneIncDirsFilter;
    private JPanel jPanelIncDirsFilter;
    private JCheckBox jCheckBoxIFDUseRegEx;
    //private JTextField jTextFieldIFDRegEx;
    private XComboBoxPattern xComboBoxPatternIFDRegEx;

    @I18nString private String txtIncludeFilesFilters = "Include filter";
    @I18nString private String txtExcludeFilesFilters = "Exclude filter";
    @I18nString private Object txtExcludeDirsFilters = "Exclude filter";
    @I18nString private Object txtIncludeDirsFilters = "Include filter";

    public JPanelConfig()
    {
        initComponents();
        initFixComponents();

        //add( Box.createVerticalGlue() );

        //Fix Bug VS4E
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        jPanelIncFilesFilter.setLayout(new BoxLayout(jPanelIncFilesFilter, BoxLayout.Y_AXIS));
        jPanelExcFilesFilter.setLayout(new BoxLayout(jPanelExcFilesFilter, BoxLayout.Y_AXIS));
        jPanelIncDirsFilter.setLayout(new BoxLayout(jPanelIncDirsFilter, BoxLayout.Y_AXIS));
    }

    public void setI18nTileIncFilesFilter(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jScrollPaneIncFilesFilter,localText);
    }

    public String getI18nTileIncFilesFilter()
    {
        return I18nSwingHelper.getTitledBorderTitle(jScrollPaneIncFilesFilter);
    }

    public void setI18nTileExcFilesFilter(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jScrollPaneExcFilesFilter,localText);
    }

    public String getI18nTileExcFilesFilter()
    {
        return I18nSwingHelper.getTitledBorderTitle(jScrollPaneExcFilesFilter);
    }

    public void setI18nTileIncDirsFilter(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jScrollPaneIncDirsFilter,localText);
    }

    public String getI18nTileIncDirsFilter()
    {
        return I18nSwingHelper.getTitledBorderTitle(jScrollPaneIncDirsFilter);
    }
    
    public void setI18nTileExcDirsFilter(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jScrollPaneExcDirsFilter,localText);
    }

    public String getI18nTileExcDirsFilter()
    {
        return I18nSwingHelper.getTitledBorderTitle(jScrollPaneExcDirsFilter);
    }
    
    public void setI18nTileUseFilesFilters(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jPanelUseFilesFilters,localText);
    }

    public String getI18nTileUseFilesFilters()
    {
        return I18nSwingHelper.getTitledBorderTitle(jPanelUseFilesFilters);
    }

    public void setI18nTileUseDirsFilters(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jPanelUseDirsFilters,localText);
    }

    public String getI18nTileUseDirsFilters()
    {
        return I18nSwingHelper.getTitledBorderTitle(jPanelUseDirsFilters);
    }

    public void setI18nTileIgnore(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jPanelIgnore,localText);
    }

    public String getI18nTileIgnore()
    {
        return I18nSwingHelper.getTitledBorderTitle(jPanelIgnore);
    }

    private void initComponents() {
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    	add(getJPanelMisc());
        add(getJScrollPaneIncFilesFilter());
    	add(getJScrollPaneExcFilesFilter());
    	add(getJScrollPaneIncDirsFilter());
    	add(getJScrollPaneExcDirsFilter());
    	setSize(627, 503);
    }

    private JPanel getJPanelUseDirsFilters() {
    	if (jPanelUseDirsFilters == null) {
    		jPanelUseDirsFilters = new JPanel();
    		jPanelUseDirsFilters.setBorder(BorderFactory.createTitledBorder(null, "Directories filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font(
    				"Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jPanelUseDirsFilters.setLayout(new BorderLayout());
    		jPanelUseDirsFilters.add(getJCheckBoxUseDirsFilters(), BorderLayout.WEST);
    		jPanelUseDirsFilters.add(getJComboBoxDirsFilters(), BorderLayout.CENTER);
    	}
    	return jPanelUseDirsFilters;
    }

    private JPanel getJPanelUseFilesFilters() {
    	if (jPanelUseFilesFilters == null) {
    		jPanelUseFilesFilters = new JPanel();
    		jPanelUseFilesFilters.setBorder(BorderFactory.createTitledBorder(null, "Files filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font(
    				"Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jPanelUseFilesFilters.setLayout(new BorderLayout());
    		jPanelUseFilesFilters.add(getJCheckBoxUseFilesFilters(), BorderLayout.WEST);
    		jPanelUseFilesFilters.add(getJComboBoxFilesFilters(), BorderLayout.CENTER);
    	}
    	return jPanelUseFilesFilters;
    }

    private JPanel getJPanelMiscLeft() {
    	if (jPanelMiscLeft == null) {
    		jPanelMiscLeft = new JPanel();
    		jPanelMiscLeft.setLayout(new GridLayout(2, 1));
    		jPanelMiscLeft.add(getJPanelUseFilesFilters());
    		jPanelMiscLeft.add(getJPanelUseDirsFilters());
    	}
    	return jPanelMiscLeft;
    }

    private JPanel getJPanelIgnore() {
    	if (jPanelIgnore == null) {
    		jPanelIgnore = new JPanel();
    		jPanelIgnore.setBorder(
    		        BorderFactory.createTitledBorder(null, "Ignore", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, new Font("Dialog",
    				Font.BOLD, 12), new Color(51, 51, 51)));
    		jPanelIgnore.setLayout(new GridLayout(2, 2));
    		jPanelIgnore.add(getJCheckBoxFFIgnoreHidden());
    		jPanelIgnore.add(getJCheckBoxIgnoreReadOnlyFiles());
    		jPanelIgnore.add(getJCheckBoxFDIgnoreHidden());
    		jPanelIgnore.add(getJCheckBoxIgnoreEmptyFiles());
    	}
    	return jPanelIgnore;
    }

    private JComboBox getJComboBoxDirsFilters() {
    	if (jComboBoxDirsFilters == null) {
    	    jComboBoxDirsFilters = new JComboBox();
    	}
    	return jComboBoxDirsFilters;
    }

    private JComboBox getJComboBoxFilesFilters() {
    	if (jComboBoxFilesFilters == null) {
    	    jComboBoxFilesFilters = new JComboBox();
    	}
    	return jComboBoxFilesFilters;
    }

    private XComboBoxPattern getXComboBoxPatternIFDRegEx() {
    	if (xComboBoxPatternIFDRegEx == null) {
    	    xComboBoxPatternIFDRegEx = new XComboBoxPattern();
    	}
    	return xComboBoxPatternIFDRegEx;
    }

    private XComboBoxPattern getXComboBoxPatternEFFRegEx() {
    	if (xComboBoxPatternEFFRegEx == null) {
    	    xComboBoxPatternEFFRegEx = new XComboBoxPattern();
    	}
    	return xComboBoxPatternEFFRegEx;
    }

    private JCheckBox getJCheckBoxEFFUseRegEx() {
    	if (jCheckBoxEFFUseRegEx == null) {
    	    jCheckBoxEFFUseRegEx = new JCheckBox();
    	    jCheckBoxEFFUseRegEx.setText("Use RegEx to exclude files (RegEx on name)");
    	}
    	return jCheckBoxEFFUseRegEx;
    }

    private JScrollPane getJScrollPaneIncDirsFilter() {
    	if (jScrollPaneIncDirsFilter == null) {
    		jScrollPaneIncDirsFilter = new JScrollPane();
    		jScrollPaneIncDirsFilter.setBorder(BorderFactory.createTitledBorder(null, "Include Directories Filter", TitledBorder.LEADING,
    				TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jScrollPaneIncDirsFilter.setViewportView(getJPanelIncDirsFilter());
    	}
    	return jScrollPaneIncDirsFilter;
    }

    private JPanel getJPanelIncDirsFilter() {
    	if (jPanelIncDirsFilter == null) {
    		jPanelIncDirsFilter = new JPanel();
    		jPanelIncDirsFilter.setLayout(new BoxLayout(jPanelIncDirsFilter, BoxLayout.Y_AXIS));
    		jPanelIncDirsFilter.add(getJCheckBoxIFDUseRegEx());
    		jPanelIncDirsFilter.add(getXComboBoxPatternIFDRegEx());
    	}
    	return jPanelIncDirsFilter;
    }

    private JPanel getJPanelExcFilesFilter() {
    	if (jPanelExcFilesFilter == null) {
    		jPanelExcFilesFilter = new JPanel();
    		jPanelExcFilesFilter.setLayout(new BoxLayout(jPanelExcFilesFilter, BoxLayout.Y_AXIS));
    	}
    	return jPanelExcFilesFilter;
    }

    private JCheckBox getJCheckBoxIFDUseRegEx() {
        if (jCheckBoxIFDUseRegEx == null) {
            jCheckBoxIFDUseRegEx = new JCheckBox();
            jCheckBoxIFDUseRegEx.setText("Use RegEx to include Directories (RegEx on name)");
        }
        return jCheckBoxIFDUseRegEx;
    }

    private JScrollPane getJScrollPaneExcFilesFilter() {
    	if (jScrollPaneExcFilesFilter == null) {
    		jScrollPaneExcFilesFilter = new JScrollPane();
    		jScrollPaneExcFilesFilter.setBorder(BorderFactory.createTitledBorder(null, "Exclude Files Filter", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
    				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jScrollPaneExcFilesFilter.setViewportView(getJPanelExcFilesFilter());
    	}
    	return jScrollPaneExcFilesFilter;
    }

    private JCheckBox getJCheckBoxIgnoreReadOnlyFiles() {
    	if (jCheckBoxIgnoreReadOnlyFiles == null) {
    		jCheckBoxIgnoreReadOnlyFiles = new JCheckBox();
    		jCheckBoxIgnoreReadOnlyFiles.setSelected(true);
    		jCheckBoxIgnoreReadOnlyFiles.setText("'readonly' files");
    	}
    	return jCheckBoxIgnoreReadOnlyFiles;
    }

    private JCheckBox getJCheckBoxFFIgnoreHidden() {
    	if (jCheckBoxFFIgnoreHidden == null) {
    		jCheckBoxFFIgnoreHidden = new JCheckBox();
    		jCheckBoxFFIgnoreHidden.setSelected(true);
    		jCheckBoxFFIgnoreHidden.setText("hidden files");
    	}
    	return jCheckBoxFFIgnoreHidden;
    }

    private JCheckBox getJCheckBoxFDIgnoreHidden() {
    	if (jCheckBoxFDIgnoreHidden == null) {
    		jCheckBoxFDIgnoreHidden = new JCheckBox();
    		jCheckBoxFDIgnoreHidden.setSelected(true);
    		jCheckBoxFDIgnoreHidden.setText("hidden directories");
    	}
    	return jCheckBoxFDIgnoreHidden;
    }

    private XComboBoxPattern getXComboBoxPatternEFDRegEx() {
        if (xComboBoxPatternEFDRegEx == null) {
            xComboBoxPatternEFDRegEx = new XComboBoxPattern();
        }
        return xComboBoxPatternEFDRegEx;
    }

    private JCheckBox getJCheckBoxEFDUseRegEx() {
        if (jCheckBoxEFDUseRegEx == null) {
            jCheckBoxEFDUseRegEx = new JCheckBox();
            jCheckBoxEFDUseRegEx.setText("Use RegEx to exclude Directories (RegEx on name)");
        }
        return jCheckBoxEFDUseRegEx;
    }

    private XComboBoxPattern getXComboBoxPatternIFFRegEx() {
        if (xComboBoxPatternIFFRegEx == null) {
            xComboBoxPatternIFFRegEx = new XComboBoxPattern();
        }
        return xComboBoxPatternIFFRegEx;
    }

    private JCheckBox getJCheckBoxIFFUseRegEx() {
        if (jCheckBoxIFFUseRegEx == null) {
            jCheckBoxIFFUseRegEx = new JCheckBox();
            jCheckBoxIFFUseRegEx.setText("Use RegEx to include files (RegEx on name)");
        }
        return jCheckBoxIFFUseRegEx;
    }

    private JPanel getJPanelIncFilesFilter() {
    	if (jPanelIncFilesFilter == null) {
    		jPanelIncFilesFilter = new JPanel();
    		//jPanelIncFilesFilter.setLayout(new BoxLayout(jPanelIncFilesFilter, BoxLayout.Y_AXIS));
    		//jPanelIncFilesFilter.add(getJCheckBoxIFFUseRegEx());
    		//jPanelIncFilesFilter.add(getXComboBoxPatternIFFRegEx());
    	}
    	return jPanelIncFilesFilter;
    }

    private JCheckBox getJCheckBoxUseDirsFilters() {
    	if (jCheckBoxUseDirsFilters == null) {
    		jCheckBoxUseDirsFilters = new JCheckBox();
    		jCheckBoxUseDirsFilters.setText("Enable");
    	}
    	return jCheckBoxUseDirsFilters;
    }

    private JScrollPane getJScrollPaneExcDirsFilter() {
    	if (jScrollPaneExcDirsFilter == null) {
    		jScrollPaneExcDirsFilter = new JScrollPane();
    		jScrollPaneExcDirsFilter.setBorder(BorderFactory.createTitledBorder(null, "Exclude Directories Filter", TitledBorder.LEADING,
    				TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jScrollPaneExcDirsFilter.setViewportView(getJPanelExcDirsFilter());
    	}
    	return jScrollPaneExcDirsFilter;
    }

    private JPanel getJPanelExcDirsFilter() {
    	if (jPanelExcDirsFilter == null) {
    		jPanelExcDirsFilter = new JPanel();
    		jPanelExcDirsFilter.setLayout(new BoxLayout(jPanelExcDirsFilter, BoxLayout.Y_AXIS));
    	}
    	return jPanelExcDirsFilter;
    }

    private JCheckBox getJCheckBoxUseFilesFilters() {
    	if (jCheckBoxUseFilesFilters == null) {
    		jCheckBoxUseFilesFilters = new JCheckBox();
    		jCheckBoxUseFilesFilters.setText("Enable");
    	}
    	return jCheckBoxUseFilesFilters;
    }

    private JCheckBox getJCheckBoxIgnoreEmptyFiles() {
    	if (jCheckBoxIgnoreEmptyFiles == null) {
    		jCheckBoxIgnoreEmptyFiles = new JCheckBox();
    		jCheckBoxIgnoreEmptyFiles.setSelected(true);
    		jCheckBoxIgnoreEmptyFiles.setText("empty files");
    	}
    	return jCheckBoxIgnoreEmptyFiles;
    }

    private JPanel getJPanelMisc() {
    	if (jPanelMisc == null) {
    		jPanelMisc = new JPanel();
    		jPanelMisc.setLayout(new GridLayout(1, 2));
    		jPanelMisc.add(getJPanelMiscLeft());
    		jPanelMisc.add(getJPanelIgnore());
    	}
    	return jPanelMisc;
    }

    private JScrollPane getJScrollPaneIncFilesFilter() {
    	if (jScrollPaneIncFilesFilter == null) {
    		jScrollPaneIncFilesFilter = new JScrollPane();
    		jScrollPaneIncFilesFilter.setBorder(BorderFactory.createTitledBorder(null, "Include Files Filter", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
    				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jScrollPaneIncFilesFilter.setViewportView(getJPanelIncFilesFilter());
    	}
    	return jScrollPaneIncFilesFilter;
    }

    private void initFixComponents()
    {
        ActionListener l = new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                updateDisplay();
            }
        };
        jComboBoxFilesFilters.addActionListener( l );
        jComboBoxFilesFilters.setModel(
                new DefaultComboBoxModel(
                    new Object[] {
                        txtIncludeFilesFilters,
                        txtExcludeFilesFilters
                        }
                    )
                );
        jComboBoxDirsFilters.addActionListener( l );
        jComboBoxDirsFilters.setModel(
                new DefaultComboBoxModel(
                    new Object[] {
                        txtExcludeDirsFilters,
                        txtIncludeDirsFilters
                        }
                    )
                );

        jCheckBoxUseDirsFilters.addActionListener( l );
        jCheckBoxUseFilesFilters.addActionListener( l );

        Properties  prop = new Properties();

        try {
            InputStream is   = getClass().getResourceAsStream( 
                    "JPanelConfig.properties" 
                    );
            prop.load( is );
            is.close();
        }
        catch( IOException e ) {
            slogger.error( "Can't load properties", e );
        }

        for(int i=0;;i++) {
            String descKey = String.format( "filetype.%d.description", i );
            String dataKey = String.format( "filetype.%d.data", i );
            String desc    = prop.getProperty( descKey );
            String data    = prop.getProperty( dataKey );
            
            if( desc != null && data != null ) {
                incFilesType.add(
                        new FileTypeCheckBox(desc,data)
                        );
                excFilesType.add(
                        new FileTypeCheckBox(desc,data)
                        );
            }
            else {
                slogger.info( "Can't find :" +  descKey );
                slogger.info( "or         :" +  dataKey );
                slogger.info( "desc :" + desc );
                slogger.info( "data :" + data );
                break;
            }
        }

        for(FileTypeCheckBox ft:incFilesType) {
            JCheckBox jcb = ft.getJCheckBox();

            jPanelIncFilesFilter.add( jcb );
            jcb.addActionListener( l );
        }
        jPanelIncFilesFilter.add(getJCheckBoxIFFUseRegEx());
        jPanelIncFilesFilter.add(getXComboBoxPatternIFFRegEx());
        jCheckBoxIFFUseRegEx.addActionListener( l );

        for(FileTypeCheckBox ft:excFilesType) {
            JCheckBox jcb = ft.getJCheckBox();

            jPanelExcFilesFilter.add( jcb );
            jcb.addActionListener( l );
        }
        jPanelExcFilesFilter.add(getJCheckBoxEFFUseRegEx());
        jPanelExcFilesFilter.add(getXComboBoxPatternEFFRegEx());
        jCheckBoxEFFUseRegEx.addActionListener( l );

        for(int i=0;;i++) {
            String descKey = String.format( "dirtype.%d.description", i );
            String dataKey = String.format( "dirtype.%d.data", i );
            String desc    = prop.getProperty( descKey );
            String data    = prop.getProperty( dataKey );
            
            if( desc != null && data != null ) {
                excDirsType.add(
                        new FileTypeCheckBox(desc,data)
                        );
            }
            else {
                slogger.info( "Can't find :" +  descKey );
                slogger.info( "or         :" +  dataKey );
                slogger.info( "desc :" + desc );
                slogger.info( "data :" + data );
                break;
            }
        }

        for(FileTypeCheckBox ft:excDirsType) {
            JCheckBox jcb = ft.getJCheckBox();
            
            jPanelExcDirsFilter.add( jcb );
            jcb.addActionListener( l );
        }

        jPanelExcDirsFilter.add(getJCheckBoxEFDUseRegEx());
        jPanelExcDirsFilter.add(getXComboBoxPatternEFDRegEx());
        jCheckBoxEFDUseRegEx.addActionListener( l );
        jCheckBoxIFDUseRegEx.addActionListener( l );
        
        Color errorColor = Color.RED;
        xComboBoxPatternIFFRegEx.setErrorBackGroundColor( errorColor );
        xComboBoxPatternEFFRegEx.setErrorBackGroundColor( errorColor );
        xComboBoxPatternEFDRegEx.setErrorBackGroundColor( errorColor );
        xComboBoxPatternIFDRegEx.setErrorBackGroundColor( errorColor );
        
        updateDisplay();
    }

    public void updateDisplayMode( ConfigMode mode )
    {
        this.mode = mode;

        if( mode == ConfigMode.BEGINNER ) {
            jCheckBoxUseFilesFilters.setSelected(false);
            jCheckBoxUseFilesFilters.setEnabled(false);

            jCheckBoxUseDirsFilters.setSelected(false);
            jCheckBoxUseDirsFilters.setEnabled(false);

            //jScrollPaneFilesFilter.setVisible( false );
            //jScrollPaneDirsFilter.setVisible( false );
        }
        else {
            jCheckBoxUseFilesFilters.setEnabled(true);
            jCheckBoxUseDirsFilters.setEnabled(true);

            //jScrollPaneFilesFilter.setVisible( true );
            //jScrollPaneDirsFilter.setVisible( true );
        }
        
        if( mode == ConfigMode.EXPERT ) {
            jCheckBoxIFFUseRegEx.setVisible( true );
            jCheckBoxEFFUseRegEx.setVisible( true );
            jCheckBoxIFDUseRegEx.setVisible( true );
            jCheckBoxEFDUseRegEx.setVisible( true );

            xComboBoxPatternIFFRegEx.setVisible( true );
            xComboBoxPatternEFFRegEx.setVisible( true );
            xComboBoxPatternIFDRegEx.setVisible( true );
            xComboBoxPatternEFDRegEx.setVisible( true );
            
            jComboBoxDirsFilters.setEnabled(true);
        }
        else {
            jCheckBoxIFFUseRegEx.setVisible( false );
            jCheckBoxIFFUseRegEx.setEnabled( false );
            
            jCheckBoxEFFUseRegEx.setVisible( false );
            jCheckBoxEFFUseRegEx.setEnabled( false );
            
            jCheckBoxIFDUseRegEx.setVisible( false );
            jCheckBoxIFDUseRegEx.setEnabled( false );

            jCheckBoxEFDUseRegEx.setVisible( false );
            jCheckBoxEFDUseRegEx.setEnabled( false );

            xComboBoxPatternIFFRegEx.setVisible( false );
            xComboBoxPatternEFFRegEx.setVisible( false );
            xComboBoxPatternIFDRegEx.setVisible( false );
            xComboBoxPatternEFDRegEx.setVisible( false );
        }
        updateDisplay();
    }
    
    private void updateDisplay()
    {
        // Global state
        boolean useFFglobal = jCheckBoxUseFilesFilters.isSelected();
        int     FFtype      = jComboBoxFilesFilters.getSelectedIndex();
        boolean useIFF      = useFFglobal && (FFtype == 0);
        boolean useEFF      = useFFglobal && (FFtype == 1);
        boolean useDFGlobal = jCheckBoxUseDirsFilters.isSelected();
        int     EFtype      = jComboBoxDirsFilters.getSelectedIndex();
        boolean useEDF      = useDFGlobal && (EFtype == 0);
        boolean useIDF      = useDFGlobal && (EFtype == 1);

        //Files filters
        jComboBoxFilesFilters.setEnabled( useFFglobal );
        for(FileTypeCheckBox ft:incFilesType) {
            ft.getJCheckBox().setEnabled( useIFF );
        }
        jCheckBoxIFFUseRegEx.setEnabled( useIFF );
        xComboBoxPatternIFFRegEx.setEnabled( useIFF && jCheckBoxIFFUseRegEx.isSelected() );

        for(FileTypeCheckBox ft:excFilesType) {
            ft.getJCheckBox().setEnabled( useEFF );
        }
        jCheckBoxEFFUseRegEx.setEnabled( useEFF );
        xComboBoxPatternEFFRegEx.setEnabled( useEFF && jCheckBoxEFFUseRegEx.isSelected() );

        //Directories Filters
        if( mode == ConfigMode.EXPERT ) {
            jComboBoxDirsFilters.setEnabled( useDFGlobal );
        }
        else {
            jComboBoxDirsFilters.setEnabled( false );
        }

        for(FileTypeCheckBox ft:excDirsType) {
            ft.getJCheckBox().setEnabled( useEDF );
        }

        jCheckBoxIFDUseRegEx.setEnabled( useIDF );
        xComboBoxPatternIFDRegEx.setEnabled( useIDF && jCheckBoxIFDUseRegEx.isSelected());

        jCheckBoxEFDUseRegEx.setEnabled( useEDF );
        xComboBoxPatternEFDRegEx.setEnabled( useEDF && jCheckBoxEFDUseRegEx.isSelected());
        
        //System.out.println("getFileFilterBuilders()=" + getFileFilterBuilders() );
    }

    /**
     * Returns true if Empty Files should be skipped
     * @return true if Empty Files should be skipped
     */
    public boolean IsIgnoreEmptyFiles()
    {
        return jCheckBoxIgnoreEmptyFiles.isSelected();
    }

    private final static void addExtIf(
            Collection<String>  c,
            FileTypeCheckBox    ft
            )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(String s:ft.getData().split( "," )) {
                if(s.length()>0) {
                    c.add( "." + s.toLowerCase() );
                }
            }
        }
    }

    private FileFilterBuilder createIncludeFilesFileFilterBuilder()
    {
        final HashSet<String>   extsList = new HashSet<String>();
        Pattern                 pattern  = null;

        if( jCheckBoxUseFilesFilters.isSelected() ) {
            for(FileTypeCheckBox ft:incFilesType) {
                    addExtIf(extsList, ft );
            }

            if( jCheckBoxIFFUseRegEx.isSelected() ) {
                try {
                    //pattern = Pattern.compile( jTextFieldIFFRegEx.getText() );
                    pattern = xComboBoxPatternIFFRegEx.getSelectedPattern();
                }
                catch( Exception ignore){}
            }
        }
        final Pattern sPattern = pattern;
        //final boolean ignoreExe = jCheckBoxIgnoreExeFiles.isSelected();
        
        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return extsList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }
    
    private FileFilterBuilder createExcludeFilesFileFilterBuilder()
    {
        final HashSet<String>   extsList = new HashSet<String>();
        Pattern                 pattern  = null;
        
        if( jCheckBoxUseFilesFilters.isSelected() ) {
            for(FileTypeCheckBox ft:excFilesType) {
                    addExtIf(extsList, ft );
            }

            if( jCheckBoxEFFUseRegEx.isSelected() ) {
                try {
                    //pattern = Pattern.compile( jTextFieldEFFRegEx.getText() );
                    pattern = xComboBoxPatternEFFRegEx.getSelectedPattern();
                }
                catch( Exception ignore){}
            }
        }
        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return extsList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }
    
    private final static void addNameIf(
            Collection<String>  c,
            FileTypeCheckBox    ft
            )
    {
        if( ft.getJCheckBox().isSelected() ) {
            for(String s:ft.getData().split( "," )) {
                if( s.length() > 0 ) {
                    c.add( s.toLowerCase() );
                }
            }
        }
    }
    
    private FileFilterBuilder createIncludeDirectoriesFileFilterBuilder()
    {
        final HashSet<String>   namesList = new HashSet<String>();
        Pattern                 pattern   = null;
        
        if( jCheckBoxUseFilesFilters.isSelected() ) {
            //TODO
            //TODO
//            for(FileTypeCheckBox ft:incDirsType) {
//                addNameIf(namesList,ft);
//            }

            if(jCheckBoxIFDUseRegEx.isSelected()) {
                try {
                    //pattern = Pattern.compile( jTextFieldIFDRegEx.getText() );
                    pattern = xComboBoxPatternIFDRegEx.getSelectedPattern();
                }
                catch( Exception ignore){
                    //TODO: display alert ???
                }
            }
        }

        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return namesList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }

    private FileFilterBuilder createExcludeDirectoriesFileFilterBuilder()
    {
        final HashSet<String>   namesList = new HashSet<String>();
        Pattern                 pattern   = null;
        
        if( jCheckBoxUseFilesFilters.isSelected() ) {
            for(FileTypeCheckBox ft:excDirsType) {
                addNameIf(namesList,ft);
            }

            if(jCheckBoxEFDUseRegEx.isSelected()) {
                try {
                    //pattern = Pattern.compile( jTextFieldEFDRegEx.getText() );
                    pattern = xComboBoxPatternEFDRegEx.getSelectedPattern();
                }
                catch( Exception ignore){}
            }
        }

        final Pattern sPattern = pattern;

        return new FileFilterBuilder()
        {
            @Override
            public Collection<String> getNamePart()
            {
                return namesList;
            }
            @Override
            public Pattern getRegExp()
            {
                return sPattern;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilder.class );
            }
        };
    }

    public FileFilterBuilders getFileFilterBuilders()
    {
        // Global state
        boolean useFFglobal = jCheckBoxUseFilesFilters.isSelected();
        int     FFtype      = jComboBoxFilesFilters.getSelectedIndex();
        final boolean useIFF      = useFFglobal && (FFtype == 0);
        final boolean useEFF      = useFFglobal && (FFtype == 1);
        boolean useDFGlobal = jCheckBoxUseDirsFilters.isSelected();
        int     EFtype      = jComboBoxDirsFilters.getSelectedIndex();
        final boolean useEDF      = useDFGlobal && (EFtype == 0);
        final boolean useIDF      = useDFGlobal && (EFtype == 1);

        slogger.info( "useFFglobal = " + useFFglobal);
        slogger.info( "FFtype = " + FFtype);
        slogger.info( "useIFF = " + useIFF);
        slogger.info( "useEFF = " + useEFF);
        slogger.info( "useDFGlobal = " + useDFGlobal);
        slogger.info( "EFtype = " + EFtype);
        slogger.info( "useEDF = " + useEDF);
        slogger.info( "useIDF = " + useIDF);

        // Special cases
        final boolean ignoreEmptyFiles = jCheckBoxIgnoreEmptyFiles.isSelected();
        final boolean ignoreHiddedFiles = jCheckBoxFFIgnoreHidden.isSelected();
        final boolean ignoreReadOnlyFiles = jCheckBoxIgnoreReadOnlyFiles.isSelected();
        final boolean ignoreHiddedDirs = jCheckBoxFDIgnoreHidden.isSelected();

        slogger.info( "ignoreEmptyFiles = " + ignoreEmptyFiles);
        slogger.info( "ignoreHiddedFiles = " + ignoreHiddedFiles);
        slogger.info( "ignoreReadOnlyFiles = " + ignoreReadOnlyFiles);
        slogger.info( "ignoreHiddedDirs = " + ignoreHiddedDirs);

        return new FileFilterBuilders()
        {
            @Override
            public FileFilterBuilder getIncludeDirs()
            {
                if( useIDF ) {
                    return createIncludeDirectoriesFileFilterBuilder();
                }
                else {
                    return null;
                }
            }
            @Override
            public FileFilterBuilder getExcludeDirs()
            {
                if( useEDF ) {
                    return createExcludeDirectoriesFileFilterBuilder();
                }
                else {
                    return null;
                }
            }
            @Override
            public FileFilterBuilder getIncludeFiles()
            {
                if( useIFF ) {
                    return createIncludeFilesFileFilterBuilder();
                }
                else {
                    return null;
                }
            }
            @Override
            public FileFilterBuilder getExcludeFiles()
            {
                if( useEFF ) {
                    return createExcludeFilesFileFilterBuilder();
                }
                else {
                    return null;
                }
            }
            @Override
            public boolean isIgnoreHiddenDirs()
            {
                return ignoreHiddedDirs;
            }
            @Override
            public boolean isIgnoreHiddenFiles()
            {
                return ignoreHiddedFiles;
            }
            @Override
            public boolean isIgnoreReadOnlyFiles()
            {
                return ignoreReadOnlyFiles;
            }
            @Override
            public boolean isIgnoreEmptyFiles()
            {
                return ignoreEmptyFiles;
            }
            @Override
            public String toString()
            {
                return ToStringBuilder.toString( this, FileFilterBuilders.class );
            }
        };
    }

    class FileTypeCheckBox implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private transient JCheckBox jCB;
        private String description;
        private String data;

        public FileTypeCheckBox( 
                String description, 
                String data
                )
        {
            this.description = description;
            this.data = data;
        }

        public JCheckBox getJCheckBox()
        {
            if( jCB == null ) {
                jCB = new JCheckBox(getDescription());
            }
            return jCB;
        }

        public final String getDescription()
        {
            return description;
        }
        public final String getData()
        {
            return data;
        }
    }
}

