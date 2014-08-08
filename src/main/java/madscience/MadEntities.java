package madscience;

import madscience.factory.mod.MadMod;
import madscience.item.ItemGMOMobPlacer;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadEntities
{
    // Genetically Modified Monster Placer
    static ItemGMOMobPlacer GENETICALLYMODIFIED_MONSTERPLACER;
    private static final String GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME = "gmoMonsterPlacer";

    // Combined Genome Monster Placer
    static CombinedGenomeMonsterPlacer COMBINEDGENOME_MONSTERPLACER;
    private static final String COMBINEDGENOME_MONSTERPLACER_INTERNALNAME = "genomeMonsterPlacer";

    // Combined Genome Data Reels
    static void createCombinedGenomeMonsterPlacer(int itemID)
    {
        MadMod.log().info("-Combined Genome Metaitem");
        COMBINEDGENOME_MONSTERPLACER = new CombinedGenomeMonsterPlacer(itemID);
        COMBINEDGENOME_MONSTERPLACER.setUnlocalizedName(COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
        COMBINEDGENOME_MONSTERPLACER.setTextureName(MadMod.ID + ":" + COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
        COMBINEDGENOME_MONSTERPLACER.setCreativeTab(MadMod.getCreativeTab());
        GameRegistry.registerItem(COMBINEDGENOME_MONSTERPLACER, COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
    }

    // Genetically Modified Mob Eggs
    static void createGeneticallyModifiedMonsterPlacer(int itemID)
    {
        MadMod.log().info("-Genetically Modified Organism Placer");
        GENETICALLYMODIFIED_MONSTERPLACER = new ItemGMOMobPlacer(itemID);
        GENETICALLYMODIFIED_MONSTERPLACER.setUnlocalizedName(GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setTextureName(MadMod.ID + ":" + GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setCreativeTab(MadMod.getCreativeTab());
        GameRegistry.registerItem(GENETICALLYMODIFIED_MONSTERPLACER, GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
    }
}
