package com.googlecode.cchlib.xutil.google.googlecontact.assertions;

import static org.fest.assertions.api.Assertions.assertThat;
import org.fest.assertions.api.AbstractAssert;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;

public class BasicEntryAssert extends AbstractAssert<BasicEntryAssert, BasicEntry>
{
    public BasicEntryAssert( final BasicEntry actual )
    {
      super(actual, BasicEntryAssert.class);
    }

    public BasicEntryAssert hasType( final String expectedType )
    {
      isNotNull();
      assertThat( this.actual.getType() ).isEqualTo( expectedType );
      return this;
    }

    public BasicEntryAssert hasValue( final String expectedValue )
    {
      isNotNull();
      assertThat( this.actual.getValue() ).isEqualTo( expectedValue );
      return this;
    }

//    public BasicEntryAssert hasType( final String expected )
//    {
//        isNotNull();
//        if (expected == null) {
//            throw new NullPointerException(formattedErrorMessage("The size to compare to should not be null"));
//        }
//        final String actualType = this.actual.getType();
//        Fail.failIfNotEqual(customErrorMessage(), rawDescription(), actualType, expected);
//        return this;
//    }

//    public BasicEntryAssert hasValue( final String expected )
//    {
//        isNotNull();
//        if (expected == null) {
//            throw new NullPointerException(formattedErrorMessage("The size to compare to should not be null"));
//        }
//        final String actualValue = this.actual.getValue();
//        Fail.failIfNotEqual(customErrorMessage(), rawDescription(), actualValue, expected);
//        return this;
//    }
}
