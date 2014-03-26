package com.googlecode.cchlib.tools.sortfiles;

import java.io.File;

public interface RecordFileParser {

    ParsedFilename parse( File file ) throws FileParserException;

}
