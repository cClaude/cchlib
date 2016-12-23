package com.googlecode.cchlib.sandbox.benchs.tools;

import java.util.LinkedList;
import java.util.List;

/**
 * @since 3.02
 */
public final class StatsEntry
{
    private final List<Long> delays;

    public StatsEntry()
    {
        this.delays = new LinkedList<>();
    }

    public StatsEntry addDelay( final long delay )
    {
        this.delays.add( Long.valueOf( delay ) );

        return this;
    }

    @Override
    public String toString()
    {
        return this.delays.toString();
    }

    public double[] getDelays()
    {
        final double[] array = new double[ this.delays.size() ];
        int i = 0;

        for( final Long l : this.delays ) {
            array[ i++ ] = l.doubleValue();
        }

        return array;
    }
}
