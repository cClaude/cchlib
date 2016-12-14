package com.googlecode.cchlib.xutil.google.googlecontact.assertions;

import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleContactAssertions
{
    private GoogleContactAssertions()
    {
        // All static
    }

    public static GoogleContactAssert assertThat( final GoogleContact actual )
    {
        return new GoogleContactAssert( actual );
    }

    public static BasicEntryAssert assertThat( final BasicEntry actual )
    {
        return new BasicEntryAssert( actual );
    }
}
