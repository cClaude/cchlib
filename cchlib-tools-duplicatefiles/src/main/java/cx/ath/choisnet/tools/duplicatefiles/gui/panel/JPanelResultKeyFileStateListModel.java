package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;

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

    public void add( final KeyFileState value )
    {
        int index = dataList.size();

        dataList.add( value );

        super.fireIntervalAdded( this, index, index );
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
