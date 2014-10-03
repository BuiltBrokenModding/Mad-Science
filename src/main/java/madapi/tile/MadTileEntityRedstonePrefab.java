package madapi.tile;

import madapi.product.MadTileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;

abstract class MadTileEntityRedstonePrefab extends MadTileEntityBasePrefab
{
    /** Determines if we have redstone powering us */
    private boolean isRedstonePowered = false;

    public MadTileEntityRedstonePrefab()
    {
        super();
    }

    MadTileEntityRedstonePrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public void checkRedstonePower()
    {
        // Determines if there is a redstone current flowing through this block.
        isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    public boolean isRedstonePowered()
    {
        // Returns current state of redstone power to this block.
        return isRedstonePowered;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.isRedstonePowered = nbt.getBoolean("isRedstonePowered");
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        if (!this.worldObj.isRemote)
        {
            // Determine if this machine currently has redstone signal applied to it.
            this.checkRedstonePower();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("isRedstonePowered", this.isRedstonePowered);
    }
}
