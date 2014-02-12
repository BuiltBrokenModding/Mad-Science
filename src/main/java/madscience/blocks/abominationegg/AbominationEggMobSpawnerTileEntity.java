package madscience.blocks.abominationegg;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class AbominationEggMobSpawnerTileEntity extends TileEntity
{
    private final AbominationEggBasicLogic field_98050_a = new AbominationEggMobSpawnerLogic(this);

    /** Overriden in a sign to provide the text. */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        nbttagcompound.removeTag("SpawnPotentials");
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }

    /** Returns the spawner logic associated with this spawner */
    public AbominationEggBasicLogic getSpawnerLogic()
    {
        return this.field_98050_a;
    }

    /** Reads a tile entity from NBT. */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.field_98050_a.readFromNBT(par1NBTTagCompound);
    }

    /** Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count ticks and creates a new spawn inside its implementation. */
    @Override
    public void updateEntity()
    {
        this.field_98050_a.updateSpawner();
        super.updateEntity();
    }

    /** Writes a tile entity to NBT. */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        this.field_98050_a.writeToNBT(par1NBTTagCompound);
    }
}
