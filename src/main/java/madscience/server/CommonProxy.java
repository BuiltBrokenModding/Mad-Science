package madscience.server;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File

    public void addLocalization(String s1, String string)
    {
    }

    /* LOCALIZATION */
    public void addName(Object obj, String s)
    {
    }

    public Random createNewRandom(World world)
    {
        return new Random(world.getSeed());
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

    public String getItemDisplayName(ItemStack newStack)
    {
        return "";
    }
    
    public void onBowUse(ItemStack stack, EntityPlayer player, int pulseRifleFireTime) 
    {

    }
    
    public void resetSavedFOV() 
    {

    }

    public String getMinecraftVersion()
    {
        return Loader.instance().getMinecraftModContainer().getVersion();
    }

    public boolean isRenderWorld(World world)
    {
        return world.isRemote;
    }

    public boolean isSimulating(World world)
    {
        return !world.isRemote;
    }

    public void registerBlock(Block block)
    {
        registerBlock(block, ItemBlockMadScience.class);
    }

    public void registerBlock(Block block, Class<? extends ItemBlock> item)
    {
        GameRegistry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", ""));
    }

    public void registerRenderingHandler(int blockID)
    {
    }

    public void registerSoundHandler()
    {
    }

    public void spawnParticle(String fxName, double posX, double posY, double posZ, double velX, double velY, double velZ)
    {        
    }
}
