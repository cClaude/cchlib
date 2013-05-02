package cx.ath.choisnet.html.gadgets;

import cx.ath.choisnet.html.HTMLDocumentException;
import cx.ath.choisnet.html.HTMLDocumentWriter;
import cx.ath.choisnet.html.HTMLFormException;
import cx.ath.choisnet.html.javascript.AbstractJavascript;

public abstract class AbstractBGSelect
    extends AbstractBG
{
    protected Integer size;
    protected AbstractJavascript javascript;

    public AbstractBGSelect(
            String gadgetName, 
            Integer size, 
            AbstractJavascript javascript
            )
    {
        super(gadgetName);

        this.size = size;
        this.javascript = javascript;
    }

    public abstract int getSelectedIndex();

    public abstract String[] getOptionValue();

    public abstract String[] getOptionDatas();

    @Override
    public void writeHTML(HTMLDocumentWriter out)
        throws HTMLDocumentException
    {
        String javascriptString = javascript == null ? null : javascript.toInLineJavascript();

        out.write(
                AbstractBGSelect.build(
                        gadgetName, 
                        getOptionValue(),
                        getOptionDatas(),
                        getSelectedIndex(), 
                        size,
                        javascriptString
                        )
                );
    }

    @Override
    public final String getHiddenHTMLDatas()
    {
        String[] optionValue = getOptionValue();
        //String[] optionDatas = getOptionDatas();
        int optionSelected = getSelectedIndex();
        String valueTxt;
        
        if(optionValue != null) {
            valueTxt = optionValue[optionSelected <= 0 ? 0 : optionSelected];
        } 
        else {
            valueTxt = String.valueOf(optionSelected);
        }
        
        return BGInputText.buildHIDDEN(gadgetName, valueTxt);
    }

    @Override
    public Object getValue(javax.servlet.ServletRequest request)
        throws HTMLFormException
    {
        return super.protected_getStringValue(request);
    }

    @Override
    public long getLongValue(javax.servlet.ServletRequest request)
        throws HTMLFormException
    {
        return Long.parseLong(super.protected_getStringValue(request));
    }

    @Override
    public String getStringValue(javax.servlet.ServletRequest request)
        throws HTMLFormException
    {
        return super.protected_getStringValue(request);
    }

    protected static final String build(
            String      gadgetName, 
            String[]    optionValue, 
            String[]    optionDatas,
            int         optionSelected,
            Integer     gadgetSize, 
            String      javascript
            )
        throws BGSelectException
    {
        StringBuilder b = new StringBuilder();

        b.append("<select name=\"")
         .append(gadgetName)
         .append("\"");

        if(javascript != null) {
            b.append(" ");
            b.append(javascript);
        }
        
        if(gadgetSize != null) {
            b.append(" size=\"");
            b.append(gadgetSize.toString());
            b.append("\"");
        }
        
        b.append(">");

        int optionLen = optionDatas.length;

        if(optionValue != null && optionLen != optionValue.length) {
            throw new BGSelectException("optionValue.length != optionDatas.length");
        }
        
        for(int i = 0; i < optionLen; i++) {
            if(optionValue != null) {
                b.append("<option value=\"")
                 .append(optionValue[i])
                 .append("\"");
            }
            else {
                b.append("<option value=\"")
                 .append(i)
                 .append("\"");
            }
            
            if(i == optionSelected) {
                b.append(" selected");
            }
            
            b.append(">");
            b.append(optionDatas[i]);
            b.append("</option>");
        }

        b.append("</select>\n");

        return b.toString();
    }
}
