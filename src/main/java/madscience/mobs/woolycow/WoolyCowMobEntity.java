package madscience.mobs.woolycow;

import java.util.ArrayList;
import java.util.Random;

import madscience.MadSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Note: If your mob swims, change EntityAnimal to EntityWaterMob.
public class WoolyCowMobEntity extends EntityAnimal implements IShearable
{
    /** Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece. */
    public static final float[][] fleeceColorTable = new float[][]
    {
    { 1.0F, 1.0F, 1.0F },
    { 0.85F, 0.5F, 0.2F },
    { 0.7F, 0.3F, 0.85F },
    { 0.4F, 0.6F, 0.85F },
    { 0.9F, 0.9F, 0.2F },
    { 0.5F, 0.8F, 0.1F },
    { 0.95F, 0.5F, 0.65F },
    { 0.3F, 0.3F, 0.3F },
    { 0.6F, 0.6F, 0.6F },
    { 0.3F, 0.5F, 0.6F },
    { 0.5F, 0.25F, 0.7F },
    { 0.2F, 0.3F, 0.7F },
    { 0.4F, 0.3F, 0.2F },
    { 0.4F, 0.5F, 0.2F },
    { 0.6F, 0.2F, 0.2F },
    { 0.1F, 0.1F, 0.1F } };

    /** This method is called when a sheep spawns in the world to select the color of sheep fleece. */
    public static int getRandomFleeceColor(Random par0Random)
    {
        int i = par0Random.nextInt(100);
        return i < 5 ? 15 : (i < 10 ? 7 : (i < 15 ? 8 : (i < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
    }

    /** The eat grass AI task for this mob. */
    private EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

    private final InventoryCrafting sheepContainer = new InventoryCrafting(new WoolyCowContainer(this), 5, 1);

    /** Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each tick. */
    private int sheepTimer;

    public WoolyCowMobEntity(World par1World)
    {
        super(par1World);

        // This is the hitbox size. Starts in the center and grows outwards.
        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.05D));
        this.tasks.addTask(5, this.aiEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.sheepContainer.setInventorySlotContents(0, new ItemStack(Item.dyePowder, 1, 0));
        this.sheepContainer.setInventorySlotContents(1, new ItemStack(Item.dyePowder, 1, 0));
        this.sheepContainer.setInventorySlotContents(2, new ItemStack(Item.dyePowder, 1, 0));
        this.sheepContainer.setInventorySlotContents(3, new ItemStack(Item.dyePowder, 1, 0));
        this.sheepContainer.setInventorySlotContents(4, new ItemStack(Item.dyePowder, 1, 0));
    }

    @Override
    protected void applyEntityAttributes()
    {
        // Changes basic attributes about the mob to make it different than
        // others.
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.21000000298023224D);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int i = 7;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    // This is required regardless of if your animal can breed or not. Set to
    // null if it can't breed - I wont cover breeding here.
    @Override
    public EntityAgeable createChild(EntityAgeable var1)
    {
        return null;
    }

    /** Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param par2 - Level of Looting used to kill this mob. */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        if (!this.getSheared())
        {
            this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 0.0F);
        }

        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
        int k;

        for (k = 0; k < j; ++k)
        {
            this.dropItem(Item.leather.itemID, 1);
        }

        j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

        for (k = 0; k < j; ++k)
        {
            if (this.isBurning())
            {
                this.dropItem(Item.beefCooked.itemID, 1);
            }
            else
            {
                this.dropItem(Item.beefRaw.itemID, 1);
            }
        }
    }

    /** This function applies the benefits of growing back wool and faster growing up to the acting entity. (This function is used in the AIEatGrass) */
    @Override
    public void eatGrassBonus()
    {
        this.setSheared(false);

        if (this.isChild())
        {
            this.addGrowth(60);
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }

    public WoolyCowMobEntity func_90015_b(EntityAgeable par1EntityAgeable)
    {
        WoolyCowMobEntity entitysheep = (WoolyCowMobEntity) par1EntityAgeable;
        WoolyCowMobEntity entitysheep1 = new WoolyCowMobEntity(this.worldObj);
        int i = this.updateSheepContainer(this, entitysheep);
        entitysheep1.setFleeceColor(15 - i);
        return entitysheep1;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k)
    {
        /* This function expands Goats to move differently depending on the circumstances. :Mob: When they are low on HP, they will move much farther distances at a time. :Mob: During night time, they will move very short distances at a time. :Mob: During
         * the day time, while they have high HP, they will prefer higher altitudes. */

        if (inWater)
        {
            // No preference while swimming!
            return 0.0F;
        }
        else
        {
            if (this.getHealth() < 5)
            {
                return MathHelper.floor_double(getDistance(i, j, k));
            }
            else if (!worldObj.isDaytime())
            {
                return -(MathHelper.floor_double(getDistance(i, j, k)));
            }
            else
            {
                return j;
            }
        }
    }

    // The sound made when it actually dies.
    @Override
    protected String getDeathSound()
    {
        return MadSounds.WOOLYCOW_HURT;
    }

    /** Returns the item ID for the item the mob drops on death. */
    @Override
    protected int getDropItemId()
    {
        return Item.leather.itemID;
    }

    public int getFleeceColor()
    {
        return this.dataWatcher.getWatchableObjectByte(16) & 15;
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationX(float par1)
    {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36)
        {
            float f1 = (this.sheepTimer - 4 - par1) / 32.0F;
            return ((float) Math.PI / 5F) + ((float) Math.PI * 7F / 100F) * MathHelper.sin(f1 * 28.7F);
        }
        else
        {
            return this.sheepTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch / (180F / (float) Math.PI);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationY(float par1)
    {
        return this.sheepTimer <= 0 ? 0.0F : (this.sheepTimer >= 4 && this.sheepTimer <= 36 ? 1.0F : (this.sheepTimer < 4 ? (this.sheepTimer - par1) / 4.0F : -(this.sheepTimer - 40 - par1) / 4.0F));
    }

    // The sound made when it's attacked. Often it's the same as the normal say
    // sound, but sometimes different (such as in the ender dragon)
    @Override
    protected String getHurtSound()
    {
        return MadSounds.WOOLYCOW_HURT;
    }

    // The sound effect played when it's just living, like a cow mooing.
    @Override
    protected String getLivingSound()
    {
        return MadSounds.WOOLYCOW_SAY;
    }

    /** returns true if a sheeps wool has been sheared */
    public boolean getSheared()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
    }

    @Override
    protected float getSoundVolume()
    {
        // Allows us to change how loud this particular entity is.
        return 0.4F;
    }

    private int getWoolColor(EntityAnimal par1EntityAnimal)
    {
        return 15 - ((WoolyCowMobEntity) par1EntityAnimal).getFleeceColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    /** Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig. */
    @Override
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

        if (itemstack != null && itemstack.itemID == Item.bucketEmpty.itemID && !par1EntityPlayer.capabilities.isCreativeMode)
        {
            if (itemstack.stackSize-- == 1)
            {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
            }
            else if (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketMilk)))
            {
                par1EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketMilk.itemID, 1, 0));
            }

            return true;
        }
        else
        {
            return super.interact(par1EntityPlayer);
        }
    }

    // This is required. If it's false, none of the above takes effect.
    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public boolean isShearable(ItemStack item, World world, int X, int Y, int Z)
    {
        return !getSheared() && !isChild();
    }

    /** Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn. */
    @Override
    public void onLivingUpdate()
    {
        if (this.worldObj.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int X, int Y, int Z, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        setSheared(true);
        int i = 1 + rand.nextInt(3);
        for (int j = 0; j < i; j++)
        {
            ret.add(new ItemStack(Block.cloth.blockID, 1, getFleeceColor()));
        }
        this.worldObj.playSoundAtEntity(this, "mob.sheep.shear", 1.0F, 1.0F);
        return ret;
    }

    @Override
    public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
        return par1EntityLivingData;
    }

    /** Plays step sound at given x, y, z for the entity */
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound(MadSounds.WOOLYCOW_STEP, 0.15F, 1.0F);
    }

    /** (abstract) Protected helper method to read subclass entity data from NBT. */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
    }

    // Sets the active target the Task system uses for tracking.
    @Override
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
    {
        super.setAttackTarget(par1EntityLivingBase);
    }

    public void setFleeceColor(int par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & 240 | par1 & 15)));
    }

    /** make a sheep sheared if set to true */
    public void setSheared(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 16)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -17)));
        }
    }

    @Override
    protected void updateAITasks()
    {
        this.sheepTimer = this.aiEatGrass.getEatGrassTick();
        super.updateAITasks();
    }

    private int updateSheepContainer(EntityAnimal par1EntityAnimal, EntityAnimal par2EntityAnimal)
    {
        int woolColorCode1 = this.getWoolColor(par1EntityAnimal);
        int woolColorCode2 = this.getWoolColor(par1EntityAnimal);

        this.sheepContainer.getStackInSlot(0).setItemDamage(woolColorCode1);
        this.sheepContainer.getStackInSlot(1).setItemDamage(woolColorCode2);
        this.sheepContainer.getStackInSlot(2).setItemDamage(woolColorCode1);
        this.sheepContainer.getStackInSlot(3).setItemDamage(woolColorCode2);
        this.sheepContainer.getStackInSlot(4).setItemDamage(woolColorCode1);

        ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(this.sheepContainer, ((WoolyCowMobEntity) par1EntityAnimal).worldObj);
        int newWoolColor;

        if (itemstack != null && itemstack.getItem().itemID == Item.dyePowder.itemID)
        {
            newWoolColor = itemstack.getItemDamage();
        }
        else
        {
            newWoolColor = this.worldObj.rand.nextBoolean() ? woolColorCode1 : woolColorCode2;
        }

        return newWoolColor;
    }

    /** (abstract) Protected helper method to write subclass entity data to NBT. */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
        par1NBTTagCompound.setByte("Color", (byte) this.getFleeceColor());
    }
}