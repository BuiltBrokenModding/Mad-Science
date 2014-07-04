package madscience.tileentities.prefab;

import java.util.ArrayList;

import madscience.factory.MadTileEntityFactory;
import madscience.factory.slotcontainers.MadSlotContainerInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

public abstract class MadTileEntityInventory extends MadTileEntityRedstone implements ISidedInventory
{
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] INVENTORY;
    
    public MadTileEntityInventory()
    {
        super();
    }
    
    public MadTileEntityInventory(String machineName)
    {
        super(machineName);
        INVENTORY = new ItemStack[MadTileEntityFactory.getMachineInfo(machineName).getContainerTemplate().length];
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        // Store the inventory items we have inside our containers.
        NBTTagList nbttaglist = nbt.getTagList("Items");
        this.clearInventory();

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < MadTileEntityFactory.getMachineInfo(this.getMachineInternalName()).getContainerTemplate().length)
            {
                this.setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
    }

    /** Allows the entity to update its state. */
    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        // Reload from NBT data what items was inside our container slots.
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < MadTileEntityFactory.getMachineInfo(this.getMachineInternalName()).getContainerTemplate().length; ++i)
        {
            if (this.getStackInSlot(i) != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbt.setTag("Items", nbttaglist);
    }
    
    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side)
    {
        return this.isItemValidForSlot(slot, item);
    }
    
    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
        // Convert the int direction into valid forge direction.
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        
        // Grab a list of all container values from enumeration.
        MadSlotContainerInterface[] containerSlots = MadTileEntityFactory.getMachineInfo(this.getMachineInternalName()).getContainerTemplate();
        int i = containerSlots.length;

        // Loop through all the gathered slots.
        for (int j = 0; j < i; ++j)
        {
            // Check if they match the side we decoded from parameter.
            MadSlotContainerInterface currentContainer = containerSlots[j];
            if (currentContainer.getExtractDirection().equals(dir) && currentContainer.canExtract())
            {
                // Allows the item to be extracted from the given slot.
                return true;
            }
        }
        
        // Default response is to return nothing!
        return false;
    }

    /** Returns the number of slots in the inventory. */
    @Override
    public int getSizeInventory()
    {
        return MadTileEntityFactory.getMachineInfo(this.getMachineInternalName()).getContainerTemplate().length;
    }

    /** Returns the stack in slot i */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.INVENTORY[slot];
    }

    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    @Override
    public ItemStack decrStackSize(int slotNumber, int amount)
    {
        if (this.INVENTORY[slotNumber] != null)
        {
            ItemStack itemstack;

            if (this.INVENTORY[slotNumber].stackSize <= amount)
            {
                itemstack = this.INVENTORY[slotNumber];
                this.INVENTORY[slotNumber] = null;
                return itemstack;
            }
            
            itemstack = this.INVENTORY[slotNumber].splitStack(amount);
            if (this.INVENTORY[slotNumber].stackSize == 0)
            {
                this.INVENTORY[slotNumber] = null;
            }

            return itemstack;
        }
        return null;
    }

    /** When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like when you close a workbench GUI. */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.INVENTORY[slot] != null)
        {
            ItemStack itemstack = this.INVENTORY[slot];
            this.INVENTORY[slot] = null;
            return itemstack;
        }
        
        return null;
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int slot, ItemStack par2ItemStack)
    {
        this.INVENTORY[slot] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName()
    {
        // Default name for inventory if none is specified.
        return "UnknownMachine";
    }

    /** If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's language. Otherwise it will be used directly. */
    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /** Do not make give this method the name canInteractWith because it clashes with Container */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }
    
    private void clearInventory()
    {
        this.INVENTORY = new ItemStack[this.getSizeInventory()];
    }

    @Override
    public void openChest() { }

    @Override
    public void closeChest() { }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        // Default response is to deny input and allow parent class to implement.
        return false;
    }

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        // Convert the int direction into valid forge direction.
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        
        // Grab a list of all container values from enumeration.
        MadSlotContainerInterface[] containerSlots = MadTileEntityFactory.getMachineInfo(this.getMachineInternalName()).getContainerTemplate();
        int i = containerSlots.length;
        
        // List which will store our integers for allowed sides.
        ArrayList<Integer> list = new ArrayList<Integer>();

        // Loop through all the gathered slots.
        for (int j = 0; j < i; ++j)
        {
            // Check if they match the side we decoded from parameter.
            MadSlotContainerInterface currentContainer = containerSlots[j];
            
            // To prevent duplicate sides being added we only add based on one or the other.
            if (currentContainer.getExtractDirection().equals(dir) && currentContainer.canExtract())
            {
                // Extraction slots.
                list.add(new Integer(side));
            }
            else if(currentContainer.getInputDirection().equals(dir) && currentContainer.canInsert())
            {
                // Insertion slots.
                list.add(new Integer(side));
            }
        }
        
        // Only return the integer array if there is something to actually return.
        if (list != null && !list.isEmpty())
        {
            // There is no shortcut for converting from int[] to List as Arrays.asList does not deal with boxing.
            int[] ret = new int[list.size()];
            for (int x=0; x < ret.length; x++)
            {
                ret[x] = list.get(x).intValue();
            }
            return ret;
        }
        
        // Default response is to return nothing!
        return null;
    }
}
