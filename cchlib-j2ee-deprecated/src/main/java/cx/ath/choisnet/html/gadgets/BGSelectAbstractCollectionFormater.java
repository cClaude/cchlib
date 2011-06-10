package cx.ath.choisnet.html.gadgets;

import java.util.AbstractCollection;

public abstract class BGSelectAbstractCollectionFormater
{
    protected String[] optionValue;
    protected String[] optionDatas;

    protected BGSelectAbstractCollectionFormater(
            AbstractCollection<?> collection
            )
    {
        final int size = collection.size();

        optionValue = new String[size];
        optionDatas = new String[size];
    }

    protected BGSelectAbstractCollectionFormater(int size)
    {
        if(size > 0) {
            optionValue = new String[size];
            optionDatas = new String[size];
        }
    }

    public String[] getOptionValue()
    {
        return optionValue;
    }

    public String[] getOptionDatas()
    {
        return optionDatas;
    }

    public abstract int getOptionSelected();
}
