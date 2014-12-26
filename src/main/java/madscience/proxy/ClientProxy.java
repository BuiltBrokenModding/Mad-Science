package madscience.proxy;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import madscience.ModMetadata;
import madscience.dump.ItemStackRenderer;
import madscience.dump.MinecraftItemJSONObject;
import madscience.dump.RenderTickHandler;
import madscience.factory.FluidFactory;
import madscience.factory.RenderingFactory;
import madscience.factory.TileEntityFactory;
import madscience.fluid.FluidRenderingTemplate;
import madscience.mod.ForgeMod;
import madscience.mod.ModData;
import madscience.mod.ModLoader;
import madscience.mod.SoundLoader;
import madscience.model.TechneModelLoader;
import madscience.product.FluidFactoryProduct;
import madscience.product.TileEntityFactoryProduct;
import madscience.rendering.RendererTemplate;
import madscience.tile.TileEntityPrefab;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.FileUtils;
import org.lwjgl.opengl.GLContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy // NO_UCD (unused code)
{
    public static final int DEFAULT_MAIN_TEXTURE_SIZE = 32;
    public static boolean gl32_enabled = false;

    static
    {
        AdvancedModelLoader.registerModelHandler( new TechneModelLoader() );
    }

    public float fovModifierHand = 0F;
    private ItemStackRenderer itemStackCamera;

    @Override
    public int getArmorIndex(String armor)
    {
        return RenderingRegistry.addNewArmourRendererPrefix( armor );
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
    public void spawnParticle(String fxName,
                              double posX,
                              double posY,
                              double posZ,
                              double velX,
                              double velY,
                              double velZ)
    {
        World clientWorld = ForgeMod.proxy.getClientWorld();
        if (clientWorld == null)
        {
            ModLoader.log().info( "Mad Particle: Could not spawn particle because client world was null!" );
            return;
        }

        if (fxName.equals( "splash" ))
        {
            EntityFX someParticle = new EntitySplashFX( clientWorld,
                                                        posX,
                                                        posY,
                                                        posZ,
                                                        velX,
                                                        velY,
                                                        velZ );
            Minecraft.getMinecraft().effectRenderer.addEffect( someParticle );
        }
        else
        {
            // Normal minecraft particle system ignores velocity completely.
            clientWorld.spawnParticle( fxName,
                                       posX,
                                       posY,
                                       posZ,
                                       0.0D,
                                       0.0D,
                                       0.0D );
        }
    }

    @Override
    public String getItemDisplayName(ItemStack stack)
    {
        if (Item.itemsList[stack.itemID] == null)
        {
            return "";
        }

        return Item.itemsList[stack.itemID].getItemDisplayName( stack );
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

        fovModifierHand = fovModifierHand > 0.001F
                          ? fovModifierHand
                          : (Float) ObfuscationReflectionHelper.getPrivateValue( EntityRenderer.class,
                                                                                 Minecraft.getMinecraft().entityRenderer,
                                                                                 "fovModifierHand",
                                                                                 "field_78507_R" );
        fovModifierHand += (f - fovModifierHand) * 0.5F;

        if (fovModifierHand > 1.5F)
        {
            fovModifierHand = 1.5F;
        }

        if (fovModifierHand < 0.1F)
        {
            fovModifierHand = 0.1F;
        }

        ObfuscationReflectionHelper.setPrivateValue( EntityRenderer.class,
                                                     Minecraft.getMinecraft().entityRenderer,
                                                     fovModifierHand,
                                                     "fovModifierHand",
                                                     "field_78507_R" );
    }

    @Override
    public void probeOpenGLCapabities()
    {
        // OpenGL capabilities.
        gl32_enabled = GLContext.getCapabilities().OpenGL32;
    }

    @Override
    public void tryEnableItemStackRenderer()
    {
        if (gl32_enabled)
        {
            TickRegistry.registerTickHandler( new RenderTickHandler(),
                                              Side.CLIENT );
        }
        else
        {
            LogWrapper.severe( "OpenGL 3.2 not detected, mod data dump for images and item/block JSON data will not work!" );
        }
    }

    @Override
    public void dumpGameAssetJSON()
    {
        // Get the Minecraft data directory.
        File dataDir = FMLClientHandler.instance().getClient().mcDataDir;

        // Build a path to dump directory.
        File dumpDirectory = new File( dataDir,
                                       "dump" );
        try
        {
            // Clean the dump directory if it exists.
            if (dumpDirectory.exists())
            {
                FileUtils.deleteDirectory( dumpDirectory );
            }
        }
        catch (IOException err)
        {
            err.printStackTrace();
        }

        // Renderer that we will be using to render game assets.
        itemStackCamera = new ItemStackRenderer( DEFAULT_MAIN_TEXTURE_SIZE );

        // List that holds final result of all entries.
        List<MinecraftItemJSONObject> vanillaObjects = new ArrayList<MinecraftItemJSONObject>();

        // Loop through all the items in the game looking for the ones we want.
        for (Item potentialMCItem : Item.itemsList)
        {
            if (potentialMCItem == null)
            {
                continue;
            }

            String proceseedName = potentialMCItem.getUnlocalizedName();
            String displayName = potentialMCItem.getStatName();

            if (displayName.contains( ".item" ) ||
                displayName.contains( ".tile" ) ||
                displayName.contains( ".name" ))
            {
                continue;
            }

            ItemStack vanillaItem = new ItemStack( potentialMCItem,
                                                   1,
                                                   0 );
            if (vanillaItem != null && itemStackCamera != null)
            {
                // Only add the item if it does not already exist.
                MinecraftItemJSONObject itemObj = new MinecraftItemJSONObject( potentialMCItem.itemID,
                                                                               potentialMCItem.getDamage( vanillaItem ),
                                                                               displayName,
                                                                               proceseedName );
                if (itemObj != null && ! vanillaObjects.contains( itemObj ))
                {
                    // Base item info rendering and add.
                    itemStackCamera.RenderItemStack( vanillaItem );
                    vanillaObjects.add( itemObj );

                    // Check if item has sub-types (metadata) items.
                    if (potentialMCItem.getHasSubtypes())
                    {
                        // Query list of sub-items from the given item.
                        List<ItemStack> subItemsArray = new ArrayList<ItemStack>();
                        potentialMCItem.getSubItems( potentialMCItem.itemID,
                                                     potentialMCItem.getCreativeTab(),
                                                     subItemsArray );
                        if (! subItemsArray.isEmpty())
                        {
                            // Loop through all sub-items returned from the item itself.
                            for (ItemStack subItemStack : subItemsArray)
                            {
                                if (subItemStack == null)
                                {
                                    continue;
                                }

                                String subProceseedName = subItemStack.getItem().getUnlocalizedName();
                                String subDisplayName = subItemStack.getItem().getItemDisplayName( subItemStack );
                                MinecraftItemJSONObject subItemObj = new MinecraftItemJSONObject( subItemStack.itemID,
                                                                                                  subItemStack.getItemDamage(),
                                                                                                  subDisplayName,
                                                                                                  subProceseedName );

                                // Render and add our sub-item to list.
                                itemStackCamera.RenderItemStack( subItemStack );
                                vanillaObjects.add( subItemObj );
                            }
                        }
                    }
                }
            }
        }

        // Loop through all the blocks in the game looking for the ones we want.
        for (Block potentialMCBlock : Block.blocksList)
        {
            if (potentialMCBlock == null)
            {
                continue;
            }

            String proceseedName = potentialMCBlock.getUnlocalizedName();
            String displayName = potentialMCBlock.getLocalizedName();

            if (displayName.contains( ".item" ) ||
                displayName.contains( ".tile" ) ||
                displayName.contains( ".name" ))
            {
                continue;
            }

            ItemStack vanillaBlock = new ItemStack( potentialMCBlock,
                                                    1,
                                                    0 );
            if (vanillaBlock != null && itemStackCamera != null)
            {
                // Only add the block if it does not already exist.
                MinecraftItemJSONObject blockObj = new MinecraftItemJSONObject( potentialMCBlock.blockID,
                                                                                0,
                                                                                displayName,
                                                                                proceseedName );
                if (blockObj != null && ! vanillaObjects.contains( blockObj ))
                {
                    itemStackCamera.RenderItemStack( vanillaBlock );
                    vanillaObjects.add( blockObj );
                }
            }
        }

        // Create a JSON builder that makes nice human-readable entries and only uses the fields we specified.
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        // Convert the data portion of our tile entity factory product to JSON string.
        String json = gson.toJson( vanillaObjects );

        try
        {
            // Save this list of names to the disk.
            FileUtils.writeStringToFile( new File( dataDir,
                                                   "dump/items.json" ),
                                         json );
        }
        catch (IOException err)
        {
            err.printStackTrace();
        }
    }

    /**
     * Serializes all registered machines to disk. Meant for developer use only. Use with caution!
     */
    @Override
    public void dumpModProjectJSON()
    {
        // Create a JSON builder that makes nice human-readable entries and only uses the fields we specified. 
        Gson gson =
                new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().serializeNulls().create();

        // Convert the data loaded for this mod into JSON string.
        ModData data = ModLoader.getMadModData();
        String json = gson.toJson( data,
                                   ModData.class );
        try
        {
            // Save this information to the disk!
            File dataDir = FMLClientHandler.instance().getClient().mcDataDir;
            FileUtils.writeStringToFile( new File( dataDir,
                                                   "dump/" + ModMetadata.ID +
                                                   ".json" ),
                                         json );
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
        Iterable<FluidFactoryProduct> registeredFluids = FluidFactory.instance().getFluidInfoList();
        for (Iterator iterator = registeredFluids.iterator(); iterator.hasNext(); )
        {
            FluidFactoryProduct registeredFluid = (FluidFactoryProduct) iterator.next();
            if (registeredFluid.getFluidID() == itemOrBlockID)
            {
                // Register fluid renderer with Minecraft/Forge. Subject to change between Forge versions.
                MinecraftForge.EVENT_BUS.register( new FluidRenderingTemplate( registeredFluid ) );

                // Allows us to override icon displays and how fluid is rendered in pipes and tanks.
                RenderingRegistry.registerBlockHandler( new FluidRenderingTemplate( registeredFluid ) );
            }
        }

        // -------------
        // TILE ENTITIES
        // -------------
        Iterable<TileEntityFactoryProduct> registeredMachines = TileEntityFactory.instance().getMachineInfoList();
        for (Iterator iterator = registeredMachines.iterator(); iterator.hasNext(); )
        {
            TileEntityFactoryProduct registeredMachine = (TileEntityFactoryProduct) iterator.next();
            if (registeredMachine.getBlockID() == itemOrBlockID)
            {
                // Populates rendering factory with master reference to what a given machine should have associated with it model and texture wise.
                RenderingFactory.instance().registerModelsToProduct( registeredMachine.getMachineName(),
                                                                     registeredMachine.getModelArchive() );

                // Minecraft/Forge related registry calls, these are subject to change between Forge versions.
                RenderingRegistry.registerBlockHandler( itemOrBlockID,
                                                        new RendererTemplate() );
                ClientRegistry.bindTileEntitySpecialRenderer( TileEntityPrefab.class,
                                                              new RendererTemplate() );
                MinecraftForgeClient.registerItemRenderer( itemOrBlockID,
                                                           new RendererTemplate() );
            }
        }
    }

    @Override
    public void registerSoundHandler()
    {
        // Register the sound event handling class.
        MinecraftForge.EVENT_BUS.register( new SoundLoader() );
    }
}
