package madscience;

import madscience.factory.mod.MadMod;
import madscience.items.combinedgenomes.CombinedGenomeMonsterPlacer;
import madscience.items.datareel.ItemDataReelEmpty;
import madscience.items.labcoat.ItemLabCoat;
import madscience.items.memories.CombinedMemoryMonsterPlacer;
import madscience.items.spawnegg.GeneticallyModifiedMonsterPlacer;
import madscience.items.warningsign.WarningSignEntity;
import madscience.items.warningsign.WarningSignItem;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadEntities
{
    // Genetically Modified Monster Placer
    static GeneticallyModifiedMonsterPlacer GENETICALLYMODIFIED_MONSTERPLACER;
    private static final String GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME = "gmoMonsterPlacer";

    // Combined Genome Monster Placer
    static CombinedGenomeMonsterPlacer COMBINEDGENOME_MONSTERPLACER;
    private static final String COMBINEDGENOME_MONSTERPLACER_INTERNALNAME = "genomeMonsterPlacer";

    // Combined Memory Monster Placer
    public static CombinedMemoryMonsterPlacer COMBINEDMEMORY_MONSTERPLACER;
    public static final String COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME = "memoryMonsterPlacer";

    // Empty Data Reel
    public static ItemDataReelEmpty DATAREEL_EMPTY;
    private static final String DATAREEL_EMPTY_INTERNALNAME = "dataReelEmpty";

    // Gives our armor a custom material so that we can detect when various events interact with it.
    private static final EnumArmorMaterial labCoatArmorMaterial = EnumHelper.addArmorMaterial("LABCOAT", 0, new int[] { 0, 0, 0, 0 }, 0);
    
    // Labcoat Body
    static ItemLabCoat LABCOAT_BODY;
    private static final String LABCOAT_BODY_INTERNALNAME = "labCoatBody";
    
    // Labcoat Leggings
    static ItemLabCoat LABCOAT_LEGGINGS;
    private static final String LABCOAT_LEGGINGS_INTERNALNAME = "labCoatLeggings";
    
    // Labcoat Goggles
    static ItemLabCoat LABCOAT_GOGGLES;
    private static final String LABCOAT_GOGGLES_INTERNALNAME = "labCoatGoggles";
    
    // Warning Sign
    public static WarningSignItem WARNING_SIGN;
    public static final String WARNING_SIGN_INTERNALNAME = "warningSign";

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

    // Memory Data Reels
    static void createCombinedMemoryMonsterPlacer(int itemID)
    {
        // For storing memories of various villages based on their professions.
        MadMod.log().info("-Combined Memory Metaitem");
        COMBINEDMEMORY_MONSTERPLACER = new CombinedMemoryMonsterPlacer(itemID);
        COMBINEDMEMORY_MONSTERPLACER.setUnlocalizedName(COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);
        COMBINEDMEMORY_MONSTERPLACER.setTextureName(MadMod.ID + ":" + COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);
        COMBINEDMEMORY_MONSTERPLACER.setCreativeTab(MadMod.getCreativeTab());
        GameRegistry.registerItem(COMBINEDMEMORY_MONSTERPLACER, COMBINEDMEMORY_MONSTERPLACER_INTERNALNAME);

        // Creates the various memory tiers, making their internal ID the amount of power we want them to create.
        // Note: These mobs are hard-coded into the combined memory monster placer.

        /* 0 - Priest [32 EU/t] 1 - Farmer [64 EU/t] 2 - Butcher [128 EU/t] 3 - Blacksmith [128 EU/t] 4 - Librarian [512 EU/t] */
    }

    static void createEmptyDataReel(int itemID)
    {
        // Empty Genome Data Reel
        MadMod.log().info("-Empty Data Reel");
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
    static void createGeneticallyModifiedMonsterPlacer(int itemID)
    {
        MadMod.log().info("-Genetically Modified Organism Placer");
        GENETICALLYMODIFIED_MONSTERPLACER = new GeneticallyModifiedMonsterPlacer(itemID);
        GENETICALLYMODIFIED_MONSTERPLACER.setUnlocalizedName(GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setTextureName(MadMod.ID + ":" + GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
        GENETICALLYMODIFIED_MONSTERPLACER.setCreativeTab(MadMod.getCreativeTab());
        GameRegistry.registerItem(GENETICALLYMODIFIED_MONSTERPLACER, GENETICALLYMODIFIED_MONSTERPLACER_INTERNALNAME);
    }

    // Lab Coat Body
    static void createLabCoatBody(int itemID, int armorID)
    {
        MadMod.log().info("-LabCoat Body");
        LABCOAT_BODY = (ItemLabCoat) new ItemLabCoat(itemID, labCoatArmorMaterial, MadForgeMod.proxy.getArmorIndex("labcoat"), armorID).setUnlocalizedName(LABCOAT_BODY_INTERNALNAME);
        GameRegistry.registerItem(LABCOAT_BODY, LABCOAT_BODY_INTERNALNAME);
    }

    // Lab Coat Leggings
    static void createLabCoatLeggings(int itemID, int armorID)
    {
        MadMod.log().info("-LabCoat Leggings");
        LABCOAT_LEGGINGS = (ItemLabCoat) new ItemLabCoat(itemID, labCoatArmorMaterial, MadForgeMod.proxy.getArmorIndex("labcoat"), armorID).setUnlocalizedName(LABCOAT_LEGGINGS_INTERNALNAME);
        GameRegistry.registerItem(LABCOAT_LEGGINGS, LABCOAT_LEGGINGS_INTERNALNAME);
    }

    // Lab Coat Goggles
    static void createLabCoatGoggles(int itemID, int armorID)
    {
        MadMod.log().info("-LabCoat Goggles");
        LABCOAT_GOGGLES = (ItemLabCoat) new ItemLabCoat(itemID, labCoatArmorMaterial, MadForgeMod.proxy.getArmorIndex("labcoat"), armorID).setUnlocalizedName(LABCOAT_GOGGLES_INTERNALNAME);
        GameRegistry.registerItem(LABCOAT_GOGGLES, LABCOAT_GOGGLES_INTERNALNAME);
    }

    // Warning Sign
    static void createWarningSign(int itemID)
    {
        MadMod.log().info("-Warning Sign Painting");
        WARNING_SIGN = (WarningSignItem) new WarningSignItem(itemID).setUnlocalizedName(WARNING_SIGN_INTERNALNAME);
        EntityRegistry.registerModEntity(WarningSignEntity.class, WARNING_SIGN_INTERNALNAME, itemID, MadForgeMod.instance, 120, 3, false);
        GameRegistry.registerItem(WARNING_SIGN, WARNING_SIGN_INTERNALNAME);
        MadForgeMod.proxy.registerRenderingHandler(itemID);
    }
}
