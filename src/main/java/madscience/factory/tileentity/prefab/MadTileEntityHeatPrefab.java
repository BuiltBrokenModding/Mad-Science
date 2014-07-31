package madscience.factory.tileentity.prefab;

import madscience.factory.heat.MadHeat;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;

class MadTileEntityHeatPrefab extends MadTileEntityEnergyPrefab
{
    /** Current level of heat that the machine has accumulated while powered and active. */
    private int heatLevelValue;

    /** Maximum allowed heat value and also when the machine is considered ready. */
    private int heatLevelMaximum;
    
    /** Trigger value that will force isHeated to return true. */
    private int heatLevelTriggerValue;
    
    /** Holds reference to if we should be setting or dealing with heat levels at all. */
    private boolean heatLevelSupported = false;
    
    public MadTileEntityHeatPrefab()
    {
        super();
    }

    MadTileEntityHeatPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        
        // Apply information from factory object if we have any.
        MadHeat[] heatSupported = registeredMachine.getHeatLevelsSupported();
        
        // If there is no information then there is no heat tracking on this machine.
        if (heatSupported != null)
        {
            // Sets the flag that will save NBT data on what we will set below.
            this.heatLevelSupported = true;
            
            for (MadHeat heatSupport : heatSupported)
            {
                // TODO: Only one heat trigger system is supported at this time.
                this.heatLevelMaximum = heatSupport.getHeatLevelMaximumValue();
                this.heatLevelTriggerValue = heatSupport.getHeatLevelTriggerValue();
                this.heatLevelValue = heatSupport.getHeatLevelValue();
            }
        }
    }

    public int getHeatLevelTimeScaled(int pxl)
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return 0;
        }
        
        // Returns scaled percentage of heat level used in GUI to show temperature.
        return (int) (this.heatLevelValue * (pxl / (float)this.heatLevelMaximum));
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        // Check if this machine supports heat level tracking.
        if (nbt.hasKey("HeatSupported"))
        {
            // Read the bool that checks if we track heat levels.
            this.heatLevelSupported = nbt.getBoolean("HeatSupported");
            
            // Based on above info determine if we should track heat at all.
            if (this.heatLevelSupported)
            {
                // Amount of heat that was stored inside of this block.
                this.heatLevelValue = nbt.getInteger("HeatValue");
                
                // Trigger value.
                this.heatLevelTriggerValue = nbt.getInteger("HeatTrigger");
                
                // Maximum amount of heat this machine can take.
                this.heatLevelMaximum = nbt.getInteger("HeatMaximum");
            }
        }
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        // Save heat level information if registered machine allows it.
        if (this.heatLevelSupported)
        {
            // Determines if we load heat level information from NBT.
            nbt.setBoolean("HeatSupported", this.heatLevelSupported);
            
            // Amount of heat current stored within the block.
            nbt.setInteger("HeatValue", this.heatLevelValue);
            
            // Trigger value where we will return true for isHeated.
            nbt.setInteger("HeatTrigger", this.heatLevelTriggerValue);
            
            // Total heat level possible within this block.
            nbt.setInteger("HeatMaximum", this.heatLevelMaximum);
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    public int getHeatLevelValue()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return 0;
        }
        
        return heatLevelValue;
    }

    public void setHeatLevelValue(int heatLevelValue)
    {
        this.heatLevelValue = heatLevelValue;
    }

    public int getHeatLevelMaximum()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return 0;
        }
        
        return heatLevelMaximum;
    }

    public void setHeatLevelMaximum(int heatLevelMaximum)
    {
        this.heatLevelMaximum = heatLevelMaximum;
    }
    
    public boolean isOverheating()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return false;
        }
        
        if (this.heatLevelValue >= this.heatLevelMaximum)
        {
            return true;
        }
        
        return false;
    }
    
    public boolean isHeatAboveZero()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return false;
        }
        
        // Return true if there is any heat above zero inside the machine.
        if (this.heatLevelValue > 0)
        {
            return true;
        }
        
        return false;
    }
    
    /** Increases internal heat inside the machine until reaching the maximum and then stops. */
    public void incrementHeatValue()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return;
        }
        
        // Increase the value of the heat if possible until the maximum.
        if (this.heatLevelValue <= this.heatLevelMaximum)
        {
            this.heatLevelValue++;
        }
    }
    
    /** Decreases the temperature in the machine until reaching zero and then stops decreasing. */
    public void decreaseHeatValue()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return;
        }
        
        // Only decrease the temperature until we reach the bottom.
        if (this.heatLevelValue > 0)
        {
            this.heatLevelValue--;
        }
    }
    
    public boolean isHeatedPastTriggerValue()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return false;
        }
        
        if (this.heatLevelValue >= this.heatLevelTriggerValue)
        {
            return true;
        }
        
        return false;
    }

    public int getHeatLevelTriggerValue()
    {
        // Always return false if we don't support heat.
        if (!this.heatLevelSupported)
        {
            return 0;
        }
        
        return heatLevelTriggerValue;
    }

    public void setHeatLevelTriggerValue(int heatLevelTriggerValue)
    {
        this.heatLevelTriggerValue = heatLevelTriggerValue;
    }

    public boolean isHeatLevelSupported()
    {        
        return heatLevelSupported;
    }

    public void setHeatLevelSupported(boolean heatLevelSupported)
    {
        this.heatLevelSupported = heatLevelSupported;
    }
}
