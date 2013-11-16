package cx.ath.choisnet.html.gadgets;

import javax.servlet.ServletRequest;
import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.HTMLFormException;
import cx.ath.choisnet.html.HTMLGadgetNotFoundException;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class BGCheckbox extends AbstractBG
{
    protected String deprecatedDatas;
    protected AbstractJavascript javascript;
    protected boolean checked;

    public BGCheckbox(
            String              checkboxName, 
            boolean             checked, 
            AbstractJavascript  javascript
            )
    {
        super(checkboxName);

        deprecatedDatas = null;
        this.javascript = null;
        this.javascript = javascript;
        this.checked = checked;
    }

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        String javascriptString = (javascript == null) ? null : javascript.toInLineJavascript();

        if(deprecatedDatas == null) {
            out.write(BGCheckbox.build(gadgetName, checked, javascriptString));
        }
        else {
            out.write(BGCheckbox.build(gadgetName, checked, javascriptString));
            out.write(deprecatedDatas);
        }
    }

    @Override
    public String getHiddenHTMLDatas()
    {
        if(checked) {
            return BGInputText.buildHIDDEN(gadgetName, "on");
        } 
        else {
            return "";
        }
    }

    @Override
    public Object getValue(ServletRequest request)
        throws HTMLFormException
    {
        return getStringValue(request);
    }

    @Override
    public long getLongValue(ServletRequest request)
        throws HTMLFormException
    {
        String s = getStringValue(request);

        if(s != null) {
            return Long.parseLong(s);
        } 
        else {
            return -1L;
        }
    }

    @Override
    public String getStringValue(ServletRequest request)
        throws HTMLGadgetNotFoundException
    {
        return BGCheckbox.getRequestParameterValues(request, gadgetName)[0];
    }

    protected static String build(String gadgetName, boolean checked, String javascript)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<input type=\"CHECKBOX\" name=\"")
          .append(gadgetName)
          .append('"');

        if(checked) {
            sb.append(" checked");
        }
        
        if(javascript != null) {
            sb.append(' ')
              .append(javascript);
        }
        
        sb.append(" />");

        return sb.toString();
    }
}
