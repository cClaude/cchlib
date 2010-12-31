package cx.ath.choisnet.tools.duplicatefiles;

public class ConfigData
{
	/* @serial */
	private int deleteFinalDisplay = 3000;
	/* @serial */
	private int deleteSleepDisplay = 100;

	public ConfigData()
	{
		//empty for the moment
		//TODO load from disk last values
	}

	/**
	 * @return the deleteFinalDisplay
	 */
	public int getDeleteFinalDisplay() {
		return deleteFinalDisplay;
	}

	/**
	 * @param deleteFinalDisplay the deleteFinalDisplay to set
	 */
	public void setDeleteFinalDisplay(int deleteFinalDisplay)
	{
		this.deleteFinalDisplay = deleteFinalDisplay;
	}

	/**
	 * @return the deleteSleepDisplay
	 */
	public int getDeleteSleepDisplay() 
	{
		return deleteSleepDisplay;
	}

	/**
	 * @param deleteSleepDisplay the deleteSleepDisplay to set
	 */
	public void setDeleteSleepDisplay(int deleteSleepDisplay) 
	{
		this.deleteSleepDisplay = deleteSleepDisplay;
	}
	
}
