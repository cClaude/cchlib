package com.googlecode.cchlib.servlet.simple;

public class HTMLParameterHelper
{
    private static final String ID = "ID";

    public HTMLParameterHelper()
    {
    }

    public static final String getHTMLID(Object object)
    {
        return ID + Integer.toHexString(object.hashCode()).toUpperCase(); // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.internationalization.useLocaleSpecificMethods
    }

    public static final int getHashCode( final String htmlID )
    {
        if( htmlID.startsWith( ID ) ) {
            return Integer.parseInt( htmlID.substring(2), 16 );
            }
        else {
            return 0;
            }
    }

    public static final int[] getHashCodes( final String[] htmlIDs )
    {
        final int[] hashCodes = new int[htmlIDs.length];
        int         i         = 0;

        for( final String htmlID : htmlIDs ) {
            hashCodes[i++] = HTMLParameterHelper.getHashCode(htmlID);
            }

        return hashCodes;
    }

    public static final int[] getHashCodes( final ParameterValue aParameterValue )
    {
        return HTMLParameterHelper.getHashCodes(aParameterValue.toArray());
    }

    public static final Object getEntry( final Iterable<?> collection, final String htmlID )
    {
        final int hashCode = HTMLParameterHelper.getHashCode(htmlID);

        for( final Object item : collection ) {
            if( item.hashCode() == hashCode ) {
                return item;
                }
            }

        return null;
    }
}
