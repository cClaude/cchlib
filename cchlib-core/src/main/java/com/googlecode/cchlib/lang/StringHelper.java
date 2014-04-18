package com.googlecode.cchlib.lang;

import java.util.ArrayList;

/**
 *
 * @since 4.1.7
 */
public final class StringHelper
{
    public static final String EMPTY = "";
    private static final String[] EMPTY_ARRAY = new String[0];

    private StringHelper() {} // ALl static

    public static String[] emptyArray()
    {
        return EMPTY_ARRAY;
    }

    /**
     * Returns the given string if it is non-null; the empty string otherwise
     * @param str the string to test and possibly return
     * @return string itself if it is non-null; {@link #EMPTY} if it is null
     */
    public static String nullToEmpty( final String str )
    {
        return (str == null) ? EMPTY : str;
    }

    /**
     * Splits this string around matches of the given token
     * <p>
     * This method work exactly like {@link #split(String, String)} but
     * is optimized for token based on character.
     * </p>
     *
     * @param str   String to split
     * @param token Char token to use as delimiter.
     * @return the array of strings computed by splitting this string around matches of the given token
     * @throws NullPointerException if str is null
     * @see #split(String, String)
     */
    public static String[] split( final String str, final char token )
    {
        if( str == null ) {
            throw new NullPointerException();
            }

        final ArrayList<String> array = new ArrayList<>();
        int                     pos   = 0;

        for(;;) {
            final int i = str.indexOf(token, pos);

            if( i < 0 ) {
                array.add( str.substring(pos) );
                break;
                }

            array.add( str.substring(pos, i) );
            pos = i + 1;
            }

        final String[] res = new String[ array.size() ];

        return array.toArray(res);
    }

    /**
     * Splits this string around matches of the given token
     * <p>
     * This method differ from method {@link String#split(String)}
     * Trailing empty strings are included in the resulting array.
     * </p>
     * <br>
     * <br>
     * The string "boo:and:foo", for example, yields the following results with these tokens:
     * <blockquote>
     * <table border="2" cellpadding="1" cellspacing="0">
     * <caption>Example</caption>
     * <tr><th>token</th><th>Result</th></tr>
     * <tr><td>:</td><td>{"boo","and","foo"};</td></tr>
     * <tr><td>o</td><td>{"b","",":and:f","",""}</td></tr>
     * <tr><td>b</td><td>{"","oo:and:foo"}</td></tr>
     * </table>
     * </blockquote>
     *
     * @param str   String to split
     * @param token Char token to use as delimiter.
     * @return the array of strings computed by splitting this string around matches of the given token
     * @throws NullPointerException if str or token is null
     * @see #split(String, char)
     */
    public static String[] split( final String str, final String token )
    {
        if( str == null ) {
            throw new NullPointerException();
            }

        final ArrayList<String> array = new ArrayList<>();
        int                     pos   = 0;

        for(;;) {
            final int i = str.indexOf(token, pos);

            if( i < 0 ) {
                array.add( str.substring(pos) );
                break;
                }

            array.add( str.substring(pos, i) );
            pos = i + token.length();
            }

        final String[] res = new String[ array.size() ];

        return array.toArray(res);
    }
}
