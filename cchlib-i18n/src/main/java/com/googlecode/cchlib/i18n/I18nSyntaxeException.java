package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.logging.LogFieldFormat;

public class I18nSyntaxeException extends Exception
{
    private static final long serialVersionUID = 1L;

    public I18nSyntaxeException( Field f )
    {
        super( "Syntaxe error on field: " + LogFieldFormat.toString( f ));
    }
}
