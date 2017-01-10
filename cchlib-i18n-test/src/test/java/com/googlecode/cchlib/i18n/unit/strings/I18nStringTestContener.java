package com.googlecode.cchlib.i18n.unit.strings;

import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.DEFAULT_BUNDLE_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myGlobalStringID1;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myGlobalStringIDMethod2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.annotation.I18nCustomMethod;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

public final class I18nStringTestContener implements I18nAutoUpdatable
{
    @I18nString private String myString;

    // Strange case, but since @I18nIgnore is use, the field should be ignore.
    @I18nString @I18nIgnore private String myStringIgnore;

    @I18nString(id="I18nStringTestContener_GlobalStringID1") private String myGlobalStringID;
    @I18nString(method="customizeString2") private String myGlobalStringIDMethod2;

    public I18nStringTestContener()
    {
        beSureNotFinal();
    }

    private void beSureNotFinal()
    {
        this.myString                = INIT_myString;
        this.myStringIgnore          = INIT_myString;
        this.myGlobalStringID        = INIT_myGlobalStringID1;
        this.myGlobalStringIDMethod2 = INIT_myGlobalStringIDMethod2;
    }

    @I18nCustomMethod
    public void customizeString2()
    {
        this.myGlobalStringIDMethod2 = DEFAULT_BUNDLE_myGlobalStringIDMethod2;
    }

    /**
     * This method will be discover and invoke by I18n process
     */
    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        getLogger().info(
            "before I18nStringTestContener.performeI18n(AutoI18nCore) : " + this
            );

        autoI18n.performeI18n( this, this.getClass() );

        getLogger().info(
            "after I18nStringTestContener.performeI18n(AutoI18nCore) : " + this
            );
    }

    private Logger getLogger()
    {
        return Logger.getLogger( I18nStringTestContener.class );
    }

    final String getMyString()
    {
        return this.myString;
    }

    final String getMyStringIgnore()
    {
        return this.myStringIgnore;
    }

    final String getMyGlobalStringID()
    {
        return this.myGlobalStringID;
    }

    final String getMyGlobalStringIDMethod2()
    {
        return this.myGlobalStringIDMethod2;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "I18nStringTestContener [myString=" );
        builder.append( this.myString );
        builder.append( ", myStringIgnore=" );
        builder.append( this.myStringIgnore );
        builder.append( ", myGlobalStringID=" );
        builder.append( this.myGlobalStringID );
        builder.append( ", myGlobalStringIDMethod2=" );
        builder.append( this.myGlobalStringIDMethod2 );
        builder.append( "]" );
        return builder.toString();
    }
}
