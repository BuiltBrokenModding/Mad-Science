package madscience;

import madscience.fluids.dna.LiquidDNA;
import madscience.fluids.dna.LiquidDNABlock;
import madscience.fluids.dna.LiquidDNABucket;
import madscience.fluids.dna.LiquidDNABucketEvent;
import madscience.fluids.dna.LiquidDNARender;
import madscience.fluids.dnaMutant.LiquidDNAMutant;
import madscience.fluids.dnaMutant.LiquidDNAMutantBlock;
import madscience.fluids.dnaMutant.LiquidDNAMutantBucket;
import madscience.fluids.dnaMutant.LiquidDNAMutantBucketEvent;
import madscience.fluids.dnaMutant.LiquidDNAMutantRender;
import madscience.items.ItemBlockTooltip;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class MadFluids
{
    // ------------------
    // FLUIDS AND BUCKETS
    // ------------------

    // LIQUID DNA
    public static int liquidDNARenderID;

    // Liquid DNA
    public static LiquidDNA LIQUIDDNA;
    public static LiquidDNABlock LIQUIDDNA_BLOCK;
    public static final String LIQUIDDNA_INTERNALNAME = "maddna";

    // Liquid DNA Bucket
    public static LiquidDNABucket LIQUIDDNA_BUCKET_ITEM;
    public static final String LIQUIDDNA_BUCKET_INTERNALNAME = "madDNABucket";

    // LIQUID MUTANT DNA
    public static int liquidDNAMutantRenderID;

    // Liquid DNA Mutant
    public static LiquidDNAMutant LIQUIDDNA_MUTANT;
    public static LiquidDNAMutantBlock LIQUIDDNA_MUTANT_BLOCK;
    public static final String LIQUIDDNA_MUTANT_INTERNALNAME = "maddnamutant";

    // Liquid DNA Mutant Bucket
    public static LiquidDNAMutantBucket LIQUIDDNA_MUTANT_BUCKET_ITEM;
    public static final String LIQUIDDNA_MUTANT_BUCKET_INTERNALNAME = "madDNAMutantBucket";

    // -------------
    // CUSTOM FLUIDS
    // -------------

    @EventHandler
    public static void createLiquidDNA(int blockFluidID, int bucketItemID)
    {
        // Still's ID must be 1 above Flowing.
        LIQUIDDNA = (LiquidDNA) new LiquidDNA().setUnlocalizedName(LIQUIDDNA_INTERNALNAME);

        // Register this fluid in the game registry.
        FluidRegistry.registerFluid(LIQUIDDNA);

        // Create the block of flowing liquid only after registering it with the system.
        LIQUIDDNA_BLOCK = (LiquidDNABlock) new LiquidDNABlock(blockFluidID).setUnlocalizedName(LIQUIDDNA_INTERNALNAME);

        // Container for our fluid.
        LIQUIDDNA_BUCKET_ITEM = (LiquidDNABucket) new LiquidDNABucket(bucketItemID, 10).setUnlocalizedName(LIQUIDDNA_BUCKET_INTERNALNAME);

        // Register the fluid with minecraft.
        GameRegistry.registerBlock(LIQUIDDNA_BLOCK, ItemBlockTooltip.class, LIQUIDDNA.getUnlocalizedName());

        // Register fluid container item.
        GameRegistry.registerItem(LIQUIDDNA_BUCKET_ITEM, LIQUIDDNA_BUCKET_ITEM.getUnlocalizedName());

        // Register our class as a valid container for our fluid.
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack(LIQUIDDNA.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(LIQUIDDNA_BUCKET_ITEM), new ItemStack(Item.bucketEmpty)));

        // Hook event in forge for filling a bucket with liquid.
        MinecraftForge.EVENT_BUS.register(new LiquidDNABucketEvent());

        // Register custom rendering for GUI's.
        MadScience.proxy.registerRenderingHandler(blockFluidID);

        // Allows us to override icon displays and how fluid is rendered in
        // pipes and tanks.
        MadFluids.liquidDNARenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new LiquidDNARender());
    }

    @EventHandler
    public static void createLiquidDNAMutant(int blockFluidID, int bucketItemID)
    {
        LIQUIDDNA_MUTANT = (LiquidDNAMutant) new LiquidDNAMutant(LIQUIDDNA_MUTANT_INTERNALNAME).setUnlocalizedName(LIQUIDDNA_MUTANT_INTERNALNAME);
        FluidRegistry.registerFluid(LIQUIDDNA_MUTANT);
        LIQUIDDNA_MUTANT_BLOCK = (LiquidDNAMutantBlock) new LiquidDNAMutantBlock(blockFluidID).setUnlocalizedName(LIQUIDDNA_MUTANT_INTERNALNAME);
        LIQUIDDNA_MUTANT_BUCKET_ITEM = (LiquidDNAMutantBucket) new LiquidDNAMutantBucket(bucketItemID, 10).setUnlocalizedName(LIQUIDDNA_MUTANT_BUCKET_INTERNALNAME);
        GameRegistry.registerBlock(LIQUIDDNA_MUTANT_BLOCK, ItemBlockTooltip.class, LIQUIDDNA_MUTANT.getUnlocalizedName());
        GameRegistry.registerItem(LIQUIDDNA_MUTANT_BUCKET_ITEM, LIQUIDDNA_MUTANT_BUCKET_ITEM.getUnlocalizedName());
        // LanguageRegistry.addName(LIQUIDDNA_MUTANT_BLOCK, LIQUIDDNA_MUTANT_DISPLAYNAME);
        // LanguageRegistry.addName(LIQUIDDNA_MUTANT_BUCKET_ITEM, LIQUIDDNA_MUTANT_BUCKET_DISPLAYNAME);
        FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack(LIQUIDDNA_MUTANT.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(LIQUIDDNA_MUTANT_BUCKET_ITEM), new ItemStack(Item.bucketEmpty)));
        MinecraftForge.EVENT_BUS.register(new LiquidDNAMutantBucketEvent());
        MadScience.proxy.registerRenderingHandler(blockFluidID);
        MadFluids.liquidDNAMutantRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new LiquidDNAMutantRender());
    }
}
