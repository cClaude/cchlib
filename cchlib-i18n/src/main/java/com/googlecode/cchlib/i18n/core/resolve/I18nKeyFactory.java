package com.googlecode.cchlib.i18n.core.resolve;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.googlecode.cchlib.NeedDoc;

/**
 *
 */
@NeedDoc
public interface I18nKeyFactory extends Serializable
{
    public String getKeyBase( Field field, String keyIdValue );
}
