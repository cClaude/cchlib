package cx.ath.choisnet.html;

import javax.servlet.ServletRequest;

/**
 *
 */
public interface AbstractFormHTML
    extends HTMLWritable
{
    String getHiddenHTMLDatas()
        throws HTMLDocumentException;

    Object getValue(ServletRequest servletrequest)
        throws HTMLFormException;
}
