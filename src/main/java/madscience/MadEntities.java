package madscience;

import java.awt.Color;

import madscience.items.CombinedGenomeMonsterPlacer;
import madscience.items.GeneticallyModifiedMonsterPlacer;
import madscience.items.ItemDataReelEmpty;
import madscience.metaitems.CombinedMemoryEntityList;
import madscience.metaitems.CombinedMemoryMonsterPlacer;
import madscience.metaitems.MainframeComponentsMetadata;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadEntities
{
    // Genetically Modified Monster Placer
    public static GeneticallyModifiedMonsterPlacer GENETICALLYMODIFIED_MONSTERPLACER;
    public static final String GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME = "gmoMonsterPlacer";

    // Combined Genome Monster Placer
    public static CombinedGenomeMonsterPlacer COMBINEDGENOME_MONSTERPLACER;
    public static final String COMBINEDGENOME_MONSTERPLACER_INTERNALNAME = "genomeMonsterPlacer";

    // Combined Memory Monster Placer
    public static CombinedMemoryMonsterPlacer COMBINEDMEMORY_MONSTERPLACER;
    public static final String COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME = "memoryMonsterPlacer";

    // Empty Data Reel
    public static ItemDataReelEmpty DATAREEL_EMPTY;
    public static final String DATAREEL_EMPTY_INTERNALNAME = "dataReelEmpty";

    // Allows mod to store all it's items under its own tab in creative mode.
    public static CreativeTabs tabMadScience;

    public static void createCustomCreativeTab(String tabInternalName, String tabDisplayName)
    {
        // Creates custom tab that shows up in creative mode to organize.
        tabMadScience = new CreativeTabs(tabInternalName)
        {
            @Override
            public ItemStack getIconItemStack()
            {
                // Shows icon of human DNA double-helix.
                return new ItemStack(MadDNA.DNA_SPIDER, 1, 0);
            }
        };

        // Add custom name for our creative tab.
        //LanguageRegistry.instance().addStringLocalization("itemGroup." + tabInternalName, "en_US", tabDisplayName);
    }

    public static void createEmptyDataReel(int itemID)
    {
        // Empty Genome Data Reel
        DATAREEL_EMPTY = (ItemDataReelEmpty) new ItemDataReelEmpty(itemID, 3515848, 3515848).setUnlocalizedName(DATAREEL_EMPTY_INTERNALNAME);
        GameRegistry.registerItem(DATAREEL_EMPTY, DATAREEL_EMPTY_INTERNALNAME);
        //LanguageRegistry.addName(DATAREEL_EMPTY, DATAREEL_EMPTY_DISPLAYNAME);
        
        // Data Reel
        GameRegistry.addRecipe(new ItemStack(DATAREEL_EMPTY, 1), new Object[]
        { "111",
          "121",
          "111",
          
          '1', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.COMPONENT_MAGNETICTAPE_METAID),
          '2', new ItemStack(MadComponents.MAINFRAME_COMPONENTS_METAITEM, 1, MadComponents.CIRCUIT_EMERALD_METAID),
        });
    }

    // Genetically Modified Mob Eggs
    public static void createGeneticallyModifiedMonsterPlacer(int itemID)
    {
        GENETICALLYMODIFIED_MONSTERPLACER = new GeneticallyModifiedMonsterPlacer(itemID);
        GENETICALLYMODIFIED_MONSTERPLACER.setUnlocalizedName(GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setTextureName(MadScience.ID + ":" + GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setCreativeTab(tabMadScience);
        //LanguageRegistry.addName(GENETICALLYMODIFIED_MONSTERPLACER, GENETICALLYMODIFIED_MONSTERPLACER_DISPLAYNAME);
        GameRegistry.registerItem(GENETICALLYMODIFIED_MONSTERPLACER, GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
    }

    // Memory Data Reels
    public static void createCombinedMemoryMonsterPlacer(int itemID)
    {
        // For storing memories of various villages based on their professions.
        COMBINEDMEMORY_MONSTERPLACER = new CombinedMemoryMonsterPlacer(itemID);
        COMBINEDMEMORY_MONSTERPLACER.setUnlocalizedName(COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);
        COMBINEDMEMORY_MONSTERPLACER.setTextureName(MadScience.ID + ":" + COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);
        COMBINEDMEMORY_MONSTERPLACER.setCreativeTab(tabMadScience);
        //LanguageRegistry.addName(COMBINEDMEMORY_MONSTERPLACER, COMBINEDMEMORY_MONSTERPLACER_DISPLAYNAME);
        GameRegistry.registerItem(COMBINEDMEMORY_MONSTERPLACER, COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);

        // Creates the various memory tiers, making their internal ID the amount of power we want them to create.
        // Note: These mobs are hard-coded into the combined memory monster placer.

        /*
         * 0 - Priest [32 EU/t] 1 - Farmer [64 EU/t] 2 - Butcher [128 EU/t] 3 - Blacksmith [128 EU/t] 4 - Librarian [512 EU/t] */
    }

    // Combined Genome Data Reels
    public static void createCombinedGenomeMonsterPlacer(int itemID)
    {
        COMBINEDGENOME_MONSTERPLACER = new CombinedGenomeMonsterPlacer(itemID);
        COMBINEDGENOME_MONSTERPLACER.setUnlocalizedName(COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
        COMBINEDGENOME_MONSTERPLACER.setTextureName(MadScience.ID + ":" + COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
        COMBINEDGENOME_MONSTERPLACER.setCreativeTab(tabMadScience);
        //LanguageRegistry.addName(COMBINEDGENOME_MONSTERPLACER, COMBINEDGENOME_MONSTERPLACER_DISPLAYNAME);
        GameRegistry.registerItem(COMBINEDGENOME_MONSTERPLACER, COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
    }
}
