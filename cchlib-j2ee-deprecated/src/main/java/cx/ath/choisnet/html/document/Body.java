package cx.ath.choisnet.html.document;

import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;

public class Body extends AbstractHTML
{
    private final String cssClass;

    public Body()
    {
        cssClass = null;
    }

    public Body(String cssClass)
    {
        this.cssClass = cssClass;
    }

    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        if(cssClass == null) {
            out.write("<body>");
            } 
        else {
            out.write((new StringBuilder()).append("<body class=\"").append(cssClass).append("\">").toString());
            }
        
        super.writeHTML(out);

        out.write("</body>");
    }
}
