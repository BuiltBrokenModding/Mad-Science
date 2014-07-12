package madscience.factory.tileentity.prefab;

import madscience.factory.MadTileEntityFactoryProduct;
import net.minecraft.nbt.NBTTagCompound;

abstract class MadTileEntityRedstone extends MadTileEntityBasePrefab
{
    /** Determines if we have redstone powering us */
    public boolean isRedstonePowered = false;

    public MadTileEntityRedstone()
    {
        super();
    }

    MadTileEntityRedstone(MadTileEntityFactoryProduct registeredMachine)
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
        
        // Checks the server world if we are currently powered by redstone.
        checkRedstonePower();
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
        // Important to call the class below us!
        super.updateEntity();

        checkRedstonePower();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("isRedstonePowered", this.isRedstonePowered);
    }
}
