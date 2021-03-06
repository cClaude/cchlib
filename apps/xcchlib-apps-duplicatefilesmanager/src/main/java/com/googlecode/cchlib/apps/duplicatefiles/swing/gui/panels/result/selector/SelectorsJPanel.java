package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import java.awt.CardLayout;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JPanel;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

public class SelectorsJPanel extends JPanel implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;

    private final Map<Selectors,SelectorPanel> map = new EnumMap<>(Selectors.class);
    private SelectorComboBox selectorComboBox;
    private final CardLayout layout;

    public SelectorsJPanel(
        final DuplicateData duplicateData
        )
    {
        this.layout   = new CardLayout(0, 0);
        setLayout( this.layout );

        for( final Selectors s : Selectors.values() ) {
            final SelectorPanel item = s.newSelectorPanel( duplicateData );

            this.map.put( s, item );

            add( item, getNameFor( item ) );
            }
    }

    public SelectorComboBox getSelectorComboBox()
    {
        if( this.selectorComboBox == null ) {
            this.selectorComboBox = new SelectorComboBox(this);
            }
        return this.selectorComboBox;
    }

    public void updateDisplay()
    {
        final SelectorPanel item = this.map.get( this.selectorComboBox.getEnumAt( this.selectorComboBox.getSelectedIndex() ) );

        this.layout.show( this, getNameFor( item ) );

        item.updateDisplay();
    }

    private String getNameFor( final SelectorPanel item )
    {
        assert item != null;

        return item.getClass().getName();
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, getClass() );

        for( final SelectorPanel p : this.map.values() ) {
            p.performeI18n( autoI18n );
            }
    }

    public void disableAllWidgets()
    {
        // TODO : disable all widgets
    }

    public void enableAllWidgets()
    {
        // TODO : enable all widgets
    }

}
