package com.googlecode.cchlib.apps.duplicatefiles.tools;

import javax.swing.Icon;

public class MySerializableIcon extends AbstractSerializableIcon {
    private static final long serialVersionUID = 1L;
    private final String name;

    public MySerializableIcon( final String name )
    {
        this.name = name;
    }

    @Override
    protected Icon createNewIcon()
    {
        return MyResourcesLoader.getImageIcon( this.name );
    }

}
