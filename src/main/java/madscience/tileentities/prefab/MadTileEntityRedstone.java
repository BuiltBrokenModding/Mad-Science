package madscience.tileentities.prefab;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class MadTileEntityRedstone extends MadTileEntityBase
{
    /** Determines if we have redstone powering us */
    public boolean isRedstonePowered = false;
    
    @Override
    public void initiate()
    {
        // Checks the server world if we are currently powered by redstone.
        checkRedstonePower();
    }
    
    public boolean isRedstonePowered()
    {
        // Returns current state of redstone power to this block.
        return isRedstonePowered;
    }
    
    public void checkRedstonePower()
    {
        // Determines if there is a redstone current flowing through this block.
        isRedstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
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
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.isRedstonePowered = nbt.getBoolean("isRedstonePowered");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("isRedstonePowered", this.isRedstonePowered);
    }
}
