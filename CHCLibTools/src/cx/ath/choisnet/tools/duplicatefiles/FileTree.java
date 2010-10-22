/**
 * 
 */
package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * TODO: Doc! and code !
 * 
 * @author Claude CHOISNET
 */
public class FileTree implements Serializable
{
    private static final long serialVersionUID = 1L;
    private HashMap<String,Node> nodes = new HashMap<String,Node>();
    
    static class Node implements Serializable
    {
        private static final long serialVersionUID = 1L;
        File                 file;
        HashMap<String,Node> nodes = new HashMap<String,Node>();
    }
    
    public FileTree()
    {
    }

    public void add(File file)
    {
        Queue<Node> q = new LinkedList<Node>();
        File f = file;
        
        while( f != null ) {
            Node n = new Node();
            n.file = f;
            q.add( n );
        }
        
        Node                n;
        Map<String,Node>    nList = nodes;
        
        while( (n = q.poll()) != null ) {
            String name  = n.file.getName();
            Node   aNode = nList.get( name );

            if( aNode == null ) {
                aNode = n;
                nList = n.nodes; // chaining
            }
            else {
                //Node already exist
                nList = aNode.nodes;
            }
        }

        q.clear();
    }
}
