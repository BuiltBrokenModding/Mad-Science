package madscience.items.spawnegg;

import net.minecraft.nbt.NBTTagCompound;

public class MadSpawnEggInfo
{
    public final short eggID;
    public final String mobID;
    private final NBTTagCompound spawnData;
    private final int primaryColor;
    private final int secondaryColor;

    public MadSpawnEggInfo(short eggID, String mobID, NBTTagCompound spawnData, int primaryColor, int secondaryColor)
    {
        this.eggID = eggID;
        this.mobID = mobID;
        this.spawnData = spawnData;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public NBTTagCompound getSpawnData()
    {
        return spawnData;
    }

    public int getPrimaryColor()
    {
        return primaryColor;
    }

    public int getSecondaryColor()
    {
        return secondaryColor;
    }
}