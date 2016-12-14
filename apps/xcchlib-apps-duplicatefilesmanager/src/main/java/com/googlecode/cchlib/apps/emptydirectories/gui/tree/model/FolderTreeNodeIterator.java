package com.googlecode.cchlib.apps.emptydirectories.gui.tree.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import com.googlecode.cchlib.util.iterator.SingletonIterator;

// Use by FolderTreeModel and by FolderTreeModel2
final class FolderTreeNodeIterator implements Iterator<FolderTreeNode>
{
    private final List<Iterator<FolderTreeNode>> iterators = new ArrayList<>();

    public FolderTreeNodeIterator( final Iterable<FolderTreeNode> rootNodes )
    {
        for( final FolderTreeNode rn : rootNodes ) {
            this.iterators.add( new SingletonIterator<>( rn ) );
            }
    }

    @Override
    public boolean hasNext()
    {
        final Iterator<Iterator<FolderTreeNode>> globalIterator = this.iterators.iterator();

        while( globalIterator.hasNext() ) {
            final Iterator<FolderTreeNode> current = globalIterator.next();

            if( current.hasNext() ) {
                return true;
                }
            }

        return false;
    }

    @Override
    public FolderTreeNode next()
    {
        // Find next non empty iterator.
        final Iterator<Iterator<FolderTreeNode>> globalIterator = this.iterators.iterator();

        while( globalIterator.hasNext() ) {
            final Iterator<FolderTreeNode> current = globalIterator.next();

            if( current.hasNext() ) {
                final FolderTreeNode node = current.next();

                // Add children to global iterator
                this.iterators.add( node.iterator() );

                return node;
                }
            else {
                globalIterator.remove(); // remove empty entry
                }
            }
        throw new NoSuchElementException();
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
