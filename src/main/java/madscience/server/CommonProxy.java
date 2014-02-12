package madscience.server;

import java.util.Random;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommonProxy
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File

    public void registerRenderingHandler(int blockID)
    {
    }

    public void registerSoundHandler()
    {
    }

    public void registerBlock(Block block)
    {
        registerBlock(block, ItemBlockMadScience.class);
    }

    public void registerBlock(Block block, Class<? extends ItemBlock> item)
    {
        GameRegistry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", ""));
    }

    /* LOCALIZATION */
    public void addName(Object obj, String s)
    {
    }

    public boolean isSimulating(World world)
    {
        return !world.isRemote;
    }

    public boolean isRenderWorld(World world)
    {
        return world.isRemote;
    }

    public String getMinecraftVersion()
    {
        return Loader.instance().getMinecraftModContainer().getVersion();
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

    public void addLocalization(String s1, String string)
    {
    }

    public String getItemDisplayName(ItemStack newStack)
    {
        return "";
    }
    
    public Random createNewRandom(World world) {
        return new Random(world.getSeed());
}
}
