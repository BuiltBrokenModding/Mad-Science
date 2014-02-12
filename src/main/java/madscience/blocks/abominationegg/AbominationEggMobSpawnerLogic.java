package madscience.blocks.abominationegg;

import madscience.MadBlocks;
import net.minecraft.world.World;

class AbominationEggMobSpawnerLogic extends AbominationEggBasicLogic
{
    /** The mob spawner we deal with */
    final AbominationEggMobSpawnerTileEntity mobSpawnerEntity;

    AbominationEggMobSpawnerLogic(AbominationEggMobSpawnerTileEntity par1TileEntityMobSpawner)
    {
        this.mobSpawnerEntity = par1TileEntityMobSpawner;
    }

    @Override
    public void func_98267_a(int par1)
    {
        this.mobSpawnerEntity.worldObj.addBlockEvent(this.mobSpawnerEntity.xCoord, this.mobSpawnerEntity.yCoord, this.mobSpawnerEntity.zCoord, MadBlocks.ABOMINATIONEGGBLOCK.blockID, par1, 0);
    }

    @Override
    public World getSpawnerWorld()
    {
        return this.mobSpawnerEntity.worldObj;
    }

    @Override
    public int getSpawnerX()
    {
        return this.mobSpawnerEntity.xCoord;
    }

    @Override
    public int getSpawnerY()
    {
        return this.mobSpawnerEntity.yCoord;
    }

    @Override
    public int getSpawnerZ()
    {
        return this.mobSpawnerEntity.zCoord;
    }
}
