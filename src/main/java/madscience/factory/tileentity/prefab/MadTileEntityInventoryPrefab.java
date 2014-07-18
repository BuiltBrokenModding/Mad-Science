package madscience.factory.tileentity.prefab;

import java.util.ArrayList;

import madscience.factory.MadTileEntityFactoryProduct;
import madscience.factory.recipes.MadRecipe;
import madscience.factory.recipes.MadRecipeComponent;
import madscience.factory.slotcontainers.MadSlotContainer;
import madscience.factory.slotcontainers.MadSlotContainerTypeEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

abstract class MadTileEntityInventoryPrefab extends MadTileEntityRedstone implements ISidedInventory
{
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] INVENTORY;

    public MadTileEntityInventoryPrefab()
    {
        super();
    }

    MadTileEntityInventoryPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        
        INVENTORY = new ItemStack[registeredMachine.getContainerTemplate().length];
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
        // Convert the int direction into valid forge direction.
        ForgeDirection dir = ForgeDirection.getOrientation(side);

        // Grab a list of all container values from enumeration.
        MadSlotContainer[] containerSlots = this.getRegisteredMachine().getContainerTemplate();
        int i = containerSlots.length;

        // Loop through all the gathered slots.
        for (int j = 0; j < i; ++j)
        {
            // Check if they match the side we decoded from parameter.
            MadSlotContainer currentContainer = containerSlots[j];
            if (currentContainer.getExtractDirection().equals(dir) && currentContainer.canExtract())
            {
                // Allows the item to be extracted from the given slot.
                return true;
            }
        }

        // Default response is to return nothing!
        return false;
    }

    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side)
    {
        return this.isItemValidForSlot(slot, item);
    }

    private void clearInventory()
    {
        this.INVENTORY = new ItemStack[this.getSizeInventory()];
    }

    @Override
    public void closeChest()
    {
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
    
    /** Returns the total number of slots marked as input. */
    public int getInputSlotCount()
    {
        // Grab a list of all container values from enumeration.
        MadSlotContainer[] containerSlots = this.getRegisteredMachine().getContainerTemplate();
        int i = containerSlots.length;
        
        // Count the number of times we see input in the enumeration for slot type.
        int inputSlots = 0;
        for (MadSlotContainer slotContainer : containerSlots)
        {
            if (slotContainer.getSlotType().name().toLowerCase().contains("input"))
            {
                inputSlots++;
            }
        }
        
        return inputSlots;
    }

    /** Returns an array containing the indices of the slots that can be accessed by automation on the given side of this block. */
    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        // Convert the int direction into valid forge direction.
        ForgeDirection dir = ForgeDirection.getOrientation(side);

        // Grab a list of all container values from enumeration.
        MadSlotContainer[] containerSlots = this.getRegisteredMachine().getContainerTemplate();
        int i = containerSlots.length;

        // List which will store our integers for allowed sides.
        ArrayList<Integer> list = new ArrayList<Integer>();

        // Loop through all the gathered slots.
        for (int j = 0; j < i; ++j)
        {
            // Check if they match the side we decoded from parameter.
            MadSlotContainer currentContainer = containerSlots[j];

            // To prevent duplicate sides being added we only add based on one or the other.
            if (currentContainer.getExtractDirection().equals(dir) && currentContainer.canExtract())
            {
                // Extraction slots.
                list.add(new Integer(side));
            }
            else if (currentContainer.getInputDirection().equals(dir) && currentContainer.canInsert())
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
            for (int x = 0; x < ret.length; x++)
            {
                ret[x] = list.get(x).intValue();
            }
            return ret;
        }

        // Default response is to return nothing!
        return null;
    }

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this more of a set than a get?* */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        // Default name for inventory if none is specified.
        return this.getMachineInternalName();
    }

    /** Returns the number of slots in the inventory. */
    @Override
    public int getSizeInventory()
    {
        return this.getRegisteredMachine().getContainerTemplate().length;
    }

    /** Returns the stack in specified slot number. */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.INVENTORY[slot];
    }
    
    /** Returns recipe result for a single furnace input based on enumeration slot types. */
    public ItemStack[] getSingleFurnaceResultBySlot(MadSlotContainerTypeEnum inputSlot, MadSlotContainerTypeEnum outputSlot)
    {
        // Grab the ItemStack by the type specified in the given machine slot.
        ItemStack itemInsideInputSlot = this.getStackInSlotByType(inputSlot);
        
        // Grab the recipe archive object array.
        MadRecipe[] recipeArchiveArray = this.getRegisteredMachine().getRecipeArchive();
        
        // Recipe archives are nested so we need to loop through them to get to first one (even if it is the only one).
        for (MadRecipe machineRecipe : recipeArchiveArray) 
        {
            // Loop through the input and look for something matching slot type and input item.
            MadRecipeComponent[] inputIngredientsArray = machineRecipe.getInputIngredientsArray();
            boolean inputMatches = false;
            for (MadRecipeComponent recipeIngredient : inputIngredientsArray) 
            {
                // Only work on loaded recipes.
                if (!recipeIngredient.isLoaded())
                {
                    continue;
                }
                
                // Determine if slot type matches
                if (!recipeIngredient.getSlotType().equals(inputSlot))
                {
                    continue;
                }
                
                // Determine if input items match.
                for (ItemStack singleRecipe : recipeIngredient.getItemStackArray())
                {
                    String ingredientUnlocalizedName = singleRecipe.getUnlocalizedName();
                    String slotItemUnlocalizedName = itemInsideInputSlot.getUnlocalizedName();
                    if (ingredientUnlocalizedName.equals(slotItemUnlocalizedName))
                    {
                        inputMatches = true;
                        break;
                    }
                }
            }
            
            // Only continue onward if we found a match for the input item.
            if (inputMatches)
            {
                // Since this is a single furnace function we return he result from first output slot.
                MadRecipeComponent[] outputResultsArray = machineRecipe.getOutputResultsArray();
                for (MadRecipeComponent recipeResult : outputResultsArray) 
                {
                    // Only work on loaded recipes.
                    if (!recipeResult.isLoaded())
                    {
                        continue;
                    }
                    
                    // Determine if this recipe result slot type matches what we would expect from inputed slot.
                    if (recipeResult.getSlotType().equals(outputSlot))
                    {
                        // Should return a loaded reference to ItemStack created with loadRecipes().
                        return recipeResult.getItemStackArray();
                    }
                }
            }
        }
        
        // Default response is to return nothing.
        return null;
    }
    
    /** Returns true if given ItemStack is used in any recipes ingredients. */
    public boolean isItemUsedInInputRecipes(ItemStack possibleInputItem)
    {
        // Grab the recipe archive object array.
        MadRecipe[] recipeArchiveArray = this.getRegisteredMachine().getRecipeArchive();
        for (MadRecipe machineRecipe : recipeArchiveArray) 
        {
            MadRecipeComponent[] inputIngredients = machineRecipe.getInputIngredientsArray();
            for (MadRecipeComponent recipeResult : inputIngredients) 
            {
                if (!recipeResult.isLoaded())
                {
                    continue;
                }
                
                // Determine if this recipe result matches anything in ingredient list.
                for (ItemStack singleItem : recipeResult.getItemStackArray())
                {
                    if (singleItem.getItem().equals(possibleInputItem.getItem()))
                    {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /** Returns stack based on shared enumeration of possible slot types. */
    public ItemStack getStackInSlotByType(MadSlotContainerTypeEnum slotType)
    {
        // Check if we have a registered machine present to reference slot types from.
        MadTileEntityFactoryProduct currentMachine = this.getRegisteredMachine();
        if (currentMachine == null)
        {
            return null;
        }
        
        // Loop through the slots in this machine looking for one that matches the parameter.
        for (MadSlotContainer currentSlot : currentMachine.getContainerTemplate()) 
        {
            if (currentSlot.getSlotType().equals(slotType))
            {
                // Return the item from the inventory based on this slot type.
                return this.INVENTORY[currentSlot.slot()];
            }
        }
        
        // Default response is to return nothing.
        return null;
    }
    
    /** Returns enumeration type for a given container slot ID. 
     *  Returns null of no type is associated with the given slot, meaning it would be player inventory or hotbar. */
    public MadSlotContainerTypeEnum getSlotTypeBySlotID(int slotID)
    {
        MadTileEntityFactoryProduct currentMachine = this.getRegisteredMachine();
        if (currentMachine == null)
        {
            return null;
        }
        
        for (MadSlotContainer currentSlot : currentMachine.getContainerTemplate()) 
        {
            if (currentSlot.slot() == slotID)
            {
                // Return the type of this container slot.
                return currentSlot.getSlotType();
            }
        }
        
        // Default response is to return nothing.
        return null;
    }
    
    /** Returns integer which represents the slot number in Minecraft/Forge. */
    public int getSlotIDByType(MadSlotContainerTypeEnum slotType)
    {
        MadTileEntityFactoryProduct currentMachine = this.getRegisteredMachine();
        if (currentMachine == null)
        {
            return -1;
        }
        
        for (MadSlotContainer currentSlot : currentMachine.getContainerTemplate()) 
        {
            if (currentSlot.getSlotType().equals(slotType))
            {
                // Return the number of this container slot.
                return currentSlot.slot();
            }
        }
        
        // Default response is to return non-slot based number which will crash the game.
        return -1;
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

    @Override
    public void initiate()
    {
        super.initiate();
    }

    /** If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's language. Otherwise it will be used directly. */
    @Override
    public boolean isInvNameLocalized()
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        // Default response is to deny input and allow parent class to implement.
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

            if (b0 >= 0 && b0 < this.getRegisteredMachine().getContainerTemplate().length)
            {
                this.setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
    }

    /** Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections). */
    @Override
    public void setInventorySlotContents(int slotNumber, ItemStack item)
    {
        this.INVENTORY[slotNumber] = item;

        if (item != null && item.stackSize > this.getInventoryStackLimit())
        {
            item.stackSize = this.getInventoryStackLimit();
        }
    }
    
    /** Sets inventory slot contents based on type of slot. */
    public void setInventorySlotContentsByType(MadSlotContainerTypeEnum slotType, ItemStack item)
    {
        MadTileEntityFactoryProduct currentMachine = this.getRegisteredMachine();
        if (currentMachine == null)
        {
            return;
        }
        
        for (MadSlotContainer currentSlot : currentMachine.getContainerTemplate()) 
        {
            if (currentSlot.getSlotType().equals(slotType))
            {
                // Set the contents of the slot using the ID of the slot container.
                this.setInventorySlotContents(currentSlot.slot(), item);
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
        for (int i = 0; i < this.getRegisteredMachine().getContainerTemplate().length; ++i)
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
}
