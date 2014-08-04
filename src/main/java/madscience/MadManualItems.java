package madscience;

import java.util.ArrayList;
import java.util.List;

import madscience.factory.item.MadItemFactoryProductData;
import madscience.factory.item.MadItemRenderPass;
import madscience.factory.item.MadMetaItemData;
import madscience.factory.model.MadModel;
import madscience.factory.model.MadModelPosition;
import madscience.factory.model.MadModelRotation;
import madscience.factory.model.MadModelScale;
import madscience.factory.rendering.MadModelItemRender;

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
                dnaSampleSubitems.add(new MadMetaItemData(0, "CaveSpider", null, null, itemModelInfo, iconCaveSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CHICKEN.
                List<MadItemRenderPass> iconChicken = new ArrayList<MadItemRenderPass>();
                iconChicken.add(new MadItemRenderPass(0, "dnaSample_overlay", 10592673));
                iconChicken.add(new MadItemRenderPass(1, "dnaSample", 16711680));
                dnaSampleSubitems.add(new MadMetaItemData(1, "Chicken", null, null, itemModelInfo, iconChicken.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // COW.
                List<MadItemRenderPass> iconCow = new ArrayList<MadItemRenderPass>();
                iconCow.add(new MadItemRenderPass(0, "dnaSample_overlay", 4470310));
                iconCow.add(new MadItemRenderPass(1, "dnaSample", 10592673));
                dnaSampleSubitems.add(new MadMetaItemData(2, "Cow", null, null, itemModelInfo, iconCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CREEPER.
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "dnaSample_overlay", 894731));
                iconCreeper.add(new MadItemRenderPass(1, "dnaSample", 0));
                dnaSampleSubitems.add(new MadMetaItemData(3, "Creeper", null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERMAN.
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "dnaSample_overlay", 1447446));
                iconCreeper.add(new MadItemRenderPass(1, "dnaSample", 0));
                dnaSampleSubitems.add(new MadMetaItemData(4, "Enderman", null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // GHAST.
                List<MadItemRenderPass> iconGhast = new ArrayList<MadItemRenderPass>();
                iconGhast.add(new MadItemRenderPass(0, "dnaSample_overlay", 16382457));
                iconGhast.add(new MadItemRenderPass(1, "dnaSample", 12369084));
                dnaSampleSubitems.add(new MadMetaItemData(5, "Ghast", null, null, itemModelInfo, iconGhast.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // HORSE.
                List<MadItemRenderPass> iconHorse = new ArrayList<MadItemRenderPass>();
                iconHorse.add(new MadItemRenderPass(0, "dnaSample_overlay", 12623485));
                iconHorse.add(new MadItemRenderPass(1, "dnaSample", 15656192));
                dnaSampleSubitems.add(new MadMetaItemData(6, "Horse", null, null, itemModelInfo, iconHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MUSHROOM COW.
                List<MadItemRenderPass> iconMushroomCow = new ArrayList<MadItemRenderPass>();
                iconMushroomCow.add(new MadItemRenderPass(0, "dnaSample_overlay", 10489616));
                iconMushroomCow.add(new MadItemRenderPass(1, "dnaSample", 12040119));
                dnaSampleSubitems.add(new MadMetaItemData(7, "MushroomCow", null, null, itemModelInfo, iconMushroomCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // OCELOT.
                List<MadItemRenderPass> iconOcelot = new ArrayList<MadItemRenderPass>();
                iconOcelot.add(new MadItemRenderPass(0, "dnaSample_overlay", 15720061));
                iconOcelot.add(new MadItemRenderPass(1, "dnaSample", 5653556));
                dnaSampleSubitems.add(new MadMetaItemData(8, "Ocelot", null, null, itemModelInfo, iconOcelot.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // PIG.
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "dnaSample_overlay", 15771042));
                iconPig.add(new MadItemRenderPass(1, "dnaSample", 14377823));
                dnaSampleSubitems.add(new MadMetaItemData(9, "Pig", null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SHEEP.
                List<MadItemRenderPass> iconSheep = new ArrayList<MadItemRenderPass>();
                iconSheep.add(new MadItemRenderPass(0, "dnaSample_overlay", 15198183));
                iconSheep.add(new MadItemRenderPass(1, "dnaSample", 16758197));
                dnaSampleSubitems.add(new MadMetaItemData(10, "Sheep", null, null, itemModelInfo, iconSheep.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SKELETON.
                List<MadItemRenderPass> iconSkeleton = new ArrayList<MadItemRenderPass>();
                iconSkeleton.add(new MadItemRenderPass(0, "dnaSample_overlay", 12698049));
                iconSkeleton.add(new MadItemRenderPass(1, "dnaSample", 4802889));
                dnaSampleSubitems.add(new MadMetaItemData(11, "Skeleton", null, null, itemModelInfo, iconSkeleton.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SPIDER.
                List<MadItemRenderPass> iconSpider = new ArrayList<MadItemRenderPass>();
                iconSpider.add(new MadItemRenderPass(0, "dnaSample_overlay", 3419431));
                iconSpider.add(new MadItemRenderPass(1, "dnaSample", 11013646));
                dnaSampleSubitems.add(new MadMetaItemData(12, "Spider", null, null, itemModelInfo, iconSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SQUID.
                List<MadItemRenderPass> iconSquid = new ArrayList<MadItemRenderPass>();
                iconSquid.add(new MadItemRenderPass(0, "dnaSample_overlay", 2243405));
                iconSquid.add(new MadItemRenderPass(1, "dnaSample", 7375001));
                dnaSampleSubitems.add(new MadMetaItemData(13, "Squid", null, null, itemModelInfo, iconSquid.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // VILLAGER.
                List<MadItemRenderPass> iconVillager = new ArrayList<MadItemRenderPass>();
                iconVillager.add(new MadItemRenderPass(0, "dnaSample_overlay", 5651507));
                iconVillager.add(new MadItemRenderPass(1, "dnaSample", 12422002));
                dnaSampleSubitems.add(new MadMetaItemData(14, "Villager", null, null, itemModelInfo, iconVillager.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WITCH.
                List<MadItemRenderPass> iconWitch = new ArrayList<MadItemRenderPass>();
                iconWitch.add(new MadItemRenderPass(0, "dnaSample_overlay", 3407872));
                iconWitch.add(new MadItemRenderPass(1, "dnaSample", 5349438));
                dnaSampleSubitems.add(new MadMetaItemData(15, "Witch", null, null, itemModelInfo, iconWitch.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WOLF.
                List<MadItemRenderPass> iconWolf = new ArrayList<MadItemRenderPass>();
                iconWolf.add(new MadItemRenderPass(0, "dnaSample_overlay", 14144467));
                iconWolf.add(new MadItemRenderPass(1, "dnaSample", 13545366));
                dnaSampleSubitems.add(new MadMetaItemData(16, "Wolf", null, null, itemModelInfo, iconWolf.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ZOMBIE.
                List<MadItemRenderPass> iconZombie = new ArrayList<MadItemRenderPass>();
                iconZombie.add(new MadItemRenderPass(0, "dnaSample_overlay", 44975));
                iconZombie.add(new MadItemRenderPass(1, "dnaSample", 7969893));
                dnaSampleSubitems.add(new MadMetaItemData(17, "Zombie", null, null, itemModelInfo, iconZombie.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BAT.
                List<MadItemRenderPass> iconBat = new ArrayList<MadItemRenderPass>();
                iconBat.add(new MadItemRenderPass(0, "dnaSample_overlay", 4996656));
                iconBat.add(new MadItemRenderPass(1, "dnaSample", 986895));
                dnaSampleSubitems.add(new MadMetaItemData(18, "Bat", null, null, itemModelInfo, iconBat.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SLIME.
                List<MadItemRenderPass> iconSlime = new ArrayList<MadItemRenderPass>();
                iconSlime.add(new MadItemRenderPass(0, "dnaSample_overlay", 5349438));
                iconSlime.add(new MadItemRenderPass(1, "dnaSample", 8306542));
                dnaSampleSubitems.add(new MadMetaItemData(19, "Slime", null, null, itemModelInfo, iconSlime.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData dnaSamples = new MadItemFactoryProductData("dna", true, 0, 64, 0, 0, 0, false, null, dnaSampleSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(dnaSamples);
        }
        
        // -----------------
        // GENOME DATA REELS
        // -----------------
        {
            List<MadMetaItemData> genomeSubitems = new ArrayList<MadMetaItemData>();
            
            {
                // CAVE SPIDER.
                List<MadItemRenderPass> iconCaveSpider = new ArrayList<MadItemRenderPass>();
                iconCaveSpider.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCaveSpider.add(new MadItemRenderPass(1, "genomeDataReel1", 803406));
                iconCaveSpider.add(new MadItemRenderPass(2, "genomeDataReel2", 11013646));
                genomeSubitems.add(new MadMetaItemData(0, "CaveSpider", null, null, itemModelInfo, iconCaveSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CHICKEN
                List<MadItemRenderPass> iconChicken = new ArrayList<MadItemRenderPass>();
                iconChicken.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconChicken.add(new MadItemRenderPass(1, "genomeDataReel1", 10592673));
                iconChicken.add(new MadItemRenderPass(2, "genomeDataReel2", 16711680));
                genomeSubitems.add(new MadMetaItemData(1, "Chicken", null, null, itemModelInfo, iconChicken.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // COW
                List<MadItemRenderPass> iconCow = new ArrayList<MadItemRenderPass>();
                iconCow.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCow.add(new MadItemRenderPass(1, "genomeDataReel1", 4470310));
                iconCow.add(new MadItemRenderPass(2, "genomeDataReel2", 10592673));
                genomeSubitems.add(new MadMetaItemData(2, "Cow", null, null, itemModelInfo, iconCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // CREEPER
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCreeper.add(new MadItemRenderPass(1, "genomeDataReel1", 894731));
                iconCreeper.add(new MadItemRenderPass(2, "genomeDataReel2", 0));
                genomeSubitems.add(new MadMetaItemData(3, "Cow", null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ENDERMAN
                List<MadItemRenderPass> iconCreeper = new ArrayList<MadItemRenderPass>();
                iconCreeper.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconCreeper.add(new MadItemRenderPass(1, "genomeDataReel1", 1447446));
                iconCreeper.add(new MadItemRenderPass(2, "genomeDataReel2", 0));
                genomeSubitems.add(new MadMetaItemData(4, "Enderman", null, null, itemModelInfo, iconCreeper.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // GHAST
                List<MadItemRenderPass> iconGhast = new ArrayList<MadItemRenderPass>();
                iconGhast.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconGhast.add(new MadItemRenderPass(1, "genomeDataReel1", 16382457));
                iconGhast.add(new MadItemRenderPass(2, "genomeDataReel2", 12369084));
                genomeSubitems.add(new MadMetaItemData(5, "Enderman", null, null, itemModelInfo, iconGhast.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // HORSE
                List<MadItemRenderPass> iconHorse = new ArrayList<MadItemRenderPass>();
                iconHorse.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconHorse.add(new MadItemRenderPass(1, "genomeDataReel1", 12623485));
                iconHorse.add(new MadItemRenderPass(2, "genomeDataReel2", 15656192));
                genomeSubitems.add(new MadMetaItemData(6, "Horse", null, null, itemModelInfo, iconHorse.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // MUSHROOM COW
                List<MadItemRenderPass> iconMushroomCow = new ArrayList<MadItemRenderPass>();
                iconMushroomCow.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconMushroomCow.add(new MadItemRenderPass(1, "genomeDataReel1", 10489616));
                iconMushroomCow.add(new MadItemRenderPass(2, "genomeDataReel2", 12040119));
                genomeSubitems.add(new MadMetaItemData(7, "MushroomCow", null, null, itemModelInfo, iconMushroomCow.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // OCELOT
                List<MadItemRenderPass> iconOcelot = new ArrayList<MadItemRenderPass>();
                iconOcelot.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconOcelot.add(new MadItemRenderPass(1, "genomeDataReel1", 15720061));
                iconOcelot.add(new MadItemRenderPass(2, "genomeDataReel2", 5653556));
                genomeSubitems.add(new MadMetaItemData(8, "Ocelot", null, null, itemModelInfo, iconOcelot.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // PIG
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconPig.add(new MadItemRenderPass(1, "genomeDataReel1", 15771042));
                iconPig.add(new MadItemRenderPass(2, "genomeDataReel2", 14377823));
                genomeSubitems.add(new MadMetaItemData(9, "Pig", null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // PIG ZOMBIE
                List<MadItemRenderPass> iconPig = new ArrayList<MadItemRenderPass>();
                iconPig.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconPig.add(new MadItemRenderPass(1, "genomeDataReel1", 15373203));
                iconPig.add(new MadItemRenderPass(2, "genomeDataReel2", 5009705));
                genomeSubitems.add(new MadMetaItemData(10, "PigZombie", null, null, itemModelInfo, iconPig.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SHEEP
                List<MadItemRenderPass> iconSheep = new ArrayList<MadItemRenderPass>();
                iconSheep.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSheep.add(new MadItemRenderPass(1, "genomeDataReel1", 15198183));
                iconSheep.add(new MadItemRenderPass(2, "genomeDataReel2", 16758197));
                genomeSubitems.add(new MadMetaItemData(11, "Sheep", null, null, itemModelInfo, iconSheep.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SKELETON
                List<MadItemRenderPass> iconSkeleton = new ArrayList<MadItemRenderPass>();
                iconSkeleton.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSkeleton.add(new MadItemRenderPass(1, "genomeDataReel1", 12698049));
                iconSkeleton.add(new MadItemRenderPass(2, "genomeDataReel2", 4802889));
                genomeSubitems.add(new MadMetaItemData(12, "Sheep", null, null, itemModelInfo, iconSkeleton.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SQUID
                List<MadItemRenderPass> iconSquid = new ArrayList<MadItemRenderPass>();
                iconSquid.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSquid.add(new MadItemRenderPass(1, "genomeDataReel1", 2243405));
                iconSquid.add(new MadItemRenderPass(2, "genomeDataReel2", 7375001));
                genomeSubitems.add(new MadMetaItemData(13, "Squid", null, null, itemModelInfo, iconSquid.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SPIDER
                List<MadItemRenderPass> iconSpider = new ArrayList<MadItemRenderPass>();
                iconSpider.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSpider.add(new MadItemRenderPass(1, "genomeDataReel1", 3419431));
                iconSpider.add(new MadItemRenderPass(2, "genomeDataReel2", 11013646));
                genomeSubitems.add(new MadMetaItemData(14, "Spider", null, null, itemModelInfo, iconSpider.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // VILLAGER
                List<MadItemRenderPass> iconVillager = new ArrayList<MadItemRenderPass>();
                iconVillager.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconVillager.add(new MadItemRenderPass(1, "genomeDataReel1", 5651507));
                iconVillager.add(new MadItemRenderPass(2, "genomeDataReel2", 12422002));
                genomeSubitems.add(new MadMetaItemData(15, "Villager", null, null, itemModelInfo, iconVillager.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WITCH
                List<MadItemRenderPass> iconWitch = new ArrayList<MadItemRenderPass>();
                iconWitch.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconWitch.add(new MadItemRenderPass(1, "genomeDataReel1", 3407872));
                iconWitch.add(new MadItemRenderPass(2, "genomeDataReel2", 5349438));
                genomeSubitems.add(new MadMetaItemData(16, "Witch", null, null, itemModelInfo, iconWitch.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // WOLF
                List<MadItemRenderPass> iconWolf = new ArrayList<MadItemRenderPass>();
                iconWolf.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconWolf.add(new MadItemRenderPass(1, "genomeDataReel1", 14144467));
                iconWolf.add(new MadItemRenderPass(2, "genomeDataReel2", 13545366));
                genomeSubitems.add(new MadMetaItemData(17, "Wolf", null, null, itemModelInfo, iconWolf.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // ZOMBIE
                List<MadItemRenderPass> iconZombie = new ArrayList<MadItemRenderPass>();
                iconZombie.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconZombie.add(new MadItemRenderPass(1, "genomeDataReel1", 44975));
                iconZombie.add(new MadItemRenderPass(2, "genomeDataReel2", 7969893));
                genomeSubitems.add(new MadMetaItemData(18, "Zombie", null, null, itemModelInfo, iconZombie.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // BAT
                List<MadItemRenderPass> iconBat = new ArrayList<MadItemRenderPass>();
                iconBat.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconBat.add(new MadItemRenderPass(1, "genomeDataReel1", 4996656));
                iconBat.add(new MadItemRenderPass(2, "genomeDataReel2", 986895));
                genomeSubitems.add(new MadMetaItemData(19, "Bat", null, null, itemModelInfo, iconBat.toArray(new MadItemRenderPass[]{})));
            }
            
            {
                // SLIME
                List<MadItemRenderPass> iconSlime = new ArrayList<MadItemRenderPass>();
                iconSlime.add(new MadItemRenderPass(0, "genomeDataReel_overlay", 16777215));
                iconSlime.add(new MadItemRenderPass(1, "genomeDataReel1", 5349438));
                iconSlime.add(new MadItemRenderPass(2, "genomeDataReel2", 8306542));
                genomeSubitems.add(new MadMetaItemData(20, "Slime", null, null, itemModelInfo, iconSlime.toArray(new MadItemRenderPass[]{})));
            }
            
            MadItemFactoryProductData genomeDataReels = new MadItemFactoryProductData("genome", true, 0, 1, 0, 0, 0, false, null, genomeSubitems.toArray(new MadMetaItemData[]{}));
            manualItems.add(genomeDataReels);
        }
    }
}
