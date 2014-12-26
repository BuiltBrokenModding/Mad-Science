package madscience.product;


import madscience.fluid.FluidBlockTemplate;
import madscience.fluid.FluidBucketTemplate;
import madscience.fluid.FluidTemplate;
import madscience.fluid.UnregisteredFluid;
import net.minecraftforge.fluids.FluidRegistry;


public class FluidFactoryProduct
{
    /**
     * Serialized data that is loaded from JSON loader and also saved.
     */
    private UnregisteredFluid data = null;

    /**
     * Instance of fluid for fluid registry.
     */
    private FluidTemplate fluidTemplate = null;

    /**
     * Instance of block fluid classic for fluid source block.
     */
    private FluidBlockTemplate blockFluidTemplate = null;

    /**
     * Instance of item fluid container (bucket by default) capable of holding some amount of fluid.
     * The entire Minecraft/Forge way of hooking and controlling this is via the EVENT_BUS.
     */
    private FluidBucketTemplate bucketTemplate = null;

    public FluidFactoryProduct(UnregisteredFluid blockData)
    {
        // Information about the fluid we save and load to disk via JSON loader.
        this.data = blockData;

        // Still's ID must be 1 above Flowing.
        this.fluidTemplate = new FluidTemplate( this );

        // Register this fluid in the game registry.
        // Note: This is done here because registering the block fluid classic will query the fluid registry. 
        FluidRegistry.registerFluid( this.fluidTemplate );

        // Create the block of flowing liquid only after registering it with the system.
        this.blockFluidTemplate = new FluidBlockTemplate( this );

        // Only create container information if container item is desired.
        if (blockData.hasFluidContainerItem())
        {
            // Container for our fluid.
            this.bucketTemplate = new FluidBucketTemplate( this );
        }
    }

    public UnregisteredFluid getData()
    {
        return data;
    }

    public String getFluidName()
    {
        return data.getFluidName();
    }

    public FluidBlockTemplate getFluidBlock()
    {
        return blockFluidTemplate;
    }

    public int getDensity()
    {
        return data.getDensity();
    }

    public int getViscosity()
    {
        return data.getViscosity();
    }

    public int getLuminosity()
    {
        return data.getLuminosity();
    }

    public int getFluidID()
    {
        return data.getFluidID();
    }

    public FluidTemplate getFluid()
    {
        return fluidTemplate;
    }

    public int getFluidContainerID()
    {
        return data.getFluidContainerID();
    }

    public String getFluidContainerName()
    {
        return data.getFluidContainerName();
    }

    public FluidBucketTemplate getFluidContainer()
    {
        return bucketTemplate;
    }

    public String getIconStillPath()
    {
        return data.getIconStillPath();
    }

    public String getIconFlowingPath()
    {
        return data.getIconFlowingPath();
    }

    public String getIconFluidContainerPath()
    {
        return data.getIconFluidContainerPath();
    }

    public boolean getShowFluidBlockInCreativeTab()
    {
        return data.getShowFluidBlockInCreativeTab();
    }
}
