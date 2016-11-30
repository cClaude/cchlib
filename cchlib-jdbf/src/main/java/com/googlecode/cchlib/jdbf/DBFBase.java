package com.googlecode.cchlib.jdbf;

/**
 * Base class for {@link DBFReader} and {@link DBFWriter}.
 */
abstract class DBFBase
{
    protected static final int END_OF_DATA = 0x1A;

    private String characterSetName = "8859_1";

    /**
     * If the library is used in a non-latin environment use
     * this method to set corresponding character set. More
     * information: http://www.iana.org/assignments/character-sets
     * Also see the documentation of the class java.nio.charset.Charset
     *
     * @return character set name
     */
    public String getCharacterSetName()
    {
        return this.characterSetName;
    }

    /**
     * Define character set
     *
     * @characterSetName character set name to set
     */
    public void setCharacterSetName( final String characterSetName )
    {
        this.characterSetName = characterSetName;
    }
}
