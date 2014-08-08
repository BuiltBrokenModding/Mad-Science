package madscience;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MadMobs
{
    @EventHandler
    static void createGMOMob(int metaID, Class mobEntity, NBTTagCompound spawnData, String eggInternalName, String genomeInternalName, int primaryColor, int secondaryColor, ItemGenomeBase primaryGenome, ItemGenomeBase secondaryGenome, int mainframeComputeTime)
    {
        // Get a free and unique entity ID for our mob.
        int freeMobID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(mobEntity, eggInternalName, freeMobID);
        EntityRegistry.registerModEntity(mobEntity, eggInternalName, freeMobID, MadForgeMod.instance, 42, 3, true);

        // Register our rendering handles on clients and ignore them on servers.
        MadForgeMod.proxy.registerRenderingHandler(metaID);

        // Recipes for creating this custom mob.
        //MainframeRecipes.addRecipe(new ItemStack(primaryGenome), new ItemStack(secondaryGenome), new ItemStack(MadEntities.COMBINEDGENOME_MONSTERPLACER, 1, metaID), mainframeComputeTime);
        //IncubatorRecipes.addSmelting(MadEntities.COMBINEDGENOME_MONSTERPLACER.itemID, metaID, new ItemStack(MadEntities.GENETICALLYMODIFIED_MONSTERPLACER, 1, metaID));
    }
}
