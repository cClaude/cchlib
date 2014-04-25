package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import com.googlecode.cchlib.util.EnumHelper;

enum CompareResourcesBundleFrameAction {
    ACTIONCMD_OPEN,
    ACTIONCMD_SAVE_LEFT,
    ACTIONCMD_SAVE_RIGHT_PREFIX,
    ACTIONCMD_SAVE_ALL,
    ACTIONCMD_QUIT,
    ACTIONCMD_PREFS;

    public String getActionCommand( final int index )
    {
        if( this == ACTIONCMD_SAVE_RIGHT_PREFIX ) {
            return name() + index;
            }

        throw new IllegalStateException();
    }

    public Integer getIndex( final String value )
    {
        return EnumHelper.getSuffixInteger( this, value );
    }

}
