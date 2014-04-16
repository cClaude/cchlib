package com.googlecode.cchlib.tools.phone.recordsorter;

public class NumberNormalizer
{
    public String normalizePhoneNumber( final String phoneNumber )
    {
        String number;

        if( phoneNumber.startsWith( "+33" ) ) {
            number = '0' + phoneNumber.substring( 3 );
        } else if( phoneNumber.startsWith( "33" ) ) {
            number = '0' + phoneNumber.substring( 2 );
        } else if( phoneNumber.startsWith( "+41" ) ) {
            number = "00" + phoneNumber.substring( 1 );
        } else if( phoneNumber.startsWith( "41" ) ) {
            number = "00" + phoneNumber;
        } else {
            number = phoneNumber;
        }

        return number;
    }
}
