package cx.ath.choisnet.tools.duplicatefiles;

public class ConfigData
{
    /* @serial */
    private int deleteSleepDisplay = 100;
    /* @serial */
    private int deleteSleepDisplayMaxEntries = 50;
//  /* @serial */
//  private int deleteFinalDisplay = 3000;

    public ConfigData()
    {
        //empty for the moment
        //TODO load from disk last values
    }

//  /**
//   * @return the deleteFinalDisplay
//   */
//  public int getDeleteFinalDisplay() {
//      return deleteFinalDisplay;
//  }
//
//  /**
//   * @param deleteFinalDisplay the deleteFinalDisplay to set
//   */
//  public void setDeleteFinalDisplay(int deleteFinalDisplay)
//  {
//      this.deleteFinalDisplay = deleteFinalDisplay;
//  }

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

    /**
     * @return the deleteSleepDisplayMaxEntries
     */
    public int getDeleteSleepDisplayMaxEntries()
    {
        return deleteSleepDisplayMaxEntries;
    }

    /**
     * @param deleteSleepDisplayMaxEntries the deleteSleepDisplayMaxEntries to set
     */
    public void setDeleteSleepDisplayMaxEntries( int deleteSleepDisplayMaxEntries )
    {
        this.deleteSleepDisplayMaxEntries = deleteSleepDisplayMaxEntries;
    }

}
