package madscience.mobs.creepercow;

import madscience.MadFluids;
import madscience.MadSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreeperCowMobEntity extends EntityMob
{
    /** Explosion radius for this creeper. */
    private int explosionRadius = 5;

    private int fuseTime = 30;
    /** Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go weird) */
    private int lastActiveTime;

    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;

    public CreeperCowMobEntity(World par1World)
    {
        super(par1World);
        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new CreeperCowEntityAISwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        // Changes basic attributes about the mob.
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.24D);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        return true;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity)
    {
        if (par1Entity instanceof IMob && !(par1Entity instanceof CreeperCowMobEntity) && this.getRNG().nextInt(20) == 0)
        {
            this.setAttackTarget((EntityLiving) par1Entity);
        }

        super.collideWithEntity(par1Entity);
    }

    // This is required regardless of if your animal can breed or not. Set to
    // null if it can't breed - I wont cover breeding here.
    public EntityAgeable createChild(EntityAgeable var1)
    {
        return null;
    }

    /** Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param par2 - Level of Looting used to kill this mob. */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
        int k;

        for (k = 0; k < j; ++k)
        {
            this.dropItem(Item.leather.itemID, 1);
            this.dropItem(Item.gunpowder.itemID, 1);
        }

        j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

        for (k = 0; k < j; ++k)
        {
            if (this.isBurning())
            {
                this.dropItem(Item.beefCooked.itemID, 1);
                this.dropItem(Item.gunpowder.itemID, 1);
            }
            else
            {
                this.dropItem(Item.beefRaw.itemID, 1);
                this.dropItem(Item.gunpowder.itemID, 1);
            }
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
    }

    /** Called when the mob is falling. Calculates and applies fall damage. */
    @Override
    protected void fall(float par1)
    {
        super.fall(par1);
        this.timeSinceIgnited = (int) (this.timeSinceIgnited + par1 * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
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

    @SideOnly(Side.CLIENT)
    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    public float getCreeperFlashIntensity(float par1)
    {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
    }

    /** Returns the current state of creeper, -1 is idle, 1 is 'in fuse' */
    public int getCreeperState()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /** Returns the sound this mob makes on death. */
    @Override
    protected String getDeathSound()
    {
        return "mob.creeper.death";
    }

    /** Returns the item ID for the item the mob drops on death. */
    @Override
    protected int getDropItemId()
    {
        return Item.gunpowder.itemID;
    }

    /** Returns the sound this mob makes when it is hurt. */
    @Override
    protected String getHurtSound()
    {
        return "mob.creeper.say";
    }

    // The sound effect played when it's just living, like a cow mooing.
    @Override
    protected String getLivingSound()
    {
        return MadSounds.CREEPERCOW_ATTACK;
    }

    /** The number of iterations PathFinder.getSafePoint will execute before giving up. */
    @Override
    public int getMaxSafePointTries()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    /** Returns true if the creeper is powered by a lightning bolt. */
    public boolean getPowered()
    {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    @Override
    protected float getSoundVolume()
    {
        // Allows us to change how loud this particular entity is.
        return 0.4F;
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
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM));
            }
            else if (!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM)))
            {
                par1EntityPlayer.dropPlayerItem(new ItemStack(MadFluids.LIQUIDDNA_MUTANT_BUCKET_ITEM.itemID, 1, 0));
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

    /** Called when the mob's health reaches 0. */
    @Override
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntitySkeleton)
        {
            int i = Item.record13.itemID + this.rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1);
            this.dropItem(i, 1);
        }
    }

    /** Called when a lightning bolt hits the entity. */
    @Override
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
    {
        super.onStruckByLightning(par1EntityLightningBolt);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
    }

    /** Called to update the entity's position/logic. */
    @Override
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;

                if (!this.worldObj.isRemote)
                {
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

                    if (this.getPowered())
                    {
                        this.playSound("mob.cow.say", 1.0F, 0.5F);
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * 2, flag);
                    }
                    else
                    {
                        this.playSound("mob.cow.say", 1.0F, 0.5F);
                        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius, flag);
                    }

                    this.setDead();
                }
            }
        }

        super.onUpdate();
    }

    /** Plays step sound at given x, y, z for the entity */
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.cow.step", 0.15F, 1.0F);
    }

    /** (abstract) Protected helper method to read subclass entity data from NBT. */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
        {
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");
        }

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
        {
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
        }
    }

    // Sets the active target the Task system uses for tracking.
    @Override
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
    {
        super.setAttackTarget(par1EntityLivingBase);
    }

    /** Sets the state of creeper, -1 to idle and 1 to be 'in fuse' */
    public void setCreeperState(int par1)
    {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte) par1));
    }

    /** (abstract) Protected helper method to write subclass entity data to NBT. */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1)
        {
            par1NBTTagCompound.setBoolean("powered", true);
        }

        par1NBTTagCompound.setShort("Fuse", (short) this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
    }
}