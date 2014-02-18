package madscience.tileentities.soniclocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.MadSounds;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SoniclocatorEntity extends MadTileEntity implements ISidedInventory
{
    public SoniclocatorEntity()
    {
        super(MadConfig.SONICLOCATOR_CAPACTITY, MadConfig.SONICLOCATOR_INPUT);
    }

    private static final int[] slots_top = new int[]
    { 0 };
    private static final int[] slots_bottom = new int[]
    { 2, 1 };
    private static final int[] slots_sides = new int[]
    { 1 };

    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] soniclocatorOutput = new ItemStack[1];

    private ItemStack[] soniclocatorInput = new ItemStack[2];

    /** Current level of heat that the machine has accumulated while powered and active. */
    public int currentHeatValue;

    /** Maximum allowed heat value and also when the machine is considered ready. */
    public int currentHeatMaximum = 1000;

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Current frame of animation we should use to display in world. */
    public int curFrame;

    /** Name to display on inventory screen. */
    private String containerCustomName;

    /** Texture that should be displayed on our model. */
    public String soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/off.png";

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 3 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Empty water bucket.
            return true;
        }
        else if (slot == 4 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Clean needle.
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
    public boolean canSmelt()
    {
        // Check if power levels are at proper values before cooking.
        if (!this.isPowered())
        {
            return false;
        }

        // Check if user even wants us activated right with help of redstone.
        if (!this.isRedstonePowered())
        {
            return false;
        }

        // Check if input slots are empty.
        if (soniclocatorInput[0] == null || soniclocatorInput[1] == null)
        {
            return false;
        }

        // Check if input slot 1 is gravel.
        ItemStack itemsInputSlot1 = new ItemStack(Block.gravel);
        if (!itemsInputSlot1.isItemEqual(this.soniclocatorInput[0]))
        {
            return false;
        }

        // Check if output slot is same as target slot and if it is at maximum stack size or not.
        if (soniclocatorOutput[0] != null && !soniclocatorInput[1].isItemEqual(soniclocatorOutput[0]))
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with
        // items.
        if (this.soniclocatorOutput[0] == null)
        {
            return true;
        }

        // Check if output slot 1 is above item stack limit.
        if (this.soniclocatorOutput[0] != null && soniclocatorOutput[0].stackSize >= soniclocatorOutput[0].getMaxStackSize())
        {
            return false;
        }

        return true;
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.soniclocatorInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.soniclocatorInput[slot].stackSize <= numItems)
            {
                itemstack = this.soniclocatorInput[slot];
                this.soniclocatorInput[slot] = null;
                return itemstack;
            }
            itemstack = this.soniclocatorInput[slot].splitStack(numItems);

            if (this.soniclocatorInput[slot].stackSize == 0)
            {
                this.soniclocatorInput[slot] = null;
            }

            return itemstack;
        }
        return null;
    }

    private ItemStack DecreaseOutputSlot(int slot, int numItems)
    {
        // Removes items from either output slot 1 or 2.
        if (this.soniclocatorOutput[slot] != null)
        {
            ItemStack itemstack;

            if (this.soniclocatorOutput[slot].stackSize <= numItems)
            {
                itemstack = this.soniclocatorOutput[slot];
                this.soniclocatorOutput[slot] = null;
                return itemstack;
            }
            itemstack = this.soniclocatorOutput[slot].splitStack(numItems);

            if (this.soniclocatorOutput[slot].stackSize == 0)
            {
                this.soniclocatorOutput[slot] = null;
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
        else if (slot == 1)
        {
            // Input stack 2 - Dirty needle.
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // Output stack 1 - Empty bucket.
            return DecreaseOutputSlot(0, numItems);
        }

        // Something bad has occurred!
        MadScience.logger.info("decrStackSize() could not return " + numItems + " stack items from slot " + slot);
        return null;
    }

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    public float getHeatAmount()
    {
        // Returns current level of heat stored inside of the machine.
        return currentHeatValue;
    }

    public int getHeatLevelTimeScaled(int pxl)
    {
        // Returns scaled percentage of heat level used in GUI to show temperature.
        return (int) (this.getHeatAmount() * (pxl / this.getMaxHeatAmount()));
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /** Returns the name of the inventory. */
    @Override
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.containerCustomName : "container.furnace";
    }

    public float getMaxHeatAmount()
    {
        // Returns maximum amount of heat allowed and also operating temperature.
        return currentHeatMaximum;
    }

    public int getSizeInputInventory()
    {
        return this.soniclocatorInput.length;
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
        return soniclocatorOutput.length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot == 0)
        {
            // Input slot 1 - DNA Samples.
            return this.soniclocatorInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - Empty of uncompleted genome data reels.
            return this.soniclocatorInput[1];
        }
        else if (slot == 2)
        {
            // Output slot 1 - Damaged or completed genome data reels.
            return this.soniclocatorOutput[0];
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
            // Input slot 1 - Gravel blocks to transpose.
            if (this.soniclocatorInput[0] != null)
            {
                ItemStack itemstack = this.soniclocatorInput[0];
                this.soniclocatorInput[0] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 1)
        {
            // Input slot 2 - Target block that we need to locate.
            if (this.soniclocatorInput[1] != null)
            {
                ItemStack itemstack = this.soniclocatorInput[1];
                this.soniclocatorInput[1] = null;
                return itemstack;
            }
            return null;
        }
        else if (slot == 2)
        {
            // Output slot 1 - Retrieved target blocks.
            if (this.soniclocatorOutput[0] != null)
            {
                ItemStack itemstack = this.soniclocatorOutput[0];
                this.soniclocatorOutput[0] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    public boolean isHeated()
    {
        // Returns true if the heater has reached it's optimal temperature.
        return this.getHeatAmount() >= this.getMaxHeatAmount();
    }

    /** If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's language. Otherwise it will be used directly. */
    @Override
    public boolean isInvNameLocalized()
    {
        return this.containerCustomName != null && this.containerCustomName.length() > 0;
    }

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        // Check if input slot 1 is a fresh egg ready to be encoded.
        if (slot == 0)
        {
            // Input slot 1 - fresh egg.
            ItemStack compareItem = new ItemStack(Item.egg);
            if (compareItem.isItemEqual(items))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void initiate()
    {
        // Ensure the class below us is called.
        super.initiate();

        // Sound that is played when we are placed into the world.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.SONICLOCATOR_PLACE, 1.0F, 1.0F);
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
        this.soniclocatorInput = new ItemStack[this.getSizeInputInventory()];
        this.soniclocatorOutput = new ItemStack[this.getSizeOutputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.soniclocatorInput.length)
            {
                this.soniclocatorInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Output items process.
        for (int i = 0; i < nbtOutput.tagCount(); ++i)
        {
            NBTTagCompound outputSaveData = (NBTTagCompound) nbtOutput.tagAt(i);
            byte b0 = outputSaveData.getByte("OutputSlot");

            if (b0 >= 0 && b0 < this.soniclocatorOutput.length)
            {
                this.soniclocatorOutput[b0] = ItemStack.loadItemStackFromNBT(outputSaveData);
            }
        }

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Current frame of animation we are displaying.
        this.curFrame = nbt.getInteger("CurrentFrame");

        // Amount of heat that was stored inside of this block.
        this.currentHeatValue = nbt.getShort("HeatLevel");

        // Path to current texture what should be loaded onto the model.
        this.soniclocatorTexture = nbt.getString("TexturePath");

        if (nbt.hasKey("CustomName"))
        {
            this.containerCustomName = nbt.getString("CustomName");
        }
    }

    /** Sets the custom display name to use when opening a GUI linked to this tile entity. */
    public void setGuiDisplayName(String par1Str)
    {
        this.containerCustomName = par1Str;
    }

    private void setHeatLevel(int amount)
    {
        // Changes block internal temperature to new value.
        if (this.currentHeatValue != amount)
        {
            this.currentHeatValue = amount;
        }
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.soniclocatorInput[0] = par2ItemStack;
        }
        else if (par1 == 1)
        {
            this.soniclocatorInput[1] = par2ItemStack;
        }
        else if (par1 == 2)
        {
            this.soniclocatorOutput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Output 1 - Locate the target item within the given chunk.
        ItemStack craftedItem = locateTargetBlock(this.soniclocatorInput[1], this.soniclocatorInput[0]);

        if (craftedItem == null)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.MAINFRAME_BREAK, 1.0F, 1.0F);
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.SONICLOCATOR_EMPTY, 1.0F, 1.0F);
            return;
        }

        // Apply wither effect to players and hurt non-players.
        damageNearbyCreatures(16);

        // Add target block to output slot 1.
        if (this.soniclocatorOutput[0] == null)
        {
            this.soniclocatorOutput[0] = craftedItem.copy();
        }
        else if (this.soniclocatorOutput[0].isItemEqual(craftedItem))
        {
            soniclocatorOutput[0].stackSize += craftedItem.stackSize;
        }

        // Remove a piece of gravel from input stack 1.
        --this.soniclocatorInput[0].stackSize;
        if (this.soniclocatorInput[0].stackSize <= 0)
        {
            this.soniclocatorInput[0] = null;
        }

        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.SONICLOCATOR_FINISH, 1.0F, 1.0F);
    }

    public void damageNearbyCreatures(int range)
    {
        double px = this.xCoord;
        double py = this.yCoord;
        double pz = this.zCoord;

        List l = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, 
                AxisAlignedBB.getBoundingBox(px - range, py - range, pz - range, px + range, py + range, pz + range));

        if (l != null && l.isEmpty())
        {
            //MadScience.logger.info("No nearby players detected!");
            return;
        }

        for (int i = 0; i < l.size(); ++i)
        {
            try
            {
                if (l.get(i) instanceof EntityLivingBase)
                {
                    EntityLivingBase x = (EntityLivingBase) l.get(i);
                    if (x != null)
                    {
                        x.addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                    }
                }
                else if (l.get(i) instanceof EntityLiving)
                {
                    EntityLiving x = (EntityLiving) l.get(i);
                    if (x != null)
                    {
                        x.addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                    }
                }

            }
            catch (Exception err)
            {
                MadScience.logger.info("Attempted to poison living creature and failed!");
            }
        }
    }

    private ItemStack locateTargetBlock(ItemStack targetItem, ItemStack replacementItem)
    {
        // Skip client worlds.
        if (worldObj.isRemote)
        {
            return null;
        }

        Chunk chunk = worldObj.getChunkFromBlockCoords(xCoord, zCoord);

        // If we cannot find the chunk then return nothing.
        if (chunk == null)
        {
            return null;
        }

        // Loop through chunk we are in looking for target block(s).
        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                for (int k = 0; k < 16; ++k)
                {
                    int l = 0;
                    try
                    {
                        l = chunk.getBlockID(i, j, k);
                        if (l > 0)
                        {
                            // Valid block, check if equal to target block.
                            ItemStack compareChunkItem = new ItemStack(Block.blocksList[l]);
                            if (compareChunkItem.isItemEqual(targetItem))
                            {
                                // Located a target block.
                                int targetX = (i);
                                int targetY = (j);
                                int targetZ = (k);

                                // Convert chunk-coords into global-coords via bitshifting.
                                int targetXGlobal = chunk.xPosition * 16 + targetX;
                                int targetZGlobal = chunk.zPosition * 16 + targetZ;
                                MadScience.logger.info("Target Block: " + String.valueOf(targetXGlobal) + "x" + String.valueOf(targetY) + "x" + String.valueOf(targetZGlobal));

                                // Update the lighting engine about our changes to the chunk.
                                worldObj.setBlock(targetXGlobal, targetY, targetZGlobal, replacementItem.itemID, 0, 3);
                                worldObj.updateAllLightTypes(targetXGlobal, targetY, targetZGlobal);
                                worldObj.markBlockForUpdate(targetXGlobal, targetY, targetZGlobal);

                                // Return the ItemStack that we found!
                                return compareChunkItem;
                            }
                        }
                    }
                    catch (Exception err)
                    {
                        continue;
                    }
                }
            }
        }

        // Default response is we don't find our target item and there is a state for this (empty).
        return null;
    }

    /**
     * 
     */
    private void updateAnimation()
    {
        // Main state is when all four requirements have been met to cook items.
        if (canSmelt() && isPowered() && isRedstonePowered())
        {
            if (curFrame <= 4 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/work_" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else if (!canSmelt() && isPowered() && !isRedstonePowered())
        {
            // Has power but still no redstone signal.
            soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/off.png";
        }
        else if (!canSmelt() && isPowered() && isRedstonePowered())
        {
            // Powered up, heater on. Just nothing inside of me!
            soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/idle.png";
        }
        else if (!isRedstonePowered())
        {
            // Turned off.
            soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/off.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        boolean inventoriesChanged = false;

        // Remove power from this device if we have some and also have heater enabled.
        if (this.isPowered() && this.isRedstonePowered())
        {
            this.consumeEnergy(MadConfig.SONICLOCATOR_CONSUME);
        }

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // Update texture based on block state.
            this.updateAnimation();

            // Update current sound that sound be played.
            this.updateSound();

            // First tick for new item being cooked in furnace.
            if (this.currentHeatValue == 0 && this.canSmelt() && this.isPowered())
            {
                // We want the soniclocator to take the same amount of time between each pulse.
                currentHeatMaximum = (MadScience.SECOND_IN_TICKS * 20);

                // Start the powerup sound.
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.SONICLOCATOR_WORK, 1.0F, 1.0F);

                // Increments the timer to kickstart the cooking loop.
                this.currentHeatValue++;
            }
            else if (this.currentHeatValue > 0 && this.canSmelt() && this.isPowered())
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Increments the timer to kickstart the cooking loop.
                this.currentHeatValue++;

                // Check if furnace has exceeded total amount of time to cook.
                if (this.currentHeatValue >= currentHeatMaximum)
                {
                    // Convert one item into another via 'cooking' process.
                    this.currentHeatValue = 0;
                    this.smeltItem();
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentHeatValue = 0;
            }

            // Update status of machine to all clients around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, 25, worldObj.provider.dimensionId, new SoniclocatorPackets(this.xCoord, this.yCoord, this.zCoord, getEnergy(ForgeDirection.UNKNOWN),
                    getEnergyCapacity(ForgeDirection.UNKNOWN), this.currentHeatValue, this.currentHeatMaximum, this.soniclocatorTexture).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    private void updateSound()
    {
        // Check if we should be playing the idle sound.
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % (MadScience.SECOND_IN_TICKS * 2.3f) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, MadSounds.SONICLOCATOR_IDLE, 1.0F, 1.0F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Current frame of animation we are displaying.
        nbt.setInteger("CurrentFrame", this.curFrame);

        // Amount of heat current stored within the block.
        nbt.setShort("HeatLevel", (short) this.currentHeatValue);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.soniclocatorTexture);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();
        NBTTagList outputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.soniclocatorInput.length; ++i)
        {
            if (this.soniclocatorInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.soniclocatorInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Output slots.
        for (int i = 0; i < this.soniclocatorOutput.length; ++i)
        {
            if (this.soniclocatorOutput[i] != null)
            {
                NBTTagCompound outputSlots = new NBTTagCompound();
                outputSlots.setByte("OutputSlot", (byte) i);
                this.soniclocatorOutput[i].writeToNBT(outputSlots);
                outputItems.appendTag(outputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);
        nbt.setTag("OutputItems", outputItems);

        if (this.isInvNameLocalized())
        {
            nbt.setString("CustomName", this.containerCustomName);
        }
    }
}
