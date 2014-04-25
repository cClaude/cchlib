package com.googlecode.cchlib.i18n.unit.i18nstring;

//import org.apache.log4j.Logger;
//import org.fest.util.VisibleForTesting;
//import com.googlecode.cchlib.i18n.annotation.I18nString;
//import com.googlecode.cchlib.i18n.core.AutoI18nCore;
//import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
//
//public class I18nStringContener implements I18nAutoCoreUpdatable {
//    private static final Logger LOGGER = Logger.getLogger( I18nStringContener.class );
//
//    @I18nString private String myString = I18nStringTest.INIT_myString;
//
//    public I18nStringContener()
//    {
//        // empty
//    }
//
//    @Override // I18nAutoCoreUpdatable
//    public void performeI18n( final AutoI18nCore autoI18n )
//    {
//        LOGGER.info( "before : " + getMyString() );
//
//        autoI18n.performeI18n( this, this.getClass() );
//
//        LOGGER.info( "after : " + getMyString() );
//    }
//
//    @VisibleForTesting
//    final String getMyString()
//    {
//        return myString;
//    }
//
//    final void setMyString( final String myString )
//    {
//        this.myString = myString; // be sure field is not final
//    }
//}
