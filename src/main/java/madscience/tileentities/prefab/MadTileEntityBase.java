package madscience.tileentities.prefab;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

abstract class MadTileEntityBase extends TileEntity
{
    private long ticks = 0;

    /** Stores reference to our machines internal name as it should be references by the rest of MC/Forge. */
    private String machineName;

    public MadTileEntityBase()
    {
        // Note: This is used to load tile entities from NBT data only!
        super();
    }

    MadTileEntityBase(String machineName)
    {
        super();
        this.machineName = machineName;
    }

    @Override
    public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }

    @Override
    public Block getBlockType()
    {
        if (this.blockType == null)
        {
            this.blockType = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
        }

        return this.blockType;
    }

    public String getMachineInternalName()
    {
        return this.machineName;
    }

    /** Called on the TileEntity's first tick. */
    public void initiate()
    {
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Check if our internal name is empty or null.
        String machineName = this.getMachineInternalName();

        // Check if we have NBT data to solve this chicken before the egg problem.
        if (nbt.hasKey("MachineName") && (machineName == null || machineName.isEmpty()))
        {
            String savedName = nbt.getString("MachineName");
            if (savedName != null && !savedName.isEmpty())
            {
                this.setMachineName(savedName);
            }
        }
    }

    public void setMachineName(String machineName)
    {
        if (this.machineName != null && !this.machineName.isEmpty())
        {
            throw new IllegalAccessError("Unable to set machine name when it already exists!");
        }

        // Set machine name from saved NBT data.
        this.machineName = machineName;
    }

    @Override
    public void updateEntity()
    {
        if (this.ticks == 0)
        {
            this.initiate();
        }

        if (this.ticks >= Long.MAX_VALUE)
        {
            this.ticks = 1;
        }

        this.ticks++;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Save the machine name since this will be needed when recreating from NBT data.
        String machineName = this.getMachineInternalName();
        if (machineName != null && !machineName.isEmpty())
        {
            nbt.setString("MachineName", this.getMachineInternalName());
        }
    }
}
