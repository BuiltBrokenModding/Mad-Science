package madscience.factory;


import cpw.mods.fml.common.registry.GameRegistry;
import madscience.data.MadFluidFactoryProductData;
import madscience.itemblock.MadItemBlockTooltip;
import madscience.mod.MadForgeMod;
import madscience.mod.MadModLoader;
import madscience.product.MadFluidFactoryProduct;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.*;


public class MadFluidFactory
{
    private static final Map<String, MadFluidFactoryProduct> registeredFluids =
            new LinkedHashMap<String, MadFluidFactoryProduct>();
    private static MadFluidFactory instance;

    public MadFluidFactory()
    {
        super();
    }

    public static synchronized MadFluidFactory instance()
    {
        if (instance == null)
        {
            instance = new MadFluidFactory();
        }

        return instance;
    }

    public MadFluidFactoryProduct registerFluid(MadFluidFactoryProductData fluidData) throws IllegalArgumentException
    {
        // Pass the data object into the product to activate it, creates needed data structures inside it based on data supplied.
        MadFluidFactoryProduct fluidProduct = new MadFluidFactoryProduct( fluidData );

        // Check to make sure we have not added this item before.
        if (! isValidFluidID( fluidProduct.getFluidName() ))
        {
            throw new IllegalArgumentException( "Duplicate MadFluidFactoryProduct '" +
                                                fluidProduct.getFluidName() +
                                                "' was added. Execution halted!" );
        }

        // Debugging!
        MadModLoader.log().info( "[MadFluidFactory]Registering fluid: " + fluidProduct.getFluidName() );

        // Actually register the fluid with the product listing.
        registeredFluids.put( fluidProduct.getFluidName(),
                              fluidProduct );

        // Register the fluid with Minecraft/Forge.
        GameRegistry.registerBlock( fluidProduct.getFluidBlock(),
                                    MadItemBlockTooltip.class,
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
        MadForgeMod.proxy.registerRenderingHandler( fluidProduct.getFluidID() );

        return fluidProduct;
    }

    public boolean isValidFluidID(String blockBaseName)
    {
        return ! MadFluidFactory.registeredFluids.containsKey( blockBaseName );
    }

    public MadFluidFactoryProduct getFluidInfo(String id)
    {
        return MadFluidFactory.registeredFluids.get( id );
    }

    public Collection<MadFluidFactoryProduct> getFluidInfoList()
    {
        return Collections.unmodifiableCollection( MadFluidFactory.registeredFluids.values() );
    }

    public List<MadFluidFactoryProductData> getFluidDataList()
    {
        // Loop through every registered block in the system.
        List<MadFluidFactoryProductData> allFluids = new ArrayList<MadFluidFactoryProductData>();

        for (Iterator iterator = getFluidInfoList().iterator(); iterator.hasNext(); )
        {
            MadFluidFactoryProduct singleFluid = (MadFluidFactoryProduct) iterator.next();
            if (singleFluid != null)
            {
                // Add the block configuration data to our list for saving.
                allFluids.add( singleFluid.getData() );
            }
        }

        // Sort the list alphabetically.
        Collections.sort( allFluids,
                          new Comparator<MadFluidFactoryProductData>()
                          {
                              @Override
                              public int compare(final MadFluidFactoryProductData object1,
                                                 final MadFluidFactoryProductData object2)
                              {
                                  return object1.getFluidName().compareTo( object2.getFluidName() );
                              }
                          } );

        return allFluids;
    }
}
