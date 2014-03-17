package madscience.tileentities.voxbox;

import java.util.Random;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadScience;
import madscience.tileentities.prefab.MadTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.PacketDispatcher;

public class VoxBoxEntity extends MadTileEntity implements ISidedInventory
{
    private static final int[] slots_bottom = new int[]
    { 2, 1 };

    private static final int[] slots_sides = new int[]
    { 1 };
    private static final int[] slots_top = new int[]
    { 0 };

    /** Random number generator used to spit out food stuffs. */
    public Random animRand = new Random();

    /** Name to display on inventory screen. */
    private String containerCustomName;

    /** Determines if we currently should be playing animation frames every tick or not. */
    public boolean shouldPlay;

    /** Path to texture that we would like displayed on this block. */
    public String TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png";

    private ItemStack[] voxboxInput = new ItemStack[1];

    public VoxBoxEntity()
    {
        super(MadConfig.VOXBOX_CAPACTITY, MadConfig.VOXBOX_INPUT);
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 0 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            // Written book.
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
        // If our one and only input slot is not null then we are good to go!
        if (voxboxInput[0] != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public void closeChest()
    {
    }

    private ItemStack DecreaseInputSlot(int slot, int numItems)
    {
        // Removes items from input slot 1 or 2.
        if (this.voxboxInput[slot] != null)
        {
            ItemStack itemstack;

            if (this.voxboxInput[slot].stackSize <= numItems)
            {
                itemstack = this.voxboxInput[slot];
                this.voxboxInput[slot] = null;
                return itemstack;
            }
            itemstack = this.voxboxInput[slot].splitStack(numItems);

            if (this.voxboxInput[slot].stackSize == 0)
            {
                this.voxboxInput[slot] = null;
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
            // Input stack 1 - Written book.
            return DecreaseInputSlot(0, numItems);
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

    public int getSizeInputInventory()
    {
        return this.voxboxInput.length;
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
            // Input slot 1 - Written book.
            return this.voxboxInput[0];
        }

        MadScience.logger.info("getStackInSlot() could not return valid stack from slot " + slot);
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        // Input slots.
        if (slot == 0)
        {
            // Input slot 1 - Written book
            if (this.voxboxInput[0] != null)
            {
                ItemStack itemstack = this.voxboxInput[0];
                this.voxboxInput[0] = null;
                return itemstack;
            }
            return null;
        }

        return null;
    }

    @SuppressWarnings("unused")
    public String getWrittenBookContents(NBTTagCompound nbt)
    {
        if (nbt == null)
        {
            return null;
        }
        else if (!nbt.hasKey("pages"))
        {
            return null;
        }
        else
        {
            NBTTagList nbttaglist = (NBTTagList) nbt.getTag("pages");

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagString nbttagstring = (NBTTagString) nbttaglist.tagAt(i);

                if (nbttagstring.data == null)
                {
                    return null;
                }

                if (nbttagstring.data.length() > 256)
                {
                    return null;
                }

                return nbttagstring.data.trim();
            }

            return null;
        }
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
        // Check if input slot 1 is a written book.
        ItemStack compareWrittenBook = new ItemStack(Item.writtenBook);
        if (slot == 0)
        {
            if (compareWrittenBook.isItemEqual(items))
            {
                return true;
            }

            return false;
        }

        return false;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
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
        this.voxboxInput = new ItemStack[this.getSizeInputInventory()];

        // Input items process.
        for (int i = 0; i < nbtInput.tagCount(); ++i)
        {
            NBTTagCompound inputSaveData = (NBTTagCompound) nbtInput.tagAt(i);
            byte b0 = inputSaveData.getByte("InputSlot");

            if (b0 >= 0 && b0 < this.voxboxInput.length)
            {
                this.voxboxInput[b0] = ItemStack.loadItemStackFromNBT(inputSaveData);
            }
        }

        // Should play animation status.
        this.shouldPlay = nbt.getBoolean("ShouldPlay");

        // Path to current texture what should be loaded onto the model.
        this.TEXTURE = nbt.getString("TexturePath");

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

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 == 0)
        {
            this.voxboxInput[0] = par2ItemStack;
        }

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public void smeltItem()
    {
        // Read the book in the slot and parse it.
        if (voxboxInput[0] != null && voxboxInput[0].stackTagCompound != null)
        {
            String bookContents = this.getWrittenBookContents(voxboxInput[0].stackTagCompound);
            MadScience.logger.info(bookContents);
        }
    }

    /** Update current frame of animation that we should be playing. */
    private void updateAnimation()
    {
        // Active state has many textures based on item cook progress.
        if (isPowered() && canSmelt())
        {
            if (worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png";
            }
            else
            {
                TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox1.png";
            }
        }
        else
        {
            // We are not powered or working.
            TEXTURE = "models/" + MadFurnaces.VOXBOX_INTERNALNAME + "/voxBox0.png";
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();
        boolean inventoriesChanged = false;

        // Update status of the machine if it has redstone power or not.
        checkRedstonePower();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // First tick for new item being cooked in furnace.
            if (this.canSmelt() && this.isPowered() && this.isRedstonePowered())
            {
                // Decrease to amount of energy this item has on client and server.
                this.consumeEnergy(MadConfig.VOXBOX_CONSUME);

                // Animation for block.
                updateAnimation();

                // Read the written book.
                this.smeltItem();
                inventoriesChanged = true;
            }

            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new VoxBoxPackets(this.xCoord, this.yCoord, this.zCoord, getEnergy(ForgeDirection.UNKNOWN),
                    getEnergyCapacity(ForgeDirection.UNKNOWN), this.TEXTURE).makePacket());
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

        // Should play animation status.
        nbt.setBoolean("ShouldPlay", this.shouldPlay);

        // Path to current texture that should be loaded onto the model.
        nbt.setString("TexturePath", this.TEXTURE);

        // Two tag lists for each type of items we have in this entity.
        NBTTagList inputItems = new NBTTagList();

        // Input slots.
        for (int i = 0; i < this.voxboxInput.length; ++i)
        {
            if (this.voxboxInput[i] != null)
            {
                NBTTagCompound inputSlots = new NBTTagCompound();
                inputSlots.setByte("InputSlot", (byte) i);
                this.voxboxInput[i].writeToNBT(inputSlots);
                inputItems.appendTag(inputSlots);
            }
        }

        // Save the input and output items.
        nbt.setTag("InputItems", inputItems);

        if (this.isInvNameLocalized())
        {
            nbt.setString("CustomName", this.containerCustomName);
        }
    }
}
