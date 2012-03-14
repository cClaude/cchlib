package cx.ath.choisnet.html;

import javax.servlet.ServletRequest;

/**
 *
 */
public interface AbstractFormHTML
    extends HTMLWritable
{

    public abstract String getHiddenHTMLDatas()
        throws HTMLDocumentException;

    public abstract Object getValue(ServletRequest servletrequest)
        throws HTMLFormException;
}
