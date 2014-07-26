package madscience.tile;

import madscience.MadConfig;
import madscience.MadForgeMod;
import madscience.factory.mod.MadMod;
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

public class ClayfurnaceEntity extends MadTileEntityPrefab
{
    boolean hasBeenLit = false;
    boolean hasStoppedSmoldering = false;
    boolean hasCompletedBurnCycle = false;
    boolean hasCooledDown = false;

    public ClayfurnaceEntity()
    {
        super();
    }

    public ClayfurnaceEntity(MadTileEntityFactoryProduct registeredMachine)
    {
        super(registeredMachine);
    }

    public ClayfurnaceEntity(String machineName)
    {
        super(machineName);
    }
    
    @Override
    public boolean canSmelt()
    {
        super.canSmelt();
        
        // If we have already completed a burn cycle we are done smelting.
        if (this.hasCompletedBurnCycle)
        {
            return false;
        }

        // Check if input slots are empty.
        if (this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1) == null ||
            this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2) == null)
        {
            return false;
        }

        // Check if input slot 1 is a block of coal.
        ItemStack itemsInputSlot1 = new ItemStack(Block.coalBlock);
        if (!itemsInputSlot1.isItemEqual(this.getStackInSlotByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1)))
        {
            return false;
        }

        return true;
    }

    ItemStack createEndResult()
    {
        // Get the final form of the inputed block will be from recipe list.
        ItemStack[] convertedOreRecipe = this.getRecipeResult(MadSlotContainerTypeEnum.INPUT_INGREDIENT1, MadSlotContainerTypeEnum.INPUT_INGREDIENT2, MadSlotContainerTypeEnum.OUTPUT_RESULT1);

        if (convertedOreRecipe == null)
        {
            return null;
        }

        // Remove block of coal from input slot 1.
        this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1), 1);

        // Remove input ore that we used to convert.
        this.decrStackSize(this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2), 1);
        
        return convertedOreRecipe[0];
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

    /** Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack items)
    {
        super.isItemValidForSlot(slot, items);
        
        // Check if machine trying to insert item into given slot is allowed.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT1))
        {
            // Input slot 1 - Coal block.
            ItemStack itemsInputSlot1 = new ItemStack(Block.coalBlock);
            if (itemsInputSlot1.isItemEqual(items))
            {
                return true;
            }
        }

        // Input slot 2 - Defined recipe for ore into block form.
        if (slot == this.getSlotIDByType(MadSlotContainerTypeEnum.INPUT_INGREDIENT2))
        {
            if (this.isItemUsedInInputRecipes(items))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        // Determines if this block has completed a full cooking cycle.
        this.hasCompletedBurnCycle = nbt.getBoolean("hasCompletedBurnCycle");

        // Determines if this block has cooled off from it's molten state.
        this.hasCooledDown = nbt.getBoolean("hasCooledDown");

        // Determines if we have been hit by the player after finished burn cycle.
        this.hasStoppedSmoldering = nbt.getBoolean("hasStoppedSmoldering");

        // Determines if this block has been caught on fire by a flint and steel.
        this.hasBeenLit = nbt.getBoolean("hasBeenLit");
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
        MadMod.log().info("Attempting to light clay furnace at " + this.xCoord + ", " + this.yCoord + ", " + this.zCoord);
        hasBeenLit = true;
    }

    @Override
    public void smeltItem()
    {
        super.smeltItem();
        
        if (this.canSmelt())
        {
            this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);

            // Flips a bool that triggers the next sequence of events for clay furnace.
            this.hasCompletedBurnCycle = true;
        }
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        
        // Active state has many textures based on item cook progress.
        if (this.hasCooledDown)
        {
            // COOLED DOWN (WAITING FOR PLAYER TO HIT US)
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/shell.png");
        }
        if (!this.canSmelt() && this.hasBeenLit && this.hasCompletedBurnCycle && hasStoppedSmoldering && !this.hasCooledDown)
        {
            // COOL DOWN (RED HOT MODE)
            if (this.getAnimationCurrentFrame() <= 4 && worldObj.getWorldTime() % (MadUtils.SECOND_IN_TICKS * 5) == 0L)
            {
                // Same one as before.
                this.createRandomSmoke();
                this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.fizz", 1.0F, 1.0F);

                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/redhot" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 5)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);
                this.hasCooledDown = true;
            }
        }
        else if (!this.canSmelt() && this.hasBeenLit && this.hasCompletedBurnCycle && !hasStoppedSmoldering && !this.hasCooledDown)
        {
            // SMOLDERING FURNACE MODE
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/done.png");

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

            if (this.getAnimationCurrentFrame() <= 3 && worldObj.getWorldTime() % 25L == 0L)
            {
                // Send a packet saying we want puffs of smoke used in minecart furnace.
                PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, MadConfig.PACKETSEND_RADIUS, worldObj.provider.dimensionId, new MadParticlePacket("largesmoke", 0.5D + this.xCoord, this.yCoord + 0.5D, this.zCoord + 0.5D,
                        worldObj.rand.nextFloat(), worldObj.rand.nextFloat() + 3.0D, worldObj.rand.nextFloat()).makePacket());

                // Load this texture onto the entity.
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/work" + this.getAnimationCurrentFrame() + ".png");

                // Update animation frame.
                this.incrementAnimationCurrentFrame();
            }
            else if (this.getAnimationCurrentFrame() >= 4)
            {
                // Check if we have exceeded the ceiling and need to reset.
                this.setAnimationCurrentFrame(0);

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
            this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/idle.png");
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Server side processing for furnace.
        if (!this.worldObj.isRemote)
        {
            this.updateModel();
            
            // First tick for new item being cooked in furnace.
            if (this.getProgressValue() == 0 && this.canSmelt() && this.hasBeenLit)
            {
                this.setProgressMaximum(MadUtils.SECOND_IN_TICKS * 420);

                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();
            }
            else if (this.getProgressValue() > 0 && this.canSmelt() && this.hasBeenLit)
            {
                // Increments the timer to kickstart the cooking loop.
                this.incrementProgressValue();

                // Check if furnace has exceeded total amount of time to cook.
                if (this.getProgressValue() >= this.getProgressMaximum())
                {
                    // Convert one item into another via 'cooking' process.
                    this.setProgressValue(0);
                    this.smeltItem();
                }
            }
            else
            {
                // Reset loop, prepare for next item or closure.
                this.setProgressValue(0);
            }
        }
    }

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
    }
    
    public void updateModel()
    {
        this.hideAllModelPieces();
        
        // Determine if we are a clay furnace still or red hot or post cooldown mode.
        if (!this.hasStoppedSmoldering)
        {
            // IDLE OR WORKING
            this.setModelWorldRenderVisibilityByName("MoltenBlock", false);
            this.setModelWorldRenderVisibilityByName("MoltenBlockShell", false);
            this.setModelWorldRenderVisibilityByName("clayFurnace", true);
        }
        else if (this.hasStoppedSmoldering && !this.hasCooledDown)
        {
            // RED HOT BLOCK
            this.setModelWorldRenderVisibilityByName("MoltenBlock", true);
            this.setModelWorldRenderVisibilityByName("MoltenBlockShell", false);
            this.setModelWorldRenderVisibilityByName("clayFurnace", false);
        }
        else if (this.hasStoppedSmoldering && this.hasCooledDown)
        {
            // COOLDOWN MODE AFTER RED HOT STATUS ENDS
            this.setModelWorldRenderVisibilityByName("MoltenBlock", false);
            this.setModelWorldRenderVisibilityByName("MoltenBlockShell", true);
            this.setModelWorldRenderVisibilityByName("clayFurnace", false);
        }
    }

    @Override
    public void updateSound()
    {
        super.updateSound();
    }

    @Override
    public void initiate()
    {
        super.initiate();
    }

    @Override
    public void onBlockRightClick(World world, int x, int y, int z, EntityPlayer player)
    {
        super.onBlockRightClick(world, x, y, z, player);
        
        // Check if the player is using flint and steel on us.
        ItemStack compareFlintNSteel = new ItemStack(Item.flintAndSteel);
        ItemStack playerItem = player.getCurrentEquippedItem();
        
        // Determine what we should do to the clay furnace depending on it's current state.
        if (!this.hasBeenLit)
        {
            // IDLE OR WORKING
            if (player != null && playerItem != null && playerItem.itemID == compareFlintNSteel.itemID)
            {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, 1.0F);
                this.setLitStatus(true);
                if (playerItem.getItemDamage() < playerItem.getMaxDamage())
                {
                    player.getCurrentEquippedItem().attemptDamageItem(1, world.rand);
                }
                return;
            }
            else
            {
                player.openGui(MadForgeMod.instance, this.getRegisteredMachine().getBlockID(), world, x, y, z);
                return;
            }
        }
        else if (this.hasBeenLit && this.hasCompletedBurnCycle && !this.hasStoppedSmoldering && !this.hasCooledDown)
        {
            // SMOLDERING FURNACE MODE
            return;
        }
        else if (this.hasBeenLit && this.hasCompletedBurnCycle && this.hasStoppedSmoldering && !this.hasCooledDown)
        {
            // RED HOT BLOCK MODE
            return;
        }
        else
        {
            return;
        }
    }

    @Override
    public void onBlockLeftClick(World world, int x, int y, int z, EntityPlayer player)
    {
        super.onBlockLeftClick(world, x, y, z, player);
        
        if (this.hasCooledDown)
        {
            // COOLED OFF MODE - WAITING FOR PLAYER TO HIT US
            if (player.canHarvestBlock(this.blockType))
            {
                MadMod.log().info("Clay Furnace: Harvested player after having been cooled down!");
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.anvil_land", 1.0F, 1.0F);

                // Set ourselves to the end result we should be!
                ItemStack finalForm = this.createEndResult();
                world.setBlock(x, y, z, finalForm.itemID);
            }
            return;
        }

        // Player broke the red hot block before it was completely cooled off.
        if (this.hasStoppedSmoldering && !this.hasCooledDown)
        {
            // RED HOT MODE
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "liquid.lavapop", 1.0F, 1.0F);
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 1.0F, 1.0F);
            world.setBlock(x, y, z, Block.lavaStill.blockID);
            return;
        }

        if (this.hasBeenLit && this.hasCompletedBurnCycle && !this.hasStoppedSmoldering && !this.hasCooledDown)
        {
            // SMOLDERING FURNACE MODE
            if (player.canHarvestBlock(this.blockType))
            {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.sand", 1.0F, 1.0F);
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 1.0F, 1.0F);
                this.setTextureRenderedOnModel("models/" + this.getMachineInternalName() + "/redhot0.png");
                this.hasStoppedSmoldering = true;
                this.setAnimationCurrentFrame(0);
                return;
            }
        }
    }
}
