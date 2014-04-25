package com.googlecode.cchlib.i18n.unit;

public interface TestReference {
    void beforePrepTest( PrepTestPartInterface prepTest );

    void performeI18n();

    void afterPrepTest();

    int getSyntaxeExceptionCount();
    int getMissingResourceExceptionCount();
}
