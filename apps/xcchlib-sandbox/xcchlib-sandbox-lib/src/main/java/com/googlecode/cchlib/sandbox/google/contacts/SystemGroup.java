package com.googlecode.cchlib.sandbox.google.contacts;

enum SystemGroup {
    MY_CONTACTS("Contacts", "My Contacts"),
    FRIENDS("Friends", "Friends"),
    FAMILY("Family", "Family"),
    COWORKERS("Coworkers", "Coworkers");

    private final String systemGroupId;
    private final String prettyName;

    SystemGroup( final String systemGroupId, final String prettyName )
    {
        this.systemGroupId = systemGroupId;
        this.prettyName = prettyName;
    }

    static SystemGroup fromSystemGroupId( final String id )
    {
        for( final SystemGroup group : SystemGroup.values() ) {
            if( id.equals( group.systemGroupId ) ) {
                return group;
            }
        }
        throw new IllegalArgumentException( "Unrecognized system group id: " + id );
    }

    @Override
    public String toString()
    {
        return this.prettyName;
    }
}