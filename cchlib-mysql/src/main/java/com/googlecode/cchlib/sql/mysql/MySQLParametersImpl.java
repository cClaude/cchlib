package com.googlecode.cchlib.sql.mysql;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.lang.StringHelper;

public class MySQLParametersImpl implements MySQLParameters
{
    private Collection<MySQLStandardParameters> mySQLStandardParameters = EnumSet.noneOf( MySQLStandardParameters.class );
    private String                              customParameters        = StringHelper.EMPTY;

    public void setMySQLStandardParameters(
        final Set<MySQLStandardParameters> mySQLStandardParameters
        )
    {
        this.mySQLStandardParameters = mySQLStandardParameters;
    }

    @Override
    @Nonnull
    public Collection<MySQLStandardParameters> getMySQLStandardParameters()
    {
        if( this.mySQLStandardParameters == null ) {
            this.mySQLStandardParameters = Collections.emptyList();
        }

        return this.mySQLStandardParameters;
    }

    public void setCustomParameters(
        final String customParameters
        )
    {
        this.customParameters = customParameters;
    }

    @Override
    @Nonnull
    public String getCustomParameters()
    {
        if( this.customParameters == null ) {
            this.customParameters = StringHelper.EMPTY;
        }

        return this.customParameters;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "MySQLParametersImpl [mySQLStandardParameters=" );
        builder.append( this.mySQLStandardParameters );
        builder.append( ", customParameters=" );
        builder.append( this.customParameters );
        builder.append( ']' );

        return builder.toString();
    }

    public static MySQLParameters copyOf( final MySQLParameters parameters )
    {
        final MySQLParametersImpl instance = new MySQLParametersImpl();

        if( parameters == null) {
            return instance;
        } else {
            return newInstance(
                    parameters.getMySQLStandardParameters(),
                    parameters.getCustomParameters()
                    );
        }
    }

    public static MySQLParametersImpl newInstance(
        final Collection<MySQLStandardParameters> mySQLStandardParameters,
        final String                              customParameters
        )
    {
        final MySQLParametersImpl instance = new MySQLParametersImpl();

        if( mySQLStandardParameters == null ) {
            instance.setMySQLStandardParameters( EnumSet.noneOf( MySQLStandardParameters.class ) );
        } else {
            instance.setMySQLStandardParameters( EnumSet.copyOf( mySQLStandardParameters ) );
        }

        instance.setCustomParameters( StringHelper.nullToEmpty( customParameters ) );

        return instance;
    }
}
