package madscience.factory.heat;

import com.google.gson.annotations.Expose;

public class MadHeat
{
    /** Starting amount of heat that the machine will start off with. */
    @Expose
    private int heatLevelValue;
    
    /** Amount of heat the machine will need to gather before it will be considered 'heated'. */
    @Expose
    private int heatLevelTriggerValue;
    
    /** Maximum amount of heat that a given machine can handle in total. */
    @Expose
    private int heatLevelMaximumValue;
    
    public MadHeat(int heatStartValue, int heatTriggerValue, int heatMaximumValue)
    {
        super();
        
        this.heatLevelValue = heatStartValue;
        this.heatLevelTriggerValue = heatTriggerValue;
        this.heatLevelMaximumValue = heatMaximumValue;
    }

    public int getHeatLevelValue()
    {
        return heatLevelValue;
    }

    public int getHeatLevelTriggerValue()
    {
        return heatLevelTriggerValue;
    }

    public int getHeatLevelMaximumValue()
    {
        return heatLevelMaximumValue;
    }
}
