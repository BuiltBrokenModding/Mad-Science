package madscience.tile.cryotube;

import java.util.EnumSet;

import madscience.MadConfig;
import madscience.MadEntities;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.items.datareel.ItemDataReelEmpty;
import madscience.items.memories.CombinedMemoryMonsterPlacer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CryotubeEntity extends MadTileEntityPrefab implements ISidedInventory, IInventory
{
    private static final int[] slots_bottom = new int[]
    { 2, 1 };

    private static final int[] slots_sides = new int[]
    { 1 };
    private static final int[] slots_top = new int[]
    { 0 };

    private ItemStack[] cryotubeInput = new ItemStack[3];

    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] cryotubeOutput = new ItemStack[2];

    /** Path to texture that we would like displayed on this block. */
    String TEXTURE = "models/" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/off.png";

    /** Current frame of animation we should use to display in world. */
    private int curFrame;

    int hatchTimeCurrentValue;

    // Hatch time.
    int hatchTimeMaximum;

    // Subject Neural Activity.
    int neuralActivityMaximum;
    int neuralActivityValue;

    /** Determines if we currently should be playing animation frames every tick or not. */
    private boolean shouldPlay;
    int subjectCurrentHealth;

    /** Keeps track of what state we are supposed to be in. */
    private boolean subjectIsAlive = false;
    // Subject Health.
    int subjectMaximumHealth;

    public CryotubeEntity()
    {
        super(MadFurnaces.CRYOTUBE_INTERNALNAME);
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 3 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Memory data reel.
            return true;
        }
        else if (slot == 4 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Rotten flesh.
            return true;
        }

        // Default response is no.
        return false;
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int slot, ItemStack items, int side)
    {
        return this.isItemValidForSlot(slot, items);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    @Override
    public boolean canSmelt()
    {
        // Check if we have water bucket and dirty needles in input slots and
        // that our internal tank has fluid.
        if (cryotubeInput[0] == null || cryotubeInput[1] == null)
        {
            return false;
        }

        // Check if input slot 1 is a Spawn egg
        if (this.cryotubeInput[0] == null)
        {
            return false;
        }

        // Check if input slot 2 is a data reel or memory reel.
        if (this.cryotubeInput[1] == null)
        {
            return false;
        }

        // Check if output slots are empty.
        if (this.cryotubeOutput[0] == null)
        {
            return true;
        }

        // Check if input slot 2 matches output slot 1.
        if (this.cryotubeInput[1].isItemEqual(cryotubeOutput[0]))
        {
            return false;
        }

        // Check if we are full of rotten flesh.
        if (cryotubeOutput != null && cryotubeOutput[1] != null && cryotubeOutput[1].stackSize >= cryotubeOutput[1].getMaxStackSize())
        {
            return false;
        }

        boolean slot1Overlimit = false;
        boolean slot2Overlimit = false;
        if (cryotubeOutput != null)
        {
            // Check if output slot 1 is over stack limit.
            if (cryotubeOutput[0] != null)
            {
                int slot1Result = cryotubeOutput[0].stackSize;
                slot1Overlimit = (slot1Result <= getInventoryStackLimit() && slot1Result <= cryotubeOutput[0].getMaxStackSize());
            }
        
            // Check if output slot 2 is over stack limit.
            if (cryotubeOutput[1] != null)
            {
                int slot2Result = cryotubeOutput[1].stackSize;
                slot2Overlimit = (slot2Result <= getInventoryStackLimit() && slot2Result <= cryotubeOutput[1].getMaxStackSize());
            }
        }

        // If either slot is over we return false.
        if (slot1Overlimit || slot2Overlimit)
        {
            return false;
        }

        return true;
    }

    @Override
    public void closeChest()
    {
    }

    /**
     * 
     */
    private void convertEmptyReelToMemory()
    {
        // Check if input slot 2 is empty data reel.
        if (cryotubeInput[1] != null && this.cryotubeInput[1].isItemEqual(new ItemStack(MadEntities.DATAREEL_EMPTY)))
        {
            // Create memory based on ceiling of neural activity.
            ItemStack createdMemory = new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, this.neuralActivityMaximum);

            // Add encoded memory data reel to output slot 1.
            if (this.cryotubeOutput[0] == null)
            {
                this.cryotubeOutput[0] = createdMemory.copy();
            }
            else if (this.cryotubeOutput[0].isItemEqual(createdMemory))
            {
                cryotubeOutput[0].stackSize += createdMemory.stackSize;
            }

            // Remove a empty data reel from input stack 2.
            if (this.cryotubeInput[1] != null)
            {
                --this.cryotubeInput[1].stackSize;
                if (this.cryotubeInput[1].stackSize <= 0)
                {
                    this.cryotubeInput[1] = null;
                }
            }
        }
    }

    private ItemStack createRandomVillagerMemory()
    {
        // Randomly picks a number from zero to five to find a villager profession type.
        int someProfessionType = this.worldObj.rand.nextInt(5);

        switch (someProfessionType)
        {
        case 0:
            // Priest
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 32);
        case 1:
            // Farmer
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 64);
        case 2:
            // Butcher
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 128);
        case 3:
            // Blacksmith
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 256);
        case 4:
            // Librarian
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 512);
        case 5:
            // Weakest one again...
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 32);
        default:
            // Weakest one catch-all...
            return new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER, 1, 32);

        }
    }

    /** Places a rotten flesh in the output slot for that item. */
    private void createRottenFlesh(int howManyRange)
    {
        // Add some rotten flesh into the output slot for it.
        ItemStack rottenFlesh = new ItemStack(Item.rottenFlesh, this.worldObj.rand.nextInt(howManyRange));
        if (rottenFlesh != null)
        {
            // Stop if we are full of rotten flesh.
            if (this.cryotubeOutput[1] != null && this.cryotubeOutput[1].stackSize >= this.cryotubeOutput[1].getMaxStackSize())
            {
                return;
            }

            if (this.cryotubeOutput[1] == null)
            {
                this.cryotubeOutput[1] = rottenFlesh.copy();
            }
            else if (this.cryotubeOutput[1].isItemEqual(rottenFlesh))
            {
                cryotubeOutput[1].stackSize += rottenFlesh.stackSize;
            }
        }
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.cryotubeInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.cryotubeInput[slot].stackSize <= numItems)
            {
                itemstack = this.cryotubeInput[slot];
                this.cryotubeInput[slot] = null;
                return itemstack;
            }
            itemstack = this.cryotubeInput[slot].splitStack(numItems);

            if (this.cryotubeInput[slot].stackSize == 0)
            {
                this.cryotubeInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.cryotubeOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.cryotubeOutput[slot].stackSize <= numItems)
            {
                itemstack = this.cryotubeOutput[slot];
                this.cryotubeOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.cryotubeOutput[slot].splitStack(numItems);

            if (this.cryotubeOutput[slot].stackSize == 0)
            {
                this.cryotubeOutput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    @Override
    public ItemStack decrStackSize(int slot, int numItems)
    {
        if (slot == 0)
        {
            // Input stack 1 - Spawn egg.
            return DecreaseInputSlot(0, numItems);
        }
        else if (slot == 1)
        {
            // Input stack 2 - Memory data reel.
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // Input stack 3 - Nether star.
            return DecreaseInputSlot(2, numItems);
        }
        else if (slot == 3)
        {
            // Output stack 1 - Memory data reel.
            return DecreaseOutputSlot(0, numItems);
        }
        else if (slot == 4)
        {
            // Output stack 2 - Rotten flesh.
            return DecreaseOutputSlot(1, numItems);
        }

        // Something bad has occurred!
        return null;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return new int[]
        { 0 };
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */ int getHatchTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (hatchTimeMaximum == 0)
        {
            hatchTimeMaximum = 200;
        }

        return (hatchTimeCurrentValue * prgPixels) / hatchTimeMaximum;
    }

    public int getHealthValue()
    {
        return subjectCurrentHealth;
    }

    @Override
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /** Returns the name of the inventory. */
    @Override
    public String getMachineInternalName()
    {
        return MadFurnaces.CRYOTUBE_INTERNALNAME;
    }

    public int getMaxHealth()
    {
        if (subjectMaximumHealth == 0)
        {
            subjectMaximumHealth = 100;
        }

        return subjectMaximumHealth;
    }

    public int getMaxNeuralActivity()
    {
        if (neuralActivityMaximum == 0)
        {
            neuralActivityMaximum = 32;
        }

        return neuralActivityMaximum;
    }

    public int getNeuralActivity()
    {
        return neuralActivityValue;
    }

    int getNeuralActivityScaled(int prgPixels)
    {
        if (neuralActivityMaximum == 0)
        {
            neuralActivityMaximum = 32;
        }

        return (int) (((float) neuralActivityValue * prgPixels) / neuralActivityMaximum);
    }

    @Override
    public EnumSet<ForgeDirection> getOutputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }

    public int getSizeInputInventory()
    {
        return this.cryotubeInput.length;
    }

    @Override
    @Deprecated
    public int getSizeInventory()
    {
        // We make use of other methods to reference the multiple hash tables.
        return 0;
    }

    public int getSizeOutputInventory()
    {
        return cryotubeOutput.length;
    }

    /** Returns the stack in numbered slot */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - Spawn egg
            return this.cryotubeInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - empty data reel / memory data reel
            return this.cryotubeInput[1];
        }
        else if (slot == 2)
        {
            // Input slot 3 - Nether star
            return this.cryotubeInput[2];
        }
        else if (slot == 3)
        {
            // Output slot 1 - Memory data reel.
            return this.cryotubeOutput[0];
        }
        else if (slot == 4)
        {
            // Output slot 2 - Rotten flesh.
            return this.cryotubeOutput[1];
        }

        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Spawn egg
            if (this.cryotubeInput[0] != null)
            {
                ItemStack itemstack = this.cryotubeInput[0];
                this.cryotubeInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Memory data reel.
            if (this.cryotubeInput[1] != null)
            {
                ItemStack itemstack = this.cryotubeInput[1];
                this.cryotubeInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Input slot 3 - Nether star.
            if (this.cryotubeInput[2] != null)
            {
                ItemStack itemstack = this.cryotubeInput[2];
                this.cryotubeInput[2] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 3)
        {
            // Output slot 1 - Memory data reel.
            if (this.cryotubeOutput[0] != null)
            {
                ItemStack itemstack = this.cryotubeOutput[0];
                this.cryotubeOutput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 4)
        {
            // Output slot 2 - rotten flesh.
            if (this.cryotubeOutput[0] != null)
            {
                ItemStack itemstack = this.cryotubeOutput[1];
                this.cryotubeOutput[0] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    @SideOnly(Side.CLIENT) int getSubjectHealthScaled(int prgPixels)
    {
        return (int) (((float) subjectCurrentHealth * prgPixels) / subjectMaximumHealth);
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if input slot 2 is a empty genome data reel.
        if (slot == 1)
        {
            // Input slot 2 - empty genome data reel.
            ItemStack compareDirtyNeedle = new ItemStack(MadEntities.DATAREEL_EMPTY);
            if (compareDirtyNeedle.isItemEqual(items))
            {
                return true;
            }

            ItemStack compareMemoryReel = new ItemStack(MadEntities.COMBINEDMEMORY_MONSTERPLACER);
            if (compareMemoryReel.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Read input/output items from world save data.
        NBTTagList nbtInput = nbt.getTagList("InputItems");
        NBTTagList nbtOutput = nbt.getTagList("OutputItems");

        // Cast the save data onto our running object.
        this.cryotubeInput = new ItemStack[this.getSizeInputInventory()];
        this.cryotubeOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.cryotubeInput.length)
            {
                this.cryotubeInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.cryotubeOutput.length)
            {
                this.cryotubeOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Hatching time maximum amount.
        this.hatchTimeMaximum = nbt.getShort("HatchTimeMax");
        this.hatchTimeCurrentValue = nbt.getShort("HatchTime");

        // Subject health.
        this.subjectMaximumHealth = nbt.getShort("HealthMax");
        this.subjectCurrentHealth = nbt.getShort("HealthValue");

        // Neural activity (power output).
        this.neuralActivityMaximum = nbt.getShort("NeuralMax");
        this.neuralActivityValue = nbt.getShort("NeuralValue");

        // Determine if we have subject already spawned.
        this.subjectIsAlive = nbt.getBoolean("SubjectAlive");

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");
    }
    
    private void resetCryotube()
    {
        // Change out state to officially being deceased.
        this.subjectIsAlive = false;

        // Subject died, reset chamber!
        this.hatchTimeCurrentValue = 0;
        this.hatchTimeMaximum = 200;

        this.neuralActivityValue = 0;
        this.neuralActivityMaximum = 0;

        this.subjectMaximumHealth = 100;
        this.subjectCurrentHealth = 0;
    }
    
    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            // Spawn egg.
            this.cryotubeInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            // Empty or memory data reel.
            this.cryotubeInput[1] = par2ItemStack;
        }
        else if (par1 == 2)
        {
            // Nether star.
            this.cryotubeInput[2] = par2ItemStack;
        }
        else if (par1 == 3)
        {
            // Memory data reel.
            this.cryotubeOutput[0] = par2ItemStack;
        }
        else if (par1 == 4)
        {
            // Rotten flesh.
            this.cryotubeOutput[1] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /** Changes model texture based on entity state in the game world and broadcasts these changes to other nearby players. */
    @Override
    public void updateAnimation()
    {
        if (!isRedstonePowered())
        {
            // Cryotube is disabled and offline.
            TEXTURE = "models/" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/off.png";
            return;
        }

        // Dead or full cryotube (full when rotten flesh reaches stack limit).
        if (!canSmelt() && isRedstonePowered() && cryotubeOutput != null && cryotubeOutput[1] != null && cryotubeOutput[1].stackSize >= cryotubeOutput[1].getMaxStackSize())
        {
            if (curFrame <= 1 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/dead_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 2)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
            return;
        }

        // Powered and running status.
        if (canSmelt() && isRedstonePowered())
        {
            if (curFrame <= 6 && worldObj.getWorldTime() % 15L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/alive_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 7)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
            return;
        }

        if (!canSmelt() && isRedstonePowered())
        {
            // Cryotube is powered but has no items inside of it.
            TEXTURE = "models/" + MadFurnaces.CRYOTUBE_INTERNALNAME + "/on.png";
            return;
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Attempt to let other machines draw power from our internal reserves.
        this.produce();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Check if there is to much rotten flesh in the machine to operate it.
            if (this.cryotubeOutput[1] != null && this.cryotubeOutput[1].stackSize >= this.cryotubeOutput[1].getMaxStackSize())
            {
                this.resetCryotube();
                return;
            }

            // Animation for block.
            this.updateAnimation();

            // Disable the tube and kill the subject if he is inside during power outage.
            if (!isRedstonePowered() && this.subjectIsAlive || !isRedstonePowered() && this.hatchTimeCurrentValue > 0)
            {
                // Cleans out the tube.
                this.resetCryotube();

                // Adds rotten flesh to output slot 2.
                this.createRottenFlesh(5);

                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_OFF, 1.0F, 1.0F);
            }

            // Giant nested if-statement determines what machine should be doing at any given time.
            if (this.hatchTimeCurrentValue <= 0 && this.hatchTimeCurrentValue <= hatchTimeMaximum && !this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // IDLE
                if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_IDLE, 1.0F, 1.0F);
                }
            }
            else if (this.hatchTimeCurrentValue <= 0 && this.hatchTimeCurrentValue <= hatchTimeMaximum && this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // --------------------------
                // TUBE EMPTY, START HATCHING
                // --------------------------

                // CRACKING EGG
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_CRACKEGG, 1.0F, 1.0F);

                // Remove a spawn egg from input stack 1 to begin the hatching process.
                --this.cryotubeInput[0].stackSize;
                if (this.cryotubeInput[0].stackSize <= 0)
                {
                    this.cryotubeInput[0] = null;
                }

                // Length of time it will take to hatch a spawn egg.
                hatchTimeMaximum = 2600;

                // Increments the timer to kickstart the cooking loop.
                this.hatchTimeCurrentValue++;

                // Notify the system to update clients.
                inventoriesChanged = true;
            }
            else if (this.hatchTimeCurrentValue > 0 && this.hatchTimeCurrentValue < hatchTimeMaximum && this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // HATCHING
                if (worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS) == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_HATCHING, 1.0F, 0.1F);
                }

                // HATCH NOISES
                if (worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 2) == 0L && worldObj.rand.nextBoolean())
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_HATCH, 1.0F, 0.1F);
                }

                // IDLE
                if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_IDLE, 1.0F, 1.0F);
                }

                // Increments the timer to keep hatching process going!
                this.hatchTimeCurrentValue++;
            }
            else if (this.hatchTimeCurrentValue >= this.hatchTimeMaximum && this.canSmelt() && this.isRedstonePowered() && !this.subjectIsAlive)
            {
                // ----------------------------------------
                // HATCHING COMPLETE, DECIDE IF STILL-BIRTH
                // ----------------------------------------

                // Decide if this egg failed to grow into the tube correctly.
                if (this.worldObj.rand.nextBoolean() && this.worldObj.rand.nextInt(100) < 42)
                {
                    // Failed to hatch this egg, try again!
                    this.hatchTimeCurrentValue = 0;
                    this.subjectIsAlive = false;

                    // We don't make as much flesh he because it was not fully grown.
                    this.createRottenFlesh(2);

                    // CRACK EGG
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_CRACKEGG, 1.0F, 0.1F);
                }
                else
                {
                    // Hatched egg without error!
                    this.hatchTimeCurrentValue = this.hatchTimeMaximum;

                    // We flip our state to officially having a subject alive inside of it.
                    this.subjectIsAlive = true;

                    // Check if we have empty data reel or existing memory to work with for generating neural activity.
                    int ceilingReel = 100;
                    if (this.cryotubeInput[1] != null && this.cryotubeInput[1].getItem() instanceof ItemDataReelEmpty)
                    {
                        // Create a random memory for our villager.
                        ceilingReel = createRandomVillagerMemory().getItemDamage();
                    }
                    else if (this.cryotubeInput[1] != null && this.cryotubeInput[1].getItem() instanceof CombinedMemoryMonsterPlacer)
                    {
                        // Damage value of memory item is the ceiling for neural activity.
                        ceilingReel = this.cryotubeInput[1].getItemDamage();
                    }

                    // Setup health and maximum ceiling for neural activity based on above statement.
                    this.subjectMaximumHealth = 100;
                    this.subjectCurrentHealth = this.subjectMaximumHealth;

                    // Setup neural activity, making ceiling the actual one so player knows they are at sub-optimal performance.
                    this.neuralActivityMaximum = ceilingReel;
                    this.neuralActivityValue = ceilingReel;
                }
            }
            else if (this.hatchTimeCurrentValue == this.hatchTimeMaximum && this.canSmelt() && this.isRedstonePowered() && this.subjectIsAlive && this.getHealthValue() > 0)
            {
                // -------------
                // SUBJECT ALIVE
                // -------------

                // Happens with or without nether star installed into cryotube.
                if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS == 0L)
                {
                    this.updateNeuralActivity();

                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_WORK, 1.0F, 1.0F);

                    // Check if we have nether star along with all other required regents to generate power.
                    ItemStack compareNetherStar = new ItemStack(Item.netherStar);
                    if (!this.energy.isFull() && this.cryotubeInput[2] != null && this.cryotubeInput[2].isItemEqual(compareNetherStar))
                    {
                        // Generate electrical power.
                        Long amtRecieved = Long.valueOf(this.neuralActivityValue) * MadConfig.CRYOTUBE_PRODUCE;
                        this.produceEnergy(amtRecieved);
                    }
                }

                // Remove health because still alive.
                if (worldObj.getWorldTime() % MadScience.SECOND_IN_TICKS * 5 == 0L)
                {
                    this.subjectCurrentHealth--;
                }
            }
            else if (this.hatchTimeCurrentValue == this.hatchTimeMaximum && this.canSmelt() && this.isRedstonePowered() && this.subjectIsAlive && this.getHealthValue() <= 0)
            {
                // ------------
                // SUBJECT DEAD
                // ------------

                // STILL BIRTH
                // if (worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 2) == 0L)
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, CryotubeSounds.CRYOTUBE_STILLBIRTH, 1.0F, 0.5F);
                }

                // Takes empty data reel and creates proper variant based on neural activity ceiling.
                this.convertEmptyReelToMemory();
                this.resetCryotube();

                // Adds rotten flesh to output slot 2.
                this.createRottenFlesh(5);

                // Update clients because things have changed inventory wise.
                inventoriesChanged = true;
            }

            // We always mark the block for an update along with the other items in the world.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new CryotubePackets(this.xCoord, this.yCoord, this.zCoord, hatchTimeCurrentValue, hatchTimeMaximum,
                    getEnergy(ForgeDirection.UNKNOWN), getEnergyCapacity(ForgeDirection.UNKNOWN), subjectCurrentHealth, subjectMaximumHealth, neuralActivityValue, neuralActivityMaximum, this.TEXTURE).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    /** Update amount of neural activity the villager inside the tube is experiencing. */
    private void updateNeuralActivity()
    {
        if (cryotubeInput == null)
        {
            this.neuralActivityValue = this.worldObj.rand.nextInt(32);
            return;
        }

        if (cryotubeInput[1] == null)
        {
            this.neuralActivityValue = this.worldObj.rand.nextInt(32);
            return;
        }

        if (this.worldObj == null)
        {
            this.neuralActivityValue = this.worldObj.rand.nextInt(32);
            return;
        }

        if (this.worldObj.rand == null)
        {
            this.neuralActivityValue = this.worldObj.rand.nextInt(32);
            return;
        }

        // Use meta-item damage value of neural activity ceiling.
        this.neuralActivityValue = this.worldObj.rand.nextInt(this.neuralActivityMaximum);
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Hatching time for spawn egg.
        nbt.setShort("HatchTimeMax", (short) this.hatchTimeMaximum);
        nbt.setShort("HatchTime", (short) this.hatchTimeCurrentValue);

        // Subject current health.
        nbt.setShort("HealthMax", (short) this.subjectMaximumHealth);
        nbt.setShort("HealthValue", (short) this.subjectCurrentHealth);

        // Neural activity (power output).
        nbt.setShort("NeuralMax", (short) this.neuralActivityMaximum);
        nbt.setShort("NeuralValue", (short) this.neuralActivityValue);

        // Determine if we have subject already spawned.
        nbt.setBoolean("SubjectAlive", this.subjectIsAlive);

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.cryotubeInput.length; ++i)
        {
            if (this.cryotubeInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.cryotubeInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.cryotubeOutput.length; ++i)
        {
            if (this.cryotubeOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.cryotubeOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);
    }
}
