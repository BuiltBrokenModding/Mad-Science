package madscience.tile.clayfurnace;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import madscience.network.MadParticlePacket;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public class ClayfurnaceEntity extends TileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[]
    { 0 };
    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };
    private ItemStack[] clayfurnaceInput = new ItemStack[2];
    int currentItemCookingMaximum;
    int currentItemCookingValue;
    int animationCurrentFrame;
    String TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/idle.png";
    boolean hasBeenLit = false;
    boolean hasStoppedSmoldering = false;
    boolean hasCompletedBurnCycle = false;
    boolean hasCooledDown = false;

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 0 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Filled bucket.
            return true;
        }
        else if (slot == 1 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Empty water bucket
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
    private boolean canSmelt()
    {
        // If we have already completed a burn cycle we are done smelting.
        if (this.hasCompletedBurnCycle)
        {
            return false;
        }

        // Check if input slots are empty.
        if (clayfurnaceInput[0] == null || clayfurnaceInput[1] == null)
        {
            return false;
        }

        // Check if input slot 1 is a block of coal.
        ItemStack itemsInputSlot1 = new ItemStack(Block.coalBlock);
        if (!itemsInputSlot1.isItemEqual(this.clayfurnaceInput[0]))
        {
            return false;
        }

        return true;
    }

    @Override
    public void closeChest()
    {
    }

    ItemStack createEndResult()
    {
        // Get the final form of the inputed block will be from recipe list.
        ItemStack convertedOreRecipe = ClayfurnaceRecipes.getSmeltingResult(this.clayfurnaceInput[1]);

        if (convertedOreRecipe == null)
        {
            return null;
        }

        // Remove block of coal from input slot 1.
        --this.clayfurnaceInput[0].stackSize;
        if (this.clayfurnaceInput[0].stackSize <= 0)
        {
            this.clayfurnaceInput[0] = null;
        }

        // Remove input ore that we used to convert.
        --this.clayfurnaceInput[1].stackSize;
        if (this.clayfurnaceInput[1].stackSize <= 0)
        {
            this.clayfurnaceInput[1] = null;
        }

        return convertedOreRecipe;
    }

    private void createRandomSmoke()
    {
        // A little bit of smoke coming off.
        int smokeRadnomizer = Math.abs(worldObj.rand.nextInt(5));
        if (smokeRadnomizer <= 0)
            smokeRadnomizer = 1;
        if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS * smokeRadnomizer == 0L)
        {
            // Send a packet saying we want a little bit of smoke.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("smoke", 0.5D + this.xCoord, this.yCoord + 0.65D, this.zCoord + 0.5D, 0.01F,
                    worldObj.rand.nextFloat() - 0.25F, 0.01F).makePacket());
        }
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.clayfurnaceInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.clayfurnaceInput[slot].stackSize <= numItems)
            {
                itemstack = this.clayfurnaceInput[slot];
                this.clayfurnaceInput[slot] = null;
                return itemstack;
            }
            itemstack = this.clayfurnaceInput[slot].splitStack(numItems);

            if (this.clayfurnaceInput[slot].stackSize == 0)
            {
                this.clayfurnaceInput[slot] = null;
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
            // Input stack 1 - Water bucket.
            return DecreaseInputSlot(0, numItems);
        }

        if (slot == 1)
        {
            // Output stack 1 - Empty bucket
            return DecreaseInputSlot(1, numItems);
        }

        // Something bad has occurred!
        MadMod.LOGGER.info("decrStackSize() could not return " + numItems + " stack items from slot " + slot);
        return null;
    }

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /** Returns an integer between 0 and the passed value representing how close the current item is to being completely cooked */
    int getItemCookTimeScaled(int prgPixels)
    {
        // Prevent divide by zero exception by setting ceiling.
        if (currentItemCookingMaximum == 0)
        {
            currentItemCookingMaximum = 200;
        }

        return (currentItemCookingValue * prgPixels) / currentItemCookingMaximum;
    }

    public int getSizeInputInventory()
    {
        return this.clayfurnaceInput.length;
    }

    @Override
    @Deprecated
    public int getSizeInventory()
    {
        // We make use of other methods to reference the multiple hash tables.
        return 0;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1
            return this.clayfurnaceInput[0];
        }

        if (slot == 1)
        {
            // Input slot 2
            return this.clayfurnaceInput[1];
        }

        MadMod.LOGGER.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - Charcoal block fuel source.
            if (this.clayfurnaceInput[0] != null)
            {
                ItemStack itemstack = this.clayfurnaceInput[0];
                this.clayfurnaceInput[0] = null;
                return itemstack;
            }
            return null;
        }

        // Input slot 2 - Raw ore block.
        if (slot == 1)
        {
            if (this.clayfurnaceInput[1] != null)
            {
                ItemStack itemstack = this.clayfurnaceInput[1];
                this.clayfurnaceInput[1] = null;
                return itemstack;
            }
            return null;
        }

        return null;
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
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == 0)
        {
            // Input slot 1 - Coal block.
            ItemStack itemsInputSlot1 = new ItemStack(Block.coalBlock);
            if (itemsInputSlot1.isItemEqual(items))
            {
                return true;
            }
        }

        // Input slot 2 - Defined recipe for ore into block form.
        if (slot == 1)
        {
            ItemStack itemsInputSlot2 = ClayfurnaceRecipes.getSmeltingResult(items);
            if (itemsInputSlot2 == null)
            {
                return false;
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
        this.clayfurnaceInput = new ItemStack[this.getSizeInputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.clayfurnaceInput.length)
            {
                this.clayfurnaceInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Determines if this block has completed a full cooking cycle.
        this.hasCompletedBurnCycle = nbt.getBoolean("hasCompletedBurnCycle");

        // Determines if this block has cooled off from it's molten state.
        this.hasCooledDown = nbt.getBoolean("hasCooledDown");

        // Determines if we have been hit by the player after finished burn cycle.
        this.hasStoppedSmoldering = nbt.getBoolean("hasStoppedSmoldering");

        // Determines if this block has been caught on fire by a flint and steel.
        this.hasBeenLit = nbt.getBoolean("hasBeenLit");

        // Amount of time before clayfurnace will regenerate some of itself using mutagen.
        this.currentItemCookingValue = nbt.getShort("CookTime");

        // Current frame of animation we are displaying.
        this.animationCurrentFrame = nbt.getInteger("CurrentFrame");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.clayfurnaceInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.clayfurnaceInput[1] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void setLitStatus(boolean shouldLight)
    {
        // Cancel out if we have already been lit on fire before.
        if (hasBeenLit)
        {
            return;
        }

        // Cancel out if we don't have anything to cook with.
        if (!canSmelt())
        {
            return;
        }

        // Flips a bool that allows this device to start cooking because it has been hit with a flint and steel.
        MadMod.LOGGER.info("Attempting to light clay furnace at " + this.xCoord + ", " + this.yCoord + ", " + this.zCoord);
        hasBeenLit = true;
    }

    private void smeltItem()
    {
        if (this.canSmelt())
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);

            // Flips a bool that triggers the next sequence of events for clay furnace.
            this.hasCompletedBurnCycle = true;
        }
    }

    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (this.hasCooledDown)
        {
            // COOLED DOWN (WAITING FOR PLAYER TO HIT US)
            TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/shell.png";
        }
        if (!this.canSmelt() && this.hasBeenLit && this.hasCompletedBurnCycle && hasStoppedSmoldering && !this.hasCooledDown)
        {
            // COOL DOWN (RED HOT MODE)
            if (animationCurrentFrame <= 4 && worldObj.getWorldTime() % (MadUtils.SECOND_IN_TICKS * 5) == 0L)
            {
                // Same one as before.
                this.createRandomSmoke();
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);

                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/redhot" + animationCurrentFrame + ".png";

                // Update animation frame.
                ++animationCurrentFrame;
            }
            else if (animationCurrentFrame >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                animationCurrentFrame = 0;
                this.hasCooledDown = true;
            }
        }
        else if (!this.canSmelt() && this.hasBeenLit && this.hasCompletedBurnCycle && !hasStoppedSmoldering && !this.hasCooledDown)
        {
            // SMOLDERING FURNACE MODE
            TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/done.png";

            // Method in this class.
            this.createRandomSmoke();
        }
        else if (this.canSmelt() && this.hasBeenLit && !this.hasCompletedBurnCycle && !hasStoppedSmoldering && !this.hasCooledDown)
        {
            // BURN CYCLE (COOKING).
            if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                // Send a packet saying we want furnace fire
                PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("flame", 0.5D + this.xCoord, this.yCoord + 0.65D, this.zCoord + 0.5D, 0.01F,
                        worldObj.rand.nextFloat() - 0.25F, 0.01F).makePacket());
            }

            if (animationCurrentFrame <= 3 && worldObj.getWorldTime() % 25L == 0L)
            {
                // Send a packet saying we want puffs of smoke used in minecart furnace.
                PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("largesmoke", 0.5D + this.xCoord, this.yCoord + 0.5D, this.zCoord + 0.5D,
                        worldObj.rand.nextFloat(), worldObj.rand.nextFloat() + 3.0D, worldObj.rand.nextFloat()).makePacket());

                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/work" + animationCurrentFrame + ".png";

                // Update animation frame.
                ++animationCurrentFrame;
            }
            else if (animationCurrentFrame >= 4)
            {
                // Check if we have exceeded the ceiling and need to reset.
                animationCurrentFrame = 0;
                
                // Play fire burning sound randomly.
                if (worldObj.rand.nextBoolean())
                {
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "fire.fire", 1.0F, 1.0F);
                }
            }
        }
        else if (!hasBeenLit && !this.hasCooledDown)
        {
            // Idle state single texture.
            TEXTURE = "models/" + MadFurnaces.CLAYFURNACE_INTERNALNAME + "/idle.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Changes texture and model visibility based on current state.
            updateAnimation();

            // First tick for new item being cooked in furnace.
            if (this.currentItemCookingValue == 0 && this.canSmelt() && this.hasBeenLit)
            {
                // New item pulled from cooking stack to be processed, check how
                // long this item will take to cook.
                try
                {
                    currentItemCookingMaximum = MadUtils.SECOND_IN_TICKS * MadConfig.CLAYFURNACE_COOKTIME_IN_SECONDS;
                }
                catch (Exception err)
                {
                    MadMod.LOGGER.info("Attempted to set cook time for clay furnace but failed, using default value of 420 (7 minutes).");
                    currentItemCookingMaximum = MadUtils.SECOND_IN_TICKS * 420;
                }

                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;
            }
            else if (this.currentItemCookingValue > 0 && this.canSmelt() && this.hasBeenLit)
            {
                // Increments the timer to kickstart the cooking loop.
                this.currentItemCookingValue++;

                // Check if furnace has exceeded total amount of time to cook.
                if (this.currentItemCookingValue >= currentItemCookingMaximum)
                {
                    // Convert one item into another via 'cooking' process.
                    this.currentItemCookingValue = 0;
                    this.smeltItem();
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentItemCookingValue = 0;
            }

            // Send update about tile entity status to all players around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new ClayfurnacePackets(this.xCoord, this.yCoord, this.zCoord, currentItemCookingValue,
                    currentItemCookingMaximum, this.hasBeenLit, this.hasStoppedSmoldering, this.hasCompletedBurnCycle, this.hasCooledDown, this.TEXTURE).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Determines if we have already finished cooking.
        nbt.setBoolean("hasCompletedBurnCycle", this.hasCompletedBurnCycle);

        // Determine if we have cooled down from molten state awaiting to be broken.
        nbt.setBoolean("hasCooledDown", this.hasCooledDown);

        // Determines if we have been hit by the player after finishing burn cycle.
        nbt.setBoolean("hasStoppedSmoldering", this.hasStoppedSmoldering);

        // Determines if this block has already been lit by the player.
        nbt.setBoolean("hasBeenLit", this.hasBeenLit);

        // Amount of time left to cook current item inside of the furnace.
        nbt.setShort("CookTime", (short) this.currentItemCookingValue);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.animationCurrentFrame);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.clayfurnaceInput.length; ++i)
        {
            if (this.clayfurnaceInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.clayfurnaceInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
    }

    @Override
    public String getInvName()
    {
       return MadFurnaces.CLAYFURNACE_INTERNALNAME;
    }
}
