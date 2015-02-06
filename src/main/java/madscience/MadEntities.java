package madscience;

import madscience.items.CombinedGenomeMonsterPlacer;
import madscience.items.GeneticallyModifiedMonsterPlacer;
import madscience.items.ItemDataReelEmpty;
import madscience.items.labcoat.ItemLabCoat;
import madscience.items.warningsign.WarningSignEntity;
import madscience.items.warningsign.WarningSignItem;
import madscience.metaitems.CombinedMemoryMonsterPlacer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.registry.EntityRegistry;
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
    
    // Gives our armor a custom material so that we can detect when various events interact with it.
    public static final EnumArmorMaterial labCoatArmorMaterial = EnumHelper.addArmorMaterial("LABCOAT", 0, new int[] { 0, 0, 0, 0 }, 0);
    
    // Labcoat Body
    public static ItemLabCoat LABCOAT_BODY;
    public static final String LABCOAT_BODY_INTERNALNAME = "labCoatBody";
    
    // Labcoat Leggings
    public static ItemLabCoat LABCOAT_LEGGINGS;
    public static final String LABCOAT_LEGGINGS_INTERNALNAME = "labCoatLeggings";
    
    // Labcoat Goggles
    public static ItemLabCoat LABCOAT_GOGGLES;
    public static final String LABCOAT_GOGGLES_INTERNALNAME = "labCoatGoggles";
    
    // Warning Sign
    public static WarningSignItem WARNING_SIGN;
    public static final String WARNING_SIGN_INTERNALNAME = "warningSign";

    // Combined Genome Data Reels
    public static void createCombinedGenomeMonsterPlacer(int itemID)
    {
        MadScience.logger.info("-Combined Genome Metaitem");
        COMBINEDGENOME_MONSTERPLACER = new CombinedGenomeMonsterPlacer(itemID);
        COMBINEDGENOME_MONSTERPLACER.setUnlocalizedName(COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
        COMBINEDGENOME_MONSTERPLACER.setTextureName(MadScience.ID + ":" + COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
        COMBINEDGENOME_MONSTERPLACER.setCreativeTab(tabMadScience);
        GameRegistry.registerItem(COMBINEDGENOME_MONSTERPLACER, COMBINEDGENOME_MONSTERPLACER_INTERNALNAME);
    }

    // Memory Data Reels
    public static void createCombinedMemoryMonsterPlacer(int itemID)
    {
        // For storing memories of various villages based on their professions.
        MadScience.logger.info("-Combined Memory Metaitem");
        COMBINEDMEMORY_MONSTERPLACER = new CombinedMemoryMonsterPlacer(itemID);
        COMBINEDMEMORY_MONSTERPLACER.setUnlocalizedName(COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);
        COMBINEDMEMORY_MONSTERPLACER.setTextureName(MadScience.ID + ":" + COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);
        COMBINEDMEMORY_MONSTERPLACER.setCreativeTab(tabMadScience);
        GameRegistry.registerItem(COMBINEDMEMORY_MONSTERPLACER, COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);

        // Creates the various memory tiers, making their internal ID the amount of power we want them to create.
        // Note: These mobs are hard-coded into the combined memory monster placer.

        /* 0 - Priest [32 EU/t] 1 - Farmer [64 EU/t] 2 - Butcher [128 EU/t] 3 - Blacksmith [128 EU/t] 4 - Librarian [512 EU/t] */
    }

    public static void createEmptyDataReel(int itemID)
    {
        // Empty Genome Data Reel
        MadScience.logger.info("-Empty Data Reel");
        DATAREEL_EMPTY = (ItemDataReelEmpty) new ItemDataReelEmpty(itemID, 3515848, 3515848).setUnlocalizedName(DATAREEL_EMPTY_INTERNALNAME);
        GameRegistry.registerItem(DATAREEL_EMPTY, DATAREEL_EMPTY_INTERNALNAME);

        // Data Reel
        GameRegistry.addRecipe(new ItemStack(DATAREEL_EMPTY, 1), new Object[]
        { "111",
          "121", 
          "111",

        '1', new ItemStack(MadComponents.COMPONENT_MAGNETICTAPE), 
        '2', new ItemStack(MadCircuits.CIRCUIT_EMERALD), });
    }

    // Genetically Modified Mob Eggs
    public static void createGeneticallyModifiedMonsterPlacer(int itemID)
    {
        MadScience.logger.info("-Genetically Modified Organism Placer");
        GENETICALLYMODIFIED_MONSTERPLACER = new GeneticallyModifiedMonsterPlacer(itemID);
        GENETICALLYMODIFIED_MONSTERPLACER.setUnlocalizedName(GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setTextureName(MadScience.ID + ":" + GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setCreativeTab(tabMadScience);
        GameRegistry.registerItem(GENETICALLYMODIFIED_MONSTERPLACER, GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
    }

    // Lab Coat Body
    public static void createLabCoatBody(int itemID, int armorID)
    {
        MadScience.logger.info("-LabCoat Body");
        LABCOAT_BODY = (ItemLabCoat) new ItemLabCoat(itemID, labCoatArmorMaterial, MadScience.proxy.getArmorIndex("labcoat"), armorID).setUnlocalizedName(LABCOAT_BODY_INTERNALNAME);
        GameRegistry.registerItem(LABCOAT_BODY, LABCOAT_BODY_INTERNALNAME);
    }

    // Lab Coat Leggings
    public static void createLabCoatLeggings(int itemID, int armorID)
    {
        MadScience.logger.info("-LabCoat Leggings");
        LABCOAT_LEGGINGS = (ItemLabCoat) new ItemLabCoat(itemID, labCoatArmorMaterial, MadScience.proxy.getArmorIndex("labcoat"), armorID).setUnlocalizedName(LABCOAT_LEGGINGS_INTERNALNAME);
        GameRegistry.registerItem(LABCOAT_LEGGINGS, LABCOAT_LEGGINGS_INTERNALNAME);
    }

    // Lab Coat Goggles
    public static void createLabCoatGoggles(int itemID, int armorID)
    {
        MadScience.logger.info("-LabCoat Goggles");
        LABCOAT_GOGGLES = (ItemLabCoat) new ItemLabCoat(itemID, labCoatArmorMaterial, MadScience.proxy.getArmorIndex("labcoat"), armorID).setUnlocalizedName(LABCOAT_GOGGLES_INTERNALNAME);
        GameRegistry.registerItem(LABCOAT_GOGGLES, LABCOAT_GOGGLES_INTERNALNAME);
    }

    // Warning Sign
    public static void createWarningSign(int itemID)
    {
        MadScience.logger.info("-Warning Sign Painting");
        WARNING_SIGN = (WarningSignItem) new WarningSignItem(itemID).setUnlocalizedName(WARNING_SIGN_INTERNALNAME);
        EntityRegistry.registerModEntity(WarningSignEntity.class, WARNING_SIGN_INTERNALNAME, itemID, MadScience.instance, 120, 3, false);
        GameRegistry.registerItem(WARNING_SIGN, WARNING_SIGN_INTERNALNAME);
        MadScience.proxy.registerRenderingHandler(itemID);
    }
}
