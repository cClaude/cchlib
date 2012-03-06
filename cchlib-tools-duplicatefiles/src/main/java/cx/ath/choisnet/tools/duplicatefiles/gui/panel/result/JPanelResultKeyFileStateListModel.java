package cx.ath.choisnet.tools.duplicatefiles.gui.panel.result;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;

//not public
class JPanelResultKeyFileStateListModel
    extends AbstractListModel<KeyFileState>
        implements KeyFileStateListModel
{
    private static final long serialVersionUID = 1L;
    private List<KeyFileState> dataList;

    public JPanelResultKeyFileStateListModel()
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
    public KeyFileState getElementAt( int index )
    {
        return dataList.get( index );
    }
    @Override
    public KeyFileState remove( int index )
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
            fireIntervalAdded( this, 0, size -1 );
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
