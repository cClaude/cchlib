<<<<<<< HEAD
package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public interface TestPartInterface extends I18nAutoCoreUpdatable
{
    void beforePrepTest( PrepTestPartInterface prepTest );
    void afterPrepTest();
    void runPerformeI18nTest();

    int getSyntaxeExceptionCount();
    int getMissingResourceExceptionCount();
}
=======
package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public interface TestPartInterface extends I18nAutoCoreUpdatable
{
    void beforePrepTest( PrepTestPartInterface prepTest );
    void afterPrepTest();
    void runPerformeI18nTest();

    int getSyntaxeExceptionCount();
    int getMissingResourceExceptionCount();
}
>>>>>>> cchlib-pre4-1-8
