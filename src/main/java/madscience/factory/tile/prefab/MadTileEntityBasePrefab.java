package madscience.factory.tile.prefab;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.tile.MadTileEntityFactoryProduct;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

abstract class MadTileEntityBasePrefab extends TileEntity
{
    /** Keeps track of the number of ticks that have passed since the tile entities creation. */
    private long ticks = 0;

    /** Stores reference to our registered machine as it should be referenced by the rest of MC/Forge. */
    private MadTileEntityFactoryProduct registeredMachine;
    
    /** Stores reference to just machine name since when loading from NBT data we need to know what we are. */
    private String registeredMachineName;

    public MadTileEntityBasePrefab()
    {
        // Note: This is used to load tile entities from NBT data only!
        super();
    }

    MadTileEntityBasePrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super();
        this.registeredMachine = registeredMachine;
        this.registeredMachineName = registeredMachine.getMachineName();
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
        return this.registeredMachineName;
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
                this.registeredMachineName = savedName;
            }
        }
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
            nbt.setString("MachineName", this.registeredMachineName);
        }
    }

    public MadTileEntityFactoryProduct getRegisteredMachine()
    {
        // Only query and re-create the registered machine if we actually need it.
        if (this.registeredMachine == null)
        {
            MadTileEntityFactoryProduct reloadedProduct = MadTileEntityFactory.instance().getMachineInfo(this.registeredMachineName);
            this.registeredMachine = reloadedProduct;
            this.registeredMachineName = reloadedProduct.getMachineName();
        }
        
        return this.registeredMachine;
    }
}
