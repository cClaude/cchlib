package com.googlecode.cchlib.apps.duplicatefiles;

import javax.swing.Icon;

public class MyStaticResources
{
    private static Icon emptyIcon;
    private static Icon emptySelectedIcon;
    private static Icon emptyLeafIcon;
    private static Icon emptyLeafSelectedIcon;

    private MyStaticResources() {}

    public static Icon getEmptyIcon()
    {
        if( emptyIcon == null ) {
            emptyIcon = MyResourcesLoader.getImageIcon( "emptyIcon.png" );
            }
        return emptyIcon;
    }

    public static Icon getEmptySelectedIcon()
    {
        if( emptySelectedIcon == null ) {
            emptySelectedIcon = MyResourcesLoader.getImageIcon( "emptySelectedIcon.png" );
            }
        return emptySelectedIcon;
    }

    public static Icon getEmptyLeafIcon()
    {
        if( emptyLeafIcon == null ) {
            emptyLeafIcon = MyResourcesLoader.getImageIcon( "emptyLeafIcon.png" );
            }
        return emptyLeafIcon;
    }

    public static Icon getEmptyLeafSelectedIcon()
    {
        if( emptyLeafSelectedIcon == null ) {
            emptyLeafSelectedIcon = MyResourcesLoader.getImageIcon( "emptyLeafSelectedIcon.png" );
            }
        return emptyLeafSelectedIcon;
    }
}
