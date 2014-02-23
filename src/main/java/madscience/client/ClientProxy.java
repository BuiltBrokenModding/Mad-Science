package madscience.client;

import madscience.MadConfig;
import madscience.MadFurnaces;
import madscience.MadSounds;
import madscience.fluids.dna.LiquidDNARender;
import madscience.fluids.dnaMutant.LiquidDNAMutantRender;
import madscience.mobs.abomination.AbominationMobEntity;
import madscience.mobs.abomination.AbominationMobModel;
import madscience.mobs.abomination.AbominationMobRender;
import madscience.mobs.creepercow.CreeperCowMobEntity;
import madscience.mobs.creepercow.CreeperCowMobModel;
import madscience.mobs.creepercow.CreeperCowMobRender;
import madscience.mobs.enderslime.EnderslimeMobEntity;
import madscience.mobs.enderslime.EnderslimeMobModel;
import madscience.mobs.enderslime.EnderslimeMobRender;
import madscience.mobs.endersquid.EnderSquidMobEntity;
import madscience.mobs.endersquid.EnderSquidMobModel;
import madscience.mobs.endersquid.EnderSquidMobRender;
import madscience.mobs.shoggoth.ShoggothMobEntity;
import madscience.mobs.shoggoth.ShoggothMobModel;
import madscience.mobs.shoggoth.ShoggothMobRender;
import madscience.mobs.werewolf.WerewolfMobEntity;
import madscience.mobs.werewolf.WerewolfMobModel;
import madscience.mobs.werewolf.WerewolfMobRender;
import madscience.mobs.woolycow.WoolyCowMobEntity;
import madscience.mobs.woolycow.WoolyCowMobModel1;
import madscience.mobs.woolycow.WoolyCowMobModel2;
import madscience.mobs.woolycow.WoolyCowMobRender;
import madscience.server.CommonProxy;
import madscience.tileentities.cryofreezer.CryofreezerEntity;
import madscience.tileentities.cryofreezer.CryofreezerRender;
import madscience.tileentities.cryotube.CryotubeEntity;
import madscience.tileentities.cryotube.CryotubeRender;
import madscience.tileentities.dataduplicator.DataDuplicatorEntity;
import madscience.tileentities.dataduplicator.DataDuplicatorRender;
import madscience.tileentities.dnaextractor.DNAExtractorEntity;
import madscience.tileentities.dnaextractor.DNAExtractorRender;
import madscience.tileentities.incubator.IncubatorEntity;
import madscience.tileentities.incubator.IncubatorRender;
import madscience.tileentities.mainframe.MainframeEntity;
import madscience.tileentities.mainframe.MainframeRender;
import madscience.tileentities.meatcube.MeatcubeEntity;
import madscience.tileentities.meatcube.MeatcubeRender;
import madscience.tileentities.sanitizer.SanitizerEntity;
import madscience.tileentities.sanitizer.SanitizerRender;
import madscience.tileentities.sequencer.SequencerEntity;
import madscience.tileentities.sequencer.SequencerRender;
import madscience.tileentities.soniclocator.SoniclocatorEntity;
import madscience.tileentities.soniclocator.SoniclocatorRender;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderEntity;
import madscience.tileentities.thermosonicbonder.ThermosonicBonderRender;
import madscience.util.MadTechneModelLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    // Modified from Source
    // http://www.minecraftforge.net/wiki/Reference_Mod_File
    
    static
    {
        AdvancedModelLoader.registerModelHandler(new MadTechneModelLoader());
    }

    @Override
    public void addLocalization(String s1, String string)
    {
        LanguageRegistry.instance().addStringLocalization(s1, string);
    }

    @Override
    public void addName(Object obj, String s)
    {
        LanguageRegistry.addName(obj, s);
    }

    /* INSTANCES */
    @Override
    public Object getClient()
    {
        return FMLClientHandler.instance().getClient();
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public String getItemDisplayName(ItemStack stack)
    {
        if (Item.itemsList[stack.itemID] == null)
            return "";

        return Item.itemsList[stack.itemID].getItemDisplayName(stack);
    }

    @Override
    public void registerRenderingHandler(int blockID)
    {
        // ------
        // FLUIDS
        // ------

        // Liquid DNA
        if (blockID == MadConfig.LIQUIDDNA)
        {
            MinecraftForge.EVENT_BUS.register(new LiquidDNARender());
        }

        // Liquid Mutant DNA
        if (blockID == MadConfig.LIQUIDDNA_MUTANT)
        {
            MinecraftForge.EVENT_BUS.register(new LiquidDNAMutantRender());
        }

        // -------------
        // TILE ENTITIES
        // -------------

        // DNA Extractor
        if (blockID == MadConfig.DNA_EXTRACTOR)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.DNAEXTRACTOR_TILEENTITY.blockID, new DNAExtractorRender());
            ClientRegistry.bindTileEntitySpecialRenderer(DNAExtractorEntity.class, new DNAExtractorRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new DNAExtractorRender());
        }

        // Syringe Sanitizer
        if (blockID == MadConfig.SANTITIZER)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.SANTITIZER_TILEENTITY.blockID, new SanitizerRender());
            ClientRegistry.bindTileEntitySpecialRenderer(SanitizerEntity.class, new SanitizerRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new SanitizerRender());
        }

        // Computer Mainframe
        if (blockID == MadConfig.MAINFRAME)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.MAINFRAME_TILEENTITY.blockID, new MainframeRender());
            ClientRegistry.bindTileEntitySpecialRenderer(MainframeEntity.class, new MainframeRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new MainframeRender());
        }

        // Genetic Sequencer
        if (blockID == MadConfig.GENE_SEQUENCER)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.SEQUENCER_TILEENTITY.blockID, new SequencerRender());
            ClientRegistry.bindTileEntitySpecialRenderer(SequencerEntity.class, new SequencerRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new SequencerRender());
        }

        // Cryogenic Freezer
        if (blockID == MadConfig.CRYOFREEZER)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.CRYOFREEZER_TILEENTITY.blockID, new CryofreezerRender());
            ClientRegistry.bindTileEntitySpecialRenderer(CryofreezerEntity.class, new CryofreezerRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new CryofreezerRender());
        }

        // Genome Incubator
        if (blockID == MadConfig.INCUBATOR)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.INCUBATOR_TILEENTITY.blockID, new IncubatorRender());
            ClientRegistry.bindTileEntitySpecialRenderer(IncubatorEntity.class, new IncubatorRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new IncubatorRender());
        }

        // Cryogenic Tube
        if (blockID == MadConfig.CRYOTUBE)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.CRYOTUBE_TILEENTITY.blockID, new CryotubeRender());
            ClientRegistry.bindTileEntitySpecialRenderer(CryotubeEntity.class, new CryotubeRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new CryotubeRender());
        }

        // Thermosonic Bonder
        if (blockID == MadConfig.THERMOSONIC)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.THERMOSONIC_TILEENTITY.blockID, new ThermosonicBonderRender());
            ClientRegistry.bindTileEntitySpecialRenderer(ThermosonicBonderEntity.class, new ThermosonicBonderRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new ThermosonicBonderRender());
        }

        // Data Reel Duplicator
        if (blockID == MadConfig.DATADUPLICATOR)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.DATADUPLICATOR_TILEENTITY.blockID, new DataDuplicatorRender());
            ClientRegistry.bindTileEntitySpecialRenderer(DataDuplicatorEntity.class, new DataDuplicatorRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new DataDuplicatorRender());
        }

        // Soniclocator Device
        if (blockID == MadConfig.SONICLOCATOR)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.SONICLOCATOR_TILEENTITY.blockID, new SoniclocatorRender());
            ClientRegistry.bindTileEntitySpecialRenderer(SoniclocatorEntity.class, new SoniclocatorRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new SoniclocatorRender());
        }

        // ----
        // MOBS
        // ----

        // Werewolf
        if (blockID == MadConfig.GMO_WEREWOLF_METAID)
        {
            // Ties together three separate classes to create new mob.
            RenderingRegistry.registerEntityRenderingHandler(WerewolfMobEntity.class, new WerewolfMobRender(new WerewolfMobModel(), 0.5F));
        }

        // Disgusting Meat Cube
        if (blockID == MadConfig.MEATCUBE)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.MEATCUBE_TILEENTITY.blockID, new MeatcubeRender());
            ClientRegistry.bindTileEntitySpecialRenderer(MeatcubeEntity.class, new MeatcubeRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new MeatcubeRender());
        }

        // Creeper Cow [Creeper + Cow]
        if (blockID == MadConfig.GMO_CREEPERCOW_METAID)
        {
            RenderingRegistry.registerEntityRenderingHandler(CreeperCowMobEntity.class, new CreeperCowMobRender(new CreeperCowMobModel(), 0.5F));
        }
        
        // Enderslime [Enderman + Slime]
        if (blockID == MadConfig.GMO_ENDERSLIME_METAID)
        {
            RenderingRegistry.registerEntityRenderingHandler(EnderslimeMobEntity.class, new EnderslimeMobRender(new EnderslimeMobModel(16), new EnderslimeMobModel(0), 0.25F));
        }

        // Bart74(bart.74@hotmail.fr)
        // Wooly cow [Cow + Sheep]
        if (blockID == MadConfig.GMO_WOOLYCOW_METAID)
        {
            RenderingRegistry.registerEntityRenderingHandler(WoolyCowMobEntity.class, new WoolyCowMobRender(new WoolyCowMobModel1(), new WoolyCowMobModel2(), 0.5F));
        }

        // Deuce_Loosely(captainlunautic@yahoo.com)
        // Shoggoth [Slime + Squid]
        if (blockID == MadConfig.GMO_SHOGGOTH_METAID)
        {
            RenderingRegistry.registerEntityRenderingHandler(ShoggothMobEntity.class, new ShoggothMobRender(new ShoggothMobModel(16), new ShoggothMobModel(0), 0.25F));
        }

        // monodemono(coolplanet3000@gmail.com)
        // The Abomination [Enderman + Spider]
        if (blockID == MadConfig.GMO_ABOMINATION_METAID)
        {
            RenderingRegistry.registerEntityRenderingHandler(AbominationMobEntity.class, new AbominationMobRender(new AbominationMobModel(), 0.5F));
        }

        // TheTechnician(tallahlf@gmail.com)
        // Ender Squid [Enderman + Squid]
        if (blockID == MadConfig.GMO_ENDERSQUID_METAID)
        {
            RenderingRegistry.registerEntityRenderingHandler(EnderSquidMobEntity.class, new EnderSquidMobRender(new EnderSquidMobModel(), 0.5F));
        }
    }

    @Override
    public void registerSoundHandler()
    {
        // Register the sound event handling class
        MinecraftForge.EVENT_BUS.register(new MadSounds());
    }
}
