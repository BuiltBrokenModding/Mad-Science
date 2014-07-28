package madscience.client;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import madscience.MadComponents;
import madscience.MadConfig;
import madscience.MadEntities;
import madscience.MadForgeMod;
import madscience.MadFurnaces;
import madscience.MadSounds;
import madscience.MadWeapons;
import madscience.factory.MadRenderingFactory;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.mod.MadMod;
import madscience.factory.mod.MadModData;
import madscience.factory.model.MadTechneModelLoader;
import madscience.factory.tileentity.MadTileEntityFactoryProduct;
import madscience.factory.tileentity.prefab.MadTileEntityPrefab;
import madscience.factory.tileentity.templates.MadTileEntityRendererTemplate;
import madscience.fluids.dna.LiquidDNARender;
import madscience.fluids.dnamutant.LiquidDNAMutantRender;
import madscience.items.components.pulserifle.ComponentPulseRifleBarrelItemRender;
import madscience.items.components.pulserifle.ComponentPulseRifleBoltItemRender;
import madscience.items.components.pulserifle.ComponentPulseRifleBulletCasingItemRender;
import madscience.items.components.pulserifle.ComponentPulseRifleGrenadeCasingItemRender;
import madscience.items.components.pulserifle.ComponentPulseRifleReceiverItemRender;
import madscience.items.components.pulserifle.ComponentPulseRifleTriggerItemRender;
import madscience.items.warningsign.WarningSignEntity;
import madscience.items.warningsign.WarningSignEntityRender;
import madscience.items.warningsign.WarningSignItemRender;
import madscience.items.weapons.pulserifle.PulseRifleItemRender;
import madscience.items.weapons.pulserifle.PulseRifleItemRenderPlayer;
import madscience.items.weapons.pulserifle.PulseRifleItemTickHandler;
import madscience.items.weapons.pulseriflebullet.PulseRifleBulletEntity;
import madscience.items.weapons.pulseriflebullet.PulseRifleBulletEntityRender;
import madscience.items.weapons.pulseriflebullet.PulseRifleBulletItemRender;
import madscience.items.weapons.pulseriflegrenade.PulseRifleGrenadeEntity;
import madscience.items.weapons.pulseriflegrenade.PulseRifleGrenadeEntityRender;
import madscience.items.weapons.pulseriflegrenade.PulseRifleGrenadeItemRender;
import madscience.items.weapons.pulseriflemagazine.PulseRifleMagazineItemRender;
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
import madscience.tile.cncmachine.CnCMachineEntity;
import madscience.tile.cncmachine.CnCMachineRender;
import madscience.tile.cryotube.CryotubeEntity;
import madscience.tile.cryotube.CryotubeRender;
import madscience.tile.magloader.MagLoaderEntity;
import madscience.tile.magloader.MagLoaderRender;
import madscience.tile.soniclocator.SoniclocatorEntity;
import madscience.tile.soniclocator.SoniclocatorRender;
import madscience.util.MadUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy // NO_UCD (unused code)
{
    public float fovModifierHand = 0F;

    static
    {
        AdvancedModelLoader.registerModelHandler(new MadTechneModelLoader());
    }

    @Override
    public int getArmorIndex(String armor)
    {
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
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
    public void spawnParticle(String fxName, double posX, double posY, double posZ, double velX, double velY, double velZ)
    {
        World clientWorld = MadForgeMod.proxy.getClientWorld();
        if (clientWorld == null)
        {
            MadMod.log().info("Mad Particle: Could not spawn particle because client world was null!");
            return;
        }

        if (fxName.equals("splash"))
        {
            EntityFX someParticle = new EntitySplashFX(clientWorld, posX, posY, posZ, velX, velY, velZ);
            Minecraft.getMinecraft().effectRenderer.addEffect(someParticle);
        }
        else
        {
            // Normal minecraft particle system ignores velocity completely.
            clientWorld.spawnParticle(fxName, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public String getItemDisplayName(ItemStack stack)
    {
        if (Item.itemsList[stack.itemID] == null)
            return "";

        return Item.itemsList[stack.itemID].getItemDisplayName(stack);
    }

    @Override
    public void resetSavedFOV()
    {
        this.fovModifierHand = 0F;
    }

    @Override
    public void onBowUse(ItemStack stack, EntityPlayer player, int pulseRifleFireTime)
    {
        float f = 1.0F;

        if (player.capabilities.isFlying)
        {
            f *= 1.1F;
        }

        float speedOnGround = 0.1F;
        // int i = player.getItemInUseDuration();
        int i = pulseRifleFireTime;
        float f1 = i / 420.0F;

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }
        else
        {
            f1 *= f1;
        }

        f *= 1.0F - f1 * 0.25F;

        fovModifierHand = fovModifierHand > 0.001F ? fovModifierHand : (Float) ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, "fovModifierHand", "field_78507_R");
        fovModifierHand += (f - fovModifierHand) * 0.5F;

        if (fovModifierHand > 1.5F)
        {
            fovModifierHand = 1.5F;
        }

        if (fovModifierHand < 0.1F)
        {
            fovModifierHand = 0.1F;
        }

        ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer, fovModifierHand, "fovModifierHand", "field_78507_R");
    }
    
    @Override
    public void dumpUnlocalizedNames()
    {        
        // List that holds final result of all entries without duplicates.
        Collection<String> unlocalizedNames = new TreeSet<String>(Collator.getInstance());
        
        // Loop through all the items in the game looking for the ones we want.
        for(Item potentialMCItem : Item.itemsList) 
        {
            if(potentialMCItem == null)
            {
                continue;
            }
            
            String proceseedName = MadUtils.cleanTag(potentialMCItem.getUnlocalizedName());
            unlocalizedNames.add(proceseedName);
            //MadScience.logger.info(proceseedName);
        }
        
        // Loop through all the blocks in the game looking for the ones we want.
        for(Block potentialMCBlock : Block.blocksList) 
        {
            if(potentialMCBlock == null)
            {
                continue;
            }
            
            String proceseedName = MadUtils.cleanTag(potentialMCBlock.getUnlocalizedName());
            unlocalizedNames.add(proceseedName);            
            //MadScience.logger.info(proceseedName);
        }
        
        // Create a JSON builder that makes nice human-readable entries and only uses the fields we specified. 
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        // Convert the data portion of our tile entity factory product to JSON string.
        String json = gson.toJson(unlocalizedNames);
        
        try
        {
            // Save this list of names to the disk.
            File dataDir = FMLClientHandler.instance().getClient().mcDataDir;
            FileUtils.writeStringToFile(new File(dataDir, "dump/" + MadUtils.getValidFileName("unlocalizedNames") + ".json"), json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /** Serializes all registered machines to disk. Meant for developer use only. Use with caution! */
    @Override
    public void dumpAllMachineJSON()
    {        
        // Create a JSON builder that makes nice human-readable entries and only uses the fields we specified. 
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        // Convert the data loaded for this mod into JSON string.
        MadModData data = MadMod.getMadModData();
        String json = gson.toJson(data, MadModData.class);
        try
        {
            // Save this information to the disk!
            File dataDir = FMLClientHandler.instance().getClient().mcDataDir;
            FileUtils.writeStringToFile(new File(dataDir, "dump/" + MadUtils.getValidFileName("mod") + ".json"), json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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

        // Grab an iterable array of all registered machines.
        Iterable<MadTileEntityFactoryProduct> registeredMachines = MadTileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine.getBlockID() == blockID)
            {
                // Populates rendering factory with master reference to what a given machine should have associated with it model and texture wise.
                MadRenderingFactory.instance().registerModelsToProduct(registeredMachine.getMachineName(), registeredMachine.getModelArchive());

                // Minecraft/Forge related registry calls, these are subject to change between Forge versions.
                RenderingRegistry.registerBlockHandler(blockID, new MadTileEntityRendererTemplate());
                ClientRegistry.bindTileEntitySpecialRenderer(MadTileEntityPrefab.class, new MadTileEntityRendererTemplate());
                MinecraftForgeClient.registerItemRenderer(blockID, new MadTileEntityRendererTemplate());
            }
        }

        // Cryogenic Tube
        if (blockID == MadConfig.CRYOTUBE)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.CRYOTUBE_TILEENTITY.blockID, new CryotubeRender());
            ClientRegistry.bindTileEntitySpecialRenderer(CryotubeEntity.class, new CryotubeRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new CryotubeRender());
        }

        // Soniclocator Device
        if (blockID == MadConfig.SONICLOCATOR)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.SONICLOCATOR_TILEENTITY.blockID, new SoniclocatorRender());
            ClientRegistry.bindTileEntitySpecialRenderer(SoniclocatorEntity.class, new SoniclocatorRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new SoniclocatorRender());
        }

        // Magazine Loader
        if (blockID == MadConfig.MAGLOADER)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.MAGLOADER_TILEENTITY.blockID, new MagLoaderRender());
            ClientRegistry.bindTileEntitySpecialRenderer(MagLoaderEntity.class, new MagLoaderRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new MagLoaderRender());
        }

        // CnC Machine
        if (blockID == MadConfig.CNCMACHINE)
        {
            RenderingRegistry.registerBlockHandler(MadFurnaces.CNCMACHINE_TILEENTITY.blockID, new CnCMachineRender());
            ClientRegistry.bindTileEntitySpecialRenderer(CnCMachineEntity.class, new CnCMachineRender());
            MinecraftForgeClient.registerItemRenderer(blockID, new CnCMachineRender());
        }

        // -------
        // WEAPONS
        // -------

        // Pulse Rifle
        if (blockID == MadConfig.WEAPON_PULSERIFLE)
        {
            MinecraftForgeClient.registerItemRenderer(MadWeapons.WEAPONITEM_PULSERIFLE.itemID, new PulseRifleItemRender());
            TickRegistry.registerTickHandler(new PulseRifleItemTickHandler(), Side.CLIENT);
            MinecraftForge.EVENT_BUS.register(new PulseRifleItemRenderPlayer());
        }

        // Pulse Rifle Bullet
        if (blockID == MadConfig.WEAPON_PULSERIFLE_BULLETITEM)
        {
            MinecraftForgeClient.registerItemRenderer(MadWeapons.WEAPONITEM_BULLETITEM.itemID, new PulseRifleBulletItemRender());
            RenderingRegistry.registerEntityRenderingHandler(PulseRifleBulletEntity.class, new PulseRifleBulletEntityRender());
        }

        // Pulse Rifle Grenade
        if (blockID == MadConfig.WEAPON_PULSERIFLE_GRENADEITEM)
        {
            MinecraftForgeClient.registerItemRenderer(MadWeapons.WEAPONITEM_GRENADEITEM.itemID, new PulseRifleGrenadeItemRender());
            RenderingRegistry.registerEntityRenderingHandler(PulseRifleGrenadeEntity.class, new PulseRifleGrenadeEntityRender());
        }

        // Pulse Rifle Magazine
        if (blockID == MadConfig.WEAPON_PULSERIFLE_MAGAZINEITEM)
        {
            MinecraftForgeClient.registerItemRenderer(MadWeapons.WEAPONITEM_MAGAZINEITEM.itemID, new PulseRifleMagazineItemRender());
        }

        // ----------
        // COMPONENTS
        // ----------

        // Component Pulse Rifle Barrel
        if (blockID == MadConfig.COMPONENT_PULSERIFLEBARREL)
        {
            MinecraftForgeClient.registerItemRenderer(MadComponents.COMPONENT_PULSERIFLEBARREL.itemID, new ComponentPulseRifleBarrelItemRender());
        }

        // Component Pulse Rifle Bolt
        if (blockID == MadConfig.COMPONENT_PULSERIFLEBOLT)
        {
            MinecraftForgeClient.registerItemRenderer(MadComponents.COMPONENT_PULSERIFLEBOLT.itemID, new ComponentPulseRifleBoltItemRender());
        }

        // Component Pulse Rifle Receiver
        if (blockID == MadConfig.COMPONENT_PULSERIFLERECEIVER)
        {
            MinecraftForgeClient.registerItemRenderer(MadComponents.COMPONENT_PULSERIFLERECIEVER.itemID, new ComponentPulseRifleReceiverItemRender());
        }

        // Component Pulse Rifle Trigger
        if (blockID == MadConfig.COMPONENT_PULSERIFLETRIGGER)
        {
            MinecraftForgeClient.registerItemRenderer(MadComponents.COMPONENT_PULSERIFLETRIGGER.itemID, new ComponentPulseRifleTriggerItemRender());
        }

        // Component Pulse Rifle Bullet Casing
        if (blockID == MadConfig.COMPONENT_PULSERIFLEBULLETCASING)
        {
            MinecraftForgeClient.registerItemRenderer(MadComponents.COMPONENT_PULSERIFLEBULLETCASING.itemID, new ComponentPulseRifleBulletCasingItemRender());
        }

        // Component Pulse Rifle Grenade Casing
        if (blockID == MadConfig.COMPONENT_PULSERIFLEGRENADECASING)
        {
            MinecraftForgeClient.registerItemRenderer(MadComponents.COMPONENT_PULSERIFLEGRENADECASING.itemID, new ComponentPulseRifleGrenadeCasingItemRender());
        }

        // -----
        // ITEMS
        // -----

        // Warning Sign
        if (blockID == MadConfig.WARNING_SIGN)
        {
            MinecraftForgeClient.registerItemRenderer(MadEntities.WARNING_SIGN.itemID, new WarningSignItemRender());
            RenderingRegistry.registerEntityRenderingHandler(WarningSignEntity.class, new WarningSignEntityRender());
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
        // Register the sound event handling class.
        MinecraftForge.EVENT_BUS.register(new MadSounds());
    }
}
