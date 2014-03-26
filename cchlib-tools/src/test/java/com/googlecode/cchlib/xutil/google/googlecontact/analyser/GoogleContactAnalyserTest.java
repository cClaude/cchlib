package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.AnalyserCustomTypeMethodContener;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.AnalyserMethodContener;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.GoogleContactAnalyser;
import com.googlecode.cchlib.xutil.google.googlecontact.analyser.TypeInfo;

public class GoogleContactAnalyserTest {

    private static final Logger LOGGER = Logger.getLogger( GoogleContactAnalyserTest.class );

    public static final int METHODS_FOR_STRING      = 27;
    public static final int METHODS_FOR_CUSTOM_TYPE = 9;
    private static final int BASIC_ENTRY = 2;
    private static final int IM_ENTRY = 3;
    private static final int ORGANIZATION_ENTRY = 8;
    private static final int ADDRESS_ENTRY = 9;

    @Test
    public void testConstructor()
    {
        GoogleContactAnalyser analyser = new GoogleContactAnalyser();

        Assert.assertNotNull( analyser );

        final TypeInfo typeInfo = analyser.getTypeInfo();

        check( typeInfo, METHODS_FOR_STRING, METHODS_FOR_CUSTOM_TYPE );

        check( typeInfo.getMethodForCustomType().get( "Custom Field" ).getTypeInfo(), BASIC_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "E-mail" ).getTypeInfo(), BASIC_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "Event" ).getTypeInfo(), BASIC_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "IM" ).getTypeInfo(), IM_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "Organization" ).getTypeInfo(), ORGANIZATION_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "Phone" ).getTypeInfo(), BASIC_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "Relation" ).getTypeInfo(), BASIC_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "Website" ).getTypeInfo(), BASIC_ENTRY, 0 );
        check( typeInfo.getMethodForCustomType().get( "Address" ).getTypeInfo(), ADDRESS_ENTRY, 0 );
    }

    private void check(
        final TypeInfo typeInfo,
        final int      expectedMETHODS_FOR_STRING,
        final int      expectedMETHODS_FOR_CUSTOM_TYPE
        )
    {
        for( final Map.Entry<String, AnalyserMethodContener> entry : typeInfo.getMethodForStrings().entrySet() ) {
            LOGGER.debug( "Entry<String, AnalyserMethodContener>: [" + entry.getKey() + "]=" + entry.getValue() );
        }
        Assert.assertEquals( expectedMETHODS_FOR_STRING, typeInfo.getMethodForStrings().size() );

        for( final Entry<String, AnalyserCustomTypeMethodContener> entry : typeInfo.getMethodForCustomType().entrySet() ) {
            LOGGER.debug( "Entry<String, AnalyserCustomTypeMethodContener>: [" + entry.getKey() + "]=" + entry.getValue() );
        }
        Assert.assertEquals( expectedMETHODS_FOR_CUSTOM_TYPE, typeInfo.getMethodForCustomType().size() );
    }

}
