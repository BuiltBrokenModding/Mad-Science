package madscience.tile;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.mod.MadMod;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.util.MadUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class MeatcubeEntity extends MadTileEntityPrefab
{
    /** Stores maximum health level of this meat cube */
    int currentMaximumMeatCubeDamage = 14;

    /** Stores current health level of this meat cube so we can display levels of damage based on it */
    int currentMeatCubeDamageValue = currentMaximumMeatCubeDamage;

    /** Last amount of ticks waited before playing an animation. */
    private long lastAnimTriggerTime = 42L;

    /** Last known number of frames played. */
    private int lastAnimFrameCount = 9;
    
    public MeatcubeEntity()
    {
        super();
    }

    public MeatcubeEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public MeatcubeEntity(String machineName)
    {
        super(machineName);
    }

    private void addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET) == null)
        {
            return;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        ItemStack bucketEmpty = new ItemStack(Item.bucketEmpty);
        ItemStack bucketMutantDNA = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);

        // Check if input slot 1 is a filled bucket.
        if (!this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET).isItemEqual(bucketMutantDNA))
        {
            // Input slot 1 was not a filled bucket.
            return;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize + bucketEmpty.stackSize;
            boolean underStackLimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= bucketEmpty.getMaxStackSize());
            if (!underStackLimit)
            {
                // If we are not under the minecraft or item stack limit for
                // output slot 2 then stop here.
                return;
            }
        }

        // Check if the internal water tank has reached capacity
        if (this.getFluidAmount() >= this.getFluidCapacity())
        {
            return;
        }

        // Add empty water bucket to output slot 1 GUI.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, bucketEmpty.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).isItemEqual(bucketEmpty))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize += bucketEmpty.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        this.addFluidAmountByBucket(1);

        // Play a regrowing noise when we manually add a bucket of water into the meatcube.
        //this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_REGROW, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        MadMod.LOGGER.info("internalWaterTank() " + this.getFluidAmount());

        // Remove a filled bucket of water from input stack 1.
        this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET), 1);

        return;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        super.isItemValidForSlot(slot, items);
        
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET))
        {
            // Input slot 1 - Filled bucket.
            ItemStack compareInputSlot = new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM);
            if (compareInputSlot.isItemEqual(items))
            {
                return true;
            }
        }

        // Input slot 2 - Empty bucket.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET))
        {
            ItemStack compareOutputSlot = new ItemStack(Item.bucketEmpty);
            if (compareOutputSlot.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Current health of our meatcube which will be used to indicate model level.
        this.currentMeatCubeDamageValue = nbt.getInteger("MeatLevel");

        // Current number of ticks we should wait until playing an animation.
        // Note: We ensure this is not restored to zero by old versions.
        this.lastAnimTriggerTime = Math.max(nbt.getLong("LastAnimTriggerTime"), MadUtils.SECOND_IN_TICKS);

        // Last number of frames that were played.
        this.lastAnimFrameCount = nbt.getInteger("LastAnimFrameCount");
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Checks to see if we can add a bucket from input slot into internal tank.
            addBucketToInternalTank();
            
            // Changes texture on model that should be displayed based on state.
            updateAnimation();

            // Plays sounds idly and at random times.
            updateSound();


            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt())
            {
                // New item pulled from cooking stack to be processed, check how long this item will take to cook.
                this.setProgressMaximum(200);

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.canSmelt())
            {
                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getProgressValue() >= this.getProgressMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.setProgressValue(0);
                    this.smeltItem();
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
            }

            // Send update about tile entity status to all players around us.            this.sendUpdatePacket();
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Current level of damage that meat cube has stored on it.
        nbt.setInteger("MeatLevel", this.currentMeatCubeDamageValue);

        // Current number of ticks we should wait until playing an animation.
        nbt.setLong("LastAnimTriggerTime", this.lastAnimTriggerTime);

        // Last number of frames that were played.
        nbt.setInteger("LastAnimFrameCount", this.lastAnimFrameCount);
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if meat cube needs healing or not.
        if (this.currentMeatCubeDamageValue < this.currentMaximumMeatCubeDamage)
        {
            return true;
        }

        // Check if there is fluid inside our internal tank.
        if (this.getFluidAmount() < (FluidContainerRegistry.BUCKET_VOLUME / 4))
        {
            return false;
        }
        return false;
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        // Converts input item into result item along with waste items.
        if (this.canSmelt())
        {
            // Decrease the amount of water in the blocks internal storage.
            if (this.removeFluidAmountExact((FluidContainerRegistry.BUCKET_VOLUME / 4)))
            {
                // Re-grow some of the meat cube (heal it).
                if (this.currentMeatCubeDamageValue < this.currentMaximumMeatCubeDamage)
                {
                    this.currentMeatCubeDamageValue++;

                    // Play a disgusting sound of the meatcube regrowing.
                    //this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_REGROW, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
                }
            }
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Check if we should be iterating our frame count.
        if (this.isPlayingAnimation() && this.getAnimationCurrentFrame() < lastAnimFrameCount && worldObj.getWorldTime() % 5L == 0L)
        {
            // Load this texture onto the entity.
            this.setTextureRenderedOnModel("models/" + MadFurnaces.MEATCUBE_INTERNALNAME + "/meatcube_" + this.getAnimationCurrentFrame() + ".png");
            this.incrementAnimationCurrentFrame();
        }
        else if (this.isPlayingAnimation() && this.getAnimationCurrentFrame() >= lastAnimFrameCount)
        {
            this.setAnimationCurrentFrame(0);
            this.setPlayingAnimation(false);
        }

        // Randomly pick a number of frames and time to wait to play them.
        if (!this.isPlayingAnimation() && worldObj.getWorldTime() % lastAnimTriggerTime == 0L)
        {
            // Get how long we should wait until next animation.
            // Note: This cannot be zero!
            lastAnimTriggerTime = Math.max(this.worldObj.rand.nextInt(666), MadUtils.SECOND_IN_TICKS);

            // Get how many frames of animation we should play this time.
            lastAnimFrameCount = this.worldObj.rand.nextInt(9);
            if (lastAnimFrameCount <= 2)
            {
                // Ensure there are always a few frames of animation played.
                lastAnimFrameCount = 9;
            }

            //this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_IDLE, 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

            // Set the flag that triggers animation to play.
            this.setPlayingAnimation(true);
        }
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
        
        // Play the sound of a heartbeat every few seconds.
        if (worldObj.getWorldTime() % ((this.currentMeatCubeDamageValue * MadUtils.SECOND_IN_TICKS) + MadUtils.SECOND_IN_TICKS) == 0L)
        {
            //this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_HEARTBEAT, ((1.0F / this.currentMeatCubeDamageValue) + 0.42F), this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }
}
