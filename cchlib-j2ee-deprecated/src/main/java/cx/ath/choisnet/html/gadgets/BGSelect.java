package cx.ath.choisnet.html.gadgets;

import cx.ath.choisnet.html.javascript.AbstractJavascript;

public class BGSelect extends AbstractBGSelect
{

    protected BGSelectAbstractCollectionFormater formater;
    protected String[] optionValue;
    protected String[] optionDatas;
    protected int      optionSelected;

    public BGSelect(
            String                              gadgetName,
            BGSelectAbstractCollectionFormater  formater, 
            Integer                             size,
            AbstractJavascript                  javascript
            )
    {
        super(gadgetName, size, javascript);

        this.optionValue = null;
        this.optionDatas = null;
        this.optionSelected = -1;
        this.formater = formater;
    }

    private void computeDatas()
    {
        if(formater != null) {
            optionValue = formater.getOptionValue();
            optionDatas = formater.getOptionDatas();
            optionSelected = formater.getOptionSelected();
        }
    }

    @Override
    public int getSelectedIndex()
    {
        if(optionDatas == null)  {
            computeDatas();
        }
        
        return optionSelected;
    }

    @Override
    public String[] getOptionValue()
    {
        if(optionDatas == null) {
            computeDatas();
        }
        
        return optionValue;
    }

    @Override
    public String[] getOptionDatas()
    {
        if(optionDatas == null) {
            computeDatas();
        }
        
        return optionDatas;
    }
}
