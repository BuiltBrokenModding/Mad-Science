package madscience.tile;

import madscience.factory.item.prefab.MadItemPrefab;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CryofreezerEntity extends MadTileEntityPrefab
{
    public CryofreezerEntity()
    {
        super();
    }

    public CryofreezerEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public CryofreezerEntity(String machineName)
    {
        super(machineName);
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if we have a valid fuel block to keep freezer cold.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            return false;
        }

        // Check for cold items or blocks with snow or ice in name.
        if (this.isItemUsedInInputRecipes(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)))
        {
            this.setProgressMaximum(200);
            // MadMod.logger.info("canSmelt() SNOWBALL ACCEPTED");
            return true;
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Loop through all of the storage items and see if we can heal them.
            for (int i = 1; i < this.getInputSlotCount(); ++i)
            {
                if (this.getStackInSlot(i) != null)
                {
                    // Check if we need to heal ourselves.
                    int dmg = this.getStackInSlot(i).getItemDamage();

                    // Heal ourselves for being inside a powered and equipped freezer.
                    if (this.getStackInSlot(i) != null && this.getStackInSlot(i).getItem() instanceof MadItemPrefab && dmg <= this.getStackInSlot(i).getMaxDamage())
                    {
                        this.getStackInSlot(i).setItemDamage(dmg - 1);

                        // Debugging message.
                        //MadMod.LOGGER.info("WORLD(" + this.getStackInSlot(i).getUnlocalizedName() + "): " + this.getStackInSlot(i).getItemDamage());
                    }
                }
            }

            // Remove one of the cooling blocks that is healing the needles and DNA samples.
            this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1), 1);
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && isPowered())
        {
            // Load this texture onto the entity.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/powered.png");
        }
        else
        {
            // Idle state single texture.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
        }
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        // Decrease to amount of energy this item has on client and server.
        if (this.isPowered() && this.canSmelt() && this.worldObj.rand.nextBoolean())
        {
            // Power consumption is not every tick but random.
            this.consumeInternalEnergy(this.getEnergyConsumeRate());
        }

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                // New item pulled from cooking stack to be processed, check how long this item will take to cook.
                this.setProgressMaximum(200);

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.isPowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getProgressValue() >= this.getProgressMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.setProgressValue(0);
                    this.smeltItem();
                    this.setInventoriesChanged();
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void onBlockRightClick(World world, int x, int y, int z, EntityPlayer par5EntityPlayer)
    {
        super.onBlockRightClick(world, x, y, z, par5EntityPlayer);
    }

    @Override
    public void onBlockLeftClick(World world, int x, int y, int z, EntityPlayer player)
    {
        super.onBlockLeftClick(world, x, y, z, player);
    }
}
