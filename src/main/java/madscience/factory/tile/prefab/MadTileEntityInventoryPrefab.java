package madscience.factory.tile.prefab;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.container.MadSlotContainer;
import madscience.factory.container.MadSlotContainerTypeEnum;
import madscience.factory.recipe.MadRecipe;
import madscience.factory.recipe.MadRecipeComponent;
import madscience.factory.tile.MadTileEntityFactoryProduct;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

abstract class MadTileEntityInventoryPrefab extends MadTileEntityRedstonePrefab implements ISidedInventory
{
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] INVENTORY;
    
    /** Determines if anything inside of this machine has changed that might be required a packet update! */
    private boolean inventoriesChanged = false;

    public MadTileEntityInventoryPrefab()
    {
        super();
    }

    MadTileEntityInventoryPrefab(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
        
        INVENTORY = new ItemStack[registeredMachine.getContainerTemplate().length];
    }
    
    public boolean isInventoriesChanged()
    {
        return inventoriesChanged;
    }

    public void setInventoriesChanged()
    {
        this.inventoriesChanged = true;
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

    public void clearInventory()
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
    
    /** Returns the total number of slots marked as input for this machine. */
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
    
    /** Returns total number of slots that are of the given slot type for this machine.. */
    public int getSlotCountByType(MadSlotContainerTypeEnum slotType)
    {
        // Grab a list of all container values from enumeration.
        MadSlotContainer[] containerSlots = this.getRegisteredMachine().getContainerTemplate();
        int i = containerSlots.length;
        
        // Count the number of times we see input in the enumeration for slot type.
        int slotCount = 0;
        for (MadSlotContainer slotContainer : containerSlots)
        {
            if (slotContainer.getSlotType().equals(slotType))
            {
                slotCount++;
            }
        }
        
        return slotCount;
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

    /** Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     *  Isn't this more of a set than a get?* */
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
    public ItemStack[] getRecipeResult(MadSlotContainerTypeEnum... searchPatternArray)
    {
        // Grab the recipe archive object array, exit if we return nothing to compare against.
        MadRecipe[] recipeArchiveArray = this.getRegisteredMachine().getRecipeArchive();
        if (recipeArchiveArray == null)
        {
            return null;
        }

        // Items from the machine that are in the slots from the player.
        List<ItemStack> machineInputItems = new ArrayList<ItemStack>();
        MadSlotContainerTypeEnum machineOutputSlot = null;
        
        // Determine slot type, adding item to appropriate list.
        for (MadSlotContainerTypeEnum slotType : searchPatternArray)
        {
            if (slotType.name().toLowerCase().contains("input"))
            {
                machineInputItems.add(this.getStackInSlotByType(slotType));
            }
            else if (slotType.name().toLowerCase().contains("output"))
            {
                // Only one output slot can be mapped at a time with this method.
                machineOutputSlot = slotType;
            }
        }
        
        // Arrays we will use to compare the inputs and outputs with given items.
        List<String> finalMachineInputSlotArray = new ArrayList<String>();
        
        // Add the unlocalized names from machine input items to their final array.
        for (ItemStack machineInputItemLiteral : machineInputItems)
        {
            finalMachineInputSlotArray.add(machineInputItemLiteral.getItem().getUnlocalizedName());            
        }
        
        // Loop through all the recipes and examine recipe components for input and output.
        for (MadRecipe singleRecipe : recipeArchiveArray)
        {
            MadRecipeComponent[] inputRecipeComponents = singleRecipe.getInputIngredientsArray();
            MadRecipeComponent[] outputRecipeComponents = singleRecipe.getOutputResultsArray();
            
            List<String> finalRecipeArchiveInputArray = new ArrayList<String>();
            
            // Loop through all recipe components and add them to string list.
            for (MadRecipeComponent inputComponent : inputRecipeComponents)
            {
                if (inputComponent.isLoaded())
                {
                    for (ItemStack tmpInputArray : inputComponent.getItemStackArray())
                    {
                        if (tmpInputArray != null)
                        {
                            finalRecipeArchiveInputArray.add(tmpInputArray.getItem().getUnlocalizedName());
                        }
                    }
                }
            }
            
            // Check if this given recipe input matches what we have in slots.
            //boolean inputArrayMatch = finalMachineInputSlotArray.containsAll(finalRecipeArchiveInputArray);
            String[] compareInput = finalMachineInputSlotArray.toArray(new String[]{});
            String[] compareMachine = finalRecipeArchiveInputArray.toArray(new String[]{});
            
            boolean inputArrayMatch = doArraysMatch(compareInput, compareMachine);
            
            // If input array matches then we will return the output.
            if (inputArrayMatch)
            {
                for (MadRecipeComponent outputComponent : outputRecipeComponents)
                {
                    if (machineOutputSlot != null && machineOutputSlot.equals(outputComponent.getSlotType()))
                    {
                        // Return the items that are associated with these input items and given output slot.
                        return outputComponent.getItemStackArray();
                    }
                }
            }
        }
        
        // Default response is to return nothing.
        return null;
    }

    private boolean doArraysMatch(String[] compareInput, String[] compareMachine)
    {
        if(compareInput.length == compareMachine.length)
        {
            for(int i = 0; i < compareInput.length; i++)
            {
                 if(!compareInput[i].equals(compareMachine[i]))
                 {
                     return false;
                 }
            }
            
            return true;
        }
        
        return false;
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
    @SuppressWarnings("ucd")
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

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        return this.isItemUsedInInputRecipes(items);
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
        super.updateEntity();
        
        if (!this.worldObj.isRemote)
        {
            // Inform Minecraft/Forge that our inventories have changed!
            if (this.inventoriesChanged)
            {
                this.onInventoryChanged();
            }
        }
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
