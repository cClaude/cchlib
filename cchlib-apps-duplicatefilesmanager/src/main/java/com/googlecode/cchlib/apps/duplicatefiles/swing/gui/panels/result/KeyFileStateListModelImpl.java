package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;

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
        final KeyFileState value = dataList.remove( index );

        super.fireIntervalRemoved( this, index, index );

        return value;
    }

    void private_add( final KeyFileState value )
    {
        dataList.add( value );
    }

    void private_fireAddedAll()
    {
        final int size = dataList.size();

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
