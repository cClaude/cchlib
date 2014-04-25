package com.googlecode.cchlib.i18n.unit.i18nstring;

//import java.util.EnumSet;
//import java.util.Locale;
//import org.apache.log4j.Logger;
//import org.fest.assertions.Assertions;
//import org.junit.Test;
//import com.googlecode.cchlib.i18n.AutoI18nConfig;
//import com.googlecode.cchlib.i18n.core.AutoI18nCore;
//import com.googlecode.cchlib.i18n.core.AutoI18nCoreFactory;
//import com.googlecode.cchlib.i18n.unit.util.TestUtils;
//
//public class I18nStringTest
//{
//    private static final Logger LOGGER = Logger.getLogger( I18nStringTest.class );
//    static final String INIT_myString = "my-string-text-1";
//    private static final String DEFAULT_BUNDLE_myString = "OK(myString)";
//    private I18nStringContener objectToI18n;
//
//    @Test
//    public void performeI18n()
//    {
//        this.objectToI18n = new I18nStringContener();
//        final EnumSet<AutoI18nConfig> config   = TestUtils.getDebugConfig();
//        final AutoI18nCore            autoI18n = AutoI18nCoreFactory.createAutoI18nCore(
//                config,
//                TestUtils.VALID_MESSAGE_BUNDLE,
//                Locale.ENGLISH
//                );
//
//        LOGGER.info( "before : " + objectToI18n.getMyString() );
//        objectToI18n.performeI18n( autoI18n );
//        LOGGER.info( "after : " + objectToI18n.getMyString() );
//
//        Assertions.assertThat( objectToI18n.getMyString() ).isEqualTo( DEFAULT_BUNDLE_myString );
//    }
//}
