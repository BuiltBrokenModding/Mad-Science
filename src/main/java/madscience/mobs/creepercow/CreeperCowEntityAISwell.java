package madscience.mobs.creepercow;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

class CreeperCowEntityAISwell extends EntityAIBase
{
    /** The creeper's attack target. This is used for the changing of the creeper's state. */
    private EntityLivingBase creeperCowAttackTarget;

    /** The creeper that is swelling. */
    private CreeperCowMobEntity swellingCreeperCow;

    CreeperCowEntityAISwell(CreeperCowMobEntity par1EntityCreeper)
    {
        this.swellingCreeperCow = par1EntityCreeper;
        this.setMutexBits(1);
    }

    /** Resets the task */
    @Override
    public void resetTask()
    {
        this.creeperCowAttackTarget = null;
    }

    /** Returns whether the EntityAIBase should begin execution. */
    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.swellingCreeperCow.getAttackTarget();
        return this.swellingCreeperCow.getCreeperState() > 0 || entitylivingbase != null && this.swellingCreeperCow.getDistanceSqToEntity(entitylivingbase) < 9.0D;
    }

    /** Execute a one shot task or start executing a continuous task */
    @Override
    public void startExecuting()
    {
        this.swellingCreeperCow.getNavigator().clearPathEntity();
        this.creeperCowAttackTarget = this.swellingCreeperCow.getAttackTarget();
    }

    /** Updates the task */
    @Override
    public void updateTask()
    {
        if (this.creeperCowAttackTarget == null)
        {
            this.swellingCreeperCow.setCreeperState(-1);
        }
        else if (this.swellingCreeperCow.getDistanceSqToEntity(this.creeperCowAttackTarget) > 49.0D)
        {
            this.swellingCreeperCow.setCreeperState(-1);
        }
        else if (!this.swellingCreeperCow.getEntitySenses().canSee(this.creeperCowAttackTarget))
        {
            this.swellingCreeperCow.setCreeperState(-1);
        }
        else
        {
            this.swellingCreeperCow.setCreeperState(1);
        }
    }
}
