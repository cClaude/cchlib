package com.googlecode.cchlib.i18n.unit.strings;

import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString1I18nTestContener2;
import static com.googlecode.cchlib.i18n.unit.strings.I18nStringTestReferenceTest.INIT_myString2I18nTestContener2;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;

public final class I18nTestContener2 implements I18nAutoUpdatable
{
    @I18nString private String myString1I18nTestContener2;
    @I18nString private String myString2I18nTestContener2;

    public I18nTestContener2()
    {
        beSureNotFinal();
    }

    private void beSureNotFinal()
    {
        this.myString1I18nTestContener2 = INIT_myString1I18nTestContener2;
        this.myString2I18nTestContener2 = INIT_myString2I18nTestContener2;
    }

    /**
     * This method will be discover and invoke by I18n process
     */
    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        getLogger().info(
            "before INIT_myStringI18nTestContener2.performeI18n(AutoI18n) : " + this
            );

        autoI18n.performeI18n( this, this.getClass() );

        getLogger().info(
            "after INIT_myStringI18nTestContener2.performeI18n(AutoI18n) : " + this
            );
    }

    private Logger getLogger()
    {
        return Logger.getLogger( I18nTestContener2.class );
    }

    final String getMyString1I18nTestContener2()
    {
        return this.myString1I18nTestContener2;
    }

    final String getMyString2I18nTestContener2()
    {
        return this.myString2I18nTestContener2;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "I18nTestContener2 [myString1I18nTestContener2=" );
        builder.append( this.myString1I18nTestContener2 );
        builder.append( ", myString2I18nTestContener2=" );
        builder.append( this.myString2I18nTestContener2 );
        builder.append( ']' );

        return builder.toString();
    }
}
