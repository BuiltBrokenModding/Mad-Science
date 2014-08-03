package madscience.tile;

import madscience.factory.mod.MadMod;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.network.MadParticlePacket;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;

public class CnCMachineEntity extends MadTileEntityPrefab
{
    /** If we start smelting we want client GUI's to know what part they are making for effect. */
    String BOOK_DECODED = "INVALID BOOK";

    // Sound tracking variables.
    boolean clientSound_FinishedCrushing;
    boolean clientSound_InsertIronBlock;
    boolean clientSound_InvalidBook;
    boolean clientSound_PowerOn;
    boolean clientSound_PressStop;

    /** Stored value for how many pieces make up the animation process for this model. */
    private int cncModelSteps = 17;

    public CnCMachineEntity()
    {
        super();
    }

    public CnCMachineEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public CnCMachineEntity(String machineName)
    {
        super(machineName);
    }

    private boolean addBucketToInternalTank()
    {
        // Check if the input slot for filled buckets is null.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET) == null)
        {
            return false;
        }

        // Output 1 - Empty bucket returns from full one in input slot 1
        ItemStack compareEmptyBucket = new ItemStack(Item.bucketEmpty);
        ItemStack compareFilledBucket = new ItemStack(Item.bucketWater);
        if (!this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET).isItemEqual(compareFilledBucket))
        {
            return false;
        }

        // Check if output slot 1 (for empty buckets) is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) != null)
        {
            int slot1Result = this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize + compareFilledBucket.stackSize;
            boolean shouldStop = (slot1Result <= getInventoryStackLimit() && slot1Result <= compareFilledBucket.getMaxStackSize());
            if (shouldStop)
                return false;
        }

        // Check if the internal water tank has reached it
        if (this.getFluidAmount() >= this.getFluidCapacity())
        {
            return false;
        }

        // Add empty water bucket to output slot 2 GUI.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET, compareEmptyBucket.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).isItemEqual(compareEmptyBucket))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_EMPTYBUCKET).stackSize += compareEmptyBucket.stackSize;
        }

        // Add a bucket's worth of water into the internal tank.
        this.addFluidAmountByBucket(1);

        // Remove a filled bucket of water from input stack 1.
        this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_FILLEDBUCKET), 1);

        return false;
    }

    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // Check if we have a block of iron in input slot 2.
        if (!this.hasIronBlock())
        {
            return false;
        }

        // Check if there is anything already in the output slot.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null &&
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize >= 1)
        {
            return false;
        }

        // Check if we have redstone power.
        if (!this.isRedstonePowered())
        {
            return false;
        }

        // Check if we have a written book in input slot 3.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if there is fluid inside our internal tank.
        if (this.isFluidTankEmpty())
        {
            this.BOOK_DECODED = "NEED WATER";
            return false;
        }

        return true;
    }

    private void drainEnergy(boolean drainWater)
    {
        if (this.isPowered() && this.canSmelt() && this.isRedstonePowered())
        {
            // Decrease to amount of energy this item has on client and server.
            this.consumeInternalEnergy(this.getEnergyConsumeRate());

            if (drainWater)
            {
                // Decrease the amount of water in the blocks internal storage.
                this.removeFluidAmountExact(1);
            }
        }
    }

    public ItemStack getItemFromBookContents()
    {
        // Retrieve the unencoded binary string contents from the book.
        String bookContents = MadUtils.BinaryToAscii(this.getStringFromBookContents());
        
        ItemStack[] possibleReturnedItem = null;
        if (bookContents.equals("m41a barrel"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "componentPulseRifleBarrel", 1, String.valueOf(0));
        }
        
        if (bookContents.equals("m41a bolt"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "componentPulseRifleBolt", 1, String.valueOf(0));
        }
        
        if (bookContents.equals("m41a reciever"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "componentPulseRifleReciever", 1, String.valueOf(0));
        }
        
        if (bookContents.equals("m41a trigger"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "componentPulseRifleTrigger", 1, String.valueOf(0));
        }
        
        if (bookContents.equals("m41a magazine"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "pulseRifleMagazine", 16, String.valueOf(0));
        }
        
        if (bookContents.equals("m41a bullets"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "componentPulseRifleBulletCasing", 64, String.valueOf(0));
        }
        
        if (bookContents.equals("m41a grenade"))
        {
            possibleReturnedItem = MadRecipe.getItemStackFromString(MadMod.ID, "componentPulseRifleGrenadeCasing", 32, String.valueOf(0));
        }
        
        // Decode the binary string into plain text.
        if (possibleReturnedItem == null)
        {
            return null;
        }
        
        if (possibleReturnedItem != null && possibleReturnedItem[0] == null)
        {
            return null;
        }

        return possibleReturnedItem[0];
    }

    public String getStringFromBookContents()
    {
        // Depending on client or server we will get info from another place.
        String bookContents = null;
        if (this.worldObj != null && !this.worldObj.isRemote)
        {
            // SERVER

            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
            {
                return null;
            }

            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackTagCompound == null)
            {
                return null;
            }

            // Retrieve the unencoded binary string contents from the book.
            bookContents = MadUtils.getWrittenBookContents(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).stackTagCompound);
            if (bookContents == null)
            {
                return null;
            }
        }
        else if (this.worldObj != null && this.worldObj.isRemote)
        {
            // CLIENT
            if (!this.BOOK_DECODED.isEmpty())
            {
                // User has entered a valid schematic into the device.
                bookContents = this.BOOK_DECODED;
                if (bookContents == null)
                {
                    return null;
                }
            }
            else
            {
                // User has entered a invalid schematic into the device.
                bookContents = "INVALID BOOK";
            }
        }

        return bookContents;
    }

    private boolean hasIronBlock()
    {
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null)
        {
            this.clientSound_InsertIronBlock = false;
            return false;
        }

        // Compare iron block.
        ItemStack compareIronBlock = new ItemStack(Block.blockIron);
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).isItemEqual(compareIronBlock))
        {
            return true;
        }

        // Default response.
        this.clientSound_InsertIronBlock = false;
        return false;
    }
    
    private void renderAllCutBlocks()
    {
        this.setModelWorldRenderVisibilityByName("Cutblock0", true);
        this.setModelWorldRenderVisibilityByName("Cutblock1", true);
        this.setModelWorldRenderVisibilityByName("Cutblock2", true);
        this.setModelWorldRenderVisibilityByName("Cutblock3", true);
        this.setModelWorldRenderVisibilityByName("Cutblock4", true);
        this.setModelWorldRenderVisibilityByName("Cutblock5", true);
        this.setModelWorldRenderVisibilityByName("Cutblock6", true);
        this.setModelWorldRenderVisibilityByName("Cutblock7", true);
        this.setModelWorldRenderVisibilityByName("Cutblock8", true);
    }
    
    private void updateModel()
    {
        // Hide all the model pieces before we start to manipulate them.
        this.hideAllModelPieces();
        
        // Base model which will always be shown.
        this.setModelWorldRenderVisibilityByName("Base", true);
        this.setModelWorldRenderVisibilityByName("Monitor", true);
        
        // Get the amount of cooking time scaled by the number of steps we want to complete.
        int cookTime = this.getItemCookTimeScaled(17);
        if (cookTime < 1)
        {
            // Press0 - visible
            this.setModelWorldRenderVisibilityByName("Press0", true);
        }

        // LOADING IRON BLOCK
        if (this.getProgressValue() < 1 && this.hasIronBlock())
        {
            this.setModelWorldRenderVisibilityByName("CompressedBlock0", true);
        }

        // Only show different parts of CnC Machine when it is powered, active and cooking.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered())
        {
            // COMPRESSING IRON BLOCK
            // STEP 0
            if (this.getProgressValue() > 0 && cookTime <= 0)
            {
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("CompressedBlock0", true);
            }

            // STEP 1
            if (cookTime == 1)
            {
                this.setModelWorldRenderVisibilityByName("Press1", true);
                this.setModelWorldRenderVisibilityByName("CompressedBlock1", true);
            }

            // STEP 2
            if (cookTime == 2)
            {
                this.setModelWorldRenderVisibilityByName("Press2", true);
                this.setModelWorldRenderVisibilityByName("CompressedBlock2", true);
            }

            // STEP 3
            if (cookTime == 3)
            {
                this.setModelWorldRenderVisibilityByName("Press3", true);
                this.setModelWorldRenderVisibilityByName("CompressedBlock3", true);
            }

            // STEP 4
            if (cookTime == 4)
            {
                this.setModelWorldRenderVisibilityByName("Press3", true);
                this.renderAllCutBlocks();
            }

            // STEP 5
            if (cookTime == 5)
            {
                this.setModelWorldRenderVisibilityByName("Press2", true);
                this.renderAllCutBlocks();
            }

            // STEP 6
            if (cookTime == 6)
            {
                this.setModelWorldRenderVisibilityByName("Press1", true);
                this.renderAllCutBlocks();
            }

            // CUTTING STATE
            // STEP 0
            if (cookTime == 7)
            {
                // NOTE: WATER TURNS ON AT THIS STAGE
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.renderAllCutBlocks();
            }

            // STEP 1
            if (cookTime == 8)
            {
                this.renderAllCutBlocks();
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water0", true);
            }

            // STEP 2
            if (cookTime == 9)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock1", true);
                this.setModelWorldRenderVisibilityByName("Cutblock2", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock3", true);
                this.setModelWorldRenderVisibilityByName("Cutblock4", true);
                this.setModelWorldRenderVisibilityByName("Cutblock5", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock6", true);
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water1", true);
            }

            // STEP 3
            if (cookTime == 10)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock2", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock3", true);
                this.setModelWorldRenderVisibilityByName("Cutblock4", true);
                this.setModelWorldRenderVisibilityByName("Cutblock5", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock6", true);
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water2", true);
            }

            // STEP 4
            if (cookTime == 11)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock3", true);
                this.setModelWorldRenderVisibilityByName("Cutblock4", true);
                this.setModelWorldRenderVisibilityByName("Cutblock5", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock6", true);
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water3", true);
            }

            // STEP 5
            if (cookTime == 12)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock4", true);
                this.setModelWorldRenderVisibilityByName("Cutblock5", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock6", true);
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water4", true);
            }

            // STEP 6
            if (cookTime == 13)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock5", true);
                
                this.setModelWorldRenderVisibilityByName("Cutblock6", true);
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water5", true);
                //this.setModelWorldRenderVisibilityByName("Water3", true);
            }

            // STEP 7
            if (cookTime == 14)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock6", true);
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water6", true);
            }

            // STEP 8
            if (cookTime == 15)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock7", true);
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water7", true);
            }

            // STEP 9
            if (cookTime == 16)
            {
                this.setModelWorldRenderVisibilityByName("Cutblock8", true);
                
                this.setModelWorldRenderVisibilityByName("Press0", true);
                this.setModelWorldRenderVisibilityByName("Water8", true);
            }

            // STEP 10 - DONE!
            if (cookTime >= 16)
            {
                this.setModelWorldRenderVisibilityByName("Press0", true);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Sound tracking.
        this.clientSound_FinishedCrushing = nbt.getBoolean("FinishCrushing");
        this.clientSound_InsertIronBlock = nbt.getBoolean("InsertIronBlock");
        this.clientSound_InvalidBook = nbt.getBoolean("InvalidBook");
        this.clientSound_PowerOn = nbt.getBoolean("PowerOn");
        this.clientSound_PressStop = nbt.getBoolean("PressStop");
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        // Converts input item into resulting weapon component part.
        if (this.canSmelt())
        {
            // Output 2 - Weapon component that was crafted from the iron block.
            ItemStack smeltingResult = getItemFromBookContents();
            if (smeltingResult == null)
            {
                return;
            }
            
            // Adds the smelted weapon component into the output slot.
            if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
            {
                this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, smeltingResult.copy());
            }
            else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(smeltingResult))
            {
                this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += smeltingResult.stackSize;
            }

            // Removes the source iron block used in the construction of the weapon part.
            this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1), 1);
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Get the scaled percentage of our progress based on how many steps we have.
        int cookTimeScaled = this.getItemCookTimeScaled(cncModelSteps);
        
        // Active state has many textures based on item cook progress.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && cookTimeScaled > 7)
        {
            // Play particle effect of water splashing down.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadMod.PACKET_SEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("splash", 0.5D + this.xCoord, this.yCoord + 0.5D, this.zCoord + 0.5D,
                    this.worldObj.rand.nextFloat(), this.worldObj.rand.nextFloat() + 0.5F, this.worldObj.rand.nextFloat()).makePacket());

            // Plays water jet-stream animation to look like iron block is being cut into shape.
            if (this.getAnimationCurrentFrame() <= 6 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/work" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 7)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
        }
        else if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && cookTimeScaled < 7)
        {
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/ready.png");
        }
        else if (!this.canSmelt() && this.isPowered() && !this.isRedstonePowered())
        {
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/powered.png");
        }
        else if (!this.canSmelt() && this.isPowered() && this.isRedstonePowered())
        {
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/ready.png");
        }
        else
        {
            // Idle state single texture.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/off.png");
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        // Drain energy if we are operating but not fluid.
        this.drainEnergy(false);

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Checks to see if we can add a bucket from input slot.
            this.addBucketToInternalTank();

            // Get the scaled percentage of our progress based on how many steps we have.
            int cookTimeScaled = this.getItemCookTimeScaled(17);

            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.isPowered())
            {
                ItemStack smeltingResult = getItemFromBookContents();
                if (smeltingResult == null)
                {
                    this.BOOK_DECODED = "INVALID BOOK";
                    this.setProgressValue(0);
                    this.clientSound_FinishedCrushing = false;
                    this.clientSound_PressStop = false;
                }
                else
                {                    
                    // For displaying part name in the GUI for clients.
                    this.BOOK_DECODED = getStringFromBookContents();
                    
                    // We know the book inserted is good.
                    this.clientSound_InvalidBook = false;

                    // New item pulled from cooking stack to be processed.
                    this.setProgressMaximum(1000);

                    // Increments the timer to kickstart the cooking loop.
                    this.incrementProgressValue();

                    // Drain only power at this time.
                    this.drainEnergy(false);
                }
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.isPowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Drain only power until the water is turned on.
                this.drainEnergy(cookTimeScaled >= 7);

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getProgressValue() >= this.getProgressMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.smeltItem();
                    this.clientSound_FinishedCrushing = false;
                    this.clientSound_PressStop = false;
                    this.setProgressValue(0);
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
                this.clientSound_PressStop = false;
                this.clientSound_FinishedCrushing = false;
            }
        }
        
        // Update the visibility of various pieces of the machine based on progress.
        updateModel();
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
        
        // Play sound of a iron block being inserted into the machine.
        if (this.hasIronBlock() && !this.clientSound_InsertIronBlock)
        {
            this.clientSound_InsertIronBlock = true;
            this.playSoundByName("InsertIronBlock");
        }
        
        // Play a sound of redstone signal activating the machine and making it active.
        if (this.isRedstonePowered() && !this.clientSound_PowerOn)
        {
            this.clientSound_PowerOn = true;
            this.playSoundByName("PowerOn");
        }
        else if (!this.isRedstonePowered() && this.clientSound_PowerOn)
        {
            // Power was set to being on while it was off. This resets it!
            this.clientSound_PowerOn = false;
            
            // Since the machine lost it's current power state we reset this alarm.
            this.clientSound_InvalidBook = false;
        }
        
        // The next batch of sounds are only played while the machine is operational.
        if (this.isPowered() && this.isRedstonePowered() && this.canSmelt())
        {
            // Play sound telling player inserted book is invalid.
            if (this.BOOK_DECODED.contains("INVALID") && !this.clientSound_InvalidBook)
            {
                this.clientSound_InvalidBook = true;
                this.playSoundByName("InvalidBook");
            }
            else if (!this.BOOK_DECODED.contains("INVALID") && this.clientSound_InvalidBook)
            {
                this.clientSound_InvalidBook = false;
            }
            
            // Get the scaled percentage of our progress based on how many steps we have.
            int cookTimeScaled = this.getItemCookTimeScaled(cncModelSteps);
            
            // --------------
            // CRUSHING PHASE
            // --------------
            if (cookTimeScaled <= 6 && this.getProgressValue() > 0 && !this.BOOK_DECODED.contains("INVALID"))
            {
                // Background sound played while crushing iron block every 3 seconds.
                if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS * 3L == 0L)
                {
                    this.playSoundByName("PressingWork");
                }
                
                // Played while iron block is being pressed every 2 seconds.
                if (cookTimeScaled < 4 && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS * 2L == 0L)
                {
                    this.playSoundByName("Press");
                }
                
                // Play a sound to indicate the end of the crushing phase.
                if (cookTimeScaled == 4 && !this.clientSound_PressStop)
                {
                    this.clientSound_PressStop = true;
                    this.playSoundByName("PressStop");
                    
                    // Spawn smoke particles to simulate the block being heated from all the pressure.
                    PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadMod.PACKET_SEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("largesmoke", 0.5D + this.xCoord, this.yCoord + 0.666D, this.zCoord + 0.5D, worldObj.rand.nextFloat(),
                            worldObj.rand.nextFloat() + 3.0D, worldObj.rand.nextFloat()).makePacket());
                }
            }
            
            // -------------------
            // WATER CUTTING PHASE
            // -------------------
            if (cookTimeScaled >= 7 && this.getProgressValue() <= this.getProgressMaximum())
            {
                // Play a sound to indicate the crusher is powering down and water jets are powering on.
                if (cookTimeScaled == 7 && !this.clientSound_FinishedCrushing)
                {
                    this.clientSound_FinishedCrushing  = true;
                    this.playSoundByName("FinishCrushing");
                }
                
                // Background sound played while cutting block with water every 4 seconds.
                if (cookTimeScaled <= 15 && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS * 4L == 0L)
                {
                    this.playSoundByName("WaterWork");
                }
                
                // Played while water is being splashed onto the iron block every 3.6 seconds.
                if (cookTimeScaled > 7 && cookTimeScaled <= 15 && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS * 3.6F == 0L)
                {
                    this.playSoundByName("WaterFlow");
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Sound tracking.
        nbt.setBoolean("FinishCrushing", this.clientSound_FinishedCrushing);
        nbt.setBoolean("InsertIronBlock", this.clientSound_InsertIronBlock);
        nbt.setBoolean("InvalidBook", this.clientSound_InvalidBook);
        nbt.setBoolean("PowerOn", this.clientSound_PowerOn);
        nbt.setBoolean("PressStop", this.clientSound_PressStop);
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
