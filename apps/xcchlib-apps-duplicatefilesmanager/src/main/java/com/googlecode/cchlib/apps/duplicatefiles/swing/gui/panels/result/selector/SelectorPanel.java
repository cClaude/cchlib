package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import javax.swing.JPanel;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

public abstract class SelectorPanel extends JPanel implements I18nAutoUpdatable // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses
{
    private static final long serialVersionUID = 1L;

    public abstract void updateDisplay();

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, getClass() );
    }
}
