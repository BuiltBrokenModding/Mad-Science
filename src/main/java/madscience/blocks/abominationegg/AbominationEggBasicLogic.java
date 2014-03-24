package madscience.blocks.abominationegg;

import madscience.MadMobs;
import madscience.MadScience;
import madscience.mobs.abomination.AbominationSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class AbominationEggBasicLogic
{
    /** The distance from which a player activates the spawner. */
    private int activatingRangeFromPlayer = 5;
    public double field_98284_d;

    /** List of minecart to spawn. */
    public double field_98287_c;

    private Entity field_98291_j;

    /** Amount of time it takes for an egg to 'grow' into a villager. */
    public int hatchTimeCurrentValue;
    /** Maximum amount of time this roll it should take for this villager spawn egg to actually spawn in our test tube. */
    public int hatchTimeMaximum;
    private int maxNearbyEntities = 5;

    /** Returns true if there's a player close enough to this mob spawner to activate it. */
    public boolean canRun()
    {
        return this.getSpawnerWorld().getClosestPlayer(this.getSpawnerX() + 0.5D, this.getSpawnerY() + 0.5D, this.getSpawnerZ() + 0.5D, this.activatingRangeFromPlayer) != null;
    }

    public abstract void func_98267_a(int i);

    /** Gets the entity name that should be spawned. */
    public String getEntityNameToSpawn()
    {
        return MadMobs.GMO_ABOMINATION_INTERNALNAME;
    }

    public abstract World getSpawnerWorld();

    public abstract int getSpawnerX();

    public abstract int getSpawnerY();

    public abstract int getSpawnerZ();

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.hasKey("MaxNearbyEntities"))
        {
            this.maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = par1NBTTagCompound.getShort("RequiredPlayerRange");
        }

        // Hatching time maximum amount.
        this.hatchTimeMaximum = par1NBTTagCompound.getShort("HatchTimeMax");
        this.hatchTimeCurrentValue = par1NBTTagCompound.getShort("HatchTime");

        if (this.getSpawnerWorld() != null && this.getSpawnerWorld().isRemote)
        {
            this.field_98291_j = null;
        }
    }

    public Entity spawnEntityInWorld()
    {
        Entity entity = null;
        entity = EntityList.createEntityByName(MadMobs.GMO_ABOMINATION_INTERNALNAME, this.getSpawnerWorld());

        if (entity != null)
        {
            if (entity instanceof EntityLiving)
            {
                EntityLiving entityliving = (EntityLiving) entity;
                entity.setLocationAndAngles(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), MathHelper.wrapAngleTo180_float(this.getSpawnerWorld().rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onSpawnWithEgg(null);
                this.getSpawnerWorld().spawnEntityInWorld(entity);
                entityliving.playLivingSound();
            }
        }

        return entity;
    }

    public void updateSpawner()
    {
        double d0;
        if (this.getSpawnerWorld().isRemote && this.canRun())
        {
            double d1 = this.getSpawnerX() + this.getSpawnerWorld().rand.nextFloat();
            double d2 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextFloat();
            d0 = this.getSpawnerZ() + this.getSpawnerWorld().rand.nextFloat();
            this.getSpawnerWorld().spawnParticle("portal", d1, d2, d0, 0.0D, 0.0D, 0.0D);

            this.field_98284_d = this.field_98287_c;
            this.field_98287_c = (this.field_98287_c + 1000.0F / (MadScience.SECOND_IN_TICKS + 200.0F)) % 360.0D;
        }

        // !ONLY SERVER CODE BELOW HERE!
        if (this.getSpawnerWorld().isRemote)
        {
            return;
        }

        if (this.hatchTimeCurrentValue <= 0 && this.hatchTimeCurrentValue <= hatchTimeMaximum)
        {
            // --------------------------
            // TUBE EMPTY, START HATCHING
            // --------------------------

            // Length of time it will take to hatch a spawn egg.
            hatchTimeMaximum = 6000;

            // Increments the timer to kickstart the cooking loop.
            this.hatchTimeCurrentValue++;
        }
        else if (this.hatchTimeCurrentValue > 0 && this.hatchTimeCurrentValue < hatchTimeMaximum)
        {
            // Increments the timer to keep hatching process going!
            this.hatchTimeCurrentValue++;
            // MadScience.logger.info("HATCHING: " + this.hatchTimeCurrentValue);
        }
        else if (this.hatchTimeCurrentValue >= this.hatchTimeMaximum && this.canRun())
        {
            // MadScience.logger.info("READY TO HATCH!");
            // ----------------------------------------
            // HATCHING COMPLETE, DECIDE IF STILL-BIRTH
            // ----------------------------------------

            // Hatched egg without error!
            this.hatchTimeCurrentValue = this.hatchTimeMaximum;
            Entity entity = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());

            if (entity == null)
            {
                return;
            }

            int j = this.getSpawnerWorld()
                    .getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), this.getSpawnerX() + 1, this.getSpawnerY() + 1, this.getSpawnerZ() + 1).expand(2, 4.0D, 2))
                    .size();

            if (j >= this.maxNearbyEntities)
            {
                return;
            }

            d0 = this.getSpawnerX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * 2;
            double d3 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
            double d4 = this.getSpawnerZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * 2;
            entity.setLocationAndAngles(d0, d3, d4, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);

            this.spawnEntityInWorld();
            this.getSpawnerWorld().playAuxSFX(2004, this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), 0);
            this.getSpawnerWorld().playSoundEffect(this.getSpawnerX() + 0.5D, this.getSpawnerY() + 0.5D, this.getSpawnerZ() + 0.5D, AbominationSounds.ABOMINATION_HATCH, 1.0F, 1.0F);

            // Remove the spawner as soon as it has been used.
            this.getSpawnerWorld().removeBlockTileEntity(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ());
            this.getSpawnerWorld().setBlockToAir(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ());
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
        par1NBTTagCompound.setShort("RequiredPlayerRange", (short) this.activatingRangeFromPlayer);
        // Hatching time for spawn egg.
        par1NBTTagCompound.setShort("HatchTimeMax", (short) this.hatchTimeMaximum);
        par1NBTTagCompound.setShort("HatchTime", (short) this.hatchTimeCurrentValue);
    }
}
