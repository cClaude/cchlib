package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.util.function.Consumer;
import com.googlecode.cchlib.util.EnumHelper;

enum CompareResourcesBundleFrameAction
{
    ACTIONCMD_OPEN( frame -> frame.jMenuItem_Open() ),
    ACTIONCMD_SAVE_LEFT( f -> f.saveFile( 0 ) ),
    ACTIONCMD_SAVE_RIGHT_PREFIX( nop -> { /* NOP */ } ),
    ACTIONCMD_SAVE_ALL( f -> f.saveAll() ),
    ACTIONCMD_QUIT( f -> f.dispose() ),
    ACTIONCMD_PREFS( f -> f.openPreferences() ),
    ;

    private Consumer<CompareResourcesBundleFrame> action;

    private CompareResourcesBundleFrameAction(
        final Consumer<CompareResourcesBundleFrame> action
        )
    {
        this.action = action;
    }

    public void doAction( final CompareResourcesBundleFrame frame )
    {
        this.action.accept( frame );
    }

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
