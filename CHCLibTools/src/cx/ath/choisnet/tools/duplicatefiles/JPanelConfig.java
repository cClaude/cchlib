package cx.ath.choisnet.tools.duplicatefiles;

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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.I18n;
import cx.ath.choisnet.i18n.I18nSwingHelper;

/**
 * VS4E Only !
 *
 */
//VS 4E -- DO NOT REMOVE THIS LINE!
public class JPanelConfig extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( JPanelConfig.class );

    private JPanel jPanelMisc;
    private JCheckBox jCheckBoxIgnoreEmptyFiles;
    private JCheckBox jCheckBoxUseFilesFilters;
    private JCheckBox jCheckBoxUseDirFilters;
    
    private ArrayList<FileTypeCheckBox> filesType = new ArrayList<FileTypeCheckBox>();
    private ArrayList<FileTypeCheckBox> dirsType  = new ArrayList<FileTypeCheckBox>();


    @I18n(methodSuffixName="I18nTileFilesFilter")
    private JScrollPane jScrollPaneFilesFilter;
    private JPanel jPanelFilesFilter;
    private JCheckBox jCheckBoxFFIgnoreHidden;
    private JCheckBox jCheckBoxFFUseRegEx;
    private JTextField jTextFieldFFRegEx;

    @I18n(methodSuffixName="I18nTileDirsFilter")
    private JScrollPane jScrollPaneDirsFilter;
    private JPanel jPanelDirsFilter;
    private JCheckBox jCheckBoxFDIgnoreHidden;
    private JCheckBox jCheckBoxFDUseRegEx;
    private JTextField jTextFieldFDRegEx;

    public JPanelConfig()
    {
        initComponents();
        initFixComponents();
    }
     
    public void setI18nTileFilesFilter(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jScrollPaneFilesFilter,localText);
    }

    public String getI18nTileFilesFilter()
    {
        return I18nSwingHelper.getTitledBorderTitle(jScrollPaneFilesFilter);
    }

    public void setI18nTileDirsFilter(String localText)
    {
        I18nSwingHelper.setTitledBorderTitle(jScrollPaneDirsFilter,localText);
    }
    
    public String getI18nTileDirsFilter()
    {
        return I18nSwingHelper.getTitledBorderTitle(jScrollPaneDirsFilter);
    }
    
    private void initComponents() {
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	add(getJPanelMisc());
        add(getJScrollPaneFilesFilter());
        add(getJScrollPaneDirsFilter());
    	setSize(438, 397);
    }

    private JCheckBox getJCheckBoxFFIgnoreHidden() {
    	if (jCheckBoxFFIgnoreHidden == null) {
    	    jCheckBoxFFIgnoreHidden = new JCheckBox();
    		jCheckBoxFFIgnoreHidden.setText("Ignore hidden files");
    	}
    	return jCheckBoxFFIgnoreHidden;
    }
 
    private JCheckBox getJCheckBoxFDIgnoreHidden() {
        if (jCheckBoxFDIgnoreHidden == null) {
            jCheckBoxFDIgnoreHidden = new JCheckBox();
            jCheckBoxFDIgnoreHidden.setText("Ignore hidden directories");
        }
        return jCheckBoxFDIgnoreHidden;
    }
 
    private JTextField getJTextFieldFDRegEx() {
        if (jTextFieldFDRegEx == null) {
            jTextFieldFDRegEx = new JTextField();
        }
        return jTextFieldFDRegEx;
    }

    private JCheckBox getJCheckBoxFDUseRegEx() {
        if (jCheckBoxFDUseRegEx == null) {
            jCheckBoxFDUseRegEx = new JCheckBox();
            jCheckBoxFDUseRegEx.setText("Custom Directories Names Filter (use RegEx)");
        }
        return jCheckBoxFDUseRegEx;
    }

    private JTextField getJTextFieldFFRegEx() {
        if (jTextFieldFFRegEx == null) {
            jTextFieldFFRegEx = new JTextField();
        }
        return jTextFieldFFRegEx;
    }

    private JCheckBox getJCheckBoxFFUseRegEx() {
        if (jCheckBoxFFUseRegEx == null) {
            jCheckBoxFFUseRegEx = new JCheckBox();
            jCheckBoxFFUseRegEx.setText("Custom Files Names Filter (use RegEx on name)");
        }
        return jCheckBoxFFUseRegEx;
    }

    private JPanel getJPanelFilesFilter() {
    	if (jPanelFilesFilter == null) {
    		jPanelFilesFilter = new JPanel();
    		jPanelFilesFilter.setLayout(new BoxLayout(jPanelFilesFilter, BoxLayout.Y_AXIS));
    	}
    	return jPanelFilesFilter;
    }

    private JCheckBox getJCheckBoxUseDirFilters() {
        if (jCheckBoxUseDirFilters == null) {
            jCheckBoxUseDirFilters = new JCheckBox();
            jCheckBoxUseDirFilters.setText("Use Excluding Directories Filters");
        }
        return jCheckBoxUseDirFilters;
    }

    private JScrollPane getJScrollPaneDirsFilter() {
    	if (jScrollPaneDirsFilter == null) {
    		jScrollPaneDirsFilter = new JScrollPane();
    		jScrollPaneDirsFilter.setBorder(BorderFactory.createTitledBorder(null, "Exclude Directories Filter", TitledBorder.LEADING,
    				TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jScrollPaneDirsFilter.setViewportView(getJPanelDirsFilter());
    	}
    	return jScrollPaneDirsFilter;
    }

    private JPanel getJPanelDirsFilter() {
    	if (jPanelDirsFilter == null) {
    		jPanelDirsFilter = new JPanel();
    		jPanelDirsFilter.setLayout(new BoxLayout(jPanelDirsFilter, BoxLayout.Y_AXIS));
    	}
    	return jPanelDirsFilter;
    }

    private JCheckBox getJCheckBoxUseFilesFilters() {
        if (jCheckBoxUseFilesFilters == null) {
            jCheckBoxUseFilesFilters = new JCheckBox();
            jCheckBoxUseFilesFilters.setText("Use Including Files Filters");
        }
        return jCheckBoxUseFilesFilters;
    }

    private JCheckBox getJCheckBoxIgnoreEmptyFiles() {
        if (jCheckBoxIgnoreEmptyFiles == null) {
            jCheckBoxIgnoreEmptyFiles = new JCheckBox();
            jCheckBoxIgnoreEmptyFiles.setText("Ignore empty files");
            jCheckBoxIgnoreEmptyFiles.setSelected( true );
        }
        return jCheckBoxIgnoreEmptyFiles;
    }

    private JPanel getJPanelMisc() {
        if (jPanelMisc == null) {
            jPanelMisc = new JPanel();
            jPanelMisc.setLayout(new GridLayout(2, 3));
            //line1 
            jPanelMisc.add(getJCheckBoxUseFilesFilters());
            jPanelMisc.add(getJCheckBoxFFIgnoreHidden());
            jPanelMisc.add(getJCheckBoxIgnoreEmptyFiles());
            //line2
            jPanelMisc.add(getJCheckBoxUseDirFilters());
            jPanelMisc.add(getJCheckBoxFDIgnoreHidden());
        }
        return jPanelMisc;
    }

    private JScrollPane getJScrollPaneFilesFilter() {
    	if (jScrollPaneFilesFilter == null) {
    		jScrollPaneFilesFilter = new JScrollPane();
    		jScrollPaneFilesFilter.setBorder(BorderFactory.createTitledBorder(null, "Include Files Filter", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
    				new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
    		jScrollPaneFilesFilter.setViewportView(getJPanelFilesFilter());
    	}
    	return jScrollPaneFilesFilter;
    }

    private void initFixComponents()
    
    {
        ActionListener l = new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                //slogger.info("ActionEvent:" +e);
                updateDisplay();
            }
        };

        jCheckBoxUseDirFilters.addActionListener( l );
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
                filesType.add(
                        new FileTypeCheckBoxData(desc,data)
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

        for(FileTypeCheckBox ft:filesType) {
            JCheckBox jcb = FileTypeCheckBox.class.cast(ft).getJCheckBox();

            jPanelFilesFilter.add( jcb );
            jcb.addActionListener( l );
        }
        jPanelFilesFilter.add(getJCheckBoxFFUseRegEx());
        jPanelFilesFilter.add(getJTextFieldFFRegEx());
        jCheckBoxFFUseRegEx.addActionListener( l );

        for(int i=0;;i++) {
            String descKey = String.format( "dirtype.%d.description", i );
            String dataKey = String.format( "dirtype.%d.data", i );
            String desc    = prop.getProperty( descKey );
            String data    = prop.getProperty( dataKey );
            
            if( desc != null && data != null ) {
                dirsType.add(
                        new FileTypeCheckBoxData(desc,data)
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

        for(FileTypeCheckBox ft:dirsType) {
            JCheckBox jcb = FileTypeCheckBox.class.cast(ft).getJCheckBox();
            
            jPanelDirsFilter.add( jcb );
            jcb.addActionListener( l );
        }

        jPanelDirsFilter.add(getJCheckBoxFDUseRegEx());
        jPanelDirsFilter.add(getJTextFieldFDRegEx());
        jCheckBoxFDUseRegEx.addActionListener( l );

        Color errorColor = Color.RED;
        jTextFieldFFRegEx.setDocument( 
                new PatternDocument(
                        jTextFieldFFRegEx,
                        errorColor
                        ) 
                );
        jTextFieldFDRegEx.setDocument(
                new PatternDocument(
                        jTextFieldFDRegEx,
                        errorColor
                        ) 
                );

        updateDisplay();
    }

    private void updateDisplay()
    {
        //jCheckBoxIgnoreEmptyFiles
        boolean useFF = jCheckBoxUseFilesFilters.isSelected();

        for(FileTypeCheckBox ft:filesType) {
            ft.getJCheckBox().setEnabled( useFF );
        }
        //jCheckBoxFFIgnoreHidden.setEnabled( useFF );
        jCheckBoxFFUseRegEx.setEnabled( useFF );
        jTextFieldFFRegEx.setEnabled( useFF && jCheckBoxFFUseRegEx.isSelected() );

        boolean useDF = jCheckBoxUseDirFilters.isSelected();
        
        for(FileTypeCheckBox ft:dirsType) {
            ft.getJCheckBox().setEnabled( useDF );
        }
        //jCheckBoxFDIgnoreHidden.setEnabled( useDF );
        jCheckBoxFDUseRegEx.setEnabled( useDF );
        jTextFieldFDRegEx.setEnabled( useDF && jCheckBoxFDUseRegEx.isSelected());
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
            Collection<String>      c,
            FileTypeCheckBoxData    ft
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

    public FileFilterBuilder getFilesFileFilterBuilder()
    {
        final HashSet<String>   extsList = new HashSet<String>();
        Pattern                 pattern  = null;

        if( jCheckBoxUseFilesFilters.isSelected() ) {
            for(FileTypeCheckBox ft:filesType) {
                if( ft instanceof FileTypeCheckBoxData) {
                    addExtIf(extsList,FileTypeCheckBoxData.class.cast( ft ));
                }
            }

            if( jCheckBoxFFUseRegEx.isSelected() ) {
                try {
                    pattern = Pattern.compile( jTextFieldFFRegEx.getText() );
                }
                catch( Exception ignore){}
            }
        }
        final Pattern sPattern = pattern;
        final boolean ignoreHidded = jCheckBoxFFIgnoreHidden.isSelected();

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
            public boolean getIgnoreHidden()
            {
                return ignoreHidded;
            }
        };
    }

    private final static void addNameIf(
            Collection<String>      c,
            FileTypeCheckBoxData    ft
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

    public FileFilterBuilder getDirectoriesFileFilterBuilder()
    {
        final HashSet<String>   namesList = new HashSet<String>();
        Pattern                 pattern   = null;
        
        if( jCheckBoxUseFilesFilters.isSelected() ) {
            for(FileTypeCheckBox ft:dirsType) {
                if( ft instanceof FileTypeCheckBoxData) {
                    addNameIf(namesList,FileTypeCheckBoxData.class.cast( ft ));
                }
            }

            if(jCheckBoxFDUseRegEx.isSelected()) {
                try {
                    pattern = Pattern.compile( jTextFieldFDRegEx.getText() );
                }
                catch( Exception ignore){}
            }
        }

        final Pattern sPattern = pattern;
        final boolean ignoreHidded = jCheckBoxFDIgnoreHidden.isSelected();

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
            public boolean getIgnoreHidden()
            {
                return ignoreHidded;
            }
        };
    }

    class FileTypeCheckBox implements Serializable
    {
        private static final long serialVersionUID = 1L;
        private transient JCheckBox jCB;
        private String description;

        public FileTypeCheckBox( 
                String description
                )
        {
            this.description = description;
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
    }

    class FileTypeCheckBoxData extends FileTypeCheckBox
    {
        private static final long serialVersionUID = 1L;
        private String data;

        public FileTypeCheckBoxData( 
                String description, 
                String data
                )
        {
            super(description);
            this.data = data;
        }
        public final String getData()
        {
            return data;
        }
    }

}

