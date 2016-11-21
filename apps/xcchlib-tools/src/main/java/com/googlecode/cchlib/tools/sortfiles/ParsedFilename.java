package com.googlecode.cchlib.tools.sortfiles;

public interface ParsedFilename {

    ParsedFilename setRawPhoneNumber( String rawPhoneNumber );
    String getRawPhoneNumber();

    ParsedFilename setRawDate( String rawDate );
    String getRawDate();

}
