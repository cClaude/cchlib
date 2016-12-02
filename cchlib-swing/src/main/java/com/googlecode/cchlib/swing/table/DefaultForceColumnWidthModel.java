package com.googlecode.cchlib.swing.table;

//Not public
final class DefaultForceColumnWidthModel implements ForceColumnWidthModel
{
    @Override
    public boolean isWidthFor( final int columnIndex )
    {
        return false;
    }

    @Override
    public int getWidthFor( final int columnIndex )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRemainingColumnIndex()
    {
        return -1;
    }
}