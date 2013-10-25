/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5TreeNode.java
** Description   :
** Encodage      : ANSI
**
**  2.01.021 2005.10.20 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5TreeNode
**
*/
package cx.ath.choisnet.util.checksum;

import cx.ath.choisnet.util.CascadingIterator;
import cx.ath.choisnet.util.FlattenIterator;
import cx.ath.choisnet.util.IteratorWrapper;
import cx.ath.choisnet.util.Wrappable;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
** Conteneur correspondant à un dossier d'empreintes : "Message Digest"
**
** @author Claude CHOISNET
** @since   2.00.006
** @version 2.01.021
*/
public class MD5TreeNode
    implements
        Comparable<MD5TreeNode>,
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
** Lien vers le noeud père
*/
private File folderFile;

/**
** Lien vers le noeud père
*/
private MD5TreeNode parent;

/**
** Couple (nom de dosser, noeud)
*/
private SortedMap<String,MD5TreeNode> nodes;

/**
** Couple (nom de fichier, empreinte)
*/
private SortedMap<String,MD5TreeEntry> entries;

/**
**
*/
protected MD5TreeNode( MD5TreeNode parent, File folderFile ) // -----------
{
 this.folderFile    = folderFile;
 this.parent        = parent;
 this.nodes         = new TreeMap<String,MD5TreeNode>();
 this.entries       = new TreeMap<String,MD5TreeEntry>();
}

/**
** @return un objet MDNode correspondant au nouveau noeud créé.
*/
public MD5TreeNode addNode( String foldername ) // ------------------------
{
 MD5TreeNode node = new MD5TreeNode( this, new File( getFile(), foldername ) );

 return addNode( node, foldername );
}

/**
** @return un objet MDNode correspondant au nouveau noeud créé.
*/
protected MD5TreeNode addNode( MD5TreeNode node, String foldername ) // ---
{
 this.nodes.put( foldername, node );

 return node;
}

/**
**
** @see Comparable#compareTo
*/
public int compareTo( MD5TreeNode anOtherMD5TreeNode ) // -----------------
{
 return this.hashCode() - anOtherMD5TreeNode.hashCode();
}

/**
** Ajoute une fichier à l'aide de son nom et de son MD5TreeEntry
*/
public void add( String filename, MD5TreeEntry md ) // --------------------
{
 this.entries.put( filename, md );
}

/**
** Retourne l'objet File correspondant au noeud courant.
**
** @return un objet File correspondant au dossier.
*/
public File getFile() // --------------------------------------------------
{
 return this.folderFile;
}

/**
**
*/
public SortedMap<String,MD5TreeEntry> getFileEntries() // ------------------
{
 return Collections.unmodifiableSortedMap( entries );
}

/**
**
*/
public MD5TreeNode getParent() // -----------------------------------------
{
 return this.parent;
}

    /**
    ** Classe permettant de faire un parcours récursif de tous les dossiers
    ** vus comme un objet MDNode
    **
    ** @see MD5TreeNode#getNodeIterator()
    */
    protected static class NodeIterator
            implements
                Iterable<MD5TreeNode>,
                Wrappable<MD5TreeNode,NodeIterator>
    {
        /** */
        private MD5TreeNode node;

        /** */
        public NodeIterator( MD5TreeNode node )
        {
            this.node = node;
        }

        /** */
        public NodeIterator wrappe( MD5TreeNode node )
        {
            return new NodeIterator( node );
        }

        /** */
        public Iterator<MD5TreeNode> iterator()
        {
            return this.node.nodes().iterator();
        }
    };

/**
**
** Iterateur des noeuds de ce noeud.
*/
public Collection<MD5TreeNode> getNodes() // ------------------------------
{
 return this.nodes.values();
}

/**
** Iterateur des noeuds de ce noeud et des fils.
*/
public Iterable<MD5TreeNode> nodes() // -----------------------------------
{
 return new FlattenIterator<MD5TreeNode>(
            this, // Le noeuds (courant) à retourner
            new CascadingIterator<MD5TreeNode>(
                    new IteratorWrapper<MD5TreeNode,NodeIterator>(
                            this.getNodes(), // Les noeuds à explorer
                            new NodeIterator( this )
                            )
                        )
                    );
// return new MultiIterator<MD5TreeNode>(
//            this, // Le noeuds (courant) à retourner
//            new CascadingIterator<MD5TreeNode>(
//                    new IteratorWrapper<MD5TreeNode,NodeIterator>(
//                            this.getNodes(), // Les noeuds à explorer
//                            new NodeIterator( this )
//                            )
//                        )
//                    );
}

} // class
