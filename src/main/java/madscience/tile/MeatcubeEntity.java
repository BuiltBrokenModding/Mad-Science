package madscience.tile;

import madscience.MadFluids;
import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import madscience.factory.model.MadModelFile;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.sounds.MadSound;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.util.MadUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class MeatcubeEntity extends MadTileEntityPrefab
{
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
        
        // Meatcube wants damage value to be set to max and count down.
        if (this.isDamageSupported())
        {
            this.setDamageValue(this.getDamageMaximum());
        }
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
                // If we are not under the minecraft or item stack limit for output slot 2 then stop here.
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
        
        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            this.updateWorldModel();
            
            // Checks to see if we can add a bucket from input slot into internal tank.
            this.addBucketToInternalTank();
            
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
        if (this.getDamageValue() < this.getDamageMaximum())
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
                if (this.getDamageValue() < this.getDamageMaximum())
                {
                    this.increaseDamageValue();

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

            // Set the flag that triggers animation to play.
            this.setPlayingAnimation(true);
        }
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
        
        // Play the sound of a heartbeat every few seconds.
        if (worldObj.getWorldTime() % ((this.getDamageValue() * MadUtils.SECOND_IN_TICKS) + MadUtils.SECOND_IN_TICKS) == 0L)
        {
            //this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MeatcubeSounds.MEATCUBE_HEARTBEAT, ((1.0F / this.currentMeatCubeDamageValue) + 0.42F), this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void onBlockLeftClick(World world, int x, int y, int z, EntityPlayer player)
    {
        super.onBlockLeftClick(world, x, y, z, player);
        
        // Remove some health from the block if we can.
        if (this.getDamageValue() <= this.getDamageMaximum() && this.getDamageValue() > 0)
        {
            this.decreaseDamageValue();

            // Spawn raw chicken, steak, and pork when meatcube is hit.
            ItemStack itemstack = new ItemStack(Item.beefRaw, 1);
            float foodTypeRoll = this.worldObj.rand.nextFloat();
            int foodAmountRoll = this.worldObj.rand.nextInt(5);

            // Prevent zero food spawn.
            if (foodAmountRoll <= 0)
            {
                foodAmountRoll = 1;
            }

            // Determine what kind of food will spawn from random roll.
            if (foodTypeRoll <= 0.2F)
            {
                // Beef
                itemstack = new ItemStack(Item.beefRaw, foodAmountRoll);
            }
            else if (foodTypeRoll <= 0.5F)
            {
                // Chicken
                itemstack = new ItemStack(Item.chickenRaw, foodAmountRoll);
            }
            else if (foodTypeRoll <= 0.8F)
            {
                // Pork
                itemstack = new ItemStack(Item.porkRaw, foodAmountRoll);
            }

            if (itemstack != null)
            {
                float f = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.worldObj.rand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0)
                {
                    int k1 = this.worldObj.rand.nextInt(21) + 10;

                    if (k1 > itemstack.stackSize)
                    {
                        k1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= k1;
                    EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.worldObj.rand.nextGaussian() * f3;
                    entityitem.motionY = (float) this.worldObj.rand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float) this.worldObj.rand.nextGaussian() * f3;

                    // Spawn the randomly chosen food item in a randomly chosen direction.
                    world.spawnEntityInWorld(entityitem);

                    // Play a cow mooaning sound, but not every single time because that is annoying.
                    if (world.rand.nextBoolean() && world.rand.nextInt(10) < 5)
                    {
                        MadSound mooaning = MadMod.getSoundByName("Moo");
                        if (mooaning != null)
                        {
                            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, mooaning.getSoundNameWithoutExtension(), 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
                        }
                    }
                }

                // Prevent counter from going below zero.
                if (this.getDamageValue() < 0)
                {
                    this.setDamageValue(0);
                }
            }
        }
    }
    
    private void updateWorldModel()
    {
//        // Hide all the pieces by default and show them based on damage value.
//        for (MadModelFile modelReference : this.getModelRenderingInfo().values())
//        {
//            this.setModelWorldRenderVisibilityByName(modelReference.getModelName(), false);
//        }
//        
//        // Base meatcube with piece zero always shows.
//        this.setModelWorldRenderVisibilityByName("meatCube", true);
//        
//        // Display different chunks of the model based on internal health value.
//        if (this.getDamageValue() >= 1)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube1", true);
//        }
//
//        if (this.getDamageValue() >= 2)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube2", true);
//        }
//
//        if (this.getDamageValue() >= 3)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube3", true);
//        }
//
//        if (this.getDamageValue() >= 4)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube4", true);
//        }
//
//        if (this.getDamageValue() >= 5)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube5", true);
//        }
//
//        if (this.getDamageValue() >= 6)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube6", true);
//        }
//
//        if (this.getDamageValue() >= 7)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube7", true);
//        }
//
//        if (this.getDamageValue() >= 8)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube8", true);
//        }
//
//        if (this.getDamageValue() >= 9)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube9", true);
//        }
//
//        if (this.getDamageValue() >= 10)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube10", true);
//        }
//
//        if (this.getDamageValue() >= 11)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube11", true);
//        }
//
//        if (this.getDamageValue() >= 12)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube12", true);
//        }
//
//        if (this.getDamageValue() >= 13)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube13", true);
//        }
//
//        if (this.getDamageValue() >= 14)
//        {
//            this.setModelWorldRenderVisibilityByName("meatCube14", true);
//        }
    }
}
