package madscience.tile.soniclocator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.factory.mod.MadMod;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.network.MadParticlePacket;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SoniclocatorEntity extends MadTileEntityPrefab implements ISidedInventory, IInventory
{
    private static final int[] slots_bottom = new int[]
    { 2, 1 };

    private static final int[] slots_sides = new int[]
    { 1 };
    private static final int[] slots_top = new int[]
    { 0 };
    

    /** Server only variable that determines if we are in cooldown mode */
    private boolean cooldownMode = false;

    /** Current frame of animation we should use to display in world. */
    private int curFrame;

    /** Maximum allowed heat value and also when the machine is considered ready. */
    int currentHeatMaximum = 400;

    /** Current level of heat that the machine has accumulated while powered and active. */
    int currentHeatValue;

    /** Stores last known amount of targets this machine found, used for determining empty status */
    long lastKnownNumberOfTargets = 0;

    /** Stores total number of thumps this machine has made, used for determining empty status */
    long lastKnownNumberOfTotalThumps = 0;

    /** Determines if we currently should be playing animation frames every tick or not. */
    private boolean shouldPlay;

    private ItemStack[] soniclocatorInput = new ItemStack[2];

    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] soniclocatorOutput = new ItemStack[1];

    /** Texture that should be displayed on our model. */
    String soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/off.png";

    public SoniclocatorEntity()
    {
        super(MadFurnaces.SONICLOCATOR_INTERNALNAME);
    }

    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override
    public boolean canExtractItem(int slot, ItemStack items, int side)
    {
        // Extract output from the bottom of the block.
        if (slot == 3 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
            return true;
        }
        else if (slot == 4 && ForgeDirection.getOrientation(side) == ForgeDirection.WEST)
        {
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

    private void damageNearbyCreatures(int range)
    {
        double px = this.xCoord;
        double py = this.yCoord;
        double pz = this.zCoord;

        List l = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(px - range, py - range, pz - range, px + range, py + range, pz + range));

        if (l != null && l.isEmpty())
        {
            // MadMod.logger.info("No nearby players detected!");
            return;
        }

        for (int i = 0; i < l.size(); ++i)
        {
            try
            {
                if (l.get(i) instanceof EntityLivingBase)
                {
                    // Players
                    EntityLivingBase x = (EntityLivingBase) l.get(i);
                    if (x != null)
                    {
                        x.addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                        x.addPotionEffect(new PotionEffect(Potion.blindness.id, 60));
                        x.addPotionEffect(new PotionEffect(Potion.confusion.id, 200));
                    }
                }
                else if (l.get(i) instanceof EntityLiving)
                {
                    // Mobs
                    EntityLiving x = (EntityLiving) l.get(i);
                    if (x != null)
                    {
                        x.addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                    }
                }

            }
            catch (Exception err)
            {
                MadMod.LOGGER.info("Attempted to poison living creature and failed!");
            }
        }
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
            // Input stack 1
            return DecreaseInputSlot(0, numItems);
        }
        else if (slot == 1)
        {
            // Input stack 2
            return DecreaseInputSlot(1, numItems);
        }
        else if (slot == 2)
        {
            // Output stack 1
            return DecreaseOutputSlot(0, numItems);
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

    public float getHeatLevel()
    {
        // Returns current level of heat stored inside of the machine.
        return currentHeatValue;
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
        return MadFurnaces.SONICLOCATOR_INTERNALNAME;
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
            // Input slot 1 - Block of gravel.
            return this.soniclocatorInput[0];
        }
        else if (slot == 1)
        {
            // Input slot 2 - Target block we want to locate in the chunk.
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

    @Override
    public void initiate()
    {
        // Ensure the class below us is called.
        super.initiate();

        // Sound that is played when we are placed into the world.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_PLACE, 1.0F, 1.0F);

        if (!this.worldObj.isRemote)
        {
            // Store this information in the location registry.
            SoniclocatorLocationRegistry.addLocation(new SoniclocatorLocationItem(this.xCoord, this.yCoord, this.zCoord));
        }
    }

    public boolean isEmptyTargetList()
    {
        // Check if we are unable to locate any more target materials in this chunk.
        if (lastKnownNumberOfTotalThumps > 0 && lastKnownNumberOfTargets <= 0)
        {
            return true;
        }

        return false;
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
        // Check if input slot 1 is a block of gravel ready to replace target block.
        if (slot == 0)
        {
            // Input slot 1 - Gravel.
            ItemStack compareItem = new ItemStack(Block.gravel);
            if (compareItem.isItemEqual(items))
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

    private ItemStack locateTargetBlock(ItemStack targetItem, ItemStack replacementItem, int blockX, int blockZ)
    {
        // Skip client worlds.
        if (worldObj.isRemote)
        {
            return null;
        }

        Chunk chunk = worldObj.getChunkFromBlockCoords(blockX, blockZ);

        // If we cannot find the chunk then return nothing.
        if (chunk == null)
        {
            return null;
        }

        // Create a temporary list to let us sort through the number of targets.
        List<SoniclocatorTargetBlock> targetList = new ArrayList<SoniclocatorTargetBlock>();
        ItemStack oreDictTargetStack = null;

        // Loop through chunk we are in looking for target block(s).
        int chunkX = chunk.xPosition * 16;
        int chunkZ = chunk.zPosition * 16;

        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                for (int k = 0; k < 128; ++k)
                {
                    // Located a target block.
                    int targetX = chunkX + i;
                    int targetY = k;
                    int targetZ = chunkZ + j;
                    int blockID = worldObj.getBlockId(targetX, targetY, targetZ);
                    
                    try
                    {
                        if (blockID > 0)
                        {
                            try
                            {
                                // Valid block, check if equal to target block.
                                ItemStack compareChucnkItem = new ItemStack(Block.blocksList[blockID]);
                                if (compareChucnkItem != null && compareChucnkItem.isItemEqual(targetItem))
                                {
                                    // Add the processed item to our list for processing outside this loop.
                                    targetList.add(new SoniclocatorTargetBlock(targetX, targetY, targetZ, compareChucnkItem));
                                    //MadMod.logger.info("[Soniclocator] Located Vanilla Block " + String.valueOf(compareChucnkItem));
                                    continue;
                                }
                            }
                            catch (Exception err)
                            {
                                MadMod.LOGGER.info("SONICLOCATOR: Attempted to query Minecraft blocklist with value out of index.");
                            }

                            // Check if the target block is inside the OreDictionary if first query fails.
                            if (targetItem.itemID == blockID)
                            {
                                int oreID = OreDictionary.getOreID(targetItem);
                                if (oreID != -1)
                                {
                                    ArrayList<ItemStack> oreDictOres = OreDictionary.getOres(oreID);
                                    if (oreDictOres != null)
                                    {
                                        for (ItemStack someItem : oreDictOres)
                                        {
                                            if (OreDictionary.itemMatches(someItem, targetItem, true) &&
                                                    someItem.getItemDamage() == targetItem.getItemDamage() &&
                                                    someItem.getDisplayName().equals(targetItem.getDisplayName()))
                                            {
                                                oreDictTargetStack = targetItem;
                                                targetList.add(new SoniclocatorTargetBlock(targetX, targetY, targetZ, targetItem));
                                                //MadMod.logger.info("[Soniclocator] Located OreDict Block " + someItem.getDisplayName());
                                                continue;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception err)
                    {
                        MadMod.LOGGER.info("Error while trying to locate target block!");
                        continue;
                    }
                }
            }
        }

        // Increase the number of thumps that we have done.
        lastKnownNumberOfTotalThumps++;

        // No point in moving forward if there is no data to process...
        if (targetList.size() <= 0)
        {
            // Zero our the target list so we can keep track of an empty state in clean way.
            //MadMod.logger.info("No targets found in this chunk or we have eaten them all!");
            lastKnownNumberOfTargets = 0;
            return null;
        }

        // Alter the number of located targets from zero to something else.
        lastKnownNumberOfTargets = targetList.size();

        // If we have more than one item we can randomly pick which one to take so it's not predictable.
        // Note: Fox magic occurred here!
        SoniclocatorTargetBlock value = targetList.get(worldObj.rand.nextInt(targetList.size()));

        // Create an explosion at the source of the target block we stole.
        worldObj.createExplosion(null, value.targetX, value.targetY, value.targetZ, 0.42F, false);

        // Create another explosion at the source tile entity thumper.
        worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 0.42F, false);

        // Update the lighting engine about our changes to the chunk.
        if (worldObj.getBlockId(value.targetX, value.targetY, value.targetZ) == targetItem.itemID && oreDictTargetStack == null)
        {
            worldObj.setBlock(value.targetX, value.targetY, value.targetZ, replacementItem.itemID, 0, 3);
            worldObj.updateAllLightTypes(value.targetX, value.targetY, value.targetZ);
            worldObj.markBlockForUpdate(value.targetX, value.targetY, value.targetZ);
            return value.foundItem;
        }
        else if (oreDictTargetStack != null)
        {
            worldObj.setBlock(value.targetX, value.targetY, value.targetZ, replacementItem.itemID, 0, 3);
            worldObj.updateAllLightTypes(value.targetX, value.targetY, value.targetZ);
            worldObj.markBlockForUpdate(value.targetX, value.targetY, value.targetZ);
            return oreDictTargetStack;
        }
        
        return null;
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

        // Number of targets known.
        this.lastKnownNumberOfTargets = nbt.getLong("lastKnownNumberOfTargets");

        // Total number of thumps we have performed.
        this.lastKnownNumberOfTotalThumps = nbt.getLong("lastKnownNumberOfTotalThumps");
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
        // Reset the internal counters for thumper control since internal items have been messed with.
        lastKnownNumberOfTargets = 0;
        lastKnownNumberOfTotalThumps = 0;

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

    @Override
    public void smeltItem()
    {
        // Find out if there are any other Soniclocators nearby, and if so kill us all!
        locateNearbySoniclocators(2600);

        // Output 1 - Locate the target item within the given chunk.
        ItemStack craftedItem = locateTargetBlock(this.soniclocatorInput[1], this.soniclocatorInput[0], this.xCoord, this.zCoord);

        if (craftedItem == null)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_EMPTY, 1.0F, 1.0F);

            // We have no targets to find!
            lastKnownNumberOfTargets = 0;
            return;
        }

        // First sound is of the thump releasing all the energy in the machine.
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_THUMP, 10.0F, 1.0F);

        // Apply wither effect to players and hurt non-players.
        damageNearbyCreatures(16);

        // Remove all internal energy from the device.
        if (this.getEnergy(ForgeDirection.UNKNOWN) > 0)
        {
            this.consumeEnergy(this.getEnergy(ForgeDirection.UNKNOWN) / 2);
        }

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

        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_FINISH, 10.0F, 1.0F);
    }

    private void locateNearbySoniclocators(int maxRange)
    {
        try
        {
            // Queries our Soniclocator registry for information about other known locations of the devices.
            Iterator<SoniclocatorLocationItem> listIterator = SoniclocatorLocationRegistry.otherSoniclocators.iterator();
            while (listIterator.hasNext())
            {
                SoniclocatorLocationItem locationItem = listIterator.next();
                if (locationItem == null)
                {
                    return;
                }

                long distanceBetweenMachines = Math.abs(SoniclocatorLocationRegistry.queryDistanceBetweenSonicLocators(new SoniclocatorLocationItem(this.xCoord, this.yCoord, this.zCoord), locationItem));
                // MadMod.logger.info("DISTANCE BETWEEN MACHINES: " + distanceBetweenMachines);

                // We got the message to abort and everything is fine!
                if (distanceBetweenMachines == 0)
                {
                    return;
                }

                // There can never be zero distance between objects, so do not run on zero. -Fox
                if (distanceBetweenMachines < maxRange)
                {
                    this.worldObj.playSoundEffect(locationItem.posX + 0.5D, locationItem.posY + 0.5D, locationItem.posZ + 0.5D, SoniclocatorSounds.SONICLOCATOR_EXPLODE, 1.0F, 1.0F);
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_EXPLODE, 1.0F, 1.0F);
                    this.worldObj.destroyBlock(locationItem.posX, locationItem.posY, locationItem.posZ, false);
                    this.worldObj.destroyBlock(this.xCoord, this.yCoord, this.zCoord, false);
                    this.worldObj.createExplosion((Entity) null, locationItem.posX, locationItem.posY, locationItem.posZ, 6, true);
                    this.worldObj.createExplosion((Entity) null, this.xCoord, this.yCoord, this.zCoord, 6, true);
                }
            }
        }
        catch (Exception err)
        {
            // MadMod.logger.info("Minecraft has failed me!");
            return;
        }
    }

    /** Update current frame of animation we should be displaying. */
    @Override
    public void updateAnimation()
    {
        // Cooldown mode that is fired after a thump.
        if (canSmelt() && isPowered() && isRedstonePowered() && cooldownMode)
        {
            // Send a packet saying we want explosion smoke for 200 ticks at this location.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("explode", 0.5D + this.xCoord, this.yCoord + 1.0D, this.zCoord + 0.5D, worldObj.rand.nextFloat(),
                    worldObj.rand.nextFloat() + 3.0D, worldObj.rand.nextFloat()).makePacket());

            if (curFrame <= 5 && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_COOLDOWNBEEP, 1.0F, 1.0F);

                if (curFrame >= 5)
                {
                    // Check if we have exceeded the ceiling and need to reset.
                    cooldownMode = false;
                    curFrame = 4;
                    currentHeatValue = 0;

                    // Play the scary Event Horizon cooldown effect.
                    this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_COOLDOWN, 1.0F, 1.0F);
                }

                // Load this texture onto the entity.
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/cooldown" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;

                // Keep the thumpers down!
                currentHeatValue = 0;
            }
        }
        else if (canSmelt() && isPowered() && isRedstonePowered() && isEmptyTargetList() && !cooldownMode)
        {
            // Powered, can smelt, but no targets so we enter empty status.
            if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                // Load this texture onto the entity.
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/idle.png";
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_EMPTY, 1.0F, 1.0F);
            }
            else
            {
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/404.png";
            }
        }
        else if (canSmelt() && isPowered() && isRedstonePowered() && !isEmptyTargetList() && !cooldownMode)
        {
            // Main state is when all four requirements have been met.
            soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/charge" + this.getHeatLevelTimeScaled(13) + ".png";
        }
        else if (!canSmelt() && isPowered() && !isRedstonePowered())
        {
            // Has power but still no redstone signal.
            currentHeatValue = 0;
            curFrame = 0;
            soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/off.png";
        }
        else if (!canSmelt() && !isPowered() && isRedstonePowered())
        {
            // Has redstone signal and can smelt but has no power.
            if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/idle.png";
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_COOLDOWNBEEP, 1.0F, 1.0F);
                
                // Disable cooldown mode if we encounter it.
                if (cooldownMode)
                {
                    cooldownMode = false;
                    curFrame = 0;
                    currentHeatValue = 0;
                }
            }
            else
            {
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/undervolt.png";
            }
        }
        else if (!canSmelt() && isPowered() && isRedstonePowered() && !cooldownMode)
        {
            // Powered up, heater on. Just nothing inside of me!
            if (curFrame <= 8 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                soniclocatorTexture = "models/" + MadFurnaces.SONICLOCATOR_INTERNALNAME + "/idle" + curFrame + ".png";

                // Update animation frame.
                ++curFrame;
            }
            else if (curFrame >= 9)
            {
                // Check if we have exceeded the ceiling and need to reset.
                curFrame = 0;
            }
        }
        else if (!isRedstonePowered())
        {
            // Turned off.
            currentHeatValue = 0;
            curFrame = 0;
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
            // Normal consumption rate while powered.
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
            if (this.currentHeatValue == 0 && this.canSmelt() && this.isPowered() && !isEmptyTargetList() && !cooldownMode)
            {
                // Start the powerup sound.
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_THUMPSTART, 0.42F, 1.0F);

                // Increments the timer to kickstart the cooking loop.
                this.currentHeatValue++;
            }
            else if (this.currentHeatValue > 0 && this.canSmelt() && this.isPowered() && !isEmptyTargetList() && !cooldownMode)
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

                    // Turns on a cooldown mode that lasts for five seconds.
                    cooldownMode = true;

                    // Reset the animation counter so it starts at zero.
                    curFrame = 0;

                    // Yes minecraft, things have changed.
                    inventoriesChanged = true;
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.currentHeatValue = 0;
            }

            // Update status of machine to all clients around us.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new SoniclocatorPackets(this.xCoord, this.yCoord, this.zCoord, getEnergy(ForgeDirection.UNKNOWN),
                    getEnergyCapacity(ForgeDirection.UNKNOWN), this.currentHeatValue, this.currentHeatMaximum, this.lastKnownNumberOfTargets, this.lastKnownNumberOfTotalThumps, this.soniclocatorTexture).makePacket());
        }

        if (inventoriesChanged)
        {
            this.onInventoryChanged();
        }
    }

    @Override
    public void updateSound()
    {
        // Check if we should be playing the idle sound.
        if (this.canSmelt() && this.isPowered() && worldObj.getWorldTime() % (MadUtils.SECOND_IN_TICKS * 2.3f) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_IDLE, 1.0F, 1.0F);
        }

        // Check if we should be changing pitch and volume of idle charging sound.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && !this.isEmptyTargetList() && !cooldownMode && worldObj.getWorldTime() % (MadUtils.SECOND_IN_TICKS * 1.7f) == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_IDLECHARGED, 0.42F, (this.currentHeatValue * 0.1f));
        }

        // Check if we should be playing the thumper charging sound.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && !cooldownMode && !this.isEmptyTargetList() && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, SoniclocatorSounds.SONICLOCATOR_THUMPCHARGE, 0.42F, 1.0F);
        }
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Last known number of targets.
        nbt.setLong("lastKnownNumberOfTargets", this.lastKnownNumberOfTargets);

        // Total number of thumps.
        nbt.setLong("lastKnownNumberOfTotalThumps", this.lastKnownNumberOfTotalThumps);

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
    }
}
