package madscience.tileentities.prefab;

import net.minecraft.nbt.NBTTagCompound;

public class MadTileEntity extends MadTileEntityEnergy
{
    public MadTileEntity()
    {
        super();
    }

    public MadTileEntity(long capacity, long maxReceive, long maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
    }
}
