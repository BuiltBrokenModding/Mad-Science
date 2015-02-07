package madscience;

import java.util.Random;

import com.builtbroken.mc.lib.mod.AbstractProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy extends AbstractProxy
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File
    
    public int getArmorIndex(String armor)
    {
        return 0;
    }

    /* INSTANCES */
    public Object getClient()
    {
        return null;
    }

    public World getClientWorld()
    {
        return null;
    }
    
    public void onBowUse(ItemStack stack, EntityPlayer player, int pulseRifleFireTime) 
    {

    }
    
    public void resetSavedFOV() 
    {

    }

    public void spawnParticle(String fxName, double posX, double posY, double posZ, double velX, double velY, double velZ)
    {        
    }
}
