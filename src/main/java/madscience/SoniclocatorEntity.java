package madscience;

import java.util.ArrayList;
import java.util.List;

import madapi.container.MadSlotContainerTypeEnum;
import madapi.mod.MadModLoader;
import madapi.network.MadParticlePacket;
import madapi.product.MadTileEntityFactoryProduct;
import madapi.tile.MadTileEntityPrefab;
import madapi.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SoniclocatorEntity extends MadTileEntityPrefab
{
    /** Server only variable that determines if we are in cooldown mode */
    private boolean cooldownMode = false;

    /** Stores last known amount of targets this machine found, used for determining empty status */
    long lastKnownNumberOfTargets = 0;

    /** Stores total number of thumps this machine has made, used for determining empty status */
    long lastKnownNumberOfTotalThumps = 0;

    public SoniclocatorEntity()
    {
        super();
    }

    public SoniclocatorEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public SoniclocatorEntity(String machineName)
    {
        super(machineName);
    }

    /** Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc. */
    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
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
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null || this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if input slot 1 is gravel.
        ItemStack itemsInputSlot1 = new ItemStack(Block.gravel);
        if (!itemsInputSlot1.isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)))
        {
            return false;
        }

        // Check if output slot is same as target slot and if it is at maximum stack size or not.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null &&
            !this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2).isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1)))
        {
            return false;
        }

        // Check if output slots are empty and ready to be filled with items.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            return true;
        }

        // Check if output slot 1 is above item stack limit.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) != null &&
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize >= this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).getMaxStackSize())
        {
            return false;
        }

        return true;
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
                MadModLoader.log().info("Attempted to poison living creature and failed!");
            }
        }
    }

    @Override
    public void initiate()
    {
        super.initiate();
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
                                MadModLoader.log().info("SONICLOCATOR: Attempted to query Minecraft blocklist with value out of index.");
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
                        MadModLoader.log().info("Error while trying to locate target block!");
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
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Number of targets known.
        this.lastKnownNumberOfTargets = nbt.getLong("lastKnownNumberOfTargets");

        // Total number of thumps we have performed.
        this.lastKnownNumberOfTotalThumps = nbt.getLong("lastKnownNumberOfTotalThumps");
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        // Output 1 - Locate the target item within the given chunk.
        ItemStack craftedItem = locateTargetBlock(
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2),
                this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1),
                this.xCoord,
                this.zCoord);

        if (craftedItem == null)
        {
            this.playSoundByName("Empty");

            // We have no targets to find!
            lastKnownNumberOfTargets = 0;
            return;
        }

        // Apply wither effect to players and hurt non-players.
        this.damageNearbyCreatures(16);

        // Remove all internal energy from the device.
        if (this.getEnergy(ForgeDirection.UNKNOWN) > 0)
        {
            this.consumeInternalEnergy(this.getEnergy(ForgeDirection.UNKNOWN) / 2);
        }

        // Add target block to output slot 1.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1) == null)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1, craftedItem.copy());
        }
        else if (this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).isItemEqual(craftedItem))
        {
            this.getStackInSlotByType(MadSlotContainerTypeEnum.OUTPUT_RESULT1).stackSize += craftedItem.stackSize;
        }

        // Remove a piece of gravel from input stack 1.
        --this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize;
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1).stackSize <= 0)
        {
            this.setInventorySlotContentsByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, null);
        }
    }

    /** Update current frame of animation we should be displaying. */
    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Cooldown mode that is fired after a thump.
        if (canSmelt() && isPowered() && isRedstonePowered() && cooldownMode)
        {
            // Send a packet saying we want explosion smoke for 200 ticks at this location.
            PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadModLoader.PACKET_SEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("explode", 0.5D + this.xCoord, this.yCoord + 1.0D, this.zCoord + 0.5D, worldObj.rand.nextFloat(),
                    worldObj.rand.nextFloat() + 3.0D, worldObj.rand.nextFloat()).makePacket());

            if (this.getAnimationCurrentFrame() <= 5 && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                this.playSoundByName("CooldownBeep.ogg");

                if (this.getAnimationCurrentFrame() >= 5)
                {
                    // Check if we have exceeded the ceiling and need to reset.
                    cooldownMode = false;
                    this.setAnimationCurrentFrame(4);
                    this.setHeatLevelValue(0);

                    // Play the scary Event Horizon cooldown effect.
                    this.playSoundByName("Cooldown");
                }

                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/cooldown" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();

                // Keep the thumpers down!
                this.setHeatLevelValue(0);
            }
        }
        else if (canSmelt() && isPowered() && isRedstonePowered() && isEmptyTargetList() && !cooldownMode)
        {
            // Powered, can smelt, but no targets so we enter empty status.
            if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
                this.playSoundByName("Empty");
            }
            else
            {
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/404.png");
            }
        }
        else if (canSmelt() && isPowered() && isRedstonePowered() && !isEmptyTargetList() && !cooldownMode)
        {
            // Main state is when all four requirements have been met.
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/charge" + this.getHeatLevelTimeScaled(13) + ".png");
        }
        else if (!canSmelt() && isPowered() && !isRedstonePowered())
        {
            // Has power but still no redstone signal.
            this.setHeatLevelValue(0);
            this.setAnimationCurrentFrame(0);
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/off.png");
        }
        else if (!canSmelt() && !isPowered() && isRedstonePowered())
        {
            // Has redstone signal and can smelt but has no power.
            if (worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
            {
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
                this.playSoundByName("CooldownBeep.ogg");
                
                // Disable cooldown mode if we encounter it.
                if (cooldownMode)
                {
                    cooldownMode = false;
                    this.setAnimationCurrentFrame(0);
                    this.setHeatLevelValue(0);
                }
            }
            else
            {
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/undervolt.png");
            }
        }
        else if (!canSmelt() && isPowered() && isRedstonePowered() && !cooldownMode)
        {
            // Powered up, heater on. Just nothing inside of me!
            if (this.getAnimationCurrentFrame() <= 8 && worldObj.getWorldTime() % 5L == 0L)
            {
                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 9)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
            }
        }
        else if (!isRedstonePowered())
        {
            // Turned off.
            this.setHeatLevelValue(0);
            this.setAnimationCurrentFrame(0);
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/off.png");
        }
    }

    @Override
    public void updateEntity()
    {
        // Important to call the class below us!
        super.updateEntity();

        // Remove power from this device if we have some and also have heater enabled.
        if (this.isPowered() && this.isRedstonePowered())
        {
            // Normal consumption rate while powered.
            this.consumeInternalEnergy(this.getEnergyConsumeRate());
        }

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            // First tick for new item being cooked in furnace.
            if (this.getHeatLevelValue() == 0 && this.canSmelt() && this.isPowered() && !isEmptyTargetList() && !cooldownMode)
            {
                // Start the powerup sound.
                this.playSoundByName("ThumpStart");

                // Increments the timer to kickstart the cooking loop.
                this.incrementHeatValue();
            }
            else if (this.getHeatLevelValue() > 0 && this.canSmelt() && this.isPowered() && !isEmptyTargetList() && !cooldownMode)
            {
                // Run on server when we have items and electrical power.
                // Note: This is the main work loop for the block!

                // Increments the timer to kickstart the cooking loop.
                this.incrementHeatValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getHeatLevelValue() >= this.getHeatLevelMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.setHeatLevelValue(0);
                    this.smeltItem();

                    // Turns on a cooldown mode that lasts for five seconds.
                    cooldownMode = true;

                    // Reset the animation counter so it starts at zero.
                    this.setAnimationCurrentFrame(0);
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setHeatLevelValue(0);
            }
        }
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
        
        // Check if we should be changing pitch and volume of idle charging sound.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && !this.isEmptyTargetList() && !cooldownMode && worldObj.getWorldTime() % (MadUtils.SECOND_IN_TICKS * 1.7f) == 0L)
        {
            this.playSoundByName("IdleCharged");
        }

        // Check if we should be playing the thumper charging sound.
        if (this.canSmelt() && this.isPowered() && this.isRedstonePowered() && !cooldownMode && !this.isEmptyTargetList() && worldObj.getWorldTime() % MadUtils.SECOND_IN_TICKS == 0L)
        {
            this.playSoundByName("ThumpCharge");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        // Last known number of targets.
        nbt.setLong("lastKnownNumberOfTargets", this.lastKnownNumberOfTargets);

        // Total number of thumps.
        nbt.setLong("lastKnownNumberOfTotalThumps", this.lastKnownNumberOfTotalThumps);
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

class SoniclocatorTargetBlock
{
    ItemStack foundItem;
    int targetX;
    int targetY;
    int targetZ;

    SoniclocatorTargetBlock(int posX, int posY, int posZ, ItemStack chunkItem)
    {
        // Position of the block we want to replace.
        targetX = posX;
        targetY = posY;
        targetZ = posZ;

        // ItemStack we examined in the world.
        foundItem = chunkItem;
    }
}
