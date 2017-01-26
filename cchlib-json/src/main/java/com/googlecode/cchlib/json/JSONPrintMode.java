package com.googlecode.cchlib.json;

import java.util.function.Function;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/** NEEDDOC */
public enum JSONPrintMode 
{
    /** NEEDDOC */
    PRETTY( JSONHelper::getPrettyPrintObjectWriter ),
    /**
     * Same than {@link #PRETTY} but also format array using
     * default indentation
     *          * @see DefaultIndenter#SYSTEM_LINEFEED_INSTANCE
     */
    PRETTY_ARRAYS( JSONHelper::getPrettyPrintArrayObjectWriter ),
    ;

    private final Function<ObjectMapper, ObjectWriter> objectWriterGetter;

    private JSONPrintMode( final Function<ObjectMapper, ObjectWriter> objectWriterGetter )
    {
        this.objectWriterGetter = objectWriterGetter;
    }

    ObjectWriter getObjectWriter( final ObjectMapper mapper )
    {
        return this.objectWriterGetter.apply( mapper );
    }
}