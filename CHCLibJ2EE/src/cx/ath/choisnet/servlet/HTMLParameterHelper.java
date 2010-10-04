package cx.ath.choisnet.servlet;

//import cx.ath.choisnet.util.IteratorFilter;
import java.util.Iterator;

public class HTMLParameterHelper
{
    public static class Selectable<T>
        implements cx.ath.choisnet.util.Selectable<T>
    {
        private final int hashCodes[];

        public boolean isSelected(Object o)
        {
            int oHashCode = o.hashCode();
            int arr$[] = hashCodes;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; i$++) {
                int hashCode = arr$[i$];
                
                if(hashCode == oHashCode) {
                    return true;
                }
            }

            return false;
        }

        public Selectable(int[] hashCodes)
        {
            this.hashCodes = hashCodes;
        }

        public Selectable(String[] htmlIDs)
        {
            this(HTMLParameterHelper.getHashCodes(htmlIDs));
        }
    }
    
    public HTMLParameterHelper()
    {
    }

    public static final String getHTMLID(Object object)
    {
        return "ID" + Integer.toHexString(object.hashCode()).toUpperCase();
    }

    public static final int getHashCode(String htmlID)
    {
        if( htmlID.startsWith("ID") ) {
            return Integer.parseInt(htmlID.substring(2), 16);
        } 
        else {
            return 0;
        }
    }

    public static final int[] getHashCodes(String[] htmlIDs)
    {
        int[] hashCodes = new int[htmlIDs.length];
        int   i = 0;

        for(String htmlID : htmlIDs ) {
            hashCodes[i++] = HTMLParameterHelper.getHashCode(htmlID);
        }

        return hashCodes;
    }

    public static final int[] getHashCodes(ParameterValue aParameterValue)
    {
        return HTMLParameterHelper.getHashCodes(aParameterValue.toArray());
    }

    public static final Object getEntry(Iterable<?> collection, String htmlID)
    {
        int hashCode = HTMLParameterHelper.getHashCode(htmlID);

        for(Iterator<?> i$ = collection.iterator(); i$.hasNext();) {
            Object item = i$.next();

            if(item.hashCode() == hashCode) {
                return item;
            }
        }
        
        return null;
    }

//    public static final <T> Iterator<T> NOTUSE_select(Iterator<T> iter, String[] htmlIDs)
//    {
//        return new IteratorFilter<T>(
//                    iter, 
//                    new Selectable<T>(htmlIDs)
//                    );
//    }
//
//    public static final <T> Iterator<T> NOTUSE_select(Iterator<T> iter, int[] hashCodes)
//    {
//        return new IteratorFilter<T>(
//                    iter, 
//                    new Selectable<T>(hashCodes)
//                    );
//    }
}
