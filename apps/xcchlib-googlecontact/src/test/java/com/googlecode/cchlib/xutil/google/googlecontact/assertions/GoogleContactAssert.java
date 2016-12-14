package com.googlecode.cchlib.xutil.google.googlecontact.assertions;

import static org.fest.assertions.api.Assertions.assertThat;
import org.fest.assertions.api.AbstractAssert;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;

public class GoogleContactAssert extends AbstractAssert<GoogleContactAssert, GoogleContact>
{
    public GoogleContactAssert( final GoogleContact actual )
    {
      super(actual, GoogleContactAssert.class);
    }

    public GoogleContactAssert hasBirthday( final String expectedBirthday )
    {
      isNotNull();
      assertThat( this.actual.getBirthday() ).isEqualTo( expectedBirthday );
      return this;
    }

    public GoogleContactAssert hasName( final String expectedName )
    {
        isNotNull();
        assertThat( this.actual.getName() ).isEqualTo( expectedName );
        return this;
    }
}
