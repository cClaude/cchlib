package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.ListModel;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.util.iterator.Iterators;

class Selected implements Iterable<KeyFileState> {
    private final List<KeyFileState> selectedList;

    Selected( final ListModel<KeyFileState> listModel, final int[] selectedIndices )
    {
        if( selectedIndices == null ) {
            throw new IllegalArgumentException( "selectedIndices is null - Illegal value" );
        }

        this.selectedList = new ArrayList<>( selectedIndices.length );

        for( final int index : selectedIndices ) {
            this.selectedList.add( listModel.getElementAt( index ) );
        }
    }

    public String getKey()
    {
        return this.selectedList.get( 0 ).getKey();
    }

    @Override
    public Iterator<KeyFileState> iterator()
    {
        return Iterators.unmodifiableIterator( this.selectedList.iterator() );
    }

    public Collection<KeyFileState> getSelectedList()
    {
        return Collections.unmodifiableList( this.selectedList );
    }

    // @Deprecated
    // public List<File> _toFileList()
    // {
    // return toFiles().collect( Collectors.toList() );
    // }

    // @Deprecated
    // public Stream<File> toFiles()
    // {
    // return this.selectedList.stream().map( kf -> kf.getFile() );
    // }

    public Stream<KeyFileState> toKeyFileStateStream()
    {
        return this.selectedList.stream();
    }
}