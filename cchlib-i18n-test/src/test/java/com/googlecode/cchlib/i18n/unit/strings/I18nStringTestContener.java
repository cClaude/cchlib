package com.googlecode.cchlib.i18n.unit.strings;

import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.annotation.I18nCustomMethod;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public class I18nStringTestContener implements I18nAutoCoreUpdatable
{
    private static final Logger LOGGER = Logger.getLogger( I18nStringTestContener.class );

    @I18nString private String myString = I18nStringTestReference.INIT_myString;
    @I18nString @I18nIgnore private String myStringIgnore = I18nStringTestReference.INIT_myString;
    @I18nString(id="I18nStringTestContener_GlobalStringID1") private String myGlobalStringID = I18nStringTestReference.INIT_myGlobalStringID1;
    @I18nString(method="customizeString2") private String myGlobalStringIDMethod2 = I18nStringTestReference.INIT_myGlobalStringIDMethod2;

    public I18nStringTestContener()
    {
        // Nothing !
    }

    @I18nCustomMethod
    public void customizeString2()
    {
        myGlobalStringIDMethod2 = I18nStringTestReference.DEFAULT_BUNDLE_myGlobalStringIDMethod2;
    }

    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        LOGGER.info( "before autoI18n.performeI18n() : " + this );
        autoI18n.performeI18n( this, this.getClass() );
        LOGGER.info( "after autoI18n.performeI18n() : " + this );
    }

    final String getMyString()
    {
        return myString;
    }

    final void setMyString( final String myString )
    {
        this.myString = myString;
    }

    final String getMyStringIgnore()
    {
        return myStringIgnore;
    }

    final void setMyStringIgnore( final String myStringIgnore )
    {
        this.myStringIgnore = myStringIgnore;
    }

    final String getMyGlobalStringID()
    {
        return myGlobalStringID;
    }

    final void setMyGlobalStringID( final String myGlobalStringID )
    {
        this.myGlobalStringID = myGlobalStringID;
    }

    final String getMyGlobalStringIDMethod2()
    {
        return myGlobalStringIDMethod2;
    }

    final void setMyGlobalStringIDMethod2( final String myGlobalStringIDMethod2 )
    {
        this.myGlobalStringIDMethod2 = myGlobalStringIDMethod2;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "I18nStringTestContener [myString=" );
        builder.append( myString );
        builder.append( ", myStringIgnore=" );
        builder.append( myStringIgnore );
        builder.append( ", myGlobalStringID=" );
        builder.append( myGlobalStringID );
        builder.append( ", myGlobalStringIDMethod2=" );
        builder.append( myGlobalStringIDMethod2 );
        builder.append( "]" );
        return builder.toString();
    }
}
