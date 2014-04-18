package com.googlecode.cchlib.i18n.core.resolve;

import com.googlecode.cchlib.NeedDoc;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 *
 */
@NeedDoc
public interface I18nKeyFactory extends Serializable
{
    String getKeyBase( Field field, String keyIdValue );
}
