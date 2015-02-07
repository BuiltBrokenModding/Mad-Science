package madscience.mobs.werewolf;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// Note: If your mob swims, change EntityAnimal to EntityWaterMob.
public class WerewolfMobEntity extends EntityMob
{
    public WerewolfMobEntity(World par1World)
    {
        super(par1World);

        // The below means if possible, it wont walk into water
        this.getNavigator().setAvoidsWater(true);

        // This is the hitbox size. Starts in the center and grows outwards.
        // this.setSize(1.5F, 0.9F);
        // this.setSize(0.9F, 1.3F);
        this.setSize(1.5F, 2.0F);

        // Defines if fire affects this mob.
        this.isImmuneToFire = false;

        // Now, we have the AI. Each number in the addTask is a priority. 0 is
        // the highest, the largest is lowest.
        // They should be set in the order which the mob should focus, because
        // it can only do one thing at a time. I'll explain my choice for order
        // below.
        // There are tons of tasks you can add. Look in the JavaDocs or other
        // mob classes to find some more!

        // Swimming should ALWAYS be first. Otherwise if your mob falls in
        // water, but it's running away from you or something it'll drown.
        this.tasks.addTask(0, new EntityAISwimming(this));

        // Makes the mob try and attack the nearest player that it encounters.
        // Note: Change EntityPlayer to another class to have mob target that.
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false, true));

        // Forces the player to take damage if the mob touches them.
        // this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D,
        // false));

        // This makes the mob run away when you punch it
        this.tasks.addTask(4, new EntityAIPanic(this, 0.38F));

        // If you have mating code, this allows it to mate.
        float wanderAmount = 0.25F;
        // this.tasks.addTask(2, new EntityAIMate(this, wanderAmount));

        // This code is used to get the mob to follow you.
        // this.tasks.addTask(5, new EntityAITempt(this, 0.3F,
        // MadEntities.EXAMPLE_ITEM.itemID, false));

        // If the mob is a child, it will follow it's parent.
        // this.tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));

        // This makes the mob walk around. Without it, it'd just stand still.
        this.tasks.addTask(6, new EntityAIWander(this, wanderAmount));

        // This makes the mob watch the nearest player, within a range set by
        // the float.
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));

        // Finally, this makes it look around when it's not looking at a player
        // or wandering.
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        // Changes basic attributes about the mob to make it different than
        // others.
        super.applyEntityAttributes();

        // Set mob total amount of health (hearts).
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(25.0D);

        // Recommended default speed is used for the mob.
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.53000000417232513D);

        // How much damage does this mob do to other mobs.
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(10.0D);

        // How many blocks will the mob pursue it's target.
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(42.0D);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int i = 7;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    @Override
    public boolean canAttackClass(Class par1Class)
    {
        return EntityCreeper.class != par1Class && EntityGhast.class != par1Class;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity)
    {
        if (par1Entity instanceof IMob && !(par1Entity instanceof WerewolfMobEntity) && this.getRNG().nextInt(20) == 0)
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
        return WerewolfMobSounds.WEREWOLF_DEATH;
    }

    // A basic example of what a mob should drop on death.
    @Override
    protected int getDropItemId()
    {
        return Item.egg.itemID;
    }

    // The sound made when it's attacked. Often it's the same as the normal say
    // sound, but sometimes different (such as in the ender dragon)
    @Override
    protected String getHurtSound()
    {
        return WerewolfMobSounds.WEREWOLF_IDLE;
    }

    // The sound effect played when it's just living, like a cow mooing.
    @Override
    protected String getLivingSound()
    {
        return WerewolfMobSounds.WEREWOLF_ATTACK;
    }

    @Override
    protected float getSoundVolume()
    {
        // Allows us to change how loud this particular entity is.
        return 0.4F;
    }

    // This is required. If it's false, none of the above takes effect.
    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    // The sound the mob plays when walking around.
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        // First variable is volume and the second is pitch.
        this.worldObj.playSoundAtEntity(this, WerewolfMobSounds.WEREWOLF_ATTACK, 0.15F, 1.0F);
    }

    // Sets the active target the Task system uses for tracking.
    @Override
    public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
    {
        super.setAttackTarget(par1EntityLivingBase);
    }
}