package com.googlecode.cchlib.xutil.google.googlecontact;

import org.junit.Assert;
import org.junit.Test;

public class GoogleContactHeaderFactoryTest extends Data
{

//    private int getMethodsForCustomTypeCount( final Map<Integer, HeaderMethodContener> map )
//    {
//        return getMethodsForCustomTypeCount( map.values() );
//    }

//    private int getMethodsForCustomTypeCount( final Iterable<HeaderMethodContener> parentContener )
//    {
//        int count = 0;
//
//        for( final HeaderMethodContener methodContener : parentContener ) {
//            if( methodContener instanceof HeaderCustomTypeMethodContener ) {
//                final HeaderCustomTypeMethodContener childContener = (HeaderCustomTypeMethodContener)methodContener;
//
//                count  += getMethodsForCustomTypeCount( childContener ) + 1;
//            } else if( methodContener instanceof HeaderCustomTypeMethodContener ) {
//                throw new IllegalStateException();
//            }
//        }
//
//        return count;
//    }

//    private int getMethodsForStringCount( final Map<Integer, HeaderMethodContener> map )
//    {
//        return getMethodsForStringCount( map.values() );
//    }

//    private int getMethodsForStringCount( final Iterable<HeaderMethodContener> parentContener )
//    {
//        int count = 0;
//
//        for( final HeaderMethodContener methodContener : parentContener ) {
//            if( methodContener instanceof HeaderCustomTypeMethodContener ) {
//                final HeaderCustomTypeMethodContener childContener = (HeaderCustomTypeMethodContener)methodContener;
//
//                count  += getMethodsForStringCount( childContener );
//            } else if( methodContener instanceof HeaderCustomTypeMethodContener ) {
//                throw new IllegalStateException();
//            } else {
//                count++;
//            }
//        }
//
//        return count;
//    }

    @Test
    public void testConstructor0() throws GoogleContactFactoryException
    {
        final GoogleContactHeader googleContactHeader = GoogleContactHeaderFactory.newGoogleContactHeader( HEADERS0 );

        Assert.assertEquals( HEADERS0.length, googleContactHeader.getIndexMethodConteners().size() );
//        Assert.assertEquals( HEADERS0.length -18, getMethodsForStringCount( googleContactHeader.getIndexMethodConteners() ) );
//        Assert.assertEquals( GoogleContactAnalyserTest.METHODS_FOR_CUSTOM_TYPE, getMethodsForCustomTypeCount( googleContactHeader.getIndexMethodConteners() ) );
    }

    @Test
    public void testConstructor1() throws GoogleContactFactoryException
    {
        final GoogleContactHeader googleContactHeader = GoogleContactHeaderFactory.newGoogleContactHeader( HEADERS1 );

        Assert.assertEquals( HEADERS1.length, googleContactHeader.getIndexMethodConteners().size() );
    }

    @Test
    public void testConstructor_Basic() throws GoogleContactFactoryException
    {

        final GoogleContactHeader googleContactHeader = GoogleContactHeaderFactory.newGoogleContactHeader( HEADERS_BASIC );

        Assert.assertEquals( HEADERS_BASIC.length, googleContactHeader.getIndexMethodConteners().size() );
    }

    @Test
    public void testConstructor_2Mails() throws GoogleContactFactoryException
    {

        final GoogleContactHeader googleContactHeader = GoogleContactHeaderFactory.newGoogleContactHeader( HEADERS_2MAILS );

        Assert.assertEquals( HEADERS_2MAILS.length, googleContactHeader.getIndexMethodConteners().size() );
    }

}
