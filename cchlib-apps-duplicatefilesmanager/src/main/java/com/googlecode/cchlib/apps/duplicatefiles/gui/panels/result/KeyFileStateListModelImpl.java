package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

//NOT public
class KeyFileStateListModelImpl
    extends AbstractListModel<KeyFileState>
        implements KeyFileStateListModel
{
    private static final long serialVersionUID = 1L;
    private final List<KeyFileState> dataList;

    public KeyFileStateListModelImpl()
    {
        this.dataList = new ArrayList<>();

        fireIntervalAdded( this, 0, dataList.size() );
    }
    @Override
    public int getSize()
    {
        return dataList.size();
    }
    @Override
    public KeyFileState getElementAt( final int index )
    {
        return dataList.get( index );
    }
    @Override
    public KeyFileState remove( final int index )
    {
        KeyFileState value = dataList.remove( index );

        super.fireIntervalRemoved( this, index, index );

        return value;
    }

    void private_add( final KeyFileState value )
    {
        dataList.add( value );
    }

    void private_fireAddedAll()
    {
        int size = dataList.size();

        if( size > 0 ) {
            fireIntervalAdded( this, 0, size - 1 );
            }
    }

    public void clear()
    {
        int index = dataList.size();

        if( index > 0 ) {
            index--;

            dataList.clear();

            super.fireIntervalRemoved( this, 0, index );
            }
    }
}
