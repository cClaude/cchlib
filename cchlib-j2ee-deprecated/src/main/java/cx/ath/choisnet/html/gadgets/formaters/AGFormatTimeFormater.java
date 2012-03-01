package cx.ath.choisnet.html.gadgets.formaters;

import cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException;
import cx.ath.choisnet.util.datetime.BasicTime;
import cx.ath.choisnet.util.datetime.BasicTimeException;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import cx.ath.choisnet.html.gadgets.BGSelectAbstractCollectionFormater;

public class AGFormatTimeFormater
    extends BGSelectAbstractCollectionFormater
{
    protected int selectedItem;

    public AGFormatTimeFormater(
            SimpleDateFormat    formatter,
            BasicTime           from,
            BasicTime           to,
            BasicTime           timeToSelect,
            Integer             minutesStep
            ) {
        super(0);

        selectedItem = -1;

        try
        {
            BasicTime           timeStep = minutesStep != null ? new BasicTime(minutesStep.intValue() * 60) : new BasicTime(60L);
            LinkedList<String>  value    = new LinkedList<String>();
            LinkedList<String>  datas    = new LinkedList<String>();
            int                 index    = 0;

            for(BasicTime ctime = new BasicTime(from); ctime.compareTo(to) < 0; ctime.add(timeStep)) {
                value.add(String.valueOf(ctime.longValue()));
                datas.add(ctime.toString(formatter));

                if(timeToSelect.equals(ctime)) {
                    selectedItem = index;
                }

                index++;
            }

            optionValue = new String[index];
            optionDatas = new String[index];

            for(int i = 0; i < index; i++) {
                optionValue[i] = value.get(i);
                optionDatas[i] = datas.get(i);
            }
        }
        catch(BasicDateTimeNegativeValueException | BasicTimeException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOptionSelected()
    {
        return selectedItem;
    }
}
