package madscience;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import madscience.factory.crafting.MadCraftingRecipe;
import madscience.factory.crafting.MadCraftingRecipeTypeEnum;
import madscience.factory.data.MadItemFactoryProductData;
import madscience.factory.furnace.MadFurnaceRecipe;
import madscience.factory.item.MadItemRenderPass;
import madscience.factory.item.MadMetaItemData;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelRotation;
import madscience.factory.model.MadModelScale;
import madscience.factory.rendering.MadModelItemRender;
import madscience.factory.sound.MadSound;
import madscience.factory.sound.MadSoundPlaybackTypeEnum;
import madscience.factory.sound.MadSoundTriggerEnum;
import madscience.util.MadColors;

public class MadManualItems
{
    private List<MadItemFactoryProductData> manualItems = null;

    public List<MadItemFactoryProductData> getManualitems()
    {
        return manualItems;
    }

    public MadManualItems()
    {
        // FINAL EXPORT ARRAY.
        manualItems = new ArrayList<MadItemFactoryProductData>();
        
        // DEFAULT ITEM RENDERING INFORMATION.
        MadModelItemRender itemRenderInfo = new MadModelItemRender(
                false,
                false,
                false,
                false,
                new MadModelScale(1.4F, 1.4F, 1.4F),            // EQUIPPED
                new MadModelPosition(0.1F, 0.3F, 0.3F),
                new MadModelRotation(90.0F, 0.0F, 1.0F, 0.0F),
                new MadModelScale(1.0F, 1.0F, 1.0F),            // FIRST_PERSON           
                new MadModelPosition(0.2F, 0.9F, 0.5F),         
                new MadModelRotation(90.0F, 0.0F, 0.5F, 0.0F),  
                new MadModelScale(1.0F, 1.0F, 1.0F),            // INVENTORY         
                new MadModelPosition(0.5F, 0.42F, 0.5F),
                new MadModelRotation(270.0F, 0.0F, 0.5F, 0.0F), 
                new MadModelScale(1.0F, 1.0F, 1.0F),            // ENTITY            
                new MadModelPosition(0.5F, 0.5F, 0.5F),
                new MadModelRotation(180.0F, 0.0F, 1.0F, 0.0F));
        
        // DEFAULT MODEL REDNERING INFORMATION.
        MadModel itemModelInfo = new MadModel(null, null, itemRenderInfo, null);
        
        // -----------
        // DNA SAMPLES
        // -----------
        {
            List<MadMetaItemData> dnaSampleSubitems = new ArrayList<MadMetaItemData>();
            
            {
                // CAVE SPIDER.
                List<MadItemRenderPass> iconCaveSpider = new ArrayList<MadItemRenderPass>();
                iconCaveSpider.add(new MadItemRenderPass(0, "dnaSample_overlay", 803406));
                iconCaveSpider.add(new MadItemRenderPass(1, "dnaSample", 11013646));
                dnaSampleSubitems.add(new MadMetaItemData(0, "CaveSpider", false, null, null, null, itemModelInfo, iconCaveSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CHICKEN.
                List<MadItemRenderPass> iconChicken = new ArrayList<MadItemRenderPass>();
                iconChicken.add(new MadItemRenderPass(0, "dnaSample_overlay", 10592673));
                iconChicken.add(new MadItemRenderPass(1, "dnaSample", 16711680));
                dnaSampleSubitems.add(new MadMetaItemData(1, "Chicken", false, null, null, null, itemModelInfo, iconChicken.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // COW.
                List<MadItemRenderPass> iconCow = new ArrayList<MadItemRenderPass>();
                iconCow.add(new MadItemRenderPass(0, "dnaSample_overlay", 4470310));
                iconCow.add(new MadItemRenderPass(1, "dnaSample", 10592673));
                dnaSampleSubitems.add(new MadMetaItemData(2, "Cow", false, null, null, null, itemModelInfo, iconCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CREEPER.
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "dnaSample_overlay", 894731));
                iconCreeper.add(new MadItemRenderPass(1, "dnaSample", 0));
                dnaSampleSubitems.add(new MadMetaItemData(3, "Creeper", false, null, null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERMAN.
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "dnaSample_overlay", 1447446));
                iconCreeper.add(new MadItemRenderPass(1, "dnaSample", 0));
                dnaSampleSubitems.add(new MadMetaItemData(4, "Enderman", false, null, null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // GHAST.
                List<MadItemRenderPass> iconGhast = new ArrayList<MadItemRenderPass>();
                iconGhast.add(new MadItemRenderPass(0, "dnaSample_overlay", 16382457));
                iconGhast.add(new MadItemRenderPass(1, "dnaSample", 12369084));
                dnaSampleSubitems.add(new MadMetaItemData(5, "Ghast", false, null, null, null, itemModelInfo, iconGhast.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // HORSE.
                List<MadItemRenderPass> iconHorse = new ArrayList<MadItemRenderPass>();
                iconHorse.add(new MadItemRenderPass(0, "dnaSample_overlay", 12623485));
                iconHorse.add(new MadItemRenderPass(1, "dnaSample", 15656192));
                dnaSampleSubitems.add(new MadMetaItemData(6, "Horse", false, null, null, null, itemModelInfo, iconHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MUSHROOM COW.
                List<MadItemRenderPass> iconMushroomCow = new ArrayList<MadItemRenderPass>();
                iconMushroomCow.add(new MadItemRenderPass(0, "dnaSample_overlay", 10489616));
                iconMushroomCow.add(new MadItemRenderPass(1, "dnaSample", 12040119));
                dnaSampleSubitems.add(new MadMetaItemData(7, "MushroomCow", false, null, null, null, itemModelInfo, iconMushroomCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // OCELOT.
                List<MadItemRenderPass> iconOcelot = new ArrayList<MadItemRenderPass>();
                iconOcelot.add(new MadItemRenderPass(0, "dnaSample_overlay", 15720061));
                iconOcelot.add(new MadItemRenderPass(1, "dnaSample", 5653556));
                dnaSampleSubitems.add(new MadMetaItemData(8, "Ocelot", false, null, null, null, itemModelInfo, iconOcelot.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // PIG.
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "dnaSample_overlay", 15771042));
                iconPig.add(new MadItemRenderPass(1, "dnaSample", 14377823));
                dnaSampleSubitems.add(new MadMetaItemData(9, "Pig", false, null, null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SHEEP.
                List<MadItemRenderPass> iconSheep = new ArrayList<MadItemRenderPass>();
                iconSheep.add(new MadItemRenderPass(0, "dnaSample_overlay", 15198183));
                iconSheep.add(new MadItemRenderPass(1, "dnaSample", 16758197));
                dnaSampleSubitems.add(new MadMetaItemData(10, "Sheep", false, null, null, null, itemModelInfo, iconSheep.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SKELETON.
                List<MadItemRenderPass> iconSkeleton = new ArrayList<MadItemRenderPass>();
                iconSkeleton.add(new MadItemRenderPass(0, "dnaSample_overlay", 12698049));
                iconSkeleton.add(new MadItemRenderPass(1, "dnaSample", 4802889));
                dnaSampleSubitems.add(new MadMetaItemData(11, "Skeleton", false, null, null, null, itemModelInfo, iconSkeleton.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SPIDER.
                List<MadItemRenderPass> iconSpider = new ArrayList<MadItemRenderPass>();
                iconSpider.add(new MadItemRenderPass(0, "dnaSample_overlay", 3419431));
                iconSpider.add(new MadItemRenderPass(1, "dnaSample", 11013646));
                dnaSampleSubitems.add(new MadMetaItemData(12, "Spider", false, null, null, null, itemModelInfo, iconSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SQUID.
                List<MadItemRenderPass> iconSquid = new ArrayList<MadItemRenderPass>();
                iconSquid.add(new MadItemRenderPass(0, "dnaSample_overlay", 2243405));
                iconSquid.add(new MadItemRenderPass(1, "dnaSample", 7375001));
                dnaSampleSubitems.add(new MadMetaItemData(13, "Squid", false, null, null, null, itemModelInfo, iconSquid.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // VILLAGER.
                List<MadItemRenderPass> iconVillager = new ArrayList<MadItemRenderPass>();
                iconVillager.add(new MadItemRenderPass(0, "dnaSample_overlay", 5651507));
                iconVillager.add(new MadItemRenderPass(1, "dnaSample", 12422002));
                dnaSampleSubitems.add(new MadMetaItemData(14, "Villager", false, null, null, null, itemModelInfo, iconVillager.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WITCH.
                List<MadItemRenderPass> iconWitch = new ArrayList<MadItemRenderPass>();
                iconWitch.add(new MadItemRenderPass(0, "dnaSample_overlay", 3407872));
                iconWitch.add(new MadItemRenderPass(1, "dnaSample", 5349438));
                dnaSampleSubitems.add(new MadMetaItemData(15, "Witch", false, null, null, null, itemModelInfo, iconWitch.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WOLF.
                List<MadItemRenderPass> iconWolf = new ArrayList<MadItemRenderPass>();
                iconWolf.add(new MadItemRenderPass(0, "dnaSample_overlay", 14144467));
                iconWolf.add(new MadItemRenderPass(1, "dnaSample", 13545366));
                dnaSampleSubitems.add(new MadMetaItemData(16, "Wolf", false, null, null, null, itemModelInfo, iconWolf.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ZOMBIE.
                List<MadItemRenderPass> iconZombie = new ArrayList<MadItemRenderPass>();
                iconZombie.add(new MadItemRenderPass(0, "dnaSample_overlay", 44975));
                iconZombie.add(new MadItemRenderPass(1, "dnaSample", 7969893));
                dnaSampleSubitems.add(new MadMetaItemData(17, "Zombie", false, null, null, null, itemModelInfo, iconZombie.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BAT.
                List<MadItemRenderPass> iconBat = new ArrayList<MadItemRenderPass>();
                iconBat.add(new MadItemRenderPass(0, "dnaSample_overlay", 4996656));
                iconBat.add(new MadItemRenderPass(1, "dnaSample", 986895));
                dnaSampleSubitems.add(new MadMetaItemData(18, "Bat", false, null, null, null, itemModelInfo, iconBat.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SLIME.
                List<MadItemRenderPass> iconSlime = new ArrayList<MadItemRenderPass>();
                iconSlime.add(new MadItemRenderPass(0, "dnaSample_overlay", 5349438));
                iconSlime.add(new MadItemRenderPass(1, "dnaSample", 8306542));
                dnaSampleSubitems.add(new MadMetaItemData(19, "Slime", false, null, null, null, itemModelInfo, iconSlime.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData dnaSamples = new MadItemFactoryProductData("dna", true, 0, 64, 0, 0, 0, false, null, dnaSampleSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(dnaSamples);
        }
        
        // ----------
        // DATA REELS
        // ----------
        {
            List<MadMetaItemData> dataReelSubitems = new ArrayList<MadMetaItemData>();
            
            {
                // DATA REEL EMPTY
                List<MadItemRenderPass> iconDataReelEmpty = new ArrayList<MadItemRenderPass>();
                iconDataReelEmpty.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconDataReelEmpty.add(new MadItemRenderPass(1, "genomeDataReel1", 3515848));
                iconDataReelEmpty.add(new MadItemRenderPass(2, "genomeDataReel2", 3515848));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentMagneticTape:0:1",
                        "1:madscience:componentMagneticTape:0:1",
                        "2:madscience:componentMagneticTape:0:1",
                        "3:madscience:componentMagneticTape:0:1",
                        "4:madscience:circuitEmerald:0:1",
                        "5:madscience:componentMagneticTape:0:1",
                        "6:madscience:componentMagneticTape:0:1",
                        "7:madscience:componentMagneticTape:0:1",
                        "8:madscience:componentMagneticTape:0:1"));
                
                dataReelSubitems.add(new MadMetaItemData(0, "DataReelEmpty", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconDataReelEmpty.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CAVE SPIDER.
                List<MadItemRenderPass> iconCaveSpider = new ArrayList<MadItemRenderPass>();
                iconCaveSpider.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCaveSpider.add(new MadItemRenderPass(1, "genomeDataReel1", 803406));
                iconCaveSpider.add(new MadItemRenderPass(2, "genomeDataReel2", 11013646));
                dataReelSubitems.add(new MadMetaItemData(1, "CaveSpider", false, null, null, null, itemModelInfo, iconCaveSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CHICKEN
                List<MadItemRenderPass> iconChicken = new ArrayList<MadItemRenderPass>();
                iconChicken.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconChicken.add(new MadItemRenderPass(1, "genomeDataReel1", 10592673));
                iconChicken.add(new MadItemRenderPass(2, "genomeDataReel2", 16711680));
                dataReelSubitems.add(new MadMetaItemData(2, "Chicken", false, null, null, null, itemModelInfo, iconChicken.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // COW
                List<MadItemRenderPass> iconCow = new ArrayList<MadItemRenderPass>();
                iconCow.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCow.add(new MadItemRenderPass(1, "genomeDataReel1", 4470310));
                iconCow.add(new MadItemRenderPass(2, "genomeDataReel2", 10592673));
                dataReelSubitems.add(new MadMetaItemData(3, "Cow", false, null, null, null, itemModelInfo, iconCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CREEPER
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCreeper.add(new MadItemRenderPass(1, "genomeDataReel1", 894731));
                iconCreeper.add(new MadItemRenderPass(2, "genomeDataReel2", 0));
                dataReelSubitems.add(new MadMetaItemData(4, "Creeper", false, null, null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERMAN
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCreeper.add(new MadItemRenderPass(1, "genomeDataReel1", 1447446));
                iconCreeper.add(new MadItemRenderPass(2, "genomeDataReel2", 0));
                dataReelSubitems.add(new MadMetaItemData(5, "Enderman", false, null, null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // GHAST
                List<MadItemRenderPass> iconGhast = new ArrayList<MadItemRenderPass>();
                iconGhast.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconGhast.add(new MadItemRenderPass(1, "genomeDataReel1", 16382457));
                iconGhast.add(new MadItemRenderPass(2, "genomeDataReel2", 12369084));
                dataReelSubitems.add(new MadMetaItemData(6, "Ghast", false, null, null, null, itemModelInfo, iconGhast.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // HORSE
                List<MadItemRenderPass> iconHorse = new ArrayList<MadItemRenderPass>();
                iconHorse.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconHorse.add(new MadItemRenderPass(1, "genomeDataReel1", 12623485));
                iconHorse.add(new MadItemRenderPass(2, "genomeDataReel2", 15656192));
                dataReelSubitems.add(new MadMetaItemData(7, "Horse", false, null, null, null, itemModelInfo, iconHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MUSHROOM COW
                List<MadItemRenderPass> iconMushroomCow = new ArrayList<MadItemRenderPass>();
                iconMushroomCow.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconMushroomCow.add(new MadItemRenderPass(1, "genomeDataReel1", 10489616));
                iconMushroomCow.add(new MadItemRenderPass(2, "genomeDataReel2", 12040119));
                dataReelSubitems.add(new MadMetaItemData(8, "MushroomCow", false, null, null, null, itemModelInfo, iconMushroomCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // OCELOT
                List<MadItemRenderPass> iconOcelot = new ArrayList<MadItemRenderPass>();
                iconOcelot.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconOcelot.add(new MadItemRenderPass(1, "genomeDataReel1", 15720061));
                iconOcelot.add(new MadItemRenderPass(2, "genomeDataReel2", 5653556));
                dataReelSubitems.add(new MadMetaItemData(9, "Ocelot", false, null, null, null, itemModelInfo, iconOcelot.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // PIG
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconPig.add(new MadItemRenderPass(1, "genomeDataReel1", 15771042));
                iconPig.add(new MadItemRenderPass(2, "genomeDataReel2", 14377823));
                dataReelSubitems.add(new MadMetaItemData(10, "Pig", false, null, null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // PIG ZOMBIE
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconPig.add(new MadItemRenderPass(1, "genomeDataReel1", 15373203));
                iconPig.add(new MadItemRenderPass(2, "genomeDataReel2", 5009705));
                dataReelSubitems.add(new MadMetaItemData(11, "PigZombie", false, null, null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SHEEP
                List<MadItemRenderPass> iconSheep = new ArrayList<MadItemRenderPass>();
                iconSheep.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSheep.add(new MadItemRenderPass(1, "genomeDataReel1", 15198183));
                iconSheep.add(new MadItemRenderPass(2, "genomeDataReel2", 16758197));
                dataReelSubitems.add(new MadMetaItemData(12, "Sheep", false, null, null, null, itemModelInfo, iconSheep.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SKELETON
                List<MadItemRenderPass> iconSkeleton = new ArrayList<MadItemRenderPass>();
                iconSkeleton.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSkeleton.add(new MadItemRenderPass(1, "genomeDataReel1", 12698049));
                iconSkeleton.add(new MadItemRenderPass(2, "genomeDataReel2", 4802889));
                dataReelSubitems.add(new MadMetaItemData(13, "Skeleton", false, null, null, null, itemModelInfo, iconSkeleton.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SQUID
                List<MadItemRenderPass> iconSquid = new ArrayList<MadItemRenderPass>();
                iconSquid.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSquid.add(new MadItemRenderPass(1, "genomeDataReel1", 2243405));
                iconSquid.add(new MadItemRenderPass(2, "genomeDataReel2", 7375001));
                dataReelSubitems.add(new MadMetaItemData(14, "Squid", false, null, null, null, itemModelInfo, iconSquid.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SPIDER
                List<MadItemRenderPass> iconSpider = new ArrayList<MadItemRenderPass>();
                iconSpider.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSpider.add(new MadItemRenderPass(1, "genomeDataReel1", 3419431));
                iconSpider.add(new MadItemRenderPass(2, "genomeDataReel2", 11013646));
                dataReelSubitems.add(new MadMetaItemData(15, "Spider", false, null, null, null, itemModelInfo, iconSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // VILLAGER
                List<MadItemRenderPass> iconVillager = new ArrayList<MadItemRenderPass>();
                iconVillager.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconVillager.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconVillager.add(new MadItemRenderPass(2, "genomeDataReel2", 12422002));
                dataReelSubitems.add(new MadMetaItemData(16, "Villager", false, null, null, null, itemModelInfo, iconVillager.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WITCH
                List<MadItemRenderPass> iconWitch = new ArrayList<MadItemRenderPass>();
                iconWitch.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconWitch.add(new MadItemRenderPass(1, "genomeDataReel1", 3407872));
                iconWitch.add(new MadItemRenderPass(2, "genomeDataReel2", 5349438));
                dataReelSubitems.add(new MadMetaItemData(17, "Witch", false, null, null, null, itemModelInfo, iconWitch.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WOLF
                List<MadItemRenderPass> iconWolf = new ArrayList<MadItemRenderPass>();
                iconWolf.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconWolf.add(new MadItemRenderPass(1, "genomeDataReel1", 14144467));
                iconWolf.add(new MadItemRenderPass(2, "genomeDataReel2", 13545366));
                dataReelSubitems.add(new MadMetaItemData(18, "Wolf", false, null, null, null, itemModelInfo, iconWolf.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ZOMBIE
                List<MadItemRenderPass> iconZombie = new ArrayList<MadItemRenderPass>();
                iconZombie.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconZombie.add(new MadItemRenderPass(1, "genomeDataReel1", 44975));
                iconZombie.add(new MadItemRenderPass(2, "genomeDataReel2", 7969893));
                dataReelSubitems.add(new MadMetaItemData(19, "Zombie", false, null, null, null, itemModelInfo, iconZombie.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BAT
                List<MadItemRenderPass> iconBat = new ArrayList<MadItemRenderPass>();
                iconBat.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconBat.add(new MadItemRenderPass(1, "genomeDataReel1", 4996656));
                iconBat.add(new MadItemRenderPass(2, "genomeDataReel2", 986895));
                dataReelSubitems.add(new MadMetaItemData(20, "Bat", false, null, null, null, itemModelInfo, iconBat.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SLIME
                List<MadItemRenderPass> iconSlime = new ArrayList<MadItemRenderPass>();
                iconSlime.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSlime.add(new MadItemRenderPass(1, "genomeDataReel1", 5349438));
                iconSlime.add(new MadItemRenderPass(2, "genomeDataReel2", 8306542));
                dataReelSubitems.add(new MadMetaItemData(21, "Slime", false, null, null, null, itemModelInfo, iconSlime.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WITHER SKELETON
                List<MadItemRenderPass> iconWitherSkeleton = new ArrayList<MadItemRenderPass>();
                iconWitherSkeleton.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconWitherSkeleton.add(new MadItemRenderPass(1, "genomeDataReel1", MadColors.endermanEgg()));
                iconWitherSkeleton.add(new MadItemRenderPass(2, "genomeDataReel2", MadColors.skeletonEgg()));
                dataReelSubitems.add(new MadMetaItemData(22, "WitherSkeleton", false, null, null, null, itemModelInfo, iconWitherSkeleton.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // VILLAGER ZOMBIE
                List<MadItemRenderPass> iconVillagerZombie = new ArrayList<MadItemRenderPass>();
                iconVillagerZombie.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconVillagerZombie.add(new MadItemRenderPass(1, "genomeDataReel1", MadColors.villagerEgg()));
                iconVillagerZombie.add(new MadItemRenderPass(2, "genomeDataReel2", MadColors.zombieEgg()));
                dataReelSubitems.add(new MadMetaItemData(23, "VillagerZombie", false, null, null, null, itemModelInfo, iconVillagerZombie.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SKELETON HORSE
                List<MadItemRenderPass> iconSkeletonHorse = new ArrayList<MadItemRenderPass>();
                iconSkeletonHorse.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSkeletonHorse.add(new MadItemRenderPass(1, "genomeDataReel1", MadColors.horseEgg()));
                iconSkeletonHorse.add(new MadItemRenderPass(2, "genomeDataReel2", MadColors.skeletonEgg()));
                dataReelSubitems.add(new MadMetaItemData(24, "SkeletonHorse", false, null, null, null, itemModelInfo, iconSkeletonHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ZOMBIE HORSE
                List<MadItemRenderPass> iconZombieHorse = new ArrayList<MadItemRenderPass>();
                iconZombieHorse.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconZombieHorse.add(new MadItemRenderPass(1, "genomeDataReel1", MadColors.horseEgg()));
                iconZombieHorse.add(new MadItemRenderPass(2, "genomeDataReel2", MadColors.zombieEgg()));
                dataReelSubitems.add(new MadMetaItemData(25, "ZombieHorse", false, null, null, null, itemModelInfo, iconZombieHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData genomeDataReels = new MadItemFactoryProductData("datareel", true, 0, 1, 0, 0, 0, false, null, dataReelSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(genomeDataReels);
        }
        
        // -------
        // NEEDLES
        // -------
        {
            List<MadMetaItemData> needleSubitems = new ArrayList<MadMetaItemData>();
            
            {
                // CAVE SPIDER
                List<MadItemRenderPass> iconCaveSpider = new ArrayList<MadItemRenderPass>();
                iconCaveSpider.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconCaveSpider.add(new MadItemRenderPass(1, "needleDNA1", 803406));
                iconCaveSpider.add(new MadItemRenderPass(2, "needleDNA2", 11013646));
                needleSubitems.add(new MadMetaItemData(0, "CaveSpider", false, null, null, null, itemModelInfo, iconCaveSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CHICKEN
                List<MadItemRenderPass> iconChicken = new ArrayList<MadItemRenderPass>();
                iconChicken.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconChicken.add(new MadItemRenderPass(1, "needleDNA1", 10592673));
                iconChicken.add(new MadItemRenderPass(2, "needleDNA2", 16711680));
                needleSubitems.add(new MadMetaItemData(1, "Chicken", false, null, null, null, itemModelInfo, iconChicken.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // COW
                List<MadItemRenderPass> iconCow = new ArrayList<MadItemRenderPass>();
                iconCow.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconCow.add(new MadItemRenderPass(1, "needleDNA1", 4470310));
                iconCow.add(new MadItemRenderPass(2, "needleDNA2", 10592673));
                needleSubitems.add(new MadMetaItemData(2, "Cow", false, null, null, null, itemModelInfo, iconCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CREEPER
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconCreeper.add(new MadItemRenderPass(1, "needleDNA1", 894731));
                iconCreeper.add(new MadItemRenderPass(2, "needleDNA2", 0));
                needleSubitems.add(new MadMetaItemData(3, "Creeper", false, null, null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERMAN
                List<MadItemRenderPass> iconEnderman = new ArrayList<MadItemRenderPass>();
                iconEnderman.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconEnderman.add(new MadItemRenderPass(1, "needleDNA1", 1447446));
                iconEnderman.add(new MadItemRenderPass(2, "needleDNA2", 0));
                needleSubitems.add(new MadMetaItemData(4, "Enderman", false, null, null, null, itemModelInfo, iconEnderman.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // HORSE
                List<MadItemRenderPass> iconHorse = new ArrayList<MadItemRenderPass>();
                iconHorse.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconHorse.add(new MadItemRenderPass(1, "needleDNA1", 12623485));
                iconHorse.add(new MadItemRenderPass(2, "needleDNA2", 15656192));
                needleSubitems.add(new MadMetaItemData(5, "Horse", false, null, null, null, itemModelInfo, iconHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MUSHROOM COW
                List<MadItemRenderPass> iconMushroomCow = new ArrayList<MadItemRenderPass>();
                iconMushroomCow.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconMushroomCow.add(new MadItemRenderPass(1, "needleDNA1", 10489616));
                iconMushroomCow.add(new MadItemRenderPass(2, "needleDNA2", 12040119));
                needleSubitems.add(new MadMetaItemData(6, "MushroomCow", false, null, null, null, itemModelInfo, iconMushroomCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // OCELOT
                List<MadItemRenderPass> iconOcelot = new ArrayList<MadItemRenderPass>();
                iconOcelot.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconOcelot.add(new MadItemRenderPass(1, "needleDNA1", 15720061));
                iconOcelot.add(new MadItemRenderPass(2, "needleDNA2", 5653556));
                needleSubitems.add(new MadMetaItemData(7, "Ocelot", false, null, null, null, itemModelInfo, iconOcelot.toArray(new MadItemRenderPass[]{})));
            }     
            
            {
                // PIG
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconPig.add(new MadItemRenderPass(1, "needleDNA1", 15771042));
                iconPig.add(new MadItemRenderPass(2, "needleDNA2", 14377823));
                needleSubitems.add(new MadMetaItemData(8, "Pig", false, null, null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SHEEP
                List<MadItemRenderPass> iconSheep = new ArrayList<MadItemRenderPass>();
                iconSheep.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconSheep.add(new MadItemRenderPass(1, "needleDNA1", 15771042));
                iconSheep.add(new MadItemRenderPass(2, "needleDNA2", 14377823));
                needleSubitems.add(new MadMetaItemData(9, "Sheep", false, null, null, null, itemModelInfo, iconSheep.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SPIDER
                List<MadItemRenderPass> iconSpider = new ArrayList<MadItemRenderPass>();
                iconSpider.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconSpider.add(new MadItemRenderPass(1, "needleDNA1", 3419431));
                iconSpider.add(new MadItemRenderPass(2, "needleDNA2", 11013646));
                needleSubitems.add(new MadMetaItemData(10, "Spider", false, null, null, null, itemModelInfo, iconSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SQUID
                List<MadItemRenderPass> iconSquid = new ArrayList<MadItemRenderPass>();
                iconSquid.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconSquid.add(new MadItemRenderPass(1, "needleDNA1", 2243405));
                iconSquid.add(new MadItemRenderPass(2, "needleDNA2", 7375001));
                needleSubitems.add(new MadMetaItemData(11, "Squid", false, null, null, null, itemModelInfo, iconSquid.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // VILLAGER
                List<MadItemRenderPass> iconVillager = new ArrayList<MadItemRenderPass>();
                iconVillager.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconVillager.add(new MadItemRenderPass(1, "needleDNA1", 5651507));
                iconVillager.add(new MadItemRenderPass(2, "needleDNA2", 12422002));
                needleSubitems.add(new MadMetaItemData(12, "Villager", false, null, null, null, itemModelInfo, iconVillager.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WITCH
                List<MadItemRenderPass> iconWitch = new ArrayList<MadItemRenderPass>();
                iconWitch.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconWitch.add(new MadItemRenderPass(1, "needleDNA1", 3407872));
                iconWitch.add(new MadItemRenderPass(2, "needleDNA2", 5349438));
                needleSubitems.add(new MadMetaItemData(13, "Witch", false, null, null, null, itemModelInfo, iconWitch.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WOLF
                List<MadItemRenderPass> iconWolf = new ArrayList<MadItemRenderPass>();
                iconWolf.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconWolf.add(new MadItemRenderPass(1, "needleDNA1", 14144467));
                iconWolf.add(new MadItemRenderPass(2, "needleDNA2", 13545366));
                needleSubitems.add(new MadMetaItemData(14, "Wolf", false, null, null, null, itemModelInfo, iconWolf.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ZOMBIE
                List<MadItemRenderPass> iconZombie = new ArrayList<MadItemRenderPass>();
                iconZombie.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconZombie.add(new MadItemRenderPass(1, "needleDNA1", 44975));
                iconZombie.add(new MadItemRenderPass(2, "needleDNA2", 7969893));
                needleSubitems.add(new MadMetaItemData(15, "Zombie", false, null, null, null, itemModelInfo, iconZombie.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BAT
                List<MadItemRenderPass> iconBat = new ArrayList<MadItemRenderPass>();
                iconBat.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconBat.add(new MadItemRenderPass(1, "needleDNA1", 4996656));
                iconBat.add(new MadItemRenderPass(2, "needleDNA2", 986895));
                needleSubitems.add(new MadMetaItemData(16, "Bat", false, null, null, null, itemModelInfo, iconBat.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MUTANT
                List<MadItemRenderPass> iconMutant = new ArrayList<MadItemRenderPass>();
                iconMutant.add(new MadItemRenderPass(0, "needleDNA_overlay", 16777215));
                iconMutant.add(new MadItemRenderPass(1, "needleDNA1", 5349438));
                iconMutant.add(new MadItemRenderPass(2, "needleDNA2", 8306542));
                needleSubitems.add(new MadMetaItemData(17, "Mutant", false, null, null, null, itemModelInfo, iconMutant.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData needles = new MadItemFactoryProductData("needle", true, 0, 64, 2, 0, 0, false, null, needleSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(needles);
        }
        
        // ----------
        // COMPONENTS
        // ----------
        {
            List<MadMetaItemData> componentSubitems = new ArrayList<MadMetaItemData>();
            
            {
                // CASE
                List<MadItemRenderPass> iconCase = new ArrayList<MadItemRenderPass>();
                iconCase.add(new MadItemRenderPass(0, "componentCase", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:minecraft:ingotIron:0:1",
                        "1:minecraft:stick:0:1",
                        "2:minecraft:ingotIron:0:1",
                        "3:minecraft:stick:0:1",
                        null,
                        "5:minecraft:stick:0:1",
                        "6:minecraft:ingotIron:0:1",
                        "7:minecraft:stick:0:1",
                        "8:minecraft:ingotIron:0:1"));
                
                componentSubitems.add(new MadMetaItemData(0, "Case", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconCase.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CPU
                List<MadItemRenderPass> iconCPU = new ArrayList<MadItemRenderPass>();
                iconCPU.add(new MadItemRenderPass(0, "componentCPU", 16777215));
                componentSubitems.add(new MadMetaItemData(1, "CPU", false, null, null, null, itemModelInfo, iconCPU.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // FAN
                List<MadItemRenderPass> iconFan = new ArrayList<MadItemRenderPass>();
                iconFan.add(new MadItemRenderPass(0, "componentFan", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentCase:0:1",
                        "1:minecraft:ingotIron:0:1",
                        "2:madscience:componentCase:0:1",
                        "3:minecraft:ingotIron:0:1",
                        "4:minecraft:ingotIron:0:1",
                        "5:minecraft:ingotIron:0:1",
                        "6:madscience:componentCase:0:1",
                        "7:minecraft:ingotIron:0:1",
                        "8:madscience:componentCase:0:1"));
                
                componentSubitems.add(new MadMetaItemData(2, "Fan", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconFan.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // POWER SUPPLY
                List<MadItemRenderPass> iconPowerSupply = new ArrayList<MadItemRenderPass>();
                iconPowerSupply.add(new MadItemRenderPass(0, "componentPowerSupply", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:minecraft:ingotIron:0:1",
                        "1:minecraft:blockRedstone:0:1",
                        "2:minecraft:ingotIron:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:madscience:circuitRedstone:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:minecraft:ingotIron:0:1",
                        "7:minecraft:blockRedstone:0:1",
                        "8:minecraft:ingotIron:0:1"));
                
                componentSubitems.add(new MadMetaItemData(3, "PowerSupply", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconPowerSupply.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // RAM
                List<MadItemRenderPass> iconRAM = new ArrayList<MadItemRenderPass>();
                iconRAM.add(new MadItemRenderPass(0, "componentRAM", 16777215));
                componentSubitems.add(new MadMetaItemData(4, "RAM", false, null, null, null, itemModelInfo, iconRAM.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SILICON WAFER
                List<MadItemRenderPass> iconSiliconWafer = new ArrayList<MadItemRenderPass>();
                iconSiliconWafer.add(new MadItemRenderPass(0, "componentSiliconWafer", 16777215));
                componentSubitems.add(new MadMetaItemData(5, "SiliconWafer", false, null, null, null, itemModelInfo, iconSiliconWafer.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // COMPUTER
                List<MadItemRenderPass> iconComputer = new ArrayList<MadItemRenderPass>();
                iconComputer.add(new MadItemRenderPass(0, "componentComputer", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentCase:0:1",
                        "1:madscience:componentFan:0:1",
                        "2:madscience:componentCase:0:1",
                        "3:madscience:componentRAM:0:1",
                        "4:madscience:componentCPU:0:1",
                        "5:madscience:componentPowerSupply:0:1",
                        "6:madscience:componentCase:0:1",
                        "7:madscience:componentScreen:0:1",
                        "8:madscience:componentCase:0:1"));
                
                componentSubitems.add(new MadMetaItemData(6, "Computer", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconComputer.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // FUSED QUARTZ
                List<MadItemRenderPass> iconFusedQuartz = new ArrayList<MadItemRenderPass>();
                iconFusedQuartz.add(new MadItemRenderPass(0, "componentFusedQuartz", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentCase:0:1",
                        "1:madscience:componentFan:0:1",
                        "2:madscience:componentCase:0:1",
                        "3:madscience:componentRAM:0:1",
                        "4:madscience:componentCPU:0:1",
                        "5:madscience:componentPowerSupply:0:1",
                        "6:madscience:componentCase:0:1",
                        "7:madscience:componentScreen:0:1",
                        "8:madscience:componentCase:0:1"));
                
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPELESS,
                        8,
                        "0:minecraft:fireball:0:1",
                        "1:minecraft:sand:0:1",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));
                
                // VANILLA FURNACE RECIPE
                List<MadFurnaceRecipe> subItemFurnaceRecipes = new ArrayList<MadFurnaceRecipe>();
                subItemFurnaceRecipes.add(new MadFurnaceRecipe("minecraft:netherquartz:0:1", "madscience:componentFusedQuartz:0:1"));
                subItemFurnaceRecipes.add(new MadFurnaceRecipe("minecraft:quartzBlock:0:1", "madscience:componentFusedQuartz:0:4"));
                
                componentSubitems.add(new MadMetaItemData(7, "FusedQuartz", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), subItemFurnaceRecipes.toArray(new MadFurnaceRecipe[]{}), null, itemModelInfo, iconFusedQuartz.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MAGNETIC TAPE
                List<MadItemRenderPass> iconMagneticTape = new ArrayList<MadItemRenderPass>();
                iconMagneticTape.add(new MadItemRenderPass(0, "componentMagneticTape", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:minecraft:redstoneDust:0:1",
                        "1:minecraft:redstoneDust:0:1",
                        "2:minecraft:redstoneDust:0:1",
                        "3:minecraft:slimeball:0:1",
                        "4:minecraft:slimeball:0:1",
                        "5:minecraft:slimeball:0:1",
                        null,
                        null,
                        null));
                
                componentSubitems.add(new MadMetaItemData(8, "MagneticTape", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconMagneticTape.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SCREEN
                List<MadItemRenderPass> iconScreen = new ArrayList<MadItemRenderPass>();
                iconScreen.add(new MadItemRenderPass(0, "componentScreen", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:minecraft:glass:0:1",
                        "1:minecraft:glass:0:1",
                        "2:minecraft:glass:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:madscience:componentTransistor:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:circuitRedstone:0:1",
                        "7:madscience:circuitDiamond:0:1",
                        "8:madscience:circuitRedstone:0:1"));
                
                componentSubitems.add(new MadMetaItemData(9, "Screen", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconScreen.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // THUMPER
                List<MadItemRenderPass> iconThumper = new ArrayList<MadItemRenderPass>();
                iconThumper.add(new MadItemRenderPass(0, "componentThumper", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:minecraft:pistonBase:0:1",
                        "1:minecraft:blockRedstone:0:1",
                        "2:minecraft:pistonBase:0:1",
                        "3:madscience:enderslimeBlock:0:1",
                        "4:madscience:componentPowerSupply:0:1",
                        "5:madscience:enderslimeBlock:0:1",
                        "6:madscience:enderslimeBlock:0:1",
                        "7:madscience:enderslimeBlock:0:1",
                        "8:madscience:enderslimeBlock:0:1"));
                
                componentSubitems.add(new MadMetaItemData(10, "Thumper", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconThumper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // TRANSISTOR
                List<MadItemRenderPass> iconTransistor = new ArrayList<MadItemRenderPass>();
                iconTransistor.add(new MadItemRenderPass(0, "componentTransistor", 16777215));
                componentSubitems.add(new MadMetaItemData(11, "Transistor", false, null, null, null, itemModelInfo, iconTransistor.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERSLIME
                List<MadItemRenderPass> iconEnderslime = new ArrayList<MadItemRenderPass>();
                iconEnderslime.add(new MadItemRenderPass(0, "componentEnderslime", 16777215));
                componentSubitems.add(new MadMetaItemData(12, "Enderslime", false, null, null, null, itemModelInfo, iconEnderslime.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // LOGO
                List<MadItemRenderPass> iconLogo = new ArrayList<MadItemRenderPass>();
                iconLogo.add(new MadItemRenderPass(0, "logo", 16777215));
                componentSubitems.add(new MadMetaItemData(13, "Logo", true, null, null, null, itemModelInfo, iconLogo.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData components = new MadItemFactoryProductData("components", true, 0, 64, 0, 0, 0, false, null, componentSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(components);
        }
        
        // --------
        // CIRCUITS
        // --------
        {
            List<MadMetaItemData> circuitSubitems = new ArrayList<MadMetaItemData>();
            
            {
                // COMPARATOR
                List<MadItemRenderPass> iconComparator = new ArrayList<MadItemRenderPass>();
                iconComparator.add(new MadItemRenderPass(0, "circuitComparator", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:comparator:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(0, "Comparator", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconComparator.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // DIAMOND
                List<MadItemRenderPass> iconDiamond = new ArrayList<MadItemRenderPass>();
                iconDiamond.add(new MadItemRenderPass(0, "circuitDiamond", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:diamond:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(1, "Diamond", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconDiamond.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // EMERALD
                List<MadItemRenderPass> iconEmerald = new ArrayList<MadItemRenderPass>();
                iconEmerald.add(new MadItemRenderPass(0, "circuitEmerald", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:emerald:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(2, "Emerald", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconEmerald.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDEREYE
                List<MadItemRenderPass> iconEnderEye = new ArrayList<MadItemRenderPass>();
                iconEnderEye.add(new MadItemRenderPass(0, "circuitEnderEye", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:eyeOfEnder:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(3, "Endereye", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconEnderEye.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERPEARL
                List<MadItemRenderPass> iconEnderpearl = new ArrayList<MadItemRenderPass>();
                iconEnderpearl.add(new MadItemRenderPass(0, "circuitEnderPearl", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:enderPearl:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(4, "Enderpearl", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconEnderpearl.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // GLOWSTONE
                List<MadItemRenderPass> iconGlowstone = new ArrayList<MadItemRenderPass>();
                iconGlowstone.add(new MadItemRenderPass(0, "circuitGlowstone", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:lightgem:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(5, "Glowstone", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconGlowstone.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // REDSTONE
                List<MadItemRenderPass> iconRedstone = new ArrayList<MadItemRenderPass>();
                iconRedstone.add(new MadItemRenderPass(0, "circuitRedstone", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:redstoneDust:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(6, "Redstone", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconRedstone.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SPIDEREYE
                List<MadItemRenderPass> iconSpidereye = new ArrayList<MadItemRenderPass>();
                iconSpidereye.add(new MadItemRenderPass(0, "circuitSpiderEye", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:madscience:componentTransistor:0:1",
                        "1:madscience:componentTransistor:0:1",
                        "2:madscience:componentTransistor:0:1",
                        "3:madscience:componentTransistor:0:1",
                        "4:minecraft:spiderEye:0:1",
                        "5:madscience:componentTransistor:0:1",
                        "6:madscience:componentTransistor:0:1",
                        "7:madscience:componentTransistor:0:1",
                        "8:madscience:componentTransistor:0:1"));
                
                circuitSubitems.add(new MadMetaItemData(7, "Spidereye", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, null, itemModelInfo, iconSpidereye.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData circuits = new MadItemFactoryProductData("circuits", true, 0, 64, 0, 0, 0, false, null, circuitSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(circuits);
        }
        
        // -----------------
        // MEMORY DATA REELS
        // -----------------
        {
            List<MadMetaItemData> memorySubitems = new ArrayList<MadMetaItemData>();
            
            {
                // PRIEST
                List<MadItemRenderPass> iconPriest = new ArrayList<MadItemRenderPass>();
                iconPriest.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconPriest.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconPriest.add(new MadItemRenderPass(2, "genomeDataReel2", Color.RED.getRGB()));
                memorySubitems.add(new MadMetaItemData(32, "Priest", false, null, null, null, itemModelInfo, iconPriest.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // FARMER
                List<MadItemRenderPass> iconFarmer = new ArrayList<MadItemRenderPass>();
                iconFarmer.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconFarmer.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconFarmer.add(new MadItemRenderPass(2, "genomeDataReel2", Color.ORANGE.getRGB()));
                memorySubitems.add(new MadMetaItemData(64, "Farmer", false, null, null, null, itemModelInfo, iconFarmer.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BUTCHER
                List<MadItemRenderPass> iconButcher = new ArrayList<MadItemRenderPass>();
                iconButcher.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconButcher.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconButcher.add(new MadItemRenderPass(2, "genomeDataReel2", Color.GREEN.getRGB()));
                memorySubitems.add(new MadMetaItemData(128, "Butcher", false, null, null, null, itemModelInfo, iconButcher.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BLACKSMITH
                List<MadItemRenderPass> iconBlacksmith = new ArrayList<MadItemRenderPass>();
                iconBlacksmith.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconBlacksmith.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconBlacksmith.add(new MadItemRenderPass(2, "genomeDataReel2", Color.BLUE.getRGB()));
                memorySubitems.add(new MadMetaItemData(256, "Blacksmith", false, null, null, null, itemModelInfo, iconBlacksmith.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // LIBRARIAN
                List<MadItemRenderPass> iconLibrarian = new ArrayList<MadItemRenderPass>();
                iconLibrarian.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconLibrarian.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconLibrarian.add(new MadItemRenderPass(2, "genomeDataReel2", Color.MAGENTA.getRGB()));
                memorySubitems.add(new MadMetaItemData(512, "Librarian", false, null, null, null, itemModelInfo, iconLibrarian.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData memories = new MadItemFactoryProductData("memory", true, 0, 64, 0, 0, 0, false, null, memorySubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(memories);
        }
        
        // -------------
        // EMPTY NEEDLES
        // -------------
        {
            List<MadMetaItemData> needleSubitems = new ArrayList<MadMetaItemData>();
            
            // NEEDLE SOUNDS.
            List<MadSound> needleSounds = new ArrayList<MadSound>();
            needleSounds.add(new MadSound("Stab.ogg", 2, 1, MadSoundTriggerEnum.LEFTCLICK, MadSoundPlaybackTypeEnum.PLAY));
            needleSounds.add(new MadSound("Stabself.ogg", 2, 1, MadSoundTriggerEnum.RIGHTCLICK, MadSoundPlaybackTypeEnum.PLAY));
            
            {
                // EMPTY NEEDLE
                List<MadItemRenderPass> iconEmptyNeedle = new ArrayList<MadItemRenderPass>();
                iconEmptyNeedle.add(new MadItemRenderPass(0, "needleEmpty", 16777215));
                
                // CRAFTING RECIPE
                // Note: Layout of crafting grid is as follows.
                // 012
                // 345
                // 678
                List<MadCraftingRecipe> subItemCraftingRecipes = new ArrayList<MadCraftingRecipe>();
                subItemCraftingRecipes.add(new MadCraftingRecipe(
                        MadCraftingRecipeTypeEnum.SHAPED,
                        1,
                        "0:minecraft:glass:0:1",
                        "1:minecraft:ingotIron:0:1",
                        "2:minecraft:glass:0:1",
                        "3:minecraft:glass:0:1",
                        null,
                        "5:minecraft:glass:0:1",
                        null,
                        "7:minecraft:stick:0:1",
                        null));
                
                needleSubitems.add(new MadMetaItemData(0, "Needle", false, subItemCraftingRecipes.toArray(new MadCraftingRecipe[]{}), null, needleSounds.toArray(new MadSound[]{}), itemModelInfo, iconEmptyNeedle.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // DIRTY NEEDLE
                List<MadItemRenderPass> iconDirtyNeedle = new ArrayList<MadItemRenderPass>();
                iconDirtyNeedle.add(new MadItemRenderPass(0, "needleDirty", 16777215));
                needleSubitems.add(new MadMetaItemData(1, "Dirty", false, null, null, null, itemModelInfo, iconDirtyNeedle.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData emptyNeedle = new MadItemFactoryProductData("emptyNeedle", true, 0, 64, 2, 0, 0, false, "madscience.item.ItemNeedleEmptyLogic", needleSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(emptyNeedle);
        }
    }
}
