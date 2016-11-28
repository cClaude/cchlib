package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.FileTypeCheckBox;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config.FilterEntry;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.swing.combobox.XComboBoxPattern;

/**
 * Handle filters configuration
 */
@SuppressWarnings({
    "squid:S00117" // Naming conventions vs generated code
    })
public class JPanelConfigFilter
    extends JPanel
        implements Iterable<FileTypeCheckBox>
{
    private static final long serialVersionUID = 1L;
    private static final  Color ERRORCOLOR = Color.RED;

    /** @serial */
    private final Map<Integer,FileTypeCheckBox> fileTypeCheckBoxMap
        = new HashMap<>();

    private final JPanel jPanelCheckBox;

    @I18nIgnore private final JCheckBox jCheckBoxRegExp;
    @I18nIgnore private final XComboBoxPattern xComboBoxPatternRegExp;

    /**
     * Create the panel.
     */
    public JPanelConfigFilter(
        final String titleBorderText,
        final String regExpText
        )
    {
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        this.jPanelCheckBox = new JPanel();
        final GridBagConstraints gbc_jPanelCheckBox = new GridBagConstraints();
        gbc_jPanelCheckBox.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelCheckBox.fill = GridBagConstraints.BOTH;
        gbc_jPanelCheckBox.gridx = 1;
        gbc_jPanelCheckBox.gridy = 0;
        add(this.jPanelCheckBox, gbc_jPanelCheckBox);
        this.jPanelCheckBox.setLayout(new BoxLayout(this.jPanelCheckBox, BoxLayout.Y_AXIS));

        this.jCheckBoxRegExp = new JCheckBox();
        this.jCheckBoxRegExp.setHorizontalAlignment(SwingConstants.TRAILING);
        this.jCheckBoxRegExp.setHorizontalTextPosition( SwingConstants.LEFT );
        this.jCheckBoxRegExp.setText( regExpText );
        this.jCheckBoxRegExp.addActionListener( this::actionPerformedCheckBoxRegExp  );
        this.jCheckBoxRegExp.setSelected( false );
        final GridBagConstraints gbc_jCheckBoxRegExp = new GridBagConstraints();
        gbc_jCheckBoxRegExp.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxRegExp.insets = new Insets(0, 0, 0, 5);
        gbc_jCheckBoxRegExp.gridx = 0;
        gbc_jCheckBoxRegExp.gridy = 1;
        add(this.jCheckBoxRegExp, gbc_jCheckBoxRegExp);

        this.xComboBoxPatternRegExp = new XComboBoxPattern();
        this.xComboBoxPatternRegExp.setErrorBackGroundColor( ERRORCOLOR );
        this.xComboBoxPatternRegExp.setEnabled( false );
        final GridBagConstraints gbc_xComboBoxPatternRegExp = new GridBagConstraints();
        gbc_xComboBoxPatternRegExp.fill = GridBagConstraints.HORIZONTAL;
        gbc_xComboBoxPatternRegExp.gridx = 1;
        gbc_xComboBoxPatternRegExp.gridy = 1;
        add( this.xComboBoxPatternRegExp, gbc_xComboBoxPatternRegExp );

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

    public JPanelConfigFilter(
        final String                  titleBorderText,
        final String                  regExpText,
        final Collection<FilterEntry> filterEntries
        )
    {
        this( titleBorderText, regExpText );

        int index = 0;

        for( final FilterEntry entry : filterEntries ) {
            final String desc = entry.getDescription();
            final String data = entry.getData();

            final FileTypeCheckBox box = new FileTypeCheckBox( desc, data );

            this.fileTypeCheckBoxMap.put( index++, box );
            this.jPanelCheckBox.add( box.getJCheckBox() );
            }
    }

    @SuppressWarnings("squid:S1172") // Unused method parameters should be removed (parameter required for signature)
    private void actionPerformedCheckBoxRegExp( final ActionEvent event )
    {
        this.xComboBoxPatternRegExp.setEnabled(
            this.jCheckBoxRegExp.isSelected()
        );
    }

    protected XComboBoxPattern getXComboBoxPatternRegExp()
    {
        return this.xComboBoxPatternRegExp;
    }

    public JCheckBox getJCheckBoxRegExp()
    {
        return this.jCheckBoxRegExp;
    }

    /**
     * @return the JCheckBox for giving key
     */
    public FileTypeCheckBox getFileTypeCheckBox( final int key )
    {
        return this.fileTypeCheckBoxMap.get( Integer.valueOf( key ) );
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
        return this.fileTypeCheckBoxMap.values().iterator();
    }
}
