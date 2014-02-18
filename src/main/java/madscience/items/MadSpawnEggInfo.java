package madscience.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.nbt.NBTTagCompound;

public class MadSpawnEggInfo
{

    public final short eggID;
    public final String mobID;
    public final NBTTagCompound spawnData;
    public final int primaryColor;
    public final int secondaryColor;

    public MadSpawnEggInfo(short eggID, String mobID, NBTTagCompound spawnData, int primaryColor, int secondaryColor)
    {
        this.eggID = eggID;
        this.mobID = mobID;
        this.spawnData = spawnData;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}