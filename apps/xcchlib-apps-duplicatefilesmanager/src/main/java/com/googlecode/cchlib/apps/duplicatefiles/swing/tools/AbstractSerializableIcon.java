package com.googlecode.cchlib.apps.duplicatefiles.swing.tools;

import javax.swing.Icon;

public abstract class AbstractSerializableIcon implements SerializableIcon {
    private static final long serialVersionUID = 1L;
    private Icon icon;

    protected abstract Icon createNewIcon();

    @Override
    public final Icon getIcon()
    {
        if( this.icon == null ) {
            this.icon = createNewIcon();
        }
        return this.icon;
    }
}
