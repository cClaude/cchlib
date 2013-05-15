package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.gui.panels.FileTypeCheckBox;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.swing.XComboBoxPattern;

/**
 *
 *
 */
//
public
class JPanelConfigFilter
    extends JPanel//ConfigFilterWB
        implements Iterable<FileTypeCheckBox>
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( JPanelConfigFilter.class );
    private static final  Color ERRORCOLOR = Color.RED;
    /** @serial */
    private Map<Integer,FileTypeCheckBox> fileTypeCheckBoxMap
        = new HashMap<Integer,FileTypeCheckBox>();

    private JPanel jPanelCheckBox;
    @I18nIgnore private JCheckBox jCheckBoxRegExp;
    @I18nIgnore private XComboBoxPattern xComboBoxPatternRegExp;

    /**
     * Create the panel.
     */
    public JPanelConfigFilter(
        final String titleBorderText,
        final String regExpText
        )
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jPanelCheckBox = new JPanel();
        GridBagConstraints gbc_jPanelCheckBox = new GridBagConstraints();
        gbc_jPanelCheckBox.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelCheckBox.fill = GridBagConstraints.BOTH;
        gbc_jPanelCheckBox.gridx = 1;
        gbc_jPanelCheckBox.gridy = 0;
        add(jPanelCheckBox, gbc_jPanelCheckBox);
        jPanelCheckBox.setLayout(new BoxLayout(jPanelCheckBox, BoxLayout.Y_AXIS));

//        JCheckBox chckbxCheckbox = new JCheckBox("checkbox1");
//        jPanelCheckBox.add(chckbxCheckbox);
//
//        JCheckBox chckbxCheckbox_1 = new JCheckBox("checkbox2");
//        jPanelCheckBox.add(chckbxCheckbox_1);

        jCheckBoxRegExp = new JCheckBox();
        jCheckBoxRegExp.setHorizontalAlignment(SwingConstants.TRAILING);
        jCheckBoxRegExp.setHorizontalTextPosition( SwingConstants.LEFT );
        jCheckBoxRegExp.setText( regExpText );
        jCheckBoxRegExp.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    xComboBoxPatternRegExp.setEnabled(
                            jCheckBoxRegExp.isSelected()
                            );
                }
            } );
        jCheckBoxRegExp.setSelected( false );
        GridBagConstraints gbc_jCheckBoxRegExp = new GridBagConstraints();
        gbc_jCheckBoxRegExp.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxRegExp.insets = new Insets(0, 0, 0, 5);
        gbc_jCheckBoxRegExp.gridx = 0;
        gbc_jCheckBoxRegExp.gridy = 1;
        add(jCheckBoxRegExp, gbc_jCheckBoxRegExp);

        xComboBoxPatternRegExp = new XComboBoxPattern();
        xComboBoxPatternRegExp.setErrorBackGroundColor( ERRORCOLOR );
        xComboBoxPatternRegExp.setEnabled( false );
        GridBagConstraints gbc_xComboBoxPatternRegExp = new GridBagConstraints();
        gbc_xComboBoxPatternRegExp.fill = GridBagConstraints.HORIZONTAL;
        gbc_xComboBoxPatternRegExp.gridx = 1;
        gbc_xComboBoxPatternRegExp.gridy = 1;
        add(xComboBoxPatternRegExp, gbc_xComboBoxPatternRegExp);

        setBorder(
            BorderFactory.createTitledBorder(
                    null,
                    titleBorderText,
                    TitledBorder.LEADING,
                    TitledBorder.DEFAULT_POSITION,
                    new Font( "Dialog", Font.BOLD, 12),
                    new Color(51, 51, 51)
                    )
                );
    }

    /**
     *
     */
    public JPanelConfigFilter(
        final String            titleBorderText,
        final String            regExpText,
        final Properties        properties,
        final String            prefix,
        final ActionListener    listener
        )
    {
        this( titleBorderText, regExpText );

        for(int i=0;;i++) {
            String descKey = String.format( "%s.%d.description", prefix, i );
            String dataKey = String.format( "%s.%d.data", prefix, i );
            String desc    = properties.getProperty( descKey );
            String data    = properties.getProperty( dataKey );

            if( desc != null && data != null ) {
                FileTypeCheckBox box = new FileTypeCheckBox( desc, data );

                fileTypeCheckBoxMap.put( i, box );
                jPanelCheckBox.add( box.getJCheckBox() );
                }
            else {
                logger.info( "Can't find :" +  descKey );
                logger.info( "or         :" +  dataKey );
                logger.info( "desc :" + desc );
                logger.info( "data :" + data );
                break;
                }
            }
    }

    protected XComboBoxPattern getXComboBoxPatternRegExp()
    {
        return xComboBoxPatternRegExp;
    }

    public JCheckBox getJCheckBoxRegExp()
    {
        return jCheckBoxRegExp;
    }

    /**
     * @return the JCheckBox for giving key
     */
    public FileTypeCheckBox getFileTypeCheckBox( final int key )
    {
        return fileTypeCheckBoxMap.get( key );
    }

    public void addPatternRegExp( final String regExp )
    {
        getXComboBoxPatternRegExp().addItem( regExp );
    }

    public Pattern getSelectedPattern()
    {
        return getXComboBoxPatternRegExp().getSelectedPattern();
    }

    @Override
    public Iterator<FileTypeCheckBox> iterator()
    {
        return fileTypeCheckBoxMap.values().iterator();
    }


}
