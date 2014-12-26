package madscience.factory;


import cpw.mods.fml.common.registry.GameRegistry;
import madscience.fluid.UnregisteredFluid;
import madscience.itemblock.ItemBlockTooltip;
import madscience.mod.ForgeMod;
import madscience.mod.ModLoader;
import madscience.product.FluidFactoryProduct;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.*;


public class FluidFactory
{
    private static final Map<String, FluidFactoryProduct> registeredFluids =
            new LinkedHashMap<String, FluidFactoryProduct>();

    private static FluidFactory instance;

    public FluidFactory()
    {
        super();
    }

    public static synchronized FluidFactory instance()
    {
        if (instance == null)
        {
            instance = new FluidFactory();
        }

        return instance;
    }

    public FluidFactoryProduct registerFluid(UnregisteredFluid fluidData) throws IllegalArgumentException
    {
        // Pass the data object into the product to activate it, creates needed data structures inside it based on data supplied.
        FluidFactoryProduct fluidProduct = new FluidFactoryProduct( fluidData );

        // Check to make sure we have not added this item before.
        if (! isValidFluidID( fluidProduct.getFluidName() ))
        {
            throw new IllegalArgumentException( "Duplicate FluidFactoryProduct '" +
                                                fluidProduct.getFluidName() +
                                                "' was added. Execution halted!" );
        }

        // Debugging!
        ModLoader.log().info( "[FluidFactory]Registering fluid: " + fluidProduct.getFluidName() );

        // Actually register the fluid with the product listing.
        registeredFluids.put( fluidProduct.getFluidName(),
                              fluidProduct );

        // Register the fluid with Minecraft/Forge.
        GameRegistry.registerBlock( fluidProduct.getFluidBlock(),
                                    ItemBlockTooltip.class,
                                    fluidProduct.getFluidName() );

        // Only register container item and call backs if they are enabled.
        if (fluidData.hasFluidContainerItem())
        {
            // Register fluid container item.
            GameRegistry.registerItem( fluidProduct.getFluidContainer(),
                                       fluidProduct.getFluidContainerName() );

            // Register our class as a valid container for our fluid.
            FluidContainerRegistry.registerFluidContainer( new FluidContainerData( FluidRegistry.getFluidStack( fluidProduct.getFluidName(),
                                                                                                                FluidContainerRegistry.BUCKET_VOLUME ),
                                                                                   new ItemStack( fluidProduct.getFluidContainer() ),
                                                                                   new ItemStack( Item.bucketEmpty ) ) );

            // Hook event in forge for filling a bucket with liquid.
            MinecraftForge.EVENT_BUS.register( fluidProduct.getFluidContainer() );
        }

        // Register custom rendering for GUI's.
        ForgeMod.proxy.registerRenderingHandler( fluidProduct.getFluidID() );

        return fluidProduct;
    }

    public boolean isValidFluidID(String blockBaseName)
    {
        return ! FluidFactory.registeredFluids.containsKey( blockBaseName );
    }

    public FluidFactoryProduct getFluidInfo(String id)
    {
        return FluidFactory.registeredFluids.get( id );
    }

    public Collection<FluidFactoryProduct> getFluidInfoList()
    {
        return Collections.unmodifiableCollection( FluidFactory.registeredFluids.values() );
    }

    public List<UnregisteredFluid> getFluidDataList()
    {
        // Loop through every registered block in the system.
        List<UnregisteredFluid> allFluids = new ArrayList<UnregisteredFluid>();

        for (Iterator iterator = getFluidInfoList().iterator(); iterator.hasNext(); )
        {
            FluidFactoryProduct singleFluid = (FluidFactoryProduct) iterator.next();
            if (singleFluid != null)
            {
                // Add the block configuration data to our list for saving.
                allFluids.add( singleFluid.getData() );
            }
        }

        // Sort the list alphabetically.
        Collections.sort( allFluids,
                          new Comparator<UnregisteredFluid>()
                          {
                              @Override
                              public int compare(final UnregisteredFluid object1, final UnregisteredFluid object2)
                              {
                                  return object1.getFluidName().compareTo( object2.getFluidName() );
                              }
                          } );

        return allFluids;
    }
}
