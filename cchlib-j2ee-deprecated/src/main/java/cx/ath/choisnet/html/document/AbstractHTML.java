package cx.ath.choisnet.html.document;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.HTMLWritable;

public abstract class AbstractHTML implements HTMLWritable
{
    private final List<HTMLWritable> htmlItemList = new LinkedList<HTMLWritable>();

    public AbstractHTML()
    {
    }

    public void add(HTMLWritable item)
    {
        htmlItemList.add(item);
    }

    public void add(String html)
    {
        htmlItemList.add(new HTMLString(html));
    }

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        for(
                Iterator<HTMLWritable> iter0 = htmlItemList.iterator(); 
                iter0.hasNext(); 
                iter0.next().writeHTML(out))
            { } // $codepro.audit.disable emptyForStatement
    }
}
