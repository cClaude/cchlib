package com.googlecode.cchlib.sql.mysql;

import java.util.Collection;
import java.util.EnumSet;
import javax.annotation.Nonnull;

public interface MySQLParameters
{
    /**
     * Return typically a {@link EnumSet}&lt;{@link MySQLParameters}&gt;
     * @return parameters for {@link MySQL#getURL(String, int, MySQLParameters)}
     */
    @Nonnull Collection<MySQLStandardParameters> getMySQLStandardParameters();

    @Nonnull String getCustomParameters();
}
