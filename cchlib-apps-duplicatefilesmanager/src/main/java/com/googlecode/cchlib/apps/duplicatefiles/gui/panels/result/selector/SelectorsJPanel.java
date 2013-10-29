package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector;

import java.awt.CardLayout;
import java.util.EnumMap;
import javax.swing.JPanel;
import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public class SelectorsJPanel extends JPanel implements I18nAutoCoreUpdatable
{
    private static final long serialVersionUID = 1L;

    private EnumMap<Selectors,SelectorPanel> map = new EnumMap<Selectors,SelectorPanel>(Selectors.class);
    private SelectorComboBox selectorComboBox;
    private CardLayout layout;

    public SelectorsJPanel(
        final DFToolKit     dFToolKit,
        final DuplicateData duplicateData
        )
    {
        this.layout   = new CardLayout(0, 0);
        setLayout( layout );

        for( Selectors s : Selectors.values() ) {
            SelectorPanel item = s.newSelectorPanel(dFToolKit, duplicateData);

            this.map.put( s, item );

            add( item, getNameFor( item ) );
            }
    }

    public SelectorComboBox getSelectorComboBox()
    {
        if( selectorComboBox == null ) {
            selectorComboBox = new SelectorComboBox(this);
            }
        return selectorComboBox;
    }

    public void updateDisplay()
    {
        final SelectorPanel item = this.map.get( selectorComboBox.getEnumAt( selectorComboBox.getSelectedIndex() ) );

        layout.show( this, getNameFor( item ) );

        item.updateDisplay();
    }

    private String getNameFor( SelectorPanel item )
    {
        assert item != null;

        return item.getClass().getName();
    }

    @Override // I18nAutoCoreUpdatable
    public void performeI18n( AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, getClass() );

        for( SelectorPanel p : this.map.values() ) {
            p.performeI18n( autoI18n );
            }
    }

}
