package com.googlecode.cchlib.i18n.unit;

@Deprecated
public interface TestReferenceDeprecated
{
    @Deprecated
    void beforePrepTest( PrepTestPart prepTest );

    @Deprecated
    void performeI18n();

    @Deprecated
    void afterPrepTest();

    @Deprecated
    int getSyntaxeExceptionCount();

    @Deprecated
    int getMissingResourceExceptionCount();
}
