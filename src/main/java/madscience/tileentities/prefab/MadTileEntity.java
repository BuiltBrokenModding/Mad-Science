package madscience.tileentities.prefab;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.api.CompatibilityModule;
import universalelectricity.api.UniversalClass;
import universalelectricity.api.electricity.IVoltageInput;
import universalelectricity.api.electricity.IVoltageOutput;
import universalelectricity.api.energy.EnergyStorageHandler;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;
import universalelectricity.api.vector.Vector3;
import universalelectricity.api.vector.VectorHelper;

@UniversalClass
public abstract class MadTileEntity extends MadTileEntityRedstone implements IEnergyInterface, IEnergyContainer
{
    protected EnergyStorageHandler energy;

    public MadTileEntity()
    {
        this(0);
    }

    public MadTileEntity(long capacity)
    {
        this(capacity, capacity, capacity);
    }

    public MadTileEntity(long energyCapacity, long transferRate)
    {
        energy = new EnergyStorageHandler(energyCapacity, transferRate);
    }

    public MadTileEntity(long capacity, long maxReceive, long maxExtract)
    {
        energy = new EnergyStorageHandler(capacity, maxReceive, maxExtract);
    }

    public void consumeEnergy(long amount)
    {
        if (!this.energy.isEmpty())
        {
            this.energy.setEnergy(Math.max((this.energy.getEnergy() - amount), 0));
        }
    }

    public void produceEnergy(long amount)
    {
        if (!this.energy.isFull())
        {
            this.energy.setEnergy(this.energy.getEnergy() + amount);
        }
    }

    protected void produce()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() > 0)
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long used = CompatibilityModule.receiveEnergy(tileEntity, direction.getOpposite(), this.energy.extractEnergy(this.energy.getEnergy(), false), true);
                    this.energy.extractEnergy(used, true);
                }
            }
        }
    }
    
    protected void consume()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            if (this.energy.getEnergy() < this.energy.getEnergyCapacity())
            {
                TileEntity tileEntity = new Vector3(this).translate(direction).getTileEntity(this.worldObj);

                if (tileEntity != null)
                {
                    long maxRecieve = this.energy.getMaxReceive();
                    long used = CompatibilityModule.extractEnergy(tileEntity, direction.getOpposite(), this.energy.receiveEnergy(maxRecieve, false), true);
                    this.energy.receiveEnergy(used, true);
                }
            }
        }
    }

    public int getPowerRemainingScaled(int prgPixels)
    {
        Double result = Long.valueOf(this.getEnergy(ForgeDirection.UNKNOWN)).doubleValue() * Long.valueOf(prgPixels).doubleValue() / Long.valueOf(this.getEnergyCapacity(ForgeDirection.UNKNOWN)).doubleValue();
        return result.intValue();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        // Accept energy if we are allowed to do so.
        if (this.energy.checkReceive())
        {
            this.consume();
        }
    }

    @Override
    public long onReceiveEnergy(ForgeDirection from, long receive, boolean doReceive)
    {
        return this.energy.receiveEnergy(receive, doReceive);
    }

    @Override
    public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract)
    {
        return this.energy.extractEnergy(extract, doExtract);
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
    }

    @Override
    public boolean canConnect(ForgeDirection direction, Object obj)
    {
        if (obj instanceof IEnergyInterface)
        {
                if (direction == null || direction.equals(ForgeDirection.UNKNOWN))
                {
                        return false;
                }

                return this.getInputDirections().contains(direction) || this.getOutputDirections().contains(direction);
        }
        return false;
    }

    /** The electrical input direction.
     * 
     * @return The direction that electricity is entered into the tile. Return null for no input. By default you can accept power from all sides. */
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }

    /** The electrical output direction.
     * 
     * @return The direction that electricity is output from the tile. Return null for no output. By default it will return an empty EnumSet. */
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public long getEnergyCapacity(ForgeDirection from)
    {
        return this.energy.getEnergyCapacity();
    }

    @Override
    public void setEnergy(ForgeDirection from, long energy)
    {
        this.energy.setEnergy(energy);
    }

    public void setEnergyCapacity(long energy)
    {
        this.energy.setCapacity(energy);
    }

    public boolean isPowered()
    {
        return this.energy.getEnergy() > 0;
    }

    @Override
    public long getEnergy(ForgeDirection from)
    {
        return this.energy.getEnergy();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.energy.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        this.energy.writeToNBT(nbt);
    }
}
