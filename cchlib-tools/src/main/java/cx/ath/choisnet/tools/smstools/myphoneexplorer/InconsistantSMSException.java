package cx.ath.choisnet.tools.smstools.myphoneexplorer;

/**
 * 
 */
public class InconsistantSMSException extends Exception 
{
    private static final long serialVersionUID = 1L;
    private String fieldName;
    private SMS previousSMS;
    private SMS currentSMS;

    public InconsistantSMSException( String fieldName, SMS previousSMS, SMS currentSMS )
    {
        this.fieldName = fieldName;
        this.previousSMS = previousSMS;
        this.currentSMS = currentSMS;
    }

    @Override
    public String getMessage()
    {
        return "Error inconsistant fields ["
                + this.fieldName
                + "]=["
                + this.previousSMS
                + '/'
                + this.currentSMS
                + ']';
    }
}
