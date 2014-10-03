package madscience.proxy;

import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import madscience.MadForgeMod;
import madscience.MadModLoader;
import madscience.MadSounds;
import madscience.factory.MadFluidFactory;
import madscience.factory.MadRenderingFactory;
import madscience.factory.MadTileEntityFactory;
import madscience.factory.fluid.MadFluidRenderingTemplate;
import madscience.factory.mod.MadModData;
import madscience.factory.model.MadTechneModelLoader;
import madscience.factory.product.MadFluidFactoryProduct;
import madscience.factory.product.MadTileEntityFactoryProduct;
import madscience.factory.rendering.MadRendererTemplate;
import madscience.factory.tile.MadTileEntityPrefab;
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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
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
            MadModLoader.log().info("Mad Particle: Could not spawn particle because client world was null!");
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
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
        
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
        Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .serializeNulls()
        .create();

        // Convert the data loaded for this mod into JSON string.
        MadModData data = MadModLoader.getMadModData();
        String json = gson.toJson(data, MadModData.class);
        try
        {
            // Save this information to the disk!
            File dataDir = FMLClientHandler.instance().getClient().mcDataDir;
            FileUtils.writeStringToFile(new File(dataDir, "dump/mod.json"), json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void registerRenderingHandler(int itemOrBlockID)
    {
        // ------
        // FLUIDS
        // ------
        Iterable<MadFluidFactoryProduct> registeredFluids = MadFluidFactory.instance().getFluidInfoList();
        for (Iterator iterator = registeredFluids.iterator(); iterator.hasNext();)
        {
            MadFluidFactoryProduct registeredFluid = (MadFluidFactoryProduct) iterator.next();
            if (registeredFluid.getFluidID() == itemOrBlockID)
            {
                // Register fluid renderer with Minecraft/Forge. Subject to change between Forge versions.
                MinecraftForge.EVENT_BUS.register(new MadFluidRenderingTemplate(registeredFluid));
                
                // Allows us to override icon displays and how fluid is rendered in pipes and tanks.
                RenderingRegistry.registerBlockHandler(new MadFluidRenderingTemplate(registeredFluid));
            }
        }
        
        // -------------
        // TILE ENTITIES
        // -------------
        Iterable<MadTileEntityFactoryProduct> registeredMachines = MadTileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext();)
        {
            MadTileEntityFactoryProduct registeredMachine = (MadTileEntityFactoryProduct) iterator.next();
            if (registeredMachine.getBlockID() == itemOrBlockID)
            {
                // Populates rendering factory with master reference to what a given machine should have associated with it model and texture wise.
                MadRenderingFactory.instance().registerModelsToProduct(registeredMachine.getMachineName(), registeredMachine.getModelArchive());

                // Minecraft/Forge related registry calls, these are subject to change between Forge versions.
                RenderingRegistry.registerBlockHandler(itemOrBlockID, new MadRendererTemplate());
                ClientRegistry.bindTileEntitySpecialRenderer(MadTileEntityPrefab.class, new MadRendererTemplate());
                MinecraftForgeClient.registerItemRenderer(itemOrBlockID, new MadRendererTemplate());
            }
        }
    }

    @Override
    public void registerSoundHandler()
    {
        // Register the sound event handling class.
        MinecraftForge.EVENT_BUS.register(new MadSounds());
    }
}
