package madscience.blocks;

import madscience.mobs.abomination.AbominationEntitySelector;
import madscience.mobs.abomination.AbominationMobEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class TileEntityAbominationEgg extends TileEntity
{
    public static int minHatchTime = 1000;
    public long ticks = 0L;
    public int hatchDelay = 0;
    public boolean hasMeetHatchTime = false;

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        ticks++;
        if(ticks + 1 >= Long.MAX_VALUE)
            ticks = 1;

        //Min delay so eggs don't hatch right away
        if(!hasMeetHatchTime)
        {
            hatchDelay++;
            if(hatchDelay >= minHatchTime)
                hasMeetHatchTime = true;
        }
        //Every 1 second check for player nearby and hatch
        else if(ticks % 20 == 0)
        {
            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord - 29, yCoord - 2, zCoord - 29, xCoord + 30, yCoord + 2, zCoord + 30);
            //TODO replace with target select to only hatch if a valid entity to kill is near
            List list = getWorldObj().selectEntitiesWithinAABB(EntityLivingBase.class, box, AbominationEntitySelector.GENERIC);
            if(list.size() > 0)
            {
                getWorldObj().setBlockToAir(xCoord, yCoord, zCoord);
                AbominationMobEntity mob = new AbominationMobEntity(getWorldObj());
                mob.setPosition(xCoord + 0.5, yCoord + 0.7, zCoord + 0.5);
                getWorldObj().spawnEntityInWorld(mob);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("canHatch"))
        {
            hasMeetHatchTime = nbt.getBoolean("canHatch");
        }
        else
        {
            hatchDelay = nbt.getInteger("ticks");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(hasMeetHatchTime)
        {
            nbt.setBoolean("canHatch", true);
        }
        else
        {
            nbt.setInteger("ticks", hatchDelay);
        }
    }
}
