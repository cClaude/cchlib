package com.googlecode.cchlib.apps.editresourcesbundle;

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

    private boolean isPrefixOf( final String value )
    {
        return value.startsWith( name() );
    }

    public Integer getIndex( final String value )
    {
        if( isPrefixOf( value ) ) {
            return Integer.valueOf( value.substring( name().length() ) );
            }
        else {
            return null;
            }
    }

}
